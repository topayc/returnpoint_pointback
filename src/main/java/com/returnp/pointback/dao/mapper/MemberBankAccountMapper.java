package com.returnp.pointback.dao.mapper;

import com.returnp.pointback.model.MemberBankAccount;

public interface MemberBankAccountMapper {
    int deleteByPrimaryKey(Integer memberBankAccountNo);

    int insert(MemberBankAccount record);

    int insertSelective(MemberBankAccount record);

    MemberBankAccount selectByPrimaryKey(Integer memberBankAccountNo);

    int updateByPrimaryKeySelective(MemberBankAccount record);

    int updateByPrimaryKey(MemberBankAccount record);
}