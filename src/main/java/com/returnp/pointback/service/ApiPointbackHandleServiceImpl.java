package com.returnp.pointback.service;

import java.util.ArrayList;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.returnp.pointback.common.AppConstants;
import com.returnp.pointback.common.DataMap;
import com.returnp.pointback.common.ResponseUtil;
import com.returnp.pointback.common.ReturnpException;
import com.returnp.pointback.dto.command.api.ApiRequest;
import com.returnp.pointback.dto.response.ReturnpBaseResponse;
import com.returnp.pointback.model.PaymentTransaction;
import com.returnp.pointback.service.interfaces.ApiPointbackHandleService;
import com.returnp.pointback.service.interfaces.ReturnpTransactionService;
import com.returnp.pointback.web.message.MessageUtils;

@Service
/*@PropertySource("classpath:/messages.properties")*/
public class ApiPointbackHandleServiceImpl implements ApiPointbackHandleService {
    
    private Logger logger = Logger.getLogger(ApiPointbackHandleServiceImpl.class);
    
    @Autowired MessageUtils messageUtils;
    @Autowired ReturnpTransactionService  returnpTransactionService;
    
    
    ArrayList<String> keys;
    ArrayList<Integer> pointAccList = new ArrayList<Integer>();

     /**
     * 기본 초기화 작업
     * 외부 연결 허용 키 목록 세팅
     * 이후 연결 키 생성 기능구현시, 디비로 관리 
     */
/*  @PostConstruct
     public void init() {
         keys = new ArrayList<String>(Arrays.asList(env.getProperty("keys").trim().split(",")));
     }*/
    
    @Override
    public ReturnpBaseResponse accumulate(DataMap dataMap) {
        ReturnpBaseResponse res = new ReturnpBaseResponse();
        
        try {
            switch(dataMap.getStr("payment_transaction_type").trim()){
                case AppConstants.PaymentTransactionType.QR: break;
                case AppConstants.PaymentTransactionType.MANUAL: break;
                case AppConstants.PaymentTransactionType.APP: break;
                case AppConstants.PaymentTransactionType.API: break;
            }
            
            this.returnpTransactionService.validateMemberAndGet(dataMap.getStr("memberEmail"),dataMap.getStr("phoneNumber"),dataMap.getStr("phoneNumberCountry"));
            this.returnpTransactionService.validateAffiliateAuthAndGet(dataMap.getStr("payment_router_type"), dataMap.getStr("payment_router_name"), dataMap.getStr("af_id"));
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
    public ReturnpBaseResponse cancelAccumulate(DataMap dataMap) {
        ReturnpBaseResponse res = new ReturnpBaseResponse();

        try {
            switch(dataMap.getStr("payment_transaction_type").trim()){
                case AppConstants.PaymentTransactionType.QR: break;
                case AppConstants.PaymentTransactionType.MANUAL: break;
                case AppConstants.PaymentTransactionType.APP: break;
                case AppConstants.PaymentTransactionType.API: break;
            }
            
            this.returnpTransactionService.validateMemberAndGet(dataMap.getStr("memberEmail"),dataMap.getStr("phoneNumber"),dataMap.getStr("phoneNumberCountry"));
            this.returnpTransactionService.validateAffiliateAuthAndGet(dataMap.getStr("payment_router_type"), dataMap.getStr("payment_router_name"), dataMap.getStr("af_id"));
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
            ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR,"2000", this.messageUtils.getMessage("pointback.message.inner_server_error"));
            return res;
        }
    }

	/* 
	 * G POINT 결제 승인
	 * 결제한 G POINT 를 차감한 후 DB insert
	 */
	@Override
	public ReturnpBaseResponse gpointPayApproval(ApiRequest apiRequest) {
		ReturnpBaseResponse res = new ReturnpBaseResponse();

        try {
        	switch(apiRequest.getPaymentTransactionType().trim()){
             case AppConstants.PaymentTransactionType.QR: break;
             case AppConstants.PaymentTransactionType.MANUAL: break;
             case AppConstants.PaymentTransactionType.APP: break;
             case AppConstants.PaymentTransactionType.API: break;
        	 }
        	 this.returnpTransactionService.validateMemberAndGet(apiRequest.getMemberEmail(), apiRequest.getMemberPhone(), apiRequest.getMemberPhone());
        	 this.returnpTransactionService.validateAffiliateAuthAndGet(apiRequest.getPaymentRouterType(), apiRequest.getPaymentRouterName(), apiRequest.getAfId());
        	 return this.returnpTransactionService.gpointPaymentApporval(apiRequest);
         /*    ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "100", this.messageUtils.getMessage("pointback.message.gpoint_payment_ok"));
            return res;*/
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
	 * G POINT 결제 취소
	 * 결제한 G POINT 를 회복한 후 DB insert
	 */
	@Override
	public ReturnpBaseResponse gpointPayCancel(ApiRequest apiRequest) {
		ReturnpBaseResponse res = null;
		try {
			 switch(apiRequest.getPaymentTransactionType().trim()){
             case AppConstants.PaymentTransactionType.QR: break;
             case AppConstants.PaymentTransactionType.MANUAL: break;
             case AppConstants.PaymentTransactionType.APP: break;
             case AppConstants.PaymentTransactionType.API: break;
        	 }
			 this.returnpTransactionService.validateMemberAndGet(apiRequest.getMemberEmail(), apiRequest.getMemberPhone(), apiRequest.getMemberPhone());
        	 this.returnpTransactionService.validateAffiliateAuthAndGet(apiRequest.getPaymentRouterType(), apiRequest.getPaymentRouterName(), apiRequest.getAfId());
        	 return this.returnpTransactionService.gpointPaymentCancel(apiRequest);
        	/* ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "100", this.messageUtils.getMessage("pointback.message.gpoint_cancel_ok"));
            return res;*/
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
