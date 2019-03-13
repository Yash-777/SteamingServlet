package com.github.unicode;

import java.io.IOException;
import java.io.PrintWriter;
import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.net.URLEncoder;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Test URL « 
 * http://localhost:8080/SteamingServlet/locale?message="יאש"
 * http://localhost:8080/SteamingServlet/locale?message="Gần đây"
 * http://localhost:8080/SteamingServlet/locale?message=%22%D7%99%D7%90%D7%A9%22
 * 
 * http://localhost:8080/SteamingServlet/msg/locale?message=%22%D7%99%D7%90%D7%A9%22
 * 
 * @author yashwanth.m
 *
 */
@WebServlet(urlPatterns = { "/msg/locale", "/locale" }, loadOnStartup = 1 )
public class UnicodeTest extends HttpServlet {

	private static final long serialVersionUID = 5081877L;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//response.setCharacterEncoding("UTF-8");
		
		String unicodeMessage = URLDecoder.decode( request.getParameter("message"), "UTF-8");
				// request.getParameter("message"); // "×××©"
				//new String(request.getParameter("message").getBytes("ISO-8859-1"), "UTF-8");
		System.out.println("Unicode Message :"+ request.getParameter("message") );
		
		try {
			System.out.println("windows-1255 : " + new String(unicodeMessage.getBytes("UTF-8"), "UTF-8") );
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		}
		
		UnicodeChars.insertAutoIncrement_SQL("Yash_777", "Telugu", unicodeMessage);
		
		PrintWriter writer = response.getWriter();
		if( unicodeMessage != null & !unicodeMessage.equals("") ) {
			writer.append("Received Message : "+ URLEncoder.encode( unicodeMessage, "UTF-8"));
		} else {
			writer.append("Not Received any Message.");
		}
		writer.flush();
		writer.close();
	}
}
