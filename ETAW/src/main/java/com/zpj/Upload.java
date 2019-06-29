package com.zpj;

import com.zpj.pojo.Worker;
import com.zpj.servlet.AddServlet;
import com.zpj.servlet.UploadServlet;

import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.util.ArrayList;
import java.util.List;
import com.zpj.mapper.WorkerMapper;
import com.zpj.util.MybatiesUtil;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

public class Upload {

    public static List getAllByExcel(String fileName) {
        List<String> list = new ArrayList<String>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = null;
            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");
                String last = item[item.length - 1];
                list.add(last);
                try {
                    SqlSession sqlSession = MybatiesUtil.getSession();
                    WorkerMapper mapper = sqlSession.getMapper(WorkerMapper.class);
                    Worker worker = new Worker();
                    worker.setSatisfaction_level(item[0]);
                    worker.setLast_evaluation(item[1]);
                    worker.setNumber_project(item[2]);
                    worker.setAverage_monthly_hours(item[3]);
                    worker.setTime_spend_company(item[4]);
                    worker.setWork_accident(item[5]);
                    worker.setLeft(item[6]);
                    worker.setPromotion(item[7]);
                    worker.setSales(item[8]);
                    worker.setSalary(item[9]);
                    try {
                        mapper.insertWorker(worker);
                    } catch (PersistenceException e) {
                        e.printStackTrace();
                    }
                    sqlSession.commit();
                    sqlSession.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            return list;
        } catch (Exception e) {
            System.out.println("error" + e);
            return null;
        }
    }
}
