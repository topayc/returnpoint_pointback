package com.returnp.pointback.dto.response;

/**
 * @author user01
 *
 * @param <T>
 */
public class SingleDataObjectResponse <T> extends BaseResponse {
	T data;

	public T getData() {
		return data;
	}

	public void setData(T data) {
		this.data = data;
	}
	
}
