package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class Member extends QueryCondition {
    private Integer memberNo;

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

    private String isSoleDist;

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

    private Date createTime;

    private Date updateTime;

    private String country;

    private String language;

    public Integer getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(Integer memberNo) {
        this.memberNo = memberNo;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName == null ? null : memberName.trim();
    }

    public String getMemberEmail() {
        return memberEmail;
    }

    public void setMemberEmail(String memberEmail) {
        this.memberEmail = memberEmail == null ? null : memberEmail.trim();
    }

    public String getMemberPassword() {
        return memberPassword;
    }

    public void setMemberPassword(String memberPassword) {
        this.memberPassword = memberPassword == null ? null : memberPassword.trim();
    }

    public String getMemberPassword2() {
        return memberPassword2;
    }

    public void setMemberPassword2(String memberPassword2) {
        this.memberPassword2 = memberPassword2 == null ? null : memberPassword2.trim();
    }

    public String getMemberType() {
        return memberType;
    }

    public void setMemberType(String memberType) {
        this.memberType = memberType == null ? null : memberType.trim();
    }

    public String getMemberStatus() {
        return memberStatus;
    }

    public void setMemberStatus(String memberStatus) {
        this.memberStatus = memberStatus == null ? null : memberStatus.trim();
    }

    public String getMemberAuthType() {
        return memberAuthType;
    }

    public void setMemberAuthType(String memberAuthType) {
        this.memberAuthType = memberAuthType == null ? null : memberAuthType.trim();
    }

    public String getMemberPhone() {
        return memberPhone;
    }

    public void setMemberPhone(String memberPhone) {
        this.memberPhone = memberPhone == null ? null : memberPhone.trim();
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
        this.joinRoute = joinRoute == null ? null : joinRoute.trim();
    }

    public String getStatusReason() {
        return statusReason;
    }

    public void setStatusReason(String statusReason) {
        this.statusReason = statusReason == null ? null : statusReason.trim();
    }

    public String getIsSoleDist() {
        return isSoleDist;
    }

    public void setIsSoleDist(String isSoleDist) {
        this.isSoleDist = isSoleDist == null ? null : isSoleDist.trim();
    }

    public String getIsRecommender() {
        return isRecommender;
    }

    public void setIsRecommender(String isRecommender) {
        this.isRecommender = isRecommender == null ? null : isRecommender.trim();
    }

    public String getIsSaleManager() {
        return isSaleManager;
    }

    public void setIsSaleManager(String isSaleManager) {
        this.isSaleManager = isSaleManager == null ? null : isSaleManager.trim();
    }

    public String getIsBranch() {
        return isBranch;
    }

    public void setIsBranch(String isBranch) {
        this.isBranch = isBranch == null ? null : isBranch.trim();
    }

    public String getIsAffiliate() {
        return isAffiliate;
    }

    public void setIsAffiliate(String isAffiliate) {
        this.isAffiliate = isAffiliate == null ? null : isAffiliate.trim();
    }

    public String getIsAgency() {
        return isAgency;
    }

    public void setIsAgency(String isAgency) {
        this.isAgency = isAgency == null ? null : isAgency.trim();
    }

    public String getRegType() {
        return regType;
    }

    public void setRegType(String regType) {
        this.regType = regType == null ? null : regType.trim();
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
        this.greenPointAccStatus = greenPointAccStatus == null ? null : greenPointAccStatus.trim();
    }

    public String getRedPointAccStatus() {
        return redPointAccStatus;
    }

    public void setRedPointAccStatus(String redPointAccStatus) {
        this.redPointAccStatus = redPointAccStatus == null ? null : redPointAccStatus.trim();
    }

    public String getGreenPointUseStatus() {
        return greenPointUseStatus;
    }

    public void setGreenPointUseStatus(String greenPointUseStatus) {
        this.greenPointUseStatus = greenPointUseStatus == null ? null : greenPointUseStatus.trim();
    }

    public String getRedPointUseStatus() {
        return redPointUseStatus;
    }

    public void setRedPointUseStatus(String redPointUseStatus) {
        this.redPointUseStatus = redPointUseStatus == null ? null : redPointUseStatus.trim();
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

    public String getCountry() {
        return country;
    }

    public void setCountry(String country) {
        this.country = country == null ? null : country.trim();
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language == null ? null : language.trim();
    }
}