package com.zpj.bean;

import com.zpj.mapper.AnswerMapper;
import com.zpj.pojo.Answer;
import com.zpj.util.MybatiesUtil;
import org.apache.ibatis.session.SqlSession;

import java.io.UnsupportedEncodingException;
import java.sql.*;
import java.util.*;


public class AnswerBean {

    private ArrayList<Answer> answerList = null;

    public ArrayList<Answer> getAnswerList(){
        return answerList;
    }
    public AnswerBean() {
    }

    public void addAllAnswer(){
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
        answers=mapper.queryAnswer();
        sqlSession.commit();
        sqlSession.close();
        if(answerList == null){
            answerList = new ArrayList<Answer>(){} ;
        }
        for (int i=0;i<answers.size();i++){
            Clob clob = (Clob)answers.get(i).getContent();
            String contentInfo = "";
            if(clob != null){
                try {
                    contentInfo = clob.getSubString((long)1,(int)clob.length());
                } catch (SQLException e) {
                    e.printStackTrace();
                }
            }
            String contentInfo2 = contentInfo.replaceAll("[&nbsp;]+","");//将多个空格替换成掉
            String content = contentInfo2.replaceAll("[<br>]{0,}","").replaceAll("(?m)^\\s*$(\\n|\\r\\n)", "   ");//去掉空行


            content = String.format("%20s",content);

            Answer answer = new Answer();
            answer.setTitle(answers.get(i).getTitle());
            answer.setAuthor(answers.get(i).getAuthor());
            answer.setContent(content);
            answerList.add(answer);
        }
    }


    public static void main(String args[]){
        AnswerBean answerBean = new AnswerBean();
        answerBean.addAllAnswer();
        ArrayList<Answer> answers = answerBean.getAnswerList();
        System.out.println(answers);
        for(int i =0;i<answers.size();i++){
            System.out.println(answers.get(i).getTitle());
            System.out.println(answers.get(i).getAuthor());
            System.out.println(answers.get(i).getContent());
        }
    }
}


