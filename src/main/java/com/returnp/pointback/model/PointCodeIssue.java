package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class PointCodeIssue extends QueryCondition {
    private Integer pointCodeIssueNo;

    private Integer pointCodeIssueRequestNo;

    private Integer memberNo;

    private String pointCode;

    private String useStatus;

    private Integer payAmount;

    private Float accPointRate;

    private Float accPointAmount;

    private Date createTime;

    private Date updateTime;

    public Integer getPointCodeIssueNo() {
        return pointCodeIssueNo;
    }

    public void setPointCodeIssueNo(Integer pointCodeIssueNo) {
        this.pointCodeIssueNo = pointCodeIssueNo;
    }

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

    public String getPointCode() {
        return pointCode;
    }

    public void setPointCode(String pointCode) {
        this.pointCode = pointCode == null ? null : pointCode.trim();
    }

    public String getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus == null ? null : useStatus.trim();
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