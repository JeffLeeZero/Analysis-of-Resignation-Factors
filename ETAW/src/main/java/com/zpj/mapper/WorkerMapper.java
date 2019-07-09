package com.zpj.mapper;

import com.zpj.pojo.Worker;
import java.util.List;

public interface WorkerMapper {

    int insertWorker(Worker worker);

    List<Worker> queryWorker(String account);

    String queryAllNumber();

    String queryLeftNumber();

    int deleteWorker(String account,String workerNumber,String satisfactionLevel, String lastEvaluation, String numberProject,String averageMonthlyHours,String timeSpendCompany ,String workAccident ,String left, String promotion,String sales, String salary);

    int updateResult(Worker work);


}
