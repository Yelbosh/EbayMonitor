package com.yelbosh.ebaymonitor.util;

import java.util.List;
import java.util.regex.Pattern;

/**
 * some tool functions for this system
 * @author Yabo
 * update: 2016-07-07
 * email: yelbosh@foxmail.com
 */
public class Tools {
	
	//find the node by server name
	public static NodeInfo findNodeByName(List<NodeInfo> nodelist, String name){
		if(name == null) return null;
		for(NodeInfo node : nodelist){
			if(name.equals(node.name))
				return node;
		}
		return null;
	}
	
	//find the node by server ip
	public static NodeInfo findNodeByIp(List<NodeInfo> nodelist, String ip){
		if(ip == null) return null;
		for(NodeInfo node : nodelist){
			if(ip.equals(node.ip))
				return node;
		}
		return null;
	}
	
	//to check if is an ip address
	public static boolean isIPAdress( String str )  
	{  
	    Pattern pattern = Pattern.compile( "^((\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])\\.){3}(\\d|[1-9]\\d|1\\d\\d|2[0-4]\\d|25[0-5]|[*])$" );  
	    return pattern.matcher( str ).matches();  
	}  
}
