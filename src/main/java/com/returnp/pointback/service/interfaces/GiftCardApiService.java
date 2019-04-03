package com.returnp.pointback.service.interfaces;

import java.util.HashMap;

import org.springframework.transaction.annotation.Transactional;

import com.returnp.pointback.dto.response.ReturnpBaseResponse;

@Transactional
public interface GiftCardApiService {
	public ReturnpBaseResponse giftCardPayment(HashMap<String, Object> reqMap);
	public ReturnpBaseResponse giftCardAccumulate(HashMap<String, Object> reqMap);
}
