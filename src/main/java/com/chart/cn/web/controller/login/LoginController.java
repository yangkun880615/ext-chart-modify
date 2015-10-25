/**  
* Project ：              asset-cmp  
* FileName ：           com.cntv.cn.web.controller.login.LoginController.java 
* Description：
* @version:     1.0
* Create at ：        2014-9-15 下午10:15:15  
*
* Modification History : 
* Date                    Author       Version         Description
*------------------------------------------------------
* 2014-9-15 下午10:15:15         yk        1.0              
*/  

package com.chart.cn.web.controller.login;

import java.io.UnsupportedEncodingException;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import nl.justobjects.pushlet.core.Dispatcher;
import nl.justobjects.pushlet.core.Event;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.view.UrlBasedViewResolver;

import com.chart.cn.constant.ComonConstant;
import com.chart.cn.core.Protocol;
import com.chart.cn.entity.resconfig.SysUser;
import com.chart.cn.service.resconfig.sysuser.SysUserService;
import com.chart.cn.util.QEncodeUtil;
import com.chart.cn.web.controller.BaseController;
@Controller
@RequestMapping("login")
public class LoginController  extends BaseController {
	@Resource
	SysUserService sysUserService;

	/**
	 * 
	* Description :  登录页面
	* @author :     杨坤
	* @Version      1.0
	* date :        2014-9-16
	* MethodName :  index
	* ReturnType :  String 
	* --------------------------------------------------
	* 修改人          修改日期                                  修改描述
	* yk   2014-9-16          创建
	* --------------------------------------------------
	 */
	@RequestMapping(value = "")
	public String index(HttpServletRequest request,HttpServletResponse response,
			ModelMap model)  
	{
		
		return "front/ftl/login/login-index";
		
	}
	

	@RequestMapping(value = "check")
	@ResponseBody
	public String check(HttpServletRequest request,HttpServletResponse response,
			ModelMap model)  
	{
		SysUser user = (SysUser) request.getSession().getAttribute("user");  
        if(user != null) {
		return "{\"login\":true}";
        }else{
        	return "{\"login\":false}";
      }
		
	}
	
	/**
	 * 登录提交页面
	* Description :  
	* @author :     钱丽颖
	* @Version      1.0
	* date :        2014-12-15
	* MethodName :  submitLogin
	* ReturnType :  String 
	* --------------------------------------------------
	* 修改人          修改日期                                  修改描述
	* qly      2014-12-15                      创建
	* --------------------------------------------------
	 */

	@RequestMapping(value = "submit-ajax",produces =ComonConstant.RESPONSE_BODY_PRODUCES_TEXT)
	@ResponseBody
	public String submitLogin(HttpServletRequest request,HttpServletResponse response,
			@RequestParam(required = true) String userName,
			@RequestParam(required = true)String password,ModelMap model)  
	{


		StringBuffer msg = new StringBuffer("");
		String passwordkey="";
	    try{
	    	passwordkey=QEncodeUtil.aesEncrypt(password, ComonConstant.SYS_USER_PASSWORD_KEY);
		}catch(Exception e){
			e.printStackTrace();
		}
		SysUser user=sysUserService.verifyloginUser(userName, passwordkey);
	   
		if(user!=null&&user.getId()>0)
		{						
			request.getSession().setAttribute("userId", user.getId());
			request.getSession().setAttribute("userName", user.getUserName());
		    request.getSession().setAttribute("user", user);
			msg.append("{\"success\":true,\"msg\":\"登录成功\"}");
						
		}		
		else{
			msg.append("{\"success\":false,\"msg\":\"用户名或者密码错误\"}");
		}			
		return msg.toString();				
		
	}
	
	/**
	 * 
	* Description :  登录成功页面
	* @author :     杨坤
	* @Version      1.0
	* date :        2014-9-16
	* MethodName :  loginSuccess
	* ReturnType :  String  
	* --------------------------------------------------
	* 修改人          修改日期                                  修改描述
	* yk   2014-9-16          创建
	* --------------------------------------------------  
	 */
	@RequestMapping(value = "/seccess")
	public String loginSuccess(HttpServletRequest request,HttpServletResponse response,ModelMap model)  {
			return UrlBasedViewResolver.REDIRECT_URL_PREFIX+"/user";
	
	}
	@RequestMapping(value = "/welcome")
	public String welcome(HttpServletRequest request,HttpServletResponse response,ModelMap model)  {
			
			return "welcome";
	
	}
	
	@RequestMapping(value = "/notice")
	public void notice(HttpServletRequest request,HttpServletResponse response,ModelMap model) throws UnsupportedEncodingException  {
			
		//更新在线的用户列表,通知好友
		Event event=Event.createDataEvent("/notice/friends");
		SysUser userInfo = (SysUser)request.getSession().getAttribute("user");
		
		if(userInfo != null){
			event.setField("userName",new String(userInfo.getUserName().toString().getBytes("utf-8"),"iso-8859-1"));
			event.setField("message",new String((userInfo.getUserName()+"上线了").getBytes("utf-8"),"iso-8859-1"));
			event.setField("userId",new String(userInfo.getId().toString()));
			event.setField("from",new String(userInfo.getUserName().getBytes("utf-8"),"iso-8859-1"));
			event.setField(Protocol.P_SUBJECT, "/notice/friends");
		}
		Dispatcher.getInstance().multicast(event);//群发
	
	}

}
  


