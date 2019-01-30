package com.returnp.pointback.dto.command.api;

import java.util.Date;

public class ApiRequest {
	private String sfId;  /* 외부 연동 클라이언트에 부여한 고유 아이디 */
	private String apiKey; /* 외부 연동 클라이언트에 부여한 서비스 권한 키  */
	
	private Integer greenPointNo;
	private Integer redPointNo;
	
	private String cacheKey; 
	private String cacheData; 
	
	private Integer memberNo;
	private String memberName;
	private String memberEmail; 
	private String memberPassword;
	private String memberPassword2;
	private String country;
	private String recommendNo;
	private String memberPhone;
	private String language;
	private String recommenderEmail;
	private String memberStatus;
	private Integer recommenderNo;
	
	private String checkExistValue; /* 중복 체크할 값*/
	private String checkExistType;  /* 1 : 이메일 중복 , 2 : 전화번호 중복*/
	
    private String affiliateSerial;  /* 결제한 가맹점 T- ID*/
    private String paymentSource; /*1 : 신용카드, 2 : 현금결제, 3 : R Point 결제*/
    private Integer paymentApprovalAmount;  /* 결제 금액*/
    private String paymentApprovalNumber;   /* 결제 승인 번호*/
    private String paymentApprovalStatus;   /* 0 :승인 , 1 :취소*/
    private String paymentTransactionType;   /* 적립 요청의  종류 구분  1 : 모바일 QR ,  2 : 밴사, 3 : 관리자, 4 : 쇼핑몰  */
    private Date paymentApprovalDateTime;  /* 결제 승인 시간 yyyy-MM-dd HH:mm:ss */

    private Integer withdrawalAmount;  /* 현금 출금 요청할 RPAY 금액*/
    private Integer pointWithdrawalNo;  /* 현금 출금 요청할 RPAY 금액*/
    
    private Integer memberBankAccountNo;
    private String bankName;
    private String accountOwner;
    private String bankAccount;
    private String tableName;
    private String accountStatus;
    private String regType;
    private Integer regAdminNo;
    
    
    private Integer nodeNo;
    private String  nodeType;
    private String  nodeTypeName;
    
    private String qrOrg;
    private String afId;
    
    private float pointAmount;
    
    
    
	public float getPointAmount() {
		return pointAmount;
	}
	public void setPointAmount(float pointAmount) {
		this.pointAmount = pointAmount;
	}
	public String getAfId() {
		return afId;
	}
	public void setAfId(String afId) {
		this.afId = afId;
	}
	public String getQrOrg() {
		return qrOrg;
	}
	public void setQrOrg(String qrOrg) {
		this.qrOrg = qrOrg;
	}
	public Integer getPointWithdrawalNo() {
		return pointWithdrawalNo;
	}
	public void setPointWithdrawalNo(Integer pointWithdrawalNo) {
		this.pointWithdrawalNo = pointWithdrawalNo;
	}
	public String getRegType() {
		return regType;
	}
	public void setRegType(String regType) {
		this.regType = regType;
	}
	public Integer getRegAdminNo() {
		return regAdminNo;
	}
	public void setRegAdminNo(Integer regAdminNo) {
		this.regAdminNo = regAdminNo;
	}
	public String getAccountStatus() {
		return accountStatus;
	}
	public void setAccountStatus(String accountStatus) {
		this.accountStatus = accountStatus;
	}
	public Integer getMemberBankAccountNo() {
		return memberBankAccountNo;
	}
	public void setMemberBankAccountNo(Integer memberBankAccountNo) {
		this.memberBankAccountNo = memberBankAccountNo;
	}
	public String getMemberStatus() {
		return memberStatus;
	}
	public void setMemberStatus(String memberStatus) {
		this.memberStatus = memberStatus;
	}
	public Integer getNodeNo() {
		return nodeNo;
	}
	public void setNodeNo(Integer nodeNo) {
		this.nodeNo = nodeNo;
	}
	public String getNodeType() {
		return nodeType;
	}
	public void setNodeType(String nodeType) {
		this.nodeType = nodeType;
	}
	public String getNodeTypeName() {
		return nodeTypeName;
	}
	public void setNodeTypeName(String nodeTypeName) {
		this.nodeTypeName = nodeTypeName;
	}
	public String getSfId() {
		return sfId;
	}
	public void setSfId(String sfId) {
		this.sfId = sfId;
	}
	public String getApiKey() {
		return apiKey;
	}
	public void setApiKey(String apiKey) {
		this.apiKey = apiKey;
	}
	public Integer getGreenPointNo() {
		return greenPointNo;
	}
	public void setGreenPointNo(Integer greenPointNo) {
		this.greenPointNo = greenPointNo;
	}
	public Integer getRedPointNo() {
		return redPointNo;
	}
	public void setRedPointNo(Integer redPointNo) {
		this.redPointNo = redPointNo;
	}
	public String getCacheKey() {
		return cacheKey;
	}
	public void setCacheKey(String cacheKey) {
		this.cacheKey = cacheKey;
	}
	public String getCacheData() {
		return cacheData;
	}
	public void setCacheData(String cacheData) {
		this.cacheData = cacheData;
	}
	public Integer getMemberNo() {
		return memberNo;
	}
	public void setMemberNo(Integer memberNo) {
		this.memberNo = memberNo;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberEmail() {
		return memberEmail;
	}
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
	public String getMemberPassword() {
		return memberPassword;
	}
	public void setMemberPassword(String memberPassword) {
		this.memberPassword = memberPassword;
	}
	public String getMemberPassword2() {
		return memberPassword2;
	}
	public void setMemberPassword2(String memberPassword2) {
		this.memberPassword2 = memberPassword2;
	}
	public String getCountry() {
		return country;
	}
	public void setCountry(String country) {
		this.country = country;
	}

	public String getRecommendNo() {
		return recommendNo;
	}
	public void setRecommendNo(String recommendNo) {
		this.recommendNo = recommendNo;
	}
	public String getMemberPhone() {
		return memberPhone;
	}
	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}
	public String getLanguage() {
		return language;
	}
	public void setLanguage(String language) {
		this.language = language;
	}
	public String getRecommenderEmail() {
		return recommenderEmail;
	}
	public void setRecommenderEmail(String recommenderEmail) {
		this.recommenderEmail = recommenderEmail;
	}
	public Integer getRecommenderNo() {
		return recommenderNo;
	}
	public void setRecommenderNo(Integer recommenderNo) {
		this.recommenderNo = recommenderNo;
	}
	public String getCheckExistValue() {
		return checkExistValue;
	}
	public void setCheckExistValue(String checkExistValue) {
		this.checkExistValue = checkExistValue;
	}
	public String getCheckExistType() {
		return checkExistType;
	}
	public void setCheckExistType(String checkExistType) {
		this.checkExistType = checkExistType;
	}
	public String getAffiliateSerial() {
		return affiliateSerial;
	}
	public void setAffiliateSerial(String affiliateSerial) {
		this.affiliateSerial = affiliateSerial;
	}
	public String getPaymentSource() {
		return paymentSource;
	}
	public void setPaymentSource(String paymentSource) {
		this.paymentSource = paymentSource;
	}
	public Integer getPaymentApprovalAmount() {
		return paymentApprovalAmount;
	}
	public void setPaymentApprovalAmount(Integer paymentApprovalAmount) {
		this.paymentApprovalAmount = paymentApprovalAmount;
	}
	public String getPaymentApprovalNumber() {
		return paymentApprovalNumber;
	}
	public void setPaymentApprovalNumber(String paymentApprovalNumber) {
		this.paymentApprovalNumber = paymentApprovalNumber;
	}
	public String getPaymentApprovalStatus() {
		return paymentApprovalStatus;
	}
	public void setPaymentApprovalStatus(String paymentApprovalStatus) {
		this.paymentApprovalStatus = paymentApprovalStatus;
	}
	public String getPaymentTransactionType() {
		return paymentTransactionType;
	}
	public void setPaymentTransactionType(String paymentTransactionType) {
		this.paymentTransactionType = paymentTransactionType;
	}
	public Date getPaymentApprovalDateTime() {
		return paymentApprovalDateTime;
	}
	public void setPaymentApprovalDateTime(Date paymentApprovalDateTime) {
		this.paymentApprovalDateTime = paymentApprovalDateTime;
	}
	public Integer getWithdrawalAmount() {
		return withdrawalAmount;
	}
	public void setWithdrawalAmount(Integer withdrawalAmount) {
		this.withdrawalAmount = withdrawalAmount;
	}
	public String getBankName() {
		return bankName;
	}
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	public String getAccountOwner() {
		return accountOwner;
	}
	public void setAccountOwner(String accountOwner) {
		this.accountOwner = accountOwner;
	}
	public String getBankAccount() {
		return bankAccount;
	}
	public void setBankAccount(String bankAccount) {
		this.bankAccount = bankAccount;
	}
	public String getTableName() {
		return tableName;
	}
	public void setTableName(String tableName) {
		this.tableName = tableName;
	}
    
    
    
}
