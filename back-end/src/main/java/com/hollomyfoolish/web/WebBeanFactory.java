package com.hollomyfoolish.web;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.web.servlet.config.annotation.DefaultServletHandlerConfigurer;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.hollomyfoolish.web.controller.ControllerBeanCfg;
import com.hollomyfoolish.web.interceptor.LoggerInterceptor;

@Configuration
@Import({
	ControllerBeanCfg.class,
	WebCommonBeanFacotory.class
})
@EnableWebMvc
public class WebBeanFactory implements WebMvcConfigurer  {

	@Bean
	public LoggerInterceptor loggerInterceptor(){
		return new LoggerInterceptor();
	}
	
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
	    registry.addInterceptor(loggerInterceptor());
	}
	
	@Override 
	public void addResourceHandlers(ResourceHandlerRegistry registry) {
	    registry.addResourceHandler("/**").addResourceLocations("/"); 
	}
	
	@Override
    public void configureDefaultServletHandling(DefaultServletHandlerConfigurer configurer) {
        configurer.enable("bfsDefServlet");
    }
}
