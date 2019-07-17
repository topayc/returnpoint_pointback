package com.returnp.pointback.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.returnp.pointback.common.ResponseUtil;
import com.returnp.pointback.common.ReturnpException;
import com.returnp.pointback.dao.mapper.ApiMapper;
import com.returnp.pointback.dto.command.api.ApiRequest;
import com.returnp.pointback.dto.response.ArrayResponse;
import com.returnp.pointback.dto.response.ObjectResponse;
import com.returnp.pointback.dto.response.ReturnpBaseResponse;
import com.returnp.pointback.service.interfaces.ApiResponseService;
import com.returnp.pointback.service.interfaces.BasePointAccumulateService;
import com.returnp.pointback.util.Crypto;
import com.returnp.pointback.util.Util;
import com.returnp.pointback.web.message.MessageUtils;

@Service
@PropertySource("classpath:/config.properties")
public class ApiServiceProviderImpl implements com.returnp.pointback.service.interfaces.ApiServiceProvider {

	@Autowired ApiMapper apiMapper	;
	@Autowired MessageUtils messageUtils;
	@Autowired BasePointAccumulateService basePointAccumulateService;
	@Autowired ApiResponseService apiResponseService;
	@Autowired Environment env;
	
	/*
	 * 서버에 저장된 캐쉬 반환
	 * cacheKey
	 * (완료)
	 */
	@Override
	public ReturnpBaseResponse getDataCache(ApiRequest apiRequest, HttpSession session) {
		ObjectResponse<String> res = new ObjectResponse<String>();
		try {
			Map<String, Object> memberMap = this.apiMapper.selectMember(apiRequest);
			String cacheData = (String)session.getAttribute(apiRequest.getCacheKey());
			res.setData(cacheData);
			ResponseUtil.setResponse(res,ResponseUtil.RESPONSE_OK,  "100", this.messageUtils.getMessage("api.transaction_completed"));
			return res;
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR, "500", this.messageUtils.getMessage("api.message.inner_server_error"));
			return res;
		}
	}

	/*
	 * 서버 캐시 저장
	 * cacheKey
	 * cacheData
	 * (완료)
	 */
	@Override
	public ReturnpBaseResponse saveDataCache(ApiRequest apiRequest, HttpSession session) {
		ReturnpBaseResponse res = new ReturnpBaseResponse();
		try {
			session.setAttribute(apiRequest.getCacheKey(), apiRequest.getCacheData());
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "100", this.messageUtils.getMessage("api.transaction_completed"));
			return res;
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR,"500", this.messageUtils.getMessage("api.message.inner_server_error"));
			return res;
		}
	}
	
	/*
	 * 회원 정보 
	 * memberEmail
	 * (완료)
	 */
	@Override
	public ReturnpBaseResponse getMemberInfo(ApiRequest apiRequest) {
		ObjectResponse<HashMap<String, Object>> res = new ObjectResponse<HashMap<String, Object>>();
		try {
			Map<String, Object> memberMap = this.apiMapper.selectMember(apiRequest);
			if (memberMap == null) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "302", this.messageUtils.getMessage( "api.message.not_member"));
				throw new ReturnpException(res);
			}
			apiRequest.setMemberNo((int)memberMap.get("memberNo"));
			res.setData(this.apiMapper.selectMemberInfo(apiRequest));
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "100", this.messageUtils.getMessage("api.transaction_completed"));
			return res;
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR,"500", this.messageUtils.getMessage("api.message.inner_server_error"));
			return res;
		}
	}
	
	/*
	 * 이메일 혹은 전화번호 중복 체크
	 * checkExistType
	 * memberEmail
	 * 혹은  memberPhone
	 * (완료)
	 */
	@Override
	public ReturnpBaseResponse isRegistered(ApiRequest apiRequest) {
		ReturnpBaseResponse  res = new ReturnpBaseResponse  ();
		
		try {
			if (apiRequest.getCheckValueType().trim().toLowerCase().equals("email")) {
				apiRequest.setMemberEmail(apiRequest.getCheckValue());
			}else if (apiRequest.getCheckValueType().trim().toLowerCase().equals("phone")){
				apiRequest.setMemberPhone(apiRequest.getCheckValue());
			}
			
			int count = this.apiMapper.selectMemberCount(apiRequest);
			if (count >  0) {
				ResponseUtil.setResponse(res, 
					ResponseUtil.RESPONSE_OK, 
					"303", 
					this.messageUtils.getMessage(
						"api.duplicated", 
						new Object[] { apiRequest.getCheckValueType().trim().toLowerCase().equals("email") ? "E-MAIL " + apiRequest.getMemberEmail() : "PHONE " + apiRequest.getMemberPhone()}));
				return res;
			}
			
			ResponseUtil.setResponse(res, 
				ResponseUtil.RESPONSE_OK, 
				"304", 
				this.messageUtils.getMessage(
					"api.not_duplicated",
					new Object[] { apiRequest.getCheckValueType().trim().toLowerCase().equals("email") ? "E-MAIL " + apiRequest.getMemberEmail() : "PHONE " + apiRequest.getMemberPhone()}));
			return res;
			
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR,"500", this.messageUtils.getMessage("api.message.inner_server_error"));
			return res;
		}
	}
	
	/*
	 * 회원 가입
	 * (완료)
	 * memberEmail
	 * memberName
	 * memberPassword
	 * memberPassword2
	 * memberPhone
	 * country
	 * recommenderEmail
	 */
	@Override
	public ReturnpBaseResponse join(ApiRequest apiRequest) {
		System.out.println(apiRequest.getRecommenderEmail());
		ObjectResponse<HashMap<String, Object>> res = new ObjectResponse<HashMap<String, Object>>();
		try {
		/*	HashMap<String, Object> affilaiteMap = this.apiMapper.selectAffiliate(apiRequest);
			System.out.println(apiRequest.getAfId());
			if (affilaiteMap == null) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "305", this.messageUtils.getMessage( "api.message.wrong_affiliate"));
					throw new ReturnpException(res);
			}else {
				apiRequest.setJoinRoute((String)affilaiteMap.get("affiliateName"));
			}*/
			
			/*이메일 중복 확인*/
			ApiRequest apiQuery = new ApiRequest(); 
			apiQuery.setMemberEmail(apiRequest.getMemberEmail());
			int count = this.apiMapper.selectMemberCount(apiQuery);
			
			if (count > 0 ) {
				ResponseUtil.setResponse(
					res, ResponseUtil.RESPONSE_OK, "306", this.messageUtils.getMessage( "api.duplicated", new Object[] { "E-MAIL " + apiRequest.getMemberEmail() }));
				throw new ReturnpException(res);
			}
			
			/*전화 번호 중복 확인*/
			apiQuery = new ApiRequest();
			apiQuery.setMemberPhone(apiRequest.getMemberPhone());
			count = this.apiMapper.selectMemberCount(apiQuery);
			if (count > 0 ) {
				ResponseUtil.setResponse(
					res, ResponseUtil.RESPONSE_OK, "307", this.messageUtils.getMessage( "api.duplicated", new Object[] { "PHONE  " + apiRequest.getMemberPhone()}));
				throw new ReturnpException(res);
			}
			
			apiRequest.setMemberPassword(Util.sha(apiRequest.getMemberPassword()));
			if (org.apache.commons.lang3.StringUtils.isEmpty(apiRequest.getMemberPassword2())) {
				apiRequest.setMemberPassword2(apiRequest.getMemberPassword());
			}
			apiRequest.setMemberPassword2(Util.sha(apiRequest.getMemberPassword2()));
			
			/* 추천인 번호 설정*/
			Map<String, Object> recommenderMap = this.apiMapper.selectRecommenderInfo(apiRequest);
			if (recommenderMap == null) {
				ResponseUtil.setResponse(
					res, ResponseUtil.RESPONSE_OK, "309", this.messageUtils.getMessage( "api.not_existed_recommender", new Object[] { apiRequest.getRecommenderEmail()}));
				throw new ReturnpException(res);
			}
			
			/* 국가 코드 설정*/
			if (org.apache.commons.lang3.StringUtils.isEmpty(apiRequest.getCountry())) {
				apiRequest.setCountry("KR");
			}
			
			/* 회원 정보 생성*/
			this.apiMapper.createMember(apiRequest);
			
			/*기본  G-POINT 생성*/
			apiRequest.setNodeNo(apiRequest.getMemberNo());
			apiRequest.setNodeType("1");
			apiRequest.setNodeTypeName("member");
			this.apiMapper.createGreenPoint(apiRequest);
			
			/*추천인 기본  G-POINT 생성*/
			apiRequest.setNodeType("2");
			apiRequest.setNodeTypeName("recommender");
			this.apiMapper.createGreenPoint(apiRequest);
			
			/*RED  G-POINT 생성*/
			this.apiMapper.createRedPoint(apiRequest);
			
			/*생성한 회원 정보 반환*/
			res.setData(this.apiMapper.selectMemberInfo(apiRequest));
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "100", this.messageUtils.getMessage("api.transaction_completed"));
			return res;
			
		}catch(ReturnpException e1) {
			e1.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e1.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR,"500", this.messageUtils.getMessage("api.message.inner_server_error"));
			return res;
		}
	}
	
	/*
	 * 회원 탈퇴
	 * (완료)
	 * memberEmail
	 */
	@Override
	public ReturnpBaseResponse deleteMember(ApiRequest apiRequest) {
		ReturnpBaseResponse  res = new ReturnpBaseResponse  ();
		try {
			Map<String, Object> memberMap = this.apiMapper.selectMember(apiRequest);
			if (memberMap == null) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "302", this.messageUtils.getMessage( "api.message.not_member"));
				throw new ReturnpException(res);
			}
			
			ApiRequest updateRequest = new ApiRequest();
			updateRequest.setMemberNo((int)memberMap.get("memberNo"));
			updateRequest.setMemberStatus("7");
			this.apiMapper.updateMember(updateRequest);
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "100", this.messageUtils.getMessage( "api.withdrawal_membership_ok"));
			return res;
			
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR,"500", this.messageUtils.getMessage("api.message.inner_server_error"));
			return res;
		}
	}
	
	/*
	 * 회원 정보 수정
	 * 
	 * 아래의 필드중 선택적으로 존재 할 수 있으며, 존재하는 값만 업데이트 함 
	 * memberEmail
	 * memberName
	 * memberPassword
	 * memberPassword2
	 * memberPhone
	 * country
	 * recommenderEmail
	 * (완료) 
	 */

	@Override
	public ReturnpBaseResponse modifyMember(ApiRequest apiRequest) {
		ObjectResponse<HashMap<String, Object>> res = new ObjectResponse<HashMap<String, Object>>();
		try {
			Map<String, Object> memberMap = this.apiMapper.selectMember(apiRequest);
			if (memberMap == null) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "302", this.messageUtils.getMessage( "api.message.not_member"));
				throw new ReturnpException(res);
			}
			
			apiRequest.setMemberNo(((int)memberMap.get("memberNo")));
			
			/* 추천인 번호 설정*/
			Map<String, Object> recommenderMap = this.apiMapper.selectRecommenderInfo(apiRequest);
			if (recommenderMap != null) {
				apiRequest.setRecommenderNo((Integer)recommenderMap.get("memberNo"));
			}
			this.apiMapper.updateMember(apiRequest);
			
			/*수정한 회원 정보 반환*/
			res.setData(this.apiMapper.selectMemberInfo(apiRequest));
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "100", this.messageUtils.getMessage("api.transaction_completed"));
			return res;
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR,"500", this.messageUtils.getMessage("api.message.inner_server_error"));
			return res;
		}
	}

	/*
	 * 지원 언어 목록 가져오기
	 * (완료) 
	 */
	@Override
	public ReturnpBaseResponse getLanguages(ApiRequest apiRequest) {
		ArrayResponse<HashMap<String, Object>>  res = new ArrayResponse<HashMap<String, Object>>();
		try {
			ArrayList<HashMap<String, Object>> langs = this.apiMapper.selectLanguages();
			res.setRows(langs);
			res.setTotal(langs.size());
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "100", this.messageUtils.getMessage( "api.transaction_completed"));
			return res;
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res,ResponseUtil.RESPONSE_ERROR, "500", this.messageUtils.getMessage("api.message.inner_server_error"));
			return res;
		}
	}
	
	/*
	 * 정책 가져오기
	 * (완료) 
	 */
	@Override
	public ReturnpBaseResponse getPolicy(ApiRequest apiRequest) {
		ObjectResponse<HashMap<String, Object>>  res = new ObjectResponse<HashMap<String, Object>>();
		try {
			res.setData(this.apiMapper.selectPolicy());
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "100", this.messageUtils.getMessage( "api.transaction_completed"));
			return res;
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR,"500", this.messageUtils.getMessage("api.message.inner_server_error"));
			return res;
		}
	}
	
	/*
	 * 회원 은행 계좌 목록 가져오기
	 * memberEmail
	 * (완료) 
	 */
	@Override
	public ReturnpBaseResponse getBankAccounts(ApiRequest apiRequest) {
		ArrayResponse<HashMap<String, Object>>  res = new ArrayResponse<HashMap<String, Object>>();
		try {
			Map<String, Object> memberMap = this.apiMapper.selectMember(apiRequest);
			if (memberMap == null) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "302", this.messageUtils.getMessage( "api.message.not_member"));
				throw new ReturnpException(res);
			}
			
			apiRequest.setMemberNo((int)memberMap.get("memberNo"));
			ArrayList<HashMap<String, Object>> bankAccounts = this.apiMapper.selectBankAccounts(apiRequest);
			res.setRows(bankAccounts);
			res.setTotal(bankAccounts.size());
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "100", this.messageUtils.getMessage( "api.transaction_completed"));
			return res;
			
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR,"500", this.messageUtils.getMessage("api.message.inner_server_error"));
			return res;
		}
	}
	
	/*
	 * 회원 은행 계좌 생성
	 * memberEmail
	 * bankName
	 * bankAccount
	 * accountOwner
	 * (완료) 
	 */
	@Override
	public ReturnpBaseResponse registerBankAccount(ApiRequest apiRequest) {
		ObjectResponse<HashMap<String, Object>> res = new ObjectResponse<HashMap<String, Object>>();
		try {
			Map<String, Object> memberMap = this.apiMapper.selectMember(apiRequest);
			if (memberMap == null) {
				ResponseUtil.setResponse(res,ResponseUtil.RESPONSE_OK,  "302", this.messageUtils.getMessage( "api.message.not_member"));
				throw new ReturnpException(res);
			}
			
			apiRequest.setMemberNo((int)memberMap.get("memberNo"));
			apiRequest.setAccountStatus("Y");
			apiRequest.setRegAdminNo(0);
			apiRequest.setRegType("U");
			
			int count = this.apiMapper.createMemberBankAccount(apiRequest);
			if (count < 1) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "300", this.messageUtils.getMessage( "api.register_member_bank_account_failed"));
				throw new ReturnpException(res);
			}
			
			ApiRequest apiCond = new ApiRequest();
			apiCond.setMemberBankAccountNo(apiRequest.getMemberBankAccountNo());
			res.setData(this.apiMapper.selectBankAccount(apiCond));
			ResponseUtil.setResponse(res,ResponseUtil.RESPONSE_OK,  "100", this.messageUtils.getMessage( "api.transaction_completed"));
			return res;
			
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR,"500", this.messageUtils.getMessage("api.message.inner_server_error"));
			return res;
		}
	}

	/*
	 * 회원 은행 계좌 삭제
	 * memberBankAccountNo
	 * memberEmail
	 * (완료) 
	 */
	@Override
	public ReturnpBaseResponse deleteBankAccount(ApiRequest apiRequest) {
		ObjectResponse<HashMap<String, Object>> res = new ObjectResponse<HashMap<String, Object>>();
		try {
			Map<String, Object> memberMap = this.apiMapper.selectMember(apiRequest);
			if (memberMap == null) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "200", this.messageUtils.getMessage( "api.message.not_member"));
				throw new ReturnpException(res);
			}
			
			apiRequest.setMemberNo((int)memberMap.get("memberNo"));
			int count = this.apiMapper.deleteMemberBankAccount(apiRequest);
			if (count < 1) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "300", this.messageUtils.getMessage( "api.delete_member_bank_account_failed"));
				throw new ReturnpException(res);
			}
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "300", this.messageUtils.getMessage( "api.delete_member_bank_account_success"));
			return res;
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR,"500", this.messageUtils.getMessage("api.message.inner_server_error"));
			return res;
		}
	}
	

	/*
	 * 회원 은행 계좌 수정
	 * memberBankAccountNo
	 * memberEmail
	 * 
	 아래의 필드는 선택적
	 * bankName
	 * bankAccount
	 * accountOwner
	 * (완료) 
	 */
	@Override
	public ReturnpBaseResponse updateBankAccount(ApiRequest apiRequest) {
		ObjectResponse<HashMap<String, Object>> res = new ObjectResponse<HashMap<String, Object>>();
		try {
			Map<String, Object> memberMap = this.apiMapper.selectMember(apiRequest);
			if (memberMap == null) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "302", this.messageUtils.getMessage( "api.message.not_member"));
				throw new ReturnpException(res);
			}
			
			apiRequest.setMemberNo((int)memberMap.get("memberNo"));
			int count = this.apiMapper.updateMemberBankAccount(apiRequest);
			if (count < 1) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "300", this.messageUtils.getMessage( "api.update_member_bank_account_failed"));
				throw new ReturnpException(res);
			}
			
			ApiRequest apiCond = new ApiRequest();
			apiCond.setMemberBankAccountNo(apiRequest.getMemberBankAccountNo());
			res.setData(this.apiMapper.selectBankAccount(apiCond));
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "300", this.messageUtils.getMessage( "api.update_member_bank_account_success"));
			return res;
			
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR,"500", this.messageUtils.getMessage("api.message.inner_server_error"));
			return res;
		}
	}

	/*
	 * 출금 신청 목록 가져오기
	 * memberEmail
	 * (완료) 
	 */
	@Override
	public ReturnpBaseResponse getWithdrawalHistory(ApiRequest apiRequest) {
		ArrayResponse<HashMap<String, Object>>  res = new ArrayResponse<HashMap<String, Object>>();
		try {
			Map<String, Object> memberMap = this.apiMapper.selectMember(apiRequest);
			if (memberMap == null) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "302", this.messageUtils.getMessage( "api.message.not_member"));
				throw new ReturnpException(res);
			}
			
			apiRequest.setMemberNo((int)memberMap.get("memberNo"));
			ArrayList<HashMap<String, Object>> withdrawals = this.apiMapper.selectWithdrawals(apiRequest);
			res.setRows(withdrawals);
			res.setTotal(withdrawals.size());
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "100", this.messageUtils.getMessage( "api.transaction_completed"));
			return res;
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR,"500", this.messageUtils.getMessage("api.message.inner_server_error"));
			return res;
		}
	}
	
	/*
	 * 출금 신청하기
	 * memberEmail
	 * memberBankAccountNo
	 * withdrawalAmount
	 * (완료) 
	 */
	@Override
	public ReturnpBaseResponse registerWithdrawal(ApiRequest apiRequest) {
		ObjectResponse<HashMap<String, Object>> res = new ObjectResponse<HashMap<String, Object>>();
		try {
			Map<String, Object> memberMap = this.apiMapper.selectMember(apiRequest);
			if (memberMap == null) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "302", this.messageUtils.getMessage( "api.message.not_member"));
				throw new ReturnpException(res);
			}
			
			apiRequest.setMemberNo((int)memberMap.get("memberNo"));
			HashMap<String, Object> redPointMap = this.apiMapper.selectRedPoint(apiRequest);
			HashMap<String, Object> policyMap = this.apiMapper.selectPolicy();
			
			int rPayWithdrawalMinLimit  = (int)policyMap.get("rPayWithdrawalMinLimit");
			int rPayWithdrawalMaxLimit  = (int)policyMap.get("rPayWithdrawalMaxLimit");
			if ((float)apiRequest.getWithdrawalAmount() < (float)rPayWithdrawalMinLimit || 
					(float)apiRequest.getWithdrawalAmount() > (float)rPayWithdrawalMaxLimit ){
				ResponseUtil.setResponse( res, ResponseUtil.RESPONSE_OK, "300",
					this.messageUtils.getMessage("api.message.wrong_withdrawal_arrange", new Object[]{ rPayWithdrawalMinLimit, rPayWithdrawalMaxLimit}));
				throw new ReturnpException(res);
			}
			
			if ((float)apiRequest.getWithdrawalAmount() > (float)redPointMap.get("pointAmount")) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "300", this.messageUtils.getMessage( "api.withdrawal_than_balance"));
				throw new ReturnpException(res);
			} 
			
			/*포인트 출금 정보 등록*/
			apiRequest.setWithdrawalMessage(null);
			apiRequest.setWithdrawalStatus("1");
			apiRequest.setWithdrawalPointType("R-POINT");
			apiRequest.setRegAdminNo(0);
			apiRequest.setRegType("U");
			
			int count = this.apiMapper.createWithdrawal(apiRequest);
			if (count < 1) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "300", this.messageUtils.getMessage( "api.create_withdrawal_info_failed"));
				throw new ReturnpException(res);
			}
			
			/* 출금한 금액만큼 레드포인트 차감 후 업데이트 */
			ApiRequest ar = new ApiRequest();
			ar.setRedPointNo((int)redPointMap.get("redPointNo"));
			ar.setPointAmount((float)redPointMap.get("pointAmount") - (float)apiRequest.getWithdrawalAmount());
			count = this.apiMapper.updateRedPoint(ar);
			if (count < 1) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "300", this.messageUtils.getMessage( "api.create_withdrawal_info_failed"));
				throw new ReturnpException(res);
			}
			
			/* 등록한 출금 정보 반환*/
			res.setData(this.apiMapper.selectWithdrawal(apiRequest));
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "100", this.messageUtils.getMessage( "api.create_withdrawal_info_success"));
			return res;
			
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR,"500", this.messageUtils.getMessage("api.message.inner_server_error"));
			return res;
		}
	}
	
	/*
	 * 출금 취소하기
	 * memberEmail
	 * pointWithdrawalNo
	 * (완료) 
	 */
	@Override
	public ReturnpBaseResponse cancelWithdrawal(ApiRequest apiRequest) {
		ObjectResponse<HashMap<String, Object>> res = new ObjectResponse<HashMap<String, Object>>();
		try {
			Map<String, Object> memberMap = this.apiMapper.selectMember(apiRequest);
			if (memberMap == null) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "302", this.messageUtils.getMessage( "api.message.not_member"));
				throw new ReturnpException(res);
			}
			/*등록된 출금 정보 */
			HashMap<String, Object> withdrawalMap = this.apiMapper.selectWithdrawal(apiRequest);
			/*등록된 출금 정보가 없을 경우 에러  */
			if (withdrawalMap == null) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "300", this.messageUtils.getMessage( "api.wrong_withdrawal_info"));
				throw new ReturnpException(res);
			}
			
			/*등록된 출금 정보가 '출금 처리중' 상태가 아닐 경우 취소 불가 */
			if (!((String)withdrawalMap.get("withdrawalStatus")).equals("1")) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "300", this.messageUtils.getMessage( "api.cant_cancel_withdrawal"));
				throw new ReturnpException(res);
			}
			
			/*회원의 R-POINT 정보  */
			apiRequest.setMemberNo((int)memberMap.get("memberNo"));
			HashMap<String, Object> redPointMap = this.apiMapper.selectRedPoint(apiRequest);
			
			/*출금 신청 정보를 출금 취소로 변경후 업데이트*/
			apiRequest.setWithdrawalStatus("4");
			int count = this.apiMapper.updateWithdrawal(apiRequest);
			if (count < 1) {
				ResponseUtil.setResponse(res,ResponseUtil.RESPONSE_OK,  "300", this.messageUtils.getMessage( "api.message.update_withdrawal_info_failed"));
				throw new ReturnpException(res);
			}
			
			/* 출금 취소한  금액만큼 레드포인트 증가 후 업데이트 */
			ApiRequest ar = new ApiRequest();
			ar.setRedPointNo((int)redPointMap.get("redPointNo"));
			ar.setPointAmount((float)redPointMap.get("pointAmount") +  Float.parseFloat(String.valueOf(withdrawalMap.get("withdrawalAmount"))));
			count = this.apiMapper.updateRedPoint(ar);
			if (count < 1) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "300", this.messageUtils.getMessage( "api.create_withdrawal_info_failed"));
				throw new ReturnpException(res);
			}
			
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "100", this.messageUtils.getMessage( "api.transaction_completed"));
			return res;
			
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR,"500", this.messageUtils.getMessage("api.message.inner_server_error"));
			return res;
		}
	}
	/*
	 * 출금 정보 삭제
	 * memberEmail
	 * pointWithdrawalNo
	 * (완료) 
	 */
	@Override
	public ReturnpBaseResponse deleteWithdrawal(ApiRequest apiRequest) {
		ObjectResponse<HashMap<String, Object>> res = new ObjectResponse<HashMap<String, Object>>();
		try {
			Map<String, Object> memberMap = this.apiMapper.selectMember(apiRequest);
			if (memberMap == null) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "302", this.messageUtils.getMessage( "api.message.not_member"));
				throw new ReturnpException(res);
			}
			
			apiRequest.setMemberNo((int)memberMap.get("memberNo"));
			int count = this.apiMapper.deleteWithdrawal(apiRequest);
			if (count < 1) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "300", this.messageUtils.getMessage( "api.create_withdrawal_info_delete_fail"));
				throw new ReturnpException(res);
			}
			
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "100", this.messageUtils.getMessage( "api.create_withdrawal_info_delete_success"));
			return res;
			
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR,"500", this.messageUtils.getMessage("api.message.inner_server_error"));
			return res;
		}
	}
	
	/*
	 * 출금 정보 수정
	 *  memberEmail
	 * pointWithdrawalNo
	 * memberBankAccountNo
	 * withdrawalAmount
	 * (완료) 
	 */
	@Override
	public ReturnpBaseResponse updateWithdrawal(ApiRequest apiRequest) {
		ObjectResponse<HashMap<String, Object>> res = new ObjectResponse<HashMap<String, Object>>();
		try {
			Map<String, Object> memberMap = null;
			HashMap<String, Object> withdrawalMap = null;
			HashMap<String, Object> redPointMap = null;
			
			memberMap = this.apiMapper.selectMember(apiRequest);
			if (memberMap == null) {
                ResponseUtil.setResponse(res,ResponseUtil.RESPONSE_OK,  "302", this.messageUtils.getMessage( "api.message.not_member"));
                throw new ReturnpException(res);
            }
			
			/*등록된 출금 정보 */
            withdrawalMap = this.apiMapper.selectWithdrawal(apiRequest);
            /*등록된 출금 정보가 없을 경우 에러  */
            if (withdrawalMap == null) {
                ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "300", this.messageUtils.getMessage( "api.wrong_withdrawal_info"));
                throw new ReturnpException(res);
            }
            
            /*등록된 출금 정보가 '출금 처리중' 상태가 아닐 경우 수정  불가 */
            if (!((String)withdrawalMap.get("withdrawalStatus")).equals("1")) {
                ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "300", this.messageUtils.getMessage( "api.cant_modify_withdrawal"));
                throw new ReturnpException(res);
            }
            
            /*회원의 R-POINT 정보  */
            apiRequest.setMemberNo((int)memberMap.get("memberNo"));
            redPointMap = this.apiMapper.selectRedPoint(apiRequest);
            
            /* 출금 신청 정보 수정 업데이트  */
            if (this.apiMapper.updateWithdrawal(apiRequest) < 1) {
                ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "300", this.messageUtils.getMessage( "api.message.update_withdrawal_info_failed"));
                throw new ReturnpException(res);
            }
          
            /* 출금액 수정 변경정보에 따른 G-POINT 업데이트  */
            ApiRequest ar = new ApiRequest();
            float updateRp = 0.0f;
            float finalRp = 0;
           
            updateRp = (int)withdrawalMap.get("withdrawalAmount")  - apiRequest.getWithdrawalAmount() ;
            finalRp  = (float)redPointMap.get("pointAmount") + updateRp;

            ar.setRedPointNo((int)redPointMap.get("redPointNo"));
            ar.setPointAmount(finalRp);
            if (this.apiMapper.updateRedPoint(ar) < 1) {
                ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "300", this.messageUtils.getMessage( "api.message.update_withdrawal_info_failed"));
                throw new ReturnpException(res);
            }
            
            ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "100", this.messageUtils.getMessage( "api.transaction_completed"));
            return res;
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR,"500", this.messageUtils.getMessage("api.message.inner_server_error"));
			return res;
		}
	}

	/*
	 * 나의 회원 리스트 
	 *  memberEmail
	 * (완료) 
	 */
	@Override
	public ReturnpBaseResponse getMyMembers(ApiRequest apiRequest) {
		ArrayResponse<HashMap<String, Object>> res = new ArrayResponse<HashMap<String, Object>>();
		try {
			Map<String, Object> memberMap = this.apiMapper.selectMember(apiRequest);
			if (memberMap == null) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "302", this.messageUtils.getMessage( "api.message.not_member"));
				throw new ReturnpException(res);
			}
			
			apiRequest.setMemberNo((int)memberMap.get("memberNo"));
			ArrayList<HashMap<String, Object>> myMembers = this.apiMapper.selectMyMembers(apiRequest);
			res.setRows(myMembers);
			res.setTotal(myMembers.size());
			ResponseUtil.setResponse(res,ResponseUtil.RESPONSE_OK,  "100", this.messageUtils.getMessage( "api.transaction_completed"));
			return res;
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res,ResponseUtil.RESPONSE_ERROR, "500", this.messageUtils.getMessage("api.message.inner_server_error"));
			return res;
		}
	}

	@Override
	public ReturnpBaseResponse getMyTotalPointInfo(ApiRequest apiRequest) {
		ArrayResponse<HashMap<String, Object>> res = new ArrayResponse<HashMap<String, Object>>();
		try {
			Map<String, Object> memberMap = this.apiMapper.selectMember(apiRequest);
			if (memberMap == null) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "302", this.messageUtils.getMessage( "api.message.not_member"));
				throw new ReturnpException(res);
			}
			
			apiRequest.setMemberNo((int)memberMap.get("memberNo"));
			ArrayList<HashMap<String, Object>> myPointInfos = this.apiMapper.selectMyTotalPointInfo(apiRequest);
			res.setRows(myPointInfos);
			res.setTotal(myPointInfos.size());
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "100", this.messageUtils.getMessage( "api.transaction_completed"));
			return res;
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR,"500", this.messageUtils.getMessage("api.message.inner_server_error"));
			return res;
		}
	}
	
	@Override
	public HashMap<String, Object> selectApiService(ApiRequest apiRequest) {
		return this.apiMapper.selectApiService(apiRequest);
	}
	
	/*
	 * G 포인트 적립 내역 
	 * memberEmail
	 */
	@Override
	public ReturnpBaseResponse getGpointAccumuateHistory(ApiRequest apiRequest) {
		ArrayResponse<HashMap<String, Object>> res = new ArrayResponse<HashMap<String, Object>>();
		try {
			Map<String, Object> memberMap = this.apiMapper.selectMember(apiRequest);
			if (memberMap == null) {
				ResponseUtil.setResponse(res,ResponseUtil.RESPONSE_OK,  "302", this.messageUtils.getMessage( "api.message.not_member"));
				throw new ReturnpException(res);
			}
			apiRequest.setMemberNo((int)memberMap.get("memberNo"));
			ArrayList<HashMap<String, Object>> myPointInfos = this.apiMapper.selectMyGPointAccumuateHistory(apiRequest);
			res.setRows(myPointInfos);
			res.setTotal(myPointInfos.size());
			ResponseUtil.setResponse(res,ResponseUtil.RESPONSE_OK,  "100", this.messageUtils.getMessage( "api.transaction_completed"));
			return res;
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR,"500", this.messageUtils.getMessage("api.message.inner_server_error"));
			return res;
		}
	}

	@Override
	public ReturnpBaseResponse validateMember(ApiRequest apiRequest) {
		ObjectResponse<HashMap<String, Object>> res = new ObjectResponse<HashMap<String, Object>>();
		try {
			Map<String, Object> memberMap = this.apiMapper.selectMember(apiRequest);
			if (memberMap == null) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "620", this.messageUtils.getMessage( "api.message.not_existed_id"));
				throw new ReturnpException(res);
			}
			
			if (!Crypto.sha(apiRequest.getMemberPassword()).equals((String)memberMap.get("memberPassword"))) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "621", this.messageUtils.getMessage( "api.message.invalid_member_pass"));
				throw new ReturnpException(res);
			}
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "100", this.messageUtils.getMessage("api.message.valid_member"));
			return res;
			
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR,"500", this.messageUtils.getMessage("api.message.inner_server_error"));
			return res;
		}
	}

	/*
	 * G 포인트의 R 포인트 전환 내역 
	 * memberEmail
	 */
	@Override
	public ReturnpBaseResponse getRpointConversionHistory(ApiRequest apiRequest) {
		return null;
	}
	
	
	/*
	 * 결제에 해당 하는 포인트 적립
	 * 해당 작업은 BasePointAccumulateService 에서 처리 하기 때문에 여기서는 빈 구현만 제공
	 * memberEmail
	 */
	@Override
	public ReturnpBaseResponse executeAccumuate(ApiRequest apiRequest) {
		return null;
	}
	
	/*
	 * 결제에 해당 하는 포인트 적립 취소 
	 * 해당 작업은 BasePointAccumulateService 에서 처리 하기 때문에 여기서는 빈 구현만 제공
	 * memberEmail
	 */
	@Override
	public ReturnpBaseResponse cancelAccumuate(ApiRequest apiRequest) {
		return null;
	}

	
}
