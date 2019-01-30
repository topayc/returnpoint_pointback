package com.returnp.pointback.web.controller;

import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.TypeMismatchException;
import org.springframework.dao.DataAccessException;
import org.springframework.web.bind.MissingServletRequestParameterException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.returnp.pointback.common.AppConstants;
import com.returnp.pointback.dto.response.ReturnpBaseResponse;
import com.returnp.pointback.dto.response.ObjectResponse;

public class ApplicationController {
	
	protected void setRespone(ReturnpBaseResponse res, String resultCode, String result,String message ){ 
		res.setResult(result);
		res.setResultCode(resultCode);	
		res.setMessage(message);
	}
	
	protected void setSuccessRespone(ReturnpBaseResponse res){ 
		res.setResult(AppConstants.ResponseResult.SUCCESS);
		res.setResultCode(AppConstants.ResponsResultCode.SUCCESS);	
		res.setMessage("요청이 처리 되었습니다");
	}
	
	protected void setSuccessRespone(ReturnpBaseResponse res, String mes){ 
		res.setResult(AppConstants.ResponseResult.SUCCESS);
		res.setResultCode(AppConstants.ResponsResultCode.SUCCESS);	
		res.setMessage(mes);
	}
	
	protected void setErrorRespone(ReturnpBaseResponse res, String mes){ 
		res.setResult(AppConstants.ResponseResult.ERROR);
		res.setResultCode(AppConstants.ResponsResultCode.ERROR);	
		res.setMessage(mes);
	}
	
	protected void setErrorRespone(ReturnpBaseResponse res){ 
		res.setResult(AppConstants.ResponseResult.FAILED);
		res.setResultCode(AppConstants.ResponsResultCode.ERROR);	
		res.setMessage("요청에러");
	}
	
	@ExceptionHandler(TypeMismatchException.class)
	@ResponseBody
	protected ReturnpBaseResponse typeMismatchExceptionHandler(TypeMismatchException e,
			HttpServletResponse response){
		ObjectResponse<String> res = new ObjectResponse<String>();
		res.setData(e.getMessage());
		this.setErrorRespone(res);
		return res;
	}
	
	@ExceptionHandler(MissingServletRequestParameterException.class)
	@ResponseBody
	protected ReturnpBaseResponse missingServletRequestParameterExceptionHandler(
			MissingServletRequestParameterException e, 
			HttpServletResponse response){
		ObjectResponse<String> res = new ObjectResponse<String>();
		res.setData(e.getMessage());
		this.setErrorRespone(res);
		return res;
	}
	
	@ExceptionHandler(DataAccessException.class)
	@ResponseBody
	protected  ReturnpBaseResponse dataAccessExceptionHandler(DataAccessException e,
			HttpServletResponse response ) {
		ObjectResponse<String> res = new ObjectResponse<String>();
		res.setData(e.getMessage());
		this.setErrorRespone(res);
		return res;
	 }
}
