package com.returnp.pointback.service;

import java.net.URL;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.returnp.pointback.common.AppConstants;
import com.returnp.pointback.common.ResponseUtil;
import com.returnp.pointback.common.ReturnpException;
import com.returnp.pointback.dao.mapper.AffiliateMapper;
import com.returnp.pointback.dao.mapper.GreenPointMapper;
import com.returnp.pointback.dao.mapper.PaymentPointbackRecordMapper;
import com.returnp.pointback.dao.mapper.PaymentTransactionMapper;
import com.returnp.pointback.dao.mapper.PointBackMapper;
import com.returnp.pointback.dao.mapper.PolicyMapper;
import com.returnp.pointback.dto.command.InnerPointBackTarget;
import com.returnp.pointback.dto.command.OuterPointBackTarget;
import com.returnp.pointback.dto.command.PointBackTarget;
import com.returnp.pointback.dto.response.ReturnpBaseResponse;
import com.returnp.pointback.model.Affiliate;
import com.returnp.pointback.model.GreenPoint;
import com.returnp.pointback.model.Member;
import com.returnp.pointback.model.PaymentPointbackRecord;
import com.returnp.pointback.model.PaymentTransaction;
import com.returnp.pointback.model.Policy;
import com.returnp.pointback.model.SoleDist;
import com.returnp.pointback.service.interfaces.PointAccumulateService;
import com.returnp.pointback.util.BASE64Util;
import com.returnp.pointback.util.QRManager;
import com.returnp.pointback.web.message.MessageUtils;

@Service
public class PointAccumulateServiceImpl implements PointAccumulateService {

	@Autowired PointBackMapper pointBackMapper;
	@Autowired PolicyMapper policyMapper;
	@Autowired AffiliateMapper affiliateMapper;
	@Autowired PaymentTransactionMapper paymentTransactionMapper;
	@Autowired GreenPointMapper greenPointMapper;
	@Autowired PaymentPointbackRecordMapper paymentPointbackRecordMapper;
	@Autowired MessageUtils messageUtils;

	/* 결제 승인에 따른 Green Point 적립 처리 
	 * @see com.returnp.pointback.service.interfaces.PointBackService#cancelGreenPointAccProcess(int)
	 */
	@Override
	public ReturnpBaseResponse processByAdmin(int paymentTransactionNo) {
		ReturnpBaseResponse res = new ReturnpBaseResponse();
		System.out.println("excuteGreenPointAccProcess");
		
		/* 적립해야할 VanPaymentTransaction 데이타 조회*/
		PaymentTransaction paymentTransaction = this.paymentTransactionMapper.selectByPrimaryKey(paymentTransactionNo);
		/*"유효하지 않는 Green Point 적립 처리 요청";*/
		if (paymentTransaction == null) {
			res.setResultCode("700");
			res.setMessage(this.messageUtils.getMessage("pointback.message.invalid_acc_ok_request"));
			return res;
		}
			
		
		/* 결제 상태가 결제 승인이 아니라면 중지 */
		if (!paymentTransaction.getPaymentApprovalStatus().trim().equals(AppConstants.PaymentApprovalStatus.PAYMENT_APPROVAL_OK)) {
			res.setResultCode("701");
			res.setMessage(this.messageUtils.getMessage("pointback.message.invalid_request"));
			return res;
		}

		/* 이미 적립이 완료된 거래내역인 경우 적립 처리 금지, 중복 적립 금지*/
		if (paymentTransaction.getPointBackStatus().trim().equals(AppConstants.GreenPointAccStatus.POINTBACK_COMPLETE)  || 
				paymentTransaction.getPointBackStatus().trim().equals(AppConstants.GreenPointAccStatus.POINTBACK_STOP) ) {
			res.setResultCode("703");
			res.setMessage(this.messageUtils.getMessage("pointback.message.completed_request_acc_ok"));
			return res; /*"이미 Green Point 적립 처리가 완료된 요청";
*/		}
		
		int acctype = 1;
		String pas = paymentTransaction.getPaymentApprovalStatus();
		switch(pas) {
		case "1" :  /* 결제 승인 */  
			acctype = 1;
			break;
		case "2":   /* 결제 취소 */ 
			acctype = -1;
			break;   
		case "3":   /* 결제 에러 */
			res.setResultCode("809");
			res.setMessage(this.messageUtils.getMessage("pointback.message.processing_acc_error"));
			return res;
		}
		
		return this.accumulateByAdmin(paymentTransactionNo, acctype);
		/* 
		 * 가맹점을 대상으로 개인의 결제 내역을 정상적으로 생성 한 경우 
		 * 정상 매출인 경우
		 * */
/*		if (paymentTransaction.getNodeType().equals("1") && paymentTransaction.getAffiliateNo() != null  &&  
				StringUtils.isNotEmpty(paymentTransaction.getPaymentApprovalNumber()) && 
				StringUtils.isNotEmpty(paymentTransaction.getAffiliateSerial())) {
			return this.accumulateByAdmin(paymentTransactionNo, acctype);
		}*/
		/*  
		 * 소비사가 가맹점에서 소비한 결제 내역을 기준으로 하는 것이 아니라,
		 * 특정 대리점이나 가맹점등에 직럽 포인트느 매출을 제공,  생성하는 경우의 특수한 경우로 
		 * 다만 특정 노드에게 포인트를 지급함과 동시에 기존의 추천관계등에 따라 포인트를 적립하는 경우 
		 * */
	/*	else {
			return this.accumulateByAdminEx(paymentTransactionNo, acctype);
		}*/
		
		
	}
	
	/* 정상 적립 처리 메서드 
	 * @param vanPaymentTransactionNo : 해당 거래 내역 번호
	 * @param accType : 적립 및 취소 플래그 값 ( 1 : 적립 , -1 : 적립 취소)
	 * @return
	 */
	@Override
	public ReturnpBaseResponse accumulateByAdmin(int paymentTransactionNo, int accType) {
		try {
		ReturnpBaseResponse res = new ReturnpBaseResponse();
		
		/*
		 * 유일한 1개의 SoleDist 검색
		 * 관련 노드가 존재하지 않을 경우 Default 로 SoleDist(총판)에게 분배함  
		 * */
		ArrayList<SoleDist> soleDistList = this.pointBackMapper.findSoleDists(new SoleDist());
		SoleDist defaultSoleDist = null;
		if (soleDistList .size() >= 0) {
			defaultSoleDist  = soleDistList.get(0);
		}
		
		/*거래 내역 가져오기*/
		PaymentTransaction transaction = this.paymentTransactionMapper.selectByPrimaryKey(paymentTransactionNo);
		
		/*정책 조회*/
		Policy policy = new Policy();
		ArrayList<Policy> policies = this.pointBackMapper.findPolicies(policy);
		policy = policies.get(policies.size() -1 );
		GreenPoint point = null;
		
		/* 
		 * 결제 고객 및 해당 고객의 1대, 2 대  추천인에 대한 적립 진행 
		 * 
		 * */
		OuterPointBackTarget outerTarget = new OuterPointBackTarget();
		outerTarget.setMemberNo(transaction.getMemberNo());
		outerTarget = this.findOuterPointBackTarget(outerTarget);
		if (outerTarget == null) {
			/*res.setMessage(this.messageUtils.getMessage("pointback.message.invalid_acc_ok_request"));
			res.setResult("801");*/
			return null;
		}
		
		/* 소비자 포인트 적립 */
		if (outerTarget.getMemberNo() != null) {
			finalPointbackProcess(
				transaction, 
				outerTarget.getMemberNo(), 
				outerTarget.getMemberNo(),
				AppConstants.NodeType.MEMBER, 
				"member", 
				policy.getCustomerComm(), 
				accType);
		}
		
		/* 소비자의 1대 추천인 적립*/
		if (outerTarget.getFirstRecommenderMemberNo() != null) {
			finalPointbackProcess(
				transaction, 
				outerTarget.getFirstRecommenderMemberNo(),
				outerTarget.getFirstRecommenderMemberNo(),
				AppConstants.NodeType.RECOMMENDER, 
				"recommender",
				policy.getCustomerRecCom(), accType);
		}else {
			 if (defaultSoleDist != null) {
                 finalPointbackProcess(
                	transaction,
                	defaultSoleDist.getMemberNo(), 
                	defaultSoleDist.getSoleDistNo(), 
                	AppConstants.NodeType.SOLE_DIST, 
                	"sole_dist",  
                	policy.getCustomerRecCom(), 
                	accType);
			 }
		}
		
		/* 소비자의 2대 추천인 적립*/
		if (outerTarget.getSecondRecommenderMemberNo() != null) {
			finalPointbackProcess(
	            transaction, 
	            outerTarget.getSecondRecommenderMemberNo(), 
	            outerTarget.getSecondRecommenderMemberNo(), 
	            AppConstants.NodeType.RECOMMENDER, "recommender",  
	            policy.getCustomerRecManagerComm(), 
	            accType );
		}else {
            if (defaultSoleDist != null) {
                finalPointbackProcess(
                    transaction,
                    defaultSoleDist.getMemberNo(), 
                    defaultSoleDist.getSoleDistNo(), 
                    AppConstants.NodeType.SOLE_DIST, 
                    "sole_dist",  
                    policy.getCustomerRecManagerComm(), 
                    accType
                ); 
            }
        }
		
		/* 
		 * Inner Node 적립(협력업체, 협력업체 추천인, 추천인의 영업 관리자, 대리점, 지사 총판)
		 * */
		InnerPointBackTarget innerTarget = this.findInnerPointBackTarget(transaction.getAffiliateSerial());
		/*
		 * 가맹점 포인트 적립
		 * */
		Affiliate targetAffiliate = this.affiliateMapper.selectBySerial(transaction.getAffiliateSerial());
		float affiliateComm =  targetAffiliate.getAffiliateComm() > 0 ?  targetAffiliate.getAffiliateComm()  : policy.getAffiliateComm();
		
		if (innerTarget.getAffiliateNo() != null) {
			finalPointbackProcess(
				transaction, 
				innerTarget.getAffiliateMemberNo(),
				innerTarget.getAffiliateNo(),
				AppConstants.NodeType.AFFILIATE, 
				"affiliate", 
				affiliateComm, accType);
		}
		
		/*
		 * 가맹점의 1대 추천인 포인트 적립
		 * */
		if (innerTarget.getAffiliateFirstRecommenderMemberNo() != null) {
			finalPointbackProcess(
				transaction, 
				innerTarget.getAffiliateFirstRecommenderMemberNo(),
				innerTarget.getAffiliateFirstRecommenderMemberNo(), 
				AppConstants.NodeType.RECOMMENDER,
				"recommender", 
				policy.getAffiliateRecComm(),
				accType);
		}else {
            if (defaultSoleDist != null) {
                finalPointbackProcess(
                    transaction,
                    defaultSoleDist.getMemberNo(), 
                    defaultSoleDist.getSoleDistNo(), 
                    AppConstants.NodeType.SOLE_DIST, 
                    "sole_dist",  
                    policy.getAffiliateRecComm(), 
                    accType
                ); 
            }
        }
		
		/*
		 * 가맹점의 2대 추천인 적립 
		 * */
		if (innerTarget.getAffiliateSecondRecommenderMemberNo() != null) {
			finalPointbackProcess(
				transaction, 
				innerTarget.getAffiliateSecondRecommenderMemberNo(), 
                innerTarget.getAffiliateSecondRecommenderMemberNo(),
                AppConstants.NodeType.RECOMMENDER, "recommender", 
                policy.getAffiliateRecManagerComm(), 
                accType); 
		}else {
            if (defaultSoleDist != null) {
                finalPointbackProcess(
                    transaction,
                    defaultSoleDist.getMemberNo(), 
                    defaultSoleDist.getSoleDistNo(), 
                    AppConstants.NodeType.SOLE_DIST, 
                    "sole_dist",  
                    policy.getAffiliateRecManagerComm(), 
                    accType
                ); 
            }
        }
		
		/*
		 * 대리점 포인트 적립
		 * */
		if (innerTarget.getAgencyNo() != null) {
			finalPointbackProcess(
				transaction,
				innerTarget.getAgencyMemberNo(),
				innerTarget.getAgencyNo(),
				AppConstants.NodeType.AGENCY, 
				"agency", 
				policy.getAgancyComm(),
				accType);
		}else {
            if (defaultSoleDist != null) {
                finalPointbackProcess(
                    transaction,
                    defaultSoleDist.getMemberNo(), 
                    defaultSoleDist.getSoleDistNo(), 
                    AppConstants.NodeType.SOLE_DIST, 
                    "sole_dist",  
                    policy.getAffiliateRecManagerComm(), 
                    accType
                ); 
            }
        }
		
		/*
		 * 대리점의 1대 추천인 포인트 적립
		 * */
		if (innerTarget.getAgencyFirstRecommenderMemberNo() != null) {
			finalPointbackProcess(
				transaction,
				innerTarget.getAgencyFirstRecommenderMemberNo(),
				innerTarget.getAgencyFirstRecommenderMemberNo(),
				AppConstants.NodeType.RECOMMENDER,
				"recommender", 
				policy.getAgancyRecComm(), 
				accType);
		}else {
            if (defaultSoleDist != null) {
                finalPointbackProcess(
                    transaction,
                    defaultSoleDist.getMemberNo(), 
                    defaultSoleDist.getSoleDistNo(), 
                    AppConstants.NodeType.SOLE_DIST, 
                    "sole_dist",  
                    policy.getAgancyRecComm(), 
                    accType
                ); 
            }
        }
		
		/*
		 * 대리점의 2대 추천인 포인트 적립
		 * */
		if (innerTarget.getAgencySecondRecommenderMemberNo() != null) {
			finalPointbackProcess(
				transaction, 
				innerTarget.getAgencySecondRecommenderMemberNo(),
				innerTarget.getAgencySecondRecommenderMemberNo(), 
				AppConstants.NodeType.RECOMMENDER,
				"recommender", 
				policy.getAgancyRecManagerComm(), 
				accType);
		}else {
            if (defaultSoleDist != null) {
                finalPointbackProcess(
                    transaction,
                    defaultSoleDist.getMemberNo(), 
                    defaultSoleDist.getSoleDistNo(), 
                    AppConstants.NodeType.SOLE_DIST, 
                    "sole_dist",  
                    policy.getAgancyRecManagerComm(), 
                    accType);
            }
        }
		
		/*
		 * 지사 포인트 적립 
		 * */
		if (innerTarget.getBranchNo() != null) {
			finalPointbackProcess(transaction, innerTarget.getBranchMemberNo(), innerTarget.getBranchNo(),
				AppConstants.NodeType.BRANCH, "branch", policy.getBranchComm(), accType);
		}else {
            if (defaultSoleDist != null) {
                finalPointbackProcess(
                    transaction,
                    defaultSoleDist.getMemberNo(), 
                    defaultSoleDist.getSoleDistNo(), 
                    AppConstants.NodeType.SOLE_DIST, 
                    "sole_dist",  
                    policy.getAffiliateRecManagerComm(), 
                    accType
                ); 
            }
        }
		
		/*
		 * 지사의 1대 추천인 포인트 적립
		 * */
		if (innerTarget.getBranchFirstRecommenderMemberNo() != null) {
			finalPointbackProcess(transaction, innerTarget.getBranchFirstRecommenderMemberNo(),
				innerTarget.getBranchFirstRecommenderMemberNo(), AppConstants.NodeType.RECOMMENDER,
				"recommender", policy.getBranchRecComm(), accType);
		}else {
            if (defaultSoleDist != null) {
                finalPointbackProcess(
                    transaction,
                    defaultSoleDist.getMemberNo(), 
                    defaultSoleDist.getSoleDistNo(), 
                    AppConstants.NodeType.SOLE_DIST, 
                    "sole_dist",  
                    policy.getBranchRecComm(), 
                    accType);
            }
        }
		
		/*
		 * 지사의 2대 추천인 포인트 적립
		 * */
		if (innerTarget.getBranchSecondRecommenderMemberNo() != null) {
			finalPointbackProcess(transaction, innerTarget.getBranchSecondRecommenderMemberNo(),
				innerTarget.getBranchSecondRecommenderMemberNo(), AppConstants.NodeType.RECOMMENDER,
				"recommender", policy.getBranchRecManagerComm(), accType);
		}else {
            if (defaultSoleDist != null) {
                finalPointbackProcess(
                    transaction,
                    defaultSoleDist.getMemberNo(), 
                    defaultSoleDist.getSoleDistNo(), 
                    AppConstants.NodeType.SOLE_DIST, 
                    "sole_dist",  
                    policy.getBranchRecManagerComm(), 
                    accType);
            }
        }
		
		/*
		 * 총판 포인트 적립
		 * */
		if (innerTarget.getSoleDistNo() != null) {
			finalPointbackProcess(transaction, innerTarget.getSoleDistMemberNo(), innerTarget.getSoleDistNo(),
				AppConstants.NodeType.SOLE_DIST, "sole_dist", policy.getSoleDistComm(), accType);
		}else {
            if (defaultSoleDist != null) {
                finalPointbackProcess(
                    transaction,
                    defaultSoleDist.getMemberNo(), 
                    defaultSoleDist.getSoleDistNo(), 
                    AppConstants.NodeType.SOLE_DIST, 
                    "sole_dist",  
                    policy.getAffiliateRecManagerComm(), 
                    accType
                ); 
            }
        }
		
		/*모든 적립이 진행된 경우 vanTransaction 의 적립 상태를 COMPLETE 로 변경*/
		String status = accType ==  1 ? AppConstants.GreenPointAccStatus.POINTBACK_COMPLETE  : 
			AppConstants.GreenPointAccStatus.POINTBACK_CANCEL_COMPLETE ;
		
		transaction.setPointBackStatus(status);
		this.paymentTransactionMapper.updateByPrimaryKey(transaction);
		res.setMessage(this.messageUtils.getMessage("pointback.message.success_pointback"));
		res.setResultCode(AppConstants.ResponsResultCode.SUCCESS);;
		return res;
		}catch(Exception e) {
			e.printStackTrace();
		}
		return null;
	}

	/* 
	 * 관리자에 의한 적립 처리 메서드로, 경희 차장님이 작성한 쿼리를 사용하며, 쿼리가 안정될 경우
	 * 큐알에 의한 적립 처리 메서드도 해당 쿼리를 사용하게끔 변경
	 * 
	 * 개인이 정상적으로 가맹점에서 결제할 때의 처리가 아닌, 개별 노드에게 직접 포인트를 부여하고, 
	 * 포인트 적립 절차를 진행하는 특수한 경우에 사용함 
	 * 
	 * 개인이 정상적으로 가맹점에서 결제할 때의 처리가 아닌, 개별 노드에게 직접 포인트를 부여하고, 
	 * 포인트 적립 절차를 진행하는 특수한 경우에 사용함 
	 * 
	 * 현재는 소스틑 구현되어 있지 않지만 사용하지 않으며, 추후 필요시 주석 제거함 
	 */
	@Override
	public ReturnpBaseResponse accumulateByAdminEx(int paymentTransactionNo, int accType) {
		ReturnpBaseResponse res = new ReturnpBaseResponse();
/*		PaymentTransaction paymentTransaction = this.paymentTransactionMapper.selectByPrimaryKey(paymentTransactionNo);
		System.out.println("노드 타입  : " + paymentTransaction.getNodeType());
		System.out.println("노드 no : " + paymentTransaction.getNodeType());
		System.out.println("affilite No  :  " + paymentTransaction.getAffiliateNo());
		
		대상 노드 검색 
		HashMap<String, Object> param = new HashMap<String, Object>();
		param.put("node_type", StringUtils.isNotEmpty(paymentTransaction.getNodeType()) ? paymentTransaction.getNodeType() : null);
		param.put("node_no", paymentTransaction.getNodeNo() != null  ? paymentTransaction.getNodeNo() : null);
		param.put("affiliate_no", paymentTransaction.getAffiliateNo() != null ? paymentTransaction.getAffiliateNo():   null);
		ArrayList<HashMap<String, Object>> nodes  = this.pointBackMapper.selectDirectNodes(param);
		if (nodes.size() < 1) {
			res.setResultCode("900");
			res.setMessage(this.messageUtils.getMessage("pointback.message.not_node"));
			return res;
		}
		
		정책 조회
		Policy policy = new Policy();
		ArrayList<Policy> policies = this.pointBackMapper.findPolicies(policy);
		policy = policies.get(policies.size() -1 );
		GreenPoint point = null;
		
		
		 * 유일한 1개의 SoleDist 검색
		 * 관련 노드가 존재하지 않을 경우 Default 로 SoleDist(총판)에게 분배  
		 * 
		ArrayList<SoleDist> soleDistList = this.pointBackMapper.findSoleDists(new SoleDist());
		SoleDist defaultSoleDist = null;
		if (soleDistList .size() >= 0) defaultSoleDist  = soleDistList.get(0);
		
		int memberNo = 0;
		int nodeNo = 0;
		String nodeType =null;  
		String nodeTypeName = null;
		float accRate = 0.0f;
		
		boolean existRecommender = false;
		boolean existSaleManager  = false;
		float recommenderAccRate = 0.0f;
		float saleManagerAccRate = 0.0f;
		
		for (HashMap<String, Object> row : nodes ) {
			nodeType = (String)row.get("node_type");
			memberNo = (Integer)row.get("memberNo");
			nodeNo = (Integer)row.get("node_no");
			switch(nodeType) {
			case "1":
				nodeTypeName = "member";
				accRate= policy.getCustomerComm();
				
				if (((Integer)row.get("recommenderNo")) != null) {
					recommenderAccRate = policy.getCustomerRecCom();
					existRecommender = true;
				}else {
					existRecommender = false;
					recommenderAccRate = 0.0f;
				}
				
				if (((Integer)row.get("saleManagerNo")) != null) {
					saleManagerAccRate = policy.getCustomerRecManagerComm();
					existSaleManager = true;
				}else {
					existSaleManager = false;
					saleManagerAccRate = 0.0f;
				}
				
				insertPointbackRecord(paymentTransaction, memberNo, nodeNo, nodeType, nodeTypeName,  accRate, accType);
				break;
			
			case "3":
				nodeTypeName = "branch";
				accRate= policy.getBranchComm();
				if (((Integer)row.get("recommenderNo")) != null) {
					recommenderAccRate = policy.getBranchRecComm();
					existRecommender = true;
				}else {
					existRecommender = false;
					recommenderAccRate = 0.0f;
				}
				
				if (((Integer)row.get("saleManagerNo")) != null) {
					saleManagerAccRate = policy.getBranchRecManagerComm();
					existSaleManager = true;
				}else {
					existSaleManager = false;
					saleManagerAccRate = 0.0f;
				}
				
				insertPointbackRecord(paymentTransaction, memberNo, nodeNo, nodeType, nodeTypeName,  accRate, accType);
				break;
			
			case "4":
				nodeTypeName = "agency";
				accRate= policy.getAgancyComm();
				if (((Integer)row.get("recommenderNo")) != null) {
					recommenderAccRate =policy.getAgancyRecComm();
					existRecommender = true;
				}else {
					existRecommender = false;
					recommenderAccRate = 0.0f;
				}
				
				if (((Integer)row.get("saleManagerNo")) != null) {
					saleManagerAccRate = policy.getAgancyRecManagerComm();
					existSaleManager = true;
				}else {
					existSaleManager = false;
					saleManagerAccRate = 0.0f;
				}
				
				insertPointbackRecord(paymentTransaction, memberNo, nodeNo, nodeType, nodeTypeName,  accRate, accType);
				break;
			
			case "5":
				nodeTypeName = "affiliate";
				accRate= policy.getAffiliateComm();
				if (((Integer)row.get("recommenderNo")) != null) {
					recommenderAccRate = policy.getAffiliateRecManagerComm();
					existRecommender = true;
				}else {
					existRecommender = false;
					recommenderAccRate = 0.0f;
				}
				
				if (((Integer)row.get("saleManagerNo")) != null) {
					saleManagerAccRate = policy.getAffiliateRecManagerComm();
					existSaleManager = true;
				}else {
					existSaleManager = false;
					saleManagerAccRate = 0.0f;
				}
				
				insertPointbackRecord(paymentTransaction, memberNo, nodeNo, nodeType, nodeTypeName,  accRate, accType);
				break;
			
			case "7":
				nodeTypeName = "soleDist";
				accRate= policy.getSoleDistComm();
				existSaleManager = false;
				existRecommender = false;
				recommenderAccRate = 0.0f;
				saleManagerAccRate = 0.0f;
				insertPointbackRecord(paymentTransaction, memberNo, nodeNo, nodeType, nodeTypeName,  accRate, accType);
				break;
			}
			
			if (existRecommender) {
				nodeType = "2";
				memberNo = (Integer)row.get("recommenderMemberNo");
				nodeNo = (Integer)row.get("recommenderNo");
				accRate = recommenderAccRate;
				nodeTypeName = "recommender";
				insertPointbackRecord(paymentTransaction, memberNo, nodeNo, nodeType, nodeTypeName,  accRate, accType);
			}else {
				if (defaultSoleDist != null) {
					nodeType = "7";
					memberNo = defaultSoleDist.getMemberNo();
					nodeNo = defaultSoleDist.getSoleDistNo();
					accRate = recommenderAccRate;
					nodeTypeName = "no_recom_default_sole_dist";
					insertPointbackRecord(paymentTransaction, memberNo, nodeNo, nodeType, nodeTypeName,  accRate, accType);
				}
			}
			
			if (existSaleManager) {
				nodeType = "2";
				memberNo = (Integer)row.get("saleManagerMemberNo");
				nodeNo = (Integer)row.get("saleManagerNo");
				accRate = saleManagerAccRate;
				nodeTypeName = "sale_manager";
				insertPointbackRecord(paymentTransaction, memberNo, nodeNo, nodeType, nodeTypeName,  accRate, accType);
			}else {
				if (defaultSoleDist != null) {
					nodeType = "7";
					memberNo = defaultSoleDist.getMemberNo();
					nodeNo = defaultSoleDist.getSoleDistNo();
					accRate = saleManagerAccRate;
					nodeTypeName = "no_manager_default_sole_dist";
					insertPointbackRecord(paymentTransaction, memberNo, nodeNo, nodeType, nodeTypeName,  accRate, accType);
				}
			}
			
			accRate= 0.0f;
			recommenderAccRate = 0.0f;
			saleManagerAccRate = 0.0f;
			existSaleManager = false;
			existRecommender = false;
		}
		
		모든 적립이 진행된 경우 vanTransaction 의 적립 상태를 COMPLETE 로 변경
		String status = accType ==  1 ? AppConstants.GreenPointAccStatus.POINTBACK_COMPLETE  : 
			AppConstants.GreenPointAccStatus.POINTBACK_CANCEL_COMPLETE ;
		
		paymentTransaction.setPointBackStatus(status);
		this.paymentTransactionMapper.updateByPrimaryKey(paymentTransaction);
		res.setMessage(this.messageUtils.getMessage("pointback.message.success_pointback"));
		res.setResultCode(AppConstants.ResponsResultCode.SUCCESS);;*/
		return res;
	}
	
	
	/* 
	 * 인코딩된 큐알 코드 스캔에 의한 적립 및 취소 처리 
	 */
	@Override
	public ReturnpBaseResponse processByEncQr(
			String qrOrg, 
			int pam, 
			String pas, 
			Date pat, 
			String pan, 
			String afId, 
			String memberEmail,
			String phoneNumber,
			String  phoneNumberCountry) {
		ReturnpBaseResponse res = new ReturnpBaseResponse();
		String decode64Qr;
		/*
		 * 기본 결제 번호만으로는 중복이 될 수 있기 때문에 
		 * 결제 번호에 TID 를 연결하여 TID 별 결제 번호를 생성
		 *  */
		pan = afId + "-" + pan;
		
		try {
			decode64Qr = BASE64Util.decodeString(qrOrg);
			URL url = new URL(decode64Qr);
			String queryParmStr =url.getQuery();
			
			HashMap<String, String> qrParsemap = QRManager.parseQRToMap(queryParmStr);
			if (qrParsemap == null) {
				res.setMessage(this.messageUtils.getMessage("pointback.message.invalid_qr"));
				res.setResultCode("600");
				res.setResult("error");
				return res;
			}
			
			/*존재 하는 회원 인지 검사*/
			ArrayList<Member> members = null;
			Member member = new Member();;
			member.setMemberEmail(memberEmail);
			members = this.pointBackMapper.findMembers(member);
			
			if (members.size() !=1 || members == null) {
				ResponseUtil.setResponse(res, "601", this.messageUtils.getMessage("pointback.message.not_member"));
				return res;
			}
			
			member = members.get(0);
			/*기기의 전화번호와 해당 이메일 계정의 전화번호가 같은 비교*/
			/*
			 * 핸드폰 번호와 회원 이메일로 존재하는 회원인지 조사
			 * 핸드폰 번호로만 조회할 경우, 타인의 아이디로 로그인 했을 때, 그 기기의 번호가 이미 등록되어 있다면 
			 * 그 기기 번호에 해당하는 사람에게 포인트가 적립되는 문제가 발생함 
			 * 사용자가 국가 번호가 포함된 전화번호나 포함되지 않는 전화번호를 가입할 때 입력할 수 있기 때문에
			 * 두가지 다 비교함 
			 */
			if (!member.getMemberPhone().equals(phoneNumber) && 
					!member.getMemberPhone().equals(phoneNumberCountry)) {
				ResponseUtil.setResponse(res, "602", this.messageUtils.getMessage("pointback.message.invalid_phonenumber"));
				throw new ReturnpException(res);
			}
			
			/* 이미 적립처리가 된  결제인지 조사 */
			PaymentTransaction ptCond = new PaymentTransaction();
			//ptCond.setPaymentApprovalNumber(qrParsemap.get("pan"));
			ptCond.setPaymentApprovalNumber(pan);
			ArrayList<PaymentTransaction> paymentTransactions = this.pointBackMapper.findPaymentTransactions(ptCond);
			
			/*사용자의 요청이 결제 승인에 의한 적립요청일 경우 */
			if (pas.equals("0")) {
				if (paymentTransactions.size()> 0) {
					res.setMessage(this.messageUtils.getMessage("pointback.message.already_register_payment_acc"));
					res.setResultCode("606");
					res.setResult("error");
					return res;
				}
			}
			/*사용자의 요청이 결제 취소에 의한 적립 취소 요청일 경우 */
			else if (pas.equals("1")){
				if (paymentTransactions.size() == 1)  {
					if (!paymentTransactions.get(0).getPaymentApprovalStatus().equals(AppConstants.PaymentApprovalStatus.PAYMENT_APPROVAL_OK)) {
						res.setMessage(this.messageUtils.getMessage("pointback.message.not_cancelable_paymment_a"));
						res.setResultCode("607");
						res.setResult("error");
						return res;
					}
				}else {
					res.setMessage(this.messageUtils.getMessage("pointback.message.not_cancelable_paymment_b"));
					res.setResultCode("608");
					res.setResult("error");
					return res;
				}
			}
			
			/* 존재하는 가맹점인지 검사 */
			Affiliate affiliateCond = new Affiliate();
			//affiliateCond.setAffiliateSerial(qrParsemap.get("af_id"));
			affiliateCond.setAffiliateSerial(afId);
			ArrayList<Affiliate> affiliates = this.pointBackMapper.findAffiliates(affiliateCond);
			if (affiliates.size() != 1 || affiliates  == null) {
				res.setMessage(this.messageUtils.getMessage("pointback.message.invalid_qr"));
				res.setResultCode("601");
				res.setResult("error");
				return res;
			} 
			
			/* 가맹점 시리얼 고유 번호 비교  */
			if (!affiliates.get(0).getAffiliateSerial().equals(afId)) {
				res.setMessage(this.messageUtils.getMessage("pointback.message.invalid_qr"));
				res.setResultCode("602");
				res.setResult("error");
				return res;
			}
		
			/*
			 * 결제 내역 등록
			 *  */
			Member targetMember = members.get(0);
			Affiliate targetAffiliate = affiliates.get(0);
			System.out.println("원문 데이타");
			System.out.println(BASE64Util.decodeString(qrOrg));
			PaymentTransaction paymentTransaction = new PaymentTransaction();
			paymentTransaction.setMemberNo(targetMember.getMemberNo());
			paymentTransaction.setMemberName(targetMember.getMemberName());
			paymentTransaction.setMemberEmail(targetMember.getMemberEmail());
			paymentTransaction.setNodeNo(targetMember.getMemberNo());
			paymentTransaction.setNodeType("1");
			paymentTransaction.setNodeEmail(targetMember.getMemberEmail());
			paymentTransaction.setNodeName(targetMember.getMemberName());;
			paymentTransaction.setMemberPhone(targetMember.getMemberPhone());
			paymentTransaction.setAffiliateNo(targetAffiliate.getAffiliateNo());
			paymentTransaction.setAffiliateSerial(targetAffiliate.getAffiliateSerial());
			paymentTransaction.setOrgPaymentData(BASE64Util.decodeString(qrOrg));
			paymentTransaction.setPaymentApprovalAmount(pam);
			paymentTransaction.setPaymentApprovalNumber(pan);
			
			Date date = new Date();
			paymentTransaction.setCreateTime(date);
			paymentTransaction.setUpdateTime(date);
			
			/* QR 코드에서 승인 상태는 0 : 승인 완료 1 : 승인 취소임, 따라서 내부코드로 변경*/
			paymentTransaction.setPaymentApprovalStatus(
					pas.equals("0") ? AppConstants.PaymentApprovalStatus.PAYMENT_APPROVAL_OK : 
						AppConstants.PaymentApprovalStatus.PAYMENT_APPROVAL_CANCEL);
			paymentTransaction.setPointBackStatus(AppConstants.GreenPointAccStatus.POINTBACK_PROGRESS);
			paymentTransaction.setPaymentTransactionType(AppConstants.PaymentTransactionType.QR);
			paymentTransaction.setPaymentApprovalDateTime(pat);
			paymentTransaction.setRegAdminNo(0);
			this.paymentTransactionMapper.insert(paymentTransaction);
			
			/* 적립 유효성 체크 및 적립 시작*/
			/*결제 승인*/
			if (pas.equals("0")) {
				/* 결제 상태가 결제 승인이 아니라면 중지 */
				if (!paymentTransaction.getPaymentApprovalStatus().trim().equals(AppConstants.PaymentApprovalStatus.PAYMENT_APPROVAL_OK)) {
					res.setResultCode("701");
					res.setMessage(this.messageUtils.getMessage("pointback.message.invalid_qr"));
					return res;
				}
				
				/* 이미 적립이 완료된 거래내역인 경우 적립 처리 금지, 중복 적립 금지*/
				if (paymentTransaction.getPointBackStatus().trim().equals(AppConstants.GreenPointAccStatus.POINTBACK_COMPLETE)  || 
						paymentTransaction.getPointBackStatus().trim().equals(AppConstants.GreenPointAccStatus.POINTBACK_STOP) ) {
					res.setResultCode("703");
					res.setMessage(this.messageUtils.getMessage("pointback.message.completed_request_acc"));
					return res; 
				}
				return this.accumulatePoint(paymentTransaction, 1);
				
			} else if (pas.equals("1")){
				/* 결제 상태가 결제 승인 취소가  아니라면 중지*/
				if (!paymentTransaction.getPaymentApprovalStatus().trim().equals(AppConstants.PaymentApprovalStatus.PAYMENT_APPROVAL_CANCEL)) {
					res.setResultCode("705");
					res.setMessage(this.messageUtils.getMessage("pointback.message.invalid_qr"));
					return res;
				}
				
				/*"이미 Green Point 취소 처리가 완료된 요청, 중복 취소 요 방지  */
				if (paymentTransaction.getPointBackStatus().trim().equals(AppConstants.GreenPointAccStatus.POINTBACK_CANCEL_COMPLETE)  || 
						paymentTransaction.getPointBackStatus().trim().equals(AppConstants.GreenPointAccStatus.POINTBACK_STOP)) {
					res.setResultCode("706");
					res.setMessage(this.messageUtils.getMessage("pointback.message.completed_request_acc_cancel"));
					return res;
				}
				return this.accumulatePoint(paymentTransaction, -1);
			}

			res.setMessage(this.messageUtils.getMessage("pointback.message.success_acc_ok"));
			res.setResultCode("100");
			return res;
			
			/* 이후 포인트 백 처리 작업 진행*/
		} catch (Exception e) {
			e.printStackTrace();
			res.setMessage(this.messageUtils.getMessage("pointback.message.success_acc_error"));
			res.setResultCode("605");
			res.setResult("error");
			return res;
		}
	}
	
	/* 
	 * 포인트 적립 메서드 	
	 * 연결된 노드 및 추천인이 없을 경우, 모두 총판으로 해당 포인트를 적립함
	 */
	@Override
	public ReturnpBaseResponse accumulatePoint(PaymentTransaction transaction, int accType) {
		ReturnpBaseResponse res = new ReturnpBaseResponse();
		
		/*정책 조회*/
		Policy policy = new Policy();
		ArrayList<Policy> policies = this.pointBackMapper.findPolicies(policy);
		policy = policies.get(policies.size() -1 );
		GreenPoint point = null;
		
		/*
		 * 유일한 1개의 SoleDist 검색
		 * 관련 노드가 존재하지 않을 경우 Default 로 SoleDist(총판)에게 분배  
		 * */
		ArrayList<SoleDist> soleDistList = this.pointBackMapper.findSoleDists(new SoleDist());
		SoleDist defaultSoleDist = null;
		if (soleDistList .size() >= 0) {
			defaultSoleDist  = soleDistList.get(0);
		}
		
		OuterPointBackTarget outerTarget = new OuterPointBackTarget();
		outerTarget.setMemberNo(transaction.getMemberNo());
		outerTarget = this.findOuterPointBackTarget(outerTarget);
		
		if (outerTarget == null) return null;
		
		/*
		 * 소비자 포인트 적립 
		 * */
		if (outerTarget.getMemberNo() != null) {
			finalPointbackProcess(
				transaction,
				outerTarget.getMemberNo(), 
				outerTarget.getMemberNo(), 
				AppConstants.NodeType.MEMBER, 
				"member",  
				policy.getCustomerComm(), 
				accType
			); 
		}
		
		/* 
		 * 소비자의 1대 추천인 포인트 적립
		 * */
		if (outerTarget.getFirstRecommenderMemberNo() != null) {
			finalPointbackProcess(
				transaction,
				outerTarget.getFirstRecommenderMemberNo(), 
				outerTarget.getFirstRecommenderMemberNo(), 
				AppConstants.NodeType.RECOMMENDER, 
				"recommender",  
				policy.getCustomerRecCom(), 
				accType
			); 
		}else {
			if (defaultSoleDist != null) {
					finalPointbackProcess(
					transaction,
					defaultSoleDist.getMemberNo(), 
					defaultSoleDist.getSoleDistNo(), 
					AppConstants.NodeType.SOLE_DIST, 
					"sole_dist",  
					policy.getCustomerRecCom(), 
					accType
				); 
			}
		}
		
		/* 
		 * 소비자의 2대 추천인 포인트 적립
		 * */
		if (outerTarget.getSecondRecommenderMemberNo() != null) {
			finalPointbackProcess(
				transaction, 
				outerTarget.getSecondRecommenderMemberNo(), 
				outerTarget.getSecondRecommenderMemberNo(), 
				AppConstants.NodeType.RECOMMENDER, "recommender",  
				policy.getCustomerRecManagerComm(), 
				accType
			); 
		}else {
			if (defaultSoleDist != null) {
				finalPointbackProcess(
					transaction,
					defaultSoleDist.getMemberNo(), 
					defaultSoleDist.getSoleDistNo(), 
					AppConstants.NodeType.SOLE_DIST, 
					"sole_dist",  
					policy.getCustomerRecManagerComm(), 
					accType
				); 
				
			}
		}
		
		/* 
		 * Inner Node 적립(협력업체, 협력업체 추천인, 추천인의 영업 관리자, 대리점, 지사 총판)
		 * */
		
		InnerPointBackTarget innerTarget = this.findInnerPointBackTarget(transaction.getAffiliateSerial());
		Affiliate targetAffiliate = this.affiliateMapper.selectBySerial(transaction.getAffiliateSerial());
		float affiliateComm =  targetAffiliate.getAffiliateComm() > 0 ?  targetAffiliate.getAffiliateComm()  : policy.getAffiliateComm();
		
		/*
		 * 가맹점 포인트 적립
		 * */
		if (innerTarget.getAffiliateNo() != null) {
			finalPointbackProcess(
				transaction, 
				innerTarget.getAffiliateMemberNo(),
				innerTarget.getAffiliateNo(), 
				AppConstants.NodeType.AFFILIATE, "affiliate", 
				affiliateComm, 
				accType
			); 
		}
		
		/*
		 * 가맹점의 1대 추천인 포인트 적립
		 * */
		if (innerTarget.getAffiliateFirstRecommenderMemberNo() != null) {
			finalPointbackProcess(
				transaction, 
				innerTarget.getAffiliateFirstRecommenderMemberNo(),
				innerTarget.getAffiliateFirstRecommenderMemberNo(),
				AppConstants.NodeType.RECOMMENDER, 
				"recommender",
				policy.getAffiliateRecComm(), accType
			); 
		}else {
			if (defaultSoleDist != null) {
				finalPointbackProcess(
					transaction,
					defaultSoleDist.getMemberNo(), 
					defaultSoleDist.getSoleDistNo(), 
					AppConstants.NodeType.SOLE_DIST, 
					"sole_dist",  
					policy.getAffiliateRecComm(), 
					accType
				); 
			}
		}
		
		/*
		 * 가맹점의 2대 추천인 포인트 적립
		 * */
		if (innerTarget.getAffiliateSecondRecommenderMemberNo() != null) {
			finalPointbackProcess(
				transaction, 
				innerTarget.getAffiliateSecondRecommenderMemberNo(), 
				innerTarget.getAffiliateSecondRecommenderMemberNo(),
				AppConstants.NodeType.RECOMMENDER, "recommender", 
				policy.getAffiliateRecManagerComm(), 
				accType); 
		}else {
			if (defaultSoleDist != null) {
				finalPointbackProcess(
					transaction,
					defaultSoleDist.getMemberNo(), 
					defaultSoleDist.getSoleDistNo(), 
					AppConstants.NodeType.SOLE_DIST, 
					"sole_dist",  
					policy.getAffiliateRecManagerComm(), 
					accType
				); 
			}
		}
		
		/*
		 * 대리점 포인트 적립
		 * */
		if (innerTarget.getAgencyNo() != null) {
			finalPointbackProcess(
				transaction, 
				innerTarget.getAgencyMemberNo(), 
				innerTarget.getAgencyNo(), 
				AppConstants.NodeType.AGENCY, 
				"agency",  
				policy.getAgancyComm(), 
				accType); 
		}else {
			if (defaultSoleDist != null) {
				finalPointbackProcess(
					transaction,
					defaultSoleDist.getMemberNo(), 
					defaultSoleDist.getSoleDistNo(), 
					AppConstants.NodeType.SOLE_DIST, 
					"sole_dist",  
					policy.getAffiliateRecManagerComm(), 
					accType
				); 
			}
		}
		
		/*
		 * 대리점의 1대 추천인 포인트 지급
		 * */
		if (innerTarget.getAgencyFirstRecommenderMemberNo() != null) {
			finalPointbackProcess(
				transaction, 
				innerTarget.getAgencyFirstRecommenderMemberNo(), 
				innerTarget.getAgencyFirstRecommenderMemberNo(), 
				AppConstants.NodeType.RECOMMENDER, 
				"recommender", 
				policy.getAgancyRecComm(), 
				accType); 
		}else {
			if (defaultSoleDist != null) {
				finalPointbackProcess(
					transaction,
					defaultSoleDist.getMemberNo(), 
					defaultSoleDist.getSoleDistNo(), 
					AppConstants.NodeType.SOLE_DIST, 
					"sole_dist",  
					policy.getAgancyRecComm(), 
					accType
				); 
			}
		}
		
		/*
		 * 대리점의 2대 추천인 포인트 지급
		 * */
		if (innerTarget.getAgencySecondRecommenderMemberNo() != null) {
			finalPointbackProcess(
				transaction,
				innerTarget.getAgencySecondRecommenderMemberNo(),
				innerTarget.getAgencySecondRecommenderMemberNo(),
				AppConstants.NodeType.RECOMMENDER,
				"recommender", 
				policy.getAgancyRecManagerComm(),
				accType); 
		}else {
			if (defaultSoleDist != null) {
				finalPointbackProcess(
					transaction,
					defaultSoleDist.getMemberNo(), 
					defaultSoleDist.getSoleDistNo(), 
					AppConstants.NodeType.SOLE_DIST, 
					"sole_dist",  
					policy.getAgancyRecManagerComm(), 
					accType);
			}
		}
		
		/*
		 * 지사 포인트 적립 
		 * */
		if (innerTarget.getBranchNo() != null) {
			finalPointbackProcess(
				transaction,
				innerTarget.getBranchMemberNo(),
				innerTarget.getBranchNo(),
				AppConstants.NodeType.BRANCH, 
				"branch",  
				policy.getBranchComm(), 
				accType); 
		}else {
			if (defaultSoleDist != null) {
				finalPointbackProcess(
					transaction,
					defaultSoleDist.getMemberNo(), 
					defaultSoleDist.getSoleDistNo(), 
					AppConstants.NodeType.SOLE_DIST, 
					"sole_dist",  
					policy.getAffiliateRecManagerComm(), 
					accType
				); 
			}
		}
		
		
		/*
		 * 지사1의 1대 추천인 포인트 적립
		 * */
		if (innerTarget.getBranchFirstRecommenderMemberNo() != null) {
			finalPointbackProcess(
				transaction, 
				innerTarget.getBranchFirstRecommenderMemberNo()
				, innerTarget.getBranchFirstRecommenderMemberNo(), 
				AppConstants.NodeType.RECOMMENDER, 
				"recommender", 
				policy.getBranchRecComm(),
				accType); 
		}else {
			if (defaultSoleDist != null) {
				finalPointbackProcess(
					transaction,
					defaultSoleDist.getMemberNo(), 
					defaultSoleDist.getSoleDistNo(), 
					AppConstants.NodeType.SOLE_DIST, 
					"sole_dist",  
					policy.getBranchRecComm(), 
					accType);
			}
		}
		
		/*
		 * 지사의 2대 추천인 포인트 적립
		 * */
		if (innerTarget.getBranchSecondRecommenderMemberNo() != null) {
			finalPointbackProcess(
				transaction, 
				innerTarget.getBranchSecondRecommenderMemberNo(), 
				innerTarget.getBranchSecondRecommenderMemberNo(),
				AppConstants.NodeType.RECOMMENDER, 
				"recommender",  
				policy.getBranchRecManagerComm(),
				accType); 
			
		}else {
			if (defaultSoleDist != null) {
				finalPointbackProcess(
					transaction,
					defaultSoleDist.getMemberNo(), 
					defaultSoleDist.getSoleDistNo(), 
					AppConstants.NodeType.SOLE_DIST, 
					"sole_dist",  
					policy.getBranchRecManagerComm(), 
					accType);
			}
		}
		
		/*
		 * 총판 포인트 적립
		 * */
		if (innerTarget.getSoleDistNo() != null) {
			finalPointbackProcess(
				transaction,
				innerTarget.getSoleDistMemberNo(), 
				innerTarget.getSoleDistNo(), 
				AppConstants.NodeType.SOLE_DIST, "sole_dist", 
				policy.getSoleDistComm(), 
				accType); 
		}else {
			if (defaultSoleDist != null) {
				finalPointbackProcess(
					transaction,
					defaultSoleDist.getMemberNo(), 
					defaultSoleDist.getSoleDistNo(), 
					AppConstants.NodeType.SOLE_DIST, 
					"sole_dist",  
					policy.getAffiliateRecManagerComm(), 
					accType
				); 
			}
		}
		
		
		/*모든 적립이 진행된 경우 vanTransaction 의 적립 상태를 COMPLETE 로 변경*/
		String status = accType ==  1 ? AppConstants.GreenPointAccStatus.POINTBACK_COMPLETE  : 
			AppConstants.GreenPointAccStatus.POINTBACK_CANCEL_COMPLETE ;
		
		transaction.setPointBackStatus(status);
		this.paymentTransactionMapper.updateByPrimaryKey(transaction);
		res.setMessage(this.messageUtils.getMessage("pointback.message.success_pointback"));
		res.setResultCode(AppConstants.ResponsResultCode.SUCCESS);;
		return res;
	}
	

	@Override
	public void  finalPointbackProcess(PaymentTransaction transaction, int memberNo, int nodeNo, String nodeType, String nodeTypeName, float accRate, int accType) {
		/* 해당 그린포인트 업데이트*/
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
		point.setPointAmount(point.getPointAmount() + (accType * transaction.getPaymentApprovalAmount() * accRate));
		this.greenPointMapper.updateByPrimaryKey(point);
		
		/* 그린포인트 적립 내역 저장*/
		PaymentPointbackRecord pointBackRecord = new PaymentPointbackRecord();
		pointBackRecord.setPaymentTransactionNo(transaction.getPaymentTransactionNo());
		pointBackRecord.setMemberNo(memberNo);
		pointBackRecord.setNodeNo(nodeNo);
		pointBackRecord.setNodeType(nodeType);
		pointBackRecord.setAccRate(accRate);
		pointBackRecord.setPointbackAmount(accType * transaction.getPaymentApprovalAmount() * accRate);
		paymentPointbackRecordMapper.insert(pointBackRecord);
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
	
	@Override
	public ReturnpBaseResponse cancelQRPayment(HashMap<String, String> qrparseMap) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public ReturnpBaseResponse accQRPayment(HashMap<String, String> qrparseMap) {
		// TODO Auto-generated method stub
		return null;
	}
	
	@Override
	public PointBackTarget findInnerPointBackFindTarget(PointBackTarget target) {
		return this.pointBackMapper.findInnerPointBackFindTarget(target);
	}
	
	@Override
	public OuterPointBackTarget findOuterPointBackTarget(OuterPointBackTarget target) {
		return this.pointBackMapper.findOuterPointBackTarget(target);
	}
	
	@Override
	public InnerPointBackTarget findInnerPointBackTarget(String affiliateSerial) {
		return this.pointBackMapper.findInnerPointBackTarget(affiliateSerial);
	}
}
