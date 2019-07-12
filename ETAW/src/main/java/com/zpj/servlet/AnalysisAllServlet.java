package com.zpj.servlet;

import analysis.Analyser;
import analysis.ResignationAnalyser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.zpj.bean.AttrBean;
import com.zpj.bean.RequestBean;
import com.zpj.bean.ResponseBean;
import tree.Attr;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.Writer;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 用于返回各属性对于整体离职情况的重要性（影响力）
 * @author 李沛昊
 */
@WebServlet(name = "AnalysisAllServlet")
public class AnalysisAllServlet extends HttpServlet {
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

        Type reqType = new TypeToken<RequestBean>(){}.getType();
        RequestBean reqBean = gson.fromJson(content,reqType);
        account = reqBean.getReqId();

        Type resType = new TypeToken<ResponseBean<AttrBean>>(){}.getType();

        ResignationAnalyser analyser = new Analyser(account);
        Map<String,Double> map = analyser.getAttrRatio();
        List<AttrBean> list = new ArrayList<>();
        map.remove("left");
        double ratio = 0.0;
        for (Map.Entry<String, Double> entry
                : map.entrySet()){
            ratio = (double)((int)(double)(entry.getValue() * 100000))/100000;
            list.add(new AttrBean(entry.getKey(),ratio));
        }
        ResponseBean<List<AttrBean>> respBean = new ResponseBean<>();
        respBean.setReqId(account);
        respBean.setResData(list);
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
