package com.returnp.pointback.web.message;

import java.util.Locale;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceAware;
import org.springframework.stereotype.Component;

@Component
public class MessageUtils implements MessageSourceAware {

	private MessageSource messageSource;
	
	@Override
	public void setMessageSource(MessageSource messageSource) {
		this.messageSource = messageSource;
	}

	public String getMessage(String code){
		return this.messageSource.getMessage(code, null,  Locale.getDefault());
	}
	
	public String getMessage(String code, Object[] arguments){
		return this.messageSource.getMessage(code, arguments, Locale.getDefault());
	}
}
