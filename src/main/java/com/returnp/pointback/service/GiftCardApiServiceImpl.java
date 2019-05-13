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
import com.returnp.pointback.dao.mapper.GiftCardIssueMapper;
import com.returnp.pointback.dao.mapper.GiftCardPaymentMapper;
import com.returnp.pointback.dao.mapper.GiftCardPolicyMapper;
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
import com.returnp.pointback.model.GiftCardPolicy;
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
	@Autowired GiftCardPolicyMapper giftCardPolicyMapper;
	@Autowired GiftCardIssueMapper  giftCardIssueMapper;
	/* 
	 * 상품권 적립 큐알 스캔에 의한 적립 처리 
	 */
	@Override
	public ReturnpBaseResponse giftCardAccumulate(QRRequest qrRequest) {
		ReturnpBaseResponse res = new ReturnpBaseResponse();
		try {
			 Member member = new Member();
	         member.setMemberEmail(qrRequest.getMemberEmail());
	         ArrayList<Member> members = this.pointBackMapper.findMembers(member);
	         
	         if (members == null || members.size() !=1  ) {
	        	 ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "601", 
	        			 this.messageUtils.getMessage("pointback.message.not_argu_member", new Object[] {qrRequest.getMemberEmail()}));
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
	         ArrayList<GiftCardIssue> issueses =this.pointBackMapper.selectGiftCardIssues(issue);
	         if (issueses == null || issueses.size() !=1 ) {
	        	 ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "2404", this.messageUtils.getMessage("api.wrong_pinnumbe"));
	                throw new ReturnpException(res);
	         }
	         
	         /*존재하는 핀 번호가 이미 적립 처리가 되었는지 검사 */
	         if (issueses.get(0).getAccableStatus().equals(AppConstants.GiftCardAccableStatus.ACCUMULATE_COMPLETE)) {
	        	 ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "2409", this.messageUtils.getMessage("api.already_accumulated_gift_card"));
	                throw new ReturnpException(res);
	         }
	         
	         /*상품권 상태 조사 */
	         switch(issueses.get(0).getGiftCardStatus()) {
	         case "2" : ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "2602", this.messageUtils.getMessage("api.stop_acc_and_pay")); throw new ReturnpException(res);
	         case "3" : ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "2603", this.messageUtils.getMessage("api.stop_acc")); throw new ReturnpException(res);
	         case "5" : ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "2605", this.messageUtils.getMessage("api.expiration_gift_card")); throw new ReturnpException(res);
	         }
	         
	         GiftCardPolicy giftCardPolicy = this.giftCardPolicyMapper.selectByPrimaryKey(1);
	         if (giftCardPolicy == null) {
	        	 ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "2408", this.messageUtils.getMessage("api.no_gift_card_policy"));
	                throw new ReturnpException(res);
	         }
	         float giftCardAccRate = giftCardPolicy.getGiftCardAccRate();
	         
	         /* 
	          * 상품권 QR 적립에 따른 포인트 증가
	          * */
	         this.increaseGiftCardPoint(
	        	members.get(0).getMemberNo(), 
	        	members.get(0).getMemberNo(), 
	        	"1", 
	        	AppConstants.NodeType.MEMBER,  
	        	issueses.get(0).
	        	getGiftCardSalePrice(), 
	        	giftCardAccRate
	        );
	         
	         /*
	          * 상품권 QR 적립 내역 인서트  
	          * */
	         GiftCardAccHistory accHistory = new GiftCardAccHistory();
	         accHistory.setMemberNo(members.get(0).getMemberNo());
	         accHistory.setGiftCardIssueNo(issueses.get(0).getGiftCardIssueNo());
	         accHistory.setBaseAmount(issueses.get(0).getGiftCardAmount());
	         accHistory.setAccRate(giftCardAccRate);
	         accHistory.setAccAmount(issueses.get(0).getGiftCardSalePrice() * giftCardAccRate);
	         accHistory.setAccTime(new Date());
	         this.historyMapper.insert(accHistory);
	         
	         /*상품권을 적립 됨 상태로 변경*/
	         issue = issueses.get(0);
	         issue.setAccableStatus(AppConstants.GiftCardAccableStatus.ACCUMULATE_COMPLETE);
	         issue.setAccQrScanTime(new Date());
	         issue.setAccQrScanner(members.get(0).getMemberEmail());
	         this.giftCardIssueMapper.updateByPrimaryKey(issue);
	         ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR, "100", this.messageUtils.getMessage("api.gift_card_acc_success"));
			return res;
		} catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR, "500", this.messageUtils.getMessage("api.gift_card_acc_error"));
			return res;
		}
	}

	/* 
	 * 상품권 결제 큐알 스캔에 의한 결제 처리
	 * 상품권 결제의 경우, 관련 노드 적립 프로세스 진행 안함 (단순 결제처리만 진행)
	 */
	@Override
	public ReturnpBaseResponse giftCardPayment(QRRequest qrRequest) {
		ReturnpBaseResponse res = new ReturnpBaseResponse();
		try {
			 Member member = new Member();
	         member.setMemberEmail(qrRequest.getMemberEmail());
	         ArrayList<Member> members = this.pointBackMapper.findMembers(member);
	         
	       /*  System.out.println("qrRequest.getMemberEmail(");
	         System.out.println(">> 상품권 결제 제휴점 이메일");
	         System.out.println(qrRequest.getMemberEmail());*/
	         
	         if (members.size() !=1 || members == null) {
	        	 ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "601", 
	        			 this.messageUtils.getMessage("pointback.message.not_argu_member", new Object[] {qrRequest.getMemberEmail()}));
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
	         affiliate.setMemberNo(members.get(0).getMemberNo());
	         ArrayList<Affiliate> affiliates = this.pointBackMapper.findAffiliates(affiliate);
	     	
	         /*가맹점 정보가 존재하지 않으면 중지 */
	         if (affiliates == null || affiliates.size() != 1) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "3000", 
						this.messageUtils.getMessage("api.no_affiliate"));
				throw new ReturnpException(res);
			 }
	         
	        affiliate = affiliates.get(0);
	        /*해당 협력업체가 상품권으로 결제 할 수 있는 제휴점이 아닌 경우, 결제 중지*/
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
	         
	         
	         /*상품권이 이미 결제 처리가 된 상품권인지 검사 */
	         if (issues.get(0).getPayableStatus().equals(AppConstants.GiftCardPayableStatus.PAYED_COMPLETE)) {
	        	 ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "2501", this.messageUtils.getMessage("api.already_payed_gift_card"));
	                throw new ReturnpException(res);
	         }
	         
	         /*상품권 상태 조사 */
	         switch(issues.get(0).getGiftCardStatus()) {
	         case "2" : ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "2602", this.messageUtils.getMessage("api.stop_acc_and_pay")); throw new ReturnpException(res);
	         case "4" : ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "2604", this.messageUtils.getMessage("api.stop_pay")); throw new ReturnpException(res);
	         case "5" : ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "2605", this.messageUtils.getMessage("api.expiration_gift_card")); throw new ReturnpException(res);
	         }

	         /*주 은행 찾기*/
	         MemberBankAccount bankAccount= new MemberBankAccount();
	         bankAccount.setMemberNo(members.get(0).getMemberNo());
	         bankAccount.setIsDefault("Y");
	         ArrayList<MemberBankAccount> bankAccounts = this.pointBackMapper.findMemberBankAccounts(bankAccount);
	         if (bankAccounts.size() < 1) {
	        	 ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "2410", this.messageUtils.getMessage("api.no_default_bank"));
	                throw new ReturnpException(res);
	         }
	         
	         /*상품권 정책 정보*/
	         GiftCardPolicy giftCardPolicy = this.giftCardPolicyMapper.selectByPrimaryKey(1);
	         if (giftCardPolicy == null) {
	        	 ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "2408", this.messageUtils.getMessage("api.no_gift_card_policy"));
	                throw new ReturnpException(res);
	         }
	         
	         float refundRate = (float)affiliates.get(0).getGiftCardPayRefundRate() != 0.0f ?  affiliates.get(0).getGiftCardPayRefundRate() : giftCardPolicy.getGiftCardPayRefundRate();
	         /*상품권 결제 정보 인서트*/
	         GiftCardPayment giftCardPayment = new GiftCardPayment();
	         giftCardPayment.setAffiliateNo(affiliates.get(0).getAffiliateNo());
	         giftCardPayment.setGiftCardIssueNo(issues.get(0).getGiftCardIssueNo());
	         giftCardPayment.setGiftCardPaymentAmount(issues.get(0).getGiftCardAmount());
	         giftCardPayment.setRefundRate(refundRate);
	         giftCardPayment.setRefundAmount(issues.get(0).getGiftCardAmount()  -   ( issues.get(0).getGiftCardAmount() * refundRate));
	         giftCardPayment.setRefundStatus("1");
	         giftCardPayment.setMemberBankAccountNo(bankAccounts.get(0).getMemberBankAccountNo());
	         Date now = new Date();
	         giftCardPayment.setCreateTime(now);
	         giftCardPayment.setUpdateTime(now);
	         if (bankAccount !=null) {
	        	 giftCardPayment.setMemberBankAccountNo(bankAccounts.get(0).getMemberBankAccountNo());
	         }
	         this.giftCardPaymentMapper.insertSelective(giftCardPayment);
	         
	         /*상품권 상태를 결제 됨으로 변경*/
	         issues.get(0).setPayableStatus(AppConstants.GiftCardPayableStatus.PAYED_COMPLETE);
	         issues.get(0).setPayQrScanTime(new Date());
	         issues.get(0).setPayQrScanner(members.get(0).getMemberEmail());
	         this.giftCardIssueMapper.updateByPrimaryKey(issues.get(0));
	         ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR, "100", this.messageUtils.getMessage("api.gift_card_pay_success"));
	         return res;
		} catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			return e.getBaseResponse();
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR, "500", this.messageUtils.getMessage("api.gift_card_pay_error"));
			return res;
		}
	}

	@Override
	public void increaseGiftCardPoint( int memberNo, int nodeNo, String nodeType, String nodeTypeName, int amount, float giftCardAccRate) {
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
			point.setPointAmount(point.getPointAmount() + ( amount * giftCardAccRate));
			this.greenPointMapper.updateByPrimaryKey(point);
			
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
