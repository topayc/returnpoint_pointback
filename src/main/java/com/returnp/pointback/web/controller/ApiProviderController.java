package com.returnp.pointback.web.controller;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;

import javax.servlet.http.HttpSession;

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

import com.returnp.pointback.common.AppConstants;
import com.returnp.pointback.common.DataMap;
import com.returnp.pointback.common.ResponseUtil;
import com.returnp.pointback.dao.mapper.ApiMapper;
import com.returnp.pointback.dto.command.api.ApiRequest;
import com.returnp.pointback.dto.response.ReturnpBaseResponse;
import com.returnp.pointback.dto.response.StringResponse;
import com.returnp.pointback.service.ApiResponseService;
import com.returnp.pointback.service.interfaces.ApiServiceProvider;
import com.returnp.pointback.service.interfaces.BasePointAccumulateService;
import com.returnp.pointback.web.message.MessageUtils;
import com.returnp.pointback.web.validator.ApiRequestValidator;

@Controller
@RequestMapping("/v1/api")
public class ApiProviderController extends ApplicationController{
	
	private static final Logger logger = LoggerFactory.getLogger(ApiProviderController.class);
	
	@Autowired MessageUtils messageUtils;
	@Autowired Environment env;
	@Autowired ApiRequestValidator apiRequestValidator;
	@Autowired ApiServiceProvider apiServiceProvider;
	@Autowired BasePointAccumulateService basePointAccumulateService;
	@Autowired ApiResponseService apiResponseService;
	@Autowired ApiMapper apiMapper;
	public static final String DEFAULT_KEY = "qwertyuiopasdfghjklzxcvbnm123456";
	
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
	public ReturnpBaseResponse  saveDataCache(ApiRequest apiRequest, HttpSession session) {
		System.out.println("## saveDataCache " );
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, "550", this.messageUtils.getMessage("api.message.wrong_sfid_wrong_key"));
		}else {
			res = this.apiServiceProvider.saveDataCache(apiRequest, session);
		}
		return res;
	}
	
	/**
	 * 캐시된 데이타 가져오기
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get_cache_data", method = RequestMethod.POST, produces="text/html;charset=UTF-8")
	public ReturnpBaseResponse getDataCache(ApiRequest apiRequest,HttpSession session) {
		System.out.println("## getDataCache " );
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, "550", this.messageUtils.getMessage("api.message.wrong_sfid_wrong_key"));
		}else {
			res = this.apiServiceProvider.getDataCache(apiRequest, session);
		}
		return res;
	}
	
	/*
	 * 회원 정보 가져오기
	 * @return
	 * @throws JsonProcessingException 
	 */
	@ResponseBody
	@RequestMapping(value = "/get_member_info", method = RequestMethod.GET ,produces="application/json" )
	public  ReturnpBaseResponse getMemberInfo(ApiRequest apiRequest) {
		System.out.println("## getMemberInfo " );
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		/*if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, "550", this.messageUtils.getMessage("api.message.wrong_sfid_wrong_key"));
			return res;
		}else {*/
			res = this.apiServiceProvider.getMemberInfo(apiRequest);
			StringResponse res2 = this.apiResponseService.generateResponse(res, "ZFYzH5HOffrXc6MV2H4+HRD0Z6g1qmRw");
			//return this.apiResponseService.generateResponse(res, (String)apiServiceMap.get("apiKey"));
			String data = apiResponseService.decode(res2.getData(),  "ZFYzH5HOffrXc6MV2H4+HRD0Z6g1qmRw");
			res2.setData(data);
			return res2;
			//return this.apiResponseService.generateResponse(res, (String)apiServiceMap.get("apiKey"));
	//	}
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
	public ReturnpBaseResponse checkDuplicated(ApiRequest apiRequest) {
		System.out.println("## checkDuplicated " );
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, "550", this.messageUtils.getMessage("api.message.wrong_sfid_wrong_key"));
		}else {
			res = this.apiServiceProvider.checkDuplicated(apiRequest);
		}
		return res;
	}
	
	
	/**
	 * 회원 가입
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/join_up", method = RequestMethod.POST,produces="text/html;charset=UTF-8" )
	public ReturnpBaseResponse join(ApiRequest apiRequest) {
		System.out.println("## join " );
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, "550", this.messageUtils.getMessage("api.message.wrong_sfid_wrong_key"));
		}else {
			res = this.apiServiceProvider.join(apiRequest);
		}
		return res;
	}
	
	/**
	 * 회원 삭제
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete_member", method = RequestMethod.POST,produces="text/html;charset=UTF-8" )
	public ReturnpBaseResponse deleteMember(ApiRequest apiRequest) {
		System.out.println("## deleteMember " );
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, "550", this.messageUtils.getMessage("api.message.wrong_sfid_wrong_key"));
		}else {
			res = this.apiServiceProvider.deleteMember(apiRequest);
		}
		return res;
	}
	
	/**
	 * 회원 정보 수정 및 업데이트 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/modify_member", method = RequestMethod.POST,produces="text/html;charset=UTF-8" )
	public ReturnpBaseResponse modifyMember(ApiRequest apiRequest) {
		System.out.println("## modifyMember " );
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, "550", this.messageUtils.getMessage("api.message.wrong_sfid_wrong_key"));
		}else {
			res = this.apiServiceProvider.modifyMember(apiRequest);
		}
		return res;
	}
	
	/**
	 * 포인트 적립 및 취소 처리에 대한 핸들러  
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/handle_accumulate", method = RequestMethod.POST,produces="text/html;charset=UTF-8" )
	public ReturnpBaseResponse executeAccumualte(ApiRequest apiRequest) {
		System.out.println("## executeAccumualte " );
		
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		DataMap dataMap = new DataMap();

		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, "550", this.messageUtils.getMessage("api.message.wrong_sfid_wrong_key"));
		}else {
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
		}
		return res;
	}
	
	/**
	 * 결제 번호에 의한 적립
	 * 이 경우는 이미 거래 내역을 생성했으나, 적립에 문제가 있을 경우 재 적립 처리를 함 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/accumulage_by_pan", method = RequestMethod.POST,produces="text/html;charset=UTF-8" )
	public ReturnpBaseResponse accumulateByPan(ApiRequest apiRequest) {
		System.out.println("## accumulateByPan " );
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, "550", this.messageUtils.getMessage("api.message.wrong_sfid_wrong_key"));
		}else {
			res = this.basePointAccumulateService.accumuatePoint(apiRequest.getPaymentApprovalNumber());
		}
		return res;
	}
	
	/**
	 * 결제 번호의 의한 적립 취소
	 * 기존 포인트 적립된 것을 모두 되돌리고, 같은 결제 번호로 결제 취소 내역과  
	 * 포인트 취소 내역을 생성  
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/cancel_accumulate_by_pan", method = RequestMethod.POST,produces="text/html;charset=UTF-8" )
	public ReturnpBaseResponse cancelAccumulatgeByPan(ApiRequest apiRequest) {
		System.out.println("## cancelAccumulatgeByPan " );
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, "550", this.messageUtils.getMessage("api.message.wrong_sfid_wrong_key"));
		}else {
			res = this.basePointAccumulateService.cancelAccumuate(apiRequest.getPaymentApprovalNumber());
		}
		return res;
	}
	
	/**
	 * 지원 언어 조회
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/langs", method = RequestMethod.POST,produces="text/html;charset=UTF-8" )
	public ReturnpBaseResponse getLangs(ApiRequest apiRequest) {
		System.out.println("## getLangs " );
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, "550", this.messageUtils.getMessage("api.message.wrong_sfid_wrong_key"));
		}else {
			res = this.apiServiceProvider.getLanguages(apiRequest);
		}
		return res;
	}
	
	/**
	 * 은행 계좌 리스트 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get_bank_accounts", method = RequestMethod.GET,produces="text/html;charset=UTF-8" )
	public ReturnpBaseResponse getMemberBankAccounts(ApiRequest apiRequest) {
		System.out.println("## getMemberBankAccounts " );
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, "550", this.messageUtils.getMessage("api.message.wrong_sfid_wrong_key"));
		}else {
			res = this.apiServiceProvider.getBankAccounts(apiRequest);
		}
		return res;
	}
	
	/**
	 * 은행 계좌 등록
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/register_bank_account", method = RequestMethod.POST,produces="text/html;charset=UTF-8" )
	public ReturnpBaseResponse registerBankAccount(ApiRequest apiRequest) {
		System.out.println("## registerBankAccount " );
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, "550", this.messageUtils.getMessage("api.message.wrong_sfid_wrong_key"));
		}else {
			res = this.apiServiceProvider.registerBankAccount(apiRequest);
		}
		return res;
	}
	
	/**
	 * 은행 계좌 수정
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update_bank_account", method = RequestMethod.POST,produces="text/html;charset=UTF-8" )
	public ReturnpBaseResponse updateBankAccount(ApiRequest apiRequest) {
		System.out.println("## updateBankAccount " );
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, "550", this.messageUtils.getMessage("api.message.wrong_sfid_wrong_key"));
		}else {
			res = this.apiServiceProvider.updateBankAccount(apiRequest);
		}
		return res;
	}
	
	/**
	 * 은행 계좌 삭제
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete_bank_account", method = RequestMethod.GET,produces="text/html;charset=UTF-8" )
	public ReturnpBaseResponse deleteBankAccount(ApiRequest apiRequest) {
		System.out.println("## deleteBankAccount " );
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, "550", this.messageUtils.getMessage("api.message.wrong_sfid_wrong_key"));
		}else {
			res = this.apiServiceProvider.deleteBankAccount(apiRequest);
		}
		return res;
	}
	
	/**
	 * 정책 제공
	 * 가맹 쇼핑몰에서 구매히 결제 적립율을 알 필요가 있기 때문에 제공 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get_policy", method = RequestMethod.POST,produces="text/html;charset=UTF-8" )
	public ReturnpBaseResponse getPolicy(ApiRequest apiRequest) {
		System.out.println("## getPolicy " );
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, "550", this.messageUtils.getMessage("api.message.wrong_sfid_wrong_key"));
		}else {
			res = this.apiServiceProvider.getPolicy(apiRequest);
		}
		return res;
	}
	
	
	/**
	 * G POINT 	 적립 내역
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/gpoint_accumulate_history", method = RequestMethod.POST,produces="text/html;charset=UTF-8" )
	public ReturnpBaseResponse getRpointAccumuateHistory(ApiRequest apiRequest) {
		return null;
	}
	
	/**
	 * R POINT  전환 내역
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/rpoint_conversion_history", method = RequestMethod.POST,produces="text/html;charset=UTF-8" )
	public ReturnpBaseResponse getRpointConversionHistory(ApiRequest apiRequest) {
		return null;
	}
	
	/**
	 * R PAY 출금 신청 하기 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/withdrawal_rpay", method = RequestMethod.POST,produces="text/html;charset=UTF-8" )
	public ReturnpBaseResponse withdrawalRpay(ApiRequest apiRequest) {
		System.out.println("## withdrawalRpay " );
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, "550", this.messageUtils.getMessage("api.message.wrong_sfid_wrong_key"));
		}else {
			res = this.apiServiceProvider.withdrawaPoint(apiRequest);
		}
		return res;
	}
	
	/**
	 * 출금 신청 목록 가져오기
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/withdrawal_rpay_history", method = RequestMethod.GET,produces="text/html;charset=UTF-8" )
	public ReturnpBaseResponse getPointWithdrawals(ApiRequest apiRequest) {
		System.out.println("## getPointWithdrawals " );
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, "550", this.messageUtils.getMessage("api.message.wrong_sfid_wrong_key"));
		}else {
			res = this.apiServiceProvider.getPointwithdrawals(apiRequest);
		}
		return res;
	}
	
	/**
	 * 나의 회원 목록 가져오기
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get_my_members", method = RequestMethod.GET,produces="text/html;charset=UTF-8" )
	public ReturnpBaseResponse getMyMembers(ApiRequest apiRequest) {
		System.out.println("## getMyMembers " );
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, "550", this.messageUtils.getMessage("api.message.wrong_sfid_wrong_key"));
		}else {
			res = this.apiServiceProvider.getMyMembers(apiRequest);
		}
		return res;
	}
}
