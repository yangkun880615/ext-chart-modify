// Copyright (c) 2000 Just Objects B.V. <just@justobjects.nl>
// Distributable under LGPL license. See terms of license at gnu.org.

package com.chart.cn.core.engine;

import nl.justobjects.pushlet.client.PushletClient;
import nl.justobjects.pushlet.client.PushletClientListener;
import nl.justobjects.pushlet.core.Event;
import nl.justobjects.pushlet.core.Protocol;
import nl.justobjects.pushlet.util.PushletException;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.Date;


public class SystemChatEngine1 extends Thread implements PushletClientListener, Protocol {
	private static SystemChatEngine1 engine = null ;  
private PushletClient PushletClient;  
private Map<String ,String> attributemap ;  
private String aHosturl=null;  
private String SUBJECT = "/pushlet/ping";  
private String imSubject = null;  

private SystemChatEngine1(String aHosturl, String subject, String imSubject, Map<String ,String> map) {  
    this.aHosturl = aHosturl;  
    this.SUBJECT = subject ;  
    this.attributemap = map ;  
    this.imSubject = imSubject ;  
}  
  
public static SystemChatEngine1 getInstance(String aHosturl, String subject ,String imSubject ,Map<String ,String> map){  
    if(engine==null){  
        engine = new SystemChatEngine1(aHosturl,subject,imSubject,map) ;  
        engine.start() ;  
    }  
    return engine ;  
}  
  
//public void run() {  
//    try {  
//
//        pushletClient = new PushletClient(aHosturl);  
//        pushletClient.setDebug(false);  
//          
//        pushletClient.setUid(attributemap.get("uid")) ;  
//          
//        pushletClient.join();  
//        pushletClient.listen(this, Protocol.MODE_PULL,SUBJECT);  
//
//        System.out.println("pushletClient started");  
//          
//        pushletClient.publish(imSubject, attributemap);  
//          
//    } catch (Exception e) {  
//        e.printStackTrace();  
//        return;  
//    }  
//}  

/** Error occurred. */  
public void onError(String message) {  
    System.out.println(message);  
}  

/** Abort event from server. */  
public void onAbort(Event theEvent) {  
}  

/** Data event from server. */  
public void onData(Event theEvent) {  
    String meSessionID = theEvent.getField("uid");  
}  

/** Heartbeat event from server. */  
public void onHeartbeat(Event theEvent) {  
}  

public void onRefresh(Event theEvent) {  
}  

public void onJoinListenAck(Event theEvent) {  
    String meSessionID = theEvent.getField("uid");  
}  

public void onListenAck(Event theEvent) {  
    String meSessionID = theEvent.getField("uid");  
}

@Override
public void onRefresh_ack(Event theEvent) {
	// TODO Auto-generated method stub
	
}  }


