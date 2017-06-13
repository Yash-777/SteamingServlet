package com.github.files;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.Enumeration;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;

/**
 * Servlet implementation class MultipartFileUpload
 * 
 * http://localhost:8080/SteamingServlet/fileUpload.jsp
 * <UL>Request Headers:
 * <LI>multipart/form-data; boundary=----WebKitFormBoundaryd4jLOs0yMIGimrVs</LI>
 * <LI>text/html,application/xhtml+xml,application/xml;q=0.9,image/webp,*-*;q=0.8</LI>
 * </UL>
 * 
 * <p>https://developer.amazon.com/public/solutions/alexa/alexa-voice-service/docs/avs-http2-requests</p>
 * <p>https://github.com/Yash-777/LearnJava/wiki/Java-Mail-API</p>
 * <OL>The following topics are addressed here:

 * <LI>The @MultipartConfig Annotation</LI>

<UL>The <a href="http://docs.oracle.com/javaee/6/tutorial/doc/gmhal.html">@MultipartConfig</a>
 annotation supports the following optional attributes:

<LI><b>location:</b> An absolute path to a directory on the file system. The location attribute does not
support a path relative to the application context. This location is used to store files temporarily
while the parts are processed or when the size of the file exceeds the specified fileSizeThreshold setting.
The default location is "".</LI>

<LI><b>fileSizeThreshold:</b> The file size in bytes after which the file will be temporarily stored on disk.
The default size is 0 bytes.</LI>

<LI><b>MaxFileSize:</b> The maximum size allowed for uploaded files, in bytes. If the size of any uploaded 
file is greater than this size, the web container will throw an exception (IllegalStateException).
The default size is unlimited.</LI>

<LI><b>maxRequestSize:</b> The maximum size allowed for a multipart/form-data request, in bytes. 
The web container will throw an exception if the overall size of all uploaded files exceeds this threshold. 
The default size is unlimited.</LI>

For, example, the @MultipartConfig annotation could be constructed as follows:
<pre>
@MultipartConfig(location="/tmp", fileSizeThreshold=1024*1024, 
    maxFileSize=1024*1024*5, maxRequestSize=1024*1024*5*5)
</pre>
</UL>

 * <LI>The getParts and getPart Methods</LI>
<UL>Servlet 3.0 supports two additional HttpServletRequest methods:
<LI>Collection<Part> getParts()</LI>
<LI>Part getPart(String name)</LI>

The request.getParts() method returns collections of all Part objects. 
If you have more than one input of type file, multiple Part objects are returned.
</UL>
 * </OL>
 * @author yashwanth.m
 *
 */
@WebServlet(name = "MultipartFileUpload", urlPatterns = {"/files/UploadFile"})
@MultipartConfig(fileSizeThreshold=1024*1024*2,	// 2MB
				maxFileSize=1024*1024*10,		// 10MB
				maxRequestSize=1024*1024*50)	// 50MB
public class MultipartFileUpload extends HttpServlet {

	private static final long serialVersionUID = 1L;
	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		System.out.println("http://localhost:8080/SteamingServlet/fileUpload.jsp");

		Enumeration<String> headerNames = request.getHeaderNames();
		if (headerNames != null) {
			while (headerNames.hasMoreElements()) {
				System.out.println("Header: " + request.getHeader(headerNames.nextElement()));
			}
		}
		
		File directoryPath = new File("E:\\");
		
		for (Part part : request.getParts()) {
			System.out.println("===== ----- Multipart Data ----- =====");
			File tempFile = new File( extractFileName(part) );
			int index = tempFile.getName().lastIndexOf('.');
			String suffix = tempFile.getName().substring(index);
			String fileName = tempFile.getName().substring(0, index);
			
			System.out.format("File Name: %s, Extension: %s \n", fileName, suffix);
			File file = File.createTempFile(fileName, suffix, directoryPath);
			// Java 7 - java.nio.file.Files
			/*try ( InputStream input = part.getInputStream() ) {
				Files.copy(input, file.toPath(), StandardCopyOption.REPLACE_EXISTING);
			}*/
			InputStream input = part.getInputStream();
			copyFileUsingStream(input, file);
		}

		request.setAttribute("message", "Upload has been done successfully!");
		getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);
	}

	/**
	 * Extracts file name from HTTP header content-disposition
	 * 
	 * <P> Content-Disposition: form-data; name="file"; filename="sample.txt"
	 */
	private String extractFileName(Part part) {
		String contentDisp = part.getHeader("content-disposition");
		String[] items = contentDisp.split(";");
		for (String s : items) {
			if (s.trim().startsWith("filename")) {
				//return s.substring(s.indexOf("=") + 2, s.length()-1);
				return s.substring(s.indexOf('=') + 1).trim().replace("\"", "");
			}
		}
		return null;
	}
	
	/**
	 * <B>Java Copy File – Stream</B>
	 * 
	 * <P>This is the conventional way of file copy in java, here we create two Files, source and destination.
	 * Then we create InputStream from source and write it to destination file using OutputStream for java
	 * copy file operation.</p>
	 * 
	 * @param is           « the input stream
	 * @param dest         « the output file
	 * @throws IOException « if any file is unavailable then throws exception.
	 */
	void copyFileUsingStream(InputStream is, File dest) throws IOException {
		long start = System.nanoTime();
		OutputStream os = null;
		try {
			os = new FileOutputStream(dest);
			byte[] buffer = new byte[1024];
			int length;
			while ((length = is.read(buffer)) > 0) {
				os.write(buffer, 0, length);
			}
		} finally {
			is.close();
			os.close();
		}
		System.out.println("Time taken by Stream Copy = "+(System.nanoTime()-start));
	}
}