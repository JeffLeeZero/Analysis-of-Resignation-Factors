package com.zpj.servlet;

import com.zpj.mapper.UserMapper;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

import com.zpj.util.MybatiesUtil;

public class LoginServelet extends HttpServlet {

    private String account;
    private String password;
    private String pass;

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        super.doGet(req, resp);
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
        switch (judgeLogin(account,password,req,resp)){
            case 0:
                out.print("<script>alert('用户名不存在');window.location.href = http://localhost:8080/login.jsp'</script>");
                break;
            case 1:
                out.print("<script>window.location.href = http://localhost:8080/index.jsp'</script>");
                break;
            case 2:
                out.print("<script>alert('密码错误');window.location.href = http://localhost:8080/login.jsp'</script>");
                break;
            default:break;
        }

    }

    private int judgeLogin(String a, String p, HttpServletRequest req, HttpServletResponse resp)throws ServletException, IOException{
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
        if (pass == ""){
            return 0;
        }else if (pass==p){
            return 1;
        }else{
            return 3;
        }

    }
}
