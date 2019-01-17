package com.returnp.pointback.common;

import com.returnp.pointback.dto.response.BaseResponse;

public class ReturnpException extends Exception {
	private BaseResponse data;
	public ReturnpException(BaseResponse res) {
		this.data = res;
	} 
	public BaseResponse getBaseResponse() {
		return data;
	}

	public void setBaseResponse(BaseResponse data) {
		this.data = data;
	}
}
