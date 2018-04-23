package edu.asupoly.ser422.lab5;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.util.Properties;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class Lab5Map extends HttpServlet {
	HttpSession session;

	public void init(ServletConfig sc) throws ServletException {

		super.init(sc);
	} // end method

	public void doPost(HttpServletRequest req, HttpServletResponse res) {
		session = req.getSession();
		String letter = "E";

		
		double grade = (double) session.getAttribute("grade");
				
		if (grade >= 60.0)
			letter = "D";
		if (grade >= 70.0)
			letter = "C";
		if (grade >= 77.0)
			letter = "C+";
		if (grade >= 80.0)
			letter = "B-";
		if (grade >= 83.0)
			letter = "B";
		if (grade >= 88.0)
			letter = "B+";
		if (grade >= 90.0)
			letter = "A-";
		if (grade >= 93.0)
			letter = "A";
		if (grade >= 98.0)
			letter = "A+";
		if (grade < 0.0)
			letter = "I";
		session.setAttribute("flag", "0");

		res.setContentType("text/html");
		session.setAttribute("letter", letter);
		try {
			res.sendRedirect(req.getContextPath()+"/lab5");
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	public void doGet(HttpServletRequest req, HttpServletResponse res) {
		doPost(req,res);
	}

}
