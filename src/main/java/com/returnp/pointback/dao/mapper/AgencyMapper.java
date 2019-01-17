package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.Agency;

public interface AgencyMapper {
    int deleteByPrimaryKey(Integer agencyNo);

    int insert(Agency record);

    int insertSelective(Agency record);

    Agency selectByPrimaryKey(Integer agencyNo);

    int updateByPrimaryKeySelective(Agency record);

    int updateByPrimaryKey(Agency record);
}