import sys, os
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

def choose_model(aid, department):
    """
    根据用户ID和职位选择（java传入）选择数据库中的模型，导出
    :param aid:用户ID
    :param department:职位
    :return:模型对象和拟合度
    """

    db = get_connection('admin/123456@orcl')
    #db = get_connection('FRANK/ZD73330274@localhost/orcl')

    cursor = db.cursor()
    log_regs = []
    svms = []
    log_reg_scores = []
    svm_scores = []
    #选出特定的拟合度和模型数据
    for i in department:
        args = (aid,i,aid,i)
        sql = "select regression.score, regression.model, svm.score, svm.model from regression,svm where regression.aid = :1 and" \
              " regression.department = :2 and svm.aid = :3 and svm.department = :4"
        cursor.execute(sql,args)
        db.commit()
        result = cursor.fetchall()
        # 逻辑回归和svm模型
        log_reg_score = result[0][0]
        log_reg = result[0][1].read()
        svm_score = result[0][2]
        svm = result[0][3].read()

        # 反序列化
        log_reg = pickle.loads(log_reg)
        svm = pickle.loads(svm)
        log_reg_scores.append(log_reg_score)
        log_regs.append(log_reg)
        svms.append(svm)
        svm_scores.append(svm_score)
    cursor.close()
    db.close()
    return log_regs, log_reg_scores, svms, svm_scores

def main(csvfileurl,aid):
    log_reg_predict_float_result = []
    svm_predict_float_result  =[]
    sale_data, saleset = data2dataframe(csvfileurl)

    log_regs, log_reg_scores, svms, svm_scores = choose_model(aid, saleset)
    count = 0
    for i in sale_data:
        log_reg_predict = log_regs[count].predict(i)
        svm_predict = svms[count].predict(i)
        for j in log_reg_predict:
            log_reg_predict_float_result.append(str(float(j)))
            log_reg_predict_float_result.append(log_reg_scores[count])
        for j in svm_predict:
            svm_predict_float_result.append(str(float(j)))
            svm_predict_float_result.append(svm_scores[count])
        count = count+1

    for i in log_reg_predict_float_result:
        print(i)
    for i in svm_predict_float_result:
        print(i)
# if __name__ == "__main__":
#     main('C:\\Users\\west\\Desktop\\Analysis-of-Resignation-Factors\\ETAW\\import_test.csv', '369分析方案' )
if __name__ == "__main__":
    a = []
    a.append(sys.argv[1])
    a.append(sys.argv[2])
    main(a[0],a[1])