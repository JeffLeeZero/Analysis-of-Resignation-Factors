package com.zpj.mapper;

import com.zpj.pojo.Worker;
import java.util.List;

public interface WorkerMapper {

    int insertWorker(Worker worker);

    List<Worker> queryWorker(String account);

    String queryAllNumber();

    String queryLeftNumber();


}
