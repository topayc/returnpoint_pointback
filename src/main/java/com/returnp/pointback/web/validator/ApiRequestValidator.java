package com.returnp.pointback.web.validator;

import org.springframework.stereotype.Component;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;

import com.returnp.pointback.dto.command.api.ApiRequest;



@Component
public class ApiRequestValidator implements Validator{

	@Override
	public boolean supports(Class<?> clazz) {
		return ApiRequest.class.isAssignableFrom(clazz);
	}

	@Override
	public void validate(Object target, Errors errors) {
		ApiRequest req = (ApiRequest)target;
	}

}
