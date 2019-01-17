package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.AdminRole;

public interface AdminRoleMapper {
    int deleteByPrimaryKey(Integer adminRoleNo);

    int insert(AdminRole record);

    int insertSelective(AdminRole record);

    AdminRole selectByPrimaryKey(Integer adminRoleNo);

    int updateByPrimaryKeySelective(AdminRole record);

    int updateByPrimaryKey(AdminRole record);
}