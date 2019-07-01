package com.zpj.servlet;

import com.google.gson.Gson;
import com.zpj.bean.LoginBean;
import com.zpj.mapper.UserMapper;
import com.zpj.pojo.User;
import com.zpj.util.MybatiesUtil;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

public class RegisterServlet extends HttpServlet {
    private String account;
    private String password;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        account = req.getParameter("phone");
        password = req.getParameter("password");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        Gson gson = new Gson();
        String jsonRegister = null;

        String message ="";
        boolean isSuccess = false;
        int i = register(account,password);
        if (i==0){
            message = "该用户已注册，请换个手机号";
            LoginBean reigsterBean = new LoginBean(message,isSuccess);
            jsonRegister = gson.toJson(reigsterBean);      //gson转换成json
        }else {
            isSuccess=true;
            LoginBean reigsterBean = new LoginBean(message,isSuccess);
            jsonRegister = gson.toJson(reigsterBean);
        }
        System.out.println(jsonRegister);
        //返回ajax
        out.print(jsonRegister);
        out.flush();
        out.close();
    }

    private int register(String a,String p){
        SqlSession sqlSession = MybatiesUtil.getSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        int count ;
        try {
            count = mapper.queryAccountExist(a);
        } catch (PersistenceException e) {
            count=0;
            e.printStackTrace();
        }
        if (count!=0){
            return 0;
        }else {
            User user = new User();
            user.setAccount(a);
            user.setPassword(p);
            mapper.insertUser(user);
            return 1;
        }
    }
}
