package com.returnp.pointback.dto.response;

import java.util.ArrayList;

public class StringResponse extends ReturnpBaseResponse {
	public int total;
	public String data;
	public int getTotal() {
		return total;
	}
	public void setTotal(int total) {
		this.total = total;
	}
	public String getData() {
		return data;
	}
	public void setData(String data) {
		this.data = data;
	}
}
