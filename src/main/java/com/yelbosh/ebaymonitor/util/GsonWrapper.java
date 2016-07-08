package com.yelbosh.ebaymonitor.util;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

import com.google.gson.Gson;
import com.yelbosh.ebaymonitor.global.UsageInfo;

/**
 * The gson wrapper for gson google
 * @author Yelbosh
 * update: 2016-07-07
 * email: yelbosh@foxmail.com
 */
public class GsonWrapper {
	private static Gson gson = null;
	
	public static String toJson(HashMap map){
		if(gson == null)
			gson = new Gson();
		return gson.toJson(map);
	}
	
	public static String exceptionMsg(String msg){
		HashMap map = new HashMap();
		map.put("stat", "0");
		map.put("msg", msg);
		return toJson(map);
	}
	
	public static String rtnData(String id){
		HashMap map = new HashMap();
		map.put("stat", "1");
		map.put("msg", "OK");
		map.put("data", id);
		return toJson(map);
	}
	
	public static String rtnUsageData(UsageInfo usage){
		HashMap map = new HashMap();
		map.put("stat", "1");
		map.put("msg", "OK");
		HashMap map2 = new HashMap();
		map2.put("cpu", usage.cpuusage);
		map2.put("mem", usage.memusage);
		map2.put("time", usage.time);
		map.put("data", map2);
		return toJson(map);
	}
	
	public static String rtnUsageDataList(LinkedList<UsageInfo> usageList){
		HashMap map = new HashMap();
		map.put("stat", "1");
		map.put("msg", "OK");
		LinkedList list = new LinkedList<HashMap>();
		for(UsageInfo usage : usageList){
			HashMap submap = new HashMap();
			submap.put("cpu", usage.cpuusage);
			submap.put("mem", usage.memusage);
			submap.put("time", usage.time);
			list.add(submap);
		}
		map.put("data", list);
		return toJson(map);
	}
	
}
