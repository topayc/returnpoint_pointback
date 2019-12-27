package com.returnp.pointback.dto.command;

import com.returnp.pointback.model.PointCodeIssue;

public class PointCodeIssueCommand extends PointCodeIssue{
	private String accTargetRange;
	private String issueType;
	private Integer affiliateNo;

	public Integer getAffiliateNo() {
		return affiliateNo;
	}

	public void setAffiliateNo(Integer affiliateNo) {
		this.affiliateNo = affiliateNo;
	}

	public String getIssueType() {
		return issueType;
	}

	public void setIssueType(String issueType) {
		this.issueType = issueType;
	}

	public String getAccTargetRange() {
		return accTargetRange;
	}

	public void setAccTargetRange(String accTargetRange) {
		this.accTargetRange = accTargetRange;
	}
	
}
