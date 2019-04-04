package com.returnp.pointback.dto;

public class QRRequest {
	private String key;
	private String qr_cmd;
	private String pinNumber;
	private String memberEmai;
	private String memberPhone;
	private String memberPhoneCountry;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getQr_cmd() {
		return qr_cmd;
	}
	public void setQr_cmd(String qr_cmd) {
		this.qr_cmd = qr_cmd;
	}
	public String getPinNumber() {
		return pinNumber;
	}
	public void setPinNumber(String pinNumber) {
		this.pinNumber = pinNumber;
	}
	public String getMemberEmai() {
		return memberEmai;
	}
	public void setMemberEmai(String memberEmai) {
		this.memberEmai = memberEmai;
	}
	public String getMemberPhone() {
		return memberPhone;
	}
	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}
	public String getMemberPhoneCountry() {
		return memberPhoneCountry;
	}
	public void setMemberPhoneCountry(String memberPhoneCountry) {
		this.memberPhoneCountry = memberPhoneCountry;
	}
}
