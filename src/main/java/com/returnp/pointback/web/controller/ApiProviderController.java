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
import com.returnp.pointback.service.interfaces.ApiPointbackHandleService;
import com.returnp.pointback.service.interfaces.ApiResponseService;
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
	@Autowired ApiPointbackHandleService apiPointbackHandleService;
	public static final String DEFAULT_KEY = "qwertyuiopasdfghjklzxcvbnm123456";
	
	@InitBinder
	public void initBinder(WebDataBinder binder) {
		SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");
		binder.registerCustomEditor(Date.class, new CustomDateEditor(df, false));
	}
	
	/**
	 * 요청된 데이터에 대한 임시 저장 
	 * 분산 서버에서 사용하는 일종의 캐시 컨트롤러
	 * 
	 * cacheKey
	 * cacheData
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/save_cache_data", method = RequestMethod.POST , produces="application/json")
	public ReturnpBaseResponse  saveDataCache(ApiRequest apiRequest, HttpSession session) {
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "301", this.messageUtils.getMessage("api.message.wrong_afid_wrong_key"));
		}else {
			res = this.apiServiceProvider.saveDataCache(apiRequest, session);
		}
		
		StringResponse stringRes  = this.apiResponseService.generateResponse(res, (String)apiServiceMap.get("apiKey"));
		return stringRes;
	}
	
	/**
	 * 캐시된 데이타 가져오기
	 * cacheKey
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get_cache_data", method = RequestMethod.GET, produces="application/json")
	public ReturnpBaseResponse getDataCache(ApiRequest apiRequest,HttpSession session) {
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "301", this.messageUtils.getMessage("api.message.wrong_afid_wrong_key"));
			return res;
		}else {
			res = this.apiServiceProvider.getDataCache(apiRequest, session);
			StringResponse stringRes  = this.apiResponseService.generateResponse(res, (String)apiServiceMap.get("apiKey"));
			return stringRes;
		}
	}
	
	/**
	 * 회원 정보 가져오기
	 * memberEmail
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get_member_info", method = RequestMethod.GET ,produces="application/json" )
	public  ReturnpBaseResponse getMemberInfo(ApiRequest apiRequest) {
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "301", this.messageUtils.getMessage("api.message.wrong_afid_wrong_key"));
			return res;
		}else {
			//System.out.println(apiServiceMap.get("apiKey"));
			res = this.apiServiceProvider.getMemberInfo(apiRequest);
			StringResponse stringRes  = this.apiResponseService.generateResponse(res, (String)apiServiceMap.get("apiKey"));
			//String data = apiResponseService.decode(stringRes.getData(),  (String)apiServiceMap.get("apiKey"));
			//stringRes.setData(data);
			return stringRes;
		}
	}
	
	
	/**
	 * 중복 검사 처리, 등록되어 있는지 여부 
	 * checkExistType ( email : 이메일 중복 여부 , phone : 전화번호 중복 여부 ) 
	 * memberEmail, memberPhone
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/is_registered", method = RequestMethod.GET , produces="application/json")
	public ReturnpBaseResponse isRegistered(ApiRequest apiRequest) {
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "301", this.messageUtils.getMessage("api.message.wrong_afid_wrong_key"));
			return res;
		}else {
			res = this.apiServiceProvider.isRegistered(apiRequest);
			StringResponse stringRes  = this.apiResponseService.generateResponse(res, (String)apiServiceMap.get("apiKey"));
			return stringRes;
		}
	}
	
	/**
	 * 회원 가입
	 * memberEmail
	 * memberName
	 * memberPassword
	 * memberPassword2
	 * memberPhone
	 * country
	 * recommenderEmail
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/join_up", method = RequestMethod.POST,produces="application/json" )
	public ReturnpBaseResponse join(ApiRequest apiRequest) {
		System.out.println("afid : " + apiRequest.getRecommenderEmail());
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "301", this.messageUtils.getMessage("api.message.wrong_afid_wrong_key"));
			return res;
		}else {
			res = this.apiServiceProvider.join(apiRequest);
			StringResponse stringRes  = this.apiResponseService.generateResponse(res, (String)apiServiceMap.get("apiKey"));
			return stringRes;
		}
		
	}
	
	/**
	 * 회원 삭제
	 * memberEmail
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete_member", method = RequestMethod.GET,produces="application/json" )
	public ReturnpBaseResponse deleteMember(ApiRequest apiRequest) {
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "301", this.messageUtils.getMessage("api.message.wrong_afid_wrong_key"));
			return res;
		}else {
			res = this.apiServiceProvider.deleteMember(apiRequest);
			StringResponse stringRes  = this.apiResponseService.generateResponse(res, (String)apiServiceMap.get("apiKey"));
			return stringRes;
		}
		
	}
	
	/**
	 * 회원 정보 수정 및 업데이트 
	 * 
	 * 아래의 필드중 선택적으로 존재 할 수 있으며, 존재하는 값만 업데이트 함 
	 * memberEmail
	 * memberName
	 * memberPassword
	 * memberPassword2
	 * memberPhone
	 * country
	 * recommenderEmail
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/modify_member", method = RequestMethod.POST,produces="application/json" )
	public ReturnpBaseResponse modifyMember(ApiRequest apiRequest) {
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "301", this.messageUtils.getMessage("api.message.wrong_afid_wrong_key"));
			return res;
		}else {
			res = this.apiServiceProvider.modifyMember(apiRequest);
			StringResponse stringRes  = this.apiResponseService.generateResponse(res, (String)apiServiceMap.get("apiKey"));
			return stringRes;
		}
	}
	
	/**
	 * 포인트 적립 및 취소 처리에 대한 핸들러  
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/handle_accumulate", method = RequestMethod.POST,produces="application/json" )
	public ReturnpBaseResponse executeAccumualte(ApiRequest apiRequest) {
		System.out.println("executeAccumualte");
		System.out.println("afid : " + apiRequest.getAfId());
		System.out.println("memberEmail : " + apiRequest.getMemberEmail());
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		DataMap dataMap = new DataMap();
		StringResponse stringRes = null;
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "301", this.messageUtils.getMessage("api.message.wrong_afid_wrong_key"));
			return res;
		}else {
			if (apiRequest.getQrOrg() != null) {
				dataMap.put("qr_org", apiRequest.getQrOrg().trim());
			}
			dataMap.put("pam", apiRequest.getPaymentApprovalAmount());
			dataMap.put("pas", apiRequest.getPaymentApprovalStatus().trim());
			dataMap.put("pat", (apiRequest.getPaymentApprovalDateTime() == null ? new Date(): apiRequest.getPaymentApprovalDateTime()));
			
			/*내부 승인 번호 체계로 변환*/
			dataMap.put("af_id", apiRequest.getAfId().trim());
			dataMap.put("pan", apiRequest.getAfId().trim() + "_"+ apiRequest.getPaymentApprovalNumber().trim());
			dataMap.put("phoneNumber", apiRequest.getMemberPhone().trim());
			dataMap.put("phoneNumberCountry", apiRequest.getMemberPhone().trim());
			dataMap.put("memberEmail", apiRequest.getMemberEmail());
			dataMap.put("key", (String)apiServiceMap.get("apiKey"));
			
			dataMap.put("payment_router_type", AppConstants.PaymentRouterType.API);
			dataMap.put("payment_router_name", AppConstants.PaymentRouterName.API);
			dataMap.put("payment_transaction_type", AppConstants.PaymentTransactionType.API);
			
			/*적립*/
			if (apiRequest.getPaymentApprovalStatus().equals("0")) {
				res = this.apiPointbackHandleService.accumulate(dataMap);
			}
			/*적립 취소*/
			else if (apiRequest.getPaymentApprovalStatus().equals("1")) {
				res = this.apiPointbackHandleService.cancelAccumulate(dataMap);
			}
			else {
				res = new ReturnpBaseResponse();
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "610", this.messageUtils.getMessage("api.message.wrong_payment_status"));
				return this.apiResponseService.generateResponse(res, (String)apiServiceMap.get("apiKey"));
			}
			stringRes  = this.apiResponseService.generateResponse(res, (String)apiServiceMap.get("apiKey"));
			return stringRes;
		}
		
	}
	
	/**
	 * 결제 번호에 의한 적립
	 * 이 경우는 이미 거래 내역을 생성했으나, 적립에 문제가 있을 경우 재 적립 처리를 함 
	 * 
	 * paymentApprovalNumber
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/accumulage_by_pan", method = RequestMethod.POST,produces="application/json" )
	public ReturnpBaseResponse accumulateByPan(ApiRequest apiRequest) {
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res,ResponseUtil.RESPONSE_OK,  "301", this.messageUtils.getMessage("api.message.wrong_afid_wrong_key"));
			return res;
		}else {
			res = this.basePointAccumulateService.accumuatePoint(apiRequest.getPaymentApprovalNumber());
			StringResponse stringRes  = this.apiResponseService.generateResponse(res, (String)apiServiceMap.get("apiKey"));
			return stringRes;
		}
		
	}
	
	/**
	 * 결제 번호의 의한 적립 취소
	 * 기존 포인트 적립된 것을 모두 되돌리고, 같은 결제 번호로 결제 취소 내역과  
	 * 포인트 취소 내역을 생성  
	 * 
	 * paymentApprovalNumber
	 * 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/cancel_accumulate_by_pan", method = RequestMethod.POST,produces="application/json" )
	public ReturnpBaseResponse cancelAccumulatgeByPan(ApiRequest apiRequest) {
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "301", this.messageUtils.getMessage("api.message.wrong_afid_wrong_key"));
			return res;
		}else {
			res = this.basePointAccumulateService.cancelAccumuate(apiRequest.getPaymentApprovalNumber());
			StringResponse stringRes  = this.apiResponseService.generateResponse(res, (String)apiServiceMap.get("apiKey"));
			return stringRes;
		}
	}
	
	/**
	 * 지원 언어 조회
	 * 파라메터 인자 없음
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/langs", method = RequestMethod.GET,produces="application/json" )
	public ReturnpBaseResponse getLangs(ApiRequest apiRequest) {
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "301", this.messageUtils.getMessage("api.message.wrong_afid_wrong_key"));
			return res;
		}else {
			res = this.apiServiceProvider.getLanguages(apiRequest);
			StringResponse stringRes  = this.apiResponseService.generateResponse(res, (String)apiServiceMap.get("apiKey"));
			return stringRes;
		}
		
	}
	
	/**
	 * 은행 계좌 리스트 
	 * 
 	 * memberEmail
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get_bank_accounts", method = RequestMethod.GET,produces="application/json" )
	public ReturnpBaseResponse getMemberBankAccounts(ApiRequest apiRequest) {
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res,ResponseUtil.RESPONSE_OK,  "301", this.messageUtils.getMessage("api.message.wrong_afid_wrong_key"));
			return res;
		}else {
			res = this.apiServiceProvider.getBankAccounts(apiRequest);
			StringResponse stringRes  = this.apiResponseService.generateResponse(res, (String)apiServiceMap.get("apiKey"));
			return stringRes;
		}
	}
	
	/**
	 * 은행 계좌 등록
	 * memberEmail
	 * bankName
	 * bankAccount
	 * accountOwner
	 * accountStatus
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/register_bank_account", method = RequestMethod.POST,produces="application/json" )
	public ReturnpBaseResponse registerBankAccount(ApiRequest apiRequest) {
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "301", this.messageUtils.getMessage("api.message.wrong_afid_wrong_key"));
			return res;
		}else {
			res = this.apiServiceProvider.registerBankAccount(apiRequest);
			StringResponse stringRes  = this.apiResponseService.generateResponse(res, (String)apiServiceMap.get("apiKey"));
			return stringRes;
		}
	}
	
	/**
	 * 은행 계좌 수정
	 * 
	 * 	memberBankAccountNo
	 * memberEmail
	 * 
	 * 아래의 필드는 선택적
	 * bankName
	 * bankAccount
	 * accountOwner
	 * accountStatus : Y or N

	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update_bank_account", method = RequestMethod.POST,produces="application/json" )
	public ReturnpBaseResponse updateBankAccount(ApiRequest apiRequest) {
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "301", this.messageUtils.getMessage("api.message.wrong_afid_wrong_key"));
			return res;
		}else {
			res = this.apiServiceProvider.updateBankAccount(apiRequest);
			StringResponse stringRes  = this.apiResponseService.generateResponse(res, (String)apiServiceMap.get("apiKey"));
			return stringRes;
		}
	}
	
	/**
	 * 은행 계좌 삭제
	 * memberBankAccountNo
	 * memberEmail
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete_bank_account", method = RequestMethod.GET,produces="application/json" )
	public ReturnpBaseResponse deleteBankAccount(ApiRequest apiRequest) {
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "301", this.messageUtils.getMessage("api.message.wrong_afid_wrong_key"));
			return res;
		}else {
			res = this.apiServiceProvider.deleteBankAccount(apiRequest);
			StringResponse stringRes  = this.apiResponseService.generateResponse(res, (String)apiServiceMap.get("apiKey"));
			return stringRes;
		}
	}
	
	/**
	 * 정책 제공
	 * 가맹 쇼핑몰에서 구매히 결제 적립율을 알 필요가 있기 때문에 제공 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get_policy", method = RequestMethod.POST,produces="application/json" )
	public ReturnpBaseResponse getPolicy(ApiRequest apiRequest) {
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "301", this.messageUtils.getMessage("api.message.wrong_afid_wrong_key"));
			return res;
		}else {
			res = this.apiServiceProvider.getPolicy(apiRequest);
			StringResponse stringRes  = this.apiResponseService.generateResponse(res, (String)apiServiceMap.get("apiKey"));
			return stringRes;
		}
	}
	
	
	/**
	 * G POINT 	 적립 내역
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get_gpoint_accumulate_history", method = RequestMethod.GET,produces="application/json" )
	public ReturnpBaseResponse getGpointAccumuateHistory(ApiRequest apiRequest) {
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "301", this.messageUtils.getMessage("api.message.wrong_afid_wrong_key"));
			return res;
		}else {
			res = this.apiServiceProvider.getGpointAccumuateHistory(apiRequest);
			StringResponse stringRes  = this.apiResponseService.generateResponse(res, (String)apiServiceMap.get("apiKey"));
			return stringRes;
		}
	}
	
	/**
	 * R POINT  전환 내역
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/rpoint_conversion_history", method = RequestMethod.GET,produces="application/json" )
	public ReturnpBaseResponse getRpointConversionHistory(ApiRequest apiRequest) {
		return null;
	}
	
	/**
	 * R PAY 출금 신청 하기 
	 * memberEmail
	 * memberBankAccountNo
	 * withdrawalAmount
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/register_withdrawal", method = RequestMethod.POST,produces="application/json" )
	public ReturnpBaseResponse registerWithdrawal(ApiRequest apiRequest) {
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res,ResponseUtil.RESPONSE_OK,  "301", this.messageUtils.getMessage("api.message.wrong_afid_wrong_key"));
			return res;
		}else {
			res = this.apiServiceProvider.registerWithdrawal(apiRequest);
			StringResponse stringRes  = this.apiResponseService.generateResponse(res, (String)apiServiceMap.get("apiKey"));
			return stringRes;
		}
	}
	
	/**
	 * 출금 신청 목록 가져오기
	 * memberEmail
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get_withdrawal_history", method = RequestMethod.GET,produces="application/json" )
	public ReturnpBaseResponse getWithdrawalHistory(ApiRequest apiRequest) {
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "301", this.messageUtils.getMessage("api.message.wrong_afid_wrong_key"));
			return res;
		}else {
			res = this.apiServiceProvider.getWithdrawalHistory(apiRequest);
			StringResponse stringRes  = this.apiResponseService.generateResponse(res, (String)apiServiceMap.get("apiKey"));
			return stringRes;
		}
	}

	/**
	 * 출금 정보 삭제
	 * memberEmail
	 * pointWithdrawalNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/delete_withdrawal", method = RequestMethod.GET,produces="application/json" )
	public ReturnpBaseResponse deleteWithdrawal(ApiRequest apiRequest) {
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "301", this.messageUtils.getMessage("api.message.wrong_afid_wrong_key"));
			return res;
		}else {
			res = this.apiServiceProvider.deleteWithdrawal(apiRequest);
			StringResponse stringRes  = this.apiResponseService.generateResponse(res, (String)apiServiceMap.get("apiKey"));
			return stringRes;
		}
	}
	
	/**
	 * 출금 취소
	 * memberEmail
	 * pointWithdrawalNo
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/cancel_withdrawal", method = RequestMethod.GET,produces="application/json" )
	public ReturnpBaseResponse cancelWithdrawal(ApiRequest apiRequest) {
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "301", this.messageUtils.getMessage("api.message.wrong_afid_wrong_key"));
			return res;
		}else {
			res = this.apiServiceProvider.cancelWithdrawal(apiRequest);
			StringResponse stringRes  = this.apiResponseService.generateResponse(res, (String)apiServiceMap.get("apiKey"));
			return stringRes;
		}
	}
	
	
	/**
	 * 출금 신청 수정하기
	 * memberEmail
	 * pointWithdrawalNo
	 * memberBankAccountNo
	 * withdrawalAmount
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/update_withdrawal", method = RequestMethod.POST,produces="application/json" )
	public ReturnpBaseResponse updatePointWithdrawal(ApiRequest apiRequest) {
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "301", this.messageUtils.getMessage("api.message.wrong_afid_wrong_key"));
			return res;
		}else {
			res = this.apiServiceProvider.updateWithdrawal(apiRequest);
			StringResponse stringRes  = this.apiResponseService.generateResponse(res, (String)apiServiceMap.get("apiKey"));
			return stringRes;
		}
	}
	
	/**
	 * 나의 회원 목록 가져오기
	 * 
	 *  memberEmail
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get_my_members", method = RequestMethod.GET,produces="application/json" )
	public ReturnpBaseResponse getMyMembers(ApiRequest apiRequest) {
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "301", this.messageUtils.getMessage("api.message.wrong_afid_wrong_key"));
			return res;
		}else {
			res = this.apiServiceProvider.getMyMembers(apiRequest);
			StringResponse stringRes  = this.apiResponseService.generateResponse(res, (String)apiServiceMap.get("apiKey"));
			return stringRes;
		}
	}
	
	/**
	 * 나의 포인트 정보 가져오기
	 * 회원포인트, 가맹점 포인트, 총판 포인드등 노드별 G-POINT 와 R-POINT 정보 
	 *  memberEmail
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/get_my_point_infos", method = RequestMethod.GET,produces="application/json" )
	public ReturnpBaseResponse getMyPointInfo(ApiRequest apiRequest) {
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "301", this.messageUtils.getMessage("api.message.wrong_afid_wrong_key"));
			return res;
		}else {
			res = this.apiServiceProvider.getMyTotalPointInfo(apiRequest);
			StringResponse stringRes  = this.apiResponseService.generateResponse(res, (String)apiServiceMap.get("apiKey"));
			return stringRes;
		}
	}
	
	/**
	 * 회원 여부 
	 *  memberEmail 회원 이메일
	 *  memberPassword 회원 평문 비밀번호 
	 * @return
	 */
	@ResponseBody
	@RequestMapping(value = "/valid_member", method = RequestMethod.GET,produces="application/json" )
	public ReturnpBaseResponse validateMember(ApiRequest apiRequest) {
		ReturnpBaseResponse  res = null;
		HashMap<String, Object> apiServiceMap = this.apiMapper.selectApiService(apiRequest);
		
		if (apiServiceMap == null) {
			res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "301", this.messageUtils.getMessage("api.message.wrong_afid_wrong_key"));
			return res;
		}else {
			res = this.apiServiceProvider.validateMember(apiRequest);
			StringResponse stringRes  = this.apiResponseService.generateResponse(res, (String)apiServiceMap.get("apiKey"));
			return stringRes;
		}
	}
}
