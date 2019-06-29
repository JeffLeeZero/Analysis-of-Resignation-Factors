package com.zpj.servlet;

import com.google.gson.Gson;
import com.zpj.github.qcloudsms.SmsMultiSender;
import com.zpj.github.qcloudsms.SmsMultiSenderResult;
import com.zpj.github.qcloudsms.httpclient.HTTPException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;

public class SmsLoginServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setCharacterEncoding("utf-8");
        PrintWriter out = resp.getWriter();
        Gson gson = new Gson();

        //生成验证码
        Random random = new Random();
        String verificationCode = String.valueOf(random.nextInt(9000)+1000);


        // 短信应用SDK AppID
        int appid = 1400167297; // 1400开头

        // 短信应用SDK AppKey
        String appkey = "49be6b5a0075d6fcf2154b36ccfb45f1";

        // 需要发送短信的手机号码
        String[] phoneNumbers = {req.getParameter("phone")};

        // 短信模板ID，需要在短信应用中申请
        // NOTE: 这里的模板ID`7839`只是一个示例，
        // 真实的模板ID需要在短信控制台中申请
        int templateId = 240296;

        // 签名
        // NOTE: 这里的签名"腾讯云"只是一个示例，
        // 真实的签名需要在短信控制台中申请，另外
        // 签名参数使用的是`签名内容`，而不是`签名ID`
        String smsSign = "观者心剧中人";

        // 指定模板ID单发短信
        try {
            String[] params = {verificationCode,"1"};
            SmsMultiSender msender = new SmsMultiSender(appid, appkey);
            SmsMultiSenderResult result =  msender.sendWithParam("86", phoneNumbers,
                    templateId, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
            System.out.print(result);

        } catch (HTTPException e) {
            e.printStackTrace();
        }

    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doPost(req, resp);
    }
}
