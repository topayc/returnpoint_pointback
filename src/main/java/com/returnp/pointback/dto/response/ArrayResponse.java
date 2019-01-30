package com.returnp.pointback.dto.response;

import java.util.ArrayList;

public class ArrayResponse<T> extends ReturnpBaseResponse{
	public int total;
	public ArrayList<T> data;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public ArrayList<T> getData() {
		return data;
	}
	public void setData(ArrayList<T> data) {
		this.data = data;
	}
}
