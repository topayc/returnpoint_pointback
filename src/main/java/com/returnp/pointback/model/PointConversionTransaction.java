package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class PointConversionTransaction extends QueryCondition {
    private Integer pointConversionTansactionNo;

    private Integer memberNo;

    private String nodeType;

    private Float conversionTotalPoint;

    private Float conversionAccPoint;

    private Float conversionAccRate;

    private String conversionStatus;

    private Date createTime;

    private Date updateTime;

    private Integer pointConvertRequestNo;

    private Float pointTransRate;

    public Integer getPointConversionTansactionNo() {
        return pointConversionTansactionNo;
    }

    public void setPointConversionTansactionNo(Integer pointConversionTansactionNo) {
        this.pointConversionTansactionNo = pointConversionTansactionNo;
    }

    public Integer getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(Integer memberNo) {
        this.memberNo = memberNo;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType == null ? null : nodeType.trim();
    }

    public Float getConversionTotalPoint() {
        return conversionTotalPoint;
    }

    public void setConversionTotalPoint(Float conversionTotalPoint) {
        this.conversionTotalPoint = conversionTotalPoint;
    }

    public Float getConversionAccPoint() {
        return conversionAccPoint;
    }

    public void setConversionAccPoint(Float conversionAccPoint) {
        this.conversionAccPoint = conversionAccPoint;
    }

    public Float getConversionAccRate() {
        return conversionAccRate;
    }

    public void setConversionAccRate(Float conversionAccRate) {
        this.conversionAccRate = conversionAccRate;
    }

    public String getConversionStatus() {
        return conversionStatus;
    }

    public void setConversionStatus(String conversionStatus) {
        this.conversionStatus = conversionStatus == null ? null : conversionStatus.trim();
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

    public Integer getPointConvertRequestNo() {
        return pointConvertRequestNo;
    }

    public void setPointConvertRequestNo(Integer pointConvertRequestNo) {
        this.pointConvertRequestNo = pointConvertRequestNo;
    }

    public Float getPointTransRate() {
        return pointTransRate;
    }

    public void setPointTransRate(Float pointTransRate) {
        this.pointTransRate = pointTransRate;
    }
}