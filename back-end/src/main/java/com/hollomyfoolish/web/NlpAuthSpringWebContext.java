package com.hollomyfoolish.web;

import org.springframework.context.ApplicationContextInitializer;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.web.context.support.AnnotationConfigWebApplicationContext;

public class NlpAuthSpringWebContext<C extends ConfigurableApplicationContext> implements ApplicationContextInitializer<C>{

	@Override
	public void initialize(C applicationContext) {
		if(applicationContext instanceof AnnotationConfigWebApplicationContext){
			AnnotationConfigWebApplicationContext ac = (AnnotationConfigWebApplicationContext)applicationContext;
			ac.register(WebBeanFactory.class);
		}
	}
}
