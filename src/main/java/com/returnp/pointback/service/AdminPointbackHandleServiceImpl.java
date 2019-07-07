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
import com.returnp.pointback.common.DataMap;
import com.returnp.pointback.common.ResponseUtil;
import com.returnp.pointback.common.ReturnpException;
import com.returnp.pointback.dao.mapper.AffiliateMapper;
import com.returnp.pointback.dao.mapper.GreenPointMapper;
import com.returnp.pointback.dao.mapper.PaymentPointbackRecordMapper;
import com.returnp.pointback.dao.mapper.PaymentTransactionMapper;
import com.returnp.pointback.dao.mapper.PointBackMapper;
import com.returnp.pointback.dao.mapper.PolicyMapper;
import com.returnp.pointback.dto.command.AffiliateCommand;
import com.returnp.pointback.dto.command.AffiliateTidCommand;
import com.returnp.pointback.dto.command.InnerPointBackTarget;
import com.returnp.pointback.dto.command.OuterPointBackTarget;
import com.returnp.pointback.dto.command.PaymentTransactionCommand;
import com.returnp.pointback.dto.response.ReturnpBaseResponse;
import com.returnp.pointback.model.Affiliate;
import com.returnp.pointback.model.GreenPoint;
import com.returnp.pointback.model.Member;
import com.returnp.pointback.model.PaymentPointbackRecord;
import com.returnp.pointback.model.PaymentTransaction;
import com.returnp.pointback.model.Policy;
import com.returnp.pointback.model.SoleDist;
import com.returnp.pointback.service.interfaces.AdminPointbackHandleService;
import com.returnp.pointback.service.interfaces.PointbackTargetService;
import com.returnp.pointback.service.interfaces.ReturnpTransactionService;
import com.returnp.pointback.web.message.MessageUtils;

@Service
/*@PropertySource("classpath:/messages.properties")*/
public class AdminPointbackHandleServiceImpl implements AdminPointbackHandleService {
	
	private Logger logger = Logger.getLogger(AdminPointbackHandleServiceImpl.class);
	
	@Autowired PointBackMapper pointBackMapper;
	@Autowired PolicyMapper policyMapper;
	@Autowired AffiliateMapper affiliateMapper;
	@Autowired PaymentTransactionMapper paymentTransactionMapper;
	@Autowired GreenPointMapper greenPointMapper;
	@Autowired PaymentPointbackRecordMapper paymentPointbackRecordMapper;
	@Autowired MessageUtils messageUtils;
	@Autowired Environment env;
	@Autowired PointbackTargetService pointBackTargetService;
	 @Autowired ReturnpTransactionService  returnpTransactionService;
	
	public static class Command {
		public static class Control{
			
		}
		public static class Request {
			public static final String  PAYMENT_APPROVAL = "PAYMENT_APPROVAL";
			public static final String PAYMENT_CANCEL = "PAYMENT_CANCEL";
		}
	}
	
	ArrayList<String> keys;
	ArrayList<Integer> pointAccList = new ArrayList<Integer>();

	 /**
	 * 기본 초기화 작업
	 * 외부 연결 허용 키 목록 세팅
	 * 이후 연결 키 생성 기능구현시, 디비로 관리 
	 */
/*	@PostConstruct
	 public void init() {
		 keys = new ArrayList<String>(Arrays.asList(env.getProperty("keys").trim().split(",")));
	 }*/
	
	@Override
	public ReturnpBaseResponse accumulate(DataMap dataMap) {
			return this.returnpTransactionService.accumulate(dataMap);
	}

	@Override
	public ReturnpBaseResponse cancelAccumulate(DataMap dataMap) {
		return this.returnpTransactionService.cancelAccumulate(dataMap);
	}
	
	@Override
	public ReturnpBaseResponse  accumuatePoint(int paymentTransactioinNo) throws ReturnpException {
		ReturnpBaseResponse res = new ReturnpBaseResponse();
		try {
			PaymentTransaction paymentTransaction = this.paymentTransactionMapper.selectByPrimaryKey(paymentTransactioinNo);
			if (paymentTransaction == null) {
				 ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "620", this.messageUtils.getMessage("pointback.message.not_payment_invalid_req"));
					throw new ReturnpException(res);
			}
			this.returnpTransactionService.accumuatePoint(paymentTransaction);
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "100", this.messageUtils.getMessage("pointback.message.success_acc_ok"));
			return res;
		}catch(ReturnpException e) {
			e.printStackTrace();
			res = e.getBaseResponse();
			return res;
		}catch(Exception e) {
			e.printStackTrace();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR,"2000", this.messageUtils.getMessage("pointback.message.inner_server_error"));
			return res;
		}
	}
	
	@Override
	public ReturnpBaseResponse accumuatePoint(String paymentApprovalNumber) throws ReturnpException {
		ReturnpBaseResponse res = new ReturnpBaseResponse();
		try {
			PaymentTransaction pt = new PaymentTransaction();
			pt.setPaymentApprovalNumber(paymentApprovalNumber);
			ArrayList<PaymentTransaction> paymentTransactions = this.pointBackMapper.findPaymentTransactions(pt);
			if (paymentTransactions.size() != 1) {
				 ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "621", this.messageUtils.getMessage("pointback.message.not_payment_invalid_req"));
					throw new ReturnpException(res);
			}		
			this.returnpTransactionService.accumuatePoint(paymentTransactions.get(0));
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
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR,"2000", this.messageUtils.getMessage("pointback.message.inner_server_error"));
			return res;
		}
	}
	
	
	
	/* 
	 * 결제 번호에 의한 적립 취소
	 */
	@Override
	public ReturnpBaseResponse cancelAccumuate(String pan) {
		DataMap dataMap = null;
		ReturnpBaseResponse res = new ReturnpBaseResponse();
		ArrayList<PaymentTransactionCommand> ptList = null;
		PaymentTransactionCommand com = null;
		try {
			com = new PaymentTransactionCommand();
			com.setPaymentApprovalNumber(pan);
			ptList = this.pointBackMapper.findPaymentTransactionCommands(com);
			if (ptList == null || ptList.size() !=1) {
				 ResponseUtil.setResponse(res,ResponseUtil.RESPONSE_OK,  "624", this.messageUtils.getMessage("pointback.message.not_existed_payment"));
					throw new ReturnpException(res);
			}
			
			dataMap = this.returnpTransactionService.convertPaymentTransactionToDataMap(ptList.get(0));
			dataMap.put("payment_router_type", ptList.get(0).getPaymentRouterType());
			dataMap.put("payment_router_name", ptList.get(0).getPaymentRouterName());
			dataMap.put("payment_transaction_type", AppConstants.PaymentTransactionType.MANUAL);
			dataMap.put("cancel_from", "admin");
			
			return this.returnpTransactionService.cancelAccumulate(dataMap);
		}catch(ReturnpException e) {
			e.printStackTrace();
			if (!TransactionAspectSupport.currentTransactionStatus().isRollbackOnly()) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
			res = e.getBaseResponse();
			return res;
		}catch(Exception e) {
			e.printStackTrace();
			if (!TransactionAspectSupport.currentTransactionStatus().isRollbackOnly()) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR,"2000", this.messageUtils.getMessage("pointback.message.inner_server_error"));
			return res;
		}
	}

	/* 
	 * 결제내역 번호에 의한 취소 처리 
	 * 결제 취소 유효성 검사를 진행
	 */
	@Override
	public ReturnpBaseResponse cancelAccumuate(int paymentTrasactionNo) {
		ReturnpBaseResponse res = new ReturnpBaseResponse();
		DataMap dataMap = null;
		
		ArrayList<PaymentTransactionCommand> ptList  = null;
		PaymentTransactionCommand com = new PaymentTransactionCommand();
		com.setPaymentTransactionNo(paymentTrasactionNo);
		
		ptList  =this.pointBackMapper.findPaymentTransactionCommands(com);
				
		try {
			if (ptList.size() == 0) {
				 ResponseUtil.setResponse(res,ResponseUtil.RESPONSE_OK,  "625", this.messageUtils.getMessage("pointback.message.not_existed_payment"));
					throw new ReturnpException(res);
			}
			
			dataMap = this.returnpTransactionService.convertPaymentTransactionToDataMap(ptList.get(0));
			dataMap.put("payment_router_type", ptList.get(0).getPaymentRouterType());
			dataMap.put("payment_router_name", ptList.get(0).getPaymentRouterName());
			dataMap.put("payment_transaction_type", AppConstants.PaymentTransactionType.MANUAL);
			
			/*요청처리를 결제 쥐소로 파라메터를 변경*/
			dataMap.put("pas", "1");  
			return this.returnpTransactionService.cancelAccumulate(dataMap);
		}catch(ReturnpException e) {
			e.printStackTrace();
			res = e.getBaseResponse();
			return res;
		}catch(Exception e) {
			e.printStackTrace();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR,"2000", this.messageUtils.getMessage("pointback.message.inner_server_error"));
			return res;
		}
	}
	
	/* 
	 * 결제내역 번호에 의한 강제 취소 처리 
	 * 유효성 검사를 하지 않는 무조건 적인 강제 취소 처리 
	 */
	@Override
	public ReturnpBaseResponse forcedCancelAccumuate(int paymentTrasactionNo) {
		ReturnpBaseResponse res = new ReturnpBaseResponse();
		DataMap dataMap = null;
		
		ArrayList<PaymentTransactionCommand> ptList  = null;
		PaymentTransactionCommand com = new PaymentTransactionCommand();
		com.setPaymentTransactionNo(paymentTrasactionNo);
		
		ptList  =this.pointBackMapper.findPaymentTransactionCommands(com);
				
		try {
			if (ptList.size() == 0) {
				 ResponseUtil.setResponse(res,ResponseUtil.RESPONSE_OK,  "665", this.messageUtils.getMessage("pointback.message.not_existed_payment"));
					throw new ReturnpException(res);
			}
			
			dataMap = this.returnpTransactionService.convertPaymentTransactionToDataMap(ptList.get(0));
			dataMap.put("payment_router_type", ptList.get(0).getPaymentRouterType());
			dataMap.put("payment_router_name", ptList.get(0).getPaymentRouterName());
			dataMap.put("payment_transaction_type", AppConstants.PaymentTransactionType.MANUAL);
			
			/*요청처리를 결제 쥐소로 파라메터를 변경*/
			dataMap.put("pas", "1");  
			
			/*유효성 검사를 하지 않는 강제 처리 플래그 설정*/
			dataMap.put("forceCancel", "Y");
			return this.returnpTransactionService.cancelAccumulate(dataMap);
		}catch(ReturnpException e) {
			e.printStackTrace();
			if (!TransactionAspectSupport.currentTransactionStatus().isRollbackOnly()) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
			res = e.getBaseResponse();
			return res;
		}catch(Exception e) {
			e.printStackTrace();
			if (!TransactionAspectSupport.currentTransactionStatus().isRollbackOnly()) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR,"2000", this.messageUtils.getMessage("pointback.message.inner_server_error"));
			return res;
		}
	}
	
	/* 
	 * 결제내역 번호에 의한 강제 적립 처리  
	 * 유효성 검사를 하지 않는 무조건 적인 강제 취소 처리 
	 */
	@Override
	public ReturnpBaseResponse forcedAccumuate(int paymentTrasactionNo) {
		DataMap dataMap = null;
		ReturnpBaseResponse res = new ReturnpBaseResponse();
		PaymentTransaction pt = null;
		try {
			pt = paymentTransactionMapper.selectByPrimaryKey(paymentTrasactionNo);
			if (pt == null) {
				 ResponseUtil.setResponse(res,ResponseUtil.RESPONSE_OK,  "628", this.messageUtils.getMessage("pointback.message.not_existed_payment"));
					throw new ReturnpException(res);
			}
			
			dataMap = this.returnpTransactionService.convertPaymentTransactionToDataMap(pt);
			/*요청처리를 결제 승인으로 파라메터를 변경*/
			dataMap.put("pas", "0");  
			
			/*유효성 검사를 하지 않는 강제 처리 플래그 설정*/
			dataMap.put("forceAcc", "Y");
			return this.returnpTransactionService.accumulate(dataMap);
		}catch(ReturnpException e) {
			e.printStackTrace();
			if (!TransactionAspectSupport.currentTransactionStatus().isRollbackOnly()) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
			res = e.getBaseResponse();
			return res;
		}catch(Exception e) {
			e.printStackTrace();
			if (!TransactionAspectSupport.currentTransactionStatus().isRollbackOnly()) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR,"2000", this.messageUtils.getMessage("pointback.message.inner_server_error"));
			return res;
		}
	}

	

}
