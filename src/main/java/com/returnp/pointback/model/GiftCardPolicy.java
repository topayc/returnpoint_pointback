package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class GiftCardPolicy extends QueryCondition {
    private Integer giftCardPolicyNo;

    private Float headSaleFeeRate;

    private Float distSaleFeeRate;

    private Float saleOrganSaleFeeRate;

    private Float giftCardAccRate;

    private Float giftCardPayRefundRate;

    private String companyName;

    private String web;

    private String bankName;

    private String bankAccount;

    private String bankAccountOwner;

    private String csTel;

    private String csOperationTime;

    private String csEmail;

    private String bankDepositText;

    private Date createTime;

    private Date updateTime;

    public Integer getGiftCardPolicyNo() {
        return giftCardPolicyNo;
    }

    public void setGiftCardPolicyNo(Integer giftCardPolicyNo) {
        this.giftCardPolicyNo = giftCardPolicyNo;
    }

    public Float getHeadSaleFeeRate() {
        return headSaleFeeRate;
    }

    public void setHeadSaleFeeRate(Float headSaleFeeRate) {
        this.headSaleFeeRate = headSaleFeeRate;
    }

    public Float getDistSaleFeeRate() {
        return distSaleFeeRate;
    }

    public void setDistSaleFeeRate(Float distSaleFeeRate) {
        this.distSaleFeeRate = distSaleFeeRate;
    }

    public Float getSaleOrganSaleFeeRate() {
        return saleOrganSaleFeeRate;
    }

    public void setSaleOrganSaleFeeRate(Float saleOrganSaleFeeRate) {
        this.saleOrganSaleFeeRate = saleOrganSaleFeeRate;
    }

    public Float getGiftCardAccRate() {
        return giftCardAccRate;
    }

    public void setGiftCardAccRate(Float giftCardAccRate) {
        this.giftCardAccRate = giftCardAccRate;
    }

    public Float getGiftCardPayRefundRate() {
        return giftCardPayRefundRate;
    }

    public void setGiftCardPayRefundRate(Float giftCardPayRefundRate) {
        this.giftCardPayRefundRate = giftCardPayRefundRate;
    }

    public String getCompanyName() {
        return companyName;
    }

    public void setCompanyName(String companyName) {
        this.companyName = companyName == null ? null : companyName.trim();
    }

    public String getWeb() {
        return web;
    }

    public void setWeb(String web) {
        this.web = web == null ? null : web.trim();
    }

    public String getBankName() {
        return bankName;
    }

    public void setBankName(String bankName) {
        this.bankName = bankName == null ? null : bankName.trim();
    }

    public String getBankAccount() {
        return bankAccount;
    }

    public void setBankAccount(String bankAccount) {
        this.bankAccount = bankAccount == null ? null : bankAccount.trim();
    }

    public String getBankAccountOwner() {
        return bankAccountOwner;
    }

    public void setBankAccountOwner(String bankAccountOwner) {
        this.bankAccountOwner = bankAccountOwner == null ? null : bankAccountOwner.trim();
    }

    public String getCsTel() {
        return csTel;
    }

    public void setCsTel(String csTel) {
        this.csTel = csTel == null ? null : csTel.trim();
    }

    public String getCsOperationTime() {
        return csOperationTime;
    }

    public void setCsOperationTime(String csOperationTime) {
        this.csOperationTime = csOperationTime == null ? null : csOperationTime.trim();
    }

    public String getCsEmail() {
        return csEmail;
    }

    public void setCsEmail(String csEmail) {
        this.csEmail = csEmail == null ? null : csEmail.trim();
    }

    public String getBankDepositText() {
        return bankDepositText;
    }

    public void setBankDepositText(String bankDepositText) {
        this.bankDepositText = bankDepositText == null ? null : bankDepositText.trim();
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