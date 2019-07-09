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
import com.returnp.pointback.service.interfaces.ApiPointbackHandleService;
import com.returnp.pointback.service.interfaces.PointbackTargetService;
import com.returnp.pointback.service.interfaces.QRPointbackHandleService;
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
   

}
