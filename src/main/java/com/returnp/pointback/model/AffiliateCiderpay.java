package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class AffiliateCiderpay extends QueryCondition {
    private Integer affiliateCiderPayNo;

    private Integer affiliateNo;

    private String ciderPayStatus;

    private Date updateTime;

    private Date createTime;

    public Integer getAffiliateCiderPayNo() {
        return affiliateCiderPayNo;
    }

    public void setAffiliateCiderPayNo(Integer affiliateCiderPayNo) {
        this.affiliateCiderPayNo = affiliateCiderPayNo;
    }

    public Integer getAffiliateNo() {
        return affiliateNo;
    }

    public void setAffiliateNo(Integer affiliateNo) {
        this.affiliateNo = affiliateNo;
    }



    public String getCiderPayStatus() {
		return ciderPayStatus;
	}

	public void setCiderPayStatus(String ciderPayStatus) {
		this.ciderPayStatus = ciderPayStatus;
	}

	public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}