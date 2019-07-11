package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.AffiliateCiderpay;

public interface AffiliateCiderpayMapper {
    int deleteByPrimaryKey(Integer affiliateCiderPayNo);

    int insert(AffiliateCiderpay record);

    int insertSelective(AffiliateCiderpay record);

    AffiliateCiderpay selectByPrimaryKey(Integer affiliateCiderPayNo);

    int updateByPrimaryKeySelective(AffiliateCiderpay record);

    int updateByPrimaryKey(AffiliateCiderpay record);
}