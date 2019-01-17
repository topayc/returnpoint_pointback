package com.returnp.pointback.model;

import java.math.BigDecimal;

import com.returnp.pointback.dto.QueryCondition;

public class DashBoardChart extends QueryCondition {
    private String labels;

    private BigDecimal greenPoint;

    private BigDecimal conversionPoint;

    private BigDecimal redPoint;

    private BigDecimal accRedPoint;

	public String getLabels() {
		return labels;
	}

	public void setLabels(String labels) {
		this.labels = labels;
	}

	public BigDecimal getGreenPoint() {
		return greenPoint;
	}

	public void setGreenPoint(BigDecimal greenPoint) {
		this.greenPoint = greenPoint;
	}

	public BigDecimal getConversionPoint() {
		return conversionPoint;
	}

	public void setConversionPoint(BigDecimal conversionPoint) {
		this.conversionPoint = conversionPoint;
	}

	public BigDecimal getRedPoint() {
		return redPoint;
	}

	public void setRedPoint(BigDecimal redPoint) {
		this.redPoint = redPoint;
	}

	public BigDecimal getAccRedPoint() {
		return accRedPoint;
	}

	public void setAccRedPoint(BigDecimal accRedPoint) {
		this.accRedPoint = accRedPoint;
	}
}