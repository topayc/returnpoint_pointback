package com.returnp.pointback.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;

import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.propertyeditors.CustomDateEditor;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.WebDataBinder;
import org.springframework.web.bind.annotation.InitBinder;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.returnp.pointback.common.AppConstants;
import com.returnp.pointback.common.DataMap;
import com.returnp.pointback.dto.command.api.ApiRequest;
import com.returnp.pointback.dto.response.ReturnpBaseResponse;
import com.returnp.pointback.service.EncryptService;
import com.returnp.pointback.service.interfaces.ApiService;
import com.returnp.pointback.service.interfaces.BasePointAccumulateService;
import com.returnp.pointback.web.message.MessageUtils;
import com.returnp.pointback.web.validator.ApiRequestValidator;

@Controller
@RequestMapping("/v1/api")
public class ApiController extends ApplicationController{
	
	private static final Logger logger = LoggerFactory.getLogger(ApiController.class);
	
	@Autowired MessageUtils messageUtils;
	@Autowired Environment env;
	@Autowired  ApiRequestValidator apiRequestValidator;
	@Autowired  ApiService apiService;
	@Autowired BasePointAccumulateService basePointAccumulateService;
	@Autowired EncryptService encryptService;
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
	}
	
	/**
	 * 요청된 데이터에 대한 임시 저장 
	 * 분산 서버에서 사용하는 일종의 캐시 컨트롤러
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save_cache_data", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	public String saveDataCache(ApiRequest apiRequest, HttpSession session) {
		System.out.println("## saveDataCache " );
		String key = "11121212";
		String result = this.encryptService.encrypt(key, this.apiService.saveDataCache(apiRequest, session));
		System.out.println("- 암호화 응답 " );
		System.out.println(result );
		return result;
	}
	
	/**
	 * 캐시된 데이타 가져오기
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get_cache_data", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	public String getDataCache(ApiRequest apiRequest,HttpSession session) {
		System.out.println("## getDataCache " );
		String key = "aes256-test-key!!";
		String result = this.encryptService.encrypt(key, this.apiService.getDataCache(apiRequest, session));
		System.out.println("- 암호화 응답 " );
		System.out.println(result );
		return result;
	}
	
	/*
	 * 회원 정보 가져오기
	 * @return
	 * @throws JsonProcessingException 
	 */
	@ResponseBody
	@RequestMapping(value = "/get_member_info", method = RequestMethod.GET ,produces="text/html;charset=UTF-8" )
	public  String getMemberInfo(ApiRequest apiRequest) throws JsonProcessingException {
		System.out.println("## getMemberInfo " );
		String key = "1123456789123456";
		String result = this.encryptService.encrypt(key, this.apiService.getMemberInfo(apiRequest));
		System.out.println("- 암호화 응답 " );
		System.out.println(result );
		return result;
	}
	
	/**
	 * 중복 검사 처리 
	 * 전화번호, 이메일, 이름 등  
	 * 1 : 이메일 중복 여부
	 * 2 : 전화번호 중복 여부 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/check_duplicated", method = RequestMethod.GET , produces="text/html;charset=UTF-8")
	public String checkDuplicated(ApiRequest apiRequest) {
		System.out.println("## checkDuplicated " );
		String key = "11121212";
		String result = this.encryptService.encrypt(key, this.apiService.checkDuplicated(apiRequest));
		System.out.println("- 암호화 응답 " );
		System.out.println(result );
		return result;
	}
	
	
	/**
	 * 회원 가입
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/join_up", method = RequestMethod.POST,produces="text/html;charset=UTF-8" )
	public String join(ApiRequest apiRequest) {
		System.out.println("## join " );
		String key = "11121212";
		String result = this.encryptService.encrypt(key, this.apiService.join(apiRequest));
		System.out.println("- 암호화 응답 " );
		System.out.println(result );
		return result;
	}
	
	/**
	 * 회원 삭제
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete_member", method = RequestMethod.POST,produces="text/html;charset=UTF-8" )
	public String deleteMember(ApiRequest apiRequest) {
		System.out.println("## deleteMember " );
		String key = "11121212";
		String result = this.encryptService.encrypt(key, this.apiService.deleteMember(apiRequest));
		System.out.println("- 암호화 응답 " );
		System.out.println(result );
		return result;
	}
	
	/**
	 * 회원 정보 수정 및 업데이트 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/modify_member", method = RequestMethod.POST,produces="text/html;charset=UTF-8" )
	public String modifyMember(ApiRequest apiRequest) {
		System.out.println("## modifyMember " );
		String key = "11121212";
		String result = this.encryptService.encrypt(key, this.apiService.modifyMember(apiRequest));
		System.out.println("- 암호화 응답 " );
		System.out.println(result );
		return result;
	}
	
	/**
	 * 포인트 적립 및 취소 처리에 대한 핸들러  
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/handle_accumulate", method = RequestMethod.POST,produces="text/html;charset=UTF-8" )
	public String executeAccumualte(ApiRequest apiRequest) {
		System.out.println("## executeAccumualte " );
		ReturnpBaseResponse res= null;
		DataMap dataMap = new DataMap();
		
		if (apiRequest.getQrOrg() != null) {
			dataMap.put("qr_org", apiRequest.getQrOrg().trim());
		}
		dataMap.put("pam", apiRequest.getPaymentApprovalAmount());
		dataMap.put("pas", apiRequest.getPaymentApprovalStatus().trim());
		
		dataMap.put("pat", (apiRequest.getPaymentApprovalDateTime() == null ? new Date(): apiRequest.getPaymentApprovalDateTime()));
		dataMap.put("pan", apiRequest.getPaymentApprovalNumber().trim());
		dataMap.put("af_id", apiRequest.getAfId().trim());
		dataMap.put("phoneNumber", apiRequest.getMemberPhone().trim());
		dataMap.put("phoneNumberCountry", apiRequest.getMemberPhone().trim());
		dataMap.put("memberEmail", apiRequest.getMemberEmail());
		dataMap.put("key", apiRequest.getApiKey());
		dataMap.put("acc_from", AppConstants.PaymentTransactionType.SHOPPING_MAL);
		
		/*적립*/
		if (apiRequest.getPaymentApprovalStatus().equals("0")) {
			res = this.basePointAccumulateService.accumulate(dataMap);
		}
		/*적립 취소*/
		else if (apiRequest.getPaymentApprovalStatus().equals("1")) {
			res = this.basePointAccumulateService.cancelAccumulate(dataMap);
		}
		
		String key = "11121212";
		String result = this.encryptService.encrypt(key, res);
		System.out.println("- 암호화 응답 " );
		System.out.println(result );
		return result;
	}
	
	/**
	 * 결제 번호에 의한 적립
	 * 이 경우는 이미 거래 내역을 생성했으나, 적립에 문제가 있을 경우 재 적립 처리를 함 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/accumulage_by_pan", method = RequestMethod.POST,produces="text/html;charset=UTF-8" )
	public String accumulateByPan(ApiRequest apiRequest) {
		System.out.println("## accumulateByPan " );
		String key = "11121212";
		String result = 
			this.encryptService.encrypt(
				key, this.basePointAccumulateService.accumuatePoint(apiRequest.getPaymentApprovalNumber()));
		System.out.println("- 암호화 응답 " );
		System.out.println(result );
		return result;
		
	}
	
	/**
	 * 결제 번호의 의한 적립 취소
	 * 기존 포인트 적립된 것을 모두 되돌리고, 같은 결제 번호로 결제 취소 내역과  
	 * 포인트 취소 내역을 생성  
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/cancel_accumulate_by_pan", method = RequestMethod.POST,produces="text/html;charset=UTF-8" )
	public String cancelAccumulatgeByPan(ApiRequest apiRequest) {
		System.out.println("## cancelAccumulatgeByPan " );
		String key = "11121212";
		String result = 
			this.encryptService.encrypt(
				key, this.basePointAccumulateService.cancelAccumuate(apiRequest.getPaymentApprovalNumber()));
		System.out.println("- 암호화 응답 " );
		System.out.println(result );
		return result;
	}
	
	/**
	 * 지원 언어 조회
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/langs", method = RequestMethod.POST,produces="text/html;charset=UTF-8" )
	public String getLangs(ApiRequest apiRequest) {
		System.out.println("## getLangs " );
		String key = "11121212";
		String result = this.encryptService.encrypt(key, this.apiService.getLanguages(apiRequest));
		System.out.println("- 암호화 응답 " );
		System.out.println(result );
		return result;
	}
	
	/**
	 * 은행 계좌 리스트 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get_bank_accounts", method = RequestMethod.GET,produces="text/html;charset=UTF-8" )
	public String getMemberBankAccounts(ApiRequest apiRequest) {
		System.out.println("## getMemberBankAccounts " );
		String key = "11121212";
		String result = this.encryptService.encrypt(key, this.apiService.getBankAccounts(apiRequest));
		System.out.println("- 암호화 응답 " );
		System.out.println(result );
		return result;
	}
	
	/**
	 * 은행 계좌 등록
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/register_bank_account", method = RequestMethod.POST,produces="text/html;charset=UTF-8" )
	public String registerBankAccount(ApiRequest apiRequest) {
		System.out.println("## registerBankAccount " );
		String key = "11121212";
		String result = this.encryptService.encrypt(key, this.apiService.registerBankAccount(apiRequest));
		System.out.println("- 암호화 응답 " );
		System.out.println(result );
		return result;
	}
	
	/**
	 * 은행 계좌 수정
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update_bank_account", method = RequestMethod.POST,produces="text/html;charset=UTF-8" )
	public String updateBankAccount(ApiRequest apiRequest) {
		System.out.println("## updateBankAccount " );
		String key = "11121212";
		String result = this.encryptService.encrypt(key, this.apiService.updateBankAccount(apiRequest));
		System.out.println("- 암호화 응답 " );
		System.out.println(result );
		return result;
	}
	
	/**
	 * 은행 계좌 삭제
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete_bank_account", method = RequestMethod.GET,produces="text/html;charset=UTF-8" )
	public String deleteBankAccount(ApiRequest apiRequest) {
		System.out.println("## deleteBankAccount " );
		String key = "11121212";
		String result = this.encryptService.encrypt(key, this.apiService.deleteBankAccount(apiRequest));
		System.out.println("- 암호화 응답 " );
		System.out.println(result );
		return result;
	}
	
	/**
	 * 정책 제공
	 * 가맹 쇼핑몰에서 구매히 결제 적립율을 알 필요가 있기 때문에 제공 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get_policy", method = RequestMethod.POST,produces="text/html;charset=UTF-8" )
	public String getPolicy(ApiRequest apiRequest) {
		System.out.println("## getPolicy " );
		String key = "11121212";
		String result = this.encryptService.encrypt(key, this.apiService.getPolicy(apiRequest));
		System.out.println("- 암호화 응답 " );
		System.out.println(result );
		return result;
	}
	
	
	/**
	 * G POINT 	 적립 내역
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/gpoint_accumulate_history", method = RequestMethod.POST,produces="text/html;charset=UTF-8" )
	public String getRpointAccumuateHistory(ApiRequest apiRequest) {
		return null;
	}
	
	/**
	 * R POINT  전환 내역
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/rpoint_conversion_history", method = RequestMethod.POST,produces="text/html;charset=UTF-8" )
	public String getRpointConversionHistory(ApiRequest apiRequest) {
		return null;
	}
	
	/**
	 * R PAY 출금 신청 하기 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/withdrawal_rpay", method = RequestMethod.POST,produces="text/html;charset=UTF-8" )
	public String withdrawalRpay(ApiRequest apiRequest) {
		System.out.println("## withdrawalRpay " );
		String key = "11121212";
		String result = this.encryptService.encrypt(key, this.apiService.withdrawaPoint(apiRequest));
		System.out.println("- 암호화 응답 " );
		System.out.println(result );
		return result;
	}
	
	/**
	 * 출금 신청 목록 가져오기
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/withdrawal_rpay_history", method = RequestMethod.GET,produces="text/html;charset=UTF-8" )
	public String getPointWithdrawals(ApiRequest apiRequest) {
		System.out.println("## getPointWithdrawals " );
		String key = "11121212";
		String result = this.encryptService.encrypt(key, this.apiService.getPointwithdrawals(apiRequest));
		System.out.println("- 암호화 응답 " );
		System.out.println(result );
		return result;
	}
	
	/**
	 * 나의 회원 목록 가져오기
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get_my_members", method = RequestMethod.GET,produces="text/html;charset=UTF-8" )
	public String getMyMembers(ApiRequest apiRequest) {
		System.out.println("## ApiRequest " );
		String key = "11121212";
		String result = this.encryptService.encrypt(key, this.apiService.getMyMembers(apiRequest));
		System.out.println("- 암호화 응답 " );
		System.out.println(result );
		return result;
	}
	
}
