package com.github.jersey.files;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.ws.rs.Consumes;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import com.sun.jersey.core.header.FormDataContentDisposition;
import com.sun.jersey.multipart.FormDataParam;

/**
 * http://localhost:8080/SteamingServlet/jersyFileUpload.html
 * http://localhost:8080/SteamingServlet/jersey1/file/upload
 * 
 * @author yashwanth.m
 *
 */
@Path("/file")
public class MultipartFileUpload {

	static String defaultPath = "E:/";
	@POST
	@Path("/upload")
	@Consumes(MediaType.MULTIPART_FORM_DATA)
	public Response uploadFile(
			@FormDataParam("file") InputStream uploadedInputStream,
			@FormDataParam("file") FormDataContentDisposition fileDetail,
			@FormDataParam("path") String path) {
	
		System.out.println("path::"+path);
		
		if( path == null || "".equals( path )) path = defaultPath;
		
		String uploadedFileLocation = path + fileDetail.getFileName();
	
		// save it
		writeToFile(uploadedInputStream, uploadedFileLocation);
	
		String output = "File uploaded to : " + uploadedFileLocation;
	
		return Response.status(200).entity(output).build();
		/*request.setAttribute("message", "Upload has been done successfully!");
		getServletContext().getRequestDispatcher("/message.jsp").forward(request, response);*/
	}

	// save uploaded file to new location
	private void writeToFile(InputStream uploadedInputStream, String uploadedFileLocation) {
		try {
			OutputStream out = new FileOutputStream(new File(uploadedFileLocation));
			int read = 0;
			byte[] bytes = new byte[1024];
	
			out = new FileOutputStream(new File(uploadedFileLocation));
			while ((read = uploadedInputStream.read(bytes)) != -1) {
				out.write(bytes, 0, read);
			}
			out.flush();
			out.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}