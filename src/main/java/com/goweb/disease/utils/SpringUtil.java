package com.goweb.disease.utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;

/**
 * 
 * spring工具类
 * 
 */
public class SpringUtil {
	
	private static ApplicationContext applicationContext = null;
	
	private static final Logger log = LogManager.getLogger(SpringUtil.class);
	
	//spring初始化
	public static void init(String path){
		applicationContext = new ClassPathXmlApplicationContext(path);
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	public static void setApplicationContext(ApplicationContext applicationContext) {
		SpringUtil.applicationContext = applicationContext;
	}
	
	public static Object getBean(String name){
		return applicationContext.getBean(name);
	}
	


	
	
}
