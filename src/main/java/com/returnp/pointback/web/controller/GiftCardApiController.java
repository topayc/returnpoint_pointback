package com.returnp.pointback.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.returnp.pointback.common.ResponseUtil;
import com.returnp.pointback.dto.response.ReturnpBaseResponse;
import com.returnp.pointback.service.interfaces.GiftCardApiService;
import com.returnp.pointback.util.QRManager;
import com.returnp.pointback.web.message.MessageUtils;

@Controller
@RequestMapping("/v1/api")
public class GiftCardApiController extends ApplicationController{
	
	private static final Logger logger = LoggerFactory.getLogger(GiftCardApiController.class);
	
	@Autowired private MessageUtils messageUtils;
	@Autowired private GiftCardApiService giftCardApiService;
	@Autowired private Environment env;
	
	private ArrayList<String> keys;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
	}
	
	@ResponseBody
	@RequestMapping(value = "/handleGiftCardReq", method = RequestMethod.POST)
	public ReturnpBaseResponse handleGiftCardReq( HashMap<String, Object> reqMap){
		logger.info("### handleGiftCardReq 호출됨");
		ReturnpBaseResponse res= null;
		if (!this.keys.contains((String)reqMap.get("key"))) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "306", this.messageUtils.getMessage("pointback.message.invalid_key"));
			return res;
		}
		String qrCmd = (String)reqMap.get("qrCmd");
		switch(qrCmd) {
		case QRManager.QRCmd.ACC_BY_GIFTCARD:
			res = this.giftCardApiService.giftCardAccumulate(reqMap);
			break;
		case QRManager.QRCmd.PAY_BY_GIFTCARD:
			res = this.giftCardApiService.giftCardPayment(reqMap);
			break;
		}
		return null;
	}
	
	
	/**
	 * @param data
	 * @param memberEmail
	 * @param memberPhone
	 * @param memberPhoneCountry
	 * @param key
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/giftCardPayment", method = RequestMethod.POST)
	public ReturnpBaseResponse giftCardPayment( HashMap<String, Object> reqMap){
		logger.info("### giftCardPayment 호출됨");
		ReturnpBaseResponse res= null;
		if (!this.keys.contains((String)reqMap.get("key"))) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "306", this.messageUtils.getMessage("pointback.message.invalid_key"));
			return res;
		}else {
		}
		return null;
	}
	
	/**
	 * 
	 * @param data
	 * @param memberEmail
	 * @param memberPhone
	 * @param memberPhoneCountry
	 * @param key
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/giftCardAccumulate", method = RequestMethod.POST)
	public ReturnpBaseResponse giftCardAccumulate( HashMap<String, Object> reqMap){
		logger.info("### giftCardAcculate 호출됨");
		ReturnpBaseResponse res= null;
		if (!this.keys.contains((String)reqMap.get("qrCmd"))) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "306", this.messageUtils.getMessage("pointback.message.invalid_key"));
			return res;
		}else {
		
		}
		return null;
		
	}
}
