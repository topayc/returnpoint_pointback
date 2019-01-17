package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class Code extends QueryCondition {
    private Integer codeNo;

    private String groupCode;

    private String groupCodeName;

    private String code;

    private String codeValue;

    private String codeName;

    private String adminUseStatus;

    private String webUseStatus;

    private Date createTime;

    private Date updateTime;

    public Integer getCodeNo() {
        return codeNo;
    }

    public void setCodeNo(Integer codeNo) {
        this.codeNo = codeNo;
    }

    public String getGroupCode() {
        return groupCode;
    }

    public void setGroupCode(String groupCode) {
        this.groupCode = groupCode == null ? null : groupCode.trim();
    }

    public String getGroupCodeName() {
        return groupCodeName;
    }

    public void setGroupCodeName(String groupCodeName) {
        this.groupCodeName = groupCodeName == null ? null : groupCodeName.trim();
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code == null ? null : code.trim();
    }

    public String getCodeValue() {
        return codeValue;
    }

    public void setCodeValue(String codeValue) {
        this.codeValue = codeValue == null ? null : codeValue.trim();
    }

    public String getCodeName() {
        return codeName;
    }

    public void setCodeName(String codeName) {
        this.codeName = codeName == null ? null : codeName.trim();
    }

    public String getAdminUseStatus() {
        return adminUseStatus;
    }

    public void setAdminUseStatus(String adminUseStatus) {
        this.adminUseStatus = adminUseStatus == null ? null : adminUseStatus.trim();
    }

    public String getWebUseStatus() {
        return webUseStatus;
    }

    public void setWebUseStatus(String webUseStatus) {
        this.webUseStatus = webUseStatus == null ? null : webUseStatus.trim();
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