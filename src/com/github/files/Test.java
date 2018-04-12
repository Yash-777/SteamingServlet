package com.github.files;


public class Test {
	public static void main(String[] args) {
		String postJSON = "first_name=Yashwanth&last_name=Merugu";
		
		StringBuilder sb = new StringBuilder();
		if ( postJSON.startsWith("{") ) {
			System.out.println("JSON String");
		} else {
			postJSON = "{\"" + postJSON;
			postJSON = postJSON.replaceAll("=", "\":\"");
			postJSON = postJSON.replaceAll("&", "\",\"");
			postJSON += "\"}";
			System.out.println("JOSN : "+postJSON);
		}
	}
}
