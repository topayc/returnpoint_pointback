package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class MemberCardInfo extends QueryCondition {
    private Integer memberCardInfoNo;

    private String cardName;

    private String cardAccount;

    private String cardExpiration;

    private Integer memberNo;

    private Integer regAdminNo;

    private Date createTime;

    private Date updateTime;

    public Integer getMemberCardInfoNo() {
        return memberCardInfoNo;
    }

    public void setMemberCardInfoNo(Integer memberCardInfoNo) {
        this.memberCardInfoNo = memberCardInfoNo;
    }

    public String getCardName() {
        return cardName;
    }

    public void setCardName(String cardName) {
        this.cardName = cardName == null ? null : cardName.trim();
    }

    public String getCardAccount() {
        return cardAccount;
    }

    public void setCardAccount(String cardAccount) {
        this.cardAccount = cardAccount == null ? null : cardAccount.trim();
    }

    public String getCardExpiration() {
        return cardExpiration;
    }

    public void setCardExpiration(String cardExpiration) {
        this.cardExpiration = cardExpiration == null ? null : cardExpiration.trim();
    }

    public Integer getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(Integer memberNo) {
        this.memberNo = memberNo;
    }

    public Integer getRegAdminNo() {
        return regAdminNo;
    }

    public void setRegAdminNo(Integer regAdminNo) {
        this.regAdminNo = regAdminNo;
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