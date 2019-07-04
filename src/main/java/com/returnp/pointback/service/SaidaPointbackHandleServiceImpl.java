package com.returnp.pointback.service;

import org.apache.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Service;
import org.springframework.transaction.interceptor.TransactionAspectSupport;

import com.returnp.pointback.common.AppConstants;
import com.returnp.pointback.common.ResponseUtil;
import com.returnp.pointback.common.ReturnpException;
import com.returnp.pointback.dto.SaidaObject;
import com.returnp.pointback.dto.response.ReturnpBaseResponse;
import com.returnp.pointback.model.PaymentTransaction;
import com.returnp.pointback.service.interfaces.SaidaPointbackHandleService;
import com.returnp.pointback.web.message.MessageUtils;

@Service
/*@PropertySource("classpath:/messages.properties")*/
public class SaidaPointbackHandleServiceImpl implements SaidaPointbackHandleService {
	
	private Logger logger = Logger.getLogger(SaidaPointbackHandleServiceImpl.class);

	@Autowired MessageUtils messageUtils;
	@Autowired Environment env;
	
	@Override
	public ReturnpBaseResponse accumulate(SaidaObject saida) {
		ReturnpBaseResponse res = new ReturnpBaseResponse();
		try {
			return res;
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			res = e.getBaseResponse();
			return res;
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR, "2000", this.messageUtils.getMessage("pointback.message.inner_server_error"));
			return res;
		}
	}

	@Override
	public ReturnpBaseResponse cancelAccumulate(SaidaObject saida) {
		// TODO Auto-generated method stub
		ReturnpBaseResponse res = new ReturnpBaseResponse();
		try {
			return res;
		}catch(ReturnpException e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			res = e.getBaseResponse();
			return res;
		}catch(Exception e) {
			e.printStackTrace();
			TransactionAspectSupport.currentTransactionStatus().setRollbackOnly();
			ResponseUtil.setResponse(res, ResponseUtil.RESPONSE_ERROR, "2000", this.messageUtils.getMessage("pointback.message.inner_server_error"));
			return res;
		}
	}
	


}
