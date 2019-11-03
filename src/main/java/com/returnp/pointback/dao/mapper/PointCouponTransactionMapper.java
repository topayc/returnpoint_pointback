package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.PointCouponTransaction;

public interface PointCouponTransactionMapper {
    int deleteByPrimaryKey(Integer pointCouponTransactionNo);

    int insert(PointCouponTransaction record);

    int insertSelective(PointCouponTransaction record);

    PointCouponTransaction selectByPrimaryKey(Integer pointCouponTransactionNo);

    int updateByPrimaryKeySelective(PointCouponTransaction record);

    int updateByPrimaryKey(PointCouponTransaction record);
}