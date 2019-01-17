package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class Recommender extends QueryCondition {
    private Integer recommenderNo;

    private String recommenderCode;

    private String recommenderName;

    private String recommenderAddress;

    private String recommenderTel;

    private String recommenderPhone;

    private Integer memberNo;

    private Integer saleManagerNo;

    private String recommenderStatus;

    private String regType;

    private Integer regAdminNo;

    private String recommenderEmail;

    private String greenPointAccStatus;

    private String redPointAccStatus;

    private String greenPointUseStatus;

    private String redPointUseStatus;

    private Date createTime;

    private Date updateTime;

    public Integer getRecommenderNo() {
        return recommenderNo;
    }

    public void setRecommenderNo(Integer recommenderNo) {
        this.recommenderNo = recommenderNo;
    }

    public String getRecommenderCode() {
        return recommenderCode;
    }

    public void setRecommenderCode(String recommenderCode) {
        this.recommenderCode = recommenderCode == null ? null : recommenderCode.trim();
    }

    public String getRecommenderName() {
        return recommenderName;
    }

    public void setRecommenderName(String recommenderName) {
        this.recommenderName = recommenderName == null ? null : recommenderName.trim();
    }

    public String getRecommenderAddress() {
        return recommenderAddress;
    }

    public void setRecommenderAddress(String recommenderAddress) {
        this.recommenderAddress = recommenderAddress == null ? null : recommenderAddress.trim();
    }

    public String getRecommenderTel() {
        return recommenderTel;
    }

    public void setRecommenderTel(String recommenderTel) {
        this.recommenderTel = recommenderTel == null ? null : recommenderTel.trim();
    }

    public String getRecommenderPhone() {
        return recommenderPhone;
    }

    public void setRecommenderPhone(String recommenderPhone) {
        this.recommenderPhone = recommenderPhone == null ? null : recommenderPhone.trim();
    }

    public Integer getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(Integer memberNo) {
        this.memberNo = memberNo;
    }

    public Integer getSaleManagerNo() {
        return saleManagerNo;
    }

    public void setSaleManagerNo(Integer saleManagerNo) {
        this.saleManagerNo = saleManagerNo;
    }

    public String getRecommenderStatus() {
        return recommenderStatus;
    }

    public void setRecommenderStatus(String recommenderStatus) {
        this.recommenderStatus = recommenderStatus == null ? null : recommenderStatus.trim();
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

    public String getRecommenderEmail() {
        return recommenderEmail;
    }

    public void setRecommenderEmail(String recommenderEmail) {
        this.recommenderEmail = recommenderEmail == null ? null : recommenderEmail.trim();
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