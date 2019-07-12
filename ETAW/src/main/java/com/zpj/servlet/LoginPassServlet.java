package com.zpj.servlet;

import com.google.gson.Gson;
import com.zpj.bean.LoginBean;
import com.zpj.bean.PassageBean;
import com.zpj.mapper.AnswerMapper;
import com.zpj.pojo.Answer;
import com.zpj.util.MybatiesUtil;
import org.apache.ibatis.session.SqlSession;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Clob;
import java.sql.SQLException;
import java.util.*;

/**
 * 文章呈现后台后台
 * @author 毕修平
 */
public class LoginPassServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String CONTENT_TYPE = "text/html; charset=GBK";
        resp.setContentType( CONTENT_TYPE);

        PrintWriter out = resp.getWriter();

        //处理传入对象
        Gson gson = new Gson();

        List<Answer> answers = getAnswer();
        Clob clob = (Clob)answers.get(0).getContent();
        String contentInfo = "";
        if(clob != null){
            try {
                contentInfo = clob.getSubString((long)1,(int)clob.length());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        PassageBean passageBean = new PassageBean(answers.get(0).getTitle(),answers.get(0).getAuthor(),contentInfo);
        passageBean.setCount(answers.size());
        String jsonPassage = null;
        try {
            jsonPassage = gson.toJson(passageBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        System.out.println(jsonPassage);
        System.out.println(passageBean.getCount());
        out.print(jsonPassage);
        out.flush();
        out.close();
    }

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        String action = req.getParameter("btnAction");
        int pageNum = Integer.parseInt(req.getParameter("pageNum"));

        req.setCharacterEncoding("UTF-8");
        resp.setCharacterEncoding("UTF-8");
        String CONTENT_TYPE = "text/html; charset=GBK";
        resp.setContentType( CONTENT_TYPE);

        PrintWriter out = resp.getWriter();

        //处理传入对象
        Gson gson = new Gson();

        List<Answer> answers = getAnswer();
        if (pageNum>answers.size()){
            pageNum=answers.size();
        }else if(pageNum<0){
            pageNum=0;
        }
        Clob clob = (Clob)answers.get(pageNum).getContent();
        String contentInfo = "";
        if(clob != null){
            try {
                contentInfo = clob.getSubString((long)1,(int)clob.length());
            } catch (SQLException e) {
                e.printStackTrace();
            }
        }
        PassageBean passageBean = new PassageBean(answers.get(pageNum).getTitle(),answers.get(pageNum).getAuthor(),contentInfo);
        String jsonPassage = null;
        try {
            jsonPassage = gson.toJson(passageBean);
        } catch (Exception e) {
            e.printStackTrace();
        }
        out.print(jsonPassage);
        out.flush();
        out.close();
    }

    //从数据库中获取文章
    private List<Answer> getAnswer(){
        SqlSession sqlSession = MybatiesUtil.getSession();
        AnswerMapper mapper = sqlSession.getMapper(AnswerMapper.class);
        List<Answer> answers = new List<Answer>() {
            @Override
            public int size() {
                return 0;
            }

            @Override
            public boolean isEmpty() {
                return false;
            }

            @Override
            public boolean contains(Object o) {
                return false;
            }

            @Override
            public Iterator<Answer> iterator() {
                return null;
            }

            @Override
            public Object[] toArray() {
                return new Object[0];
            }

            @Override
            public <T> T[] toArray(T[] a) {
                return null;
            }

            @Override
            public boolean add(Answer answer) {
                return false;
            }

            @Override
            public boolean remove(Object o) {
                return false;
            }

            @Override
            public boolean containsAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean addAll(Collection<? extends Answer> c) {
                return false;
            }

            @Override
            public boolean addAll(int index, Collection<? extends Answer> c) {
                return false;
            }

            @Override
            public boolean removeAll(Collection<?> c) {
                return false;
            }

            @Override
            public boolean retainAll(Collection<?> c) {
                return false;
            }

            @Override
            public void clear() {

            }

            @Override
            public Answer get(int index) {
                return null;
            }

            @Override
            public Answer set(int index, Answer element) {
                return null;
            }

            @Override
            public void add(int index, Answer element) {

            }

            @Override
            public Answer remove(int index) {
                return null;
            }

            @Override
            public int indexOf(Object o) {
                return 0;
            }

            @Override
            public int lastIndexOf(Object o) {
                return 0;
            }

            @Override
            public ListIterator<Answer> listIterator() {
                return null;
            }

            @Override
            public ListIterator<Answer> listIterator(int index) {
                return null;
            }

            @Override
            public List<Answer> subList(int fromIndex, int toIndex) {
                return null;
            }
        };
        answers = mapper.queryAnswer();
        sqlSession.commit();
        sqlSession.close();
        return answers;
    }
}
