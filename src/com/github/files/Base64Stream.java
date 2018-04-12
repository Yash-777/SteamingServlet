package com.github.files;

import java.io.DataInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class FilesStream
 * 
 * EX: http://way2java.com/servlets/sending-image-client-servlet-example/
 * 
 * Test URL � http://localhost:8080/SteamingServlet/files/base64?userField=image
 * @author yashwanth.m
 */
@WebServlet(urlPatterns = { "/files/base64" }) /*asyncSupported = true,*/
public class Base64Stream extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * https://www.w3schools.com/html/html5_video.asp
	 * https://www.w3schools.com/html/mov_bbb.mp4
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException {

		String userName = req.getParameter("userField");
		System.out.println("UserName :"+userName);
		if( userName != null & !userName.equals("") ) {
			
			/*To send binary data in a MIME body response, use the ServletOutputStream returned by getOutputStream.*/
			ServletOutputStream sos = res.getOutputStream();// for binary stream of file
			File f = null;
			if( userName.equalsIgnoreCase("IMAGE") ) {
				f = new File("D:\\SVN_Commit.PNG");
				res.setContentType("image/png"); // see different MIME type
			} else if( userName.equalsIgnoreCase("VIDEO") ) {
				f = new File("D:\\mov_bbb.mp4");
				res.setContentType("video/mp4"); // see different MIME type
			}
			
			if( f != null ) {
				DataInputStream dis = new DataInputStream(new FileInputStream(f));
				byte[] byteArray = new byte[(int) f.length()];
				
				try {
					dis.readFully(byteArray); // now the array contains the image
				} catch (Exception e) {
					byteArray = null;
				} finally {
					dis.close();
				}
				sos.write(byteArray); // send the byte array to client
			}
			sos.close();
		} else {
			res.setContentType("text/html;charset=UTF-8");
			PrintWriter writer = res.getWriter();
			File f = null;
			String html = null;
			if( userName.equalsIgnoreCase("IMAGE") ) {
				f = new File("D:\\SVN_Commit.PNG");
				html = "<img src='data:image/png;base64,{{-}}'>";
				
			} else {
				f = new File("D:\\mov_bbb.mp4");
				html = "<video controls><source type='video/mp4' src='data:video/mp4;base64,{{-}}' /></video>";
			}
			
			if( f != null ) {
				DataInputStream dis = new DataInputStream(new FileInputStream(f));
				byte[] byteArray = new byte[(int) f.length()];
				
				try {
					dis.readFully(byteArray); // now the array contains the image
				} catch (Exception e) {
					byteArray = null;
				} finally {
					dis.close();
				}
				String encodedfile = 
						new String(org.apache.commons.codec.binary.Base64.encodeBase64( byteArray ), "UTF-8");
				
				html = html.replace("{{-}}", encodedfile);
				writer.append(html);
			}
		}
	}
}