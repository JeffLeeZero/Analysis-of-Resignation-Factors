package com.zpj.servlet;

import analysis.Analyser;
import analysis.ResignationAnalyser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zpj.bean.AttrBean;
import com.zpj.bean.RequestBean;
import com.zpj.bean.ResponseBean;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@WebServlet(name = "LeftRatioServlet")
public class LeftRatioServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        doGet(request,response);
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

        Type resType = new TypeToken<ResponseBean<AttrBean>>(){}.getType();

        ResignationAnalyser analyser = new Analyser(account);
        Map<String,Double> map = analyser.getAttrRatio("left");
        AttrBean attrBean = new AttrBean("left",0);
        for (Map.Entry<String, Double> entry
                : map.entrySet()){
            attrBean.addList(entry.getKey(),entry.getValue());
        }
        ResponseBean<AttrBean> respBean = new ResponseBean<>();
        respBean.setResData(attrBean);
        String res = gson.toJson(respBean,resType);
        try{
            out.print(res);
            out.flush();
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            out.close();
        }


    }
}
