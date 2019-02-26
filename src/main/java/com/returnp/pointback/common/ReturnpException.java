package com.returnp.pointback.common;

import com.returnp.pointback.dto.response.ReturnpBaseResponse;

public class ReturnpException extends RuntimeException {
	private static final long serialVersionUID = 1L;
	private ReturnpBaseResponse data;
	public ReturnpException(ReturnpBaseResponse res) {
		this.data = res;
	} 
	public ReturnpBaseResponse getBaseResponse() {
		return data;
	}

	public void setBaseResponse(ReturnpBaseResponse data) {
		this.data = data;
	}
}
