package com.returnp.pointback.service.interfaces;

import java.util.Date;
import java.util.HashMap;

import org.springframework.transaction.annotation.Transactional;

import com.returnp.pointback.dto.command.InnerPointBackTarget;
import com.returnp.pointback.dto.command.OuterPointBackTarget;
import com.returnp.pointback.dto.command.PointBackTarget;
import com.returnp.pointback.dto.response.ReturnpBaseResponse;
import com.returnp.pointback.model.GreenPoint;
import com.returnp.pointback.model.PaymentTransaction;

@Transactional
public interface PointAccumulateService {
	
	public ReturnpBaseResponse processByAdmin(int vanPaymentTransactionNo);
	public ReturnpBaseResponse accumulateByAdminEx(int paymentTransactionNo, int accType);
	public GreenPoint  createRecommenderRPoint(int memberNo);
	public ReturnpBaseResponse processByEncQr (String qrOrg, int pam, String pas, Date pat, String pan, String afId, String memberEmail, String phoneNumber, String phoneNumberCountry);
	public ReturnpBaseResponse accumulatePoint(PaymentTransaction paymentTransaction, int accType);
	
	public void  finalPointbackProcess(PaymentTransaction transaction, int memberNo, int nodeNo, String nodeType, String nodeTypeName, float accRate, int accType);
	
	public PointBackTarget findInnerPointBackFindTarget(PointBackTarget target);
	public OuterPointBackTarget findOuterPointBackTarget(OuterPointBackTarget target);
	public InnerPointBackTarget findInnerPointBackTarget(String affiliateCode);
	
	public ReturnpBaseResponse cancelQRPayment(HashMap<String, String> qrparseMap);
	public ReturnpBaseResponse accQRPayment(HashMap<String, String> qrparseMap);
	public ReturnpBaseResponse accumulateByAdmin(int paymentTransactionNo, int accType);
}
