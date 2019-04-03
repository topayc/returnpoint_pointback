package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.GiftCardPayment;

public interface GiftCardPaymentMapper {
    int deleteByPrimaryKey(Integer giftCardPaymentNo);

    int insert(GiftCardPayment record);

    int insertSelective(GiftCardPayment record);

    GiftCardPayment selectByPrimaryKey(Integer giftCardPaymentNo);

    int updateByPrimaryKeySelective(GiftCardPayment record);

    int updateByPrimaryKey(GiftCardPayment record);
}