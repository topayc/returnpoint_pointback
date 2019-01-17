package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.MembershipRequest;

public interface MembershipRequestMapper {
    int deleteByPrimaryKey(Integer membershipRequestNo);

    int insert(MembershipRequest record);

    int insertSelective(MembershipRequest record);

    MembershipRequest selectByPrimaryKey(Integer membershipRequestNo);

    int updateByPrimaryKeySelective(MembershipRequest record);

    int updateByPrimaryKey(MembershipRequest record);
}