package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class GiftCardAccHistory extends QueryCondition {
    private Integer giftCardAccHistoryNo;

    private Integer memberNo;

    private Integer giftCardIssueNo;

    private Integer baseAmount;

    private Float accRate;

    private Float accAmount;

    private Date accTime;

    private Date createTime;

    private Date updateTime;

    public Integer getGiftCardAccHistoryNo() {
        return giftCardAccHistoryNo;
    }

    public void setGiftCardAccHistoryNo(Integer giftCardAccHistoryNo) {
        this.giftCardAccHistoryNo = giftCardAccHistoryNo;
    }

    public Integer getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(Integer memberNo) {
        this.memberNo = memberNo;
    }

    public Integer getGiftCardIssueNo() {
        return giftCardIssueNo;
    }

    public void setGiftCardIssueNo(Integer giftCardIssueNo) {
        this.giftCardIssueNo = giftCardIssueNo;
    }

    public Integer getBaseAmount() {
        return baseAmount;
    }

    public void setBaseAmount(Integer baseAmount) {
        this.baseAmount = baseAmount;
    }

    public Float getAccRate() {
        return accRate;
    }

    public void setAccRate(Float accRate) {
        this.accRate = accRate;
    }

    public Float getAccAmount() {
        return accAmount;
    }

    public void setAccAmount(Float accAmount) {
        this.accAmount = accAmount;
    }

    public Date getAccTime() {
        return accTime;
    }

    public void setAccTime(Date accTime) {
        this.accTime = accTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }
}