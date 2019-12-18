package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.PointCodeTransaction;

public interface PointCodeTransactionMapper {
    int deleteByPrimaryKey(Integer pointCodeTransactionNo);

    int insert(PointCodeTransaction record);

    int insertSelective(PointCodeTransaction record);

    PointCodeTransaction selectByPrimaryKey(Integer pointCodeTransactionNo);

    int updateByPrimaryKeySelective(PointCodeTransaction record);

    int updateByPrimaryKey(PointCodeTransaction record);
}