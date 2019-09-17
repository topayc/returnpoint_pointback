package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class Policy extends QueryCondition {
    private Integer policyNo;

    private Integer regAdminNo;

    private Float branchComm;

    private Float soleDistComm;

    private Float agancyComm;

    private Float affiliateComm;

    private Float branchRecComm;

    private Float agancyRecComm;

    private Float affiliateRecComm;

    private Float customerRecCom;

    private Float customerComm;

    private Float pointTransRate;

    private Float pointTransLimit;

    private Float redPointAccRate;

    private Integer membershipTransLimit;

    private Integer gPointMovingMinLimit;

    private Integer gPointMovingMaxLimit;

    private Integer rPointMovingMinLimit;

    private Integer rPointMovingMaxLimit;

    private Integer maxGpointAccLImit;

    private Float customerRecManagerComm;

    private Float affiliateRecManagerComm;

    private Float agancyRecManagerComm;

    private Float branchRecManagerComm;

    private Integer rPayWithdrawalMinLimit;

    private Integer rPayWithdrawalMaxLimit;

    private Date createTime;

    private Date updateTime;

    public Integer getPolicyNo() {
        return policyNo;
    }

    public void setPolicyNo(Integer policyNo) {
        this.policyNo = policyNo;
    }

    public Integer getRegAdminNo() {
        return regAdminNo;
    }

    public void setRegAdminNo(Integer regAdminNo) {
        this.regAdminNo = regAdminNo;
    }

    public Float getBranchComm() {
        return branchComm;
    }

    public void setBranchComm(Float branchComm) {
        this.branchComm = branchComm;
    }

    public Float getSoleDistComm() {
        return soleDistComm;
    }

    public void setSoleDistComm(Float soleDistComm) {
        this.soleDistComm = soleDistComm;
    }

    public Float getAgancyComm() {
        return agancyComm;
    }

    public void setAgancyComm(Float agancyComm) {
        this.agancyComm = agancyComm;
    }

    public Float getAffiliateComm() {
        return affiliateComm;
    }

    public void setAffiliateComm(Float affiliateComm) {
        this.affiliateComm = affiliateComm;
    }

    public Float getBranchRecComm() {
        return branchRecComm;
    }

    public void setBranchRecComm(Float branchRecComm) {
        this.branchRecComm = branchRecComm;
    }

    public Float getAgancyRecComm() {
        return agancyRecComm;
    }

    public void setAgancyRecComm(Float agancyRecComm) {
        this.agancyRecComm = agancyRecComm;
    }

    public Float getAffiliateRecComm() {
        return affiliateRecComm;
    }

    public void setAffiliateRecComm(Float affiliateRecComm) {
        this.affiliateRecComm = affiliateRecComm;
    }

    public Float getCustomerRecCom() {
        return customerRecCom;
    }

    public void setCustomerRecCom(Float customerRecCom) {
        this.customerRecCom = customerRecCom;
    }

    public Float getCustomerComm() {
        return customerComm;
    }

    public void setCustomerComm(Float customerComm) {
        this.customerComm = customerComm;
    }

    public Float getPointTransRate() {
        return pointTransRate;
    }

    public void setPointTransRate(Float pointTransRate) {
        this.pointTransRate = pointTransRate;
    }

    public Float getPointTransLimit() {
        return pointTransLimit;
    }

    public void setPointTransLimit(Float pointTransLimit) {
        this.pointTransLimit = pointTransLimit;
    }

    public Float getRedPointAccRate() {
        return redPointAccRate;
    }

    public void setRedPointAccRate(Float redPointAccRate) {
        this.redPointAccRate = redPointAccRate;
    }

    public Integer getMembershipTransLimit() {
        return membershipTransLimit;
    }

    public void setMembershipTransLimit(Integer membershipTransLimit) {
        this.membershipTransLimit = membershipTransLimit;
    }

    public Integer getgPointMovingMinLimit() {
        return gPointMovingMinLimit;
    }

    public void setgPointMovingMinLimit(Integer gPointMovingMinLimit) {
        this.gPointMovingMinLimit = gPointMovingMinLimit;
    }

    public Integer getgPointMovingMaxLimit() {
        return gPointMovingMaxLimit;
    }

    public void setgPointMovingMaxLimit(Integer gPointMovingMaxLimit) {
        this.gPointMovingMaxLimit = gPointMovingMaxLimit;
    }

    public Integer getrPointMovingMinLimit() {
        return rPointMovingMinLimit;
    }

    public void setrPointMovingMinLimit(Integer rPointMovingMinLimit) {
        this.rPointMovingMinLimit = rPointMovingMinLimit;
    }

    public Integer getrPointMovingMaxLimit() {
        return rPointMovingMaxLimit;
    }

    public void setrPointMovingMaxLimit(Integer rPointMovingMaxLimit) {
        this.rPointMovingMaxLimit = rPointMovingMaxLimit;
    }

    public Integer getMaxGpointAccLImit() {
        return maxGpointAccLImit;
    }

    public void setMaxGpointAccLImit(Integer maxGpointAccLImit) {
        this.maxGpointAccLImit = maxGpointAccLImit;
    }

    public Float getCustomerRecManagerComm() {
        return customerRecManagerComm;
    }

    public void setCustomerRecManagerComm(Float customerRecManagerComm) {
        this.customerRecManagerComm = customerRecManagerComm;
    }

    public Float getAffiliateRecManagerComm() {
        return affiliateRecManagerComm;
    }

    public void setAffiliateRecManagerComm(Float affiliateRecManagerComm) {
        this.affiliateRecManagerComm = affiliateRecManagerComm;
    }

    public Float getAgancyRecManagerComm() {
        return agancyRecManagerComm;
    }

    public void setAgancyRecManagerComm(Float agancyRecManagerComm) {
        this.agancyRecManagerComm = agancyRecManagerComm;
    }

    public Float getBranchRecManagerComm() {
        return branchRecManagerComm;
    }

    public void setBranchRecManagerComm(Float branchRecManagerComm) {
        this.branchRecManagerComm = branchRecManagerComm;
    }

    public Integer getrPayWithdrawalMinLimit() {
        return rPayWithdrawalMinLimit;
    }

    public void setrPayWithdrawalMinLimit(Integer rPayWithdrawalMinLimit) {
        this.rPayWithdrawalMinLimit = rPayWithdrawalMinLimit;
    }

    public Integer getrPayWithdrawalMaxLimit() {
        return rPayWithdrawalMaxLimit;
    }

    public void setrPayWithdrawalMaxLimit(Integer rPayWithdrawalMaxLimit) {
        this.rPayWithdrawalMaxLimit = rPayWithdrawalMaxLimit;
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