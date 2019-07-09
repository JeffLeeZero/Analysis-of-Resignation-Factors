package com.zpj.servlet;

import analysis.Analyser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zpj.bean.RequestBean;
import com.zpj.mapper.WorkerMapper;
import com.zpj.pojo.User;
import com.zpj.pojo.Worker;
import com.zpj.util.MybatiesUtil;
import org.apache.ibatis.session.SqlSession;
import sun.rmi.runtime.Log;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

@WebServlet(name = "InsertWorkerServlet")
public class InsertWorkerServlet extends HttpServlet {

    private String account;
    private String left;
    private String accuracyRate;

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        account = request.getParameter("Account");
        String workerNumber = request.getParameter("Number");
        String satisfactionLevel = request.getParameter("SatisfactionLevel");
        String lastEvaluation = request.getParameter("LastEvaluation");
        String numberProject = request.getParameter("NumberProject");
        String averageMonthly = request.getParameter("AverageMonthly");
        String timeSpendCompany = request.getParameter("TimeSpendCompany");
        String workAccident = request.getParameter("WorkAccident");
        String promotion = request.getParameter("Promotion");
        String department = request.getParameter("Department");
        String salary = request.getParameter("Salary");

        ArrayList<String> data = new ArrayList<>();
        data.add(satisfactionLevel);
        data.add(lastEvaluation);
        data.add(averageMonthly);
        data.add(numberProject);
        data.add(timeSpendCompany);
        data.add(workAccident);
        data.add(promotion);
        data.add(salary);
        data.add(department);
        data.add(workerNumber);



        try{
            System.out.println(data);
            Analyser analyser = new Analyser(account);
            //得到离职率
            ArrayList<String> result = analyser.getProbability(data);
            System.out.println(result);
            left = result.get(0);
            accuracyRate = result.get(1);
            String notAccuracyRate = String.valueOf(1 - Double.valueOf(accuracyRate));
            String reason = "该员工";

            System.out.println("编号" + workerNumber + "员工信息分析成功");
            System.out.println("该员工是否会离职？ " + left);
            System.out.println("该预测的准确率为:" + accuracyRate);
            System.out.println("结果分析：" + reason);

            request.getSession().setAttribute("left", left);
            request.getSession().setAttribute("accuracyRate", accuracyRate);
            request.getSession().setAttribute("notAccuracyRate",notAccuracyRate);
            request.getSession().setAttribute("reason",reason);
            request.getRequestDispatcher("http://localhost:8080/analyseWorker.jsp").forward(request, response);

//            String message = "提交成功";
//            request.getSession().setAttribute("message",message);
//            response.sendRedirect("http://localhost:8080/analyseWorker.jsp");

        } catch (Exception e){
            e.printStackTrace();
//            String message = "提交失败";
//            request.getSession().setAttribute("message",message);
//            response.sendRedirect("http://localhost:8080/insertWorker.jsp");
        }

        Worker worker = new Worker();
        worker.setAccount(account);
        worker.setWorker_number(workerNumber);
        worker.setSatisfaction_level(satisfactionLevel);
        worker.setLast_evaluation(lastEvaluation);
        worker.setAverage_monthly_hours(averageMonthly);
        worker.setNumber_project(numberProject);
        worker.setTime_spend_company(timeSpendCompany);
        worker.setWork_accident(workAccident);
        worker.setPromotion(promotion);
        worker.setSalary(salary);
        worker.setSales(department);
        worker.setLeft(left);
        System.out.println(worker);
        SqlSession sqlSession = MybatiesUtil.getSession();
        try {
            WorkerMapper mapper = sqlSession.getMapper(WorkerMapper.class);
            if (mapper.insertWorker(worker) <= 0){
                System.out.println("插入失败");
            }
        } catch (Exception e){
            e.printStackTrace();
        }finally {
            sqlSession.commit();
            sqlSession.close();
        }
    }
}

