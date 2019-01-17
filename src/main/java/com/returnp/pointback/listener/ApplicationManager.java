package com.returnp.pointback.listener;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;


@Component
public class ApplicationManager implements ApplicationListener<ApplicationEvent>{
	

	@Override
	public void onApplicationEvent(ApplicationEvent event) {
		
	}
}
