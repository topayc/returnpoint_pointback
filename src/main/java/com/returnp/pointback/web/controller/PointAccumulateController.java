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
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import com.returnp.pointback.common.AppConstants;
import com.returnp.pointback.common.DataMap;
import com.returnp.pointback.common.ResponseUtil;
import com.returnp.pointback.dto.CiderObject;
import com.returnp.pointback.dto.response.ReturnpBaseResponse;
import com.returnp.pointback.service.interfaces.AdminPointbackHandleService;
import com.returnp.pointback.service.interfaces.BasePointAccumulateService;
import com.returnp.pointback.service.interfaces.PointAccumulateService;
import com.returnp.pointback.service.interfaces.QRPointbackHandleService;
import com.returnp.pointback.service.interfaces.CiderPointbackHandleService;
import com.returnp.pointback.web.message.MessageUtils;

@Controller
@PropertySource("classpath:/config.properties")
public class PointAccumulateController extends ApplicationController{
	
	@Autowired PointAccumulateService pointBackService;
	@Autowired BasePointAccumulateService basePointAccumulateService;
	@Autowired MessageUtils messageUtils;
	@Autowired Environment env;
	@Autowired QRPointbackHandleService qrPointBackHandler;
	@Autowired AdminPointbackHandleService adminPointBackHandler;
	@Autowired CiderPointbackHandleService  saidaPointBackHandler;
	
	ArrayList<String> keys;
	ArrayList<Integer> pointAccList = new ArrayList<Integer>();
	
	private static final Logger logger = LoggerFactory.getLogger(PointAccumulateController.class);
	
	public static class Command {
		public static class Control{
			
		}
		public static class Request {
			public static final String  PAYMENT_APPROVAL = "PAYMENT_APPROVAL";
			public static final String PAYMENT_CANCEL = "PAYMENT_CANCEL";
		}
	}
	
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
	 * QR 코드 스캔으로 인한 적립 및 적립 취소 처리 
	 * KICC, 금결원등 여러 밴사의 QR 코드를 처리함  
	 */
	@ResponseBody
	@RequestMapping(value = "/qrAccumulatePoint", method = RequestMethod.GET)
	public ReturnpBaseResponse qrAccumulatePoint( 
			@RequestParam(value = "paymentRouterType", required = false) String paymentRouterType, 
			@RequestParam(value = "paymentRouterName", required = false ) String paymentRouterName, 
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
		
		logger.info("-----------------------PointAccumulateController.qrAccumulatePoint----------------------------");
		logger.info("Payemt Router Type  : " + paymentRouterType );
		logger.info("Payemt Rouert Name  : " + paymentRouterName );
		logger.info("------------------------------------------------------------------------------------------------------");
		
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
			
			dataMap.put("payment_router_type", paymentRouterType);
			dataMap.put("payment_router_name", paymentRouterName);
			dataMap.put("payment_transaction_type", AppConstants.PaymentTransactionType.QR);
			
			/*내부 형태대로 결제 번호 재 새성성*/
			dataMap.put("pan", (String)dataMap.get("af_id")+ "_" + (String)dataMap.getStr("pan")); 
			
			/*적립*/
			if (pas.equals("0")) {
				res = this.qrPointBackHandler.accumulate(dataMap);
			}
		
			/*적립 취소*/
			else if (pas.equals("1")) {
				res = this.qrPointBackHandler.cancelAccumulate(dataMap);
			}
		}
		return res;
	}
	
	/**
	 * 사이다 앱으로 부터 오는 적립 요청 처리 
	 * @param saida
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/ciderAccumulatePoint", method = RequestMethod.GET)
	public ReturnpBaseResponse saidaAccumulatePoint(
			@RequestParam(value = "userid", required = false) String userId, 
			@RequestParam(value = "goodname", required = false) String goodName, 
			@RequestParam(value = "price", required = false) int price, 
			@RequestParam(value = "recvphone", required = false) String recvPhone, 
			@RequestParam(value = "memo", required = false) String memo, 
			@RequestParam(value = "reqaddr", required = false) String reqAddr, 
			@RequestParam(value = "reqdate", required = false) Date reqDate, 
			@RequestParam(value = "pay_memo", required = false) String payMemo, 
			@RequestParam(value = "pay_addr", required = false) String payAddr, 
			@RequestParam(value = "pay_date", required = false) Date payDate, 
			@RequestParam(value = "pay_type", required = false) String payType, 
			@RequestParam(value = "pay_state", required = false) String payState,
			@RequestParam(value = "var1", required = false) String var1,
			@RequestParam(value = "var2", required = false) String var2,
			@RequestParam(value = "mul_no", required = false) String mulNo,
			@RequestParam(value = "payurl", required = false) String payUrl,
			@RequestParam(value = "csturl", required = false) String cstUrl,
			@RequestParam(value = "card_name", required = false) String cardName,
			@RequestParam(value = "payerPhone", required = false) String payerPhone
			) {
		System.out.println("####### PointAccumulateService.saidaAccumulatePoint ");
		ReturnpBaseResponse res= null;
		
		CiderObject cider = new CiderObject();
		cider.setUserId(userId);
		cider.setGoodName(goodName);
		cider.setPrice(price);
		cider.setRecvPhone(recvPhone);
		cider.setMemo(payMemo);
		cider.setReqAddr(reqAddr);
		cider.setReqDate(reqDate);
		cider.setPayMemo(payMemo);
		cider.setPayAddr(payAddr);
		cider.setPayDate(payDate);
		cider.setPayType(payType);
		cider.setPayState(payState);
		cider.setVar1(var1);
		cider.setVar2(var2);
		cider.setMulNo(mulNo);
		cider.setPayUrl(payUrl);
		cider.setCstUrl(cstUrl);
		cider.setCardName(cardName);
		cider.setPayerPhone(payerPhone);
		
		
		/*승인번호의 고유성을 확보하기 위해 승인번호를 주문번호를 통하여 재 생성 */
		cider.setPaymentApprovalNumber("CIDER" + cider.getMulNo() );
		
		/* URL 경로에 따라 세팅되는 라우팅 정보*/
		cider.setPaymentRouterName("CIDER");
		cider.setPaymentRouterType("PG");
		cider.setPaymentTransactionType(AppConstants.PaymentTransactionType.APP);
		
		/*적립*/
		if (cider.getPayState().equals("4")) {
			res = this.saidaPointBackHandler.accumulate(cider);
		} 
		/*적립 취소	*/
		else if (cider.getPayState().equals("9") || cider.getPayState().equals("64") || 
					cider.getPayState().equals("70") || cider.getPayState().equals("71")) {
			res = this.saidaPointBackHandler.cancelAccumulate(cider);
		}
		return res;
	}
			
	/**
	 * 관리자 시스템으로 부터 요청되는 적립 및 적립 취소를 처리 
	 */
	@ResponseBody
	@RequestMapping(value = "/manualAccumulatePoint", method = RequestMethod.GET)
	public ReturnpBaseResponse manualAccumulatePoint( 
			@RequestParam(value = "paymentRouterType", required = false ) String paymentRouterType, 
			@RequestParam(value = "paymentRouterName", required = false ) String paymentRouterName, 
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
		
		System.out.println("####### PointAccumulateService.manualAccumulatePoint ");
		ReturnpBaseResponse res= null;
		
		DataMap dataMap = new DataMap();
		if (!this.keys.contains(key)) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "306", this.messageUtils.getMessage("pointback.message.invalid_key"));
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
			
			dataMap.put("payment_router_type", paymentRouterType.trim());
			dataMap.put("payment_router_name", paymentRouterName.trim());
			dataMap.put("payment_transaction_type", AppConstants.PaymentTransactionType.MANUAL);

			if (phoneNumberCountry != null) {
				dataMap.put("phoneNumberCountry", phoneNumberCountry.trim());
			}
			dataMap.put("memberEmail", memberEmail);
			dataMap.put("key", key.trim());
			
			/*내부 형태대로 결제 번호 재 새성성*/
			dataMap.put("pan", (String)dataMap.get("af_id")+ "_" + (String)dataMap.getStr("pan")); 
			
			/*적립*/
			if (pas.equals("0")) {
				res = this.adminPointBackHandler.accumulate(dataMap);
			}
		
			/*적립 취소*/
			else if (pas.equals("1")) {
				res = this.adminPointBackHandler.cancelAccumulate(dataMap);
			}
		}
		return res;
	}
	
	
	/**
	 * PaymentTransactionNo 에 의한 적립 처리 (결제 요청이 유효한지도 검사한 후 재 적립을 진행)
	 * @param paymentTrasactionNo
	 * @param key
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/accumuateByPaymentTransactionNo", method = RequestMethod.GET)
	public ReturnpBaseResponse accumuateByPaymentTransactionNo( 
			@RequestParam(value = "paymentTrasactionNo", required = true ) int paymentTrasactionNo,  
			@RequestParam(value = "key", required = true ) String key
			){
		
		System.out.println("####### accumuateByPaymentTransactionNo");
		ReturnpBaseResponse res= null;
		if (!this.keys.contains(key)) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "306", this.messageUtils.getMessage("pointback.message.invalid_key"));
			return res;
		}else {
			res = this.adminPointBackHandler.accumuatePoint(paymentTrasactionNo);
		}
		
		return res;
	}
	
	/**
	 * 결제 승인 번호에 의한 의한 적립 처리 (결제 요청이 유효한지도 검사한 후 재 적립을 진행)
	 * @param paymentTrasactionNo
	 * @param key
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/accumuateByPan", method = RequestMethod.GET)
	public ReturnpBaseResponse accumuateByPan( 
			@RequestParam(value = "pan", required = true ) String pan,  
			@RequestParam(value = "key", required = true ) String key
			){
		
		System.out.println("####### accumuateByPan");
		ReturnpBaseResponse res= null;
		if (!this.keys.contains(key)) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "306", this.messageUtils.getMessage("pointback.message.invalid_key"));
			return res;
		}else {
			res = this.adminPointBackHandler.accumuatePoint(pan);
		}
		
		return res;
	}
	
	/**
	 * PaymentTransactionNo 에 의한 적립 취소 처리 
	 * @param paymentTrasactionNo
	 * @param key
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/cancelAccByPaymentTransactionNo", method = RequestMethod.GET)
	public ReturnpBaseResponse cancelAccumulateByPaymentTransactionNo( 
			@RequestParam(value = "paymentTransactionNo", required = true ) int paymentTrasactionNo,  
			@RequestParam(value = "key", required = true ) String key
			){
		
		logger.info("##### cancelAccumulateByPaymentTransactionNo");
		ReturnpBaseResponse res= null;
		if (!this.keys.contains(key)) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "306", this.messageUtils.getMessage("pointback.message.invalid_key"));
			return res;
		}else {
			res = this.adminPointBackHandler.cancelAccumuate(paymentTrasactionNo);
		}
		return res;
	}

	/**
	 * PaymentTransactionNo 에 의한 적립 강제 취소 처리 - 유효성 검사를 하지 않는 무조건 적인 취소 처리 
	 * @param paymentTrasactionNo
	 * @param key
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/forcedCancelAccByPaymentTransactionNo", method = RequestMethod.GET)
	public ReturnpBaseResponse forcedCancelAccByPaymentTransactionNo( 
			@RequestParam(value = "paymentTransactionNo", required = true ) int paymentTrasactionNo,  
			@RequestParam(value = "key", required = true ) String key
			){
		
		//System.out.println("####### forcedCancelAccByPaymentTransactionNo");
		ReturnpBaseResponse res= null;
		if (!this.keys.contains(key)) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "306", this.messageUtils.getMessage("pointback.message.invalid_key"));
			return res;
		}else {
			res = this.adminPointBackHandler.forcedCancelAccumuate(paymentTrasactionNo);
		}
		return res;
	}

	/**
	 * PaymentTransactionNo 에 의한 강제 적립 처리- 요청이 유효한지 검사하지 않고 무조건 적립 처리 해줌
	 * @param paymentTrasactionNo
	 * @param key
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/forcedlAccByPaymentTransactionNo", method = RequestMethod.GET)
	public ReturnpBaseResponse forcedlAccByPaymentTransactionNo( 
			@RequestParam(value = "paymentTransactionNo", required = true ) int paymentTrasactionNo,  
			@RequestParam(value = "key", required = true ) String key
			){
		
		//System.out.println("####### forcedlAccByPaymentTransactionNo");
		ReturnpBaseResponse res= null;
		if (!this.keys.contains(key)) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "306", this.messageUtils.getMessage("pointback.message.invalid_key"));
			return res;
		}else {
			res = this.adminPointBackHandler.forcedAccumuate(paymentTrasactionNo);
		}
		return res;
	}
	
	/**
	 * 결제 승인 번호(pan) 에 의한 적립 취소 처리
	 * @param paymentTrasactionNo
	 * @param key
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/cancelAccByPaymentApprovalNumber", method = RequestMethod.GET)
	public ReturnpBaseResponse cancelAccumulateByPaymentApprovalNumber( 
			@RequestParam(value = "pan", required = true ) String pan,  
			@RequestParam(value = "key", required = true ) String key
			){
		
		//System.out.println("####### cancelAccumulateByPaymentApprovalNumber");
		ReturnpBaseResponse res= null;
		
		if (!this.keys.contains(key)) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "306", this.messageUtils.getMessage("pointback.message.invalid_key"));
			return res;
		}else {
			res = this.adminPointBackHandler.cancelAccumuate(pan);
		}
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value = "/control", method = RequestMethod.GET)
	public ReturnpBaseResponse control(
		@RequestParam(value = "apiKey", required = true )String apiKey, 
		@RequestParam(value = "command", required = true ) String command ) {
		System.out.println("control");
		return null;
	}

	@ResponseBody
	@RequestMapping(value = "/test_mode", method = RequestMethod.GET)
	public ReturnpBaseResponse accumulateByAdmin() {
		ReturnpBaseResponse res = new ReturnpBaseResponse();
		this.setErrorRespone(res,"test 접속 성공");
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value = "/manageData", method = RequestMethod.GET)
	public ReturnpBaseResponse manageData() {
		ReturnpBaseResponse res = new ReturnpBaseResponse();
		return res;
	}
}
