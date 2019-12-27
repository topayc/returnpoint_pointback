package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class PointCodeIssueRequest extends QueryCondition {
    private Integer pointCodeIssueRequestNo;

    private Integer memberNo;

    private Integer affiliateNo;

    private String issueType;

    private Integer payAmount;

    private Float accPointRate;

    private Float accPointAmount;

    private String accTargetRange;

    private Float depositAmount;

    private Float depositRate;

    private String status;

    private String uploadFile;

    private String depositBankAccount;

    private String publisher;

    private Date useStartTime;

    private Date useEndTime;

    private Date createTime;

    private Date updateTime;

    public Integer getPointCodeIssueRequestNo() {
        return pointCodeIssueRequestNo;
    }

    public void setPointCodeIssueRequestNo(Integer pointCodeIssueRequestNo) {
        this.pointCodeIssueRequestNo = pointCodeIssueRequestNo;
    }

    public Integer getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(Integer memberNo) {
        this.memberNo = memberNo;
    }

    public Integer getAffiliateNo() {
        return affiliateNo;
    }

    public void setAffiliateNo(Integer affiliateNo) {
        this.affiliateNo = affiliateNo;
    }

    public String getIssueType() {
        return issueType;
    }

    public void setIssueType(String issueType) {
        this.issueType = issueType == null ? null : issueType.trim();
    }

    public Integer getPayAmount() {
        return payAmount;
    }

    public void setPayAmount(Integer payAmount) {
        this.payAmount = payAmount;
    }

    public Float getAccPointRate() {
        return accPointRate;
    }

    public void setAccPointRate(Float accPointRate) {
        this.accPointRate = accPointRate;
    }

    public Float getAccPointAmount() {
        return accPointAmount;
    }

    public void setAccPointAmount(Float accPointAmount) {
        this.accPointAmount = accPointAmount;
    }

    public String getAccTargetRange() {
        return accTargetRange;
    }

    public void setAccTargetRange(String accTargetRange) {
        this.accTargetRange = accTargetRange == null ? null : accTargetRange.trim();
    }

    public Float getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(Float depositAmount) {
        this.depositAmount = depositAmount;
    }

    public Float getDepositRate() {
        return depositRate;
    }

    public void setDepositRate(Float depositRate) {
        this.depositRate = depositRate;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status == null ? null : status.trim();
    }

    public String getUploadFile() {
        return uploadFile;
    }

    public void setUploadFile(String uploadFile) {
        this.uploadFile = uploadFile == null ? null : uploadFile.trim();
    }

    public String getDepositBankAccount() {
        return depositBankAccount;
    }

    public void setDepositBankAccount(String depositBankAccount) {
        this.depositBankAccount = depositBankAccount == null ? null : depositBankAccount.trim();
    }

    public String getPublisher() {
        return publisher;
    }

    public void setPublisher(String publisher) {
        this.publisher = publisher == null ? null : publisher.trim();
    }

    public Date getUseStartTime() {
        return useStartTime;
    }

    public void setUseStartTime(Date useStartTime) {
        this.useStartTime = useStartTime;
    }

    public Date getUseEndTime() {
        return useEndTime;
    }

    public void setUseEndTime(Date useEndTime) {
        this.useEndTime = useEndTime;
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