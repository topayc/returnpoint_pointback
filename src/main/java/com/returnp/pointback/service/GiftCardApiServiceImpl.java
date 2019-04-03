package com.returnp.pointback.service;

import java.util.HashMap;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.returnp.pointback.common.ResponseUtil;
import com.returnp.pointback.common.ReturnpException;
import com.returnp.pointback.dto.response.ReturnpBaseResponse;
import com.returnp.pointback.service.interfaces.GiftCardApiService;
import com.returnp.pointback.web.message.MessageUtils;

@Service
public class GiftCardApiServiceImpl implements GiftCardApiService {

	@Autowired MessageUtils messageUtils;
	@Autowired Environment env;
	
	/* 
	 * 상품권 결제 큐알 스캔에 의한 결제 처리 
	 */
	@Override
	public ReturnpBaseResponse giftCardPayment(HashMap<String, Object> reqMap) {
		ReturnpBaseResponse res = new ReturnpBaseResponse();
		try {
			return res;
		} catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR, "500", "상품권 결제 QR 처리 에러 ");
			return res;
		}
	}

	/* 
	 * 상품권 적립 큐알 스캔에 의한 적립 처리
	 */
	@Override
	public ReturnpBaseResponse giftCardAccumulate(HashMap<String, Object> reqMap) {
		ReturnpBaseResponse res = new ReturnpBaseResponse();
		try {
			return res;
		} catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR, "500", "상품권 적립 QR 처리 에러 ");
			return res;
		}
	}
}
