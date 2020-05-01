package com.developer.farmington.app.ws;

import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.stereotype.Component;

@Component
public class SpringApplicationContext implements ApplicationContextAware {
	private static ApplicationContext CONTEXT;

	@Override
	public void setApplicationContext(ApplicationContext context) throws BeansException {
		CONTEXT = context;		
	}
	
	public static Object getBean(String beanName) {
		return CONTEXT.getBean(beanName);
	}	

}
