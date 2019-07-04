import sys, os
PATH = os.path.abspath(os.path.dirname(os.path.dirname(__file__)))
sys.path.append(PATH)
from sklearn.model_selection import train_test_split
import pandas as pd
from sklearn import svm
import cx_Oracle as oracle
import _pickle as pickle

def get_connection(conn_str):
    """
    获取与oracle数据库的连接
    :param conn_str:
    :return:数据库操作对象
    """
    db = oracle.connect(conn_str)
    return db

def get_csvfile(filepath):
    """
    获取对应路径下的csv文件（路径由java参数传递）
    :param filepath:csv文件路径
    :return:对应的数据集dataframe和包含的职位信息set
    """
    data = pd.read_csv(filepath)
    data.loc[data.salary == 'low', 'salary'] = 0
    data.loc[data.salary == 'medium', 'salary'] = 1
    data.loc[data.salary == 'high', 'salary'] = 2
    z = data.iloc[:, 8]
    saleset = set()
    for i in z:
        saleset.update([i])
    x_set = data.loc[:, ['satisfaction_level', 'last_evaluation', 'average_montly_hours', 'time_spend_company','number_project',
                             'Work_accident', 'promotion_last_5years', 'salary']]
    y_set = data.loc[:,'left']
    return saleset,data, x_set, y_set

def train(saleset, data, x_set, y_set):
    """
    训练数据模型
    :param saleset:职位集合
    :param data:训练数据集
    :param x_set:选出除了Left和sales特征以外的所有特征集
    :param y_set:选出Left特征的特征集
    :return:存储svm模型和逻辑回归模型的参数和相应的正确率
    """
    svc_score = []
    svc_parameter = []
    for i in saleset:
        #区分出每个职位的数据集
        x = x_set.loc[data['sales'] == i]
        y = y_set.loc[data['sales'] == i]
        #训练模型
        x_train, x_test, y_train, y_test = train_test_split(x, y, test_size=0.2, random_state=0)
        #用svm训练模型
        svc = svm.SVC(kernel="linear")
        svc.fit(x_train,y_train)
        #用于序列化模型对象
        svc_parameter.append(pickle.dumps(svc))
        svc_score.append(svc.score(x_test, y_test))

    return svc_parameter, svc_score,saleset

def import_model(parameter,score,saleset,aid):
    """
    导入数据模型到数据库中
    :param parameter: 模型参数
    :param score:模型正确率
    :param saleset:职位集合
    :param aid:用户模型方案名称
    :param tablename:导入的数据库表
    :return:
    """
    db = get_connection('admin/123456@localhost/SYSTEM')
    model_data = pd.DataFrame(parameter, columns=['MODEL'])
    model_data['DEPARTMENT'] = pd.Series(list(saleset))
    model_data['SCORE'] = pd.Series(score)
    cursor = db.cursor()
    #判断数据库中是否有相同的用户ID
    sql = "select DISTINCT aid from svm"
    sql = sql.replace('\'','')
    cursor.execute(sql)
    db.commit()
    aid_set = cursor.fetchall()
    # 如果数据库不为空
    if (len(aid_set)):
        for i in aid_set:
            # 如果该用户模型方案命名与数据库中的一致，则跳过
            if (str(i[0]) == aid):
                break
            else:
                # 获取到model_data的行总数，依次选出对应的model_parameter、model_score和职位
                for i in range(model_data.shape[0]):
                    sql = "insert into svm (AID, DEPARTMENT,SCORE,MODEL) VALUES ('%s', '%s','%s',:blobData)" % (
                        aid, str(model_data['DEPARTMENT'][i]), str(model_data['SCORE'][i]))
                    sql = sql.replace('\'', '')
                    cursor.setinputsizes(blobData=oracle.BLOB)
                    cursor.execute(sql, {'blobData': model_data['MODEL'][i]})
                    db.commit()
            break
    # 为空直接导入
    else:
        for i in range(model_data.shape[0]):
            sql = "insert into svm (AID, DEPARTMENT,SCORE,MODEL) VALUES ('%s', '%s','%s',:blobData)" % (
                aid, str(model_data['DEPARTMENT'][i]), str(model_data['SCORE'][i]))
            cursor.setinputsizes(blobData=oracle.BLOB)
            cursor.execute(sql, {'blobData': model_data['MODEL'][i]})
            db.commit()
    cursor.close()
    db.close()

def main(aid,filepath):
    filepath = open(filepath)
    saleset, data, x_set, y_set = get_csvfile(filepath)
    svc_parameter, svc_score,saleset = train(saleset, data, x_set, y_set)
    import_model(svc_parameter, svc_score, saleset, aid)
    print("this is svm\n")

if __name__ == "__main__":
    main('369分析方案', r"C:\Users\west\Desktop\Analysis-of-Resignation-Factors\ETAW\test.csv")