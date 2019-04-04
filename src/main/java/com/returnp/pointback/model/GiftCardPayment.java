package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class GiftCardPayment extends QueryCondition {
    private Integer giftCardPaymentNo;

    private Integer affiliateNo;

    private Integer giftCardIssueNo;

    private Integer giftCardPaymentAmount;

    private Integer memberBankAccountNo;

    private Float refundRate;

    private Float refundAmount;

    private String refundStatus;

    private Date createTime;

    private Date updateTime;

    public Integer getGiftCardPaymentNo() {
        return giftCardPaymentNo;
    }

    public void setGiftCardPaymentNo(Integer giftCardPaymentNo) {
        this.giftCardPaymentNo = giftCardPaymentNo;
    }

    public Integer getAffiliateNo() {
        return affiliateNo;
    }

    public void setAffiliateNo(Integer affiliateNo) {
        this.affiliateNo = affiliateNo;
    }

    public Integer getGiftCardIssueNo() {
        return giftCardIssueNo;
    }

    public void setGiftCardIssueNo(Integer giftCardIssueNo) {
        this.giftCardIssueNo = giftCardIssueNo;
    }

    public Integer getGiftCardPaymentAmount() {
        return giftCardPaymentAmount;
    }

    public void setGiftCardPaymentAmount(Integer giftCardPaymentAmount) {
        this.giftCardPaymentAmount = giftCardPaymentAmount;
    }

 

    public Integer getMemberBankAccountNo() {
		return memberBankAccountNo;
	}

	public void setMemberBankAccountNo(Integer memberBankAccountNo) {
		this.memberBankAccountNo = memberBankAccountNo;
	}

	public Float getRefundRate() {
        return refundRate;
    }

    public void setRefundRate(Float refundRate) {
        this.refundRate = refundRate;
    }

    public Float getRefundAmount() {
        return refundAmount;
    }

    public void setRefundAmount(Float refundAmount) {
        this.refundAmount = refundAmount;
    }

    public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus == null ? null : refundStatus.trim();
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