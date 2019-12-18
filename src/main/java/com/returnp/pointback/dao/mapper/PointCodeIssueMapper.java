package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.PointCodeIssue;

public interface PointCodeIssueMapper {
    int deleteByPrimaryKey(Integer pointCodeIssueNo);

    int insert(PointCodeIssue record);

    int insertSelective(PointCodeIssue record);

    PointCodeIssue selectByPrimaryKey(Integer pointCodeIssueNo);

    int updateByPrimaryKeySelective(PointCodeIssue record);

    int updateByPrimaryKey(PointCodeIssue record);
}