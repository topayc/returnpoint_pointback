package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class PointConvertRequest extends QueryCondition {
    private Integer pointConvertRequestNo;

    private Integer memberNo;

    private Integer greenPointNo;

    private String nodeType;

    private String requestNodeTypeName;

    private Float convertPointAmount;

    private String regType;

    private Integer regAdminNo;

    private Date createTime;

    private Date updateTime;

    public Integer getPointConvertRequestNo() {
        return pointConvertRequestNo;
    }

    public void setPointConvertRequestNo(Integer pointConvertRequestNo) {
        this.pointConvertRequestNo = pointConvertRequestNo;
    }

    public Integer getMemberNo() {
        return memberNo;
    }

    public void setMemberNo(Integer memberNo) {
        this.memberNo = memberNo;
    }

    public Integer getGreenPointNo() {
        return greenPointNo;
    }

    public void setGreenPointNo(Integer greenPointNo) {
        this.greenPointNo = greenPointNo;
    }

    public String getNodeType() {
        return nodeType;
    }

    public void setNodeType(String nodeType) {
        this.nodeType = nodeType == null ? null : nodeType.trim();
    }

    public String getRequestNodeTypeName() {
        return requestNodeTypeName;
    }

    public void setRequestNodeTypeName(String requestNodeTypeName) {
        this.requestNodeTypeName = requestNodeTypeName == null ? null : requestNodeTypeName.trim();
    }

    public Float getConvertPointAmount() {
        return convertPointAmount;
    }

    public void setConvertPointAmount(Float convertPointAmount) {
        this.convertPointAmount = convertPointAmount;
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