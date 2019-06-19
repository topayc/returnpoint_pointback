package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.PaymentRouter;

public interface PaymentRouterMapper {
    int deleteByPrimaryKey(Integer paymentRouterNo);

    int insert(PaymentRouter record);

    int insertSelective(PaymentRouter record);

    PaymentRouter selectByPrimaryKey(Integer paymentRouterNo);

    int updateByPrimaryKeySelective(PaymentRouter record);

    int updateByPrimaryKey(PaymentRouter record);
}