package com.zpj.mapper;

import com.zpj.pojo.Answer;

import java.util.List;
/**
 * 文章mybatis接口
 * @author 毕修平
 */
public interface AnswerMapper {
    List<Answer> queryAnswer();
}
