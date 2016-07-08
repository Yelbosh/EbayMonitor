package com.yelbosh.ebaymonitor.util;

import org.apache.log4j.Logger;
import org.apache.log4j.PropertyConfigurator;

/**
 * The logger util for log4j
 * @author Yelbosh
 * update: 2016-07-07
 * email: yelbosh@foxmail.com
 */
public class LoggerUtil {
	
	public static Logger getLogger(Class className){
		PropertyConfigurator.configure(ClassLoader.getSystemResource("log4j.properties"));
    	Logger logger = Logger.getLogger(className);
    	return logger;
	}
	
}
