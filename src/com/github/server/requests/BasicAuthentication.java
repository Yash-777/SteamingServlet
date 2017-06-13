package com.github.server.requests;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * <p>Servlet implementation class <a href="http://java.boot.by/wcd-guide/ch05s03.html">BasicAuthentication.</a> * 
 * HTTP Basic Authentication, which is based on a username and password, is the authentication
 * mechanism defined in the HTTP/1.0 specification. A web server requests a web client to authenticate the user.</p>
 * 
 * <ul>Tomcat Server User Authentication:
 * <li> Add user's and their role's in <code>tomcat-users.xml</code> file.<pre>
&lttomcat-users&gt
	&ltrole rolename="manager"/&gt
	&ltuser username="user1" password="user1Pass" roles="manager"/&gt
&lt/tomcat-users&gt
</pre></li>
 * <li> These users are created statically fixed to access the resource available in the container. If we want to add
 * the user we need to add the details in <code>tomcat-users.xml</code> file then we need to start the server.
 * 
 * @author yashwanth.m
 */
@WebServlet("/auth/BasicAuthentication")
public class BasicAuthentication extends HttpServlet {
	private static final long serialVersionUID = 1L;

	@Override
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("=== GET ===");
		HttpRequestData.requestData(req);
		
		PrintWriter writer = resp.getWriter();
		writer.append("GET Served at: ").append(req.getContextPath());
	}
}
