package com.yelbosh.ebaymonitor.action;

import java.io.IOException;
import java.util.HashMap;
import java.util.LinkedList;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yelbosh.ebaymonitor.global.MonitorDataBuffer;
import com.yelbosh.ebaymonitor.global.UsageInfo;
import com.yelbosh.ebaymonitor.util.GsonWrapper;
import com.yelbosh.ebaymonitor.util.SystemConfig;

/**
 * Servlet implementation class MonitorAction
 */
public class MonitorAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MonitorAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		String id = request.getParameter("id");
		String type = request.getParameter("type");
		if(type == null) type = "0";
		SystemConfig system = SystemConfig.getInstance();
		if(!system.isInitialized()){
			response.getWriter().append(GsonWrapper.exceptionMsg("Server Internal Error! please contact the administrator!"));
		}
		if(id == null){
			response.getWriter().append(GsonWrapper.exceptionMsg("wrong server id! please check your input!"));
			return;
		}
		if("0".equals(type)){ //return the newest data
			UsageInfo usage = MonitorDataBuffer.getInstance().read(id);
			response.getWriter().append(GsonWrapper.rtnUsageData(usage));
			return;
		}else{
			LinkedList<UsageInfo> usageList = MonitorDataBuffer.getInstance().readAll(id);
			response.getWriter().append(GsonWrapper.rtnUsageDataList(usageList));
			return;
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
