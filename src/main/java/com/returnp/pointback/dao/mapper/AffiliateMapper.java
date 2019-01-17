package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.Affiliate;

public interface AffiliateMapper {
    int deleteByPrimaryKey(Integer affiliateNo);

    int insert(Affiliate record);

    int insertSelective(Affiliate record);

    Affiliate selectByPrimaryKey(Integer affiliateNo);

    int updateByPrimaryKeySelective(Affiliate record);

    int updateByPrimaryKey(Affiliate record);
    
    Affiliate selectBySerial(String serial);
}