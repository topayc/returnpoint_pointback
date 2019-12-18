package com.returnp.pointback.dto.command;

import com.returnp.pointback.model.PointCodeIssue;

public class PointCodeIssueCommand extends PointCodeIssue{
	private String accTargetRange;

	public String getAccTargetRange() {
		return accTargetRange;
	}

	public void setAccTargetRange(String accTargetRange) {
		this.accTargetRange = accTargetRange;
	}
	
}
