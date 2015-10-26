/**  
* Project ：              ext-chart  
* FileName ：           com.chart.cn.web.controller.chart.HelloWorldPlushlet.java 
* Description : 
* @author :     yk 
* @version:     1.0
* Create at ：        2015年3月9日 下午11:25:49  
*
* Modification History : 
* Date                    Author       Version         Description
*------------------------------------------------------
* 2015年3月9日 下午11:25:49         yk        1.0              
*/  

package com.chart.cn.web.controller.chart;

import nl.justobjects.pushlet.core.Event;
import nl.justobjects.pushlet.core.EventPullSource;

public class HelloWorldPlushlet {
    static public class HwPlushlet extends EventPullSource {  
        // 休眠1秒  
        @Override  
        protected long getSleepTime() {  
            return 1000;  
        }  
        @Override  
        protected Event pullEvent() {  
            Event event = Event.createDataEvent("/cuige/he");  
            event.setField("mess", "hello,world!Plushlet!");  
            return event;  
        }  
    }
}
  


