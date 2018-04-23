/*
 * Lab5Servlet.java
 *
 * Copyright:  2008 Kevin A. Gary All Rights Reserved
 *
 */
package edu.asupoly.ser422.lab5;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

/**
 * @author Kevin Gary
 *
 */
@SuppressWarnings("serial")
public class Lab5Servlet extends HttpServlet {
	HttpSession session;
	
	public void init(ServletConfig sc) throws ServletException {
		super.init(sc);
	}
	
	/*REFACTOR ORIGINAL SERVLET:
	 * 1. REMOVE SINGLETON - DONE
	 * 2. MAKE NETWORK CALLS TO LAB5CALC OR LAB5MAP
	 * 3. DETERMINE LIGHTWEIGHT FORMAT TO RETURN DATA TO SERVLET
	 * 3a. CONSIDER HTTP SESSIONS
	 * 4. RENDER RESPONSE WITH SERVLET
	 */

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		session = req.getSession();
		if (session.getAttribute("flag").equals("1")) {
			req.getRequestDispatcher("/lab5map").forward(req, res);
		}
		
		
		StringBuffer pageBuf = new StringBuffer();
		pageBuf.append("\n\t<br/>Year: " + session.getAttribute("year"));
		pageBuf.append("\n\t<br/>Subject: " + session.getAttribute("subject"));
		pageBuf.append("\n\t<br/>Grade: " + session.getAttribute("grade"));
		pageBuf.append("\n\t<br/>Letter: " + session.getAttribute("letter"));
		
		PrintWriter out = res.getWriter();
		out.println(pageBuf.toString());
		
	}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		session=req.getSession();
		session.setAttribute("flag", "1");
		req.getRequestDispatcher("/lab5calc").forward(req, res);
		}
}
