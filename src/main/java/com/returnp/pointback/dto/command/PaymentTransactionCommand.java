package com.returnp.pointback.dto.command;

import com.returnp.pointback.model.PaymentTransaction;

public class PaymentTransactionCommand extends PaymentTransaction {
	public String affiliateName;
	
    public int paymentRouterNo;
    public String paymentRouterType;
    public String paymentRouterName;
    public String paymentRouterCode;
    
	public int getPaymentRouterNo() {
		return paymentRouterNo;
	}

	public void setPaymentRouterNo(int paymentRouterNo) {
		this.paymentRouterNo = paymentRouterNo;
	}

	public String getPaymentRouterType() {
		return paymentRouterType;
	}

	public void setPaymentRouterType(String paymentRouterType) {
		this.paymentRouterType = paymentRouterType;
	}

	public String getPaymentRouterName() {
		return paymentRouterName;
	}

	public void setPaymentRouterName(String paymentRouterName) {
		this.paymentRouterName = paymentRouterName;
	}

	public String getPaymentRouterCode() {
		return paymentRouterCode;
	}

	public void setPaymentRouterCode(String paymentRouterCode) {
		this.paymentRouterCode = paymentRouterCode;
	}

	public String getAffiliateName() {
		return affiliateName;
	}

	public void setAffiliateName(String affiliateName) {
		this.affiliateName = affiliateName;
	}

	
}

