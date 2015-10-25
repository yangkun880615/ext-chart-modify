package com.chart.cn.web.controller;

import java.io.PrintWriter;

import javax.servlet.http.HttpServletRequest;





import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.mvc.multiaction.MultiActionController;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import com.chart.cn.exception.BusinessException;
import com.chart.cn.exception.ControllerExeception;
import com.chart.cn.exception.ParameterException;
import com.chart.cn.exception.SessionTimeoutException;


public class BaseController  extends MultiActionController {
	

	
	protected   Logger logger = LoggerFactory.getLogger(getClass());  

	//全局base_path
	protected String BASE_PATH="";
	
	/** 基于@ExceptionHandler异常处理 */
	//(value = { BusinessException.class, ParameterException.class, Exception.class})
	@ExceptionHandler
	public String exp(HttpServletRequest request, HttpServletResponse response,Exception ex) throws Exception {
		BASE_PATH=request.getContextPath();
		System.out.println("this is"+BASE_PATH);
		request.setAttribute("ex", ex);
		
		// 根据不同错误转向不同页面
		if(ex instanceof BusinessException) {
			ex.printStackTrace();
			return "exception/error-business";
		}else if(ex instanceof ParameterException) {
			ex.printStackTrace();
			return "exception/error-parameter";
		}
		else if(ex instanceof ControllerExeception) {
			return "exception/error-controller";
		} else if(ex instanceof SessionTimeoutException) {
		       logger.info("登录超时！！");
			return UrlBasedViewResolver.REDIRECT_URL_PREFIX+"/login";
		}
		else {
			ex.printStackTrace();
			return "exception/error";
		}
	}
	

}