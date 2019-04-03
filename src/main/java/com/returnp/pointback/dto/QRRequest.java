package com.returnp.pointback.dto;

public class QRRequest {
	private String key;
	private String qrCmd;
	private String data;
	private String memberEmai;
	private String memberPhone;
	private String memberPhoneCountry;
	public String getKey() {
		return key;
	}
	public void setKey(String key) {
		this.key = key;
	}
	public String getQrCmd() {
		return qrCmd;
	}
	public void setQrCmd(String qrCmd) {
		this.qrCmd = qrCmd;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
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
