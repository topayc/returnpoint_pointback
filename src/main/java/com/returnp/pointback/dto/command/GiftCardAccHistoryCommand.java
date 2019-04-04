package com.returnp.pointback.dto.command;

import com.returnp.pointback.model.GiftCardAccHistory;

public class GiftCardAccHistoryCommand extends GiftCardAccHistory {
	private String giftCardName;
	private String memberName;
	private String memberEmail;
	private String memberPhone;
	
	
	public String getMemberEmail() {
		return memberEmail;
	}
	public void setMemberEmail(String memberEmail) {
		this.memberEmail = memberEmail;
	}
	public String getGiftCardName() {
		return giftCardName;
	}
	public void setGiftCardName(String giftCardName) {
		this.giftCardName = giftCardName;
	}
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemberPhone() {
		return memberPhone;
	}
	public void setMemberPhone(String memberPhone) {
		this.memberPhone = memberPhone;
	}

}
