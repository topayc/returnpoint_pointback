package com.returnp.pointback.dto.command;

import com.returnp.pointback.model.GiftCardAccHistory;

public class GiftCardAccHistoryCommand extends GiftCardAccHistory {
	private String memberName;
	private String memeberEmai;
	private String memeberPhone;
	public String getMemberName() {
		return memberName;
	}
	public void setMemberName(String memberName) {
		this.memberName = memberName;
	}
	public String getMemeberEmai() {
		return memeberEmai;
	}
	public void setMemeberEmai(String memeberEmai) {
		this.memeberEmai = memeberEmai;
	}
	public String getMemeberPhone() {
		return memeberPhone;
	}
	public void setMemeberPhone(String memeberPhone) {
		this.memeberPhone = memeberPhone;
	}
}
