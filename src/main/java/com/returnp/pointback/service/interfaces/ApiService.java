package com.returnp.pointback.service.interfaces;

import javax.servlet.http.HttpSession;

import org.springframework.transaction.annotation.Transactional;

import com.returnp.pointback.dto.command.api.ApiRequest;
import com.returnp.pointback.dto.response.ReturnpBaseResponse;

@Transactional
public interface ApiService {
	public ReturnpBaseResponse getDataCache(ApiRequest apiRequest,HttpSession session);
	
	public ReturnpBaseResponse saveDataCache(ApiRequest apiRequest, HttpSession session);
	
	public ReturnpBaseResponse join(ApiRequest apiRequest);

	public ReturnpBaseResponse deleteMember(ApiRequest apiRequest);
	
	public ReturnpBaseResponse modifyMember(ApiRequest apiRequest);
	
	public ReturnpBaseResponse getMemberInfo(ApiRequest apiRequest);
	
	public ReturnpBaseResponse checkDuplicated(ApiRequest apiRequest);
	
	public ReturnpBaseResponse executeAccumuate(ApiRequest apiRequest);
	
	public ReturnpBaseResponse cancelAccumuate(ApiRequest apiRequest);
	
	public ReturnpBaseResponse getLanguages(ApiRequest apiRequest);
	
	public ReturnpBaseResponse getGpointAccumuateHistory(ApiRequest apiRequest);
	
	public ReturnpBaseResponse getRpointConversionHistory(ApiRequest apiRequest);
	
	public ReturnpBaseResponse getPolicy(ApiRequest apiRequest);
	
	public ReturnpBaseResponse getBankAccounts(ApiRequest apiRequest);
	
	public ReturnpBaseResponse registerBankAccount(ApiRequest apiRequest);
	
	public ReturnpBaseResponse updateBankAccount(ApiRequest apiRequest);
	
	public ReturnpBaseResponse deleteBankAccount(ApiRequest apiRequest);
	
	/*출금 관련 */
	public ReturnpBaseResponse getPointwithdrawals(ApiRequest apiRequest);
	
	public ReturnpBaseResponse withdrawaPoint(ApiRequest apiRequest);
	
	public ReturnpBaseResponse deletePointWithdrawal(ApiRequest apiRequest);
	
	public ReturnpBaseResponse updatePointWithdrawal(ApiRequest apiRequest);

	public ReturnpBaseResponse getMyMembers(ApiRequest apiRequest);
	
}