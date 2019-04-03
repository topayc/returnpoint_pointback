package com.returnp.pointback.service.interfaces;

import org.springframework.transaction.annotation.Transactional;

import com.returnp.pointback.dto.QRRequest;
import com.returnp.pointback.dto.response.ReturnpBaseResponse;
import com.returnp.pointback.model.GreenPoint;
import com.returnp.pointback.model.Member;

@Transactional
public interface GiftCardApiService {
	public ReturnpBaseResponse giftCardPayment(QRRequest qrRequest);
	public ReturnpBaseResponse giftCardAccumulate(QRRequest qrRequest);
	public void increasePoint( int memberNo, int nodeNo, String nodeType, String nodeTypeName, int amount, float accRate);
	public GreenPoint createRecommenderRPoint(int memberNo);
}
