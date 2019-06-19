package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.AffiliatePaymentRouter;

public interface AffiliatePaymentRouterMapper {
    int deleteByPrimaryKey(Integer affiliatePaymentRouterNo);

    int insert(AffiliatePaymentRouter record);

    int insertSelective(AffiliatePaymentRouter record);

    AffiliatePaymentRouter selectByPrimaryKey(Integer affiliatePaymentRouterNo);

    int updateByPrimaryKeySelective(AffiliatePaymentRouter record);

    int updateByPrimaryKey(AffiliatePaymentRouter record);
}