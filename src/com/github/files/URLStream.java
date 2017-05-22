package com.github.files;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class URLStream
 * 
 * Test URL � localhsot:8080/SteamingServlet/files/URLStream
 */
@WebServlet("/files/URLStream")
public class URLStream extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public URLStream() {
        super();
    }

	/**
	 * @see HttpServlet#service(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		File source = new File("D:\\SVN_Commit.PNG");
		long start = System.nanoTime();
		ServletOutputStream sos = response.getOutputStream();
		
		InputStream image = null;
		BufferedInputStream bin = null;
		BufferedOutputStream bout = null;
		
		try {
			image = new FileInputStream(source);
			bin = new BufferedInputStream( image );
			bout = new BufferedOutputStream( sos );
			int ch =0; ;
			while((ch=bin.read())!=-1) {
				bout.write(ch);
			}
		} finally {
			bin.close();
			image.close();
			bout.close();
			sos.close();
		}
		System.out.println("Time taken by Stream Copy = "+(System.nanoTime()-start));
	}
	
	/**
	 * <B>Java Copy File � Stream</B>
	 * 
	 * <P>This is the conventional way of file copy in java, here we create two Files, source and destination.
	 * Then we create InputStream from source and write it to destination file using OutputStream for java
	 * copy file operation.</p>
	 * 
	 * @param source       � the input file
	 * @param dest         � the output file
	 * @throws IOException � if any file is unavailable then throws exception.
	 */
	void copyFileUsingStream(File source, File dest) throws IOException {
		long start = System.nanoTime();
		InputStream is = null;
		OutputStream os = null;
		try {
			is = new FileInputStream(source);
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
