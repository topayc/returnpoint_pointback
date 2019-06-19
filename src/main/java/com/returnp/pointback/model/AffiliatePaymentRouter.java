package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;

public class AffiliatePaymentRouter extends QueryCondition {
    private Integer affiliatePaymentRouterNo;

    private Integer affiliateNo;

    private Integer paymentRouterNo;

    public Integer getAffiliatePaymentRouterNo() {
        return affiliatePaymentRouterNo;
    }

    public void setAffiliatePaymentRouterNo(Integer affiliatePaymentRouterNo) {
        this.affiliatePaymentRouterNo = affiliatePaymentRouterNo;
    }

    public Integer getAffiliateNo() {
        return affiliateNo;
    }

    public void setAffiliateNo(Integer affiliateNo) {
        this.affiliateNo = affiliateNo;
    }

    public Integer getPaymentRouterNo() {
        return paymentRouterNo;
    }

    public void setPaymentRouterNo(Integer paymentRouterNo) {
        this.paymentRouterNo = paymentRouterNo;
    }
}