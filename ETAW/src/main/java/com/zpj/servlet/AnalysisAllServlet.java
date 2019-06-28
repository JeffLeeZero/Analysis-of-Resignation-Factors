package com.zpj.servlet;

import analysis.Analyser;
import analysis.ResignationAnalyser;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
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
        Type resType = new TypeToken<List<Attr>>(){}.getType();

        ResignationAnalyser analyser = new Analyser(account);
        Map<String,Double> map = analyser.getAttrRatio();
        List<Attr> list = new ArrayList<>();
        Attr a;
        for (Map.Entry<String, Double> entry
                : map.entrySet()){
            a = new Attr(entry.getKey());
            a.setD(entry.getValue());
            list.add(a);
        }
        String res = gson.toJson(list,resType);
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
