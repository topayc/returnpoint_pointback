package com.returnp.pointback.dto.command;

import java.util.Date;

public class SoleDistCommand {
    private Integer soleDistNo;

    private Integer memberNo;

    private String memberName;
    private String memberEmail;
    private String soleDistCode;

    private String soleDistEmail;

    private String soleDistName;

    private String soleDistAddress;

    private String soleDistTel;

    private String soleDistPhone;

    private String soleDistStatus;

    private String regType;

    private Integer regAdminNo;

    private String greenPointAccStatus;

    private String redPointAccStatus;

    private String greenPointUseStatus;

    private String redPointUseStatus;

    private Date createTime;

    private Date updatetime;
    
    private float greenPointAmount;
    
    private float redPointAmount;

    
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

	public Integer getSoleDistNo() {
		return soleDistNo;
	}

	public void setSoleDistNo(Integer soleDistNo) {
		this.soleDistNo = soleDistNo;
	}

	public Integer getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(Integer memberNo) {
		this.memberNo = memberNo;
	}

	public String getSoleDistCode() {
		return soleDistCode;
	}

	public void setSoleDistCode(String soleDistCode) {
		this.soleDistCode = soleDistCode;
	}

	public String getSoleDistEmail() {
		return soleDistEmail;
	}

	public void setSoleDistEmail(String soleDistEmail) {
		this.soleDistEmail = soleDistEmail;
	}

	public String getSoleDistName() {
		return soleDistName;
	}

	public void setSoleDistName(String soleDistName) {
		this.soleDistName = soleDistName;
	}

	public String getSoleDistAddress() {
		return soleDistAddress;
	}

	public void setSoleDistAddress(String soleDistAddress) {
		this.soleDistAddress = soleDistAddress;
	}

	public String getSoleDistTel() {
		return soleDistTel;
	}

	public void setSoleDistTel(String soleDistTel) {
		this.soleDistTel = soleDistTel;
	}

	public String getSoleDistPhone() {
		return soleDistPhone;
	}

	public void setSoleDistPhone(String soleDistPhone) {
		this.soleDistPhone = soleDistPhone;
	}

	public String getSoleDistStatus() {
		return soleDistStatus;
	}

	public void setSoleDistStatus(String soleDistStatus) {
		this.soleDistStatus = soleDistStatus;
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

	public String getGreenPointAccStatus() {
		return greenPointAccStatus;
	}

	public void setGreenPointAccStatus(String greenPointAccStatus) {
		this.greenPointAccStatus = greenPointAccStatus;
	}

	public String getRedPointAccStatus() {
		return redPointAccStatus;
	}

	public void setRedPointAccStatus(String redPointAccStatus) {
		this.redPointAccStatus = redPointAccStatus;
	}

	public String getGreenPointUseStatus() {
		return greenPointUseStatus;
	}

	public void setGreenPointUseStatus(String greenPointUseStatus) {
		this.greenPointUseStatus = greenPointUseStatus;
	}

	public String getRedPointUseStatus() {
		return redPointUseStatus;
	}

	public void setRedPointUseStatus(String redPointUseStatus) {
		this.redPointUseStatus = redPointUseStatus;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdatetime() {
		return updatetime;
	}

	public void setUpdatetime(Date updatetime) {
		this.updatetime = updatetime;
	}

	public float getGreenPointAmount() {
		return greenPointAmount;
	}

	public void setGreenPointAmount(float greenPointAmount) {
		this.greenPointAmount = greenPointAmount;
	}

	public float getRedPointAmount() {
		return redPointAmount;
	}

	public void setRedPointAmount(float redPointAmount) {
		this.redPointAmount = redPointAmount;
	}
    
}
