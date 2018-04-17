package com.apical.oddm.web.framework.listener;

import javax.servlet.ServletContextEvent;

import org.springframework.web.context.ContextLoaderListener;

public class ApplicationListener extends ContextLoaderListener {

	/**
	 * 初始一些系统变量，一定要程序执行前执行
	 * 禁止提前初始化日志（禁止使用log）
	 */
	@Override
	public void contextInitialized(ServletContextEvent event) {
		System.setProperty("druid.logType", "slf4j");//设置druid日志使用slfj
		System.setProperty("org.jboss.logging.provider", "slf4j"); //hibernate的日志也使用slf4j
		System.out.println("初始化spring容器前设置系统参数");
		super.contextInitialized(event); //会初始化ContextLoaderListener spring的容器，就不需要配置ContextLoaderListener监听了
		//Quartz.main(null); //启动定时任务， 改为spring容器管理配置文件
	}
}
