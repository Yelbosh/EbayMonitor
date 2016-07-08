package com.yelbosh.ebaymonitor.backend;

import java.util.List;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

import com.yelbosh.ebaymonitor.util.NodeInfo;
import com.yelbosh.ebaymonitor.util.SystemConfig;

/**
 * Singleton model, data fetch service, managing a few threads to do the metric collection job every 30 seconds
 * @author Yabo
 * update: 2016-07-07
 * email: yelbosh@foxmail.com
 */
public class PullDataService {
	private static PullDataService service = null;
	private boolean _initialized;
	private ScheduledExecutorService executor;
	
	private PullDataService(){
		_initialized = false;
	}
	
	public static PullDataService getInstance(){
		if(service == null){
			service = new PullDataService();
		}
		return service;
	}
	
	public void initialize(){
		if(_initialized) 
			return;
		SystemConfig systemConfig = SystemConfig.getInstance();
		if(!systemConfig.isInitialized())
			return;
		List nodelist = systemConfig.getNodelist();
		executor = Executors.newScheduledThreadPool(nodelist.size());
		//allocate a thread to do the metric collection job for each node
		for(int i=0;i<nodelist.size();i++){
			NodeInfo nodeInfo = (NodeInfo)nodelist.get(i);
			executor.scheduleAtFixedRate(new PullDataTask(nodeInfo), 0, 30000, TimeUnit.MILLISECONDS); 
		}
		_initialized = true;
	}
	
	public void shutdown(){
		if(!_initialized) return;
		executor.shutdown();
		_initialized = false;
	}
	
}
