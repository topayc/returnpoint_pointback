package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class VanOriData extends QueryCondition {
    private Integer vanOriDataNo;

    private String vanData;

    private Date createTime;

    private Date updateTime;

    public Integer getVanOriDataNo() {
        return vanOriDataNo;
    }

    public void setVanOriDataNo(Integer vanOriDataNo) {
        this.vanOriDataNo = vanOriDataNo;
    }

    public String getVanData() {
        return vanData;
    }

    public void setVanData(String vanData) {
        this.vanData = vanData == null ? null : vanData.trim();
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