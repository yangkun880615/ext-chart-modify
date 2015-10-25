package com.chart.cn.util;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;

import com.google.gson.Gson;
import com.google.gson.JsonSyntaxException;
import com.google.gson.internal.StringMap;
import com.google.gson.reflect.TypeToken;

/**
 * json工具包类
 * 
 */
public class JsonUtils {

	private static Gson gson = new Gson();

	// ?
	public static List<Map<String, Object>> _toMap(List<Object> list) {
		List<Map<String, Object>> returnList = new ArrayList<Map<String, Object>>();

		for (Object each : list) {

			if (each instanceof StringMap) {
				returnList.add(toMap((StringMap) each));
			} else if (each instanceof ArrayList) {
				for (Object e : (ArrayList) each) {
					returnList.add(toMap((StringMap) e));
				}
			}
			// my
			else {
				String json = toJson(each);
				System.out.println(json);
				returnList.add(toMap(json));
			}
		}
		return returnList;
	}
 
	public static StringBuffer buildData(String type,String key,Object value)
	{
		StringBuffer result=new StringBuffer();
		if(type.equalsIgnoreCase("string")){
			result.append("\"").append(key).append("\":").append("\"")
			.append(value).append("\"");
		}
		if(type.equalsIgnoreCase("int")||type.equalsIgnoreCase("boolean")){
			result.append("\"").append(key).append("\":").append(value);
		}
		return result;
		
	}	
	
	@SuppressWarnings("rawtypes")
	public static Map<String, Object> toMap(StringMap gsonStringMap) {
		Map<String, Object> pool = new HashMap<String, Object>();

		Iterator keys = gsonStringMap.keySet().iterator();
		while (keys.hasNext()) {
			Object key = keys.next();
			Object value = gsonStringMap.get(key);
			pool.put(key.toString(), value);
			if (value instanceof ArrayList) {
				ArrayList list = (ArrayList) value;
				for (Object each : list) {
					if (each instanceof StringMap) {
						pool.putAll(toMap((StringMap) each));
					}
				}
			} else if (value instanceof StringMap) {
				pool.putAll(toMap((StringMap) value));
			}
		}
		return pool;
	}

	// t
	public static Object json2object(String json) throws JsonSyntaxException {
		return json2object(json, Object.class);
	}

	public static <T> T json2object(String json, Class<T> clazz)
			throws JsonSyntaxException {
		return gson.fromJson(json, clazz);
	}

	// t
	public static String toJson(Object obj) {
		return gson.toJson(obj);
	}

	/**
	 * 得到一个json串的所有键值对
	 * @param json
	 * @return
	 */
	@SuppressWarnings({ "rawtypes" })
	// t
	public static Map<String, Object> toMap(String json) {
		return toMap((StringMap) gson.fromJson(json, Object.class));
	}

	/**
	 * 得到一个json串的所有键值对
	 * @param json
	 * @return
	 */
	// t
	@SuppressWarnings({ "rawtypes", "unchecked" })
	public static List<Map<String, Object>> toMapList(String json) {
		Object obj = gson.fromJson(json, Object.class);

		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		if (obj instanceof ArrayList) {
			list.addAll(_toMap((ArrayList) obj));
		} else {
			list.add(toMap((StringMap) obj));
		}

		return list;
	}

	public static int number(String json) {
		boolean b = false;
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		Object obj = gson.fromJson(json, Object.class);
		int count = 0;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (obj instanceof ArrayList) {
			list.addAll(_toMap((ArrayList) obj));
			for (Object c : list) {

				if (c instanceof Object) {
					String json1 = toJson(c);
					Object ob=json2object(json1);
					System.out.println(ob.getClass());
					b = true;
				}

			}
			if (b = true) {
				count++;
			}
			count++;
		} else {
			list.add(toMap((StringMap) obj));
			for (int i = 0; i < list.size(); i++) {

			}
			count++;
		}

		/*
		 * while(obj instanceof ArrayList ||obj instanceof StringMap){ count++;
		 * break; }
		 */

		return count;
	}

	public static int count(String json) {
		boolean b = false;
		Map<Integer, Integer> map = new HashMap<Integer, Integer>();
		Object obj = gson.fromJson(json, Object.class);
		int count = 0;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();
		if (obj instanceof ArrayList) {
			list.addAll(_toMap((ArrayList) obj));
			for (Object c : list) {

				if (c instanceof HashMap) {
					b = true;
					String j = toJson(c);
					System.out.println(j);

				}

			}
			if (b = true) {
				count++;
			}
			count++;
		} else {
			list.add(toMap((StringMap) obj));
			for (int i = 0; i < list.size(); i++) {

			}
			count++;
		}

		/*
		 * while(obj instanceof ArrayList ||obj instanceof StringMap){ count++;
		 * break; }
		 */

		return count;
	}

	public static void main(String[] args) {
		String str = "[{'id': '1','code': 'bj','name': '北京',"
				+ "'map': [{'id': '2','code': 'sz','name': '深圳','map': '22.9, 14.998'}]}, "
				+ "{'id': '10','code': 'gz','name': '广州','map': '23.13,113.26443'}]";
		String sr = "[{'id': '1','code': 'bj','name': '北京','map': '39.90403, 116.40752599999996'}, "
				+ "{'id': '2','code': 'sz','name': '深圳','map': '22.543099, 114.05786799999998'},"
				+ " {'id': '9','code': 'sh','name': '上海','map': '31.230393,121.473704'}, "
				+ "{'id': '10','code': 'gz','name': '广州','map': '23.129163,113.26443500000005'}]";
		int c = number(sr);
		System.out.println(c);

	}

	// my 返回一个Map<String,Object>类型 如果对象为b 可调用b.get("code")类似方法
	public static Object getObject(String json, int index) {
		Object obj = gson.fromJson(json, Object.class);
		System.out.println(obj.getClass());
		Object bj = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		if (obj instanceof ArrayList) {
			list.addAll(_toMap((ArrayList) obj));
		} else {
			list.add(toMap((StringMap) obj));
		}
		if (list.size() == 1) {
			bj = list.get(0);
		} else {
			if (index > list.size() - 1 || index < 0) {
				System.out.println("请输入范围内的index");
			} else {
				bj = list.get(index);
			}
		}

		return bj;
	}

	public static Object getObject(String json, int index, String key) {
		Object obj = gson.fromJson(json, Object.class);
		System.out.println(obj.getClass());
		Object bj = null;
		List<Map<String, Object>> list = new ArrayList<Map<String, Object>>();

		if (obj instanceof ArrayList) {
			list.addAll(_toMap((ArrayList) obj));
		} else {
			list.add(toMap((StringMap) obj));
		}
		if (list.size() == 1) {
			bj = list.get(0);
		} else {
			if (index > list.size() - 1 || index < 0) {
				System.out.println("请输入范围内的index");
			} else {
				bj = list.get(index).get(key);
			}
		}

		return bj;
	}

	public static List<?> jsonToList(String jsonStr) {
		List<?> objList = null;
		if (gson != null) {
			Type type = new TypeToken<List<?>>() {
			}.getType();
			objList = gson.fromJson(jsonStr, type);
		}
		return objList;
	}

	// json串转化成list
	public static List<?> jsonToList(String jsonStr, Type type) {
		List<?> objList = null;
		if (gson != null) {
			objList = gson.fromJson(jsonStr, type);
		}
		return objList;
	}

	// json串转化成map
	public static Map<?, ?> jsonToMap(String jsonStr) {
		Map<?, ?> objMap = null;
		if (gson != null) {
			Type type = new TypeToken<Map<?, ?>>() {
			}.getType();
			objMap = gson.fromJson(jsonStr, type);
		}
		return objMap;
	}

	// json串转化成bean Class<?>
	public static Object jsonToBean(String jsonStr, Object cl) {
		Object obj = null;
		if (gson != null) {
			obj = gson.fromJson(jsonStr, cl.getClass());
		}
		return obj;
	}

	/*
	 * public static Map<Integer,String> getCount(String json){
	 * Map<Integer,String>map=new HashMap<Integer,String>();
	 * 
	 * }
	 */
	

}