package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class PointCodeTransaction extends QueryCondition {
    private Integer pointCodeTransactionNo;

    private Integer memberNo;

    private Integer pointCodeIssueNo;

    private String pointBackStatus;

    private Integer payAmount;

    private Float accPointRate;

    private Float accPointAmount;

    private String pointCode;

    private Date createTime;

    private Date updateTime;

    public Integer getPointCodeTransactionNo() {
        return pointCodeTransactionNo;
    }

    public void setPointCodeTransactionNo(Integer pointCodeTransactionNo) {
        this.pointCodeTransactionNo = pointCodeTransactionNo;
    }

    public Integer getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(Integer memberNo) {
        this.memberNo = memberNo;
    }

    public Integer getPointCodeIssueNo() {
        return pointCodeIssueNo;
    }

    public void setPointCodeIssueNo(Integer pointCodeIssueNo) {
        this.pointCodeIssueNo = pointCodeIssueNo;
    }

    public String getPointBackStatus() {
        return pointBackStatus;
    }

    public void setPointBackStatus(String pointBackStatus) {
        this.pointBackStatus = pointBackStatus == null ? null : pointBackStatus.trim();
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

    public String getPointCode() {
        return pointCode;
    }

    public void setPointCode(String pointCode) {
        this.pointCode = pointCode == null ? null : pointCode.trim();
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