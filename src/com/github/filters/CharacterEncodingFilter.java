package com.github.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;

/**
 * Servlet Filter implementation class CharacterEncodingFilter
 * 
 * http://tomcat.apache.org/tomcat-8.0-doc/config/filter.html
 * 
 * @author yashwanth.m
 */
@WebFilter("/msg")
public class CharacterEncodingFilter implements Filter {
	@Override
	public void init(FilterConfig filterConfig) throws ServletException {
	}
	@Override
	public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
		// place your code here
		
		// https://wiki.apache.org/tomcat/FAQ/CharacterEncoding#Q8
		System.out.println("Inside filter.");
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		response.setContentType("text/html; charset=UTF-8");

		// pass the request along the filter chain
		chain.doFilter(request, response);
	}
	@Override
	public void destroy() {
	}
}
