package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.GiftCardAccHistory;

public interface GiftCardAccHistoryMapper {
    int deleteByPrimaryKey(Integer giftCardAccHistoryNo);

    int insert(GiftCardAccHistory record);

    int insertSelective(GiftCardAccHistory record);

    GiftCardAccHistory selectByPrimaryKey(Integer giftCardAccHistoryNo);

    int updateByPrimaryKeySelective(GiftCardAccHistory record);

    int updateByPrimaryKey(GiftCardAccHistory record);
}