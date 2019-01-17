package com.returnp.pointback.dto.request;

public class FormPaginationReq {
	public String pageDataType; 
	public int page;
	public int rows;
	
	public String getPageDataType() {
		return pageDataType;
	}
	public void setPageDataType(String pageDataType) {
		this.pageDataType = pageDataType;
	}
	public int getPage() {
		return page;
	}
	public void setPage(int page) {
		this.page = page;
	}
	public int getRows() {
		return rows;
	}
	public void setRows(int rows) {
		this.rows = rows;
	}
}
