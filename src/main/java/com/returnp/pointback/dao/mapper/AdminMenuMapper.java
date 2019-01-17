package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.AdminMenu;

public interface AdminMenuMapper {
    int deleteByPrimaryKey(Integer adminMenuNo);

    int insert(AdminMenu record);

    int insertSelective(AdminMenu record);

    AdminMenu selectByPrimaryKey(Integer adminMenuNo);

    int updateByPrimaryKeySelective(AdminMenu record);

    int updateByPrimaryKey(AdminMenu record);
}