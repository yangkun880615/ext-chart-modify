package com.chart.cn.web.Interceptor;


import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;




/**s
 * 前台拦截器
 * <p/>
 * 处理Config、和登录信息
 */
public class FrontContextInterceptor extends HandlerInterceptorAdapter {

    


	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler)
			throws ServletException {
			return true;
//		String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");    
// 
//        if(requestUrl.contains("login")) {    
//            return true;    
//        }    
//  

			

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * org.springframework.web.servlet.handler.HandlerInterceptorAdapter#postHandle
	 * (javax.servlet.http.HttpServletRequest,
	 * javax.servlet.http.HttpServletResponse, java.lang.Object,
	 * org.springframework.web.servlet.ModelAndView)
	 */
	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//得到当前路径的id
		String uri = request.getRequestURI();
		if (modelAndView != null) {
			    ModelMap map = modelAndView.getModelMap();
				map.addAttribute("uri", uri);
				map.addAttribute("welcom",getNowTime());
		super.postHandle(request, response, handler, modelAndView);
		}
	}
	
	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {

	}
	


	
	//适配现在的时间
	private String getNowTime(){

			return "hello";
	

	}
	
}
