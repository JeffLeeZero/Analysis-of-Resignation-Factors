package com.zpj.servlet;

import com.zpj.mapper.UserMapper;
import com.zpj.pojo.User;
import com.zpj.util.MybatiesUtil;
import org.apache.ibatis.binding.BindingException;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Arrays;

@WebServlet(name = "RegisterServlet")
public class RegisterServlet extends HttpServlet {

    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        response.setContentType("text/html;charset=UTF-8");
        PrintWriter out = response.getWriter();

        SqlSession sqlSession = MybatiesUtil.getSession();
        User user = new User();
        user.setAccount(request.getParameter("account"));
        user.setPassword(request.getParameter("password"));

        UserMapper mapper = sqlSession.getMapper(UserMapper.class);

        try {
            if(mapper.queryAccountExist(user.getAccount()) != null){
                out.print("用户名存在");
            } else {
                mapper.insertUser(user);
                sqlSession.commit();
                out.print("<script>alert('添加成功');window.location.href = 'http://localhost:8080/etaw/Register.jsp'</script>");
            }
        } catch (BindingException e) {
            sqlSession.rollback();
            out.print("<script>alert('错误!')</script>");
        } catch (Exception e) {
            sqlSession.rollback();
            e.printStackTrace();
        } finally {
            sqlSession.close();
        }

    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        System.out.println("get");
        request.setCharacterEncoding("UTF-8");
        response.setCharacterEncoding("UTF-8");
        String CONTENT_TYPE = "text/html; charset=GBK";
        response.setContentType(CONTENT_TYPE);
    }
}
