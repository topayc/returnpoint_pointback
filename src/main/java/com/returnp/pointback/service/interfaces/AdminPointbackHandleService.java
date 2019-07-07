package com.returnp.pointback.service.interfaces;

import org.springframework.transaction.annotation.Transactional;

import com.returnp.pointback.common.DataMap;
import com.returnp.pointback.common.ReturnpException;
import com.returnp.pointback.dto.CiderObject;
import com.returnp.pointback.dto.command.InnerPointBackTarget;
import com.returnp.pointback.dto.command.OuterPointBackTarget;
import com.returnp.pointback.dto.command.PointBackTarget;
import com.returnp.pointback.dto.response.ReturnpBaseResponse;
import com.returnp.pointback.model.Affiliate;
import com.returnp.pointback.model.GreenPoint;
import com.returnp.pointback.model.Member;
import com.returnp.pointback.model.PaymentTransaction;

@Transactional
public interface AdminPointbackHandleService {
	public ReturnpBaseResponse accumulate(DataMap dataMap);

	public ReturnpBaseResponse cancelAccumulate(DataMap dataMap);
	
	public ReturnpBaseResponse   accumuatePoint(int paymentTransactioinNo) throws ReturnpException;
	
	public ReturnpBaseResponse   accumuatePoint(String paymentApprovalNumber) throws ReturnpException;

	public ReturnpBaseResponse cancelAccumuate(String pan);

	public ReturnpBaseResponse cancelAccumuate(int paymentTrasactionNo);
	
	public ReturnpBaseResponse forcedCancelAccumuate(int paymentTrasactionNo);

	public ReturnpBaseResponse forcedAccumuate(int paymentTrasactionNo);

}
