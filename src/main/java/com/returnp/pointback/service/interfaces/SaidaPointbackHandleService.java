package com.returnp.pointback.service.interfaces;

import org.springframework.transaction.annotation.Transactional;

import com.returnp.pointback.common.DataMap;
import com.returnp.pointback.common.ReturnpException;
import com.returnp.pointback.dto.SaidaObject;
import com.returnp.pointback.dto.response.ReturnpBaseResponse;
import com.returnp.pointback.model.Affiliate;
import com.returnp.pointback.model.Member;
import com.returnp.pointback.model.PaymentTransaction;

@Transactional
public interface SaidaPointbackHandleService {

	ReturnpBaseResponse accumulate(SaidaObject saida);

	ReturnpBaseResponse cancelAccumulate(SaidaObject saida);
	
	public Member validateMemberAuth(SaidaObject saida) throws ReturnpException;

	public Affiliate validateAffiliateAuth(SaidaObject saida) throws ReturnpException;
	
	public PaymentTransaction  createPaymentTransaction(SaidaObject saida) throws ReturnpException;
	
	public void  validateAccumulateRequest(SaidaObject saida) throws ReturnpException;

	public PaymentTransaction  validateCancelRequest(SaidaObject saida) throws ReturnpException;


}
