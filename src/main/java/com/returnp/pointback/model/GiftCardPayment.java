package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class GiftCardPayment extends QueryCondition {
    private Integer giftCardPaymentNo;

    private Integer affiliateNo;

    private Integer giftCardIssueNo;

    private Integer giftCardPaymentAmount;

    private Float refundRate;

    private float  refundAmount;

    private String refundStatus;

    private String bankName;

    private String backAccount;

    private String bankAccountOwner;

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

    public Float getRefundRate() {
        return refundRate;
    }

    public void setRefundRate(Float refundRate) {
        this.refundRate = refundRate;
    }

   

    public float getRefundAmount() {
		return refundAmount;
	}

	public void setRefundAmount(float refundAmount) {
		this.refundAmount = refundAmount;
	}

	public String getRefundStatus() {
        return refundStatus;
    }

    public void setRefundStatus(String refundStatus) {
        this.refundStatus = refundStatus == null ? null : refundStatus.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBackAccount() {
        return backAccount;
    }

    public void setBackAccount(String backAccount) {
        this.backAccount = backAccount == null ? null : backAccount.trim();
    }

    public String getBankAccountOwner() {
        return bankAccountOwner;
    }

    public void setBankAccountOwner(String bankAccountOwner) {
        this.bankAccountOwner = bankAccountOwner == null ? null : bankAccountOwner.trim();
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