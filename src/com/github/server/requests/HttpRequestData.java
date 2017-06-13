package com.github.server.requests;

import java.io.BufferedReader;
import java.io.IOException;
import java.util.Enumeration;
import java.util.Map;
import java.util.Set;

import javax.servlet.http.HttpServletRequest;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class HttpRequestData {
	
	public static void main(String[] args) {
		String jsonString = "{'desiredCapabilities':{'browserName':'firefox','version':'','platform':'ANY'}}";
		capabilitiesJSON(jsonString);
	}
	
	public static void capabilitiesJSON(String jsonString) {
		try {
			JSONObject json = new JSONObject( jsonString );
			if( json.has("desiredCapabilities")) {
				System.out.println("List of JSON Object values: ");
				System.out.println("desiredCapabilities : {");
				String nodeBrowser = json.getString("desiredCapabilities");
				JSONObject caps = new JSONObject( nodeBrowser );
				JSONArray names = caps.names();
				for (int i = 0; i < names.length(); i++) {
					String key = (String) names.get( i );
					String value = (String) caps.get(key);
					System.out.format("%s : %s \n", key, value);
				}
				System.out.println("}");
			}
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static String requestData( HttpServletRequest request ) {
		try {
			// URL Parameters
			Map<String, String[]> parameterMap = request.getParameterMap();
			Set<String> keySet = parameterMap.keySet();
			if ( ! keySet.isEmpty() ) {
				System.out.println("List of Parameters : ");
				for (String key : keySet) {
					System.out.format("%s : %s \n", key, request.getParameter(key));
				}
			}
		
			// Body Content
			BufferedReader br = request.getReader();
			StringBuilder sb = new StringBuilder();
			String str = null;
			while ((str = br.readLine()) != null) {
				sb.append(str);
			} // SB: {"username":"yashwanth.merugu@gmail.com","password":"Yash@777"}
			
			if ( sb.length() > 0 ) {
				System.out.println("SB : "+sb);
				JSONObject json = new JSONObject( sb.toString() );
				JSONArray names = json.names();
				for (int i = 0; i < names.length(); i++) {
					if( i == 0 ) {
						System.out.println("List of JSON Object values: ");
					}
					String key = (String) names.get( i );
					String value = (String) json.get(key);
					System.out.format("%s : %s \n", key, value);
				}
			}
			
			// Header Data
			Enumeration<String> headerNames = request.getHeaderNames();
			for (String name : java.util.Collections.list(headerNames)) {
				Enumeration<String> headerValues = request.getHeaders(name);
				for (String value : java.util.Collections.list(headerValues)) {
					System.out.format("Headers « %s : %s\n", name, value);
				}
			}
			
		} catch (JSONException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return "";
	}
}
