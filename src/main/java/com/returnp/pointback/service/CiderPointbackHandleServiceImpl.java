package com.returnp.pointback.service;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.returnp.pointback.common.AppConstants;
import com.returnp.pointback.common.DataMap;
import com.returnp.pointback.common.ResponseUtil;
import com.returnp.pointback.common.ReturnpException;
import com.returnp.pointback.dao.mapper.AffiliateMapper;
import com.returnp.pointback.dao.mapper.GreenPointMapper;
import com.returnp.pointback.dao.mapper.PaymentPointbackRecordMapper;
import com.returnp.pointback.dao.mapper.PaymentTransactionMapper;
import com.returnp.pointback.dao.mapper.PaymentTransactionRouterMapper;
import com.returnp.pointback.dao.mapper.PointBackMapper;
import com.returnp.pointback.dao.mapper.PolicyMapper;
import com.returnp.pointback.dto.CiderObject;
import com.returnp.pointback.dto.response.ReturnpBaseResponse;
import com.returnp.pointback.model.Affiliate;
import com.returnp.pointback.model.Member;
import com.returnp.pointback.model.PaymentTransaction;
import com.returnp.pointback.service.interfaces.CiderPointbackHandleService;
import com.returnp.pointback.service.interfaces.PointbackTargetService;
import com.returnp.pointback.service.interfaces.ReturnpTransactionService;
import com.returnp.pointback.web.message.MessageUtils;

@Service
/*@PropertySource("classpath:/messages.properties")*/
public class CiderPointbackHandleServiceImpl implements CiderPointbackHandleService {
	
	private Logger logger = Logger.getLogger(CiderPointbackHandleServiceImpl.class);

	@Autowired PointBackMapper pointBackMapper;
	@Autowired PolicyMapper policyMapper;
	@Autowired AffiliateMapper affiliateMapper;
	@Autowired PaymentTransactionMapper paymentTransactionMapper;
	@Autowired GreenPointMapper greenPointMapper;
	@Autowired PaymentPointbackRecordMapper paymentPointbackRecordMapper;
	@Autowired MessageUtils messageUtils;
	@Autowired Environment env;
	@Autowired PointbackTargetService pointBackTargetService;
	@Autowired PaymentTransactionRouterMapper paymentTransactionRouterMapper;
	@Autowired ReturnpTransactionService returnpTransactionService;
	@Override
	public ReturnpBaseResponse accumulate(CiderObject cider) {
		ReturnpBaseResponse res = new ReturnpBaseResponse();
		try {
			
			/* 
			 * 전화번호로 회원 유효성 검사
			 * */
			Member member = new Member();
			String phoneNumber = null;
			String phoneNumberCountry = null;
			
			if (cider.getRecvPhone().contains("+")) {
				phoneNumberCountry = cider.getRecvPhone().trim(); 
				phoneNumber = "0" + (phoneNumberCountry.substring(phoneNumberCountry.length() - 10, phoneNumberCountry.length()));
			}else {
				phoneNumber = cider.getRecvPhone().trim(); 
				phoneNumberCountry = "+82" + phoneNumber.substring(1); 
			}
			
			member.setMemberPhone(phoneNumber);
			ArrayList<Member> members = this.pointBackMapper.findMembers(member);
			
			/*  기본 전화번호가  2개 이상 등록되어 있음  */
			if (members.size() > 1) {
				ResponseUtil.setResponse(
						res, ResponseUtil.RESPONSE_OK, "5109", member.getMemberPhone() +  " 전화번호가 2개 이상이 등록되어 있습니다.");
				throw new ReturnpException(res);
			} 
			
			/*  기본 전화번호가 등록되어 있지 않은 경우, 국가별 핸드폰 형태 번호로 재 조회 */
			else if (members.size() <1) {
				member.setMemberPhone(phoneNumberCountry);
				members = this.pointBackMapper.findMembers(member);
				if (members.size() < 1) { 
					ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "5111", 
							this.messageUtils.getMessage("pointback.message.not_argu_member", new Object[] {member.getMemberPhone()}));
					throw new ReturnpException(res);
				}else if (members.size()> 1 ) {
					ResponseUtil.setResponse(
							res, ResponseUtil.RESPONSE_ERROR, "5109", member.getMemberPhone() +  " 전화번호가 2개 이상이 등록되어 있습니다.");
					throw new ReturnpException(res);
				}
			}
			
			/* 
			 * UserId 로 가맹점 유효성 검사 
			 * */
			Member member2= new Member();
			member2.setMemberEmail(cider.getUserId());
			ArrayList<Member> members2 = this.pointBackMapper.findMembers(member);
			if (members2.size() !=1) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "5087", 
						this.messageUtils.getMessage("pointback.message.not_exist_affiliater", new Object[] {cider.getUserId()}));
				throw new ReturnpException(res);
				
			}
			
			Affiliate affiliate= new Affiliate();
			affiliate.setMemberNo(members.get(0).getMemberNo());
			ArrayList<Affiliate> affiliates = this.pointBackMapper.findAffiliates(affiliate);;
			if (affiliates.size() != 1) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "5087", 
						this.messageUtils.getMessage("pointback.message.not_exist_affiliater", new Object[] {cider.getUserId()}));
				throw new ReturnpException(res);
			}
			
			/*
			 * Cider 오브젝트를 DataMap 로 변환
			 */
			DataMap dataMap = new DataMap();
			dataMap.put("pam", cider.getPrice());
			dataMap.put("pas", "0");
			dataMap.put("pat", cider.getPayDate());
			dataMap.put("pan", cider.getPaymentApprovalNumber());
			
			/* CIDER 앱으로 결제 적립 하는 경우, af_id 는 null 로 세팅*/
			dataMap.put("af_id", null);
			
			dataMap.put("phoneNumber", phoneNumber.trim());
			dataMap.put("phoneNumberCountry", phoneNumberCountry.trim());
			dataMap.put("memberEmail", members.get(0).getMemberEmail());
			
			dataMap.put("payment_router_type", cider.getPaymentRouterType());
			dataMap.put("payment_router_name", cider.getPaymentRouterName());
			dataMap.put("payment_transaction_type", AppConstants.PaymentTransactionType.APP);
			
			this.returnpTransactionService.validateAccumulateRequest(dataMap);
			PaymentTransaction paymentTransaction = this.returnpTransactionService.createPaymentTransaction(dataMap);
			this.returnpTransactionService.accumuatePoint(paymentTransaction);
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "100", this.messageUtils.getMessage("pointback.message.success_acc_ok"));
			return res;
			
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			res = e.getBaseResponse();
			return res;
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR, "2000", this.messageUtils.getMessage("pointback.message.inner_server_error"));
			return res;
		}
	}
	@Override
	public ReturnpBaseResponse cancelAccumulate(CiderObject cider) {
		ReturnpBaseResponse res = new ReturnpBaseResponse();
		try {
			/* 
			 * 전화번호로 회원 유효성 검사
			 */
			Member member = new Member();
			String phoneNumber = null;
			String phoneNumberCountry = null;
			
			if (cider.getRecvPhone().contains("+")) {
				phoneNumberCountry = cider.getRecvPhone().trim(); 
				phoneNumber = "0" + (phoneNumberCountry.substring(phoneNumberCountry.length() - 10, phoneNumberCountry.length()));
			}else {
				phoneNumber = cider.getRecvPhone().trim(); 
				phoneNumberCountry = "+82" + phoneNumber.substring(1); 
			}
			
			member.setMemberPhone(phoneNumber);
			ArrayList<Member> members = this.pointBackMapper.findMembers(member);
			
			/*  기본 전화번호가  2개 이상 등록되어 있음  */
			if (members.size() > 1) {
				ResponseUtil.setResponse(
						res, ResponseUtil.RESPONSE_OK, "5109", member.getMemberPhone() +  " 전화번호가 2개 이상이 등록되어 있습니다.");
				throw new ReturnpException(res);
			} 
			
			/*  기본 전화번호가 등록되어 있지 않은 경우, 국가별 핸드폰 형태 번호로 재 조회 */
			else if (members.size() <1) {
				member.setMemberPhone(phoneNumberCountry);
				members = this.pointBackMapper.findMembers(member);
				if (members.size() < 1) { 
					ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "5111", 
							this.messageUtils.getMessage("pointback.message.not_argu_member", new Object[] {member.getMemberPhone()}));
					throw new ReturnpException(res);
				}else if (members.size()> 1 ) {
					ResponseUtil.setResponse(
							res, ResponseUtil.RESPONSE_ERROR, "5109", member.getMemberPhone() +  " 전화번호가 2개 이상이 등록되어 있습니다.");
					throw new ReturnpException(res);
				}
			}
			
			/* 
			 * UserId 로 가맹점 유효성 검사 
			 */
			Member member2= new Member();
			member2.setMemberEmail(cider.getUserId());
			ArrayList<Member> members2 = this.pointBackMapper.findMembers(member);
			if (members2.size() !=1) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "5087", 
						this.messageUtils.getMessage("pointback.message.not_exist_affiliater", new Object[] {cider.getUserId()}));
				throw new ReturnpException(res);
				
			}
			
			Affiliate affiliate= new Affiliate();
			affiliate.setMemberNo(members.get(0).getMemberNo());
			ArrayList<Affiliate> affiliates = this.pointBackMapper.findAffiliates(affiliate);;
			if (affiliates.size() != 1) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "5087", 
						this.messageUtils.getMessage("pointback.message.not_exist_affiliater", new Object[] {cider.getUserId()}));
				throw new ReturnpException(res);
			}
			
			/*Cider 오브젝트를 DataMap 로 변환*/
			DataMap dataMap = new DataMap();

			dataMap.put("pam", cider.getPrice());
			dataMap.put("pas", "1");
			dataMap.put("pat", cider.getPayDate());
			dataMap.put("pan", cider.getPaymentApprovalNumber());
			
			/* CIDER 앱으로 결제 적립 하는 경우, af_id 는 null 로 세팅*/
			dataMap.put("af_id", null);

			dataMap.put("phoneNumber", phoneNumber.trim());
			dataMap.put("phoneNumberCountry", phoneNumberCountry.trim());
			dataMap.put("memberEmail", members.get(0).getMemberEmail());
			
			dataMap.put("payment_router_type", "VAN");
			dataMap.put("payment_router_name", "CIDER");
			dataMap.put("payment_transaction_type", AppConstants.PaymentTransactionType.APP);
			
			this.returnpTransactionService.validateCancelRequest(dataMap);
			this.returnpTransactionService.restorePoint(dataMap);
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "100", this.messageUtils.getMessage("pointback.cancel_accumulate_ok"));
			return res;
			
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			res = e.getBaseResponse();
			return res;
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR, "2000", this.messageUtils.getMessage("pointback.message.inner_server_error"));
			return res;
		}
	}


}
