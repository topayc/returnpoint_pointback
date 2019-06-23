package com.returnp.pointback.web.controller;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;

import javax.annotation.PostConstruct;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.returnp.pointback.common.AppConstants.AccumulateRequestFrom;
import com.returnp.pointback.common.AppConstants;
import com.returnp.pointback.common.DataMap;
import com.returnp.pointback.common.ResponseUtil;
import com.returnp.pointback.dto.response.ReturnpBaseResponse;
import com.returnp.pointback.service.interfaces.BasePointAccumulateService;
import com.returnp.pointback.service.interfaces.PointAccumulateService;
import com.returnp.pointback.web.message.MessageUtils;

/**
 * @author topayc
 * KICC 외의 다른 밴사 및 PG 의 적립 요청 처리 컨트롤러 
 */
@Controller
@PropertySource("classpath:/config.properties")
public class CommonPointAccumulaterController extends ApplicationController{
	
	@Autowired PointAccumulateService pointBackService;
	@Autowired BasePointAccumulateService basePointAccumulateService;
	@Autowired MessageUtils messageUtils;
	@Autowired Environment env;
	
	ArrayList<String> keys;
	ArrayList<Integer> pointAccList = new ArrayList<Integer>();
	
	private static final Logger logger = LoggerFactory.getLogger(CommonPointAccumulaterController.class);
	
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
	}
	
	 /**
	 * 기본 초기화 작업
	 * 외부 연결 허용 키 목록 세팅
	 * 이후 연결 키 생성 기능구현시, 디비로 관리 
	 */
	@PostConstruct
	 public void init() {
		 keys = new ArrayList<String>(Arrays.asList(env.getProperty("keys").trim().split(",")));
	 }
	
	/**
	 * 기존 KICC 외에 다른 밴사를 통해 요청되어 지는 큐알 적립 요청 처리 
	 */
	@ResponseBody
	@RequestMapping(value = "/commonQrAccPoint", method = RequestMethod.GET)
	public ReturnpBaseResponse commonqQrAccPoint( 
			@RequestParam(value = "vanName", required = false ) String vanName, 
			//@RequestParam(value = "seq", required = false, defaultValue = "1") int seq, 
			@RequestParam(value = "qr_org", required = false) String qr_org, 
			@RequestParam(value = "pam", required = true ) int pam,
			@RequestParam(value = "pas", required = true ) String pas,
			@RequestParam(value = "pat", required = false ) Date pat,
			@RequestParam(value = "pan", required = true ) String pan,
			@RequestParam(value = "af_id", required = true ) String afId,
			@RequestParam(value = "phoneNumber", required = true ) String phoneNumber,
			@RequestParam(value = "phoneNumberCountry", required = false ) String phoneNumberCountry,
			@RequestParam(value = "memberEmail", required = true ) String memberEmail,
			@RequestParam(value = "key", required = true ) String key
			){
		
		System.out.println("-------------------------commonqQrAccPoint--------------------------------");
		System.out.println("--------------------------------------------------------------------------------");
		
		ReturnpBaseResponse res= null;
		
		DataMap dataMap = new DataMap();
		if (!this.keys.contains(key)) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res,ResponseUtil.RESPONSE_OK,  "306", this.messageUtils.getMessage("pointback.message.invalid_key"));
			return res;
		}else {
			if (qr_org != null) {
				dataMap.put("qr_org", qr_org.trim());
			}
			dataMap.put("pam", pam);
			dataMap.put("pas", pas.trim());
			
			dataMap.put("pat", (pat == null ? new Date(): pat));
			dataMap.put("pan", pan.trim());
			dataMap.put("af_id", afId.trim());
			dataMap.put("phoneNumber", phoneNumber.trim());

			if (phoneNumberCountry != null) {
				dataMap.put("phoneNumberCountry", phoneNumberCountry.trim());
			}
			dataMap.put("memberEmail", memberEmail);
			dataMap.put("key", key.trim());
			
			dataMap.put("payment_router_type", AppConstants.PaymentRouterType.VAN);
			dataMap.put("payment_router_name", vanName);
			dataMap.put("payment_transaction_type", AppConstants.PaymentTransactionType.QR);
			
			/*적립*/
			if (pas.equals("0")) {
				res = this.basePointAccumulateService.accumulate(dataMap);
			}
		
			/*적립 취소*/
			else if (pas.equals("1")) {
				res = this.basePointAccumulateService.cancelAccumulate(dataMap);
			}
		}
		return res;
	}
	
	/**
	 * PG 사를 통한 적립 요청 처리 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/pg/pgAccumulate", method = RequestMethod.GET)
	public ReturnpBaseResponse pgAccumulate( 
			){
		ReturnpBaseResponse res= null;
		return res;
	}
	
	
}
