package com.zpj;

import com.zpj.mapper.WorkerMapper;
import com.zpj.util.MybatiesUtil;
import org.apache.ibatis.session.SqlSession;

public class Analysis {


    public static String getAllNumber(){
        try {
            SqlSession sqlSession = MybatiesUtil.getSession();
            WorkerMapper workerMapper = sqlSession.getMapper(WorkerMapper.class);
            return  workerMapper.queryAllNumber();
        } catch (Exception e){
            System.out.println("查询公司总人数错误"+e);
            return null;
        }
    }

    public static String getLeftNumber(){
        try {
            SqlSession sqlSession = MybatiesUtil.getSession();
            WorkerMapper workerMapper = sqlSession.getMapper(WorkerMapper.class);
            return workerMapper.queryLeftNumber();
        } catch (Exception e){
            System.out.println("查询公司离职人数错误"+e);
            return null;
        }

    }

}
