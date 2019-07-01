package com.zpj.mapper;

import com.zpj.pojo.User;
import org.apache.ibatis.annotations.Param;

public interface UserMapper {
    int insertUser(User user);

    int queryAccountExist(@Param("account") String account);

    String queryPassByAccount(@Param("account") String account);

    int insertAttachment(User user);

}
