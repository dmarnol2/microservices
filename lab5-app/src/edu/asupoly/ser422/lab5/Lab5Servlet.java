/*
 * Lab5Servlet.java
 *
 * Copyright:  2008 Kevin A. Gary All Rights Reserved
 *
 */
package edu.asupoly.ser422.lab5;

import java.io.IOException;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;
import org.apache.http.client.fluent.Request;
import javax.servlet.*;
import javax.servlet.http.*;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import sun.net.www.http.HttpClient;
import org.json.simple.JSONObject;
import org.json.simple.parser.JSONParser;
import org.json.simple.parser.ParseException;

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

	/* (non-Javadoc)
	 * @see javax.servlet.http.HttpServlet#doGet(javax.servlet.http.HttpServletRequest, javax.servlet.http.HttpServletResponse)
	 */
	public void doGet(HttpServletRequest req, HttpServletResponse res)
			throws ServletException, IOException {
				doPost(req,res);
			}
	
	public void doPost(HttpServletRequest req, HttpServletResponse res)
				throws ServletException, IOException {

		StringBuffer pageBuf = new StringBuffer();
		String year= req.getParameter("year");
		String subject = req.getParameter("subject");
		
		final String calcUrl = "http://localhost:8081/lab5-calc/lab5calc";
		final String mapUrl = "http://localhost:8081/lab5-map/lab5map";
	    final String contentType = "application/json";
	    final String charSet = "UTF-8";
	   
	    CloseableHttpClient httpclient = HttpClients.createDefault();
	    HttpPost post = new HttpPost(calcUrl);
	    ArrayList <NameValuePair> vals = new ArrayList <NameValuePair>();
        vals.add(new BasicNameValuePair("year", year));
        vals.add(new BasicNameValuePair("subject", subject));
        
        post.setEntity(new UrlEncodedFormEntity(vals));
        CloseableHttpResponse response = httpclient.execute(post);
        String calcContent;
        String grade="";
        JSONParser parser = new JSONParser();
        JSONObject jsonCalc = null;

        try {
            System.out.println(response.getStatusLine());
            HttpEntity entity = response.getEntity();
            calcContent =  EntityUtils.toString(entity);
            	try {
					jsonCalc = (JSONObject) parser.parse(calcContent);
					grade = (String) jsonCalc.get("grade");
					} catch (ParseException e) {
					e.printStackTrace();
					}
            		EntityUtils.consume(entity);
            		} finally {
            		response.close();
        		} // end try

        //network call to lab5map
        HttpPost post2 = new HttpPost(mapUrl);
	    //List <NameValuePair> nvps2 = new ArrayList <NameValuePair>();
        vals.add(new BasicNameValuePair("grade", grade));
        //nvps2.add(new BasicNameValuePair("subject", subject));
        post2.setEntity(new UrlEncodedFormEntity(vals));
        CloseableHttpResponse response2 = httpclient.execute(post2);
        String mapContent;
        try {
            System.out.println(response2.getStatusLine());
            HttpEntity entity2 = response2.getEntity();
            mapContent =  EntityUtils.toString(entity2);
            EntityUtils.consume(entity2);
		    } finally {
            response2.close();
            }  // end try

            httpclient.close();

        JSONParser parser2 = new JSONParser();
        JSONObject jsonMap = null;
		
		try {
			jsonMap = (JSONObject) parser2.parse(mapContent);
		} catch (ParseException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		pageBuf.append("\n\t<br/>Year: " + year);//jsonCalc.get("year"));
		pageBuf.append("\n\t<br/>Subject: " + subject);//jsonCalc.get("subject"));
		pageBuf.append("\n\t<br/>Grade: " + grade); //jsonCalc.get("grade"));
		pageBuf.append("\n\t<br/>Letter: " + jsonMap.get("letter"));
        PrintWriter out = res.getWriter();
		out.println(pageBuf.toString());

	} // end doPost

} // end lab5servlet

