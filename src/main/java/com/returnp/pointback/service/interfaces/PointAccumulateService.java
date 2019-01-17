package com.returnp.pointback.service.interfaces;

import java.util.Date;
import java.util.HashMap;

import org.springframework.transaction.annotation.Transactional;

import com.returnp.pointback.dto.command.InnerPointBackTarget;
import com.returnp.pointback.dto.command.OuterPointBackTarget;
import com.returnp.pointback.dto.command.PointBackTarget;
import com.returnp.pointback.dto.response.BaseResponse;
import com.returnp.pointback.model.GreenPoint;
import com.returnp.pointback.model.PaymentTransaction;

@Transactional
public interface PointAccumulateService {
	
	public BaseResponse processByAdmin(int vanPaymentTransactionNo);
	public BaseResponse accumulateByAdminEx(int paymentTransactionNo, int accType);
	public GreenPoint  createRecommenderRPoint(int memberNo);
	public BaseResponse processByEncQr (String qrOrg, int pam, String pas, Date pat, String pan, String afId, String memberEmail, String phoneNumber, String phoneNumberCountry);
	public BaseResponse accumulatePoint(PaymentTransaction paymentTransaction, int accType);
	
	public void  finalPointbackProcess(PaymentTransaction transaction, int memberNo, int nodeNo, String nodeType, String nodeTypeName, float accRate, int accType);
	
	public PointBackTarget findInnerPointBackFindTarget(PointBackTarget target);
	public OuterPointBackTarget findOuterPointBackTarget(OuterPointBackTarget target);
	public InnerPointBackTarget findInnerPointBackTarget(String affiliateCode);
	
	public BaseResponse cancelQRPayment(HashMap<String, String> qrparseMap);
	public BaseResponse accQRPayment(HashMap<String, String> qrparseMap);
	public BaseResponse accumulateByAdmin(int paymentTransactionNo, int accType);
}
