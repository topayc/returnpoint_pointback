package com.returnp.pointback.service.interfaces;

import org.springframework.transaction.annotation.Transactional;

import com.returnp.pointback.common.DataMap;
import com.returnp.pointback.dto.response.ReturnpBaseResponse;

@Transactional
public interface ApiPointbackHandleService {

	public ReturnpBaseResponse accumulate(DataMap dataMap);

	public ReturnpBaseResponse cancelAccumulate(DataMap dataMap);
	
	
}
