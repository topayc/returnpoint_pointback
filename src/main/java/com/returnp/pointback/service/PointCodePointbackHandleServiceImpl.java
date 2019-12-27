package com.returnp.pointback.service;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.returnp.pointback.common.AppConstants;
import com.returnp.pointback.common.ResponseUtil;
import com.returnp.pointback.common.ReturnpException;
import com.returnp.pointback.dao.mapper.GreenPointMapper;
import com.returnp.pointback.dao.mapper.PointBackMapper;
import com.returnp.pointback.dao.mapper.PointCodeIssueMapper;
import com.returnp.pointback.dao.mapper.PointCodePointbackRecordMapper;
import com.returnp.pointback.dao.mapper.PointCodeTransactionMapper;
import com.returnp.pointback.dao.mapper.PointCouponMapper;
import com.returnp.pointback.dao.mapper.PointCouponPointbackRecordMapper;
import com.returnp.pointback.dao.mapper.PointCouponTransactionMapper;
import com.returnp.pointback.dto.command.OuterPointBackTarget;
import com.returnp.pointback.dto.command.PointCodeIssueCommand;
import com.returnp.pointback.dto.response.ReturnpBaseResponse;
import com.returnp.pointback.model.GreenPoint;
import com.returnp.pointback.model.Member;
import com.returnp.pointback.model.PointCodeIssue;
import com.returnp.pointback.model.PointCodePointbackRecord;
import com.returnp.pointback.model.PointCodeTransaction;
import com.returnp.pointback.model.Policy;
import com.returnp.pointback.service.interfaces.PointCouponPointbackHandleService;
import com.returnp.pointback.service.interfaces.PointbackTargetService;
import com.returnp.pointback.service.interfaces.ReturnpTransactionService;
import com.returnp.pointback.web.message.MessageUtils;

@Service
/*@PropertySource("classpath:/messages.properties")*/
public class PointCouponPointbackHandleServiceImpl implements PointCouponPointbackHandleService {
    
	   private Logger logger = Logger.getLogger(PointCouponPointbackHandleServiceImpl.class);
	    
	    @Autowired MessageUtils messageUtils;
	    @Autowired PointBackMapper pointBackMapper;
	    @Autowired PointCouponTransactionMapper pointCouponTransactionMapper;
	    @Autowired PointCodeTransactionMapper pointCodeTransactionMapper;
	    @Autowired PointCodeIssueMapper pointCodeIssueMapper;
	    @Autowired PointCodePointbackRecordMapper pointCodePointbackRecordMapper;
	    @Autowired PointCouponPointbackRecordMapper pointCouponPointbackRecordMapper;
	    @Autowired ReturnpTransactionService  returnpTransactionService;
	    @Autowired PointbackTargetService pointBackTargetService;
	    @Autowired GreenPointMapper greenPointMapper;
	    @Autowired PointCouponMapper pointCouponMapper;;
	    
	    
	    public static class Command {
	        public static class Control{
	            
	        }
	        public static class Request {
	            public static final String  PAYMENT_APPROVAL = "PAYMENT_APPROVAL";
	            public static final String PAYMENT_CANCEL = "PAYMENT_CANCEL";
	        }
	    }
	    
	    ArrayList<String> keys;
	    ArrayList<Integer> pointAccList = new ArrayList<Integer>();
	    @Override
	    public ReturnpBaseResponse accumulate(String memberEmail, String phoneNumber, String phoneNumberCountry, String pointCode) {
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
	            
	            /*포인트 적립권 유효성 판단*/
	            PointCodeIssue pc = new PointCodeIssue();
	            pc.setPointCode(pointCode.trim());
	            ArrayList<PointCodeIssueCommand> pointCodeIssuecommands= this.pointBackMapper.selectPointCodeIssueCommands(pc);
	            if (pointCodeIssuecommands == null || pointCodeIssuecommands.size() != 1 ) {
	                 ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "987", this.messageUtils.getMessage("pointback.invalid_point_coupon"));
	                     throw new ReturnpException(res);
	            }
	            
	            boolean isCodeUsable = true;
	            switch (pointCodeIssuecommands.get(0).getUseStatus()) {
	            case "1": break;
	            case "2": isCodeUsable = false; ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "983", this.messageUtils.getMessage("pointback.use_stop_point_coupon")); break;
	            case "3": isCodeUsable = false; ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "989", this.messageUtils.getMessage("pointback.use_completed_point_coupon")); break;
	            case "4": isCodeUsable = false; ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "981", this.messageUtils.getMessage("pointback.reg_cancel_point_coupon")); break;
	            }
	            
	            if (!isCodeUsable) {
	                throw new ReturnpException(res);
	            }

	            /*포인트 적립 트랜잭션 유효성 판단및 생성*/
	            HashMap<String, Object> dbparam = new HashMap<String, Object>();
	            dbparam.put("pointCode",pointCode);
	            
	            ArrayList<HashMap<String, Object>> pctcs = this.pointBackMapper.selectPointCodeTransactions(dbparam);
	            if ( pctcs.size() > 0 ) {
	                 ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "987", this.messageUtils.getMessage("pointback.already_reg_point_coupon_transaction"));
	                     throw new ReturnpException(res);
	            }
	            
	            PointCodeTransaction pct = new PointCodeTransaction();
	            pct.setMemberNo(members.get(0).getMemberNo());
	            pct.setPointCodeIssueNo(pointCodeIssuecommands.get(0).getPointCodeIssueNo());
	            pct.setPayAmount(pointCodeIssuecommands.get(0).getPayAmount());
	            pct.setPointCode(pointCodeIssuecommands.get(0).getPointCode());
	            pct.setAccPointAmount(pointCodeIssuecommands.get(0).getAccPointAmount());
	            pct.setAccPointRate(pointCodeIssuecommands.get(0).getAccPointRate());
	            pct.setPointBackStatus("1");
	            this.pointCodeTransactionMapper.insert(pct);
	            
	            /* 쿠폰 등록자 및 등록자의 1대만 적립 포인트 만큼 증가 
	             * 증가완료후 포인트 백 상태 변경
	             * 포인트 적립 레코드 생성
	             * */
	            
	            OuterPointBackTarget outerTarget = new OuterPointBackTarget();
	            outerTarget.setMemberNo(members.get(0).getMemberNo());
	            outerTarget = this.pointBackTargetService.findOuterPointBackTarget(outerTarget);
	            
	            Policy policy = new Policy();
	            ArrayList<Policy> policies = this.pointBackMapper.findPolicies(policy);
	            policy = policies.get(policies.size() -1 );
	            
	            /* 쿠폰 등록자 포인트 적립 **/
	            if (outerTarget.getMemberNo() != null) {
	                increasePoint(
	                    pct.getPointCodeTransactionNo(),
	                    pointCodeIssuecommands.get(0).getAccPointRate(),
	                    pointCodeIssuecommands.get(0).getAccPointAmount(),
	                    outerTarget.getMemberNo(), 
	                    outerTarget.getMemberNo(), 
	                    AppConstants.NodeType.MEMBER, 
	                    "member"  
	                ); 
	            }
	            
	            /*2 인경우 1대 만 적립*/
	            if (pointCodeIssuecommands.get(0).getAccTargetRange().equals("2")) {
	                if (outerTarget.getFirstRecommenderMemberNo() != null) {
	                      increasePoint(
	                        pct.getPointCodeTransactionNo(),
	                        policy.getCustomerRecCom(),
	                        pointCodeIssuecommands.get(0).getAccPointAmount() * policy.getCustomerRecCom(),
	                        outerTarget.getFirstRecommenderMemberNo(), 
	                        outerTarget.getFirstRecommenderMemberNo(), 
	                        AppConstants.NodeType.RECOMMENDER, 
	                        "recommender"
	                    ); 
	                }
	            }
	            
	            /*3인 경우 1대 , 2대 모두 적립*/
	            if (pointCodeIssuecommands.get(0).getAccTargetRange().equals("3")) {
	                if (outerTarget.getFirstRecommenderMemberNo() != null) {
	                    increasePoint(
	                        pct.getPointCodeTransactionNo(),
	                        policy.getCustomerRecCom(),
	                        pointCodeIssuecommands.get(0).getAccPointAmount() * policy.getCustomerRecCom(),
	                        outerTarget.getFirstRecommenderMemberNo(), 
	                        outerTarget.getFirstRecommenderMemberNo(), 
	                        AppConstants.NodeType.RECOMMENDER, 
	                       "recommender"
	                  ); 
	              }
	                
	                if (outerTarget.getSecondRecommenderMemberNo() != null) {
	                    increasePoint(
	                        pct.getPointCodeTransactionNo(),
	                        policy.getCustomerRecManagerComm(),
	                        pointCodeIssuecommands.get(0).getAccPointAmount() * policy.getCustomerRecManagerComm(),
	                        outerTarget.getSecondRecommenderMemberNo(),
	                        outerTarget.getSecondRecommenderMemberNo(), 
	                        AppConstants.NodeType.RECOMMENDER, 
	                        "recommender"
	                  ); 
	              }
	            }
	            
	           /*포인트 쿠폰 트랜잭션의 적립 상태를 적립완료로 번경*/
	            pct.setPointBackStatus("3");
	            this.pointCodeTransactionMapper.updateByPrimaryKeySelective(pct);
	            
	            /*적립된 포인트 쿠폰의 상태를 사용 완료로 변경*/
	            pointCodeIssuecommands.get(0).setUseStatus("3");
	            this.pointCodeIssueMapper.updateByPrimaryKeySelective(pointCodeIssuecommands.get(0));
	            
	            ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "100", this.messageUtils.getMessage("pointback.point_coupon.pointback_accumulate_ok"));
	            return res;
	        }catch(ReturnpException e) {
	            e.printStackTrace();
	            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	            res = e.getBaseResponse();
	            return res;
	        }catch(Exception e) {
	            e.printStackTrace();
	            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	            ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR,"2000", this.messageUtils.getMessage("pointback.message.inner_server_error"));
	            return res;
	        }
	    }
	    
	    @Override
	    public void increasePoint(int pointCouponTransactionNo, float accRate, float accPointAmount, int memberNo, int nodeNo, String nodeType, String nodeTypeName) {
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
	                point.setPointAmount(point.getPointAmount() + accPointAmount);
	                this.greenPointMapper.updateByPrimaryKey(point);

	                /* G Point 적립 내역 저장*/
	                PointCodePointbackRecord pRecord= new PointCodePointbackRecord();
	                pRecord.setMemberNo(memberNo);
	                pRecord.setPointCodeTransactionNo(pointCouponTransactionNo);
	                pRecord.setNodeNo(nodeNo);
	                pRecord.setNodeType(nodeType);
	                pRecord.setAccRate(accRate);
	                pRecord.setAccPoint(accPointAmount);
	                this.pointCodePointbackRecordMapper.insert(pRecord);
	            } catch (Exception e) {
	                e.printStackTrace();
	                ReturnpBaseResponse res = new ReturnpBaseResponse();
	                ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR,"2000", this.messageUtils.getMessage("pointback.message.inner_server_error"));
	                throw new ReturnpException(res);
	            }
	    }
	        
	    @Override
	    public ReturnpBaseResponse cancelAccumulate(String memberEmail, String phoneNumber, String phoneNumberCountry,String couponNumber) {
	         ReturnpBaseResponse res = new ReturnpBaseResponse();
	        try {
	            ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_OK, "100", this.messageUtils.getMessage("pointback.point_coupon.pointback_cancel_ok"));
	            return res;
	        }catch(ReturnpException e) {
	            e.printStackTrace();
	            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	            res = e.getBaseResponse();
	            return res;
	        }catch(Exception e) {
	            e.printStackTrace();
	            TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
	            ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR,"2000", this.messageUtils.getMessage("pointback.message.inner_server_error"));
	            return res;
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
	    
	     /**
	     * 기본 초기화 작업
	     * 외부 연결 허용 키 목록 세팅
	     * 이후 연결 키 생성 기능구현시, 디비로 관리 
	     */
	/*  @PostConstruct
	     public void init() {
	         keys = new ArrayList<String>(Arrays.asList(env.getProperty("keys").trim().split(",")));
	     }*/
}
