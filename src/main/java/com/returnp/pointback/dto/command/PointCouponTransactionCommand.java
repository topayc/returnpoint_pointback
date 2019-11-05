package com.returnp.pointback.dto.command;

import java.util.Date;

import com.returnp.pointback.model.PointCouponTransaction;

public class PointCouponTransactionCommand extends PointCouponTransaction {
	private String memberName;
	private String memberEmail;
	private String memberPhone;
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
    private String accTargetRange;
    
    
	public String getAccTargetRange() {
		return accTargetRange;
	}
	public void setAccTargetRange(String accTargetRange) {
		this.accTargetRange = accTargetRange;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberEmail() {
		return memberEmail;
	}
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
	public String getMemberPhone() {
		return memberPhone;
	}
	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}
	public String getCouponNumber() {
		return couponNumber;
	}
	public void setCouponNumber(String couponNumber) {
		this.couponNumber = couponNumber;
	}
	public String getCouponType() {
		return couponType;
	}
	public void setCouponType(String couponType) {
		this.couponType = couponType;
	}
	public String getUseStatus() {
		return useStatus;
	}
	public void setUseStatus(String useStatus) {
		this.useStatus = useStatus;
	}
	public String getDeliveryStatus() {
		return deliveryStatus;
	}
	public void setDeliveryStatus(String deliveryStatus) {
		this.deliveryStatus = deliveryStatus;
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
		this.publisher = publisher;
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
    
}
