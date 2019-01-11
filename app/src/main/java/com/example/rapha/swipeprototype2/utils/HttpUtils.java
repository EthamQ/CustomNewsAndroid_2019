package com.example.rapha.swipeprototype2.utils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

public class HttpUtils {
	
	/**
	 * Sends a http get request to "url".
	 * @param url 
	 * @return returns the answer to the requestas a JSONObject.
	 * @throws Exception
	 */
	public static JSONObject httpGET(String url) throws Exception {
		System.out.println("ABC in httpGET");

		final String USER_AGENT = "Mozilla/5.0";
		URL urlObject = new URL(url);
		HttpURLConnection connnection = (HttpURLConnection) urlObject.openConnection();
		connnection.setRequestMethod("GET");

		//add request header
		connnection.setRequestProperty("User-Agent", USER_AGENT);

		// TODO: check responseCode!
		int responseCode = connnection.getResponseCode();

		BufferedReader reader = new BufferedReader(
		        new InputStreamReader(connnection.getInputStream()));
		String inputLine;
		StringBuffer response = new StringBuffer();
		while ((inputLine = reader.readLine()) != null) {
			response.append(inputLine);
		}
		reader.close();

		return new JSONObject(response.toString());
	}
	
}
