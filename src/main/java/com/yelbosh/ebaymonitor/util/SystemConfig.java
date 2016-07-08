package com.yelbosh.ebaymonitor.util;

import java.io.InputStream;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

import org.apache.log4j.Logger;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;

/**
 * The system configuration object to store some basic and node information
 * @author Yabo
 * update: 2016-07-07
 * email: yelbosh@foxmail.com
 */
public class SystemConfig {
	private static SystemConfig configuration = null;
	private boolean _initialized = false;
	private List nodelist;
	
	private SystemConfig(){
		initialize();
	}
	
	public static SystemConfig getInstance(){
		if(configuration == null){
			configuration = new SystemConfig();
		}
		return configuration;
	}
	
	public boolean isInitialized(){
		return _initialized;
	}
	
	public List getNodelist(){
		return nodelist;
	}
	
	//read the user configuration and initialize the object
	private void initialize(){
		if(_initialized) 
			return;
//		Logger logger = LoggerUtil.getLogger(SystemConfig.class);
		nodelist = new LinkedList<NodeInfo>();
		try {
			InputStream input = this.getClass().getResourceAsStream("/configure.xml");
			SAXReader reader = new SAXReader();
			Document document = reader.read(input);
			Element root = document.getRootElement();
			Element monitorRoot = root.element("monitor-nodes");
			List list  = monitorRoot.elements("node");
			for(Iterator i = list.iterator();i.hasNext();) {
				Element nodeitem = (Element) i.next();
				String id = nodeitem.element("id").getText();
				String name = nodeitem.element("name").getText();
				String ip = nodeitem.element("ip").getText();
				String uname = nodeitem.element("uname").getText();
				String pwd = nodeitem.element("pwd").getText();
				NodeInfo node = new NodeInfo(id, name, ip, uname, pwd);
				nodelist.add(node);
			}
			_initialized = true;
		} catch (DocumentException e) {
			_initialized = false;
//			logger.error(SystemExceptionNoti.EXCEPTION_CONFIGURE_1);
//			logger.error(e.getMessage());
		}
		if(nodelist.size() == 0){
			_initialized = false;
//			logger.error(SystemExceptionNoti.EXCEPTION_CONFIGURE_2);
		}
		return;
	}
}
