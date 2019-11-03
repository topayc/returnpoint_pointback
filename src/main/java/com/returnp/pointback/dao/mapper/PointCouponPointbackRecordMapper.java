package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.PointCouponPointbackRecord;

public interface PointCouponPointbackRecordMapper {
    int deleteByPrimaryKey(Integer pointCouponPointbackRecord);

    int insert(PointCouponPointbackRecord record);

    int insertSelective(PointCouponPointbackRecord record);

    PointCouponPointbackRecord selectByPrimaryKey(Integer pointCouponPointbackRecord);

    int updateByPrimaryKeySelective(PointCouponPointbackRecord record);

    int updateByPrimaryKey(PointCouponPointbackRecord record);
}