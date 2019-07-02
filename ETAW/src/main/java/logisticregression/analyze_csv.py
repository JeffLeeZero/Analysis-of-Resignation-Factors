import sys, os
PATH = os.path.abspath(os.path.dirname(os.path.dirname(__file__)))
sys.path.append(PATH)
import pandas as pd
import cx_Oracle as oracle
import _pickle as pickle



def data2dataframe(csvfile):
    """
    将用户传入的csv文件中数据整理成预测数据集
    :param csvfile: csv文件路径
    :return: 预测数据集
    """
    data = pd.read_csv(csvfile)
    salary = data.loc[:, 'salary']
    salarylist = []
    for i in salary:
        salarylist.append(i)
    if('low' in salarylist):
        data.loc[data.salary == 'low', 'salary'] = 0
    if('medium' in salarylist):
        data.loc[data.salary == 'medium', 'salary'] = 1
    if('high' in salarylist):
        data.loc[data.salary == 'high', 'salary'] = 2

    z = data.loc[:,data['sales']]
    sale_data = []
    saleset = set()
    for i in z:
        saleset.update([i])
    for i in saleset:
        #区分出每个职位的数据集
        salesrow = data.loc[data['sales'] == i]
        x = salesrow.loc[:, ['satisfaction_level', 'last_evaluation', 'average_montly_hours', 'time_spend_company','number_project',
                             'Work_accident', 'promotion_last_5years', 'salary']]
        sale_data.append(x)
    return sale_data, saleset

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
    log_regs = []
    scores = []
    #选出特定的拟合度和模型数据
    for i in department:
        sql = "select SCORE, MODEL from REGRESSION where AID = '" + aid + "' and DEPARTMENT = '" + i + "'"
        cursor.execute(sql)
        db.commit()
        result = cursor.fetchall()
        score = result[0][0]
        result_log_reg = result[0][1].read()
        # 反序列化
        log_reg = pickle.loads(result_log_reg)
        log_regs.append(log_reg)
        scores.append(score)
    cursor.close()
    db.close()
    return log_regs, scores

def main(csvfileurl,aid):
    predict_result_float_arry = []
    sale_data, saleset = data2dataframe(csvfileurl)
    log_regs, scores = export_model(aid, saleset)

    """
    LogisticRegression2 方法
    count = 0
    for i in sale_data:
        predict_result = log_regs[count].predict(i)
        predict_result_proba = log_regs[count].predict_proba(i)
        for j in predict_result:
            predict_result_float_arry.append(str(float(j)))
            predict_result_float_arry.append(scores[count])
        count = count+1

    """

    count = 0
    for i in sale_data:
        predict_result = log_regs[count].predict(i)
        for j in predict_result:
            predict_result_float_arry.append(str(float(j)))
            predict_result_float_arry.append(scores[count])
        count = count+1

    for i in predict_result_float_arry:
        print(i)



if __name__ == "__main__":
    a = []
    a.append(sys.argv[1])
    a.append(sys.argv[2])
    main(a[0],a[1])