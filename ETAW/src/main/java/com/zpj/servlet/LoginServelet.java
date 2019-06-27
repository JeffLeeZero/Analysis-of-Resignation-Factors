package com.zpj.servlet;

import com.zpj.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import com.zpj.util.MybatiesUtil;

public class LoginServelet extends HttpServlet {

    private String account;
    private String password;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        account = req.getParameter("account");
        password = req.getParameter("password");


    }

    private boolean judgeLogin(String a, String p, HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
        SqlSession sqlSession = MybatiesUtil.getSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        return true;
    }
}
