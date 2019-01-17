package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.PointDepositTransaction;

public interface PointDepositTransactionMapper {
    int deleteByPrimaryKey(Integer pointDepositTransactionNo);

    int insert(PointDepositTransaction record);

    int insertSelective(PointDepositTransaction record);

    PointDepositTransaction selectByPrimaryKey(Integer pointDepositTransactionNo);

    int updateByPrimaryKeySelective(PointDepositTransaction record);

    int updateByPrimaryKey(PointDepositTransaction record);
}