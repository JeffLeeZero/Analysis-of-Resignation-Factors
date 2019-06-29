package com.zpj;

import com.zpj.mapper.UserMapper;
import com.zpj.mapper.WorkerMapper;
import com.zpj.pojo.User;
import com.zpj.pojo.Worker;
import com.zpj.util.MybatiesUtil;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class test1 {
    @Test
    public void m1(){
        SqlSession sqlSession = MybatiesUtil.getSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        String pass = null;
        User user = new User();
        user.setAccount("123");
        user.setPassword("123456");
        try {
           pass=mapper.queryPassword("17371445076");
//           mapper.insertUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sqlSession.commit();
        sqlSession.close();
        System.out.println(pass==null);
    }

/*
    @Test
    public void m2(){
        SqlSession sqlSession = MybatiesUtil.getSession();
        WorkerMapper workerMapper = sqlSession.getMapper(WorkerMapper.class);
        String pass = "pass";
        Worker worker = new Worker();
        worker.setSatisfaction_level("1");
        worker.setLast_evaluation("2");
        worker.setNumber_project("3");
        worker.setAverage_monthly_hours("4");
        worker.setTime_spend_company("5");
        worker.setWork_accident("6");
        worker.setLeft("7");
        worker.setPromotion("8");
        worker.setSales("9");
        worker.setSalary("10");
        try{
           workerMapper.insertWorker(worker);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sqlSession.commit();
        sqlSession.close();
        System.out.println(pass);
    }

    @Test
    public void m3() throws FileNotFoundException{
        User user = new User();
        user.setAccount("123");
        String url = "C:\\Users\\west\\Desktop\\upload\\员工离职数据.csv";
        File uploadFile = new File(url);
        if(uploadFile.exists()){
            uploadFile.mkdir();
        }
        InputStream is = null;
        try{
            is = new FileInputStream(uploadFile);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
            System.out.println(e);
        }

        byte[] att = new byte[0];
        try {
            ByteArrayOutputStream swapStream = new ByteArrayOutputStream();
            byte[] buff = new byte[100];
            int rc=0;
            while ((rc=is.read(buff,0,100))>0){
                swapStream.write(buff, 0, rc);
            }
            att = swapStream.toByteArray();
        } catch (IOException e) {
            e.printStackTrace();
            System.out.println(e);
        }
        user.setAttachment(att);

        byte[] test = user.getAttachment();
        for (int i=0;i<att.length;i++){
            System.out.print(test[i]);
        }

        SqlSession sqlSession = MybatiesUtil.getSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        try {
            mapper.insertAttachment(user);
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println(e);
        }
        sqlSession.commit();
        sqlSession.close();
    }

    @Test
    public void m4(){

        String pass = "pass";
        String fileName = "C:\\Users\\west\\Desktop\\upload\\员工离职数据.csv";

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
                    WorkerMapper workerMapper = sqlSession.getMapper(WorkerMapper.class);
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
                        workerMapper.insertWorker(worker);
                    } catch (PersistenceException e) {
                        e.printStackTrace();
                    }
                    sqlSession.commit();
                    sqlSession.close();
                    System.out.println(pass);
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
            System.out.println(list);
        } catch (Exception e) {
            System.out.println("error" + e);
        }
    }

*/


}
