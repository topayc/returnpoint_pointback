package com.returnp.pointback.dto.response;

import java.util.ArrayList;

public class SearchListResponse<T> extends BaseResponse{
	public int total;
	public ArrayList<T> rows;

	
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public ArrayList<T> getRows() {
		return rows;
	}
	public void setRows(ArrayList<T> rows) {
		this.rows = rows;
	}
}
