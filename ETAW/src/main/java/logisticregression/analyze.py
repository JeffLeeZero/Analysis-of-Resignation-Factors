import sys,os
PATH = os.path.abspath(os.path.dirname(os.path.dirname(__file__)))
sys.path.append(PATH)
import pandas as pd
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

def data2dataframe(data):
    """
    将用户手动输入的数据（JAVA传输）转化为可以分析的dataframe结构
    :param data:输入的数据字符串
    :return:测试数据集
    """
    data_float_list = data.split(",")
    #处理数据
    data_float_list[0] = pd.Series(float(data_float_list[0]))
    test_data = pd.DataFrame(data_float_list[0], columns=['satisfaction_level'])
    test_data['last_evaluation'] = pd.Series(float(data_float_list[1]))
    test_data['average_montly_hours'] = pd.Series(float(data_float_list[2]))
    test_data['number_project'] = pd.Series(float(data_float_list[3]))
    test_data['time_spend_company'] = pd.Series(float(data_float_list[4]))
    test_data['Work_accident'] = pd.Series(float(data_float_list[5]))
    test_data['promotion_last_5years'] = pd.Series(float(data_float_list[6]))
    test_data['salary'] = pd.Series(float(data_float_list[7]))
    return test_data

def choose_model(aid, department):
    """
    根据用户ID和职位选择（java传入）选择数据库中的模型，导出
    :param aid:用户模型分析方案
    :param department:职位
    :return:模型对象和拟合度
    """
    #db = get_connection('admin/123456@localhost/SYSTEM')
    db = get_connection(('FRANK/ZD73330274@localhost/orcl'))
    cursor = db.cursor()
    #选出特定的逻辑回归模型和向量机模型
    args = (aid, department,aid, department)
    cursor.execute('select regression.score, regression.model, svm.score, svm.model from regression,svm where regression.aid = :1 and regression.department = :2 and svm.aid = :3 and svm.department = :4'
               , args)
    db.commit()
    result = cursor.fetchall()
    #逻辑回归和svm模型
    log_reg_score = result[0][0]
    log_reg = result[0][1].read()
    svm_score = result[0][2]
    svm = result[0][3].read()
    #反序列化
    log_reg = pickle.loads(log_reg)
    svm = pickle.loads(svm)
    cursor.close()
    db.close()
    return log_reg, log_reg_score, svm, svm_score


def main(data='0.38,0.53,157,2,3,0,0,0',aid = 'jeff12分析方案', department = 'IT'):
    log_reg_predict_float_result = []
    svm_predict_float_result  =[]
    x_test = data2dataframe(data)
    log_reg, log_reg_score, svm, svm_score = choose_model(aid, department)

    log_reg_predict = log_reg.predict(x_test)
    log_reg_predict_float_result.append(str(float(log_reg_predict)))
    log_reg_predict_float_result.append(log_reg_score)

    svm_predict = svm.predict(x_test)
    svm_predict_float_result.append(str(float(svm_predict)))
    svm_predict_float_result.append(svm_score)

    for i in log_reg_predict_float_result:
        print(i)
    for i in svm_predict_float_result:
        print(i)

#data='0.38 0.53 157 3 0 0 0',aid = '1', department = 'IT'
if __name__ == "__main__":
    a = []
    a.append(sys.argv[1])
    a.append(sys.argv[2])
    a.append(sys.argv[3])
    main(a[0],a[1],a[2])
