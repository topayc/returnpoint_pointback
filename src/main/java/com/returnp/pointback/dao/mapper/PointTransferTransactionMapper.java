package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.PointTransferTransaction;

public interface PointTransferTransactionMapper {
    int deleteByPrimaryKey(Integer pointTransferTransactionNo);

    int insert(PointTransferTransaction record);

    int insertSelective(PointTransferTransaction record);

    PointTransferTransaction selectByPrimaryKey(Integer pointTransferTransactionNo);

    int updateByPrimaryKeySelective(PointTransferTransaction record);

    int updateByPrimaryKey(PointTransferTransaction record);
}