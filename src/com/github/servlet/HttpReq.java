package com.github.servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * http://127.0.0.1:8080/SteamingServlet/lifecycle/http
 * 
 * https://javaee.github.io/servlet-spec/downloads/servlet-3.1/Final/servlet-3_1-final.pdf
 * 
 * Servlet implementation class LifeCycleMethods
 */
@WebServlet("/lifecycle/http")
public class HttpReq extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public HttpReq() {
		super(); // Default call to parent constructor.
		System.out.println("Object Created...");
	}
	public void getCurrentStack(StackTraceElement e[]) {
		boolean doNext = false;
		for (StackTraceElement s : e) {
			if (doNext) {
				System.out.format("Stack Trace [C:M]:[%s:%s()]\n",s.getClassName(), s.getMethodName());
			}
			doNext = s.getMethodName().equals("getStackTrace");
		}
	}
	
	@Override
	public void init() throws ServletException {
		getCurrentStack(Thread.currentThread().getStackTrace());
	}
	@Override
	public void destroy() {
		getCurrentStack(Thread.currentThread().getStackTrace());
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("===== ----- ----- =====");
		getCurrentStack(Thread.currentThread().getStackTrace());
		
		response.getWriter().append("Served at: ").append(request.getContextPath());
		destroy();
	}
}
