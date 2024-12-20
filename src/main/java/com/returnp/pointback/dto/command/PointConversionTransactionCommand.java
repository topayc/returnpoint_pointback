package com.returnp.pointback.dto.command;

import java.util.Date;

public class PointConversionTransactionCommand {
	 private String memberName;
	    private String memberEmail;
	    private String memberPassword;
	    private String memberPassword2;
	    private String memberType;
	    private String memberStatus;
	    private String memberAuthType;
	    private String memberPhone;
	    private Integer recommenderNo;
	    private String joinRoute;
	    private String statusReason;
	    private String isRecommender;
	    private String isSaleManager;
	    private String isBranch;
	    private String isAffiliate;
	    private String isAgency;
	    private String regType;
	    private Integer regAdminNo;
	    private String greenPointAccStatus;
	    private String redPointAccStatus;
	    private String greenPointUseStatus;
	    private String redPointUseStatus;
	    
	    private Integer pointConversionTansactionNo;
	    private Integer memberNo;
	    private Integer pointConvertRequestNo;
	    private Float conversionTotalPoint;
	    private Float conversionAccPoint;
	    private Float conversionAccRate;
	    private Float pointTransRate;
	    private String nodeType;
	    private String conversionStatus;
	    private Date createTime;
	    private Date updateTime;
	
	    
		public Integer getPointConvertRequestNo() {
			return pointConvertRequestNo;
		}
		public void setPointConvertRequestNo(Integer pointConvertRequestNo) {
			this.pointConvertRequestNo = pointConvertRequestNo;
		}
	
		public String getNodeType() {
			return nodeType;
		}
		public void setNodeType(String nodeType) {
			this.nodeType = nodeType;
		}
		
		public String getConversionStatus() {
			return conversionStatus;
		}
		public void setConversionStatus(String conversionStatus) {
			this.conversionStatus = conversionStatus;
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
		public String getMemberPassword() {
			return memberPassword;
		}
		public void setMemberPassword(String memberPassword) {
			this.memberPassword = memberPassword;
		}
		public String getMemberPassword2() {
			return memberPassword2;
		}
		public void setMemberPassword2(String memberPassword2) {
			this.memberPassword2 = memberPassword2;
		}
		public String getMemberType() {
			return memberType;
		}
		public void setMemberType(String memberType) {
			this.memberType = memberType;
		}
		public String getMemberStatus() {
			return memberStatus;
		}
		public void setMemberStatus(String memberStatus) {
			this.memberStatus = memberStatus;
		}
		public String getMemberAuthType() {
			return memberAuthType;
		}
		public void setMemberAuthType(String memberAuthType) {
			this.memberAuthType = memberAuthType;
		}
		public String getMemberPhone() {
			return memberPhone;
		}
		public void setMemberPhone(String memberPhone) {
			this.memberPhone = memberPhone;
		}
		public Integer getRecommenderNo() {
			return recommenderNo;
		}
		public void setRecommenderNo(Integer recommenderNo) {
			this.recommenderNo = recommenderNo;
		}
		public String getJoinRoute() {
			return joinRoute;
		}
		public void setJoinRoute(String joinRoute) {
			this.joinRoute = joinRoute;
		}
		public String getStatusReason() {
			return statusReason;
		}
		public void setStatusReason(String statusReason) {
			this.statusReason = statusReason;
		}
		public String getIsRecommender() {
			return isRecommender;
		}
		public void setIsRecommender(String isRecommender) {
			this.isRecommender = isRecommender;
		}
		public String getIsSaleManager() {
			return isSaleManager;
		}
		public void setIsSaleManager(String isSaleManager) {
			this.isSaleManager = isSaleManager;
		}
		public String getIsBranch() {
			return isBranch;
		}
		public void setIsBranch(String isBranch) {
			this.isBranch = isBranch;
		}
		public String getIsAffiliate() {
			return isAffiliate;
		}
		public void setIsAffiliate(String isAffiliate) {
			this.isAffiliate = isAffiliate;
		}
		public String getIsAgency() {
			return isAgency;
		}
		public void setIsAgency(String isAgency) {
			this.isAgency = isAgency;
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
		public Float getPointTransRate() {
			return pointTransRate;
		}
		public void setPointTransRate(Float pointTransRate) {
			this.pointTransRate = pointTransRate;
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
		
	    
}
