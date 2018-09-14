package com.hollomyfoolish.bf;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.Import;

import com.hollomyfoolish.repo.RepoBeanCfg;
import com.hollomyfoolish.service.ServiceBeanCfg;

@Configurable
@Import({
	RepoBeanCfg.class,
	ServiceBeanCfg.class
})
public class BeanFactory {

}
