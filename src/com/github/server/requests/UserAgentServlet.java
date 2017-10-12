package com.github.server.requests;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import eu.bitwalker.useragentutils.Browser;
import eu.bitwalker.useragentutils.Manufacturer;
import eu.bitwalker.useragentutils.OperatingSystem;
import eu.bitwalker.useragentutils.RenderingEngine;
import eu.bitwalker.useragentutils.UserAgent;
import eu.bitwalker.useragentutils.Version;

/**
 * URL : http://localhost:8080/SteamingServlet/UserAgent
 * 
 * Servlet implementation class UserAgent
 * 
 * https://github.com/HaraldWalker/user-agent-utils
 * https://github.com/HaraldWalker/user-agent-utils/releases
 */
@WebServlet("/UserAgent")
public class UserAgentServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
		String header = req.getHeader("User-Agent");
		UserAgent userAgent = UserAgent.parseUserAgentString( header);
		
		OperatingSystem os = userAgent.getOperatingSystem();
		String platform = os.getName();
		
		System.out.println("Operation System : "+platform);
		System.out.println("Operation Manufacturer : "+os.getManufacturer().getName());
		
		Browser browser = userAgent.getBrowser();
		String browserName = browser.getName(); // or browser.getGroup().getName();
		Version browserVersion = userAgent.getBrowserVersion();
		System.out.println("The user is using browser " + browserName + " - version " + browserVersion);
		
		String majorVersion = browserVersion.getMajorVersion();
		String minorVersion = browserVersion.getMinorVersion();
		System.out.format("Browser Versions Maj:[%s], Min:[%s]\n", majorVersion, minorVersion);
		
		RenderingEngine renderingEngine = browser.getRenderingEngine();
		String engine = renderingEngine.toString();
		
		Manufacturer manufacturer = browser.getManufacturer();
		System.out.println("Browser Manufacturer : "+manufacturer);
		
		UserAgentPojo pojo = new UserAgentPojo();
		pojo.setBrowserName(browserName);
		pojo.setVersion(browserVersion.toString());
		pojo.setMajour(majorVersion);
		pojo.setMinor(minorVersion);
		pojo.setPlatfomName(platform);
		pojo.setEngine(engine);
		
		PrintWriter writer = resp.getWriter();
		writer.append("<div>");
		writer.append( pojo.toJSONString() );
		writer.append("</div>");
		
	}
}