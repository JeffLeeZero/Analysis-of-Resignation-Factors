package com.zpj.servlet;

import com.google.gson.Gson;
import com.zpj.bean.LoginBean;
import com.zpj.bean.VerificationCode;
import com.zpj.github.qcloudsms.SmsMultiSender;
import com.zpj.github.qcloudsms.SmsMultiSenderResult;
import com.zpj.github.qcloudsms.httpclient.HTTPException;
import com.zpj.mapper.UserMapper;
import com.zpj.servlet.LoginServlet;
import com.zpj.util.MybatiesUtil;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Random;


public class SmsLoginServlet extends HttpServlet {
    private String type;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        System.out.println("get");
        resp.setHeader("Access-Control-Allow-Origin", "*");
        resp.setCharacterEncoding("utf-8");
        PrintWriter out = resp.getWriter();
        Gson gson = new Gson();

        //判断是登陆还是注册的验证码


        //生成验证码
        Random random = new Random();
        String verificationCode = String.valueOf(random.nextInt(9000)+1000);


        // 短信应用SDK AppID
        int appid = 1400224946; // 1400开头

        // 短信应用SDK AppKey
        String appkey = "3432e884e7be50766f4e5422145125e6";

        // 需要发送短信的手机号码
        String[] phoneNumbers = {req.getParameter("phone")};

        // 短信模板ID，需要在短信应用中申请
        // NOTE: 这里的模板ID`7839`只是一个示例，
        // 真实的模板ID需要在短信控制台中申请
        int templateId = 363240;

        // 签名
        // NOTE: 这里的签名"腾讯云"只是一个示例，
        // 真实的签名需要在短信控制台中申请，另外
        // 签名参数使用的是`签名内容`，而不是`签名ID`
        String smsSign = "诸皮匠";

        boolean isSuccess = false;
        String message="";
        String jsonPhone = null;


        if (type.equals("isLogin") && queryPhone(phoneNumbers[0])==0){
            message = "不存在该用户或手机未注册";
            LoginBean loginBean = new LoginBean(message,isSuccess);
            jsonPhone = gson.toJson(loginBean);
            System.out.println(jsonPhone);
            out.print(jsonPhone);
        }else{
            // 指定模板ID单发短信
            try {
                String[] params = {verificationCode,"1"};
                SmsMultiSender msender = new SmsMultiSender(appid, appkey);
                SmsMultiSenderResult result =  msender.sendWithParam("86", phoneNumbers,
                        templateId, params, smsSign, "", "");  // 签名参数未提供或者为空时，会使用默认签名发送短信
                System.out.print(result);
                VerificationCode resData = new VerificationCode();
                resData.setVerificationCode(verificationCode);


                if(result.result == 0){
                    isSuccess = true;
                }
                else{
                    isSuccess = false;
                }
                message = verificationCode;
                LoginBean loginBean = new LoginBean(message,isSuccess);
                jsonPhone = gson.toJson(loginBean);
                System.out.println(jsonPhone);
                out.print(jsonPhone);


            } catch (HTTPException e) {
                e.printStackTrace();
            }
            out.flush();
            out.close();
        }


    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        type=req.getParameter("alert_type");
        System.out.println(type);
        doGet(req, resp);
    }
    private int queryPhone(String a)throws ServletException, IOException{
        SqlSession sqlSession = MybatiesUtil.getSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        String pass = null;
        try {
            pass = mapper.queryPassByAccount(a);
        } catch (Exception e) {
            pass="";
            e.printStackTrace();
        }
        sqlSession.commit();
        sqlSession.close();
        if (pass==null){
            return 0;
        } else{
            return 1;
        }

    }

}
