package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class AdminMenu extends QueryCondition {
    private Integer adminMenuNo;

    private String menuNameKr;

    private String menuNameEn;

    private Integer menuDeps;

    private String link;

    private String viewReqCode;

    private String menuDesKr;

    private String menuDesEn;

    private Date createTime;

    private Date updateTime;

    private Integer regAdminNo;

    public Integer getAdminMenuNo() {
        return adminMenuNo;
    }

    public void setAdminMenuNo(Integer adminMenuNo) {
        this.adminMenuNo = adminMenuNo;
    }

    public String getMenuNameKr() {
        return menuNameKr;
    }

    public void setMenuNameKr(String menuNameKr) {
        this.menuNameKr = menuNameKr == null ? null : menuNameKr.trim();
    }

    public String getMenuNameEn() {
        return menuNameEn;
    }

    public void setMenuNameEn(String menuNameEn) {
        this.menuNameEn = menuNameEn == null ? null : menuNameEn.trim();
    }

    public Integer getMenuDeps() {
        return menuDeps;
    }

    public void setMenuDeps(Integer menuDeps) {
        this.menuDeps = menuDeps;
    }

    public String getLink() {
        return link;
    }

    public void setLink(String link) {
        this.link = link == null ? null : link.trim();
    }

    public String getViewReqCode() {
        return viewReqCode;
    }

    public void setViewReqCode(String viewReqCode) {
        this.viewReqCode = viewReqCode == null ? null : viewReqCode.trim();
    }

    public String getMenuDesKr() {
        return menuDesKr;
    }

    public void setMenuDesKr(String menuDesKr) {
        this.menuDesKr = menuDesKr == null ? null : menuDesKr.trim();
    }

    public String getMenuDesEn() {
        return menuDesEn;
    }

    public void setMenuDesEn(String menuDesEn) {
        this.menuDesEn = menuDesEn == null ? null : menuDesEn.trim();
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

    public Integer getRegAdminNo() {
        return regAdminNo;
    }

    public void setRegAdminNo(Integer regAdminNo) {
        this.regAdminNo = regAdminNo;
    }
}