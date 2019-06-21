package com.returnp.pointback.common;

public class AppConstants {
	public static String ADMIN_SESSION = "adminSession";

	public static class AdminType {
		public static  final String SUPER = "1";   /*전체 시스템 관리자*/
		public static  final String HEAD_ORGAN= "10";    /*상품권 본사*/
		public static  final String DIST_ORGAN = "11";  /*상품권 총판*/ 
		public static  final String SALES_ORGAN = "12";  /*상품권 판매점 */ 
	}
	public static class NodeType {
		public static final String MEMBER = "1";
		public static final String RECOMMENDER = "2";
		public static final String BRANCH = "3";
		public static final String AGENCY = "4";
		public static final String AFFILIATE = "5";
		public static final String SALE_MANAGER = "6";
		public static final String SOLE_DIST = "7";
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
		public static String PAYMENT_APPROVAL_OK = "1";
		public static String PAYMENT_APPROVAL_CANCEL = "2";
		public static String PAYMENT_APPROVAL_ERROR = "3";
	}

	public static class CategoryStatus {
		public static String USE = "1";
		public static String NOT_USE = "2";

	}

	public static class ConversionStatus {
		public static String PROGRESS = "1";
		public static String STOP = "2";
		public static String COMPLETE = "3";
	}

	public static class ResponsResultCode {
		public static String SUCCESS = "100";
		public static String ERROR = "101";
		public static String FAILED = "102";
	}

	public static class ResponseResult {
		public static String SUCCESS = "success";
		public static String ERROR = "error";
		public static String FAILED = "failed";
	}

	public static class KeywordType {
		public static String EMAIL = "1";
		public static String PHONE = "2";
		public static String NAME = "3";
	}

	public static class ReigistType {
		public static String REGIST_BY_ADMIN = "A";
		public static String REGIST_BY_USER = "U";
	}

	public static class PaymentType {
		public static String PAYMENT_ONLINE = "1";
		public static String PAYMENT_CREDIT = "2";
	}

	/* 고객이 가맹점에서 결제한 수단 */
	public static class AffiliatePaymentType {
		public static String CREDIT = "1";
		public static String CASH = "2";
	}

	public static class PointAccStatus {
		public static String ACC_OK = "Y";
		public static String ACC_NO = "N";
	}

	public static class PointUseStatus {
		public static String USE_OK = "Y";
		public static String USE_NO = "N";
	}

	public static class NodeStatus {
		public static String NORMAL = "1";
		public static String REG_WAIT = "2";
		public static String AUTO_NOT = "3";
		public static String AUTH_OK = "4";
		public static String USE_STOP = "5";
		public static String USE_WITHDRAWAL = "6";
		public static String ADMIN_WITHDRAWAL = "7";
	}

	public static class PaymentStatus {
		public static String PAYMENT_CHECK = "1";
		public static String PAYMENT_OK = "2";
		public static String PAYMENT_CANCEL = "3";
		public static String PAYMENT_CANCEL_PROCESSING = "4";
		public static String PAYMENT_CANCEL_PROCESSING_COMPLETER = "5";
	}

	public static class PaymentTransactionType{
		public static final String  QR = "1";
		public static final  String  VAN= "2";
		public static final  String  MANUAL= "3";
		public static final  String  APP= "4";
		public static final  String  API= "5";
	}

	public static class VanPaymentStatus {
		public static String VAN_AUTO_REGIST = "1";
		public static String ADMIN_MANUAL_REGIST = "2";
	}

	public static class Point {
		public static String GREEN_POINT = "1";
		public static String RED_POINT = "2";
	}

	public static class PointType {
		public static String R_PAY = "R_PAY";
		public static String R_POINT = "R_POINT";
	}

	public static class WithDrawalStatus {
		public static String WITHDRAWAL_PROCESSING = "1";
		public static String WITHDRAWAL_COMPLETED = "2";
		public static String WITHDRAWAL_CANCEL_BY_USER = "3";
		public static String WITHDRAWAL_CANCEL_BY_ADMIN = "4";
		public static String WITHDRAWAL_HOLDING = "5";
	}
	
	public static class MemberBankAccountStatus{
		public static String USE  = "Y";
		public static String NOT_USE = "N";
	}

	public static class CodeGoup {
		public static String NODE_TYPE = "CD00001";
		public static String POINTBACk_STATUS = "CD00002";
		public static String CATEGORY_STATUS = "CD00003";
		public static String CONVERSON_STATUS = "CD00004";
		public static String AUTH_TYPE = "CD00005";
		public static String KEYWORD_TYPE = "CD00006";
		public static String NODE_STATUS = "CD00007";
		public static String PAYMENT_STATUS = "CD00008";
		public static String VAN_PAYMENT_STATUS = "CD00009";
		public static String PAYMENT_TYPE = "CD00010";
		public static String POINT_TYPE = "CD00011";
		public static String REGIST_TYPE = "CD00012";
		public static String POINT_ACC_STATUS = "CD00013";
		public static String POINT_USE_STATUS = "CD00014";
		public static String PAYMENT_TRANSACTION_TYPE = "CD00015";
		public static String ORDER_TYPE = "CD00016";
		public static String PAYMENT_APPROVAL_STATUS = "CD00017";
		public static String API_SERVICE_STATUS = "CD00018";
		public static String POINT_TRANSFER_TYPE = "CD00019";
		public static String POINT_TRANSFER_STAUS = "CD00020";
		public static String BOARD_TYPE = "CD00021";
	}
	
	public static class reaccumulateType {
		public static final String ACC_AFTER_DEL = "1";
		public static final String ACC_NOT_DEL = "2";
	}
	
	public static class AccumulateRequestFrom {
		public static final String  FROM_BACKOFFICE = "1";
		public static final String  FROM_QR   = "2";
		public static final String  FROM_SHOPPING_MALL   = "3";
	}
	
	
	
	public static class PaymentTransactionStatus{
		public static final String APPROVAL = "1";
		public static final String CANCEL = "2";
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
	
	public static class ProductStatus {
		public static String ON_SALE  = "1"; //판매중
		public static String NOT_ON_SALE = "2"; // 판매 중지
		public static String NOT_STOCL = "3"; // 재고 없음
	}
	
	public static class GiftCardType {
		public static String MOBILE_GIFTCARD  = "1";
		public static String REAL_GIFTCARD  = "2";
	}
	
	public static class PurchageMemberType {
		public static String MEMBER  = "1";
		public static String NOT_MEMBER  = "2";
	}
	
	public static class GiftCardAccableStatus {
		public static String ACCUMULAT_NOT = "Y";
		public static String ACCUMULATE_COMPLETE= " N";
	}
	
	public static class GiftCardPayableStatus {
		public static String PAYED_NOT = "Y";
		public static String PAYED_COMPLETE= " N";
	}
	
	public static class GiftCardUseStatus {
		public static String NOT_PAYED = "Y";
		public static String ALREADY_PAYED= "N";
	}
	
	public static class GiftCardStatus {
		public static String NORMAL  = "1";
		public static String STOP_USING= "2";
	}
	
	public static class OrderStatus {
		public static String ORDER_RECEPTION  = "1";
		public static String PRODUCT_IN_PREPARATION = "2";
		public static String PRODUCT_READY= "3";
		public static String SHIPPING_PREPARATION = "4";
		public static String SHIPPING = "5";
		public static String SHIPPING_COMPLETED= "6";
		public static String ORDER_PROCESSED= "7"; /*주문 처리 완료*/ 
		public static String ORDER_USER_CANCELED = "8";  /* 사용자 주문 취소*/
		public static String ORDER_ADMIN_CANCELED = "9"; /* 관리자 주문 취소*/
	}
	
	public static class GiftCardPublishType{
		public static String NORMAL_PUBLISH  = "1";
		public static String ORGANL_PUBLISH  = "2";
	}
	
	public static class AffiliateType{
		public static String COMMON_AFFILIATE  = "A001"; /* 일반 가맹점, 일반 결제후 영수증 큐일 적립 가맹점*/
		public static String GIFT_CARD_USAGE_AFFILIATE   = "A002";  /* 상품권 사용처 */
		public static String NO_BUSINESS_NUMBER_AFFILIATE    = "A003";  /* 무사업자 가맹점*/
		public static String ONLINE_AFFILIATE    = "A004";  /* 온라인 쇼핑몰등의 온라인 사업자 */
	}

	public static class OrganType{
		public static String COMMON_FRANCHISE_ORGAN= "1"; /* 일반 조직*/
		public static String GIFTCARD_SALES_ORGAN   = "2";  /* 판매 조직*/
	}
	
	public static class OrganStatus{
		public static String NORMAL= "1"; /*정상*/
		public static String STOP   = "2";  /* 중지*/
		public static String HOLD= "3";  /* 보류*/
	}
	
	public static class SaleOrganType{
		public static String SALE_HEADQUARTER= "10";  /* 본사*/
		public static String SALE_DISTRIBUTOR   = "11";   /* 총판*/
		public static String SALE_STORE= "12";   /* 판매점*/
	}
	
	public static class BargainType{
		public static String COMMON= "1"; /* 일반 결제 거래*/
		public static String CREDIT   = "2";  /* 신용 거래 */
	}
	
	public static class OrderType{
		public static String ISSUE_HEADQUARTER = "10"; /* 본사 의 재고 및 발주 주문*/
		public static String ISSUE_DISTRIBUTOR   = "11";  /* 총판  구매 요청에 의한 발행 */
		public static String ISSUE_STORE  = "12";  /* 판매점 구매 요청에 의한 발행*/
		public static String ISSUE_MEMBER  = "20";  /* 일반 회원 구매 요청에 의한 발행*/
	}
	public static class OrderReason{
		public static String ISSUE_FOR_STOCK = "1"; /* 재고 발행*/
		public static String ISSUE_FOR_BUY   = "2";  /* 판매점 및 일반 회원 구매에 의한 발행  */
	}
	
	public static class IssueStatus{
		public static String PREPARE_TO_ISSUE = "1"; /* 발행 준비*/
		public static String SSUING   = "2";  /* 발행중*/
		public static String ISSUED   = "3";  /* 발행 완료  */
		public static String ISSUE_CANCEL   = "4";  /* 발행 취소 */
	}
	
	public static class PaymentRouterType{
		public static final String  VAN = "VAN";
		public static final  String  PG= "PG";
		public static final  String  ADMIN= "ADMIN";
	}
	
	public static class PaymentRouterName{
		public static final String  KICC  = "KICC";
		public static final  String  KFT= "KFT";
		public static final  String  SAIDA= "SAIDA";
		public static final  String  ADMIN= "ADMIN";
	}
	
	

}
