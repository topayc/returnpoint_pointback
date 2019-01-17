package com.returnp.pointback.service.interfaces;

import org.springframework.transaction.annotation.Transactional;

import com.returnp.pointback.common.DataMap;
import com.returnp.pointback.common.ReturnpException;
import com.returnp.pointback.dto.command.InnerPointBackTarget;
import com.returnp.pointback.dto.command.OuterPointBackTarget;
import com.returnp.pointback.dto.command.PointBackTarget;
import com.returnp.pointback.dto.response.BaseResponse;
import com.returnp.pointback.model.Affiliate;
import com.returnp.pointback.model.GreenPoint;
import com.returnp.pointback.model.Member;
import com.returnp.pointback.model.PaymentTransaction;

@Transactional
public interface BasePointAccumulateService {
	public BaseResponse accumulate(DataMap dataMap);

	public BaseResponse cancelAccumulate(DataMap dataMap);

	public void restorePoint(DataMap dataMap) throws ReturnpException;
	
	public void   accumuatePoint(int paymentTransactioinNo) throws ReturnpException;
	
	public void accumuatePoint(PaymentTransaction paymentTransaction) throws ReturnpException;

	public PaymentTransaction  createPaymentTransaction(DataMap dataMap) throws ReturnpException;
	
	public void  increasePoint(PaymentTransaction transaction, int memberNo, int nodeNo, String nodeType, String nodeTypeName, float accRate) throws ReturnpException;

	public GreenPoint  createRecommenderRPoint(int memberNo);

	public PointBackTarget findInnerPointBackFindTarget(PointBackTarget target);
	
	public OuterPointBackTarget findOuterPointBackTarget(OuterPointBackTarget target);
	
	public InnerPointBackTarget findInnerPointBackTarget(String affiliateCode);

	public BaseResponse cancelAccumuate(String pan);

	public BaseResponse cancelAccumuate(int paymentTrasactionNo);
	
	public Member validateMemberAuth(String MemberEmail, String phoneNumber, String phoneNumberCountry) throws ReturnpException;

	public Affiliate validateAffiliateAuth(String afId) throws ReturnpException;

	public PaymentTransaction  validate(String pan, String pas) throws ReturnpException;
	
	public DataMap convertPaymentTransactionToDataMap(PaymentTransaction pt);

	
}
