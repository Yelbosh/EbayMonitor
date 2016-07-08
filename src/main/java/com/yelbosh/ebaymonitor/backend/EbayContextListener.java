package com.yelbosh.ebaymonitor.backend;

import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;

/**
 * Application Lifecycle Listener implementation class EbayContextListener
 *
 */
public class EbayContextListener implements ServletContextListener {

    /**
     * Default constructor. 
     */
    public EbayContextListener() {
        // TODO Auto-generated constructor stub
    }

	/**
     * @see ServletContextListener#contextInitialized(ServletContextEvent)
     */
    public void contextInitialized(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    	System.out.println("init");
    	PullDataService dataService = PullDataService.getInstance();
    	dataService.initialize();
    }

	/**
     * @see ServletContextListener#contextDestroyed(ServletContextEvent)
     */
    public void contextDestroyed(ServletContextEvent arg0)  { 
         // TODO Auto-generated method stub
    	System.out.println("closed");
    	PullDataService dataService = PullDataService.getInstance();
    	dataService.shutdown();
    }
	
}
