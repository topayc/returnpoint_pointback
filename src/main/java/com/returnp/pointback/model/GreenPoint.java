package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class GreenPoint extends QueryCondition {
    private Integer greenPointNo;

    private Integer memberNo;

    private Integer nodeNo;

    private String nodeType;

    private String nodeTypeName;

    private Float pointAmount;

    private Date greenPointCreateTime;

    private Date greenPointUpdateTime;

    public Integer getGreenPointNo() {
        return greenPointNo;
    }

    public void setGreenPointNo(Integer greenPointNo) {
        this.greenPointNo = greenPointNo;
    }

    public Integer getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(Integer memberNo) {
        this.memberNo = memberNo;
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

    public String getNodeTypeName() {
        return nodeTypeName;
    }

    public void setNodeTypeName(String nodeTypeName) {
        this.nodeTypeName = nodeTypeName == null ? null : nodeTypeName.trim();
    }

    public Float getPointAmount() {
        return pointAmount;
    }

    public void setPointAmount(Float pointAmount) {
        this.pointAmount = pointAmount;
    }

    public Date getGreenPointCreateTime() {
        return greenPointCreateTime;
    }

    public void setGreenPointCreateTime(Date greenPointCreateTime) {
        this.greenPointCreateTime = greenPointCreateTime;
    }

    public Date getGreenPointUpdateTime() {
        return greenPointUpdateTime;
    }

    public void setGreenPointUpdateTime(Date greenPointUpdateTime) {
        this.greenPointUpdateTime = greenPointUpdateTime;
    }
}