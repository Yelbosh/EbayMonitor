package com.yelbosh.ebaymonitor.global;

import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

import com.yelbosh.ebaymonitor.backend.PullDataService;
import com.yelbosh.ebaymonitor.backend.PullDataTask;
import com.yelbosh.ebaymonitor.util.NodeInfo;
import com.yelbosh.ebaymonitor.util.SystemConfig;

/**
 * The data buffer where the pulldata task will put the pulled data here, and servlets will read the data from here
 * @author Yabo
 * update: 2016-07-07
 * email: yelbosh@foxmail.com
 */
public class MonitorDataBuffer {
	private static MonitorDataBuffer databuffer = null;
	private HashMap<String,LinkedList<UsageInfo>> nodeMap;
	private boolean _initialized = false;
	
	//if possible, some popular memory cache database can be used here, such as Redis/Memcached, and so on.
	//I dont think my method is the best, however, its simple and easy to implement.
	private MonitorDataBuffer(){
		initialize();
	}
	
	public static MonitorDataBuffer getInstance(){
		if(databuffer == null){
			databuffer = new MonitorDataBuffer();
		}
		return databuffer;
	}
	
	public void initialize(){
		if(_initialized) 
			return;
		SystemConfig systemConfig = SystemConfig.getInstance();
		if(!systemConfig.isInitialized())
			return;
		nodeMap = new HashMap<String, LinkedList<UsageInfo>>();
		List<NodeInfo> nodelist = systemConfig.getNodelist();
		for(NodeInfo node : nodelist){
			String id = node.id;
			LinkedList<UsageInfo> list = new LinkedList<UsageInfo>();
			nodeMap.put(id, list);
		}
		_initialized = true;
	}
	
	public UsageInfo read(String id){
		LinkedList<UsageInfo> list = nodeMap.get(id);
		return list.getLast();
	}
	
	public LinkedList<UsageInfo> readAll(String id){
		LinkedList<UsageInfo> list = nodeMap.get(id);
		return list;
	}
	
	public void write(String id, int cpuusage, int memusage){
		LinkedList<UsageInfo> list = nodeMap.get(id);
		UsageInfo info = new UsageInfo(cpuusage, memusage, System.currentTimeMillis());
		if(list.size() < 10){
			list.add(info);
		}else{
			list.poll();
			list.offer(info);
		}
	}
	
	
	
}
