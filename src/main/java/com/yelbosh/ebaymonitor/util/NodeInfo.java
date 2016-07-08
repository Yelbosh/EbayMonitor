package com.yelbosh.ebaymonitor.util;

/**
 * a struct to store the monitored node information
 * @author Yabo
 * update: 2016-07-07
 * email: yelbosh@foxmail.com
 */
public class NodeInfo {
	public NodeInfo(String id, String name, String ip){
		this.id = id;
		this.name = name;
		this.ip = ip;
	}
	
	public NodeInfo(String id, String name, String ip, String uname, String pwd){
		this.id = id;
		this.name = name;
		this.ip = ip;
		this.uname = uname;
		this.pwd = pwd;
	}
	
	public String id;
	public String name;
	public String ip;
	public String uname;
	public String pwd;
}
