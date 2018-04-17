package com.apical.oddm.web.framework.exception;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.multiaction.NoSuchRequestHandlingMethodException;


public class ExceptionResolver implements HandlerExceptionResolver  {

	private static final Logger log = LoggerFactory.getLogger(ExceptionResolver.class);
	@Override
	public ModelAndView resolveException(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) {
		// TODO Auto-generated method stub
		ModelAndView modelAndView = new ModelAndView();
		log.error("系统异常：",ex);
		ex.printStackTrace();
		System.out.println("。。。。ExceptionResolver。。。。");
		if(ex.getClass() == NoSuchRequestHandlingMethodException.class){
			modelAndView.addObject("ex", ex);
			return modelAndView;
		}else {
			modelAndView.addObject("ex", ex);
			return modelAndView;
		}
	}
	/*private String getStackTraceAsString(Exception e) {
	    // StringWriter将包含堆栈信息
	    StringWriter stringWriter = new StringWriter();
	    //必须将StringWriter封装成PrintWriter对象，
	    //以满足printStackTrace的要求
	    PrintWriter printWriter = new PrintWriter(stringWriter);
	    //获取堆栈信息
	    e.printStackTrace(printWriter);
	    //转换成String，并返回该String
	    StringBuffer error = stringWriter.getBuffer();
	    return error.toString();
	}*/

}
