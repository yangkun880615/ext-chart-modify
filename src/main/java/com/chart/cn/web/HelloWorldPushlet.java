/**  
* Project ：              ext-chart-modify  
* FileName ：           com.chart.cn.web.HelloWorldPushlet.java 
* Description : 
* Copyright :   Copyright  中国网络电视台 (c) 2015 All Rights reserved.
* Company :     中国网络电视台
* @author :     yk 
* @version:     1.0
* Create at ：        2015年11月10日 下午7:45:42  
*
* Modification History : 
* Date                    Author       Version         Description
*------------------------------------------------------
* 2015年11月10日 下午7:45:42         yk        1.0              
*/  

package com.chart.cn.web;

import java.io.UnsupportedEncodingException;
import nl.justobjects.pushlet.core.Event;
import nl.justobjects.pushlet.core.EventPullSource;
public class HelloWorldPushlet {
	private static final long serialVersionUID = -8940934044114406724L;
	 
    public static class HWPushlet extends EventPullSource {
//         Log log = Log.getLog(HWPushlet.class.getName(),
//                    HWPushlet.class.getName(), true);

        
         @Override
         protected long getSleepTime() {
               return 1000;//每一秒钟自动执行一次
         }

        
         @Override
         protected Event pullEvent() {
           //注意，一下是设定消息的主题/guoguo/helloworld，号称主题是可以继承的
          //但是笔者的测试是失败的，也许方法不对，呵呵
               Event event = Event.createDataEvent("/guoguo/helloworld");
               String data= "hello,world 郭强 "+System.currentTimeMillis();
               try {
                    data=new String(data.getBytes("UTF-8"),"ISO-8859-1");
               } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
               }
               event.setField("hw",data);
               return event;
         }
    }
}
  


