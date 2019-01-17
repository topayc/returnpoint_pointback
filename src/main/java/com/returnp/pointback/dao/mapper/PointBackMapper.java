package com.returnp.pointback.dao.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import org.apache.ibatis.annotations.Param;

import com.returnp.pointback.dto.command.GreenPointCommand;
import com.returnp.pointback.dto.command.InnerPointBackTarget;
import com.returnp.pointback.dto.command.MembershipRequestCommand;
import com.returnp.pointback.dto.command.OuterPointBackTarget;
import com.returnp.pointback.dto.command.PaymentPointbackRecordCommand;
import com.returnp.pointback.dto.command.PointBackTarget;
import com.returnp.pointback.dto.command.PointConversionTransactionCommand;
import com.returnp.pointback.dto.command.PointConvertRequestCommand;
import com.returnp.pointback.dto.command.RecommenderCommand;
import com.returnp.pointback.dto.command.RedPointCommand;
import com.returnp.pointback.dto.request.SearchCondition;
import com.returnp.pointback.model.Admin;
import com.returnp.pointback.model.Affiliate;
import com.returnp.pointback.model.Agency;
import com.returnp.pointback.model.Branch;
import com.returnp.pointback.model.CompanyBankAccount;
import com.returnp.pointback.model.GreenPoint;
import com.returnp.pointback.model.Member;
import com.returnp.pointback.model.MembershipRequest;
import com.returnp.pointback.model.PaymentPointbackRecord;
import com.returnp.pointback.model.PaymentTransaction;
import com.returnp.pointback.model.PointConversionTransaction;
import com.returnp.pointback.model.Policy;
import com.returnp.pointback.model.Recommender;
import com.returnp.pointback.model.SaleManager;
import com.returnp.pointback.model.SoleDist;

public interface PointBackMapper {
	public ArrayList<Policy> findPolicies(Policy cond);
	
	public PointBackTarget findInnerPointBackFindTarget(PointBackTarget target);
	public OuterPointBackTarget findOuterPointBackTarget(OuterPointBackTarget target);
	public InnerPointBackTarget findInnerPointBackTarget(@Param("affiliateSerial") String affiliateSerial);
	
	public ArrayList<Admin> findAdmins(Admin admin);
	public ArrayList<Member> findMembers(Member member);
	public ArrayList<GreenPointCommand> findGreenPointCommands(GreenPointCommand cond);
	public ArrayList<GreenPoint> findGreenPoints(GreenPoint cond);
	public ArrayList<RedPointCommand> findGreenPointCommands(RedPointCommand cond);
	public ArrayList<RedPointCommand> findRedPointCommands(RedPointCommand command);
	
	public ArrayList<RecommenderCommand> findRecommenderCommands(Recommender recommender);
	public Recommender findRecommender(Recommender recommender);
	public ArrayList<Branch> findBranches(Branch brach);
	public ArrayList<Agency> findAgencies(Agency agency);
	public ArrayList<Affiliate> findAffiliates(Affiliate affiliate);
	public ArrayList<SaleManager> findSaleManagers(SaleManager saleManager);
	public ArrayList<MembershipRequestCommand> findMembershipRequests(SearchCondition  nodeSearch);
	public MembershipRequest findMembershipRequest(MembershipRequest  membershipRequest);
	public MembershipRequestCommand findMembershipRequestCommand(MembershipRequestCommand  membershipRequestCommand);
	public ArrayList<CompanyBankAccount> findCompanyBanks(CompanyBankAccount companyBankAccount);
	public ArrayList<PaymentTransaction> findPaymentTransactions(PaymentTransaction record);
	public ArrayList<SoleDist> findSoleDists(SoleDist record);
	public ArrayList<MembershipRequestCommand> findMembershipRequestCommands(MembershipRequestCommand mrCond);

	public ArrayList<PaymentPointbackRecord> findPaymentPointbackRecords(PaymentPointbackRecord paymentPointbackRecord);
	public ArrayList<PaymentPointbackRecordCommand> findPaymentPointbackRecordCommands(PaymentPointbackRecordCommand paymentPointbackRecordCommand);

	public ArrayList<PointConvertRequestCommand> findPointConvertRequestCommands(PointConvertRequestCommand mrCond);
	public ArrayList<PointConversionTransactionCommand> findPointConversionTransactionCommands(PointConversionTransactionCommand mrCond);
	public ArrayList<PointConversionTransaction> findPointConversionTransactions(PointConversionTransaction mrCond);
	public ArrayList<HashMap<String, Object>> selectDirectNodes(HashMap<String, Object> param);
}
