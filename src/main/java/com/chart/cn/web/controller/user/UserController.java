/**  
* Project ：              ext-chart-modify  
* FileName ：           com.chart.cn.web.controller.user.UserController.java 
* Description：
* @version:     1.0
* Create at ：        2014-9-16 下午1:43:36  
*
* Modification History : 
* Date                    Author       Version         Description
*------------------------------------------------------
* 2014-9-16 下午1:43:36         yk        1.0              
*/  

package com.chart.cn.web.controller.user;


import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chart.cn.constant.ComonConstant;
import com.chart.cn.entity.resconfig.SysUser;
import com.chart.cn.exception.ControllerExeception;
import com.chart.cn.service.resconfig.sysuser.SysUserService;
import com.chart.cn.web.controller.BaseController;

@Controller
@RequestMapping("user")
public class UserController extends BaseController {

	@Resource
	private SysUserService  sysUserService;
	
	/**
	 * 
		*
			* Description :如果用户不登陆直接访问主页回返回登录页面
			* @author :     qly
			* @Version      1.0
			* @param request
			* @param response
			* @param model
			* @return
			* date :        2015年1月6日
			* MethodName :  index
			* ReturnType :  String
			* --------------------------------------------------
			* 修改人          修改日期                         修改描述
			* qly             2015年1月6日                   创建
			* --------------------------------------------------
	 */
	@RequestMapping(value = "")
	public String index(HttpServletRequest request,HttpServletResponse response,
			ModelMap model) {
		 return "front/ftl/user/user-index";
		
	}
	
	@RequestMapping(value="view-friends",method = RequestMethod.GET,produces =ComonConstant.RESPONSE_BODY_PRODUCES_JSON)
	@ResponseBody
	public String queryViewSysDeptUserList(HttpServletRequest request,HttpServletResponse response,
			ModelMap model) throws ControllerExeception {
		//false  表示不显示 用户的复选框
		String stringJson=sysUserService.queryForSysUserFriendsJson(false);
		return stringJson;
	}
	
	
	@RequestMapping(value="session-show",produces =ComonConstant.RESPONSE_BODY_PRODUCES_JSON)
	@ResponseBody
	public SysUser querySessionId(HttpServletRequest request,HttpServletResponse response,
			ModelMap model) throws ControllerExeception {
		SysUser user = (SysUser) request.getSession().getAttribute("user");  
		return user;
	}
}
  


