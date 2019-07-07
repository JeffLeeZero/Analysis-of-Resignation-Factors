package com.zpj.servlet;

import analysis.Analyser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zpj.bean.RequestBean;
import com.zpj.pojo.User;
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

@WebServlet(name = "InsertWorkerServlet")
public class InsertWorkerServlet extends HttpServlet {
    private String account;



    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        response.getWriter().append("Served at: ").append(request.getContextPath());
    }

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        request.setCharacterEncoding("UTF-8");
        account = request.getParameter("Account");
        String satisfactionLevel = request.getParameter("SatisfactionLevel");
        String lastEvaluation = request.getParameter("LastEvaluation");
        String numberProject = request.getParameter("NumberProject");
        String averageMonthly = request.getParameter("AverageMonthly");
        String timeSpendCompany = request.getParameter("TimeSpendCompany");
        String workAccident = request.getParameter("WorkAccident");
        String promotion = request.getParameter("Promotion");
        String department = request.getParameter("Department");
        String salary = request.getParameter("Salary");
        String number = request.getParameter("Number");

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
        data.add(number);
        try{
            System.out.println(data);
            Analyser analyser = new Analyser(account);
            //得到离职率
            ArrayList<String> result = analyser.getProbability(data);
            System.out.println(result);
            String left = result.get(0);
            String accuracyRate = result.get(1);
            String notAccuracyRate = String.valueOf(1 - Double.valueOf(accuracyRate));
            String reason = "该员工";

            System.out.println("编号" + number + "员工信息分析成功");
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

    }


}

