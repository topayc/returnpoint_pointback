package com.returnp.pointback.service;

import java.util.ArrayList;
import java.util.Date;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.returnp.pointback.common.AppConstants;
import com.returnp.pointback.common.ResponseUtil;
import com.returnp.pointback.common.ReturnpException;
import com.returnp.pointback.dao.mapper.AffiliateMapper;
import com.returnp.pointback.dao.mapper.GiftCardAccHistoryMapper;
import com.returnp.pointback.dao.mapper.GiftCardPaymentMapper;
import com.returnp.pointback.dao.mapper.GreenPointMapper;
import com.returnp.pointback.dao.mapper.PaymentPointbackRecordMapper;
import com.returnp.pointback.dao.mapper.PaymentTransactionMapper;
import com.returnp.pointback.dao.mapper.PointBackMapper;
import com.returnp.pointback.dao.mapper.PolicyMapper;
import com.returnp.pointback.dto.QRRequest;
import com.returnp.pointback.dto.response.ReturnpBaseResponse;
import com.returnp.pointback.model.Affiliate;
import com.returnp.pointback.model.GiftCardAccHistory;
import com.returnp.pointback.model.GiftCardIssue;
import com.returnp.pointback.model.GiftCardPayment;
import com.returnp.pointback.model.GreenPoint;
import com.returnp.pointback.model.Member;
import com.returnp.pointback.model.MemberBankAccount;
import com.returnp.pointback.service.interfaces.GiftCardApiService;
import com.returnp.pointback.web.message.MessageUtils;

@Service
public class GiftCardApiServiceImpl implements GiftCardApiService {

	@Autowired PointBackMapper pointBackMapper;;
	@Autowired AffiliateMapper affiliateMapper;
	@Autowired MessageUtils messageUtils;
	@Autowired Environment env;

	@Autowired PolicyMapper policyMapper;
	@Autowired PaymentTransactionMapper paymentTransactionMapper;
	@Autowired GreenPointMapper greenPointMapper;
	@Autowired PaymentPointbackRecordMapper paymentPointbackRecordMapper;
	@Autowired GiftCardAccHistoryMapper historyMapper;
	@Autowired GiftCardPaymentMapper giftCardPaymentMapper;
	/* 
	 * 상품권 적립 큐알 스캔에 의한 적립 처리 
	 */
	@Override
	public ReturnpBaseResponse giftCardAccumulate(QRRequest qrRequest) {
		ReturnpBaseResponse res = new ReturnpBaseResponse();
		try {
			 Member member = new Member();
	         member.setMemberEmail(qrRequest.getMemberEmai());
	         ArrayList<Member> members = this.pointBackMapper.findMembers(member);
	         
	         if (members.size() !=1 || members == null) {
	        	 ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "601", 
	        			 this.messageUtils.getMessage("pointback.message.not_argu_member", new Object[] {qrRequest.getMemberEmai()}));
	                throw new ReturnpException(res);
	            }
	         
	         /* * 기기의 전화번호와 해당 이메일 계정의 전화번호가 같은 비교 */
	         if (!members.get(0).getMemberPhone().equals(qrRequest.getMemberPhone()) && 
	        		 !members.get(0).getMemberPhone().equals(qrRequest.getMemberPhoneCountry())) {
	        	 ResponseUtil.setResponse(res,ResponseUtil.RESPONSE_OK,  "602", this.messageUtils.getMessage("pointback.message.invalid_argu_phonenumber",
	        			 new Object[] { qrRequest.getMemberPhone(), qrRequest.getMemberPhoneCountry(), members.get(0).getMemberPhone()}));
	        	 throw new ReturnpException(res);
	         }
	         
	         /* * 핀번호가 존재하는 지 검사 */
	         GiftCardIssue issue = new GiftCardIssue();
	         issue.setPinNumber(qrRequest.getPinNumber());
	         ArrayList<GiftCardIssue> issues =this.pointBackMapper.selectGiftCardIssues(issue);
	         if (issues.size() !=1 || issues == null) {
	        	 ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "2404", this.messageUtils.getMessage("api.wrong_pinnumbe"));
	                throw new ReturnpException(res);
	         }
	         
	         float accRate = 0.1f;
	         
	         /* 
	          * 적립에 따른 포인트 증가
	          * */
	         this.increasePoint(member.getMemberNo(), member.getMemberNo(), "1", AppConstants.NodeType.MEMBER,  issues.get(0).getGiftCardSalePrice(), accRate);
	         
	         /*
	          * 상품권 적립 내역 인서트  
	          * */
	         GiftCardAccHistory accHistory = new GiftCardAccHistory();
	         accHistory.setMemberNo(member.getMemberNo());
	         accHistory.setGiftCardIssueNo(issue.getGiftCardIssueNo());
	         accHistory.setBaseAmount(issue.getGiftCardAmount());
	         accHistory.setAccRate(accRate);
	         accHistory.setAccAmount(issues.get(0).getGiftCardSalePrice() * (1 - accRate));
	         accHistory.setAccTime(new Date());
	         this.historyMapper.insert(accHistory);
	         ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR, "100", "상품권 적립 처리가 완료되었습니다 ");
			return res;
		} catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR, "500", "상품권 결제 QR 처리 에러 ");
			return res;
		}
	}

	/* 
	 * 상품권 결제 큐알 스캔에 의한 적립 처리
	 */
	@Override
	public ReturnpBaseResponse giftCardPayment(QRRequest qrRequest) {
		ReturnpBaseResponse res = new ReturnpBaseResponse();
		try {
			 Member member = new Member();
	         member.setMemberEmail(qrRequest.getMemberEmai());
	         ArrayList<Member> members = this.pointBackMapper.findMembers(member);
	         
	         if (members.size() !=1 || members == null) {
	        	 ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "601", 
	        			 this.messageUtils.getMessage("pointback.message.not_argu_member", new Object[] {qrRequest.getMemberEmai()}));
	                throw new ReturnpException(res);
	          }
	         
	         /* * 기기의 전화번호와 해당 이메일 계정의 전화번호가 같은 비교 */
	         if (!members.get(0).getMemberPhone().equals(qrRequest.getMemberPhone()) && 
	        		 !members.get(0).getMemberPhone().equals(qrRequest.getMemberPhoneCountry())) {
	        	 ResponseUtil.setResponse(res,ResponseUtil.RESPONSE_OK,  "602", this.messageUtils.getMessage("pointback.message.invalid_argu_phonenumber",
	        			 new Object[] { qrRequest.getMemberPhone(), qrRequest.getMemberPhoneCountry(), members.get(0).getMemberPhone()}));
	        	 throw new ReturnpException(res);
	         }

	         /* 가맹점 자격이 없을 경우, 중지*/
	         if (!members.get(0).getIsAffiliate().equals("Y")) {
	        	 ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "2405", this.messageUtils.getMessage("api.unqualified_payment_qr"));
	                throw new ReturnpException(res);
	         }
	         
	         /*가맹점 정보 */
	         Affiliate affiliate = new Affiliate();
	         affiliate.setMemberNo(member.getMemberNo());
	         ArrayList<Affiliate> affiliates = this.pointBackMapper.findAffiliates(affiliate);
	     	
	         /*가맹점 정보가 존재하지 않으면 중지 */
	         if (affiliates == null || affiliates.size() != 1) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "603", 
						this.messageUtils.getMessage("pointback.message.not_argu_affiliate", new Object[] {member.getMemberName()}));
				throw new ReturnpException(res);
			 }
	         
	        affiliate = affiliates.get(0);
	        if (!affiliate.getAffiliateType().contains(AppConstants.AffiliateType.GIFT_CARD_USAGE_AFFILIATE)) {
	        	ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "3001", 
						this.messageUtils.getMessage("api.unqualified_affiliate_for_giftcard", new Object[] {member.getMemberName()}));
				throw new ReturnpException(res);
	        }
	         
	         /* 핀번호가 존재하는 지 검사 */
	         GiftCardIssue issue = new GiftCardIssue();
	         issue.setPinNumber(qrRequest.getPinNumber());
	         ArrayList<GiftCardIssue> issues =this.pointBackMapper.selectGiftCardIssues(issue);
	         if (issues.size() !=1 || issues == null) {
	        	 ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "2404", this.messageUtils.getMessage("api.wrong_pinnumber"));
	                throw new ReturnpException(res);
	         }
	         
	         /*주 은행 찾기*/
	         MemberBankAccount bankAccount= new MemberBankAccount();
	         bankAccount.setMemberNo(member.getMemberNo());
	         bankAccount.setIsDefault("Y");
	         ArrayList<MemberBankAccount> bankAccounts = this.pointBackMapper.findMemberBankAccounts(bankAccount);
	         
	         /*상품권 결제 정보 인서트*/
	         GiftCardPayment giftCardPayment = new GiftCardPayment();
	         giftCardPayment.setAffiliateNo(affiliates.get(0).getAffiliateNo());
	         giftCardPayment.setGiftCardIssueNo(issues.get(0).getGiftCardIssueNo());
	         giftCardPayment.setGiftCardPaymentAmount(issues.get(0).getGiftCardAmount());
	         giftCardPayment.setRefundRate(affiliates.get(0).getGiftCardPayRefundRate());
	         giftCardPayment.setRefundAmount(issues.get(0).getGiftCardAmount() *  (1-affiliates.get(0).getGiftCardPayRefundRate()));
	         giftCardPayment.setRefundStatus("1");
	         giftCardPayment.setMemberBankAccountNo(bankAccounts.get(0).getMemberBankAccountNo());
	         this.giftCardPaymentMapper.insert(giftCardPayment);
	         
	         ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR, "100", "상품권 결제 요청 완료되었습니다. ");
	         return res;
		} catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR, "500", "상품권 적립 QR 처리 에러 ");
			return res;
		}
	}

	@Override
	public void increasePoint( int memberNo, int nodeNo, String nodeType, String nodeTypeName, int amount, float accRate) {
		try {
			/* G Point 증가 업데이트 */
			GreenPoint point =  new GreenPoint();
			ArrayList<GreenPoint> greenPoints = null;

			point.setMemberNo(memberNo);
			point.setNodeType(nodeType);

			if (nodeType.equals(AppConstants.NodeType.RECOMMENDER)) {
				greenPoints = this.pointBackMapper.findGreenPoints(point);
				point = greenPoints == null || greenPoints.size() < 1 ? this.createRecommenderRPoint(memberNo) : greenPoints.get(0);
			}else {
				point = this.pointBackMapper.findGreenPoints(point).get(0);
			}

			point.setNodeNo(nodeNo);
			point.setNodeTypeName(nodeTypeName);
			point.setPointAmount(point.getPointAmount() + (amount * accRate));
			this.greenPointMapper.updateByPrimaryKey(point);

			/* 상품권에 의한  적립 내역 저장*/
			Date date = new Date();
			GiftCardAccHistory accHistory = new GiftCardAccHistory();
			accHistory.setMemberNo(memberNo);
			accHistory.setBaseAmount(amount);
			accHistory.setAccAmount(amount * accRate);
			accHistory.setAccTime(date);
			accHistory.setAccRate(accRate);
			this.historyMapper.insert(accHistory);
			
		} catch (Exception e) {
			e.printStackTrace();
			ReturnpBaseResponse res = new ReturnpBaseResponse();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR,"2000", this.messageUtils.getMessage("pointback.message.inner_server_error"));
			throw new ReturnpException(res);
		}
	}

	@Override
	public GreenPoint createRecommenderRPoint(int memberNo) {
		/* 추천인용 Green Point 생성*/
		GreenPoint greenPoint = new GreenPoint();
		greenPoint.setMemberNo(memberNo);
		greenPoint.setNodeNo(memberNo);
		greenPoint.setNodeType(AppConstants.NodeType.RECOMMENDER);
		greenPoint.setPointAmount((float)0);
		greenPoint.setNodeTypeName("recommender");
		this.greenPointMapper.insert(greenPoint);
		return greenPoint;
	}
}
