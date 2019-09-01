package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.GpointPayment;

public interface GpointPaymentMapper {
    int deleteByPrimaryKey(Integer gpointPaymentNo);

    int insert(GpointPayment record);

    int insertSelective(GpointPayment record);

    GpointPayment selectByPrimaryKey(Integer gpointPaymentNo);

    int updateByPrimaryKeySelective(GpointPayment record);

    int updateByPrimaryKey(GpointPayment record);
}