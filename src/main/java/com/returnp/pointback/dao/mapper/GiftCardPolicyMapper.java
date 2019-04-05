package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.GiftCardPolicy;

public interface GiftCardPolicyMapper {
    int deleteByPrimaryKey(Integer giftCardPolicyNo);

    int insert(GiftCardPolicy record);

    int insertSelective(GiftCardPolicy record);

    GiftCardPolicy selectByPrimaryKey(Integer giftCardPolicyNo);

    int updateByPrimaryKeySelective(GiftCardPolicy record);

    int updateByPrimaryKey(GiftCardPolicy record);
}