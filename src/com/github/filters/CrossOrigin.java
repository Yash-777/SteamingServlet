package com.github.filters;

import java.io.IOException;
import javax.servlet.Filter;
import javax.servlet.FilterChain;
import javax.servlet.FilterConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletRequest;
import javax.servlet.ServletResponse;
import javax.servlet.annotation.WebFilter;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet Filter implementation class CrossOrigin
 * 
 * @author yashwanth.m
 *
 */
@WebFilter("/CrossOrigin")
public class CrossOrigin implements Filter {

	/**
	 * @see Filter#doFilter(ServletRequest, ServletResponse, FilterChain)
	 */
	public void doFilter(ServletRequest req, ServletResponse resp, FilterChain chain) throws IOException, ServletException {
		HttpServletResponse response = (HttpServletResponse) resp;
		setAccessControlHeaders(response);
		
		// pass the request along the filter chain
		chain.doFilter(req, resp);
	}

	public void init(FilterConfig fConfig) throws ServletException {
	}
	public void destroy() {
	}
	
	/**
	 * <P> Sets access control headers to allow cross-origin resource sharing from
	 * any origin. </p>
	 * 
	 * <P> Firstly sends an HTTP "Pre-Flighted" request by the OPTIONS method 
	 * to the resource on the other domain, in order to determine whether the actual 
	 * request is safe to send. </p>
	 * 
	 * <p> NOTE: Error 
	 * Failed to load http://localhost:8080/SteamingServlet/msg/locale?message=%D7%99%D7%90%D7%A9:
	 * Response to preflight request doesn't pass access control check:
	 * No 'Access-Control-Allow-Origin' header is present on the requested resource.
	 * Origin 'http://api.jquery.com' is therefore not allowed access.
	 * </P>
	 * @param response The response to modify.
	 * @see <a href="http://www.w3.org/TR/cors/">http://www.w3.org/TR/cors/</a>
	 */
	private void setAccessControlHeaders(HttpServletResponse response) {
		// An Access-Control-Allow-Origin (ACAO) header with a wild-card that allows all domains:
		response.setHeader("Access-Control-Allow-Origin", "*");
		//CORS also supports other types of HTTP requests [ GET, POST, PUT, DELETE, OPTIONS, HEAD ]
		response.setHeader("Access-Control-Allow-Methods", "GET, POST, PUT, DELETE, OPTIONS, HEAD");
		// The list of non-standard | custom HTTP headers.
		response.setHeader("Access-Control-Allow-Headers", "Accept, Content-Type, X-PINGOTHER, Origin, X-Requested-With"
				+ ", Access-Control-Allow-Headers, Authorization");
		// Tell client that this Pre-flight info is valid for 86400 seconds is 24 hours.
		response.setHeader("Access-Control-Max-Age", "86400");
	}
}
