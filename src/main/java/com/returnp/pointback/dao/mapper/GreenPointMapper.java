package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.GreenPoint;

public interface GreenPointMapper {
    int deleteByPrimaryKey(Integer greenPointNo);

    int insert(GreenPoint record);

    int insertSelective(GreenPoint record);

    GreenPoint selectByPrimaryKey(Integer greenPointNo);

    int updateByPrimaryKeySelective(GreenPoint record);

    int updateByPrimaryKey(GreenPoint record);
}