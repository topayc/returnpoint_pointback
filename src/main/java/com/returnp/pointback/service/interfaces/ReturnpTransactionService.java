package com.returnp.pointback.service.interfaces;

import org.springframework.transaction.annotation.Transactional;

import com.returnp.pointback.common.DataMap;
import com.returnp.pointback.common.ReturnpException;
import com.returnp.pointback.dto.command.api.ApiRequest;
import com.returnp.pointback.dto.response.ReturnpBaseResponse;
import com.returnp.pointback.model.Affiliate;
import com.returnp.pointback.model.GreenPoint;
import com.returnp.pointback.model.Member;
import com.returnp.pointback.model.PaymentTransaction;

@Transactional
public interface ReturnpTransactionService {
	public ReturnpBaseResponse accumulate(DataMap dataMap);

	public ReturnpBaseResponse cancelAccumulate(DataMap dataMap);

	public void restorePoint(DataMap dataMap) throws ReturnpException;
	
	public void accumuatePoint(PaymentTransaction paymentTransaction) throws ReturnpException;

	public PaymentTransaction  createPaymentTransaction(DataMap dataMap) throws ReturnpException;
	
	public void  increasePoint(PaymentTransaction transaction, int memberNo, int nodeNo, String nodeType, String nodeTypeName, float accRate) throws ReturnpException;

	public GreenPoint  createRecommenderRPoint(int memberNo);

	public Member validateMemberAndGet(String MemberEmail, String phoneNumber, String phoneNumberCountry) throws ReturnpException;

	public Affiliate validateAffiliateAuthAndGet(String paymentRouterType, String paymentRouterName, String afId) throws ReturnpException;
	
	public void  validateAccumulateRequest(DataMap dataMap) throws ReturnpException;

	public PaymentTransaction  validateCancelRequest(DataMap dataMap) throws ReturnpException;
	
	public DataMap convertPaymentTransactionToDataMap(PaymentTransaction pt);

	public ReturnpBaseResponse gpointPaymentApporval(ApiRequest apiRequest);

	public ReturnpBaseResponse gpointPaymentCancel(ApiRequest apiRequest) ;
	
	
}
