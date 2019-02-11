package com.example.rapha.swipeprototype2.http;

import android.os.AsyncTask;
import android.util.Log;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import org.json.JSONException;
import org.json.JSONObject;

public class HttpUtils {

	public static void httpGETAsync(HttpRequest httpRequest, String url) {
		Log.d("oftheday", "httpGETAsync()");
		new DownloadImageTask(httpRequest).execute(url);
	}
	
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




	private static class DownloadImageTask extends AsyncTask<String, Void, JSONObject> {
		HttpRequest httpRequest;
		public DownloadImageTask(HttpRequest httpRequest) {
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
				Log.d("oftheday", "httpGETAsync() doInBackground error: " + e.toString());
				e.printStackTrace();
			}
			JSONObject json = new JSONObject();
			try {
				json = new JSONObject(response.toString());
			} catch (JSONException e) {
				e.printStackTrace();
			}
			Log.d("oftheday", "httpGETAsync() doInBackground jsonlength: " + json.length());
			return json;
		}

		protected void onPostExecute(JSONObject result) {
			Log.d("oftheday", "httpGETAsync() onPostExecute");
			httpRequest.httpRequester.httpResultCallback(result);
		}
	}
	
}
