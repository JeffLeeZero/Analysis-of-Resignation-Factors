package com.zpj.servlet;

import com.google.gson.Gson;
import com.zpj.bean.LoginBean;
import com.zpj.mapper.UserMapper;
import com.zpj.pojo.User;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.lang.reflect.Type;


import com.zpj.util.MybatiesUtil;

public class LoginServelet extends HttpServlet {

    private String account;
    private String password;
    private String pass;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {

        ccount = req.getParameter("account");
        password = req.getParameter("password");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String CONTENT_TYPE = "text/html; charset=GBK";
        resp.setContentType( CONTENT_TYPE);
        BufferedReader reader = req.getReader();
        String str = reader.readLine();
        PrintWriter out = resp.getWriter();

        //处理传入对象
        Gson gson = new Gson();
        String message = null;
        boolean isSuccess=false;
//        Type requestType = new TypeToken<RequestBean<User>>(){}.getType();
//        RequestBean<LoginBean> loginRequest = gson.fromJson(str,requestType);
//        LoginBean account = loginRequest.getReqParam();

        switch (judgeLogin(account,password)){
            case 0:
                message="不存在该用户";
                break;
            case 1:
                isSuccess=true;
                break;
            case 2:
                message="密码错误";
                break;
            default:break;
        }
        LoginBean loginBean = new LoginBean();
        loginBean.setMessage(message);
        loginBean.setSuccess(isSuccess);
        String jsonLogin = gson.toJson(loginBean);
        out.print(jsonLogin);
        out.close();
        out.flush();

    }

    private int judgeLogin(String a, String p)throws ServletException, IOException{
        SqlSession sqlSession = MybatiesUtil.getSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        String pass = null;
        try {
            pass = mapper.queryPassword(a);
        } catch (Exception e) {
            pass="";
            e.printStackTrace();
        }
        sqlSession.commit();
        sqlSession.close();
        if (pass.equals("")){
            return 0;
        }else if (pass.equals(p)){
            return 1;
        }else{
            System.out.println(p);
            return 2;
        }

    }
}
