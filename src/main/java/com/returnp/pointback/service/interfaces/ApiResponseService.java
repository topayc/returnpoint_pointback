package com.returnp.pointback.service.interfaces;

import java.util.ArrayList;
import java.util.HashMap;

import org.springframework.transaction.annotation.Transactional;

import com.returnp.pointback.dto.command.api.ApiRequest;
import com.returnp.pointback.dto.response.ReturnpBaseResponse;
import com.returnp.pointback.dto.response.StringResponse;

@Transactional
public interface ApiResponseService {
	public String encode(String encyptKey, ReturnpBaseResponse res);
	public String decode(String data , String encyptKey);
	public StringResponse generateResponse(ReturnpBaseResponse base, String key);
}
