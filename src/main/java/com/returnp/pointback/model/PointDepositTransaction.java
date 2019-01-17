package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class PointDepositTransaction extends QueryCondition {
    private Integer pointDepositTransactionNo;

    private Integer depositAmount;

    private Integer depositMemberNo;

    private String depositMemberType;

    private Date createTime;

    private Date updateTime;

    public Integer getPointDepositTransactionNo() {
        return pointDepositTransactionNo;
    }

    public void setPointDepositTransactionNo(Integer pointDepositTransactionNo) {
        this.pointDepositTransactionNo = pointDepositTransactionNo;
    }

    public Integer getDepositAmount() {
        return depositAmount;
    }

    public void setDepositAmount(Integer depositAmount) {
        this.depositAmount = depositAmount;
    }

    public Integer getDepositMemberNo() {
        return depositMemberNo;
    }

    public void setDepositMemberNo(Integer depositMemberNo) {
        this.depositMemberNo = depositMemberNo;
    }

    public String getDepositMemberType() {
        return depositMemberType;
    }

    public void setDepositMemberType(String depositMemberType) {
        this.depositMemberType = depositMemberType == null ? null : depositMemberType.trim();
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