package com.returnp.pointback.dao.mapper;

import java.util.ArrayList;
import java.util.HashMap;

import com.returnp.pointback.dto.command.api.ApiRequest;

public interface ApiMapper {
	HashMap<String,Object> selectMemberInfo(ApiRequest apiRequest);
	HashMap<String,Object> selectMember(ApiRequest apiRequest);
	int  createMember(ApiRequest apiRequest);
	int  updateMember(ApiRequest apiRequest);
	int  deleteMember(ApiRequest apiRequest);

	int  createGreenPoint(ApiRequest apiRequest);
	int  createRedPoint(ApiRequest apiRequest);
	int selectMemberCount(ApiRequest apiRequest);
	
	HashMap<String,Object> selectRecommenderInfo(ApiRequest apiRequest);
	ArrayList<HashMap<String,Object>> selectLanguages();
	HashMap<String,Object> selectPolicy();

	ArrayList<HashMap<String,Object>> selectBankAccounts(ApiRequest apiRequest);
	HashMap<String,Object> selectBankAccount(ApiRequest apiRequest);
	int  createMemberBankAccount(ApiRequest apiRequest);
	int  deleteMemberBankAccount(ApiRequest apiRequest);
	int updateMemberBankAccount(ApiRequest apiRequest);
	
	ArrayList<HashMap<String,Object>> selectPointwithdrawals(ApiRequest apiRequest);
	HashMap<String,Object> selectPointwithdrawal(ApiRequest apiRequest);
	int  creatPointWithdrawal(ApiRequest apiRequest);
	int  deletePointWithdrawal(ApiRequest apiRequest);
	int  updatePointWithdrawal(ApiRequest apiRequest);
	
	ArrayList<HashMap<String,Object>> selectMyMembers(ApiRequest apiRequest);
	HashMap<String,Object> selectRedPoint(ApiRequest apiRequest);
	int  updateRedPoint(ApiRequest apiRequest);
}
