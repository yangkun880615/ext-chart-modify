package com.chart.cn.web.controller.chart;



import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;















import nl.justobjects.pushlet.core.Command;
import nl.justobjects.pushlet.core.Dispatcher;
import nl.justobjects.pushlet.core.Event;
import nl.justobjects.pushlet.core.EventParser;
import nl.justobjects.pushlet.core.Session;
import nl.justobjects.pushlet.core.SessionManager;
import nl.justobjects.pushlet.util.Log;
import nl.justobjects.pushlet.util.Servlets;

import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.chart.cn.core.SysUserThread;
import com.chart.cn.entity.resconfig.SysUser;

import nl.justobjects.pushlet.core.Protocol;;

@Controller
@RequestMapping("chart")
public class chartController  {

	@RequestMapping(value = "")
	@ResponseBody
	public int index(String message,String userId,String userName,HttpServletRequest request,HttpServletResponse response,
			ModelMap model) throws UnsupportedEncodingException {
		String eventType = Servlets.getParameter(request,com.chart.cn.core.Protocol.P_EVENT);
//		Event event1 = null;
//		try {
//			// Create Event by parsing XML from input stream.
//			event1 = EventParser.parse(new InputStreamReader(request.getInputStream()));
//
//			// Always must have an event type
//			if (event1.getEventType() == null) {
//				Log.warn("Pushlet.doPost(): bad request, no event specified");
//				response.sendError(HttpServletResponse.SC_BAD_REQUEST, "No eventType specified");
//				return -2;
//			}
//
//
//		} catch (Throwable t) {
//			// Error creating event
//			Log.warn("Pushlet:  Error creating event in doPost(): ", t);
//			response.setStatus(HttpServletResponse.SC_BAD_REQUEST);
//			return -3;
//		}
		
		String msg=message;
		SessionManager sessionManage =SessionManager.getInstance();
		int a= sessionManage.getSessionCount();
		Session []  sessionArray = sessionManage.getSessions();
		for(Session s : sessionArray){
			System.out.println(s.getId()+"session");
		}
		System.out.println(a);
		if(!"".equals(msg)){
//			SessionManager.getInstance().stop();
			 if(SessionManager.getInstance().hasSession(userId)){
			Event event=Event.createDataEvent("/message/world");
		
			SysUser userInfo = (SysUser)request.getSession().getAttribute("user");
			String id = event.getField(Protocol.P_ID);
			if(userInfo != null&&userName!=null&&userId!=null){
				event.setField("userName",new String(userName.toString().getBytes("utf-8"),"iso-8859-1"));
				event.setField("message",new String(msg));
				event.setField("userId",new String(userId.getBytes("utf-8"),"iso-8859-1"));
				event.setField("from",new String(userInfo.getUserName().getBytes("utf-8"),"iso-8859-1"));
				event.setField("fromId",userInfo.getId());
				event.setField(Protocol.P_SUBJECT, "singleChart");
			}
//			Dispatcher.getInstance().multicast(event);//群发
			Dispatcher.getInstance().unicast(event, userId);//点对点发 向id=2 的用户发送消息
			
//			Session session = SessionManager.getInstance().getSession(userId);
//			Command command = Command.create(session, event, request, response);
//			Dispatcher.getInstance().multicast(command.reqEvent);
			
			//测试离线发送消息
//			for(int i=0;i<3;i++){
//				
//				event.setField("userName",new String("w".toString().getBytes("utf-8"),"iso-8859-1"));
//				event.setField("message",new String("啦啦啦".getBytes("utf-8"),"iso-8859-1"));
//				event.setField("user",new String("222".getBytes("utf-8"),"iso-8859-1"));
//				event.setField("from",new String("yk".getBytes("utf-8"),"iso-8859-1"));
//				Dispatcher.getInstance().unicast(event, "1");//点对点发 向id=2 的用户发送消息
//				
//
//			}
			return 1;//用户登陆状态
			}else{
				return 0;//聊天的用户未登陆
			}
		}
		return -1;//聊天信息为空
		
	}


}
