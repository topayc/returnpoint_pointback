package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.AffiliateDetail;

public interface AffiliateDetailMapper {
    int deleteByPrimaryKey(Integer affiliateDetailNo);

    int insert(AffiliateDetail record);

    int insertSelective(AffiliateDetail record);

    AffiliateDetail selectByPrimaryKey(Integer affiliateDetailNo);

    int updateByPrimaryKeySelective(AffiliateDetail record);

    int updateByPrimaryKey(AffiliateDetail record);
}