package com.hollomyfoolish.repo;

import org.springframework.beans.factory.annotation.Configurable;
import org.springframework.context.annotation.ComponentScan;

@Configurable
@ComponentScan(basePackageClasses = RepoBeanCfg.class)
public class RepoBeanCfg {

}
