package com.github.files;

import java.io.ByteArrayInputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Iterator;
import java.util.List;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.commons.codec.binary.Base64;
import org.apache.tomcat.util.http.fileupload.FileItem;
import org.apache.tomcat.util.http.fileupload.FileUploadException;
import org.apache.tomcat.util.http.fileupload.IOUtils;
import org.apache.tomcat.util.http.fileupload.disk.DiskFileItemFactory;
import org.apache.tomcat.util.http.fileupload.servlet.ServletFileUpload;


/**
 * Servlet implementation class ApacheFileUpload
 * 
 * http://localhost:8080/SteamingServlet/fileUpload.jsp
 */
@WebServlet({ "/Apache/FileUpload", "/fileUpload" })
public class ApacheFileUpload extends HttpServlet {
	private static final long serialVersionUID = 1L;
	public ApacheFileUpload() {
		super();
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//doGet(request, response);
		
		// https://commons.apache.org/proper/commons-fileupload/using.html
		commonsFileUpload(request, response);
	}
	
	public void commonsFileUpload( HttpServletRequest request, HttpServletResponse response) {
		
		// Check that we have a file upload request
		boolean isMultipart = ServletFileUpload.isMultipartContent(request);

		if (!isMultipart)
			throw new IllegalArgumentException("Not multipart content!");

		// Create a factory for disk-based file items
		DiskFileItemFactory factory = new DiskFileItemFactory();

		// Configure a repository (to ensure a secure temp location is used)
		ServletContext servletContext = this.getServletConfig().getServletContext();
		File repository = (File) servletContext.getAttribute("javax.servlet.context.tempdir");
		factory.setRepository(repository);

		// Create a new file upload handler
		ServletFileUpload upload = new ServletFileUpload(factory);

		// Parse the request
		try {
			List<FileItem> items = upload.parseRequest(request);
			
			// Process the uploaded items
			Iterator<FileItem> iter = items.iterator();
			while (iter.hasNext()) {
				FileItem item = iter.next();

				if (item.isFormField()) {
					System.out.println("Multipart Normal Data");
					processFormField(item);
				} else {
					System.out.println("Multipart File Data");
					processUploadedFile(item);
					
					//File uploaded successfully
					request.setAttribute("message", "File Uploaded Successfully");
				}
			}
			
			
		} catch (FileUploadException e) {
			e.printStackTrace();
		}
	}
	
	public void processFormField(FileItem item) {
		// Process a regular form field
		if (item.isFormField()) {
			String name = item.getFieldName();
			String value = item.getString();
			
			if( name.equals("firstFile") || name.equals("secondFile") ) {
				fileBase64Stream(value);
				System.out.println( name );
			} else {
				System.out.println( name +":"+ value );
			}
		}
	}
	public void processUploadedFile(FileItem item) {
		// Process a file upload
		if (!item.isFormField()) {
			String fileName = item.getName();	
			
			File uploadedFile = new File(getFilePath() + "/" + fileName);
			System.out.println("File Uploaded Location : "+uploadedFile.getAbsolutePath());
			
			try {
				item.write(uploadedFile);
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}
	
	public void fileBase64Stream( String value ) {
		// String value = "image1,Base64String";
		String[] fileInfo = value.split(",");
		String fileName = fileInfo[0];
		String base64String = fileInfo[1];
		
		byte[] imageBytes = Base64.decodeBase64( base64String );
				//javax.xml.bind.DatatypeConverter.parseBase64Binary( fileBase64Stream ); for ImageData
		InputStream byteArrayInputStream = new ByteArrayInputStream(imageBytes);
			//new BufferedInputStream( imageBytes );
	
		File outputFileName = new File(getFilePath() + "/" + fileName);
		try {
			IOUtils.copy(byteArrayInputStream, new FileOutputStream(outputFileName));
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	public File getFilePath() {
		String root = "E:/files/";//getServletContext().getRealPath("/");
		File path = new File(root + "/uploads");
		if (!path.exists()) {
			path.mkdirs();
		}
		return path;
	}
}
