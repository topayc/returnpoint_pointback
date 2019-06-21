package com.returnp.pointback.code;

import java.util.ArrayList;

import com.returnp.pointback.dto.CodeKeyValuePair;

public class CodeDefine {
	public static  ArrayList<CodeKeyValuePair> getNodeTypes() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "일반 회원", "Y", "Y", ""));
		list.add(new CodeKeyValuePair("2", "정회원", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("3", "지사", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("4", "대리점", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("5", "협력 업체(가맹점)", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("6", "영업 관리자", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("7", "총판", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getPointBackStatuses() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "포인트백 시작 ", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "포인트 백 진행중", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("3", "포인트 백 완료", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("4", "포인트 백 취소 시작", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("5", "포인트 백 취소중", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("6", "포인트 백 취소 완료", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("7", "중지", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getCategoryStatuses() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "사용", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "미사용", "Y", "Y" ,""));
		return list;
	}
	
	
	public static ArrayList<CodeKeyValuePair> getConversionStatuses() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "전환중 ", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "전환 완료", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "전환 중지", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getAuthTypes() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "이메일 ", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "핸드폰(문자 인증)", "Y", "Y" ,""));
		return list;
	}

	public static ArrayList<CodeKeyValuePair> getKeywordTypes() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("memberEmail", "이메일 ", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("memberPhone", "전화번호", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("memberName", "이름", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getNodeStatuses() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "정상 ", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "등록 대기 ", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("3", "미 인증", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("4", "인증 완료", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("5", "사용 중지", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("6", "강제 탈퇴", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("7", "사용자 탈퇴", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("8", "사용자 삭제", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getPaymentStatuses() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "입금(결제) 확인중", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "입금(결제) 확인 완료", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("3", "입금(결제) 취소", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("4", "입금(결제) 환불 처리중", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("5", "입금(결제) 환불 완료", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("6", "고객 입금 완료 확인 요청", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getVanPaymentStatusest() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "결제 승인", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "결제 승인 취소", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("3", "결제 승인 오류", "Y", "Y" ,"승인 과정을 재 시도 해주세요"));
		return list;
	}
	
	
	public static ArrayList<CodeKeyValuePair> getPaymentTypest() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "온라인 입금 ", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "신용 카드 결제 ", "N", "N" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getPointTypes() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "GREEN 포인트", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "RED 포인트", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getRegistTypes() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("U", "사용자 등록", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("A", "관리자 등록", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getPointAccStatuses() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "적립 가능", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "적립 불가", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getPointUseStatuses() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "사용 가능", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "사용 불가", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getPaymentTransactionTypes(){
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "QR CODE 매출 등록", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "VAN 매출 등록", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("3", "관리자 수동 매출 등록", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("4", "결제 앱 연동 매출", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("4", "API 호출 결제", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getQueryOrderTypes(){
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("asc", "오름차순", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("desc", "내림차순", "Y", "Y" ,""));
		return list;
	}

	public static ArrayList<CodeKeyValuePair> getPaymentApprovalStatuses() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "결제 승인", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "결제 승인 취소", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("3", "결제 승인 오류", "Y", "Y" ,"승인 과정을 재 시도 해주세요"));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getApiServiceStatuses() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "서비스 숭인", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "서비스 중지", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> geApiServiceTypes() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "쇼핑몰 연동", "Y", "Y" ,""));
	/*	list.add(new CodeKeyValuePair("2", "이체", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("3", "기타", "Y", "Y" ,""));*/
		return list;
	}

	public static ArrayList<CodeKeyValuePair> getPointTransferTypes() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "선물", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "이체", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("3", "기타", "Y", "Y" ,""));
		return list;
	}

	public static ArrayList<CodeKeyValuePair> getPointointTransferStatuses() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "송금 완료", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "송금 실패", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("3", "송금 취소", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getBoardTypes() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "공지", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "FAQ", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("3", "일반 문의", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("4", "가맹 문의", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getCommonBoardCategories() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "일반", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "회원 정보", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("3", "포인트", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("4", "기타", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getAffiliateBoardCategories() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "일반 회원", "Y", "Y", ""));
		list.add(new CodeKeyValuePair("2", "정회원", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("3", "지사", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("4", "대리점", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("5", "협력 업체(가맹점)", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("6", "영업 관리자", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("7", "총판", "Y", "Y" ,""));
		return list;
	}
	
	
	public static ArrayList<CodeKeyValuePair> getBoardSearchKeywordTypest() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "제목", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "내용", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("3", "작성자", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getBoardReplyStatuses() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "답변 대기", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "답변 환료", "Y", "Y" ,""));
		return list;
	}

	public static ArrayList<CodeKeyValuePair> getWithdrawalStatuses() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "출금 처리중", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "출금 처리 완료", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("3", "출금 보류", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("4", "유저 출금 취소", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("5", "관리자 출금 취소", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getPointTypeStatuses() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("R_PAY", "R_PAY", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("R_POINT", "R_POINT", "Y", "Y" ,""));
		return list;
	}

	public static ArrayList<CodeKeyValuePair> getMemberBankAccountStatuses() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("Y", "사용 가능", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("N", "사용 불가", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getBankst() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("003", "기업은행", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("004", "국민은행", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("005", "외환은행", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("007", "수협", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("011", "농협", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("020", "우리은행", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("023", "제일은행", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("027", "씨티은행", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("031", "대구은행", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("032", "부산은행", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("034", "광주은행", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("035", "제주은행", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("037", "전북은행", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("039", "경남은행", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("045", "새마을금고", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("048", "신협", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("071", "우체국", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("081", "하나은행", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("088", "신한은행", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getAffiliateTypes() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("A001", "가맹점", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("A002", "제휴점", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("A003", "무사업자", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("A004", "온라인", "Y", "Y" ,""));
		return list;	
	}
	
	

	public static Object getMarketerStatuses() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "정상", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "중지", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getProductStatuses() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "판매 중", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "판매 중지", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("3", "재고 없음", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getGiftCardTypes() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "온라인 - 모바일 상품권", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "오프라인 - 실물 상품권", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getPurchaseMemberTypes() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "회원 구매", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "비회원 구매", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getGiftCardAccStatuses() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "적립 완료", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "미 적립", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getGiftCardUseStatuses() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "사용 완료", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "미 사용", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getGiftCardStatuses() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "정상 사용", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "사용 중지", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getOrderStatuses() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "주문 접수 완료", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "상품 준비중", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("3", "상품 준비 완료", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("4", "배송 준비", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("5", "배송 중", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("6", "배송 완료", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("7", "주문 처리 완료", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("8", "주문 취소", "Y", "Y" ,"")); /* 사용자 주문 취소*/ 
		list.add(new CodeKeyValuePair("9", " 관리자 주문 취소", "Y", "Y" ,"")); /* 관리자 주문 취소*/ 
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getOrganTypes() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "일반 프랜차이즈", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "판매 조직 ", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getSaleOrganTypes() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("10", "본사", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("11", "총판 ", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("12", "판매점", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getOrganStatuses() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "정상", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "중지", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("3", "보류", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getBargainTypest() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "일반 결제 거래", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "신용 거래", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getOrderTypes() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("10", "본사 발주", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("11", "총판 발주", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("12", "판매점 발주", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("20", "일반 회원 발주", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getOrderReasons() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "재고 주문", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "일반 판매에 주문", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getIssueStatuses() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "발행 준비", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "발행중", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("3", "발행 완료", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("4", "발행 취소 - 삭제", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getRefundStatuses() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "결제 처리중", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "결제 처리 완료", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("3", "결제 보류", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("4", "사용자 결제 요청 취소", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("5", "관리가 결제 처리 취소", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getPaymentRouterTypes() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("1", "VAN", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("2", "PG", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("3", "ADMIN", "Y", "Y" ,""));
		return list;
	}
	
	public static ArrayList<CodeKeyValuePair> getPaymentRouters() {
		ArrayList<CodeKeyValuePair> list = new ArrayList<CodeKeyValuePair>();
		list.add(new CodeKeyValuePair("KICC", "KICC", "Y", "Y" ,""));
		list.add(new CodeKeyValuePair("AAAA", "AAAA", "Y", "Y" ,""));
		return list;
	}
}	
