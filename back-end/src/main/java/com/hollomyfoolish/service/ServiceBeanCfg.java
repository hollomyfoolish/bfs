package com.hollomyfoolish.service;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;

@Configurable
@ComponentScan(basePackageClasses = ServiceBeanCfg.class)
public class ServiceBeanCfg {

}
