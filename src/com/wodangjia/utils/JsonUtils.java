package com.wodangjia.utils;

import org.json.JSONException;
import org.json.JSONObject;

public class JsonUtils {
	public static int getInt(String jsonString,String name) {
		int num=-1;
		try {
			JSONObject jsonObject=new JSONObject(jsonString);
			num = jsonObject.getInt(name);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return num;
	}
	public static Object getJsonObject(String jsonString ,String name) {
		
		JSONObject jsonObject2=null;
		try {
			JSONObject jsonObject=new JSONObject(jsonString);
			jsonObject2=jsonObject.getJSONObject(name);
		} catch (JSONException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return jsonObject2;
	}
	public static JSONObject stringToJsonObject() {
		
		return null;
	}
}
