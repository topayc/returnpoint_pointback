package com.returnp.pointback.service;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

import org.apache.commons.lang3.StringUtils;
import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.returnp.pointback.common.AppConstants;
import com.returnp.pointback.common.DataMap;
import com.returnp.pointback.common.ResponseUtil;
import com.returnp.pointback.common.ReturnpException;
import com.returnp.pointback.dao.mapper.AffiliateMapper;
import com.returnp.pointback.dao.mapper.GpointPaymentMapper;
import com.returnp.pointback.dao.mapper.GreenPointMapper;
import com.returnp.pointback.dao.mapper.PaymentPointbackRecordMapper;
import com.returnp.pointback.dao.mapper.PaymentTransactionMapper;
import com.returnp.pointback.dao.mapper.PaymentTransactionRouterMapper;
import com.returnp.pointback.dao.mapper.PointBackMapper;
import com.returnp.pointback.dao.mapper.PolicyMapper;
import com.returnp.pointback.dto.command.AffiliateCommand;
import com.returnp.pointback.dto.command.AffiliateTidCommand;
import com.returnp.pointback.dto.command.InnerPointBackTarget;
import com.returnp.pointback.dto.command.OuterPointBackTarget;
import com.returnp.pointback.dto.command.PaymentTransactionCommand;
import com.returnp.pointback.dto.command.api.ApiRequest;
import com.returnp.pointback.dto.response.ReturnpBaseResponse;
import com.returnp.pointback.model.Affiliate;
import com.returnp.pointback.model.GpointPayment;
import com.returnp.pointback.model.GreenPoint;
import com.returnp.pointback.model.Member;
import com.returnp.pointback.model.PaymentPointbackRecord;
import com.returnp.pointback.model.PaymentRouter;
import com.returnp.pointback.model.PaymentTransaction;
import com.returnp.pointback.model.PaymentTransactionRouter;
import com.returnp.pointback.model.Policy;
import com.returnp.pointback.model.SoleDist;
import com.returnp.pointback.service.interfaces.PointbackTargetService;
import com.returnp.pointback.service.interfaces.ReturnpTransactionService;
import com.returnp.pointback.web.message.MessageUtils;

@Service
public class ReturnpTransactionServiceImpl implements ReturnpTransactionService {
	private Logger logger = Logger.getLogger(ReturnpTransactionServiceImpl.class);
	  @Autowired PointBackMapper pointBackMapper;
	    @Autowired PolicyMapper policyMapper;
	    @Autowired AffiliateMapper affiliateMapper;
	    @Autowired PaymentTransactionMapper paymentTransactionMapper;
	    @Autowired GreenPointMapper greenPointMapper;
	    @Autowired PaymentPointbackRecordMapper paymentPointbackRecordMapper;
	    @Autowired MessageUtils messageUtils;
	    @Autowired Environment env;
	    @Autowired PointbackTargetService pointBackTargetService;
	    @Autowired PaymentTransactionRouterMapper paymentTransactionRouterMapper;
	    @Autowired GpointPaymentMapper gpointPaymentMapper;
	    
	@Override
	public ReturnpBaseResponse accumulate(DataMap dataMap) {
        ReturnpBaseResponse res = new ReturnpBaseResponse();
        
        try {
            switch(dataMap.getStr("payment_transaction_type").trim()){
                case AppConstants.PaymentTransactionType.QR: break;
                case AppConstants.PaymentTransactionType.MANUAL: break;
                case AppConstants.PaymentTransactionType.APP: break;
                case AppConstants.PaymentTransactionType.API: break;
            }
            
            this.validateMemberAndGet(dataMap.getStr("memberEmail"),dataMap.getStr("phoneNumber"),dataMap.getStr("phoneNumberCountry"));
            this.validateAffiliateAuthAndGet(dataMap.getStr("payment_router_type"), dataMap.getStr("payment_router_name"), dataMap.getStr("af_id"));
            /* 
			 * 강제 적립 인지 여부 확인
			 * 강제 적립인 경우에는 유효성 검사 없이 바로 적립 진행
			 * */
			if (!dataMap.containsKey("forceAcc") || !((String)dataMap.get("forceAcc")).equals("Y")) {
				this.validateAccumulateRequest(dataMap);
			}
            
            PaymentTransaction paymentTransaction = this.createPaymentTransaction(dataMap);
            this.accumuatePoint(paymentTransaction);
            ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "100", this.messageUtils.getMessage("pointback.message.success_acc_ok"));
            return res;
            
        }catch(ReturnpException e) {
            e.printStackTrace();
        	if (!TransactionAspectSupport.currentTransactionStatus().isRollbackOnly()) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
            res = e.getBaseResponse();
            return res;
        }catch(Exception e) {
            e.printStackTrace();
        	if (!TransactionAspectSupport.currentTransactionStatus().isRollbackOnly()) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
            ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR, "2000", this.messageUtils.getMessage("pointback.message.inner_server_error"));
            return res;
        }
	}

	@Override
	public ReturnpBaseResponse cancelAccumulate(DataMap dataMap) {
        ReturnpBaseResponse res = new ReturnpBaseResponse();

        try {
            switch(dataMap.getStr("payment_transaction_type").trim()){
                case AppConstants.PaymentTransactionType.QR: break;
                case AppConstants.PaymentTransactionType.MANUAL: break;
                case AppConstants.PaymentTransactionType.APP: break;
                case AppConstants.PaymentTransactionType.API: break;
            }
            
            this.validateMemberAndGet(dataMap.getStr("memberEmail"),dataMap.getStr("phoneNumber"),dataMap.getStr("phoneNumberCountry"));
            this.validateAffiliateAuthAndGet(dataMap.getStr("payment_router_type"), dataMap.getStr("payment_router_name"), dataMap.getStr("af_id"));
            
            /* 
			 * 강제 취소인지 여부 확인
			 * 강제 취소인 경우에는 유효성 검사 없이 바로 적립 진행
			 * */
			if (!dataMap.containsKey("forceCancel") || !((String)dataMap.get("forceCancel")).equals("Y")) {
				this.validateCancelRequest(dataMap);
			}
            
            this.restorePoint(dataMap);
            ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "100", this.messageUtils.getMessage("pointback.cancel_accumulate_ok"));
            return res;
        }catch(ReturnpException e) {
            e.printStackTrace();
        	if (!TransactionAspectSupport.currentTransactionStatus().isRollbackOnly()) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
            res = e.getBaseResponse();
            return res;
        }catch(Exception e) {
            e.printStackTrace();
        	if (!TransactionAspectSupport.currentTransactionStatus().isRollbackOnly()) {
				TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			}
            ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR,"2000", this.messageUtils.getMessage("pointback.message.inner_server_error"));
            return res;
        }
	}
	

	@Override
	public void restorePoint(DataMap dataMap) throws ReturnpException {
		ReturnpBaseResponse res = new ReturnpBaseResponse();
		PaymentTransaction pt = new PaymentTransaction();
		pt.setPaymentApprovalNumber(dataMap.getStr("pan"));

		ArrayList<PaymentTransaction> ptList = this.pointBackMapper.findPaymentTransactions(pt);

		/* 강제 적립 취소인지 여부 확인 */
		if (!dataMap.containsKey("forceCancel") || !((String) dataMap.get("forceCancel")).equals("Y")) {
			if (ptList == null || ptList.size() < 1) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "626",
						this.messageUtils.getMessage("pointback.message.not_existed_payment"));
				throw new ReturnpException(res);
			}

			else if (ptList.size() > 1) {
				ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "675",
						this.messageUtils.getMessage("pointback.message.already_accumulate_or_canceled"));
				throw new ReturnpException(res);
			}
		}

		PaymentPointbackRecord ppb = new PaymentPointbackRecord();
		ppb.setPaymentTransactionNo(ptList.get(0).getPaymentTransactionNo());
		ArrayList<PaymentPointbackRecord> ppbList = this.pointBackMapper.findPaymentPointbackRecords(ppb);
		if (ppbList.size() < 1)
			return;

		/* 결제 내역 생성 */
		PaymentTransaction transaction = this.createPaymentTransaction(dataMap);

		PaymentPointbackRecord record = null;
		ArrayList<GreenPoint> gPoints = null;
		GreenPoint gPoint = null;
		for (PaymentPointbackRecord pointbackRecord : ppbList) {
			/* G Point 차감 */
			gPoint = new GreenPoint();
			gPoint.setMemberNo(pointbackRecord.getMemberNo());
			gPoint.setNodeType(pointbackRecord.getNodeType());
			gPoint.setNodeNo(pointbackRecord.getNodeNo());

			gPoints = this.pointBackMapper.findGreenPoints(gPoint);
			if (gPoints.size() != 1) {
				/*
				 * System.out.println("갯수 : " + rPoints.size() ); System.out.println("멤버 번호 : "
				 * + pointbackRecord.getMemberNo() ); System.out.println("노드 번호: " +
				 * pointbackRecord.getNodeNo() ); System.out.println("노드 타입 : " +
				 * pointbackRecord.getNodeType() );
				 */
				continue;
			}

			gPoint = gPoints.get(0);
			gPoint.setPointAmount(gPoint.getPointAmount() - pointbackRecord.getPointbackAmount());
			this.greenPointMapper.updateByPrimaryKey(gPoint);

			/* G Point 차감 내역 생성 */
			record = new PaymentPointbackRecord();
			record.setPaymentTransactionNo(transaction.getPaymentTransactionNo());
			record.setMemberNo(pointbackRecord.getMemberNo());
			record.setNodeNo(record.getNodeNo());
			record.setNodeType(pointbackRecord.getNodeType());
			record.setAccRate(pointbackRecord.getAccRate());
			record.setPointbackAmount(transaction.getPaymentApprovalAmount() * pointbackRecord.getAccRate() * -1);
			paymentPointbackRecordMapper.insert(record);
		}

		transaction.setPointBackStatus(AppConstants.AccumulateStatus.POINTBACK_CANCEL_COMPLETE);
		this.paymentTransactionMapper.updateByPrimaryKey(transaction);
	}

	@Override
	public void accumuatePoint(PaymentTransaction transaction) throws ReturnpException {
		ReturnpBaseResponse res = new ReturnpBaseResponse();
        try {
            /*정책 조회*/
            Policy policy = new Policy();
            ArrayList<Policy> policies = this.pointBackMapper.findPolicies(policy);
            policy = policies.get(policies.size() -1 );
            
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
            outerTarget = this.pointBackTargetService.findOuterPointBackTarget(outerTarget);
            
            if (outerTarget == null) {
                ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "622", this.messageUtils.getMessage("pointback.message.cant_searching_outer_node"));
                throw new ReturnpException(res);
            }
            
            /*
             * 소비자 포인트 적립 
             * */
            if (outerTarget.getMemberNo() != null) {
                increasePoint(
                    transaction,
                    outerTarget.getMemberNo(), 
                    outerTarget.getMemberNo(), 
                    AppConstants.NodeType.MEMBER, 
                    "member",  
                    policy.getCustomerComm()
                ); 
            }
            
            /* 
             * 소비자의 1대 추천인 포인트 적립
             * */
            if (outerTarget.getFirstRecommenderMemberNo() != null) {
                increasePoint(
                    transaction,
                    outerTarget.getFirstRecommenderMemberNo(), 
                    outerTarget.getFirstRecommenderMemberNo(), 
                    AppConstants.NodeType.RECOMMENDER, 
                    "recommender",  
                    policy.getCustomerRecCom()
                ); 
            }else {
                if (defaultSoleDist != null) {
                        increasePoint(
                        transaction,
                        defaultSoleDist.getMemberNo(), 
                        defaultSoleDist.getSoleDistNo(), 
                        AppConstants.NodeType.SOLE_DIST, 
                        "sole_dist",  
                        policy.getCustomerRecCom()
                    ); 
                }
            }
            
            /* 
             * 소비자의 2대 추천인 포인트 적립
             * */
            if (outerTarget.getSecondRecommenderMemberNo() != null) {
                increasePoint(
                    transaction, 
                    outerTarget.getSecondRecommenderMemberNo(), 
                    outerTarget.getSecondRecommenderMemberNo(), 
                    AppConstants.NodeType.RECOMMENDER, "recommender",  
                    policy.getCustomerRecManagerComm()
                ); 
            }else {
                if (defaultSoleDist != null) {
                    increasePoint(
                        transaction,
                        defaultSoleDist.getMemberNo(), 
                        defaultSoleDist.getSoleDistNo(), 
                        AppConstants.NodeType.SOLE_DIST, 
                        "sole_dist",  
                        policy.getCustomerRecManagerComm()
                    ); 
                }
            }
            
            /* 
             * Inner Node 적립(협력업체, 협력업체 추천인, 추천인의 영업 관리자, 대리점, 지사 총판)
             * */
            
            AffiliateTidCommand atidCommand = new AffiliateTidCommand();
            atidCommand.setTid(transaction.getAffiliateSerial());
            ArrayList<AffiliateTidCommand> atidList = this.pointBackMapper.selectAffilaiteTidCommands(atidCommand);
            Affiliate targetAffiliate = this.affiliateMapper.selectByPrimaryKey(atidList.get(0).getAffiliateNo());
            
            InnerPointBackTarget innerTarget = this.pointBackTargetService.findInnerPointBackTarget(transaction.getAffiliateSerial());
            float affiliateComm =  targetAffiliate.getAffiliateComm() > 0 ?  targetAffiliate.getAffiliateComm()  : policy.getAffiliateComm();
            
            /*
             * 가맹점 포인트 적립
             * */
            if (innerTarget.getAffiliateNo() != null) {
                increasePoint(
                    transaction, 
                    innerTarget.getAffiliateMemberNo(),
                    innerTarget.getAffiliateNo(), 
                    AppConstants.NodeType.AFFILIATE, "affiliate", 
                    affiliateComm
                ); 
            }
            
            /*
             * 가맹점의 1대 추천인 포인트 적립
             * */
            if (innerTarget.getAffiliateFirstRecommenderMemberNo() != null) {
                increasePoint(
                    transaction, 
                    innerTarget.getAffiliateFirstRecommenderMemberNo(),
                    innerTarget.getAffiliateFirstRecommenderMemberNo(),
                    AppConstants.NodeType.RECOMMENDER, 
                    "recommender",
                    policy.getAffiliateRecComm()
                ); 
            }else {
                if (defaultSoleDist != null) {
                    increasePoint(
                        transaction,
                        defaultSoleDist.getMemberNo(), 
                        defaultSoleDist.getSoleDistNo(), 
                        AppConstants.NodeType.SOLE_DIST, 
                        "sole_dist",  
                        policy.getAffiliateRecComm()
                    ); 
                }
            }
            
            /*
             * 가맹점의 2대 추천인 포인트 적립
             * */
            if (innerTarget.getAffiliateSecondRecommenderMemberNo() != null) {
                increasePoint(
                    transaction, 
                    innerTarget.getAffiliateSecondRecommenderMemberNo(), 
                    innerTarget.getAffiliateSecondRecommenderMemberNo(),
                    AppConstants.NodeType.RECOMMENDER, "recommender", 
                    policy.getAffiliateRecManagerComm()
                    ); 
            }else {
                if (defaultSoleDist != null) {
                    increasePoint(
                        transaction,
                        defaultSoleDist.getMemberNo(), 
                        defaultSoleDist.getSoleDistNo(), 
                        AppConstants.NodeType.SOLE_DIST, 
                        "sole_dist",  
                        policy.getAffiliateRecManagerComm()
                    ); 
                }
            }
            
            /*
             * 대리점 포인트 적립
             * */
            if (innerTarget.getAgencyNo() != null) {
                increasePoint(
                    transaction, 
                    innerTarget.getAgencyMemberNo(), 
                    innerTarget.getAgencyNo(), 
                    AppConstants.NodeType.AGENCY, 
                    "agency",  
                    policy.getAgancyComm()
                    ); 
            }else {
                if (defaultSoleDist != null) {
                    increasePoint(
                        transaction,
                        defaultSoleDist.getMemberNo(), 
                        defaultSoleDist.getSoleDistNo(), 
                        AppConstants.NodeType.SOLE_DIST, 
                        "sole_dist",  
                        policy.getAffiliateRecManagerComm()
                    ); 
                }
            }
            
            /*
             * 대리점의 1대 추천인 포인트 지급
             * */
            if (innerTarget.getAgencyFirstRecommenderMemberNo() != null) {
                increasePoint(
                    transaction, 
                    innerTarget.getAgencyFirstRecommenderMemberNo(), 
                    innerTarget.getAgencyFirstRecommenderMemberNo(), 
                    AppConstants.NodeType.RECOMMENDER, 
                    "recommender", 
                    policy.getAgancyRecComm()
                    ); 
            }else {
                if (defaultSoleDist != null) {
                    increasePoint(
                        transaction,
                        defaultSoleDist.getMemberNo(), 
                        defaultSoleDist.getSoleDistNo(), 
                        AppConstants.NodeType.SOLE_DIST, 
                        "sole_dist",  
                        policy.getAgancyRecComm()
                    ); 
                }
            }
            
            /*
             * 대리점의 2대 추천인 포인트 지급
             * */
            if (innerTarget.getAgencySecondRecommenderMemberNo() != null) {
                increasePoint(
                    transaction,
                    innerTarget.getAgencySecondRecommenderMemberNo(),
                    innerTarget.getAgencySecondRecommenderMemberNo(),
                    AppConstants.NodeType.RECOMMENDER,
                    "recommender", 
                    policy.getAgancyRecManagerComm()
                    );
            }else {
                if (defaultSoleDist != null) {
                    increasePoint(
                        transaction,
                        defaultSoleDist.getMemberNo(), 
                        defaultSoleDist.getSoleDistNo(), 
                        AppConstants.NodeType.SOLE_DIST, 
                        "sole_dist",  
                        policy.getAgancyRecManagerComm()
                    );
                }
            }
            
            /*
             * 지사 포인트 적립 
             * */
            if (innerTarget.getBranchNo() != null) {
                increasePoint(
                    transaction,
                    innerTarget.getBranchMemberNo(),
                    innerTarget.getBranchNo(),
                    AppConstants.NodeType.BRANCH, 
                    "branch",  
                    policy.getBranchComm()
                );
            }else {
                if (defaultSoleDist != null) {
                    increasePoint(
                        transaction,
                        defaultSoleDist.getMemberNo(), 
                        defaultSoleDist.getSoleDistNo(), 
                        AppConstants.NodeType.SOLE_DIST, 
                        "sole_dist",  
                        policy.getAffiliateRecManagerComm()
                    ); 
                }
            }
            
            /*
             * 지사1의 1대 추천인 포인트 적립
             * */
            if (innerTarget.getBranchFirstRecommenderMemberNo() != null) {
                increasePoint(
                    transaction, 
                    innerTarget.getBranchFirstRecommenderMemberNo()
                    , innerTarget.getBranchFirstRecommenderMemberNo(), 
                    AppConstants.NodeType.RECOMMENDER, 
                    "recommender", 
                    policy.getBranchRecComm()
                );
            }else {
                if (defaultSoleDist != null) {
                    increasePoint(
                        transaction,
                        defaultSoleDist.getMemberNo(), 
                        defaultSoleDist.getSoleDistNo(), 
                        AppConstants.NodeType.SOLE_DIST, 
                        "sole_dist",  
                        policy.getBranchRecComm()
                    );
                }
            }
            
            /*
             * 지사의 2대 추천인 포인트 적립
             * */
            if (innerTarget.getBranchSecondRecommenderMemberNo() != null) {
                increasePoint(
                    transaction, 
                    innerTarget.getBranchSecondRecommenderMemberNo(), 
                    innerTarget.getBranchSecondRecommenderMemberNo(),
                    AppConstants.NodeType.RECOMMENDER, 
                    "recommender",  
                    policy.getBranchRecManagerComm()
                );
                
            }else {
                if (defaultSoleDist != null) {
                    increasePoint(
                        transaction,
                        defaultSoleDist.getMemberNo(), 
                        defaultSoleDist.getSoleDistNo(), 
                        AppConstants.NodeType.SOLE_DIST, 
                        "sole_dist",  
                        policy.getBranchRecManagerComm()
                    );
                }
            }
            
            /*
             * 총판 포인트 적립
             * */
            if (innerTarget.getSoleDistNo() != null) {
                increasePoint(
                    transaction,
                    innerTarget.getSoleDistMemberNo(), 
                    innerTarget.getSoleDistNo(), 
                    AppConstants.NodeType.SOLE_DIST, "sole_dist", 
                    policy.getSoleDistComm()
                );
            }else {
                if (defaultSoleDist != null) {
                    increasePoint(
                        transaction,
                        defaultSoleDist.getMemberNo(), 
                        defaultSoleDist.getSoleDistNo(), 
                        AppConstants.NodeType.SOLE_DIST, 
                        "sole_dist",  
                        policy.getAffiliateRecManagerComm()
                    ); 
                }
            }
            
            transaction.setPointBackStatus(AppConstants.AccumulateStatus.POINTBACK_COMPLETE  );
            this.paymentTransactionMapper.updateByPrimaryKey(transaction);
    
        }catch(ReturnpException e) {
            throw e;
        }catch(Exception e2) {
            e2.printStackTrace();
            ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR,"1000", this.messageUtils.getMessage("pointback.message.inner_server_error"));
            throw new ReturnpException(res);
        }
		
	}

	@Override
	public PaymentTransaction createPaymentTransaction(DataMap dataMap) throws ReturnpException {
		ReturnpBaseResponse res = new ReturnpBaseResponse();
        PaymentTransaction pt = null;
        try {
            Member mem = new Member();
            mem.setMemberEmail(dataMap.getStr("memberEmail"));
            ArrayList<Member> members = this.pointBackMapper.findMembers(mem);
            mem = members.get(0);
            
            AffiliateTidCommand atidCommand = null;
            ArrayList<AffiliateTidCommand> atidList = null;
            Affiliate affiliate = null;
            Member affiliateMember= null;
            ArrayList<Member> affiliateMembers = null;
            
            if (dataMap.get("payment_router_type").equals(AppConstants.PaymentRouterType.VAN) || 
            		dataMap.get("payment_router_type").equals(AppConstants.PaymentRouterType.ADMIN)  || 
            		dataMap.get("payment_router_type").equals(AppConstants.PaymentRouterType.API)) {
            	atidCommand = new AffiliateTidCommand();
            	atidCommand.setTid(dataMap.getStr("af_id"));
            	atidList = this.pointBackMapper.selectAffilaiteTidCommands(atidCommand);
            	affiliate = this.affiliateMapper.selectByPrimaryKey(atidList.get(0).getAffiliateNo());
            	
            }else if(dataMap.get("payment_router_type").equals(AppConstants.PaymentRouterType.PG)) {
            	affiliateMember= new Member();
            	affiliateMember.setMemberEmail(dataMap.getStr("affiliateEmail"));
            	affiliateMembers = this.pointBackMapper.findMembers(affiliateMember);
    			
    			affiliate= new Affiliate();
    			affiliate.setMemberNo(affiliateMembers.get(0).getMemberNo());
    			affiliate =  this.pointBackMapper.findAffiliates(affiliate).get(0);
            }
            
            pt = new PaymentTransaction();
            pt.setMemberNo(mem.getMemberNo());
            pt.setMemberName(mem.getMemberName());
            pt.setMemberEmail(mem.getMemberEmail());
            pt.setNodeNo(mem.getMemberNo());
            pt.setNodeType("1");
            pt.setNodeEmail(mem.getMemberEmail());
            pt.setNodeName(mem.getMemberName());;
            pt.setMemberPhone(mem.getMemberPhone());
            pt.setAffiliateNo(affiliate.getAffiliateNo());
            //pt.setAffiliateSerial(affiliate.getAffiliateSerial());
            pt.setAffiliateSerial(dataMap.getStr("af_id"));
            //paymentTransaction.setOrgPaymentData(BASE64Util.decodeString(qrOrg));
            pt.setPaymentApprovalNumber(dataMap.getStr("pan"));
            Date date = new Date();
            pt.setCreateTime(date);
            pt.setUpdateTime(date);
            /* 
             * QR 코드에서 승인 상태는 0 : 승인 완료 1 : 승인 취소임,  0, 1 아닐때 에러 
             */
            String aps  = dataMap.getStr("pas").equals("0") ? 
                AppConstants.PaymentApprovalStatus.PAYMENT_APPROVAL_OK : 
                (dataMap.getStr("pas").equals("1") ? AppConstants.PaymentApprovalStatus.PAYMENT_APPROVAL_CANCEL : AppConstants.PaymentApprovalStatus.PAYMENT_APPROVAL_ERROR) ;
            pt.setPaymentApprovalStatus(aps);
            
            /*승인 취소 인 경우 금액을 마이너스 처리하여 insert*/
            int amount = dataMap.getStr("pas").trim().equals("0") ? dataMap.getInt("pam") :  (dataMap.getStr("pas").trim().equals("1") ?  dataMap.getInt("pam") * -1 : 0 );
            pt.setPaymentApprovalAmount(amount);
            /*
             * 적립 처리 작업인 경우 : 적립 진행중으로  표시
             * 적립 취소 처리 작업인 경우 : 적립 처리 취소중 표시
             * 이외의 경우 요청 자체가 에러임
             */
            
            String pbs = dataMap.getStr("pas").equals("0") ? 
                AppConstants.AccumulateStatus.POINTBACK_PROGRESS : 
                (dataMap.getStr("pas").equals("1") ? AppConstants.AccumulateStatus.POINTBACK_CANCEL_PROGRESS : AppConstants.AccumulateStatus.POINTBACK_REQ_ERROR) ;
            pt.setPointBackStatus(pbs);
            
            pt.setPaymentTransactionType(dataMap.getStr("payment_transaction_type"));
            pt.setPaymentApprovalDateTime((Date)dataMap.get("pat"));
            pt.setRegAdminNo(0);
            this.paymentTransactionMapper.insert(pt);
            
            /* 
             * payment_transaction_rotuer  테이블 기록
             */
			PaymentRouter router = new PaymentRouter();
			router.setPaymentRouterName(dataMap.getStr("payment_router_name"));
			router.setPaymentRouterType(dataMap.getStr("payment_router_type"));
			ArrayList<PaymentRouter> routers = this.pointBackMapper.selectPaymentRouters(router);
			if (routers.size() != 1) {
				 ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "7681", this.messageUtils.getMessage("pointback.message.unregister_router_request"));
					throw new ReturnpException(res);
			}
			
			PaymentTransactionRouter ptr= new PaymentTransactionRouter();
			ptr.setPaymentTransactionNo(pt.getPaymentTransactionNo());
			ptr.setPaymentRouterNo(routers.get(0).getPaymentRouterNo());
			
			this.paymentTransactionRouterMapper.insert(ptr);
			
            return pt;
            
        }catch(Exception e) {
            e.printStackTrace();
            ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR,"1000", this.messageUtils.getMessage("pointback.message.inner_server_error"));
            throw new ReturnpException(res);
        }
	}

	@Override
	public void increasePoint(PaymentTransaction transaction, int memberNo, int nodeNo, String nodeType,
			String nodeTypeName, float accRate) throws ReturnpException {
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
	            point.setPointAmount(point.getPointAmount() + (transaction.getPaymentApprovalAmount() * accRate));
	            this.greenPointMapper.updateByPrimaryKey(point);

	            /* G Point 적립 내역 저장*/
	            PaymentPointbackRecord pointBackRecord = new PaymentPointbackRecord();
	            pointBackRecord.setPaymentTransactionNo(transaction.getPaymentTransactionNo());
	            pointBackRecord.setMemberNo(memberNo);
	            pointBackRecord.setNodeNo(nodeNo);
	            pointBackRecord.setNodeType(nodeType);
	            pointBackRecord.setAccRate(accRate);
	            pointBackRecord.setPointbackAmount(transaction.getPaymentApprovalAmount() * accRate);
	            paymentPointbackRecordMapper.insert(pointBackRecord);
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

	@Override
	public Member validateMemberAndGet(String memberEmail, String phoneNumber, String phoneNumberCountry)
			throws ReturnpException {
		ReturnpBaseResponse res = new ReturnpBaseResponse();
        try {
            /*존재 하는 회원 인지 검사*/
            Member member = new Member();;
            member.setMemberEmail(memberEmail);
            ArrayList<Member> members = this.pointBackMapper.findMembers(member);
            
            if (members.size() !=1 || members == null) {
                ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "601", 
                        this.messageUtils.getMessage("pointback.message.not_argu_member", new Object[] {memberEmail}));
                throw new ReturnpException(res);
            }
            
            /*member = members.get(0);*/
            
            /*
             * 기기의 전화번호와 해당 이메일 계정의 전화번호가 같은 비교
             */
            if (!members.get(0).getMemberPhone().equals(phoneNumber) && 
                    !members.get(0).getMemberPhone().equals(phoneNumberCountry)) {
                ResponseUtil.setResponse(res,ResponseUtil.RESPONSE_OK,  "602", this.messageUtils.getMessage("pointback.message.invalid_argu_phonenumber",
                    new Object[] { phoneNumber, phoneNumberCountry, members.get(0).getMemberPhone()}));
                throw new ReturnpException(res);
            }
        return member;
        } catch (ReturnpException e1) {
            e1.printStackTrace();
            throw e1;
        } catch (Exception e2) {
            e2.printStackTrace();
            throw e2;
        }
	}

	@Override
	public Affiliate validateAffiliateAuthAndGet(String paymentRouterType, String paymentRouterName, String afId)
			throws ReturnpException {
		 ReturnpBaseResponse res = new ReturnpBaseResponse();
	        try {
	            /* 하나의 가맹점에서 다수의 TID 를 등록할 수 있게 변경됨으로써, 인증 로직을 다음과 같이 수정함 */
	            AffiliateTidCommand atidCommand = new AffiliateTidCommand();
	            atidCommand.setTid(afId);
	            
	            ArrayList<AffiliateTidCommand> atidList = this.pointBackMapper.selectAffilaiteTidCommands(atidCommand);
	            if (atidList == null || atidList.size() < 1) {
	                ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "603", 
	                        this.messageUtils.getMessage("pointback.message.invalide_tid", new Object[] {afId}));
	                throw new ReturnpException(res);
	            }
	            
	            AffiliateCommand affiliateCommand= new AffiliateCommand();
	            affiliateCommand.setAffiliateSerial(afId);
	            affiliateCommand.setPaymentRouterName(paymentRouterName);
	            affiliateCommand.setPaymentRouterType(paymentRouterType);
	            ArrayList<AffiliateCommand> affiliateCommandList = this.pointBackMapper.findAffiliateCommands(affiliateCommand);
	            
	            if (affiliateCommandList.size() != 1) {
	                ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "609", 
	                        this.messageUtils.getMessage("pointback.message.not_connected_tid_affiliate"));
	                throw new ReturnpException(res);
	            }
	            
	            Affiliate affiliate = this.affiliateMapper.selectByPrimaryKey(affiliateCommandList.get(0).getAffiliateNo());
	            
	            if (affiliate == null) {
	                ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "698", 
	                        this.messageUtils.getMessage("pointback.message.not_connected_tid_affiliate"));
	                throw new ReturnpException(res);
	            }
	            
	            if (affiliate.getGreenPointAccStatus().trim().equals("N")) {
	            	 ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "781", 
		                        this.messageUtils.getMessage("pointback.message.temp_stop_green_accumulate"));
		                throw new ReturnpException(res);
	            }
	            if (!affiliate.getAffiliateType().contains(AppConstants.AffiliateType.COMMON_AFFILIATE)  && 
	                    !affiliate.getAffiliateType().contains(AppConstants.AffiliateType.ONLINE_AFFILIATE)) {
	                ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "619", 
	                        this.messageUtils.getMessage("pointback.message.acc_ok_but_not_accable_affiliate"));
	                throw new ReturnpException(res);
	            }
	            return affiliate;
	        } catch (ReturnpException e1) {
	            e1.printStackTrace();
	            throw e1;
	        } catch (Exception e2) {
	            e2.printStackTrace();
	            throw e2;
	        }
	}
	
	@Override
	public void validateAccumulateRequest(DataMap dataMap) throws ReturnpException {
        ReturnpBaseResponse res =  new ReturnpBaseResponse();
        try {
              
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            /*
             * PaymentTransaction paymentTransaction = new PaymentTransaction();
                ArrayList<PaymentTransaction> paymentTransactions = this.pointBackMapper.findPaymentTransactions(paymentTransaction);
             */

            /*
             * 결제 번호 및 승인 일시로 조회하여 이미 등록된 결제 정보가 있는지 조회
             * */
            
            PaymentTransactionCommand ptCommand = new PaymentTransactionCommand();
            ptCommand.setPaymentApprovalNumber(dataMap.getStr("pan"));
            ptCommand.setPaymentRouterType(dataMap.getStr("payment_router_type"));
            ptCommand.setPaymentRouterName(dataMap.getStr("payment_router_name"));
            ptCommand.setPaymentApprovalDateTime((Date)dataMap.get("pat"));
            
            ArrayList<PaymentTransactionCommand> paymentTransactions = this.pointBackMapper.findPaymentTransactionCommands(ptCommand);

            logger.info("------------------------------------- 적립 결제 정보---------------------------------------");
            System.out.println("Payment Router Type  : "  + dataMap.getStr("payment_router_type"));
            System.out.println("Payment Router name  : "  + dataMap.getStr("payment_router_name"));
            System.out.println("Approval Payment Number : "  + dataMap.getStr("pan"));
            System.out.println("Approval Payment Date Time : "  + df.format((Date)dataMap.get("pat")));
            
            if (paymentTransactions.size() >  0  ) {
                logger.info("기 등록된 결제의 승인 일시 : "  + df.format(paymentTransactions.get(0).getPaymentApprovalDateTime())); 
            }
            
            logger.info("----------------------------------------------------------------------------------------------");
            
            PaymentTransaction paymentTransaction = new PaymentTransaction();
            if (paymentTransactions.size() >  1  ) {
                ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "604", this.messageUtils.getMessage("pointback.message.already_accumulate_or_canceled"));
                    throw new ReturnpException(res);
            }
          
            /* 
             * 결제 번호가 같을 경우 바로 에러 처리를 해주어야 하나, 상태별 에러 메시지를 뿌려주기 위해 
             * 아래의 처리를 진행함 
             * */
            if (paymentTransactions.size() == 1) {
                paymentTransaction = paymentTransactions.get(0);
                 //부적절한 요청 - 현재 적립 처리중인 내역에 대한 중복 적립 요청
                 if (paymentTransaction.getPaymentApprovalStatus().equals(AppConstants.PaymentApprovalStatus.PAYMENT_APPROVAL_OK)) {
                     if (paymentTransaction.getPointBackStatus().equals(AppConstants.AccumulateStatus.POINTBACK_START) || 
                             paymentTransaction.getPointBackStatus().equals(AppConstants.AccumulateStatus.POINTBACK_PROGRESS)) {
                         ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "605", this.messageUtils.getMessage("pointback.message.already_accumulating_req"));
                         throw new ReturnpException(res);
                     }
                    
                      //부적절한 요청 - 적립 처리가 완료된 내역에 대한 중복 적립 요청
                    if (paymentTransaction.getPointBackStatus().equals(AppConstants.AccumulateStatus.POINTBACK_COMPLETE) ) {
                        ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "606", this.messageUtils.getMessage("pointback.message.already_complete_req"));
                        throw new ReturnpException(res);
                    }
                    
                     //부적절한 요청 - 적립 처리 에러 내역에 대한 중복 적립 요청
                    if (paymentTransaction.getPointBackStatus().equals(AppConstants.AccumulateStatus.POINTBACK_STOP) ) {
                        ResponseUtil.setResponse(res,ResponseUtil.RESPONSE_OK,  "607", this.messageUtils.getMessage("pointback.message.error_accumulate"));
                        throw new ReturnpException(res);
                    }
                }else if (paymentTransaction.getPaymentApprovalStatus().equals(AppConstants.PaymentApprovalStatus.PAYMENT_APPROVAL_CANCEL)){
                     //이미 결제 취소된 중이거나 결제 취소가 완료, 혹은 결제 취소 에러가 발생한 내역에 대한 적립 요청인 경우 - 부적절한 적립 요청  
                    ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "608", this.messageUtils.getMessage("pointback.message.error_acc_req_to_already_canceled_payement"));
                    throw new ReturnpException(res);
                }else if (paymentTransaction.getPaymentApprovalStatus().equals(AppConstants.PaymentApprovalStatus.PAYMENT_APPROVAL_ERROR)){
                    ResponseUtil.setResponse(res,ResponseUtil.RESPONSE_OK,  "609", this.messageUtils.getMessage("pointback.message.error_paymenttransaction"));
                    throw new ReturnpException(res);
                }else {
                     ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "610", this.messageUtils.getMessage("pointback.message.invalid_req"));
                        throw new ReturnpException(res);
                }
             }else {
            	  /*1회 적립 한도 초과 여부 검사 */
             	Policy policy = new Policy();
                ArrayList<Policy> policies = this.pointBackMapper.findPolicies(policy);
                policy = policies.get(policies.size() -1 );
                 
                 /*1일 적립 한도 초과시 에러 처리 */
                 if (dataMap.getInt("pam") > policy.getMaxGpointAccLImit()) {
                	 ResponseUtil.setResponse(res,ResponseUtil.RESPONSE_OK,  "787", this.messageUtils.getMessage("pointback.message.exceed_once_acc_limit",
                             new Object[] { policy.getMaxGpointAccLImit()}));
                       throw new ReturnpException(res);
                 }
             }
        } catch(ReturnpException ee) {
            ee.printStackTrace();
            throw ee;
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
	}

	@Override
	public PaymentTransaction validateCancelRequest(DataMap dataMap) throws ReturnpException {
		ReturnpBaseResponse res =  new ReturnpBaseResponse();
        try {
            SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            
            /*
             * 결제 번호 및 승인 일시로 조회하여 등록된 결제 정보가 있는지 조회
             * */
            
            PaymentTransactionCommand ptCommand = new PaymentTransactionCommand();
            ptCommand.setPaymentApprovalNumber(dataMap.getStr("pan"));
            ptCommand.setPaymentRouterType(dataMap.getStr("payment_router_type"));
            ptCommand.setPaymentRouterName(dataMap.getStr("payment_router_name"));
            
            /*
             * 결제 취소에 의한 적립의 경우 결제 승인일자와 결제 취소 일자가 틀리기 때문에
             * 결제 취소 일자는 파라메터로 세팅을 하면 안되
             * ptCommand.setPaymentApprovalDateTime((Date)dataMap.get("pat"));
             */
            
            ArrayList<PaymentTransactionCommand> paymentTransactions = this.pointBackMapper.findPaymentTransactionCommands(ptCommand);
            
            System.out.println("------------------------------------- 적립 취소 결제 정보---------------------------------------");
            System.out.println("Payment Router Type  : "  + dataMap.getStr("payment_router_type"));
            System.out.println("Payment Router name  : "  + dataMap.getStr("payment_router_name"));
            System.out.println("Approval Payment Number : "  + dataMap.getStr("pan"));
            
            if (paymentTransactions.size() >  0  ) {
                logger.info("기 등록된 결제의 승인 일시 : "  + df.format(paymentTransactions.get(0).getPaymentApprovalDateTime())); 
            }
            
            System.out.println("---------------------------------------------------------------------------------------------------");
            
            
            /* 존재하지 않는 내역에 대한 취소 요청  */ 
            if (paymentTransactions.size() == 0  ) {
                 ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "611", this.messageUtils.getMessage("pointback.message.wrong_req_not_existed_paynumber"));
                throw new ReturnpException(res);
            }
            
            /* 디비 내역 무결성 깨짐, 관리자 문의*/   
            PaymentTransaction paymentTransaction = new PaymentTransaction();
            paymentTransaction = paymentTransactions.get(0);
            
            /*결체 취소된 내역은 다시 취소할 수 없음*/
            if (paymentTransaction.getPaymentApprovalStatus().equals(AppConstants.PaymentApprovalStatus.PAYMENT_APPROVAL_CANCEL)){ 
                 /* 부적절한 취소 요청 - 현재 적립 취소중인 내역  */
                 if (paymentTransaction.getPointBackStatus().equals(AppConstants.AccumulateStatus.POINTBACK_CANCEL_START) || 
                         paymentTransaction.getPointBackStatus().equals(AppConstants.AccumulateStatus.POINTBACK_CANCEL_PROGRESS)) {
                    ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "613", this.messageUtils.getMessage("pointback.message.already_canceling_req"));
                    throw new ReturnpException(res);
                }
                
                 /* 부적절한 취소 요청 - 현재 적립 취소가 완료된 내역 */
                else if (paymentTransaction.getPointBackStatus().equals(AppConstants.AccumulateStatus.POINTBACK_CANCEL_COMPLETE) ) {
                    ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "614", this.messageUtils.getMessage("pointback.message.already_canceled_req"));
                    throw new ReturnpException(res);
                }
                
                 /* 부적절한 취소 요청 - 현재 적립 취소 에러가 발생한 내역 */
                else if (paymentTransaction.getPointBackStatus().equals(AppConstants.AccumulateStatus.POINTBACK_CANCEL_STOP) ) {
                    ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "615", this.messageUtils.getMessage("pointback.message.error_acc_canceling"));
                    throw new ReturnpException(res);
                }else {
                     ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "616", this.messageUtils.getMessage("pointback.message.already_cancel_accumulate_or_error"));
                        throw new ReturnpException(res);
                }
            }
            
            /*
             * 결제 승인 된 내역은 적립이 완료된 경우에만 취소가 가능
             * */
            else if (paymentTransaction.getPaymentApprovalStatus().equals(AppConstants.PaymentApprovalStatus.PAYMENT_APPROVAL_OK)){
                if (paymentTransaction.getPointBackStatus().equals(AppConstants.AccumulateStatus.POINTBACK_START) || 
                        paymentTransaction.getPointBackStatus().equals(AppConstants.AccumulateStatus.POINTBACK_PROGRESS)) {
                    ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "617", this.messageUtils.getMessage("pointback.message.now_accumulating_wait"));
                    throw new ReturnpException(res);
                }
                
                if (paymentTransaction.getPointBackStatus().equals(AppConstants.AccumulateStatus.POINTBACK_STOP)) {
                    ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "618", this.messageUtils.getMessage("pointback.message.cancle_for_error_acc"));
                    throw new ReturnpException(res);
                }
            }else {
                 ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "619", this.messageUtils.getMessage("pointback.message.invalid_req"));
                throw new ReturnpException(res);
            }
            return paymentTransaction;
        } catch(ReturnpException ee) {
            ee.printStackTrace();
            throw ee;
        } catch(Exception e) {
            e.printStackTrace();
            throw e;
        }
	}

	@Override
	public DataMap convertPaymentTransactionToDataMap(PaymentTransaction pt) {
	    DataMap dataMap = new DataMap();
        dataMap.put("qr_org", pt.getOrgPaymentData());
        dataMap.put("pam",pt.getPaymentApprovalAmount());
        
        String pas  = null;
        if (pt.getPaymentApprovalStatus() == AppConstants.PaymentApprovalStatus.PAYMENT_APPROVAL_OK ) {
            pas = "0";
        }else if (pt.getPaymentApprovalStatus() == AppConstants.PaymentApprovalStatus.PAYMENT_APPROVAL_CANCEL ) {
            pas = "1";
        }else {
            pas = "3";
        }
            
        dataMap.put("pas", pas);
        
        dataMap.put("pat", (pt.getPaymentApprovalDateTime() == null ? new Date(): pt.getPaymentApprovalDateTime()));
        dataMap.put("pan", pt.getPaymentApprovalNumber());
        dataMap.put("af_id", pt.getAffiliateSerial());
        dataMap.put("phoneNumber", pt.getMemberPhone());

        dataMap.put("memberEmail", pt.getMemberEmail());
        dataMap.put("payment_transaction_type", AppConstants.PaymentTransactionType.MANUAL);
        return dataMap;
	}

	@Override
	public ReturnpBaseResponse gpointPaymentApporval(ApiRequest apiRequest)  {
		 ReturnpBaseResponse res = new ReturnpBaseResponse();
		 try {
			 if (apiRequest.getPaymentApprovalAmount() != (apiRequest.getRealPaymentAmount() + apiRequest.getGpointPaymentAmount())){
				 ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "700",
	                        this.messageUtils.getMessage("pointback.message.invalid_payment_sum"));
	                throw new ReturnpException(res);
			 }
			 
			 Member member = new Member();
			 member.setMemberEmail(apiRequest.getMemberEmail());
	         ArrayList<Member> members = this.pointBackMapper.findMembers(member);
	         member = members.get(0);
			 
	         AffiliateTidCommand atidCommand = null;
	         ArrayList<AffiliateTidCommand> atidList = null;
	         Affiliate affiliate = null;
	         
	         atidCommand = new AffiliateTidCommand();
        	 atidCommand.setTid(apiRequest.getAfId());
        	 atidList = this.pointBackMapper.selectAffilaiteTidCommands(atidCommand);
        	 affiliate = this.affiliateMapper.selectByPrimaryKey(atidList.get(0).getAffiliateNo());
	         
        	 /* 보유 포인트와 결제 포인트의 비교*/
        	 GreenPoint gPoint = new GreenPoint();
	         gPoint.setMemberNo(member.getMemberNo());
	         gPoint.setNodeType(AppConstants.NodeType.MEMBER);
	         gPoint  = this.pointBackMapper.findGreenPoints(gPoint).get(0);
	         if (apiRequest.getGpointPaymentAmount() > gPoint.getPointAmount() ) {
	        	 ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "701",
	                        this.messageUtils.getMessage("pointback.message.not_enough_for_gpoint_pay"));
	                throw new ReturnpException(res);
	         }
	         
        	 /*G POINT Payment Table insert*/
        	 GpointPayment gpointPayment = new GpointPayment();
        	 gpointPayment.setAffiliateNo(affiliate.getAffiliateNo());
        	 gpointPayment.setAffiliateSerial(affiliate.getAffiliateSerial());
        	 gpointPayment.setAffiliateName(affiliate.getAffiliateName());
        	 gpointPayment.setMemberNo(member.getMemberNo());
        	 gpointPayment.setMemberName(member.getMemberName());
        	 gpointPayment.setMemberEmail(member.getMemberEmail());
        	 gpointPayment.setMemberPhone(member.getMemberPhone());

        	 gpointPayment.setPaymentApprovalAmount(apiRequest.getPaymentApprovalAmount());
        	 gpointPayment.setRealPaymentAmount(apiRequest.getRealPaymentAmount());
        	 gpointPayment.setGpointPaymentAmount(apiRequest.getGpointPaymentAmount());

        	 gpointPayment.setPaymentApprovalNumber(apiRequest.getPaymentApprovalNumber());
        	 gpointPayment.setPaymentMethod(apiRequest.getPaymentMethod());
        	 gpointPayment.setPaymentTransactionType(apiRequest.getPaymentTransactionType());
        	 gpointPayment.setPaymentApprovalDateTime(apiRequest.getPaymentApprovalDateTime());
        	 
        	 String aps  = apiRequest.getPaymentApprovalStatus().equals("0") ? 
                     AppConstants.PaymentApprovalStatus.PAYMENT_APPROVAL_OK : 
                     (apiRequest.getPaymentApprovalStatus().equals("1") ? AppConstants.PaymentApprovalStatus.PAYMENT_APPROVAL_CANCEL : AppConstants.PaymentApprovalStatus.PAYMENT_APPROVAL_ERROR) ;        
        	 gpointPayment.setPaymentApprovalStatus(aps);
        	 
	         this.gpointPaymentMapper.insert(gpointPayment);
	         
	         /*G POINT 삭감 */
	         
	         gPoint.setPointAmount(gPoint.getPointAmount() - apiRequest.getGpointPaymentAmount());
	         this.greenPointMapper.updateByPrimaryKey(gPoint);
	         
	         ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "100", this.messageUtils.getMessage("pointback.message.gpoint_payment_ok"));
	         return res;
		 }catch(ReturnpException e) {
			 e.printStackTrace();
			 res = e.getBaseResponse();
			 return res; 
		 }catch(Exception e) {
			 e.printStackTrace();
			 ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR, "2000", this.messageUtils.getMessage("pointback.message.inner_server_error"));
			 return res; 
		 }
	}

	@Override
	public ReturnpBaseResponse gpointPaymentCancel(ApiRequest apiRequest) throws ReturnpException {
		ReturnpBaseResponse res = new ReturnpBaseResponse();
		try {
			 Member member = new Member();
			 member.setMemberEmail(apiRequest.getMemberEmail());
	         ArrayList<Member> members = this.pointBackMapper.findMembers(member);
	         member = members.get(0);
			 
	         AffiliateTidCommand atidCommand = null;
	         ArrayList<AffiliateTidCommand> atidList = null;
	         Affiliate affiliate = null;
	         
	         atidCommand = new AffiliateTidCommand();
        	 atidCommand.setTid(apiRequest.getAfId());
        	 atidList = this.pointBackMapper.selectAffilaiteTidCommands(atidCommand);
        	 affiliate = this.affiliateMapper.selectByPrimaryKey(atidList.get(0).getAffiliateNo());
	         
        	 /*G POINT Payment Table insert*/
        	 GpointPayment gpointPayment = new GpointPayment();
        	 gpointPayment.setAffiliateNo(affiliate.getAffiliateNo());
        	 gpointPayment.setAffiliateSerial(affiliate.getAffiliateSerial());
        	 gpointPayment.setAffiliateName(affiliate.getAffiliateName());
        	 gpointPayment.setMemberNo(member.getMemberNo());
        	 gpointPayment.setMemberName(member.getMemberName());
        	 gpointPayment.setMemberEmail(member.getMemberEmail());
        	 gpointPayment.setMemberPhone(member.getMemberPhone());
        	 
        	 gpointPayment.setPaymentApprovalAmount(apiRequest.getPaymentApprovalAmount() * -1);
        	 gpointPayment.setRealPaymentAmount(apiRequest.getRealPaymentAmount() * -1);
        	 gpointPayment.setGpointPaymentAmount(apiRequest.getGpointPaymentAmount()* -1);

        	 gpointPayment.setPaymentApprovalNumber(apiRequest.getPaymentApprovalNumber());
        	 gpointPayment.setPaymentMethod(apiRequest.getPaymentMethod());
        	 gpointPayment.setPaymentTransactionType(apiRequest.getPaymentTransactionType());
        	 gpointPayment.setPaymentApprovalDateTime(apiRequest.getPaymentApprovalDateTime());

        	 String aps  = apiRequest.getPaymentApprovalStatus().equals("0") ? 
                     AppConstants.PaymentApprovalStatus.PAYMENT_APPROVAL_OK : 
                     (apiRequest.getPaymentApprovalStatus().equals("1") ? AppConstants.PaymentApprovalStatus.PAYMENT_APPROVAL_CANCEL : AppConstants.PaymentApprovalStatus.PAYMENT_APPROVAL_ERROR) ;        
        	 gpointPayment.setPaymentApprovalStatus(aps);
        	 
	         this.gpointPaymentMapper.insert(gpointPayment);

			/*G POINT 증가 */
			GreenPoint gPoint = new GreenPoint();
			gPoint.setMemberNo(member.getMemberNo());
			gPoint.setNodeType(AppConstants.NodeType.MEMBER);
			gPoint  = this.pointBackMapper.findGreenPoints(gPoint).get(0);

			gPoint.setPointAmount(gPoint.getPointAmount() + apiRequest.getGpointPaymentAmount());
			this.greenPointMapper.updateByPrimaryKey(gPoint);

			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "100", this.messageUtils.getMessage("pointback.message.gpoint_cancel_ok"));
			return res;
		}catch(ReturnpException e) {
			e.printStackTrace();
			res = e.getBaseResponse();
			return res; 
		}catch(Exception e) {
			e.printStackTrace();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR, "2000", this.messageUtils.getMessage("pointback.message.inner_server_error"));
			return res; 
		}
	}

}
