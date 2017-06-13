package com.github.server.requests;

import java.io.BufferedReader;
import java.io.DataOutputStream;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URISyntaxException;
import java.net.URL;

import org.apache.commons.httpclient.HttpClient;
import org.apache.commons.httpclient.NameValuePair;
import org.apache.commons.httpclient.methods.GetMethod;
import org.apache.commons.httpclient.methods.PostMethod;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.ResponseHandler;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;

public class HttpMainClient {
	
	public static String accessResource_JAVA_IO(String httpMethod, String targetURL, String urlParameters) {
		HttpURLConnection con = null; 
		BufferedReader responseStream = null;
		try {
			if (httpMethod.equalsIgnoreCase("GET")) {
				URL url = new URL( targetURL+"?"+urlParameters );
				responseStream = new BufferedReader(new InputStreamReader( url.openStream() ));
			}else if (httpMethod.equalsIgnoreCase("POST")) {
				con = (HttpURLConnection) new URL(targetURL).openConnection();
				// inform the connection that we will send output and accept input
				con.setDoInput(true);   con.setDoOutput(true);  con.setRequestMethod("POST");
				con.setUseCaches(false); // Don't use a cached version of URL connection.
				con.setRequestProperty("Content-Type", "application/x-www-form-urlencoded");
				con.setRequestProperty("Content-Length", Integer.toString(urlParameters.getBytes().length));
				con.setRequestProperty("Content-Language", "en-US");

				DataOutputStream requestStream = new DataOutputStream ( con.getOutputStream() );
				requestStream.writeBytes(urlParameters);
				requestStream.close();

				responseStream = new BufferedReader(new InputStreamReader( con.getInputStream(), "UTF-8" ));
			}

			StringBuilder response = new StringBuilder(); // or StringBuffer if not Java 5+ 
			String line;
			while((line = responseStream.readLine()) != null) {
				response.append(line).append('\r');
			}
			responseStream.close();
			return response.toString();
		} catch (Exception e) {
			e.printStackTrace();		return null;
		} finally {
			if(con != null) con.disconnect();
		}
	}

	public static String accessResource_Appache_commons(String url){
		String response_String = null;
	
		HttpClient client = new HttpClient();
	
		GetMethod method = new GetMethod( url ); 
		//PostMethod method = new PostMethod( url );
		method.setRequestHeader("Content-type", "text/xml; charset=ISO-8859-1");
		method.setQueryString(new NameValuePair[] { 
			new NameValuePair("param1","value1"),
			new NameValuePair("param2","value2")
		});  //The pairs are encoded as UTF-8 characters. 
		try{
			int statusCode = client.executeMethod(method);
			System.out.println("Status Code = "+statusCode);
		
			//Get data as a String OR BYTE array method.getResponseBody()
			response_String = method.getResponseBodyAsString();
			method.releaseConnection();
		} catch(IOException e) {
			e.printStackTrace();
		}
		return response_String;
	}
	
	public static String accessResource_Appache(String url) throws ClientProtocolException, IOException{
		try {
			CloseableHttpClient httpclient = HttpClients.createDefault();
			URIBuilder builder = new URIBuilder( url )
									.addParameter("param1", "appache1")
									.addParameter("param2", "appache2");

			//HttpGet method = new HttpGet( builder.build() );
			HttpPost method = new HttpPost( builder.build() );

			// Create a custom response handler
			ResponseHandler<String> responseHandler = new ResponseHandler<String>() {
				@Override
				public String handleResponse( final HttpResponse response) throws IOException {
					int status = response.getStatusLine().getStatusCode();
					if (status >= 200 && status < 300) {
						HttpEntity entity = response.getEntity();
						return entity != null ? EntityUtils.toString(entity) : null;
					}
					return "";
				}
			};
			return httpclient.execute( method, responseHandler );
		} catch (URISyntaxException e) {
			e.printStackTrace();
		}
		return null;
	}
	
	static String targetURL = "http://localhost:8080/SteamingServlet/HttpConsumer";
	static String urlParameters = "param1=value11&param2=value12";
	public static void main(String[] args) throws IOException {

		String response = "";
		//java.awt.Desktop.getDesktop().browse(java.net.URI.create( targetURL+"?"+urlParameters ));
		//response = accessResource_JAVA_IO( "POST", targetURL, urlParameters );
		//response = accessResource_Appache_commons( targetURL );
		response = accessResource_Appache( targetURL );
		System.out.println("Response: "+response);
	}
}
