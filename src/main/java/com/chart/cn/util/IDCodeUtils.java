package com.chart.cn.util;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import org.springframework.util.CollectionUtils;
import org.apache.commons.lang.StringUtils;

import com.chart.cn.constant.ComonConstant;

public class IDCodeUtils {

	public static void main(String[] args) {
		String pid = "";
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("id", "1");
		map.put("idcode", "001003");
		map.put("pid", "0");
		list.add(map);
		String idcode = idcodeGenerate("001", 3, list);
		System.out.println(idcode);
		// int max_no = getCurrentMaxOderNo("001", list);
		// System.out.println(max_no);
	}

	public static int getCurrentMaxIdCode(String pid, List<Map<String, Object>> list) {
		int max = 0;
		if (CollectionUtils.isEmpty(list))
			return max;
		for (Map<String, Object> map : list) {
			String idcode_str = (String) map.get("idcode");
			if (!"0".equals(pid)) {
				idcode_str = StringUtils.replaceOnce(idcode_str, pid, "");
			}
			int idcode = Integer.valueOf(idcode_str);
			if (idcode > max) {
				max = idcode;
			}
		}
		return max;
	}

	public static String getNextIdcodeByCurentIdcode(String pid, int current_idcode, int num) {
		StringBuilder idcodeCommand = new StringBuilder();
		if (!pid.equals("0")) {
			idcodeCommand.append(pid);
		}
		Integer next_no = current_idcode + 1;
		int len = (next_no + "").length();
		for (int i = len; i < num; i++) {
			idcodeCommand.append("0");
		}
		idcodeCommand.append(next_no);

		return idcodeCommand.toString();
	}

	public static String getNextIdcodeByCurentOrderNo(String pid, int current_order_no, int num) {
		StringBuilder idcodeCommand = new StringBuilder();
		if (!pid.equals("0")) {
			idcodeCommand.append(pid);
		}
		Integer next_no = current_order_no;
		int len = (next_no + "").length();
		for (int i = len; i < num; i++) {
			idcodeCommand.append("0");
		}
		idcodeCommand.append(next_no);

		return idcodeCommand.toString();
	}

	public static String idcodeGenerate(String pid, int number, List<Map<String, Object>> currentList) {
		int current_no = getCurrentMaxIdCode(pid, currentList);
		String idcode = getNextIdcodeByCurentIdcode(pid, current_no, number);
		return idcode;
	}

	public static int getCurrentMaxOrderNo(List<Map<String, Object>> list) {
		int max = 0;
		if (CollectionUtils.isEmpty(list))
			return max;
		for (Map<String, Object> map : list) {
			Integer order_no = (Integer) map.get("order_no");
			if (order_no == null) {
				break;
			}
			if (order_no > max) {
				max = order_no;
			}
		}
		return max;
	}

	public static int getNextOrderNoByCurentNo(List<Map<String, Object>> list) {
		int max = getCurrentMaxOrderNo(list);
		int next_order_no = max + 1;
		return next_order_no;

	}
	   public static String[] getParentId(String strId)
	    {
	    	  int length=strId.length();
	    	  int idlength=ComonConstant.CODE_GEN_LENGTH;
		      String []arry=new String[length/idlength];
		      for(int i=0;i<length;i+=idlength)
		      {
		    	String mid=strId.substring(0,i+idlength);
		    	arry[i/idlength]=mid;
		    
		       }
		       	     
	         return arry;
	    }
	public static List<String> getDistinctIdArry(List<String> idlist,String moduleId)
    {
    	
    	Set se=new HashSet();
    	
    	for(String id:idlist)
    	{
    	   String []ary=getParentId(id);
    	   
    	   for(String mid:ary)
    	   {
    		 if(mid.substring(0,ComonConstant.CODE_GEN_LENGTH).equals(moduleId))
    		 {
    		 se.add(mid);   
    		 }
    	   }
    	}
    	String[]ary=new String[se.size()];
    	
    	ArrayList<String>ids=new ArrayList<String>();  
    	
    	for(Object s:se)
    	{
    		
    		ids.add(s.toString());
    	}
    
    
    	
    	return ids;
    }
}
