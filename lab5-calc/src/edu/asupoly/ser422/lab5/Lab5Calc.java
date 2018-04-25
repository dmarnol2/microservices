package edu.asupoly.ser422.lab5;

import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.Properties;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.json.simple.JSONObject;

public class Lab5Calc extends HttpServlet{
	// These are the possible queries
		private static String queryAll = "SELECT grade FROM Enrolled";
		private static String queryYear = "SELECT grade from Enrolled JOIN Student ON (Enrolled.sid=Student.id) WHERE year=";
		private static String querySubject = "SELECT grade from Enrolled JOIN Course ON (Enrolled.crsid=Course.id) WHERE subject='";
		private static String queryYearSubject = "SELECT grade from (Student JOIN Enrolled ON (Student.id=Enrolled.sid)) JOIN Course ON (Enrolled.crsid=Course.id) WHERE year=";

		private String __jdbcUrl    = null;
		private String __jdbcUser   = null;
		private String __jdbcPasswd = null;
		private String __jdbcDriver = null;
		
		
		//private static String _filename = null;

		public void init(ServletConfig sc) throws ServletException {
			super.init(sc);
			} 

		@SuppressWarnings("null")
		public void doPost(HttpServletRequest req, HttpServletResponse res) throws IOException {
			PrintWriter out = res.getWriter();
			JSONObject obj = new JSONObject();
			String year = req.getParameter("year");
			String subject = req.getParameter("subject");
			
			Properties props = new Properties();
			try {
				InputStream propFile = this.getClass().getClassLoader().getResourceAsStream("lab5db.properties");
				props.load(propFile);
				propFile.close();
			}
			catch (IOException ie) {
				ie.getMessage();
				try {
					throw new Exception("Could not open property file");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.getMessage();
				}
			}

			__jdbcUrl    = props.getProperty("jdbc.url");
			__jdbcUser   = props.getProperty("jdbc.user");
			__jdbcPasswd = props.getProperty("jdbc.passwd");
			__jdbcDriver = props.getProperty("jdbc.driver");
			try {
				Class.forName(__jdbcDriver); // ensure the driver is loaded
			}
			catch (ClassNotFoundException cnfe) {
				System.out.println("*** Cannot find the JDBC driver");
				cnfe.printStackTrace();
				try {
					throw new Exception("Cannot initialize service from property file");
				} catch (Exception e) {
					// TODO Auto-generated catch block
					e.getMessage();
				}
			}
		

			Connection conn = null;
			Statement stmt = null;
			ResultSet rs = null;
			double grade = 0.0;
			try {
				// Create the connection anew every time
				conn = DriverManager.getConnection(__jdbcUrl, __jdbcUser, __jdbcPasswd);

				stmt = conn.createStatement();
				// GET REQ PARAMETERS
				
				int iyear = 0;
				String query;
				if (req.getParameter("year") != null && !req.getParameter("year").trim().isEmpty()) {
					iyear = Integer.parseInt(req.getParameter("year"));
				}
				if (iyear <=4 && iyear >= 1 && subject != null && !subject.trim().isEmpty()) {
					query = queryYearSubject + year + " AND subject='" + subject + "'";
				} else if (iyear <=4 && iyear >= 1) {
					query = queryYear + year;
				} else if (subject != null && !subject.trim().isEmpty()) {
					query = querySubject + subject + "'";
				} else {
					query = queryAll;
				}
				System.out.println("Executing query " + query);
				
				rs = stmt.executeQuery(query);
				int count = 0;
				double gradesum = -1.0;
				while (rs.next()) {
					gradesum += rs.getDouble(1);
					count++;
				}
				if (count > 0) {
					grade = gradesum / count;
				}
			}
			catch (SQLException se) {
				System.out.println("*** Uh-oh! Database Exception");
				se.getErrorCode();
			}
			catch (Exception e) {
				System.out.println("*** Some other exception was thrown");
				e.getMessage();
			}
			finally {  // why nest all of these try/finally blocks?
				try {
					if (rs != null) { rs.close(); }
				} catch (Throwable t1) {
					t1.getMessage();
				}
				try {
					if (stmt != null) { stmt.close(); }
				} catch (Throwable t2) {
					t2.getMessage();
				}
				try {
					if (conn != null) { conn.close(); }
				}
				catch (Throwable t) {
					t.getMessage();
				}
			}
			String gradeVal = Double.toString(grade);
			res.setContentType("application/json");
			res.setCharacterEncoding("UTF-8");
			//obj.put("year", year);
			//obj.put("subject", subject);
			obj.put("grade", gradeVal);
			out.print(obj);

		} // end doPost

		public void doGet(HttpServletRequest req, HttpServletResponse res) throws IOException{
			doPost(req, res);

    	} // end doGet
    } // end Lab5Calc