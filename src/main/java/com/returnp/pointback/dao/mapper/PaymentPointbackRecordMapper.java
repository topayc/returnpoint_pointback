package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.PaymentPointbackRecord;

public interface PaymentPointbackRecordMapper {
    int deleteByPrimaryKey(Integer paymentPointbackRecordNo);

    int insert(PaymentPointbackRecord record);

    int insertSelective(PaymentPointbackRecord record);

    PaymentPointbackRecord selectByPrimaryKey(Integer paymentPointbackRecordNo);

    int updateByPrimaryKeySelective(PaymentPointbackRecord record);

    int updateByPrimaryKey(PaymentPointbackRecord record);
}