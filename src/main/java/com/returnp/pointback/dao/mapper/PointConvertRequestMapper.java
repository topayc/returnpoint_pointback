package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.PointConvertRequest;

public interface PointConvertRequestMapper {
    int deleteByPrimaryKey(Integer pointConvertRequestNo);

    int insert(PointConvertRequest record);

    int insertSelective(PointConvertRequest record);

    PointConvertRequest selectByPrimaryKey(Integer pointConvertRequestNo);

    int updateByPrimaryKeySelective(PointConvertRequest record);

    int updateByPrimaryKey(PointConvertRequest record);
}