/**
 * 
 */
package com.chart.cn.core.engine;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import com.chart.cn.core.engine.SystemChatEngine.OnlineUser;

import nl.justobjects.pushlet.client.PushletClient;
import nl.justobjects.pushlet.util.PushletException;


/**
 * @author rogergou
 *
 */
public class MainChat {

	/**
	 * @param args
	 */
	public static void main(String[] args) {

		
		SystemChatEngine.main(null);
		
		PushletClient pushletClient = SystemChatEngine.getPushletClient();
		
		System.out.println("开始。。。。。。。。。。。。。" + pushletClient);
		
		while (true) {
			
			
			try {
				
				InputStreamReader stdin = new InputStreamReader(System.in);//键盘输入 
				BufferedReader bufin = new BufferedReader(stdin); 
			

				String message = bufin.readLine(); 


				Set<String> keySet = new HashSet(SystemChatEngine.getOnlines().keySet());
	
				
			    for(String  curkey :  keySet){
			    	OnlineUser  curOnlineUser =SystemChatEngine.getOnlines().get(curkey);
			    	
			    	Map attributemap = new HashMap();
					try {
						attributemap.put("p_to", curOnlineUser.getId());
						attributemap.put("message", java.net.URLEncoder.encode("系统广播："+message,"UTF-8"));
						
					} catch (UnsupportedEncodingException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
					try {
						pushletClient.publish("/user/chat", attributemap);
					} catch (PushletException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
			    	
			    	
		        }
				
				
				
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
			
		}//end while

	}

}
