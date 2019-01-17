package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class RedPoint extends QueryCondition {
    private Integer redPointNo;

    private Integer memberNo;

    private Float pointAmount;

    private Date redPointCreateTime;

    private Date redPointUpdateTime;

    public Integer getRedPointNo() {
        return redPointNo;
    }

    public void setRedPointNo(Integer redPointNo) {
        this.redPointNo = redPointNo;
    }

    public Integer getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(Integer memberNo) {
        this.memberNo = memberNo;
    }

    public Float getPointAmount() {
        return pointAmount;
    }

    public void setPointAmount(Float pointAmount) {
        this.pointAmount = pointAmount;
    }

    public Date getRedPointCreateTime() {
        return redPointCreateTime;
    }

    public void setRedPointCreateTime(Date redPointCreateTime) {
        this.redPointCreateTime = redPointCreateTime;
    }

    public Date getRedPointUpdateTime() {
        return redPointUpdateTime;
    }

    public void setRedPointUpdateTime(Date redPointUpdateTime) {
        this.redPointUpdateTime = redPointUpdateTime;
    }
}