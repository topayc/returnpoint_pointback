package com.returnp.pointback.dto.command;

import java.util.Date;

public class PointConvertRequestCommand {
	private String memberEmail;
	private String memberName;
	private String memberPhone;
	private String memberTel;
	
	private Integer pointConvertRequestNo;
	private Integer memberNo;
	private Integer greenPointNo;
	private Float pointAmount;
	private String nodeType;
	private String requestNodeTypeName;
	private Float convertPointAmount;
	private String regType;
	private Integer regAdminNo;
	private Date createTime;
	private Date updateTime;
	
	
	
	public Float getPointAmount() {
		return pointAmount;
	}
	public void setPointAmount(Float pointAmount) {
		this.pointAmount = pointAmount;
	}
	public String getMemberEmail() {
		return memberEmail;
	}
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
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
	public String getMemberTel() {
		return memberTel;
	}
	public void setMemberTel(String memberTel) {
		this.memberTel = memberTel;
	}
	public Integer getPointConvertRequestNo() {
		return pointConvertRequestNo;
	}
	public void setPointConvertRequestNo(Integer pointConvertRequestNo) {
		this.pointConvertRequestNo = pointConvertRequestNo;
	}
	public Integer getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(Integer memberNo) {
		this.memberNo = memberNo;
	}
	public Integer getGreenPointNo() {
		return greenPointNo;
	}
	public void setGreenPointNo(Integer greenPointNo) {
		this.greenPointNo = greenPointNo;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	public String getRequestNodeTypeName() {
		return requestNodeTypeName;
	}
	public void setRequestNodeTypeName(String requestNodeTypeName) {
		this.requestNodeTypeName = requestNodeTypeName;
	}
	public Float getConvertPointAmount() {
		return convertPointAmount;
	}
	public void setConvertPointAmount(Float convertPointAmount) {
		this.convertPointAmount = convertPointAmount;
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
	};

	
}
