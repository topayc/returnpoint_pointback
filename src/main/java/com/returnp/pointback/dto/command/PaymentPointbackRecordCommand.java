package com.returnp.pointback.dto.command;

import java.util.Date;

import com.returnp.pointback.model.PaymentPointbackRecord;

public class PaymentPointbackRecordCommand extends PaymentPointbackRecord {
	
    private String memberName;
    private String memberPhone;
    private String memberEmail;
    private Float paymentApprovalAmount;
    private String affiliateSerial;
    private Date paymentApprovalDateTime;
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberPhone() {
		return memberPhone;
	}
	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}
	public String getMemberEmail() {
		return memberEmail;
	}
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
	public Float getPaymentApprovalAmount() {
		return paymentApprovalAmount;
	}
	public void setPaymentApprovalAmount(Float paymentApprovalAmount) {
		this.paymentApprovalAmount = paymentApprovalAmount;
	}
	public String getAffiliateSerial() {
		return affiliateSerial;
	}
	public void setAffiliateSerial(String affiliateSerial) {
		this.affiliateSerial = affiliateSerial;
	}
	public Date getPaymentApprovalDateTime() {
		return paymentApprovalDateTime;
	}
	public void setPaymentApprovalDateTime(Date paymentApprovalDateTime) {
		this.paymentApprovalDateTime = paymentApprovalDateTime;
	}

    
}
