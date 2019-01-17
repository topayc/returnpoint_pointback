package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.Branch;

public interface BranchMapper {
    int deleteByPrimaryKey(Integer branchNo);

    int insert(Branch record);

    int insertSelective(Branch record);

    Branch selectByPrimaryKey(Integer branchNo);

    int updateByPrimaryKeySelective(Branch record);

    int updateByPrimaryKey(Branch record);
}