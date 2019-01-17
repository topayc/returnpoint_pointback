package com.returnp.pointback.common;

public class AppConstants {
	public static String ADMIN_SESSION = "adminSession";
	
	public static class NodeType {
		public static final String MEMBER  = "1";
		public static final String RECOMMENDER  = "2";
		public static final String BRANCH    = "3";
		public static final String AGENCY    = "4";
		public static final String AFFILIATE    = "5";
		public static final String SALE_MANAGER    = "6";
		public static final String SOLE_DIST    = "7";
	}
	
	public static class NodeTypeName {
		public static final String MEMBER  = "member";
		public static final String RECOMMENDER  = "recommender";
		public static final String BRANCH    = "branch";
		public static final String AGENCY    = "agency";
		public static final String AFFILIATE    = "affiliate ";
		public static final String SALE_MANAGER    = "sale_manager";
		public static final String SOLE_DIST    = "sole_dist";
	}
	
	public static class GreenPointAccStatus {
		public static String POINTBACK_START  = "1";
		public static String POINTBACK_PROGRESS  = "2";
		public static String POINTBACK_COMPLETE  = "3";
		public static String POINTBACK_CANCEL_START= "4";
		public static String POINTBACK_CANCEL_PROGRESS= "5";
		public static String POINTBACK_CANCEL_COMPLETE  = "6";
		public static String POINTBACK_STOP= "7";
		public static String POINTBACK_CANCEL_STOP= "8";
		public static String POINTBACK_REQ_ERROR= "9";
	
	}
	
	public static class PaymentApprovalStatus {
		public static String  PAYMENT_APPROVAL_OK = "1";
		public static String  PAYMENT_APPROVAL_CANCEL = "2";
		public static String  PAYMENT_APPROVAL_ERROR= "3";
	}
	
	public static class CategoryStatus {
		public static String USE = "1";
		public static String NOT_USE  = "2";
	
	}
	
	public static class ConversionStatus {
		public static String PROGRESS  = "1";
		public static String STOP  = "2";
		public static String COMPLETE  = "3";
	}
	
	public static class ResponsResultCode {
		public static String SUCCESS  = "100";
		public static String ERROR   = "101";
		public static String FAILED    = "102";
	}
	
	public static class ResponseResult{
		public static String SUCCESS  = "success";
		public static String ERROR   = "error";
		public static String FAILED    = "failed";
	}

	public static class KeywordType{
		public static String  EMAIL  = "1";
		public static String PHONE   = "2";
		public static String NAME    = "3";
	}
	
	public static class ReigistType {
		public static String  REGIST_BY_ADMIN  = "A";
		public static String  REGIST_BY_USER = "U";
	}
	
	public static class PaymentType {
		public static String  PAYMENT_ONLINE  = "1";
		public static String  PAYMENT_CREDIT = "2";
	}
	
	public static class PointAccStatus {
		public static String  ACC_OK  = "Y";
		public static String  ACC_NO  = "N";
	}
	
	public static class PointUseStatus {
		public static String  USE_OK  = "Y";
		public static String  USE_NO  = "N";
	}
	
	public static class NodeStatus {
		public static String  NORMAL  = "1";
		public static String  REG_WAIT  = "2";
		public static String  AUTO_NOT  = "3";
		public static String  AUTH_OK  = "4";
		public static String  USE_STOP  = "5";
		public static String  USE_WITHDRAWAL  = "6";
		public static String  ADMIN_WITHDRAWAL   = "7";
	}
	
	public static class PaymentStatus {
		public static String  PAYMENT_OK  = "1";
		public static String  PAYMENT_CANCEL = "2";
		public static String  PAYMENT_ERROR= "3";
	}
	
	public static class PaymentTransactionType{
		public static final String  QR = "1";
		public static final String  VAN= "2";
		public static final String  ADMIN= "3";
		public static final String  SHOPPING_MAL= "4";
	}
	public static class VanPaymentStatus {
		public static String  VAN_AUTO_REGIST = "1";
		public static String  ADMIN_MANUAL_REGIST  = "2";
	}
	
	public static class Point{
		public static String  GREEN_POINT = "1";
		public static String  RED_POINT   = "2";
	}
	
	public static class AccumulateRequestFrom {
		public static final String  FROM_BACKOFFICE = "1";
		public static final String  FROM_QR   = "2";
		public static final String  FROM_SHOPPING_MALL   = "3";
	}
	
	public static class AccumulateRequesParameter{
		public static final String  QR_ORG = "qr_org";
		public static final String  PAYMENT_AMOUNT   = "pam";
		public static final String  PAYMENT_STATUS   = "pas";
		public static final String  PAYMENT_TIME   = "pat";
		public static final String  PAYMENT_APPROVAL_NUMBER   = "pan";
		public static final String  AFFILITE_TID= "af_id";
		public static final String  PHONE_NUMBER= "phoneNumber";
		public static final String  PHONE_NUMBER_COUNTRY= "phoneNumberCountry";
		public static final String  MEMBER_EMAIL= "memberEmail";
		public static final String  ACC_REQUEST_FROM= "acc_from";
		public static final String  KEY= "key";
	}
	
	public static class AccumulateStatus {
		public static String POINTBACK_START  = "1";
		public static String POINTBACK_PROGRESS  = "2";
		public static String POINTBACK_COMPLETE  = "3";
		public static String POINTBACK_CANCEL_START= "4";
		public static String POINTBACK_CANCEL_PROGRESS= "5";
		public static String POINTBACK_CANCEL_COMPLETE  = "6";
		public static String POINTBACK_STOP= "7";
		public static String POINTBACK_CANCEL_STOP= "8";
		public static String POINTBACK_REQ_ERROR= "9";
	
	}
}
