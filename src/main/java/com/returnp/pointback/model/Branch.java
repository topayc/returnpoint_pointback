package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class Branch extends QueryCondition {
    private Integer branchNo;

    private Integer soleDistNo;

    private String branchCode;

    private String branchName;

    private String branchAddress;

    private String branchTel;

    private String branchPhone;

    private String branchStatus;

    private Integer memberNo;

    private Integer recommenderNo;

    private String regType;

    private Integer regAdminNo;

    private String branchEmail;

    private String greenPointAccStatus;

    private String redPointAccStatus;

    private String greenPointUseStatus;

    private String redPointUseStatus;

    private Date createTime;

    private Date updateTime;

    public Integer getBranchNo() {
        return branchNo;
    }

    public void setBranchNo(Integer branchNo) {
        this.branchNo = branchNo;
    }

    public Integer getSoleDistNo() {
        return soleDistNo;
    }

    public void setSoleDistNo(Integer soleDistNo) {
        this.soleDistNo = soleDistNo;
    }

    public String getBranchCode() {
        return branchCode;
    }

    public void setBranchCode(String branchCode) {
        this.branchCode = branchCode == null ? null : branchCode.trim();
    }

    public String getBranchName() {
        return branchName;
    }

    public void setBranchName(String branchName) {
        this.branchName = branchName == null ? null : branchName.trim();
    }

    public String getBranchAddress() {
        return branchAddress;
    }

    public void setBranchAddress(String branchAddress) {
        this.branchAddress = branchAddress == null ? null : branchAddress.trim();
    }

    public String getBranchTel() {
        return branchTel;
    }

    public void setBranchTel(String branchTel) {
        this.branchTel = branchTel == null ? null : branchTel.trim();
    }

    public String getBranchPhone() {
        return branchPhone;
    }

    public void setBranchPhone(String branchPhone) {
        this.branchPhone = branchPhone == null ? null : branchPhone.trim();
    }

    public String getBranchStatus() {
        return branchStatus;
    }

    public void setBranchStatus(String branchStatus) {
        this.branchStatus = branchStatus == null ? null : branchStatus.trim();
    }

    public Integer getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(Integer memberNo) {
        this.memberNo = memberNo;
    }

    public Integer getRecommenderNo() {
        return recommenderNo;
    }

    public void setRecommenderNo(Integer recommenderNo) {
        this.recommenderNo = recommenderNo;
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

    public String getBranchEmail() {
        return branchEmail;
    }

    public void setBranchEmail(String branchEmail) {
        this.branchEmail = branchEmail == null ? null : branchEmail.trim();
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