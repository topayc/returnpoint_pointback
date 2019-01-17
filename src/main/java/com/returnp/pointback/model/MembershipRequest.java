package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class MembershipRequest extends QueryCondition {
    private Integer membershipRequestNo;

    private Integer memberNo;

    private String depositor;

    private Integer companyBankAccountNo;

    private Integer recommenderNo;

    private Integer paymentAmount;

    private String paymentStatus;

    private String gradeType;

    private String paymentType;

    private String regType;

    private Integer regAdminNo;

    private Date createTime;

    private Date updateTime;

    public Integer getMembershipRequestNo() {
        return membershipRequestNo;
    }

    public void setMembershipRequestNo(Integer membershipRequestNo) {
        this.membershipRequestNo = membershipRequestNo;
    }

    public Integer getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(Integer memberNo) {
        this.memberNo = memberNo;
    }

    public String getDepositor() {
        return depositor;
    }

    public void setDepositor(String depositor) {
        this.depositor = depositor == null ? null : depositor.trim();
    }

    public Integer getCompanyBankAccountNo() {
        return companyBankAccountNo;
    }

    public void setCompanyBankAccountNo(Integer companyBankAccountNo) {
        this.companyBankAccountNo = companyBankAccountNo;
    }

    public Integer getRecommenderNo() {
        return recommenderNo;
    }

    public void setRecommenderNo(Integer recommenderNo) {
        this.recommenderNo = recommenderNo;
    }

    public Integer getPaymentAmount() {
        return paymentAmount;
    }

    public void setPaymentAmount(Integer paymentAmount) {
        this.paymentAmount = paymentAmount;
    }

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus == null ? null : paymentStatus.trim();
    }

    public String getGradeType() {
        return gradeType;
    }

    public void setGradeType(String gradeType) {
        this.gradeType = gradeType == null ? null : gradeType.trim();
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType == null ? null : paymentType.trim();
    }

    public String getRegType() {
        return regType;
    }

    public void setRegType(String regType) {
        this.regType = regType == null ? null : regType.trim();
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