/**  
* Project ：              ext-chart-modify  
* FileName ：           com.chart.cn.web.controller.MyPushlet.java 
* Description : 
* Copyright :   Copyright  中国网络电视台 (c) 2015 All Rights reserved.
* Company :     中国网络电视台
* @author :     yk 
* @version:     1.0
* Create at ：        2015年11月10日 下午7:32:35  
*
* Modification History : 
* Date                    Author       Version         Description
*------------------------------------------------------
* 2015年11月10日 下午7:32:35         yk        1.0              
*/  

package com.chart.cn.web.controller;
import java.io.IOException;   
import java.io.UnsupportedEncodingException; 
import javax.servlet.ServletException; 
import javax.servlet.http.HttpServlet;  
import javax.servlet.http.HttpServletRequest; 
import javax.servlet.http.HttpServletResponse; 
import nl.justobjects.pushlet.core.Dispatcher; 
import nl.justobjects.pushlet.core.Event; 
import nl.justobjects.pushlet.core.SessionManager;  
 
 public class MyPushlet extends HttpServlet {  
	 /* protected long getSleepTime() {             return 1000;           } */ 	
	 @Override    
	 protected  void doGet(HttpServletRequest req, HttpServletResponse resp)
			 throws ServletException, IOException {   
		 myUnicast();         
		 myMulticast();     
		 myBroadcast();       }   
	 private void myUnicast() throws UnsupportedEncodingException    
	 {     
		 
	HttpServletRequest request;      
	if(SessionManager.getInstance().hasSession("liu")){  
	  Event  event = Event.createDataEvent("myevent1");  
     String str = "推送特定成员消息hhhh"; 
   str =new String(str.getBytes("UTF-8"),"ISO-8859-1"); //解决中文问题，原始的是不能推送中文字符        
   event.setField("key1", str);         
   Dispatcher.getInstance().unicast(event,"liu"); //向ID为liu的用户推送     
   System.out.println("success！！");       
   }           else {           
	   System.out.println("liu do not login....0000000000000000");  
	   }       }     
	 
	 private void myMulticast()  
	 {          
	Event  event = Event.createDataEvent("myevent1");      
	 event.setField("key1", "推送分组成功....");       
	 Dispatcher.getInstance().multicast(event); 
	 //向所有和myevent1名称匹配的事件推送      
	 System.out.println(" 分组success！！！");      
	 }   
	 private void myBroadcast()       { 
		 
		 Event  event = Event.createDataEvent("myevent1"); //向所有的事件推送，不要求和这儿的myevent1名称匹配  
		 event.setField("key1", "推送所有人成功....");          
		 Dispatcher.getInstance().broadcast(event);        
		 System.out.println("所有人推送success！！！");    
		 }  
	 }
 



