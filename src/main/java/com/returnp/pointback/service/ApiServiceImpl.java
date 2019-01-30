package com.returnp.pointback.service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpSession;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.returnp.pointback.common.ResponseUtil;
import com.returnp.pointback.common.ReturnpException;
import com.returnp.pointback.dao.mapper.ApiMapper;
import com.returnp.pointback.dto.command.api.ApiRequest;
import com.returnp.pointback.dto.response.ArrayResponse;
import com.returnp.pointback.dto.response.ObjectResponse;
import com.returnp.pointback.dto.response.ReturnpBaseResponse;
import com.returnp.pointback.service.interfaces.BasePointAccumulateService;
import com.returnp.pointback.util.Util;
import com.returnp.pointback.web.message.MessageUtils;

@Service
public class ApiServiceImpl implements com.returnp.pointback.service.interfaces.ApiService {

	@Autowired ApiMapper apiMapper	;
	@Autowired MessageUtils messageUtils;
	@Autowired BasePointAccumulateService basePointAccumulateService;
	
	/*
	 * 서버에 저장된 캐쉬 반환
	 * cacheKey
	 * (완료)
	 */
	@Override
	public ReturnpBaseResponse getDataCache(ApiRequest apiRequest, HttpSession session) {
		ObjectResponse<String> res = new ObjectResponse<String>();
		try {
			String cacheData = (String)session.getAttribute(apiRequest.getCacheKey());
			res.setData(cacheData);
			ResponseUtil.setResponse(res, "100", this.messageUtils.getMessage("api.transaction_completed"));
			return res;
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, "1001", this.messageUtils.getMessage("api.message.inner_server_error"));
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
			ResponseUtil.setResponse(res, "100", this.messageUtils.getMessage("api.transaction_completed"));
			return res;
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, "1001", this.messageUtils.getMessage("api.message.inner_server_error"));
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
			Map<String, Object> memberMap = this.apiMapper.selectMemberInfo(apiRequest);
			if (memberMap == null) {
				ResponseUtil.setResponse(res, "20002", this.messageUtils.getMessage( "api.message.not_member"));
				throw new ReturnpException(res);
			}
			apiRequest.setMemberNo((int)memberMap.get("memberNo"));
			res.setData(this.apiMapper.selectMemberInfo(apiRequest));
			ResponseUtil.setResponse(res, "100", this.messageUtils.getMessage("api.transaction_completed"));
			return res;
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, "1001", this.messageUtils.getMessage("api.message.inner_server_error"));
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
	public ReturnpBaseResponse checkDuplicated(ApiRequest apiRequest) {
		ReturnpBaseResponse  res = new ReturnpBaseResponse  ();
		try {
			int count = this.apiMapper.selectMemberCount(apiRequest);
			if (count >  0) {
				ResponseUtil.setResponse(res, "103", 
					this.messageUtils.getMessage(
						"api.duplicated", 
						new Object[] { apiRequest.getCheckExistType().equals("1") ? apiRequest.getMemberEmail() : apiRequest.getMemberPhone()}));
				throw new ReturnpException(res);
			}
			
			ResponseUtil.setResponse(res, "101", 
				this.messageUtils.getMessage(
					"api.not_duplicated",
					new Object[] { apiRequest.getCheckExistType().equals("1") ? apiRequest.getMemberEmail() : apiRequest.getMemberPhone()}));
			return res;
			
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, "1001", this.messageUtils.getMessage("api.message.inner_server_error"));
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
		ObjectResponse<HashMap<String, Object>> res = new ObjectResponse<HashMap<String, Object>>();
		try {
		
			/*이메일 중복 확인*/
			ApiRequest api = new ApiRequest();
			api.setMemberEmail(apiRequest.getMemberEmail());
			int count = this.apiMapper.selectMemberCount(apiRequest);
			
			if (count > 0 ) {
				ResponseUtil.setResponse(res, "20001", 
					this.messageUtils.getMessage(
						"api.duplicated", 
						new Object[] { apiRequest.getMemberEmail() }));
				throw new ReturnpException(res);
			}
			
			/*전화 번호 중복 확인*/
			api = new ApiRequest();
			api.setMemberPhone(apiRequest.getMemberPhone());
			count = this.apiMapper.selectMemberCount(apiRequest);
			
			if (count > 0 ) {
				ResponseUtil.setResponse(res, "20001", 
					this.messageUtils.getMessage(
						"api.duplicated", 
						new Object[] { apiRequest.getMemberPhone()}));
				throw new ReturnpException(res);
			}
			
			apiRequest.setMemberPassword(Util.sha(apiRequest.getMemberPassword()));
			apiRequest.setMemberPassword2(Util.sha(apiRequest.getMemberPassword2()));
			Map<String, Object> recommenderMap = this.apiMapper.selectRecommenderInfo(apiRequest);
			
			if (recommenderMap != null) {
				apiRequest.setRecommenderNo((Integer)recommenderMap.get("memberNo"));
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
			ResponseUtil.setResponse(res, "100", this.messageUtils.getMessage("api.transaction_completed"));
			return res;
			
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, "1001", this.messageUtils.getMessage("api.message.inner_server_error"));
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
			Map<String, Object>  memberInfoMap = this.apiMapper.selectMemberInfo(apiRequest);
			if (memberInfoMap == null) {
				ResponseUtil.setResponse(res, "20002", this.messageUtils.getMessage( "api.message.not_member"));
				throw new ReturnpException(res);
			}
			
			ApiRequest updateRequest = new ApiRequest();
			updateRequest.setMemberNo((int)memberInfoMap.get("memberNo"));
			updateRequest.setMemberStatus("6");
			this.apiMapper.updateMember(updateRequest);
			ResponseUtil.setResponse(res, "100", this.messageUtils.getMessage( "api.withdrawal_membership_ok"));
			return res;
			
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, "1001", this.messageUtils.getMessage("api.message.inner_server_error"));
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
			Map<String, Object>  memberMap = this.apiMapper.selectMemberInfo(apiRequest);
			if (memberMap == null) {
				ResponseUtil.setResponse(res, "20002", this.messageUtils.getMessage( "api.message.not_member"));
				throw new ReturnpException(res);
			}
			
			apiRequest.setMemberNo(((int)memberMap.get("memberNo")));
			this.apiMapper.updateMember(apiRequest);
			
			/*수정한 회원 정보 반환*/
			res.setData(this.apiMapper.selectMemberInfo(apiRequest));
			ResponseUtil.setResponse(res, "100", this.messageUtils.getMessage("api.transaction_completed"));
			return res;
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, "1001", this.messageUtils.getMessage("api.message.inner_server_error"));
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
			res.setData(langs);
			res.setTotal(langs.size());
			ResponseUtil.setResponse(res, "100", this.messageUtils.getMessage( "api.transaction_completed"));
			return res;
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, "1001", this.messageUtils.getMessage("api.message.inner_server_error"));
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
			ResponseUtil.setResponse(res, "100", this.messageUtils.getMessage( "api.transaction_completed"));
			return res;
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, "1001", this.messageUtils.getMessage("api.message.inner_server_error"));
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
			Map<String, Object> memberMap = this.apiMapper.selectMemberInfo(apiRequest);
			if (memberMap == null) {
				ResponseUtil.setResponse(res, "20002", this.messageUtils.getMessage( "api.message.not_member"));
				throw new ReturnpException(res);
			}
			
			apiRequest.setMemberNo((int)memberMap.get("memberNo"));
			ArrayList<HashMap<String, Object>> bankAccounts = this.apiMapper.selectBankAccounts(apiRequest);
			res.setData(bankAccounts);
			res.setTotal(bankAccounts.size());
			ResponseUtil.setResponse(res, "100", this.messageUtils.getMessage( "api.transaction_completed"));
			return res;
			
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, "1001", this.messageUtils.getMessage("api.message.inner_server_error"));
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
			
			Map<String, Object> memberMap = this.apiMapper.selectMemberInfo(apiRequest);
			if (memberMap == null) {
				ResponseUtil.setResponse(res, "20002", this.messageUtils.getMessage( "api.message.not_member"));
				throw new ReturnpException(res);
			}
			
			apiRequest.setAccountStatus("Y");
			apiRequest.setRegAdminNo(0);
			apiRequest.setRegType("U");
			
			int count = this.apiMapper.createMemberBankAccount(apiRequest);
			if (count < 1) {
				ResponseUtil.setResponse(res, "2007", this.messageUtils.getMessage( "api.register_member_bank_account_failed"));
				throw new ReturnpException(res);
			}
			
			ApiRequest apiCond = new ApiRequest();
			apiCond.setMemberBankAccountNo(apiRequest.getMemberBankAccountNo());
			res.setData(this.apiMapper.selectBankAccount(apiCond));
			ResponseUtil.setResponse(res, "100", this.messageUtils.getMessage( "api.transaction_completed"));
			return res;
			
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, "1001", this.messageUtils.getMessage("api.message.inner_server_error"));
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
			
			Map<String, Object> memberMap = this.apiMapper.selectMemberInfo(apiRequest);
			if (memberMap == null) {
				ResponseUtil.setResponse(res, "20002", this.messageUtils.getMessage( "api.message.not_member"));
				throw new ReturnpException(res);
			}
			apiRequest.setMemberNo((int)memberMap.get("memberNo"));
			int count = this.apiMapper.deleteMemberBankAccount(apiRequest);
			if (count < 1) {
				ResponseUtil.setResponse(res, "2007", this.messageUtils.getMessage( "api.delete_member_bank_account_failed"));
				throw new ReturnpException(res);
			}
			ResponseUtil.setResponse(res, "100", this.messageUtils.getMessage( "api.delete_member_bank_account_success"));
			return res;
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, "1001", this.messageUtils.getMessage("api.message.inner_server_error"));
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
			Map<String, Object> memberInfoMap = this.apiMapper.selectMemberInfo(apiRequest);
			if (memberInfoMap == null) {
				ResponseUtil.setResponse(res, "20002", this.messageUtils.getMessage( "api.message.not_member"));
				throw new ReturnpException(res);
			}
			
			apiRequest.setMemberNo((int)memberInfoMap.get("memberNo"));
			int count = this.apiMapper.updateMemberBankAccount(apiRequest);
			if (count < 1) {
				ResponseUtil.setResponse(res, "2007", this.messageUtils.getMessage( "api.update_member_bank_account_failed"));
				throw new ReturnpException(res);
			}
			
			ApiRequest apiCond = new ApiRequest();
			apiCond.setMemberBankAccountNo(apiRequest.getMemberBankAccountNo());
			res.setData(this.apiMapper.selectBankAccount(apiCond));
			ResponseUtil.setResponse(res, "100", this.messageUtils.getMessage( "api.update_member_bank_account_success"));
			return res;
			
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, "1001", this.messageUtils.getMessage("api.message.inner_server_error"));
			return res;
		}
	}

	/*
	 * 출금 신청 목록 가져오기
	 * memberEmail
	 * (완료) 
	 */
	@Override
	public ReturnpBaseResponse getPointwithdrawals(ApiRequest apiRequest) {
		ArrayResponse<HashMap<String, Object>>  res = new ArrayResponse<HashMap<String, Object>>();
		try {
			Map<String, Object> mameberMap = this.apiMapper.selectMemberInfo(apiRequest);
			if (mameberMap == null) {
				ResponseUtil.setResponse(res, "20002", this.messageUtils.getMessage( "api.message.not_member"));
				throw new ReturnpException(res);
			}
			
			apiRequest.setMemberNo((int)mameberMap.get("memberNo"));
			ArrayList<HashMap<String, Object>> withdrawals = this.apiMapper.selectPointwithdrawals(apiRequest);
			res.setData(withdrawals);
			res.setTotal(withdrawals.size());
			ResponseUtil.setResponse(res, "100", this.messageUtils.getMessage( "api.transaction_completed"));
			return res;
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, "1001", this.messageUtils.getMessage("api.message.inner_server_error"));
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
	public ReturnpBaseResponse withdrawaPoint(ApiRequest apiRequest) {
		ObjectResponse<HashMap<String, Object>> res = new ObjectResponse<HashMap<String, Object>>();
		try {
			Map<String, Object> memberInfoMap = this.apiMapper.selectMemberInfo(apiRequest);
			if (memberInfoMap == null) {
				ResponseUtil.setResponse(res, "20002", this.messageUtils.getMessage( "api.message.not_member"));
				throw new ReturnpException(res);
			}
			
			apiRequest.setMemberNo((int)memberInfoMap.get("memberNo"));
			HashMap<String, Object> redPointMap = this.apiMapper.selectRedPoint(apiRequest);
			if ((float)apiRequest.getWithdrawalAmount() > (float)redPointMap.get("pointAmount")) {
				ResponseUtil.setResponse(res, "20009", this.messageUtils.getMessage( "api.withdrawal_than_balance"));
				throw new ReturnpException(res);
			} 
			
			/*포인트 출금 정보 등록*/
			int count = this.apiMapper.creatPointWithdrawal(apiRequest);
			if (count < 1) {
				ResponseUtil.setResponse(res, "2007", this.messageUtils.getMessage( "api.create_withdrawal_info_failed"));
				throw new ReturnpException(res);
			}
			
			/* 출금한 금액만큼 레드포인트 차감 후 업데이트 */
			ApiRequest ar = new ApiRequest();
			ar.setRedPointNo((int)redPointMap.get("redPointNo"));
			ar.setPointAmount((float)redPointMap.get("pointAmount") - (float)apiRequest.getWithdrawalAmount());
			count = this.apiMapper.updateRedPoint(ar);
			if (count < 1) {
				ResponseUtil.setResponse(res, "2007", this.messageUtils.getMessage( "api.create_withdrawal_info_failed"));
				throw new ReturnpException(res);
			}
			
			/* 등록한 출금 정보 반환*/
			res.setData(this.apiMapper.selectPointwithdrawal(apiRequest));
			ResponseUtil.setResponse(res, "100", this.messageUtils.getMessage( "api.create_withdrawal_info_success"));
			return res;
			
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, "1001", this.messageUtils.getMessage("api.message.inner_server_error"));
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
	public ReturnpBaseResponse deletePointWithdrawal(ApiRequest apiRequest) {
		ObjectResponse<HashMap<String, Object>> res = new ObjectResponse<HashMap<String, Object>>();
		try {
			Map<String, Object> memberInfoMap = this.apiMapper.selectMemberInfo(apiRequest);
			if (memberInfoMap == null) {
				ResponseUtil.setResponse(res, "20002", this.messageUtils.getMessage( "api.message.not_member"));
				throw new ReturnpException(res);
			}
			
			apiRequest.setMemberNo((int)memberInfoMap.get("memberNo"));
			int count = this.apiMapper.deletePointWithdrawal(apiRequest);
			if (count < 1) {
				ResponseUtil.setResponse(res, "2007", this.messageUtils.getMessage( "api.create_withdrawal_info_delete_fail"));
				throw new ReturnpException(res);
			}
			
			ResponseUtil.setResponse(res, "100", this.messageUtils.getMessage( "api.create_withdrawal_info_delete_success"));
			return res;
			
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, "1001", this.messageUtils.getMessage("api.message.inner_server_error"));
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
	public ReturnpBaseResponse updatePointWithdrawal(ApiRequest apiRequest) {
		ObjectResponse<HashMap<String, Object>> res = new ObjectResponse<HashMap<String, Object>>();
		try {
			Map<String, Object> memberInfoMap = this.apiMapper.selectMemberInfo(apiRequest);
			if (memberInfoMap == null) {
				ResponseUtil.setResponse(res, "20002", this.messageUtils.getMessage( "api.message.not_member"));
				throw new ReturnpException(res);
			}
			
			apiRequest.setMemberNo((int)memberInfoMap.get("memberNo"));
			int count = this.apiMapper.updatePointWithdrawal(apiRequest);
			if (count < 1) {
				ResponseUtil.setResponse(res, "2007", this.messageUtils.getMessage( "api.create_withdrawal_info_udpate_fail"));
				throw new ReturnpException(res);
			}
			
			res.setData(this.apiMapper.selectPointwithdrawal(apiRequest));
			ResponseUtil.setResponse(res, "100", this.messageUtils.getMessage( "api.create_withdrawal_info_udpate_success"));
			return res;
			
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, "1001", this.messageUtils.getMessage("api.message.inner_server_error"));
			return res;
		}
	}

	/*
	 * 나의 회원 리스트 
	 *  memberEmail
	 * pointWithdrawalNo
	 * memberBankAccountNo
	 * withdrawalAmount
	 * (완료) 
	 */
	@Override
	public ReturnpBaseResponse getMyMembers(ApiRequest apiRequest) {
		ArrayResponse<HashMap<String, Object>> res = new ArrayResponse<HashMap<String, Object>>();
		try {
			Map<String, Object> memberInfoMap = this.apiMapper.selectMemberInfo(apiRequest);
			if (memberInfoMap == null) {
				ResponseUtil.setResponse(res, "20002", this.messageUtils.getMessage( "api.message.not_member"));
				throw new ReturnpException(res);
			}
			
			apiRequest.setMemberNo((int)memberInfoMap.get("memberNo"));
			ArrayList<HashMap<String, Object>> myMembers = this.apiMapper.selectMyMembers(apiRequest);
			res.setData(myMembers);
			res.setTotal(myMembers.size());
			ResponseUtil.setResponse(res, "100", this.messageUtils.getMessage( "api.transaction_completed"));
			return res;
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, "1001", this.messageUtils.getMessage("api.message.inner_server_error"));
			return res;
		}
	}

	/*
	 * 결제에 해당 하는 포인트 적립
	 * memberEmail
	 */
	@Override
	public ReturnpBaseResponse executeAccumuate(ApiRequest apiRequest) {
		return null;
	}
	
	/*
	 * 결제에 해당 하는 포인트 적립 취소 
	 * memberEmail
	 */
	@Override
	public ReturnpBaseResponse cancelAccumuate(ApiRequest apiRequest) {
		return null;
	}

	/*
	 * G 포인트 적립 내역 
	 * memberEmail
	 */
	@Override
	public ReturnpBaseResponse getGpointAccumuateHistory(ApiRequest apiRequest) {
		return null;
	}

	/*
	 * G 포인트의 R 포인트 전환 내역 
	 * memberEmail
	 */
	@Override
	public ReturnpBaseResponse getRpointConversionHistory(ApiRequest apiRequest) {
		return null;
	}
}
