package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class SaleManager extends QueryCondition {
    private Integer saleManagerNo;

    private Integer memberNo;

    private String saleManagerCode;

    private String saleManagerName;

    private String saleManagerAddress;

    private String saleManagerTel;

    private String saleManagerPhone;

    private String saleManagerStatus;

    private String regType;

    private Integer regAdminNo;

    private String saleManagerEmail;

    private String greenPointAccStatus;

    private String redPointAccStatus;

    private String greenPointUseStatus;

    private String redPointUseStatus;

    private Date createTime;

    private Date updateTime;

    public Integer getSaleManagerNo() {
        return saleManagerNo;
    }

    public void setSaleManagerNo(Integer saleManagerNo) {
        this.saleManagerNo = saleManagerNo;
    }

    public Integer getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(Integer memberNo) {
        this.memberNo = memberNo;
    }

    public String getSaleManagerCode() {
        return saleManagerCode;
    }

    public void setSaleManagerCode(String saleManagerCode) {
        this.saleManagerCode = saleManagerCode == null ? null : saleManagerCode.trim();
    }

    public String getSaleManagerName() {
        return saleManagerName;
    }

    public void setSaleManagerName(String saleManagerName) {
        this.saleManagerName = saleManagerName == null ? null : saleManagerName.trim();
    }

    public String getSaleManagerAddress() {
        return saleManagerAddress;
    }

    public void setSaleManagerAddress(String saleManagerAddress) {
        this.saleManagerAddress = saleManagerAddress == null ? null : saleManagerAddress.trim();
    }

    public String getSaleManagerTel() {
        return saleManagerTel;
    }

    public void setSaleManagerTel(String saleManagerTel) {
        this.saleManagerTel = saleManagerTel == null ? null : saleManagerTel.trim();
    }

    public String getSaleManagerPhone() {
        return saleManagerPhone;
    }

    public void setSaleManagerPhone(String saleManagerPhone) {
        this.saleManagerPhone = saleManagerPhone == null ? null : saleManagerPhone.trim();
    }

    public String getSaleManagerStatus() {
        return saleManagerStatus;
    }

    public void setSaleManagerStatus(String saleManagerStatus) {
        this.saleManagerStatus = saleManagerStatus == null ? null : saleManagerStatus.trim();
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

    public String getSaleManagerEmail() {
        return saleManagerEmail;
    }

    public void setSaleManagerEmail(String saleManagerEmail) {
        this.saleManagerEmail = saleManagerEmail == null ? null : saleManagerEmail.trim();
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