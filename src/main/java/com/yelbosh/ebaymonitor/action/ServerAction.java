package com.yelbosh.ebaymonitor.action;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.yelbosh.ebaymonitor.global.MonitorDataBuffer;
import com.yelbosh.ebaymonitor.global.UsageInfo;
import com.yelbosh.ebaymonitor.util.GsonWrapper;
import com.yelbosh.ebaymonitor.util.NodeInfo;
import com.yelbosh.ebaymonitor.util.SystemConfig;
import com.yelbosh.ebaymonitor.util.Tools;

/**
 * Servlet implementation class UserAction
 */
public class ServerAction extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ServerAction() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		SystemConfig system = SystemConfig.getInstance();
		if(!system.isInitialized()){
			response.getWriter().append(GsonWrapper.exceptionMsg("Server Internal Error! please contact the administrator!"));
		}
		String name = request.getParameter("name");
		NodeInfo node;
		if(Tools.isIPAdress(name))
			node = Tools.findNodeByIp(SystemConfig.getInstance().getNodelist(), name);
		else 
			node = Tools.findNodeByName(SystemConfig.getInstance().getNodelist(), name);
		if(node == null){
			response.getWriter().append(GsonWrapper.exceptionMsg("can not find the input server, please check your name or ip address!"));
		}else
			response.sendRedirect("/monitor.html?id="+node.id);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
