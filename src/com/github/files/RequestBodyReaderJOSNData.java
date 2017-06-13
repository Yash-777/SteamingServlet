package com.github.files;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Servlet implementation class RequestBodyJOSNData.
 * 
 * http://localhost:8080/SteamingServlet/BodyData.jsp
 * <UL>Request Headers:
 * <LI>application/x-www-form-urlencoded</LI>
 * <LI>text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*-*;q=0.8</LI>
 * </UL>
 */
@WebServlet("/postJSON")
public class RequestBodyReaderJOSNData extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		
		// https://stackoverflow.com/a/25247239/5081877
		Enumeration<String> headerNames = req.getHeaderNames();
		if (headerNames != null) {
			while (headerNames.hasMoreElements()) {
				System.out.println("Header: " + req.getHeader(headerNames.nextElement()));
			}
		}
		
		
		StringBuilder sb = new StringBuilder();
		BufferedReader br = req.getReader();
		String str = null;
		while ((str = br.readLine()) != null) {
			sb.append(str);
		} // SB: {"username":"yashwanth.merugu@gmail.com","password":"Yash@777"}
		System.out.println("SB : "+sb);
		
		String postJSON = sb.toString();
		
		if ( postJSON.startsWith("{") ) {
			System.out.println("JSON String");
		} else {
			postJSON = "{\"" + postJSON;
			postJSON = postJSON.replaceAll("=", "\":\"");
			postJSON = postJSON.replaceAll("&", "\",\"");
			postJSON += "\"}";
			System.out.println("JOSN : "+postJSON);
		}
		
		int indexOf = sb.indexOf("{", 0);
		System.out.println("Non JSON : "+indexOf);
		
		if( indexOf > -1 ) {
			sb.append("{", 0, 1);
			
		}
		JSONObject jObj = null;
		String userName = null, password = null;
		
		try {
			jObj = new JSONObject( postJSON );
			
			/*To check JSONObject contains a specific key.
			 * https://developer.android.com/reference/org/json/JSONObject.html
			 * https://stackoverflow.com/q/17487205/5081877
			 * */
			if( jObj.has("first_name")) {
				userName = jObj.getString("first_name");
			}
			password = jObj.getString("last_name");
		} catch (JSONException e) {
			e.printStackTrace();
		}
		
		/*Using writer to send a message to requested resource*/
		/*PrintWriter writer = resp.getWriter();
		writer.print("Details Received successfully! \n userName:"+userName+"\t password:"+password);*/
		
		System.out.println("Details Resceived successfully! \n userName:"+userName+"\t password:"+password );
		req.setAttribute("message", "Details Resceived successfully! \n userName:"+userName+"\t password:"+password);
		getServletContext().getRequestDispatcher("/message.jsp").forward(req, resp);
		//resp.sendRedirect( "../message.jsp" );
		//resp.setHeader("Location", jspLoaction);
		
		/*JSONObject jobj = new JSONObject();
		//String urlToRedirect = "message.jsp";
		try {
			jobj.put("url", "http://localhost:8080/SteamingServlet/message.jsp");
			jobj.put("message", "Details Resceived successfully! \n userName:"+userName+"\t password:"+password);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		resp.getWriter().write(jobj.toString());*/
	}
}
