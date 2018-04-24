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

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;

import sun.net.www.http.HttpClient;

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
		
		Request.Get("http://targethost/homepage")
	    .execute().returnContent();
		
		session=req.getSession();
		session.setAttribute("flag", "1");
		String year= req.getParameter("year");
		String subject = req.getParameter("subject");
		//req.getRequestDispatcher("/lab5calc").forward(req, res);
		//NEW EVERYTHING HERE BELOW
		final String calcUrl = "http://localhost:8001/lab5-calc/lab5calc";
		final String mapUrl = "http://localhost:8001/lab5-map/lab5map";
	    //private String contentType = "application/json-impl";
	    final String charSet = "UTF-8";
	   
	    CloseableHttpClient httpclient = HttpClients.createDefault();
        
            HttpGet httpget = new HttpGet(mapUrl);
        List <NameValuePair> nvps = new ArrayList <NameValuePair>();
        nvps.add(new BasicNameValuePair("year", year));
        nvps.add(new BasicNameValuePair("subject", subject));
        httpPost.setEntity(new UrlEncodedFormEntity(nvps));
        CloseableHttpResponse response2 = httpclient.execute(httpPost);

        try {
            System.out.println(response2.getStatusLine());
            HttpEntity entity2 = response2.getEntity();
            //ADDING HTTPCLIENT CALL MAP METHOD
            String grade = (String) session.getAttribute("grade");
            //HttpPost httpPost2 = new HttpPost(mapUrl);
            nvps.add(new BasicNameValuePair("grade", grade));
           //httpPost2.setEntity(new UrlEncodedFormEntity(nvps));
            //CloseableHttpResponse response3 = httpclient.execute(httpPost2);
            //HttpEntity entity3 = response3.getEntity();
            StringBuffer pageBuf = new StringBuffer();
    		//pageBuf.append("\n\t<br/>Year: " + entity3.toString());
    		pageBuf.append("\n\t<br/>Subject: " + entity2.toString());
    		
    		PrintWriter out = res.getWriter();
    		out.println(pageBuf.toString());
            //EntityUtils.consume(entity3);
            
            // do something useful with the response body
            // and ensure it is fully consumed
            EntityUtils.consume(entity2);
        } finally {
            response2.close();
        } 
        httpclient.close();
	    
	}
}
