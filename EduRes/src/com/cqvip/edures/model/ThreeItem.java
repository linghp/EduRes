package com.cqvip.edures.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class ThreeItem {

    private int id;
	private String title;
	private String handle;
	private String abstrac;
	private String ihandle;
	public int getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	
	
	public String getHandle() {
		return handle;
	}
	public ThreeItem(JSONObject  json) throws JSONException{
		id = json.getInt("id");
		title = json.getString("title");
		handle = json.getString("handle");
		if(!json.isNull("abstrac")){
		abstrac = json.getString("abstrac");
		}
	if(!json.isNull("ihandle")){
		ihandle = json.getString("ihandle");
	}
		
	}

	public static List<ThreeItem> formList(String str) throws JSONException{
		List<ThreeItem> mtempList=new ArrayList<ThreeItem>();
		JSONArray arrayList= new JSONArray(str);
		if(arrayList!=null&&arrayList.length()>0){
			for(int i=0;i<arrayList.length();i++){
				ThreeItem itm = new ThreeItem(arrayList.getJSONObject(i));
				mtempList.add(itm);
			}
		return mtempList;
		}
	return null;
	}
	public static List<ThreeItem> formDetailList(String str) throws JSONException{
		JSONObject json = new JSONObject(str);
		List<ThreeItem> mtempList=new ArrayList<ThreeItem>();
		JSONArray arrayList= json.getJSONArray("items");
		if(arrayList!=null&&arrayList.length()>0){
			for(int i=0;i<arrayList.length();i++){
				ThreeItem itm = new ThreeItem(arrayList.getJSONObject(i));
				mtempList.add(itm);
			}
			return mtempList;
		}
		return null;
	}
	public String getAbstrac() {
		return abstrac;
	}
	public String getIhandle() {
		return ihandle;
	}
	
}
