package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class SoleDist extends QueryCondition {
    private Integer soleDistNo;

    private Integer memberNo;

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
        this.soleDistCode = soleDistCode == null ? null : soleDistCode.trim();
    }

    public String getSoleDistEmail() {
        return soleDistEmail;
    }

    public void setSoleDistEmail(String soleDistEmail) {
        this.soleDistEmail = soleDistEmail == null ? null : soleDistEmail.trim();
    }

    public String getSoleDistName() {
        return soleDistName;
    }

    public void setSoleDistName(String soleDistName) {
        this.soleDistName = soleDistName == null ? null : soleDistName.trim();
    }

    public String getSoleDistAddress() {
        return soleDistAddress;
    }

    public void setSoleDistAddress(String soleDistAddress) {
        this.soleDistAddress = soleDistAddress == null ? null : soleDistAddress.trim();
    }

    public String getSoleDistTel() {
        return soleDistTel;
    }

    public void setSoleDistTel(String soleDistTel) {
        this.soleDistTel = soleDistTel == null ? null : soleDistTel.trim();
    }

    public String getSoleDistPhone() {
        return soleDistPhone;
    }

    public void setSoleDistPhone(String soleDistPhone) {
        this.soleDistPhone = soleDistPhone == null ? null : soleDistPhone.trim();
    }

    public String getSoleDistStatus() {
        return soleDistStatus;
    }

    public void setSoleDistStatus(String soleDistStatus) {
        this.soleDistStatus = soleDistStatus == null ? null : soleDistStatus.trim();
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

    public Date getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(Date updatetime) {
        this.updatetime = updatetime;
    }
}