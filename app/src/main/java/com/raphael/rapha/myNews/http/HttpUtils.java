package com.raphael.rapha.myNews.http;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class HttpUtils {

	/**
	 * Calls the httpResultCallback() function of the class calling this function
	 * with the received data.
	 * The calling class is defined in the HttpRequest object.
	 * @param httpRequest
	 * @param url
	 */
	public static void httpGETAsync(HttpRequest httpRequest, String url) {
		new HttpGetAsync(httpRequest).execute(url);
	}
	
	/**
	 * Sends a http get request to the specified url.
	 * @param url 
	 * @return returns the answer to the request as a JSONObject.
	 * @throws Exception
	 */
	public static JSONObject httpGET(String url, HttpRequest httpRequest) throws Exception {
		StringBuffer response = new StringBuffer();
		final String USER_AGENT = "Mozilla/5.0";
		URL urlObject = new URL(url);
		HttpURLConnection connnection = (HttpURLConnection) urlObject.openConnection();
		connnection.setRequestMethod("GET");

		//add request header
		connnection.setRequestProperty("User-Agent", USER_AGENT);

		int responseCode = connnection.getResponseCode();
		// Pass the response code to the calling fragment or activity to handle it.
		if(httpRequest.httpRequester != null){
			httpRequest.requestInfo.setHttpResponseCode(responseCode);
			httpRequest.httpRequester.httpResultCallback(httpRequest.requestInfo);
		}
		Log.d("HTTPCHECK", "Request to URL: " + url + ", ResponseCode: " + responseCode);

		BufferedReader reader = new BufferedReader(
				new InputStreamReader(connnection.getInputStream()));
		String inputLine;

		while ((inputLine = reader.readLine()) != null) {
			response.append(inputLine);
		}
		reader.close();
		return new JSONObject(response.toString());
	}




	private static class HttpGetAsync extends AsyncTask<String, Void, JSONObject> {
		HttpRequest httpRequest;
		public HttpGetAsync(HttpRequest httpRequest) {
			this.httpRequest = httpRequest;
		}

		protected JSONObject doInBackground(String... voids) {
			Log.d("oftheday", "httpGETAsync() doInBackground");
			StringBuffer response = new StringBuffer();
			try{
				final String USER_AGENT = "Mozilla/5.0";
				URL urlObject = new URL(voids[0]);
				HttpURLConnection connnection = (HttpURLConnection) urlObject.openConnection();
				connnection.setRequestMethod("GET");

				//add request header
				connnection.setRequestProperty("User-Agent", USER_AGENT);

				// TODO: check responseCode!
				int responseCode = connnection.getResponseCode();
				Log.d("HTTPCHECK", "Request to URL: " + voids[0] + ", ResponseCode: " + responseCode);

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
			JSONObject json = new JSONObject();
			try {
				json = new JSONObject(response.toString());
			} catch (JSONException e) {
				httpRequest.requestInfo.setErrorOccurred(true);
				e.printStackTrace();
			}
			Log.d("oftheday", "httpGETAsync() doInBackground jsonlength: " + json.length());
			return json;
		}

		protected void onPostExecute(JSONObject result) {
			Log.d("oftheday", "httpGETAsync() onPostExecute");
			httpRequest.requestInfo.setRequestResponse(result);
			httpRequest.httpRequester.httpResultCallback(httpRequest.requestInfo);
		}
	}
	
}
