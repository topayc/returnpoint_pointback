package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;

public class PaymentRouter extends QueryCondition {
    private Integer paymentRouterNo;

    private String paymentRouterType;

    private String paymentRouterCode;

    private String paymentRouterName;

    public Integer getPaymentRouterNo() {
        return paymentRouterNo;
    }

    public void setPaymentRouterNo(Integer paymentRouterNo) {
        this.paymentRouterNo = paymentRouterNo;
    }

    public String getPaymentRouterType() {
        return paymentRouterType;
    }

    public void setPaymentRouterType(String paymentRouterType) {
        this.paymentRouterType = paymentRouterType == null ? null : paymentRouterType.trim();
    }

    public String getPaymentRouterCode() {
        return paymentRouterCode;
    }

    public void setPaymentRouterCode(String paymentRouterCode) {
        this.paymentRouterCode = paymentRouterCode == null ? null : paymentRouterCode.trim();
    }

    public String getPaymentRouterName() {
        return paymentRouterName;
    }

    public void setPaymentRouterName(String paymentRouterName) {
        this.paymentRouterName = paymentRouterName == null ? null : paymentRouterName.trim();
    }
}