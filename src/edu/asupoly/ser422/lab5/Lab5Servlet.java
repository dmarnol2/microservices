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
	
	public void init(ServletConfig sc) throws ServletException {
		super.init(sc);
	}

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		StringBuffer pageBuf = new StringBuffer();
		double grade;
		String year = req.getParameter("year");
		String subject = req.getParameter("subject");

		if (year != null && !year.trim().isEmpty()) {
			pageBuf.append("<br/>Year: " + year);
		}
		if (subject != null && !subject.trim().isEmpty()) {
			pageBuf.append("<br/>Subject: " + subject);
		}

		Lab5Calc calc = null;
		try {
			calc = new Lab5Calc();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Lab5Map map = null;
		try {
			map = new Lab5Map();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		/*try {
			service = Lab5Service.getService();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		//if (service == null) {
		//	pageBuf.append("\tSERVICE NOT AVAILABLE");
		//} else {
			grade = calc.calculateGrade(year, subject);
			pageBuf.append("\n\t<br/>Grade: " + grade);
			pageBuf.append("\n\t<br/>Letter: " + map.mapToLetterGrade(grade));
		//}

		// some generic setup - our content type and output stream
		res.setContentType("text/html");
		PrintWriter out = res.getWriter();

		out.println(pageBuf.toString());
	}
	public void doPost(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
		doGet(req, res);
	}
}
