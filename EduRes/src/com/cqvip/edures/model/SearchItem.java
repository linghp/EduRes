package com.cqvip.edures.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

public class SearchItem {
	
    private String id;
	private String title;
	private String abstrac;
	private String handle;
	private String bcount;//浏览次数
	private String ihandle;
	private String namecn;
	private String author;
	private String num;
	private String unit;
	private String address;
	
	
	public String getHandle() {
		return handle;
	}
	public String getBcount() {
		return bcount;
	}
	public String getIhandle() {
		return ihandle;
	}
	public String getId() {
		return id;
	}
	public String getTitle() {
		return title;
	}
	public String getAbstrac() {
		return abstrac;
	}
	
	public SearchItem(JSONObject  json) throws JSONException{
		
		title = json.getString("title");
		if(!json.isNull("abstrac")){
		abstrac = json.getString("abstrac");
		}
		ihandle = json.getString("ihandle");
		handle = json.getString("handle");
	}

	public static List<SearchItem> formList(JSONObject json) throws JSONException{
		List<SearchItem> mtempList=new ArrayList<SearchItem>();
		JSONArray arrayList= json.getJSONArray("items");
		if(arrayList!=null&&arrayList.length()>0){
			for(int i=0;i<arrayList.length();i++){
				SearchItem itm = new SearchItem(arrayList.getJSONObject(i));
				mtempList.add(itm);
			}
		return mtempList;
		}
	return null;
	}
	
}
