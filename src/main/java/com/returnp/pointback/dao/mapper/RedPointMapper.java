package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.RedPoint;

public interface RedPointMapper {
    int deleteByPrimaryKey(Integer redPointNo);

    int insert(RedPoint record);

    int insertSelective(RedPoint record);

    RedPoint selectByPrimaryKey(Integer redPointNo);

    int updateByPrimaryKeySelective(RedPoint record);

    int updateByPrimaryKey(RedPoint record);
}