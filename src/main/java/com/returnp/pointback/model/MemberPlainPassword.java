package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class MemberPlainPassword extends QueryCondition {
    private Integer memberPlainPasswordNo;

    private Integer memberNo;

    private String plainPassword;

    private String hashPassword;

    private Date createTime;

    private Date updateTime;

    public Integer getMemberPlainPasswordNo() {
        return memberPlainPasswordNo;
    }

    public void setMemberPlainPasswordNo(Integer memberPlainPasswordNo) {
        this.memberPlainPasswordNo = memberPlainPasswordNo;
    }

    public Integer getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(Integer memberNo) {
        this.memberNo = memberNo;
    }

    public String getPlainPassword() {
        return plainPassword;
    }

    public void setPlainPassword(String plainPassword) {
        this.plainPassword = plainPassword == null ? null : plainPassword.trim();
    }

    public String getHashPassword() {
        return hashPassword;
    }

    public void setHashPassword(String hashPassword) {
        this.hashPassword = hashPassword == null ? null : hashPassword.trim();
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