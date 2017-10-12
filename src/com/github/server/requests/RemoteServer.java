package com.github.server.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.catalina.realm.GenericPrincipal;

/**
 * Servlet implementation class RemoteServer
 */
@WebServlet("/wd/hub/session")
public class RemoteServer extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("=== /wd/hub/session - Get ===");
		HttpRequestData.requestData(req);
		
		PrintWriter writer = resp.getWriter();
		writer.append("GET Served at: ").append(req.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		System.out.println("=== /wd/hub/session - POST ===");
		
		// Body Content
		BufferedReader br = req.getReader();
		StringBuilder sb = new StringBuilder();
		String str = null;
		while ((str = br.readLine()) != null) {
			sb.append(str);
		}
		HttpRequestData.capabilitiesJSON(sb.toString());
		
		GenericPrincipal userPrincipal = (GenericPrincipal) req.getUserPrincipal();
		String userName = userPrincipal.getName();
		String password = userPrincipal.getPassword();
		
		System.out.format("User: %s \t Pass: %s \n", userName, password);
		
		PrintWriter writer = resp.getWriter();
		writer.append("GET Served at: ").append(req.getContextPath());
	}
}
