
package com.zpj.servlet;

import com.google.gson.Gson;
import com.zpj.bean.LoginBean;
import com.zpj.mapper.UserMapper;
import com.zpj.util.MybatiesUtil;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * 找回密码后台
 * @author 毕修平
 */
public class FindPassServlet extends HttpServlet {
    private String account;
    private String password;
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        doPost(req,resp);
}

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        PrintWriter out = resp.getWriter();
        Gson gson = new Gson();
        String jsonFind = null;

        String message ="";
        boolean isSuccess = false;
        account=req.getParameter("phone");
        password=req.getParameter("password");
        int i=updatePass(account,password);           //判断密码更新是否成功
        if (i==1){
            isSuccess=true;
        }else{
            message = "密码更改失败，请检查密码格式是否正确";
        }
        LoginBean findBean = new LoginBean(message,isSuccess);
        jsonFind = gson.toJson(findBean);
        System.out.println(jsonFind);
        out.print(jsonFind);
        out.flush();
        out.close();
    }

    //在数据库更新密码
    private int updatePass(String a, String p){
        SqlSession sqlSession = MybatiesUtil.getSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        try {
            mapper.updatePassword(a,p);
            sqlSession.commit();
            sqlSession.close();
            return 1;
        } catch (Exception e) {
            e.printStackTrace();
            sqlSession.commit();
            sqlSession.close();
            return 0;
        }
    }
}
