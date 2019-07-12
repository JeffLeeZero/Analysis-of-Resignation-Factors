package com.zpj.mapper;

import com.zpj.pojo.User;
import org.apache.ibatis.annotations.Param;
/**
 * 用户mybatis接口
 * @author 毕修平
 */
public interface UserMapper {
    int insertUser(User user);    //插入用户

    int queryAccountExist(@Param("account") String account);   //查询用户数量

    String queryPassByAccount(@Param("account") String account);    //通过账号查询密码

    int insertAttachment(User user);       //插入附件

    int updatePassword(@Param("account") String account,@Param("password") String password);    //更新密码

}
