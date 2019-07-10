package com.zpj.servlet;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zpj.bean.RequestBean;
import com.zpj.bean.ResponseBean;
import com.zpj.mapper.WorkerMapper;
import com.zpj.pojo.Worker;
import com.zpj.util.MybatiesUtil;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.List;

@WebServlet(name = "DeleteHistoryServlet")
public class DeleteHistoryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        //对返回消息进行设置
        /* 允许跨域的主机地址 */

        response.setHeader("Access-Control-Allow-Origin", "*");
        response.setCharacterEncoding("utf-8");

        //获取writer
        PrintWriter out = response.getWriter();

        //读取请求内容
        BufferedReader reader = request.getReader();
        String content = reader.readLine();

        //请求数据的bean类

        String account = "";
        Gson gson = new Gson();

        Type reqType = new TypeToken<RequestBean<List<Worker>>>(){}.getType();
        RequestBean<List<Worker>> reqBean = gson.fromJson(content,reqType);
        account = reqBean.getReqId();
        List<Worker> list = reqBean.getReqParam();
        SqlSession sqlSession = MybatiesUtil.getSession();
        WorkerMapper mapper = sqlSession.getMapper(WorkerMapper.class);
        try{
            for (Worker w:
                 list) {
                System.out.println(account);
                System.out.println(w.getWorker_number());
                mapper.deleteWorker(account,w.getWorker_number(),w.getSatisfaction_level(),w.getLast_evaluation(),w.getNumber_project(),w.getAverage_monthly_hours(),w.getTime_spend_company(),w.getWork_accident(),w.getLeft(),w.getPromotion(),w.getSales(),w.getSalary());
            }
            Type respType = new TypeToken<ResponseBean>(){}.getType();
            ResponseBean respBean = new ResponseBean<>();
            respBean.setReqId(account);
            String res = gson.toJson(respBean,respType);
            out.print(res);
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            out.close();
            sqlSession.commit();
            sqlSession.close();
        }
    }
}
