package com.hollomyfoolish.web.ctxconfig;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;

@Configurable
@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {
	
//	INGORE_PATH_PATTERNS = new String[]{
//			ctxPath + "/saml2/**/*",
//			ctxPath + "/oauth/**/*",
//			ctxPath + "/index.html",
//			ctxPath + "/__index_test__.html",
//			ctxPath + "/api/**/*",
//			ctxPath + "/"
//		};
	
    @Override
    protected void configure(HttpSecurity http) throws Exception {
    	// TODO config spring security here
    	http
    		.csrf().disable();
//        http
//            .authorizeRequests()
//                .antMatchers("/saml2/**/*", "/index.html", "/", "/__index_test__.html").permitAll()
//                .anyRequest().authenticated()
//                .and()
//            .csrf().disable()
//            .logout().disable()
//            .antMatcher("/oauth/authorize")
//            	.addFilterAfter(afterAuthCodeCreatedFilter(), SessionManagementFilter.class);
    }
}
