package com.returnp.pointback.dto.command;

import com.returnp.pointback.model.AffiliateTid;

public class AffiliateTidCommand extends AffiliateTid {
	private String affiliateName;
	private String affiliateEmail;;
	private Integer memberNo;;

	
	public String getAffiliateEmail() {
		return affiliateEmail;
	}

	public void setAffiliateEmail(String affiliateEmail) {
		this.affiliateEmail = affiliateEmail;
	}

	public Integer getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(Integer memberNo) {
		this.memberNo = memberNo;
	}

	public String getAffiliateName() {
		return affiliateName;
	}

	public void setAffiliateName(String affiliateName) {
		this.affiliateName = affiliateName;
	}
	
}
