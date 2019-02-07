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

import com.returnp.pointback.common.AppConstants.AccumulateRequestFrom;
import com.returnp.pointback.common.AppConstants;
import com.returnp.pointback.common.DataMap;
import com.returnp.pointback.common.ResponseUtil;
import com.returnp.pointback.dto.response.ReturnpBaseResponse;
import com.returnp.pointback.service.interfaces.BasePointAccumulateService;
import com.returnp.pointback.service.interfaces.PointAccumulateService;
import com.returnp.pointback.web.message.MessageUtils;

@Controller
@PropertySource("classpath:/config.properties")
public class PointAccumulateController extends ApplicationController{
	
	@Autowired PointAccumulateService pointBackService;
	@Autowired BasePointAccumulateService basePointAccumulateService;
	@Autowired MessageUtils messageUtils;
	@Autowired Environment env;
	
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
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
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
	
	@ResponseBody
	@RequestMapping(value = "/control", method = RequestMethod.GET)
	public ReturnpBaseResponse control(
		@RequestParam(value = "apiKey", required = true )String apiKey, 
		@RequestParam(value = "command", required = true ) String command ) {
		System.out.println("control");
		return null;
	}
	
	/**
	 * 인크립트된 QR 코드 스캔에 의한  포인트 백 적립 및 취소 요청 처리 
	 * @param command
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/QR/iD", method = RequestMethod.GET)
	public ReturnpBaseResponse accumulateByEncrptQr(
		@RequestParam(value = "qr_org", required = true ) String qrOrg, 
		@RequestParam(value = "pam", required = true ) int pam,
		@RequestParam(value = "pas", required = true ) String pas,
		@RequestParam(value = "pat", required = true ) Date pat,
		@RequestParam(value = "pan", required = true ) String pan,
		@RequestParam(value = "af_id", required = true ) String afId,
		@RequestParam(value = "phoneNumber", required = true ) String phoneNumber,
		@RequestParam(value = "phoneNumberCountry", required = true ) String phoneNumberCountry,
		@RequestParam(value = "memberEmail", required = true ) String memberEmail,
		@RequestParam(value = "key", required = true ) String key
		){
		
		/*System.out.println("excuteByEncrptQr");
		System.out.println(qrOrg);
		System.out.println(pam);
		System.out.println(pas);
		System.out.println(pan);
		System.out.println(afId);
		System.out.println(phoneNumber);
		System.out.println(phoneNumberCountry);*/
		
		ReturnpBaseResponse res = null;
		if (this.keys.contains(key)) {
			res = this.pointBackService.processByEncQr(
				qrOrg, 
				pam, 
				pas,
				pat, 
				pan, 
				afId, 
				memberEmail, 
				phoneNumber,
				phoneNumberCountry
			);
		}else {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, "306", this.messageUtils.getMessage("pointback.message.invalid_key"));
		}
		return res;
	}
	
	/**
	 * 내부 관리자 어드민에서 결제 내역을 수동 생성함로서 호출되는  포인트 백 적립 및 취소 요청 처리 
	 * 적립 실패된 내역에 대해서 재 적립 요청도 처리 
	 * @param command
	 * @param paymentTransactionNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/accumulateByAdmin", method = RequestMethod.GET)
	public ReturnpBaseResponse accumulateByAdmin(
		@RequestParam(value = "cmd", required = true ) String command, 
		@RequestParam(value = "paymentTransactionNo", required = true ) int paymentTransactionNo,
		@RequestParam(value = "key", required = true ) String key) {
		
		System.out.println("PointAccumulateController - accumulateByAdmin");
		ReturnpBaseResponse res = new ReturnpBaseResponse();
		if (!this.keys.contains(key)) {
			ResponseUtil.setResponse(res, "306", this.messageUtils.getMessage("pointback.message.invalid_key"));
			return res;
		}
		res = this.pointBackService.processByAdmin(paymentTransactionNo);
		/*		
 		switch(command) {
			case Command.Request.PAYMENT_APPROVAL:
				if (pointAccList.contains((Integer)paymentTransactionNo)) {
					this.setErrorRespone(res,"현재 Green Point가 적립중인 요청");
					this.setErrorRespone(res,this.messageUtils.getMessage("pointback.message.processing_acc_ok"));
				}else {
					pointAccList.add((Integer)paymentTransactionNo);
					res = this.pointBackService.excuteGreenPointAccProcess(paymentTransactionNo);
					pointAccList.remove((Integer)paymentTransactionNo);
				}
				res = this.pointBackService.accumulateByAdmin(paymentTransactionNo);
			break;
			
			case Command.Request.PAYMENT_CANCEL:
				res = this.pointBackService.cancelGreenPointAccProcess(paymentTransactionNo);
			break;
		}
		*/
		return res;
	}

	/********************************************************************************************************************************************************************
	 * 기존에은 위에 컨트롤러 메서드를 사용했으나. 
	 * 아래의 신규 추가된 메서드로 이후 처리할 예정이며, 완료되면 이전 메서드는 삭제 
	 * 앞으로은 아래의 컨트롤러를 이용하도록 관련 소스를 수정해야 함 
	 * 
	 ********************************************************************************************************************************************************************/
	
	/**
	 * 새로 추가된 메서드 관리자에서 수동으로 매출및 적립을 발생
	 * 관리자에서 호출은 이 메서드로 단일화 함 
	 * 적립 및 취소에 대한 처리 컨트롤러
	 * @param qr_org
	 * @param pam
	 * @param pas
	 * @param pat
	 * @param pan
	 * @param afId
	 * @param phoneNumber
	 * @param phoneNumberCountry
	 * @param memberEmail
	 * @param key
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/manualAccumulatePoint", method = RequestMethod.GET)
	public ReturnpBaseResponse manualAccumulatePoint( 
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
		
		System.out.println("####### manualAccumulatePoint");
		ReturnpBaseResponse res= null;
		
		DataMap dataMap = new DataMap();
		if (!this.keys.contains(key)) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, "306", this.messageUtils.getMessage("pointback.message.invalid_key"));
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
			dataMap.put("acc_from", AppConstants.PaymentTransactionType.ADMIN);
			
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
	 * 새로 추가된 메서드로 qr 로 부터 매출 및 적립 처리 
	 * qr 에서 호출은 이 메서드로 단일화 함 
	 * @param qr_org
	 * @param pam
	 * @param pas
	 * @param pat
	 * @param pan
	 * @param afId
	 * @param phoneNumber
	 * @param phoneNumberCountry
	 * @param memberEmail
	 * @param key
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/qrAccumulatePoint", method = RequestMethod.GET)
	public ReturnpBaseResponse handleAccumulateQRRequest( 
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
		//System.out.println("####### qrAccumulatePoint");
		ReturnpBaseResponse res= null;
		
		DataMap dataMap = new DataMap();
		if (!this.keys.contains(key)) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, "306", this.messageUtils.getMessage("pointback.message.invalid_key"));
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
			dataMap.put("acc_from", AppConstants.PaymentTransactionType.QR);
			
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
	 * PaymentTransactionNo 에 의한 적립 처리 
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
			ResponseUtil.setResponse(res, "306", this.messageUtils.getMessage("pointback.message.invalid_key"));
			return res;
		}else {
			res = this.basePointAccumulateService.accumuatePoint(paymentTrasactionNo);
		}
		
		return res;
	}
	
	/**
	 * 결제 승인 번호에 의한 의한 적립 처리 
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
			ResponseUtil.setResponse(res, "306", this.messageUtils.getMessage("pointback.message.invalid_key"));
			return res;
		}else {
			res = this.basePointAccumulateService.accumuatePoint(pan);
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
		
		System.out.println("####### cancelAccumulateByPaymentTransactionNo");
		ReturnpBaseResponse res= null;
		if (!this.keys.contains(key)) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, "306", this.messageUtils.getMessage("pointback.message.invalid_key"));
			return res;
		}else {
			res = this.basePointAccumulateService.cancelAccumuate(paymentTrasactionNo);
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
		
		System.out.println("####### cancelAccumulateByPaymentApprovalNumber");
		ReturnpBaseResponse res= null;
		
		if (!this.keys.contains(key)) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, "306", this.messageUtils.getMessage("pointback.message.invalid_key"));
			return res;
		}else {
			res = this.basePointAccumulateService.cancelAccumuate(pan);
		}
		return res;
	}
	
	@ResponseBody
	@RequestMapping(value = "/test_mode", method = RequestMethod.GET)
	public ReturnpBaseResponse accumulateByAdmin() {
		ReturnpBaseResponse res = new ReturnpBaseResponse();
		this.setErrorRespone(res,"test 접속 성공");
		return res;
	}
}
