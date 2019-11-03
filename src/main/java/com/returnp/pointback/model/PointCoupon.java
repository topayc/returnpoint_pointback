package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class PointCoupon extends QueryCondition {
    private Integer pointCouponNo;

    private String couponNumber;

    private String couponType;

    private String useStatus;

    private String deliveryStatus;

    private Integer payAmount;

    private Float accPointRate;

    private Float accPointAmount;

    private String publisher;

    private Date useStartTime;

    private Date useEndTime;

    private Date updateTime;

    private Date createTime;

    public Integer getPointCouponNo() {
        return pointCouponNo;
    }

    public void setPointCouponNo(Integer pointCouponNo) {
        this.pointCouponNo = pointCouponNo;
    }

    public String getCouponNumber() {
        return couponNumber;
    }

    public void setCouponNumber(String couponNumber) {
        this.couponNumber = couponNumber == null ? null : couponNumber.trim();
    }

    public String getCouponType() {
        return couponType;
    }

    public void setCouponType(String couponType) {
        this.couponType = couponType == null ? null : couponType.trim();
    }

    public String getUseStatus() {
        return useStatus;
    }

    public void setUseStatus(String useStatus) {
        this.useStatus = useStatus == null ? null : useStatus.trim();
    }

    public String getDeliveryStatus() {
        return deliveryStatus;
    }

    public void setDeliveryStatus(String deliveryStatus) {
        this.deliveryStatus = deliveryStatus == null ? null : deliveryStatus.trim();
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

    public Date getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(Date updateTime) {
        this.updateTime = updateTime;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }
}