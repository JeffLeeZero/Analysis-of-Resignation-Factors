package com.zpj.mapper;

import com.zpj.pojo.Worker;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface WorkerMapper {

    int insertWorker(Worker worker);

    List<Worker> queryWorker(String account);

    String queryAllNumber();

    String queryLeftNumber();

    int deleteWorker(@Param("account")String account, @Param("workNumber")String workerNumber, @Param("satisfactionLevel")String satisfactionLevel, @Param("lastEvaluation")String lastEvaluation, @Param("numberProject")String numberProject, @Param("averageMonthlyHours")String averageMonthlyHours, @Param("timeSpendCompany")String timeSpendCompany , @Param("workAccident")String workAccident , @Param("left")String left, @Param("promotion")String promotion, @Param("sales")String sales, @Param("salary")String salary);



}
