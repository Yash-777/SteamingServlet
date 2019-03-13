package com.github.servlet;

import java.io.IOException;

import javax.servlet.GenericServlet;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

public class GenreicReq extends GenericServlet {
	private static final long serialVersionUID = 1L;

	public GenreicReq() {
		System.out.println("Object Created...");
	}
	
	@Override
	public void init() throws ServletException {
		getCurrentStack(Thread.currentThread().getStackTrace());
	}
	@Override
	public void destroy() {
		getCurrentStack(Thread.currentThread().getStackTrace());
	}
	
	@Override
	public void service(ServletRequest arg0, ServletResponse arg1) throws ServletException, IOException {
		System.out.println("===== ----- ----- =====");
		try {
			HttpServletRequest request = (HttpServletRequest) arg0;
			HttpServletResponse response = (HttpServletResponse) arg1;
			getCurrentStack(Thread.currentThread().getStackTrace());
			
			response.getWriter().append("Served at: ").append(request.getContextPath());
			destroy();
		} catch (ClassCastException e) {
			throw new ServletException("non-HTTP request or response");
		}
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
	
}
