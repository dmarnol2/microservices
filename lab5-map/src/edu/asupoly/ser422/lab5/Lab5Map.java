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

import org.json.simple.JSONObject;


public class Lab5Map extends HttpServlet {
	

	public void init(ServletConfig sc) throws ServletException {

		super.init(sc);
	} // end method

	public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
		
		String letter = "E";
		PrintWriter out = res.getWriter();
		JSONObject obj = new JSONObject();

		
		double grade =90;
				
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
		
		res.setContentType("application/json");
		res.setCharacterEncoding("UTF-8");
		obj.put("letter", letter);
		out.print(obj);

	}
	public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException {
		doPost(req,res);
	}

}
