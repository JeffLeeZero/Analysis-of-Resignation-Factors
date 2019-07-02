package com.zpj.servlet;

import analysis.Analyser;
import sun.rmi.runtime.Log;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.ArrayList;

@WebServlet(name = "InsertWorkerServlet")
public class InsertWorkerServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
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
        data.add(numberProject);
        data.add(averageMonthly);
        data.add(timeSpendCompany);
        data.add(workAccident);
        data.add(promotion);
        data.add(department);
        data.add(salary);
        try{
            System.out.println(data);
            Analyser analyser = new Analyser(LoginServlet.account);
            //得到离职率
            ArrayList<String> result = analyser.getProbability(data, LoginServlet.account,department);
            double leftRatio = Double.valueOf(result.get(0));
            double accuracyRate = Double.valueOf(result.get(1));
            System.out.println("该员工的离职概率为:" + leftRatio);
            System.out.println("该预测的准确率为:" + accuracyRate);

            request.getSession().setAttribute("leftRatio", leftRatio);
            request.getRequestDispatcher("analyseWorker.jsp").forward(request, response);

//            String message = "提交成功";
//            request.getSession().setAttribute("message",message);
            response.sendRedirect("/analyseWorker.jsp");

        } catch (Exception e){
            e.printStackTrace();
//            String message = "提交失败";
//            request.getSession().setAttribute("message",message);
            response.sendRedirect("/insertWorker.jsp");
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    }
}
