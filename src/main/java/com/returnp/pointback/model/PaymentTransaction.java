package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class PaymentTransaction extends QueryCondition {
    private Integer paymentTransactionNo;

    private Integer memberNo;

    private String memberName;

    private String memberPhone;

    private String memberEmail;

    private String nodeType;

    private Integer nodeNo;

    private String nodeEmail;

    private String nodeName;

    private String nodePhone;

    private Integer affiliateNo;

    private String affiliateSerial;

    private Integer paymentApprovalAmount;

    private String paymentApprovalNumber;

    private String paymentApprovalStatus;

    private String pointBackStatus;

    private String paymentTransactionType;

    private Date paymentApprovalDateTime;

    private String orgPaymentData;

    private Integer regAdminNo;

    private Date createTime;

    private Date updateTime;

    public Integer getPaymentTransactionNo() {
        return paymentTransactionNo;
    }

    public void setPaymentTransactionNo(Integer paymentTransactionNo) {
        this.paymentTransactionNo = paymentTransactionNo;
    }

    public Integer getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(Integer memberNo) {
        this.memberNo = memberNo;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName == null ? null : memberName.trim();
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone == null ? null : memberPhone.trim();
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail == null ? null : memberEmail.trim();
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType == null ? null : nodeType.trim();
    }

    public Integer getNodeNo() {
        return nodeNo;
    }

    public void setNodeNo(Integer nodeNo) {
        this.nodeNo = nodeNo;
    }

    public String getNodeEmail() {
        return nodeEmail;
    }

    public void setNodeEmail(String nodeEmail) {
        this.nodeEmail = nodeEmail == null ? null : nodeEmail.trim();
    }

    public String getNodeName() {
        return nodeName;
    }

    public void setNodeName(String nodeName) {
        this.nodeName = nodeName == null ? null : nodeName.trim();
    }

    public String getNodePhone() {
        return nodePhone;
    }

    public void setNodePhone(String nodePhone) {
        this.nodePhone = nodePhone == null ? null : nodePhone.trim();
    }

    public Integer getAffiliateNo() {
        return affiliateNo;
    }

    public void setAffiliateNo(Integer affiliateNo) {
        this.affiliateNo = affiliateNo;
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

    public String getPointBackStatus() {
        return pointBackStatus;
    }

    public void setPointBackStatus(String pointBackStatus) {
        this.pointBackStatus = pointBackStatus == null ? null : pointBackStatus.trim();
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

    public String getOrgPaymentData() {
        return orgPaymentData;
    }

    public void setOrgPaymentData(String orgPaymentData) {
        this.orgPaymentData = orgPaymentData == null ? null : orgPaymentData.trim();
    }

    public Integer getRegAdminNo() {
        return regAdminNo;
    }

    public void setRegAdminNo(Integer regAdminNo) {
        this.regAdminNo = regAdminNo;
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