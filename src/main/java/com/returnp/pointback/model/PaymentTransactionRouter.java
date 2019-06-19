package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;

public class PaymentTransactionRouter extends QueryCondition {
    private Integer paymentTransactionRouterNo;

    private Integer paymentTransactionNo;

    private String paymentTransactionRouterType;

    private String paymentTransactionRouterName;

    public Integer getPaymentTransactionRouterNo() {
        return paymentTransactionRouterNo;
    }

    public void setPaymentTransactionRouterNo(Integer paymentTransactionRouterNo) {
        this.paymentTransactionRouterNo = paymentTransactionRouterNo;
    }

    public Integer getPaymentTransactionNo() {
        return paymentTransactionNo;
    }

    public void setPaymentTransactionNo(Integer paymentTransactionNo) {
        this.paymentTransactionNo = paymentTransactionNo;
    }

    public String getPaymentTransactionRouterType() {
        return paymentTransactionRouterType;
    }

    public void setPaymentTransactionRouterType(String paymentTransactionRouterType) {
        this.paymentTransactionRouterType = paymentTransactionRouterType == null ? null : paymentTransactionRouterType.trim();
    }

    public String getPaymentTransactionRouterName() {
        return paymentTransactionRouterName;
    }

    public void setPaymentTransactionRouterName(String paymentTransactionRouterName) {
        this.paymentTransactionRouterName = paymentTransactionRouterName == null ? null : paymentTransactionRouterName.trim();
    }
}