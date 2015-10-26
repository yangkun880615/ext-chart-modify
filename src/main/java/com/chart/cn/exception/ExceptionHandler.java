/**  
* Project ：              ext-chart-modify  
* FileName ：           com.chart.cn.exception.ExceptionHandler.java 
* Description：
* @version:     1.0
* Create at ：        2014-9-15 下午6:09:48  
*
* Modification History : 
* Date                    Author       Version         Description
*------------------------------------------------------
* 2014-9-15 下午6:09:48         yk        1.0              
*/  

package com.chart.cn.exception;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.web.servlet.HandlerExceptionResolver;
import org.springframework.web.servlet.ModelAndView;



public class ExceptionHandler  implements HandlerExceptionResolver {

	@Override
	public ModelAndView resolveException(HttpServletRequest request,
			HttpServletResponse response, Object handler, Exception ex) {

		Map<String, Object> model = new HashMap<String, Object>();
		model.put("ex", ex);
		
		// 根据不同错误转向不同页面
		if(ex instanceof BusinessException) {
			return new ModelAndView("error-business", model);
		}else if(ex instanceof ParameterException) {
			return new ModelAndView("error-parameter", model);
		} else {
			return new ModelAndView("error", model);
		}
	
	}

}
  


