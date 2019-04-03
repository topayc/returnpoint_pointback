package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.GiftCardIssue;
import com.returnp.pointback.model.GiftCardIssueKey;

public interface GiftCardIssueMapper {
    int deleteByPrimaryKey(GiftCardIssueKey key);

    int insert(GiftCardIssue record);

    int insertSelective(GiftCardIssue record);

    GiftCardIssue selectByPrimaryKey(GiftCardIssueKey key);

    int updateByPrimaryKeySelective(GiftCardIssue record);

    int updateByPrimaryKey(GiftCardIssue record);
}