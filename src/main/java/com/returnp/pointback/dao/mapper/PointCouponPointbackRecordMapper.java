package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.PointCouponPointbackRecord;

public interface PointCouponPointbackRecordMapper {
    int deleteByPrimaryKey(Integer pointCouponPointbackRecordNo);

    int insert(PointCouponPointbackRecord record);

    int insertSelective(PointCouponPointbackRecord record);

    PointCouponPointbackRecord selectByPrimaryKey(Integer pointCouponPointbackRecordNo);

    int updateByPrimaryKeySelective(PointCouponPointbackRecord record);

    int updateByPrimaryKey(PointCouponPointbackRecord record);
}