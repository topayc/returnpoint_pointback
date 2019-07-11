package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.MemberPlainPassword;

public interface MemberPlainPasswordMapper {
    int deleteByPrimaryKey(Integer memberPlainPasswordNo);

    int insert(MemberPlainPassword record);

    int insertSelective(MemberPlainPassword record);

    MemberPlainPassword selectByPrimaryKey(Integer memberPlainPasswordNo);

    int updateByPrimaryKeySelective(MemberPlainPassword record);

    int updateByPrimaryKey(MemberPlainPassword record);
}