/**  
* Project ：              ext-chart-modify  
* FileName ：           com.chart.cn.web.Interceptor.SessionTimeoutInterceptor.java 
* Description：
* @version:     1.0
* Create at ：        2014-9-19 上午11:18:15  
*
* Modification History : 
* Date                    Author       Version         Description
*------------------------------------------------------
* 2014-9-19 上午11:18:15         yk        1.0              
*/  

package com.chart.cn.web.Interceptor;


import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.ui.ModelMap;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.chart.cn.core.SysUserThread;
import com.chart.cn.entity.resconfig.SysUser;
import com.chart.cn.exception.SessionTimeoutException;


public class SessionTimeoutInterceptor extends HandlerInterceptorAdapter {

	public String[] allowUrls;//还没发现可以直接配置不拦截的资源，所以在代码里面来排除  
    
    public void setAllowUrls(String[] allowUrls) {  
        this.allowUrls = allowUrls;  
    }  
	@Override
	public boolean preHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler) throws Exception {
		String requestUrl = request.getRequestURI().replace(request.getContextPath(), "");    
 
        if(null != allowUrls && allowUrls.length>=1) { 
            for(String url : allowUrls) {
                if(requestUrl.contains(url)) {    
                    return true;    
                }    
            }  
		}
        SysUser user = (SysUser) request.getSession().getAttribute("user");  
        if(user == null) {

        	if (request.getHeader("x-requested-with") != null
        			 && request.getHeader("x-requested-with").equalsIgnoreCase("XMLHttpRequest")){
//    	        PrintWriter writer = response.getWriter();
//    			writer.print("{\"login\":false}");
//    	        IOUtils.closeQuietly(writer);
    			//throw new RuntimeException("error...");
    	        return false;
        	}else
        	  throw new SessionTimeoutException();
        }else{
        	 SysUserThread.set(user);
        	 return true;
        }

	}

	@Override
	public void postHandle(HttpServletRequest request,
			HttpServletResponse response, Object handler,
			ModelAndView modelAndView) throws Exception {
		//得到当前路径的id
		String uri = request.getRequestURI();
		if (modelAndView != null) {
			    ModelMap map = modelAndView.getModelMap();
				map.addAttribute("uri", uri);
		super.postHandle(request, response, handler, modelAndView);
		}
		
	}

	@Override
	public void afterCompletion(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex)
			throws Exception {
		SysUserThread.remove();
		
	}

}
  


