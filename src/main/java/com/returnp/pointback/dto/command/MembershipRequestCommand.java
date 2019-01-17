package com.returnp.pointback.dto.command;

import java.util.Date;

public class MembershipRequestCommand {
	private Integer membershipRequestNo;
	private Integer companyBankAccountNo;
	private Integer memberNo;
	private Integer paymentAmount;
	private String paymentStatus;
	private String memberName;
	private String memberEmail;
	private String gradeType;
	private String paymentType;
	private String regType;
	private Integer regAdminNo;
	private String bankName;
	private String bankAccount;
	private String bankOwnerName;
	private Date updateTime;
	private Date createTime;
	
	
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
	public Integer getMembershipRequestNo() {
		return membershipRequestNo;
	}
	public void setMembershipRequestNo(Integer membershipRequestNo) {
		this.membershipRequestNo = membershipRequestNo;
	}
	public Integer getCompanyBankAccountNo() {
		return companyBankAccountNo;
	}
	public void setCompanyBankAccountNo(Integer companyBankAccountNo) {
		this.companyBankAccountNo = companyBankAccountNo;
	}
	public Integer getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(Integer memberNo) {
		this.memberNo = memberNo;
	}
	public Integer getPaymentAmount() {
		return paymentAmount;
	}
	public void setPaymentAmount(Integer paymentAmount) {
		this.paymentAmount = paymentAmount;
	}
	public String getPaymentStatus() {
		return paymentStatus;
	}
	public void setPaymentStatus(String paymentStatus) {
		this.paymentStatus = paymentStatus;
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
	public String getGradeType() {
		return gradeType;
	}
	public void setGradeType(String gradeType) {
		this.gradeType = gradeType;
	}
	public String getPaymentType() {
		return paymentType;
	}
	public void setPaymentType(String paymentType) {
		this.paymentType = paymentType;
	}
	public String getRegType() {
		return regType;
	}
	public void setRegType(String regType) {
		this.regType = regType;
	}
	public Integer getRegAdminNo() {
		return regAdminNo;
	}
	public void setRegAdminNo(Integer regAdminNo) {
		this.regAdminNo = regAdminNo;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getBankOwnerName() {
		return bankOwnerName;
	}
	public void setBankOwnerName(String bankOwnerName) {
		this.bankOwnerName = bankOwnerName;
	}
	

}
