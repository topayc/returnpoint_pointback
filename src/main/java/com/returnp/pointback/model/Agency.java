package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class Agency extends QueryCondition {
    private Integer agencyNo;

    private String agencyCode;

    private String agencyName;

    private String agencyAddress;

    private String agencyTel;

    private String agencyPhone;

    private Integer memberNo;

    private Integer branchNo;

    private Integer recommenderNo;

    private String agencyStatus;

    private String regType;

    private Integer regAdminNo;

    private String agencyEmail;

    private String greenPointAccStatus;

    private String redPointAccStatus;

    private String greenPointUseStatus;

    private String redPointUseStatus;

    private Date createTime;

    private Date updateTime;

    public Integer getAgencyNo() {
        return agencyNo;
    }

    public void setAgencyNo(Integer agencyNo) {
        this.agencyNo = agencyNo;
    }

    public String getAgencyCode() {
        return agencyCode;
    }

    public void setAgencyCode(String agencyCode) {
        this.agencyCode = agencyCode == null ? null : agencyCode.trim();
    }

    public String getAgencyName() {
        return agencyName;
    }

    public void setAgencyName(String agencyName) {
        this.agencyName = agencyName == null ? null : agencyName.trim();
    }

    public String getAgencyAddress() {
        return agencyAddress;
    }

    public void setAgencyAddress(String agencyAddress) {
        this.agencyAddress = agencyAddress == null ? null : agencyAddress.trim();
    }

    public String getAgencyTel() {
        return agencyTel;
    }

    public void setAgencyTel(String agencyTel) {
        this.agencyTel = agencyTel == null ? null : agencyTel.trim();
    }

    public String getAgencyPhone() {
        return agencyPhone;
    }

    public void setAgencyPhone(String agencyPhone) {
        this.agencyPhone = agencyPhone == null ? null : agencyPhone.trim();
    }

    public Integer getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(Integer memberNo) {
        this.memberNo = memberNo;
    }

    public Integer getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(Integer branchNo) {
        this.branchNo = branchNo;
    }

    public Integer getRecommenderNo() {
        return recommenderNo;
    }

    public void setRecommenderNo(Integer recommenderNo) {
        this.recommenderNo = recommenderNo;
    }

    public String getAgencyStatus() {
        return agencyStatus;
    }

    public void setAgencyStatus(String agencyStatus) {
        this.agencyStatus = agencyStatus == null ? null : agencyStatus.trim();
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

    public String getAgencyEmail() {
        return agencyEmail;
    }

    public void setAgencyEmail(String agencyEmail) {
        this.agencyEmail = agencyEmail == null ? null : agencyEmail.trim();
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
}