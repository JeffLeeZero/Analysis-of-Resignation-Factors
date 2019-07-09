package com.zpj.servlet;

import java.io.BufferedReader;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zpj.bean.RequestBean;
import com.zpj.bean.ResponseBean;
import com.zpj.mapper.WorkerMapper;
import com.zpj.pojo.User;
import com.zpj.pojo.Worker;
import com.zpj.util.MybatiesUtil;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;

@WebServlet(name = "HistoryServlet")
public class HistoryServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

        Type reqType = new TypeToken<RequestBean>(){}.getType();
        RequestBean reqBean = gson.fromJson(content,reqType);
        account = reqBean.getReqId();
        SqlSession sqlSession = MybatiesUtil.getSession();
        WorkerMapper mapper = sqlSession.getMapper(WorkerMapper.class);
        List<Worker> workerList;
        Type respType = new TypeToken<ResponseBean<List<Worker>>>(){}.getType();
        try{
            workerList = mapper.queryWorker(account);
            ResponseBean<List<Worker>> respBean = new ResponseBean<>();
            respBean.setResData(workerList);
            respBean.setReqId(account);
            String res = gson.toJson(respBean,respType);
            out.print(res);
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            out.close();
            sqlSession.close();
        }
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doPost(request,response);

    }
}
