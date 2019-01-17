package com.returnp.pointback.dto.command;

public class OuterPointBackTarget {
	private Integer memberNo;    // 회원 번호
	private String memberName;
	private Integer firstRecommenderMemberNo;    
	private String firstRecommenderMemberName;    
	private Integer secondRecommenderMemberNo;    
	private String secondRecommenderMenberName;
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
	public Integer getFirstRecommenderMemberNo() {
		return firstRecommenderMemberNo;
	}
	public void setFirstRecommenderMemberNo(Integer firstRecommenderMemberNo) {
		this.firstRecommenderMemberNo = firstRecommenderMemberNo;
	}
	public String getFirstRecommenderMemberName() {
		return firstRecommenderMemberName;
	}
	public void setFirstRecommenderMemberName(String firstRecommenderMemberName) {
		this.firstRecommenderMemberName = firstRecommenderMemberName;
	}
	public Integer getSecondRecommenderMemberNo() {
		return secondRecommenderMemberNo;
	}
	public void setSecondRecommenderMemberNo(Integer secondRecommenderMemberNo) {
		this.secondRecommenderMemberNo = secondRecommenderMemberNo;
	}
	public String getSecondRecommenderMenberName() {
		return secondRecommenderMenberName;
	}
	public void setSecondRecommenderMenberName(String secondRecommenderMenberName) {
		this.secondRecommenderMenberName = secondRecommenderMenberName;
	}
	
	
}
