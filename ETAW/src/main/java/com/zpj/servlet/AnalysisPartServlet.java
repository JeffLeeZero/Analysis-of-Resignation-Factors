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
import java.util.Map;

/**
 * 返回某个特征值各个取值的离职率
 * @author 李沛昊
 */
@WebServlet(name = "AnalysisPartServlet")
public class AnalysisPartServlet extends HttpServlet {
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

        String account = "",value = "";
        Gson gson = new Gson();
        Type reqType = new TypeToken<RequestBean<AttrBean>>(){}.getType();
        RequestBean<AttrBean> reqBean = gson.fromJson(content,reqType);
        account = reqBean.getReqId();
        AttrBean attrBean = reqBean.getReqParam();
        value = attrBean.getName();
        ResignationAnalyser analyser = new Analyser(account);
        Map<String,Double> map = analyser.getAttrRatio(value);
        if(map.get("isSeperated")>0){
            attrBean.setSeperated(true);
        }else {
            attrBean.setSeperated(false);
        }
        map.remove("isSeperated");
        for (Map.Entry<String,Double> entry:
             map.entrySet()) {
            attrBean.addList(entry.getKey(),entry.getValue());
        }
        if(!attrBean.isSeperated()){
            attrBean.sortList();
        }

        Type respType = new TypeToken<ResponseBean<AttrBean>>(){}.getType();
        ResponseBean<AttrBean> respBean = new ResponseBean<>();
        respBean.setResData(attrBean);
        try{
            out.print(gson.toJson(respBean,respType));
        }catch (Exception e){
            e.printStackTrace();
        }finally {
            out.flush();
            out.close();
        }
    }
}
