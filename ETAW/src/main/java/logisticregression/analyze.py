import sys,os
PATH = os.path.abspath(os.path.dirname(os.path.dirname(__file__)))
sys.path.append(PATH)
import pandas as pd
import cx_Oracle as oracle
import _pickle as pickle
from logisticregression.logistic_regression import LogisticRegression

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

def export_model(aid, department):
    """
    根据用户ID和职位选择（java传入）选择数据库中的模型，导出
    :param aid:用户ID
    :param department:职位
    :return:模型对象和拟合度
    """
    conn_str = 'FRANK/ZD73330274@localhost/orcl'
    db = oracle.connect(conn_str)
    cursor = db.cursor()
    #选出特定的拟合度和模型数据
    sql = "select SCORE, MODEL from REGRESSION where AID = '" + aid +"' and DEPARTMENT = '" + department +"'"
    cursor.execute(sql)
    db.commit()
    result = cursor.fetchall()
    score = result[0][0]
    result_log_reg = result[0][1].read()
    #反序列化
    log_reg = pickle.loads(result_log_reg)
    cursor.close()
    db.close()
    return log_reg, score

def main(data,aid,department):
    predict_result_float_arry = []

    x_test = data2dataframe(data)
    log_reg, score = export_model(aid, department)
    predict_result = log_reg.predict(x_test)

    predict_result_float_arry.append(str(float(predict_result)))
    predict_result_float_arry.append(score)

    for i in predict_result_float_arry:
        print(i)

#data='0.38 0.53 157 3 0 0 0',aid = '1', department = 'IT'
if __name__ == "__main__":
    a = []
    a.append(sys.argv[1])
    a.append(sys.argv[2])
    a.append(sys.argv[3])
    main(a[0],a[1],a[2])
