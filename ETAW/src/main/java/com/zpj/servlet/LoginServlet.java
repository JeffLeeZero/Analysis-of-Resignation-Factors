package com.zpj.servlet;

import com.google.gson.Gson;
import com.zpj.bean.LoginBean;
import com.zpj.mapper.UserMapper;
import com.zpj.pojo.User;
import org.apache.ibatis.exceptions.PersistenceException;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.SessionCookieConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.PrintWriter;
import java.io.BufferedReader;
import java.lang.reflect.Type;


import com.zpj.util.MybatiesUtil;

/**
 * 登录后台
 * @author 毕修平
 */
public class LoginServlet extends HttpServlet {

    public  String account;
    private String password;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        account = req.getParameter("account");
        password = req.getParameter("password");
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String CONTENT_TYPE = "text/html; charset=GBK";
        resp.setContentType( CONTENT_TYPE);

        PrintWriter out = resp.getWriter();

        //处理传入对象
        Gson gson = new Gson();
        String message = null;
        boolean isSuccess=false;

        HttpSession session = req.getSession();
        switch (judgeLogin(account,password)){
            case 0:
                message="不存在该用户";
                break;
            case 1:
                isSuccess=true;
                session.setAttribute("account",account);
                session.setMaxInactiveInterval(60*15);
                System.out.println(session.getAttribute("account"));
                break;
            case 2:
                message="密码错误";
                break;
            default:break;
        }
        String jsonLogin = null;
        try {
            LoginBean loginBean = new LoginBean(message,isSuccess);
            jsonLogin = gson.toJson(loginBean);                          //将bean转成json传到前端
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(jsonLogin);
        out.print(jsonLogin);
        out.flush();
        out.close();
    }

    //登录检测 0：不存在该用户 1：成功 2：密码错误
    private int judgeLogin(String a, String p)throws ServletException, IOException{
        SqlSession sqlSession = MybatiesUtil.getSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        String pass = null;
        try {
            pass = mapper.queryPassByAccount(a);
        } catch (PersistenceException e) {
            pass="";
            e.printStackTrace();
        }
        sqlSession.commit();
        sqlSession.close();
        if (pass==null){
            return 0;
        }else if (pass.equals(p)){
            return 1;
        }else{
            return 2;
        }

    }
}
