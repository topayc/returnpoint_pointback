package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.PointCodePointbackRecord;

public interface PointCodePointbackRecordMapper {
    int deleteByPrimaryKey(Integer pointCodePointbackRecordNo);

    int insert(PointCodePointbackRecord record);

    int insertSelective(PointCodePointbackRecord record);

    PointCodePointbackRecord selectByPrimaryKey(Integer pointCodePointbackRecordNo);

    int updateByPrimaryKeySelective(PointCodePointbackRecord record);

    int updateByPrimaryKey(PointCodePointbackRecord record);
}