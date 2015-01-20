package com.cqvip.edures.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

/**
 * 下载文件
 * @author luojiang
 *
 */
public class Shortfile {
	private String name;
	private String url;
	private boolean isdown;

	public Shortfile(JSONObject json) throws JSONException{
		name = json.getString("name");
		url = json.getString("url");
		isdown = json.getBoolean("isdown");
	}
	public static Shortfile[] formList(JSONArray array) throws JSONException{
		Shortfile[] lists = null;
		if(array!=null&&array.length()>0){
			lists = new Shortfile[array.length()];
			for(int i=0;i<array.length();i++){
				lists[i]=(new Shortfile(array.getJSONObject(i)));
			}
		}
		return lists;
	}
	public String getName() {
		return name;
	}
	public String getUrl() {
		return url;
	}
	public boolean isIsdown() {
		return isdown;
	}
	
}
