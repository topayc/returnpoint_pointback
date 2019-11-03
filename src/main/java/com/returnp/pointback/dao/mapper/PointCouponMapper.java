package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.PointCoupon;

public interface PointCouponMapper {
    int deleteByPrimaryKey(Integer pointCouponNo);

    int insert(PointCoupon record);

    int insertSelective(PointCoupon record);

    PointCoupon selectByPrimaryKey(Integer pointCouponNo);

    int updateByPrimaryKeySelective(PointCoupon record);

    int updateByPrimaryKey(PointCoupon record);
}