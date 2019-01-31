package com.returnp.pointback.service;

import org.springframework.transaction.annotation.Transactional;

import com.returnp.pointback.dto.command.api.ApiRequest;
import com.returnp.pointback.dto.response.ReturnpBaseResponse;

@Transactional
public interface EncryptService {
	public String encrypt(String encyptKey, ReturnpBaseResponse res);
	public ApiRequest decrypt(String encyptKey, String data);
}
