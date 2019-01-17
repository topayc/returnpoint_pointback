package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class CompanyBankAccount extends QueryCondition {
    private Integer companyBankAccountNo;

    private Integer regAdminNo;

    private String bankName;

    private String bankOwnerName;

    private String bankAccount;

    private Date createTime;

    private Date updateTime;

    public Integer getCompanyBankAccountNo() {
        return companyBankAccountNo;
    }

    public void setCompanyBankAccountNo(Integer companyBankAccountNo) {
        this.companyBankAccountNo = companyBankAccountNo;
    }

    public Integer getRegAdminNo() {
        return regAdminNo;
    }

    public void setRegAdminNo(Integer regAdminNo) {
        this.regAdminNo = regAdminNo;
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankOwnerName() {
        return bankOwnerName;
    }

    public void setBankOwnerName(String bankOwnerName) {
        this.bankOwnerName = bankOwnerName == null ? null : bankOwnerName.trim();
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount == null ? null : bankAccount.trim();
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