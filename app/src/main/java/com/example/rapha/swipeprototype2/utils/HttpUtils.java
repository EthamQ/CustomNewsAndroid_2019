package com.example.rapha.swipeprototype2.utils;

import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONObject;

import javax.net.ssl.HttpsURLConnection;

public class HttpUtils {
	
	/**
	 * Sends a http get request to "url".
	 * @param url 
	 * @return returns the answer to the requestas a JSONObject.
	 * @throws Exception
	 */
	public static JSONObject httpGET(String url) throws Exception {

		StringBuffer response = new StringBuffer();
	try{
		final String USER_AGENT = "Mozilla/5.0";
		URL urlObject = new URL(url);
		HttpURLConnection connnection = (HttpURLConnection) urlObject.openConnection();
		connnection.setRequestMethod("GET");

		//add request header
		connnection.setRequestProperty("User-Agent", USER_AGENT);

		// TODO: check responseCode!
		int responseCode = connnection.getResponseCode();
		Log.d("HTTPCHECK", "Request to URL: " + url + ", ResponseCode: " + responseCode);

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(connnection.getInputStream()));
		String inputLine;

		while ((inputLine = reader.readLine()) != null) {
			response.append(inputLine);
		}
		reader.close();
	}
	catch(Exception e){
		e.printStackTrace();
		}


		return new JSONObject(response.toString());
	}
	
}
