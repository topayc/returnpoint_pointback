package com.returnp.pointback.service.interfaces;

import org.springframework.transaction.annotation.Transactional;

import com.returnp.pointback.common.DataMap;
import com.returnp.pointback.common.ReturnpException;
import com.returnp.pointback.dto.command.InnerPointBackTarget;
import com.returnp.pointback.dto.command.OuterPointBackTarget;
import com.returnp.pointback.dto.command.PointBackTarget;
import com.returnp.pointback.dto.response.ReturnpBaseResponse;
import com.returnp.pointback.model.Affiliate;
import com.returnp.pointback.model.GreenPoint;
import com.returnp.pointback.model.Member;
import com.returnp.pointback.model.PaymentTransaction;

@Transactional
public interface PointCodePointbackHandleService {
	public ReturnpBaseResponse accumulate(String memberEmail, String phoneNumber, String phoneNumberCountry, String couponNumber);
	public ReturnpBaseResponse cancelAccumulate(String memberEmail, String phoneNumber, String phoneNumberCountry,String couponNumber);
	public void increasePoint(int pointCouponTransactionNo, float accRate, float accPointAmount, int memberNo, int nodeNo, String nodeType, String nodeTypeName); 
	public GreenPoint createRecommenderRPoint(int memberNo) ;

}
