package com.yelbosh.ebaymonitor.backend;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.StringTokenizer;

import org.apache.log4j.Logger;

import com.yelbosh.ebaymonitor.global.MonitorDataBuffer;
import com.yelbosh.ebaymonitor.global.UsageInfo;
import com.yelbosh.ebaymonitor.util.LoggerUtil;
import com.yelbosh.ebaymonitor.util.NodeInfo;
import com.yelbosh.ebaymonitor.util.SystemExceptionNoti;

/**
 * I use this class to monitor the process that monitor server takes to get cpu/memory trend data
 * @author Yabo
 * update: 2016-07-07
 * email: yelbosh@foxmail.com
 */
public class PullDataTask implements Runnable{
	private NodeInfo node;
	private Logger logger;
	
	public PullDataTask(NodeInfo node){
		this.node = node;
//		this.logger = LoggerUtil.getLogger(PullDataTask.class);
	}

	public void run() {
		String id = node.id;
		String ip = node.ip;
		String uname = node.uname;
		String pwd = node.pwd;
		//remote login, read system info from /proc directory
		//OR: utilize RMI to obtain the remote node cpu/memory information
		
		//I didn't have these node server now, so I just list the methods below of how to
		//get these information. I will add the RMI method in future.
		//currently I will use some simulated data to return to monitor server
		MonitorDataBuffer buffer = MonitorDataBuffer.getInstance();
		buffer.initialize();
		int cpuusage = (int)(75+Math.random()*(21));
		int memusage = (int)(cpuusage+(-10 + Math.random()*(20)));
		buffer.write(id, cpuusage, memusage);
		//log the monitor history
//		logger.info("[server: "+node.id+"]\t" + cpuusage + "\t" + memusage + "\t" + System.currentTimeMillis() + "\n");
	}
	
	//get the linux CPU usage
    public int cpuUsage() {
        try {
            Map<?, ?> map1 = this.cpuinfo();
            Thread.sleep(5 * 1000);
            Map<?, ?> map2 = this.cpuinfo();

            long user1 = Long.parseLong(map1.get("user").toString());
            long nice1 = Long.parseLong(map1.get("nice").toString());
            long system1 = Long.parseLong(map1.get("system").toString());
            long idle1 = Long.parseLong(map1.get("idle").toString());

            long user2 = Long.parseLong(map2.get("user").toString());
            long nice2 = Long.parseLong(map2.get("nice").toString());
            long system2 = Long.parseLong(map2.get("system").toString());
            long idle2 = Long.parseLong(map2.get("idle").toString());

            long total1 = user1 + system1 + nice1;
            long total2 = user2 + system2 + nice2;
            float total = total2 - total1;

            long totalIdle1 = user1 + nice1 + system1 + idle1;
            long totalIdle2 = user2 + nice2 + system2 + idle2;
            float totalidle = totalIdle2 - totalIdle1;

            float cpusage = (total / totalidle) * 100;
            return (int) cpusage;
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        return 0;
    }
    
  //get the linux CPU info
    public Map<?, ?> cpuinfo() {
        InputStreamReader inputs = null;
        BufferedReader buffer = null;
        Map<String, Object> map = new HashMap<String, Object>();
        try {
            inputs = new InputStreamReader(new FileInputStream("/proc/stat"));
            buffer = new BufferedReader(inputs);
            String line = "";
            while (true) {
                line = buffer.readLine();
                if (line == null) {
                    break;
                }
                if (line.startsWith("cpu")) {
                    StringTokenizer tokenizer = new StringTokenizer(line);
                    List<String> temp = new ArrayList<String>();
                    while (tokenizer.hasMoreElements()) {
                        String value = tokenizer.nextToken();
                        temp.add(value);
                    }
                    map.put("user", temp.get(1));
                    map.put("nice", temp.get(2));
                    map.put("system", temp.get(3));
                    map.put("idle", temp.get(4));
                    map.put("iowait", temp.get(5));
                    map.put("irq", temp.get(6));
                    map.put("softirq", temp.get(7));
                    map.put("stealstolen", temp.get(8));
                    break;
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                buffer.close();
                inputs.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return map;
    }
    
    //get the linux memory info
    public static int memoryUsage() {
        Map<String, Object> map = new HashMap<String, Object>();
        InputStreamReader inputs = null;
        BufferedReader buffer = null;
        try {
            inputs = new InputStreamReader(new FileInputStream("/proc/meminfo"));
            buffer = new BufferedReader(inputs);
            String line = "";
            while (true) {
                line = buffer.readLine();
                if (line == null)
                    break;
                int beginIndex = 0;
                int endIndex = line.indexOf(":");
                if (endIndex != -1) {
                    String key = line.substring(beginIndex, endIndex);
                    beginIndex = endIndex + 1;
                    endIndex = line.length();
                    String memory = line.substring(beginIndex, endIndex);
                    String value = memory.replace("kB", "").trim();
                    map.put(key, value);
                }
            }

            long memTotal = Long.parseLong(map.get("MemTotal").toString());
            long memFree = Long.parseLong(map.get("MemFree").toString());
            long memused = memTotal - memFree;
            long buffers = Long.parseLong(map.get("Buffers").toString());
            long cached = Long.parseLong(map.get("Cached").toString());

            double usage = (double) (memused - buffers - cached) / memTotal * 100;
            return (int) usage;
        } catch (Exception e) {
            e.printStackTrace();
        } finally {
            try {
                buffer.close();
                inputs.close();
            } catch (Exception e2) {
                e2.printStackTrace();
            }
        }
        return 0;
    }
}
