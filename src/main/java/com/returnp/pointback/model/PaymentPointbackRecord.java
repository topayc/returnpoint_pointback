package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class PaymentPointbackRecord extends QueryCondition {
    private Integer paymentPointbackRecordNo;

    private Integer memberNo;

    private Integer paymentTransactionNo;

    private Integer nodeNo;

    private String nodeType;

    private Float accRate;

    private Float pointbackAmount;

    private Date createTime;

    private Date updateTime;

    public Integer getPaymentPointbackRecordNo() {
        return paymentPointbackRecordNo;
    }

    public void setPaymentPointbackRecordNo(Integer paymentPointbackRecordNo) {
        this.paymentPointbackRecordNo = paymentPointbackRecordNo;
    }

    public Integer getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(Integer memberNo) {
        this.memberNo = memberNo;
    }

    public Integer getPaymentTransactionNo() {
        return paymentTransactionNo;
    }

    public void setPaymentTransactionNo(Integer paymentTransactionNo) {
        this.paymentTransactionNo = paymentTransactionNo;
    }

    public Integer getNodeNo() {
        return nodeNo;
    }

    public void setNodeNo(Integer nodeNo) {
        this.nodeNo = nodeNo;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType == null ? null : nodeType.trim();
    }

    public Float getAccRate() {
        return accRate;
    }

    public void setAccRate(Float accRate) {
        this.accRate = accRate;
    }

    public Float getPointbackAmount() {
        return pointbackAmount;
    }

    public void setPointbackAmount(Float pointbackAmount) {
        this.pointbackAmount = pointbackAmount;
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