package com.chart.cn.core.engine;

import java.util.HashMap;


public class UserSessionMap {
	private HashMap<String, String> userForSession;
	private static UserSessionMap usersession = new UserSessionMap();
	
	private UserSessionMap(){
		this.userForSession = new HashMap<String,String>();
	}
	
	public static UserSessionMap getInstance(){
		if(usersession == null){
			usersession = new UserSessionMap();
			}
		return usersession;
	}

	public HashMap<String, String> getUserForSession() {
		return userForSession;
	}
	
	public synchronized void addNewUerSession(String username,String sessionId){
		getInstance().getUserForSession().put(username, sessionId);
	}
	
	public synchronized void removeUserSession(String sessionId){
		
		for(String name:getInstance().getUserForSession().keySet()){
			if(sessionId.equals(getInstance().getUserForSession().get(name))){
				getInstance().getUserForSession().remove(name);
				break;
			}
		}
	}
	
	public void showALlOnlineUserName(){
		for(String name:getInstance().getUserForSession().keySet()){
			System.out.println("username:"+name+"      id:"+getInstance().getUserForSession().get(name));
		}
	}
	
	public String getSessionId(String name){
		return getInstance().getUserForSession().get(name);
	}
}
