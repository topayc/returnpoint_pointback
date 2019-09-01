package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class GpointPayment extends QueryCondition {
    private Integer gpointPaymentNo;

    private Integer memberNo;

    private String memberPhone;

    private String memberName;

    private String memberEmail;

    private Integer affiliateNo;

    private String affiliateName;

    private String affiliateSerial;

    private Integer paymentApprovalAmount;

    private String paymentApprovalNumber;

    private String paymentApprovalStatus;

    private String paymentMethod;

    private String paymentTransactionType;

    private Date paymentApprovalDateTime;

    private Integer realPaymentAmount;

    private Integer gpointPaymentAmount;

    private Date createTime;

    private Date updateTime;

    public Integer getGpointPaymentNo() {
        return gpointPaymentNo;
    }

    public void setGpointPaymentNo(Integer gpointPaymentNo) {
        this.gpointPaymentNo = gpointPaymentNo;
    }

    public Integer getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(Integer memberNo) {
        this.memberNo = memberNo;
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone == null ? null : memberPhone.trim();
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName == null ? null : memberName.trim();
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail == null ? null : memberEmail.trim();
    }

    public Integer getAffiliateNo() {
        return affiliateNo;
    }

    public void setAffiliateNo(Integer affiliateNo) {
        this.affiliateNo = affiliateNo;
    }

    public String getAffiliateName() {
        return affiliateName;
    }

    public void setAffiliateName(String affiliateName) {
        this.affiliateName = affiliateName == null ? null : affiliateName.trim();
    }

    public String getAffiliateSerial() {
        return affiliateSerial;
    }

    public void setAffiliateSerial(String affiliateSerial) {
        this.affiliateSerial = affiliateSerial == null ? null : affiliateSerial.trim();
    }

    public Integer getPaymentApprovalAmount() {
        return paymentApprovalAmount;
    }

    public void setPaymentApprovalAmount(Integer paymentApprovalAmount) {
        this.paymentApprovalAmount = paymentApprovalAmount;
    }

    public String getPaymentApprovalNumber() {
        return paymentApprovalNumber;
    }

    public void setPaymentApprovalNumber(String paymentApprovalNumber) {
        this.paymentApprovalNumber = paymentApprovalNumber == null ? null : paymentApprovalNumber.trim();
    }

    public String getPaymentApprovalStatus() {
        return paymentApprovalStatus;
    }

    public void setPaymentApprovalStatus(String paymentApprovalStatus) {
        this.paymentApprovalStatus = paymentApprovalStatus == null ? null : paymentApprovalStatus.trim();
    }

    public String getPaymentMethod() {
        return paymentMethod;
    }

    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod == null ? null : paymentMethod.trim();
    }

    public String getPaymentTransactionType() {
        return paymentTransactionType;
    }

    public void setPaymentTransactionType(String paymentTransactionType) {
        this.paymentTransactionType = paymentTransactionType == null ? null : paymentTransactionType.trim();
    }

    public Date getPaymentApprovalDateTime() {
        return paymentApprovalDateTime;
    }

    public void setPaymentApprovalDateTime(Date paymentApprovalDateTime) {
        this.paymentApprovalDateTime = paymentApprovalDateTime;
    }

    public Integer getRealPaymentAmount() {
        return realPaymentAmount;
    }

    public void setRealPaymentAmount(Integer realPaymentAmount) {
        this.realPaymentAmount = realPaymentAmount;
    }

    public Integer getGpointPaymentAmount() {
        return gpointPaymentAmount;
    }

    public void setGpointPaymentAmount(Integer gpointPaymentAmount) {
        this.gpointPaymentAmount = gpointPaymentAmount;
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