package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class PointTransferTransaction extends QueryCondition {
    private Integer pointTransferTransactionNo;

    private Integer pointTransferer;

    private Integer pointReceiver;

    private Float pointTransferAmount;

    private String pointNode;

    private String pointType;

    private String pointTransferType;

    private String pointTransferStatus;

    private Date createTime;

    private Date updateTime;

    public Integer getPointTransferTransactionNo() {
        return pointTransferTransactionNo;
    }

    public void setPointTransferTransactionNo(Integer pointTransferTransactionNo) {
        this.pointTransferTransactionNo = pointTransferTransactionNo;
    }

    public Integer getPointTransferer() {
        return pointTransferer;
    }

    public void setPointTransferer(Integer pointTransferer) {
        this.pointTransferer = pointTransferer;
    }

    public Integer getPointReceiver() {
        return pointReceiver;
    }

    public void setPointReceiver(Integer pointReceiver) {
        this.pointReceiver = pointReceiver;
    }

    public Float getPointTransferAmount() {
        return pointTransferAmount;
    }

    public void setPointTransferAmount(Float pointTransferAmount) {
        this.pointTransferAmount = pointTransferAmount;
    }

    public String getPointNode() {
        return pointNode;
    }

    public void setPointNode(String pointNode) {
        this.pointNode = pointNode == null ? null : pointNode.trim();
    }

    public String getPointType() {
        return pointType;
    }

    public void setPointType(String pointType) {
        this.pointType = pointType == null ? null : pointType.trim();
    }

    public String getPointTransferType() {
        return pointTransferType;
    }

    public void setPointTransferType(String pointTransferType) {
        this.pointTransferType = pointTransferType == null ? null : pointTransferType.trim();
    }

    public String getPointTransferStatus() {
        return pointTransferStatus;
    }

    public void setPointTransferStatus(String pointTransferStatus) {
        this.pointTransferStatus = pointTransferStatus == null ? null : pointTransferStatus.trim();
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