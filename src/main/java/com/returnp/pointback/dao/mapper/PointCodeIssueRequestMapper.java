package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.PointCodeIssueRequest;

public interface PointCodeIssueRequestMapper {
    int deleteByPrimaryKey(Integer pointCodeIssueRequestNo);

    int insert(PointCodeIssueRequest record);

    int insertSelective(PointCodeIssueRequest record);

    PointCodeIssueRequest selectByPrimaryKey(Integer pointCodeIssueRequestNo);

    int updateByPrimaryKeySelective(PointCodeIssueRequest record);

    int updateByPrimaryKey(PointCodeIssueRequest record);
}