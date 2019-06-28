package com.zpj.servlet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.zpj.pojo.Worker;
import com.zpj.mapper.WorkerMapper;
import com.zpj.util.MybatiesUtil;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;
import java.io.PrintWriter;

@WebServlet(name = "AddServlet")
public class AddServlet extends HttpServlet {
    private Worker worker = new Worker();

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        boolean flag = false;
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String CONTENT_TYPE = "text/html; charset=GBK";
        worker.setSatisfaction_level(request.getParameter("satisfaction_level"));
        worker.setLast_evaluation(request.getParameter("last_evaluation"));
        worker.setNumber_project(request.getParameter("number_project"));
        worker.setAverage_monthly_hours(request.getParameter("average_monthly_hour"));
        worker.setTime_spend_company(request.getParameter("time_spend_company"));
        worker.setWork_accident(request.getParameter("work_accident"));
        worker.setLeft(request.getParameter("left"));
        worker.setPromotion(request.getParameter("promotion"));
        worker.setSales(request.getParameter("sales"));
        worker.setSalary(request.getParameter("salary"));
        flag = addWorker(worker,request,response);

        if (flag){
            response.setContentType( CONTENT_TYPE);
            PrintWriter out = response.getWriter();
            System.out.println("读入数据库成功");
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);
    }

    public boolean addWorker(Worker worker, HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException{
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String CONTENT_TYPE = "text/html; charset=GBK";
        response.setContentType( CONTENT_TYPE);
        try {
            SqlSession sqlSession = MybatiesUtil.getSession();
            WorkerMapper mapper = sqlSession.getMapper(WorkerMapper.class);
            Worker worker1 = new Worker();
            worker1.setSatisfaction_level(worker.getSatisfaction_level());
            worker1.setLast_evaluation(worker.getLast_evaluation());
            worker1.setNumber_project(worker.getNumber_project());
            worker1.setAverage_monthly_hours(worker.getAverage_monthly_hours());
            worker1.setTime_spend_company(worker.getTime_spend_company());
            worker1.setWork_accident(worker.getWork_accident());
            worker1.setLeft(worker.getLeft());
            worker1.setPromotion(worker.getPromotion());
            worker1.setSales(worker.getSales());
            worker1.setSalary(worker.getSalary());
            try {
                mapper.insert(worker1);
            } catch (PersistenceException e) {
                e.printStackTrace();
            }
            sqlSession.commit();
            sqlSession.close();
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
        return true;
    }
}
