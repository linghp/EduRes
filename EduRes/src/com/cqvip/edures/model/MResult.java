package com.cqvip.edures.model;

import org.json.JSONException;
import org.json.JSONObject;

public class MResult {

	private int code;
	private String message;
	private String result;
	
	public MResult(JSONObject json) throws JSONException{
		code = json.getInt("code");
		message = json.getString("message");
		result = json.getString("result");
		
	}
	public int getCode() {
		return code;
	}
	public String getMessage() {
		return message;
	}
	public String getResult() {
		return result;
	}
}
