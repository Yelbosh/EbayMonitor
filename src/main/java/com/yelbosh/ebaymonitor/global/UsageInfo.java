package com.yelbosh.ebaymonitor.global;

/**
 * The data structure to denote the usage information
 * @author Yabo
 * update: 2016-07-07
 * email: yelbosh@foxmail.com
 */
public class UsageInfo {
	public UsageInfo(int cpuusage, int memusage, long time){
		this.cpuusage = cpuusage;
		this.memusage = memusage;
		this.time = time;
	}
	public int cpuusage;
	public int memusage;
	public long time; //Millisecond timestamp
}
