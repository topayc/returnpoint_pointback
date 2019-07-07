package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;

public class PaymentTransactionRouter extends QueryCondition {
    private Integer paymentTransactionRouterNo;

    private Integer paymentTransactionNo;

    private Integer paymentRouterNo;

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

    public Integer getPaymentRouterNo() {
        return paymentRouterNo;
    }

    public void setPaymentRouterNo(Integer paymentRouterNo) {
        this.paymentRouterNo = paymentRouterNo;
    }
}