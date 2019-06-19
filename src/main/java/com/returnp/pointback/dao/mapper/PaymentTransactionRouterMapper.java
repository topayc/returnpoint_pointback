package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.PaymentTransactionRouter;

public interface PaymentTransactionRouterMapper {
    int deleteByPrimaryKey(Integer paymentTransactionRouterNo);

    int insert(PaymentTransactionRouter record);

    int insertSelective(PaymentTransactionRouter record);

    PaymentTransactionRouter selectByPrimaryKey(Integer paymentTransactionRouterNo);

    int updateByPrimaryKeySelective(PaymentTransactionRouter record);

    int updateByPrimaryKey(PaymentTransactionRouter record);
}