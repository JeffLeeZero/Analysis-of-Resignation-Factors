import sys,os
PATH = os.path.abspath(os.path.dirname(os.path.dirname(__file__)))
sys.path.append(PATH)
from sklearn.model_selection import train_test_split
import pandas as pd
from logisticregression.logistic_regression import LogisticRegression
import cx_Oracle as oracle
import _pickle as pickle



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
    return saleset,data

def train(saleset, data):
    """
    训练数据模型
    :param saleset:职位数据
    :param data:数据集
    :return:返回模型list和拟合度list
    """
    model_score = []
    model_parameter = []
    for i in saleset:
        #区分出每个职位的数据集
        salesrow = data.loc[data['sales'] == i]
        x = salesrow.loc[:, ['satisfaction_level', 'last_evaluation', 'average_montly_hours', 'time_spend_company',
                             'Work_accident', 'promotion_last_5years', 'salary']]
        y = salesrow.iloc[:, 9]
        #训练模型
        x_train, x_test, y_train, y_test = train_test_split(x, y, test_size=0.1, random_state=0)
        log_reg = LogisticRegression()
        log_reg.fit(x_train, y_train)
        #用于序列化模型对象
        model_parameter.append(pickle.dumps(log_reg))
        model_score.append(log_reg.score(x_test, y_test))
    return model_parameter, model_score, saleset

def import_model(mp,ms,s,aid):
    """
    导入数据模型到数据库中
    :param mp:模型参数集
    :param ms:模型拟合度集
    :param s:职位集
    :param i:用户ID
    :return:
    """
    conn_str = 'FRANK/ZD73330274@localhost/orcl'
    db = oracle.connect(conn_str)
    model_data = pd.DataFrame(mp, columns=['MODEL'])
    model_data['DEPARTMENT'] = pd.Series(list(s))
    model_data['SCORE'] = pd.Series(ms)
    cursor = db.cursor()
    #判断数据库中是否有相同的用户ID
    sql = "select aid from regression"
    cursor.execute(sql)
    db.commit()
    aid_set = cursor.fetchall()
    #如果数据库不为空
    if (len(aid_set)):
        for i in aid_set:
            #比较是否已存在该用户ID，如果有则跳过不存
            if (i == tuple(aid)):
                break
            else:
                #获取到model_data的行总数，依次选出对应的model_parameter、model_score和职位
                for i in range(model_data.shape[0]):
                    sql = "insert into REGRESSION (AID, DEPARTMENT,SCORE，MODEL) VALUES ('%s', '%s','%s',:blobData)" % (
                        aid, str(model_data['DEPARTMENT'][i]), str(model_data['SCORE'][i]))
                    cursor.setinputsizes(blobData=oracle.BLOB)
                    cursor.execute(sql, {'blobData': model_data['MODEL'][i]})
                    db.commit()
                break
    #为空直接导入
    else:
        for i in range(model_data.shape[0]):
            sql = "insert into REGRESSION (AID, DEPARTMENT,SCORE，MODEL) VALUES ('%s', '%s','%s',:blobData)" % (
                aid, str(model_data['DEPARTMENT'][i]), str(model_data['SCORE'][i]))
            cursor.setinputsizes(blobData=oracle.BLOB)
            cursor.execute(sql, {'blobData': model_data['MODEL'][i]})
            db.commit()
    cursor.close()
    db.close()

def main(aid,filepath):
    filepath = open(filepath)
    saleset, data = get_csvfile(filepath)
    model_parameter, model_score, saleset = train(saleset, data)
    import_model(model_parameter, model_score, saleset, aid)

if __name__ == "__main__":
    a = []
    a.append(sys.argv[1])
    a.append(sys.argv[2])
    main(a[0],a[1])



