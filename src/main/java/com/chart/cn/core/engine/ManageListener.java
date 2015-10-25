package com.chart.cn.core.engine;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class ManageListener extends HttpServlet{

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		
		super.doGet(req, resp);
	}

	@Override
	protected void doPost(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		resp.setCharacterEncoding("utf-8");
		String addName = req.getParameter("addUserName");
		String userId = UserSessionMap.getInstance().getSessionId(addName);
		if(userId!=null&&userId!=""){
			resp.getWriter().write(userId);
		}else{
			resp.getWriter().write("no-user");
		}
	}
	
}
