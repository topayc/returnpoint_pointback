package com.returnp.pointback.service.interfaces;

import org.springframework.transaction.annotation.Transactional;

import com.returnp.pointback.dto.CiderObject;
import com.returnp.pointback.dto.response.ReturnpBaseResponse;

@Transactional
public interface CiderPointbackHandleService {

	public ReturnpBaseResponse accumulate(CiderObject cider);

	public ReturnpBaseResponse cancelAccumulate(CiderObject cider);
}
