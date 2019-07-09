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

    public static List<Worker> getAllByExcel(String fileName) {
        List<Worker> workerList = new ArrayList<Worker>();
        List<String> list = new ArrayList<>();
        try {
            BufferedReader reader = new BufferedReader(new FileReader(fileName));
            String line = reader.readLine();
            while ((line = reader.readLine()) != null) {
                String item[] = line.split(",");
                String last = item[item.length - 1];
                list.add(last);
                Worker worker = new Worker();
                worker.setSatisfaction_level(item[0]);
                worker.setLast_evaluation(item[1]);
                worker.setAverage_monthly_hours(item[2]);
                worker.setNumber_project(item[3]);
                worker.setTime_spend_company(item[4]);
                worker.setWork_accident(item[5]);
                worker.setPromotion(item[6]);
                worker.setSalary(item[7]);
                worker.setSales(item[8]);
                worker.setWorker_number(item[9]);
                workerList.add(worker);
            }
            return workerList;
        } catch (Exception e) {
            System.out.println("error" + e);
            return null;
        }
    }

}
