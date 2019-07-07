package com.returnp.pointback.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.returnp.pointback.common.AppConstants;
import com.returnp.pointback.common.ResponseUtil;
import com.returnp.pointback.common.ReturnpException;
import com.returnp.pointback.dao.mapper.AffiliateMapper;
import com.returnp.pointback.dao.mapper.GreenPointMapper;
import com.returnp.pointback.dao.mapper.PaymentPointbackRecordMapper;
import com.returnp.pointback.dao.mapper.PaymentTransactionMapper;
import com.returnp.pointback.dao.mapper.PointBackMapper;
import com.returnp.pointback.dao.mapper.PolicyMapper;
import com.returnp.pointback.dto.SaidaObject;
import com.returnp.pointback.dto.command.PaymentTransactionCommand;
import com.returnp.pointback.dto.response.ReturnpBaseResponse;
import com.returnp.pointback.model.Affiliate;
import com.returnp.pointback.model.Member;
import com.returnp.pointback.model.PaymentTransaction;
import com.returnp.pointback.service.interfaces.PointbackTargetService;
import com.returnp.pointback.service.interfaces.SaidaPointbackHandleService;
import com.returnp.pointback.web.message.MessageUtils;

@Service
/*@PropertySource("classpath:/messages.properties")*/
public class SaidaPointbackHandleServiceImpl implements SaidaPointbackHandleService {
	
	private Logger logger = Logger.getLogger(SaidaPointbackHandleServiceImpl.class);

	@Autowired PointBackMapper pointBackMapper;
	@Autowired PolicyMapper policyMapper;
	@Autowired AffiliateMapper affiliateMapper;
	@Autowired PaymentTransactionMapper paymentTransactionMapper;
	@Autowired GreenPointMapper greenPointMapper;
	@Autowired PaymentPointbackRecordMapper paymentPointbackRecordMapper;
	@Autowired MessageUtils messageUtils;
	@Autowired Environment env;
	@Autowired PointbackTargetService pointBackTargetService;
	
	@Override
	public ReturnpBaseResponse accumulate(SaidaObject saida) {
		ReturnpBaseResponse res = new ReturnpBaseResponse();
		try {
			Member member = this.validateMemberAuth(saida);
			Affiliate affiliate = this.validateAffiliateAuth(saida);
			this.validateAccumulateRequest(saida);
			
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
	public ReturnpBaseResponse cancelAccumulate(SaidaObject saida) {
		// TODO Auto-generated method stub
		ReturnpBaseResponse res = new ReturnpBaseResponse();
		try {
			Member member = this.validateMemberAuth(saida);
			Affiliate affiliate = this.validateAffiliateAuth(saida);
			this.validateCancelRequest(saida);
			
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
	public Member validateMemberAuth(SaidaObject saida) throws ReturnpException {
		try {
			ReturnpBaseResponse res = new ReturnpBaseResponse();
			String phoneNumber = null;
			String phoneNumberCountry = null;
			
			if (saida.getRecvPhone().contains("+")) {
				phoneNumberCountry = saida.getRecvPhone().trim(); 
				phoneNumber = "0" + (phoneNumberCountry.substring(phoneNumberCountry.length() - 10, phoneNumberCountry.length()));
			}else {
				phoneNumber = saida.getRecvPhone().trim(); 
				phoneNumberCountry = "+82" + phoneNumber.substring(1); 
			}
			
			Member member = new Member();
			member.setMemberPhone(phoneNumber);
			
			/* 전화번호에 의한 회원 존재 여부 검사*/
			boolean validMember = true;
			ArrayList<Member> members = this.pointBackMapper.findMembers(member);
			
			/*  기본 전화번호가  2개 이상 등록되어 있음  */
			if (members.size() > 1) {
				validMember = false;
				ResponseUtil.setResponse(
						res, ResponseUtil.RESPONSE_OK, "5109", member.getMemberPhone() +  " 전화번호가 2개 이상이 등록되어 있습니다.");
			} 
			
			/*  기본 전화번호가 등록되어 있지 않은 경우, 국가별 핸드폰 형태 번호로 재 조회 */
			else if (members.size() <1) {
				member.setMemberPhone(phoneNumberCountry);
				members = this.pointBackMapper.findMembers(member);
				if (members.size() < 1) { 
					validMember = false;
					ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "5111", 
							this.messageUtils.getMessage("pointback.message.not_argu_member", new Object[] {member.getMemberPhone()}));
				}else if (members.size()> 1 ) {
					validMember = false;
					ResponseUtil.setResponse(
							res, ResponseUtil.RESPONSE_ERROR, "5109", member.getMemberPhone() +  " 전화번호가 2개 이상이 등록되어 있습니다.");
				}
			}
			
			/* 리턴포인트 회원이 아니더라도 별도의 테이블 혹은 파일에 결제 내역을 저장할 수 있음*/
			if (!validMember) {
				throw new ReturnpException(res);
			}
			return members.get(0);
			
		}catch (ReturnpException e1) {
			e1.printStackTrace();
			throw e1;
		} catch (Exception e2) {
			e2.printStackTrace();
			throw e2;
		}
		
	}

	@Override
	public Affiliate validateAffiliateAuth(SaidaObject saida) throws ReturnpException {
		try {
			ReturnpBaseResponse res = new ReturnpBaseResponse();
			Member member= new Member();
			member.setMemberEmail(saida.getUserId());
			ArrayList<Member> members = this.pointBackMapper.findMembers(member);
			if (members.size() !=1) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "5087", 
						this.messageUtils.getMessage("pointback.message.not_exist_affiliater", new Object[] {saida.getUserId()}));
				throw new ReturnpException(res);
				
			}
			
			Affiliate affiliate= new Affiliate();
			affiliate.setMemberNo(members.get(0).getMemberNo());
			ArrayList<Affiliate> affiliates = this.pointBackMapper.findAffiliates(affiliate);
			
			if (affiliates.size() != 1) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "5087", 
						this.messageUtils.getMessage("pointback.message.not_exist_affiliater", new Object[] {saida.getUserId()}));
				throw new ReturnpException(res);
			}
			return affiliates.get(0);
			
		}catch (ReturnpException e1) {
			e1.printStackTrace();
			throw e1;
		} catch (Exception e2) {
			e2.printStackTrace();
			throw e2;
		}
	}

	@Override
	public void validateAccumulateRequest(SaidaObject saida) throws ReturnpException {
		ReturnpBaseResponse res =  new ReturnpBaseResponse();
		try {
			
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
			/*
			 * PaymentTransaction paymentTransaction = new PaymentTransaction();
				ArrayList<PaymentTransaction> paymentTransactions = this.pointBackMapper.findPaymentTransactions(paymentTransaction);
			 */

			/*
			 * 결제 번호 및 승인 일시로 조회하여 이미 등록된 결제 정보가 있는지 조회
			 * */
			
			PaymentTransactionCommand ptCommand = new PaymentTransactionCommand();
			ptCommand.setPaymentApprovalNumber(saida.getPaymentApprovalNumber());
			ptCommand.setPaymentRouterType(saida.getPaymentRouterType());
			ptCommand.setPaymentRouterName(saida.getPaymentRouterName());
			ptCommand.setPaymentApprovalDateTime(saida.getPayDate());
			
			ArrayList<PaymentTransactionCommand> paymentTransactions = this.pointBackMapper.findPaymentTransactionCommands(ptCommand);

			logger.info("------------------------------------- 적립 결제 정보---------------------------------------");
			System.out.println("Payment Router Type  : "  + saida.getPaymentRouterType());
			System.out.println("Payment Router name  : "  + saida.getPaymentRouterName());
			System.out.println("Approval Payment Number : "  + saida.getPaymentApprovalNumber());
			System.out.println("Approval Payment Date Time : "  + df.format(saida.getPayDate()));
			
			if (paymentTransactions.size() >  0  ) {
				logger.info("기 등록된 결제의 승인 일시 : "  + df.format(paymentTransactions.get(0).getPaymentApprovalDateTime())); 
			}
			
			logger.info("----------------------------------------------------------------------------------------------");
			
			PaymentTransaction paymentTransaction = new PaymentTransaction();
			if (paymentTransactions.size() >  1  ) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "604", this.messageUtils.getMessage("pointback.message.already_accumulate_or_canceled"));
					throw new ReturnpException(res);
			}
			/* 
			 * 결제 번호가 같을 경우 바로 에러 처리를 해주어야 하나, 상태별 에러 메시지를 뿌려주기 위해 
			 * 아래의 처리를 진행함 
			 * */
			if (paymentTransactions.size() == 1) {
				paymentTransaction = paymentTransactions.get(0);
				 //부적절한 요청 - 현재 적립 처리중인 내역에 대한 중복 적립 요청
				 if (paymentTransaction.getPaymentApprovalStatus().equals(AppConstants.PaymentApprovalStatus.PAYMENT_APPROVAL_OK)) {
					 if (paymentTransaction.getPointBackStatus().equals(AppConstants.AccumulateStatus.POINTBACK_START) || 
							 paymentTransaction.getPointBackStatus().equals(AppConstants.AccumulateStatus.POINTBACK_PROGRESS)) {
						 ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "605", this.messageUtils.getMessage("pointback.message.already_accumulating_req"));
						 throw new ReturnpException(res);
					 }
					
					  //부적절한 요청 - 적립 처리가 완료된 내역에 대한 중복 적립 요청
					if (paymentTransaction.getPointBackStatus().equals(AppConstants.AccumulateStatus.POINTBACK_COMPLETE) ) {
						ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "606", this.messageUtils.getMessage("pointback.message.already_complete_req"));
						throw new ReturnpException(res);
					}
					
					 //부적절한 요청 - 적립 처리 에러 내역에 대한 중복 적립 요청
					if (paymentTransaction.getPointBackStatus().equals(AppConstants.AccumulateStatus.POINTBACK_STOP) ) {
						ResponseUtil.setResponse(res,ResponseUtil.RESPONSE_OK,  "607", this.messageUtils.getMessage("pointback.message.error_accumulate"));
						throw new ReturnpException(res);
					}
				}else if (paymentTransaction.getPaymentApprovalStatus().equals(AppConstants.PaymentApprovalStatus.PAYMENT_APPROVAL_CANCEL)){
					 //이미 결제 취소된 중이거나 결제 취소가 완료, 혹은 결제 취소 에러가 발생한 내역에 대한 적립 요청인 경우 - 부적절한 적립 요청  
					ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "608", this.messageUtils.getMessage("pointback.message.error_acc_req_to_already_canceled_payement"));
					throw new ReturnpException(res);
				}else if (paymentTransaction.getPaymentApprovalStatus().equals(AppConstants.PaymentApprovalStatus.PAYMENT_APPROVAL_ERROR)){
					ResponseUtil.setResponse(res,ResponseUtil.RESPONSE_OK,  "609", this.messageUtils.getMessage("pointback.message.error_paymenttransaction"));
					throw new ReturnpException(res);
				}else {
					 ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "610", this.messageUtils.getMessage("pointback.message.invalid_req"));
						throw new ReturnpException(res);
				}
			 }
		} catch(ReturnpException ee) {
			ee.printStackTrace();
			throw ee;
		} catch(Exception e) {
			e.printStackTrace();
			throw e;
		}
		
	}

	@Override
	public PaymentTransaction createPaymentTransaction(SaidaObject saida) throws ReturnpException {
		// TODO Auto-generated method stub
		return null;
	}


	@Override
	public PaymentTransaction validateCancelRequest(SaidaObject saida) throws ReturnpException {
		// TODO Auto-generated method stub
		return null;
	}
}
