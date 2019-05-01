package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.AffiliateTid;

public interface AffiliateTidMapper {
    int deleteByPrimaryKey(Integer affiliateTidNo);

    int insert(AffiliateTid record);

    int insertSelective(AffiliateTid record);

    AffiliateTid selectByPrimaryKey(Integer affiliateTidNo);

    int updateByPrimaryKeySelective(AffiliateTid record);

    int updateByPrimaryKey(AffiliateTid record);
}