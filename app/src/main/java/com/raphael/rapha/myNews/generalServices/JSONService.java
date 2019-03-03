package com.raphael.rapha.myNews.generalServices;

import org.json.JSONException;
import org.json.JSONObject;

public class JSONService {
	
	/**
	 * If the "key" value of the jsonObject is null then it returns an empty String.
	 * If it exists it returns the value for the key.
	 * Use this function if you want to avoid try catch everywhere.
	 * @param jsonObject
	 * @param key
	 * @return
	 */
	public static String getStringErrorHandled(JSONObject jsonObject, String key) {
		String value = "";
		try {
			value = jsonObject.getString(key);
		} catch (JSONException e) {
			// value doesn't exist or is null
			e.printStackTrace();
		}
		return value;
	}

	/**
	 * Takes the entry in the "jsonObject" with the key "arrayKey".
	 * The entry has to be an array. It then returns the array entry at "arrayIndex".
	 * @param jsonObject
	 * @param arrayKey
	 * @param arrayIndex
	 * @return
	 */
	public static JSONObject getArrayEntryFromJson(JSONObject jsonObject, String arrayKey, int arrayIndex) {
		JSONObject article = new JSONObject();
		try {
			article = (JSONObject)jsonObject.getJSONArray(arrayKey).get(arrayIndex);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return article;
	}
}
