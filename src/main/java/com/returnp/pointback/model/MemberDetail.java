package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class MemberDetail extends QueryCondition {
    private Integer memberDetailNo;

    private Integer memberNo;

    private Integer regAdminNo;

    private Date createTime;

    private Date updateTime;

    public Integer getMemberDetailNo() {
        return memberDetailNo;
    }

    public void setMemberDetailNo(Integer memberDetailNo) {
        this.memberDetailNo = memberDetailNo;
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