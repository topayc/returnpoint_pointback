package com.returnp.pointback.model;

import com.returnp.pointback.dto.QueryCondition;
import java.util.Date;

public class PointCouponPointbackRecord extends QueryCondition {
    private Integer pointCouponPointbackRecordNo;

	private Integer memberNo;

	private Integer pointCouponTransactionNo;

	private Integer nodeNo;

	private String nodeType;

	private Float accRate;

	private Float accPoint;

	private Date createTime;

	private Date updateTime;

	public Integer getPointCouponPointbackRecordNo() {
		return pointCouponPointbackRecordNo;
	}

	public void setPointCouponPointbackRecordNo(Integer pointCouponPointbackRecordNo) {
		this.pointCouponPointbackRecordNo = pointCouponPointbackRecordNo;
	}

	public Integer getMemberNo() {
		return memberNo;
	}

	public void setMemberNo(Integer memberNo) {
		this.memberNo = memberNo;
	}

	public Integer getPointCouponTransactionNo() {
		return pointCouponTransactionNo;
	}

	public void setPointCouponTransactionNo(Integer pointCouponTransactionNo) {
		this.pointCouponTransactionNo = pointCouponTransactionNo;
	}

	public Integer getNodeNo() {
		return nodeNo;
	}

	public void setNodeNo(Integer nodeNo) {
		this.nodeNo = nodeNo;
	}

	public String getNodeType() {
		return nodeType;
	}

	public void setNodeType(String nodeType) {
		this.nodeType = nodeType == null ? null : nodeType.trim();
	}

	public Float getAccRate() {
		return accRate;
	}

	public void setAccRate(Float accRate) {
		this.accRate = accRate;
	}

	public Float getAccPoint() {
		return accPoint;
	}

	public void setAccPoint(Float accPoint) {
		this.accPoint = accPoint;
	}

	public Date getCreateTime() {
		return createTime;
	}

	public void setCreateTime(Date createTime) {
		this.createTime = createTime;
	}

	public Date getUpdateTime() {
		return updateTime;
	}

	public void setUpdateTime(Date updateTime) {
		this.updateTime = updateTime;
	}

}