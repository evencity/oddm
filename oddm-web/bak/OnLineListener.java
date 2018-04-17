package com.apical.oddm.web.framework.listener;

import java.util.concurrent.atomic.AtomicInteger;

import javax.servlet.http.HttpSession;
import javax.servlet.http.HttpSessionEvent;
import javax.servlet.http.HttpSessionListener;

import com.apical.oddm.web.framework.constant.GlobalConstant;
 
public class OnLineListener implements HttpSessionListener{ 

	private static AtomicInteger currUsersCount = new AtomicInteger(0);
	
     /***********
     * 创建session时调用
     */ 
     public void sessionCreated(HttpSessionEvent event) { 
    	 //在登录的时候 在线人数再加一，这里不统计
         System.err.println("sessionCreated当前用户人数："+currUsersCount.get()); 
     } 
 
     /************
     * 销毁session时调用
     */ 
     public void sessionDestroyed(HttpSessionEvent event) { 
         System.err.println("销毁session......"); 
         HttpSession session =event.getSession(); 
         System.err.println("SESSION_INFO "+session.getAttribute(GlobalConstant.SESSION_INFO));
         if (session.getAttribute(GlobalConstant.SESSION_INFO) != null) {//用户没点击退出、直接关闭浏览器，这里不为空。直接点击注销session为空。
             decrementAndGet();//indexController 退出方法也调用
             System.err.println("sessionDestroyed当前用户人数："+currUsersCount.get()); 
         }
    }

	/**
	 * 增加在线用户数量
	 * @return
	 */
	public static int incrementAndGet() {
		return currUsersCount.incrementAndGet();
	}

	/**
	 * 减少在线用户数量
	 * @return
	 */
	public static int decrementAndGet() {
		if (currUsersCount.get() == 0) return 0;
		return currUsersCount.decrementAndGet();
	} 
 
	public static int get() {
		return currUsersCount.get();
	} 
}