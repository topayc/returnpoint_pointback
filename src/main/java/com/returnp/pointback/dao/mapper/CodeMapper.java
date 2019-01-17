package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.Code;

public interface CodeMapper {
    int deleteByPrimaryKey(Integer codeNo);

    int insert(Code record);

    int insertSelective(Code record);

    Code selectByPrimaryKey(Integer codeNo);

    int updateByPrimaryKeySelective(Code record);

    int updateByPrimaryKey(Code record);
}