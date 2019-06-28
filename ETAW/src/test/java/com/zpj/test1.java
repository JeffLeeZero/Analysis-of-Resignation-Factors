package com.zpj;

import com.zpj.mapper.UserMapper;
import com.zpj.pojo.User;
import com.zpj.util.MybatiesUtil;
import org.apache.ibatis.session.SqlSession;
import org.junit.Test;

public class test1 {
    @Test
    public void m1(){
        SqlSession sqlSession = MybatiesUtil.getSession();
        UserMapper mapper = sqlSession.getMapper(UserMapper.class);
        String pass = null;
        User user = new User();
        user.setAccount("17371445076");
        user.setPassword("123456");
        try {
//           pass=mapper.queryPassword("15198524595");
           mapper.insertUser(user);
        } catch (Exception e) {
            e.printStackTrace();
        }
        sqlSession.commit();
        sqlSession.close();
        System.out.println(pass);
    }
}
