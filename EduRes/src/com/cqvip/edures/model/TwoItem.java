package com.cqvip.edures.model;

import java.util.ArrayList;
import java.util.List;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import com.cqvip.edures.base.BaseModel;

public class TwoItem extends BaseModel{
	private int id;
	private String name;
	
	public int getId() {
		return id;
	}
	public String getName() {
		return name;
	}
	public TwoItem(JSONObject  json) throws JSONException{
		id = json.getInt("id");
		name = json.getString("name");
	}
	public static List<TwoItem> formList(String str) throws JSONException{
		JSONObject json = new JSONObject(str);
		List<TwoItem> mtempList=new ArrayList<TwoItem>();
		JSONArray arrayList= json.getJSONArray("items");
		if(arrayList!=null&&arrayList.length()>0){
			for(int i=0;i<arrayList.length();i++){
				TwoItem itm = new TwoItem(arrayList.getJSONObject(i));
				mtempList.add(itm);
			}
			return mtempList;
		}
		return null;
	}
}
