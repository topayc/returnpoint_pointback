package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class AffiliateDetail extends QueryCondition {
    private Integer affiliateDetailNo;

    private Integer affiliateNo;

    private String businessNumber;

    private String businessType;

    private String businessItem;

    private Date createTime;

    private Date updateTime;

    public Integer getAffiliateDetailNo() {
        return affiliateDetailNo;
    }

    public void setAffiliateDetailNo(Integer affiliateDetailNo) {
        this.affiliateDetailNo = affiliateDetailNo;
    }

    public Integer getAffiliateNo() {
        return affiliateNo;
    }

    public void setAffiliateNo(Integer affiliateNo) {
        this.affiliateNo = affiliateNo;
    }

    public String getBusinessNumber() {
        return businessNumber;
    }

    public void setBusinessNumber(String businessNumber) {
        this.businessNumber = businessNumber == null ? null : businessNumber.trim();
    }

    public String getBusinessType() {
        return businessType;
    }

    public void setBusinessType(String businessType) {
        this.businessType = businessType == null ? null : businessType.trim();
    }

    public String getBusinessItem() {
        return businessItem;
    }

    public void setBusinessItem(String businessItem) {
        this.businessItem = businessItem == null ? null : businessItem.trim();
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