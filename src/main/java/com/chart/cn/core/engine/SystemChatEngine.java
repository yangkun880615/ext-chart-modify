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


public class SystemChatEngine extends Thread implements PushletClientListener, Protocol {
	private static SystemChatEngine  systemChatEngine = new SystemChatEngine("http://localhost:8088/bsIM/pushlet.srv");

	private PushletClient pushletClient;
	private String aHosturl=null;
	private static final String SUBJECT = "/pushlet/ping,/user/login,/user/chat";
    private String meSessionID = null;

    //在线用户  里面装的就是在线用户队列 key= pushlet专有的 SessionID 此属性内部使用
    private Map<String,OnlineUser> onlinesUsedByinner = Collections.synchronizedMap(new HashMap(0));
    
    //在线用户  里面装的就是在线用户队列 key= accountID
    private Map<String,OnlineUser> onlines = Collections.synchronizedMap(new HashMap(0));
    

	private SystemChatEngine(String aHosturl) {
		this.aHosturl = aHosturl;
	}
	
	public void run() {
		// Create and start a Pushlet client; we receive callbacks
		// through onHeartbeat() and onData().
		try {

			pushletClient = new PushletClient(aHosturl);	
			pushletClient.setDebug(false);
			pushletClient.join();
			pushletClient.listen(this, Protocol.MODE_PULL,SUBJECT);


			
			p("pushletClient started");
			
		} catch (PushletException pe) {
			p("Error in setting up pushlet session pe=" + pe);
			return;
		}

		
	}

	/** Error occurred. */
	public void onError(String message) {
		p(message);
	}

	/** Abort event from server. */
	public void onAbort(Event theEvent) {
		p("onAbort received: " + theEvent);
	}

	/** Data event from server. */
	public void onData(Event theEvent) {
		// Calculate round trip delay
		
		if( theEvent.getSubject().equalsIgnoreCase("/user/login") ){
		
			if(!meSessionID.equalsIgnoreCase(theEvent.getField(Protocol.P_FROM))){
		        //如果是登陆的消息 并且不是自己发出的 并且 队列中没有这个id的节点
		        if ( onlinesUsedByinner.get(theEvent.getField(Protocol.P_FROM)) == null) {
		            
		        	OnlineUser curOnlineUser = new OnlineUser(theEvent.getField(Protocol.P_FROM), theEvent.getField("accountID") ,theEvent.getField("name")  );
		        	curOnlineUser.setAliveTS(Long.parseLong(theEvent.getField(Protocol.P_TIME)));
		
		        	//添加到在线数组中
		            onlinesUsedByinner.put(curOnlineUser.getId() ,curOnlineUser);
		            onlines.put(curOnlineUser.getAccountID(), curOnlineUser);
		            p(curOnlineUser.username + "["+ curOnlineUser.accountID+"]上线了！！");
					 
		
		        }
		        else
		        {
		        	OnlineUser curOnlineUser = onlinesUsedByinner.get(theEvent.getField(Protocol.P_FROM));
		        	
		                //如果是登陆的消息 并且不是自己发出的 并且 树中有这个id的节点  就更新他的在线时间戳
		        	curOnlineUser.setAliveTS(Long.parseLong(theEvent.getField(Protocol.P_TIME)));
		        	onlinesUsedByinner.put(curOnlineUser.getId() ,curOnlineUser);
		        	onlines.put(curOnlineUser.getAccountID(), curOnlineUser);
		             
		            
		        }	
	        
			}
		}
		
		
		
		if( theEvent.getSubject().equalsIgnoreCase("/user/chat") ){		
			if(!meSessionID.equalsIgnoreCase(theEvent.getField(Protocol.P_FROM))){
			     
				long then = Long.parseLong(theEvent.getField(Protocol.P_TIME));
				long delay = System.currentTimeMillis() - then;
				p("从"+onlinesUsedByinner.get(theEvent.getField(Protocol.P_FROM)).getUsername()+"["+onlinesUsedByinner.get(theEvent.getField(Protocol.P_FROM)).getAccountID()+"]来");
				p("onData:  #" + theEvent.getField("message") + " on " + (new Date()).toLocaleString() + " ms");
				
				
			}
		    

			
		}
		
		
		


	}

	/** Heartbeat event from server. */
	public void onHeartbeat(Event theEvent) {
		



		
		p("onHeartbeat received: " + theEvent);
		
		
		
	}

	/** Generic print. */
	public void p(String s) {
		System.out.println("[println:] " + s);
	}

	/** Main program. */
	public static void main(String args[])
	{
	
		
		systemChatEngine.start();
		


	  
	  
	}

	public void onRefresh(Event theEvent) {
		long then = Long.parseLong(theEvent.getField(Protocol.P_TIME));
		long delay = System.currentTimeMillis() - then;
		//p("onRefresh:  #" + theEvent + " in " + delay + " ms");
		
		
		
	}

	public void onRefresh_ack(Event theEvent) {
		
		long curEventP_time = Long.parseLong(theEvent.getField(Protocol.P_TIME));
		
		
		Set<String> keySet = new HashSet(onlinesUsedByinner.keySet());
		
	    for(String  curkey :  keySet){
            //当前时间戳-上次更新的时间戳 如果间隔较大 说明没更新  掉了
            if((curEventP_time- onlinesUsedByinner.get(curkey).getAliveTS())>30){
            	OnlineUser curOnlineUser = onlinesUsedByinner.get(curkey);
                p(curOnlineUser.username + "["+ curOnlineUser.accountID+"]离开了！！");
			    
                onlinesUsedByinner.remove(curkey);//从在线用户移除
                onlines.remove(curOnlineUser.accountID);//从在线用户移除
            }
        }
		
		
        keySet = onlinesUsedByinner.keySet();
		
/*	    for(String  curkey :  keySet){
	    	 p("现在有在线用户："+onlines.get(curkey).username + "["+ onlines.get(curkey).accountID+"]！！");
	    	 
        }	*/	
		
		
		
		
	

		
		long then = Long.parseLong(theEvent.getField(Protocol.P_TIME));
		long delay = System.currentTimeMillis() - then;
		//p("onRefresh_ack:  #" + theEvent + " in " + delay + " ms");
		
		
		
		
		
		
		Map attributemap = new HashMap();
		try {
			attributemap.put("name", java.net.URLEncoder.encode("system系统","UTF-8"));
			attributemap.put("accountID", java.net.URLEncoder.encode("system","UTF-8"));
		} catch (UnsupportedEncodingException e1) {
			// TODO Auto-generated catch block
			e1.printStackTrace();
		}
		try {
			pushletClient.publish("/user/login", attributemap);
		} catch (PushletException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
		
		
		
	}
	
	
	protected class OnlineUser {
		private String id = null;
		private String username = null;
		private String accountID = null;
		private long aliveTS = 0;
		
		
		
		public OnlineUser(String id ,String accountID, String username){
			this.id = id;
			this.accountID = accountID;
			this.username = username;
			
		}
		
		/**
		 * @return the id
		 */
		public String getId() {
			return id;
		}
		
		/**
		 * @return the accountID
		 */
		public String getAccountID() {
			return accountID;
		}
		
		/**
		 * @return the username
		 */
		public String getUsername() {
			return username;
		}

		/**
		 * @return the aliveTS
		 */
		public long getAliveTS() {
			return aliveTS;
		}
		/**
		 * @param aliveTS the aliveTS to set
		 */
		public void setAliveTS(long aliveTS) {
			this.aliveTS = aliveTS;
		}



		
		
		
		
	}


	public void onJoinListenAck(Event theEvent) {
		// TODO Auto-generated method stub
		//p("onJoinListenAck received: " + theEvent);
		
		meSessionID = theEvent.getField(Protocol.P_ID);
		
		
		

		
	}

	public void onListenAck(Event theEvent) {
		// TODO Auto-generated method stub
		//p("onListenAck received: " + theEvent);
		meSessionID = theEvent.getField(Protocol.P_ID);

	}

	/**
	 * @return the pushletClient
	 */
	public static  PushletClient getPushletClient() {
		
		while(  systemChatEngine.pushletClient==null){
			
			
		}
	
		return systemChatEngine.pushletClient;
	}

	/**
	 * 在线用户  里面装的就是在线用户队列 key= accountID
	 * @return the onlines
	 */
	public static Map<String, OnlineUser> getOnlines() {
		return systemChatEngine.onlines;
	}
	

}


