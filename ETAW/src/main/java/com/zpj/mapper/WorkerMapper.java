package com.zpj.mapper;

import com.zpj.pojo.Worker;
import org.apache.ibatis.annotations.Param;

public interface WorkerMapper {

    int insertWorker(Worker worker);

    String queryAllNumber();

    String queryLeftNumber();
}
