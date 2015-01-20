package com.cqvip.edures.util;

import org.json.JSONException;
import org.json.JSONObject;

import android.text.TextUtils;

import com.cqvip.edures.base.BaseMessage;

public class AppUtil {

    /**
     * 获取返回的json，映射成BaseMessage对象
     * @param jsonStr  返回json字符串
     * @param modelName 需要映射的模块名称
     * @return
     * @throws Exception
     */
	public static BaseMessage getMessage (String jsonStr,String modelName) throws Exception {
		BaseMessage message = new BaseMessage();
		JSONObject jsonObject = null;
		try {
			jsonObject = new JSONObject(jsonStr);
			if (jsonObject != null) {
				message.setCode(jsonObject.getString("code"));
				message.setMessage(jsonObject.getString("message"));
				if(!TextUtils.isEmpty(modelName)){
					message.setResult(jsonObject.getString("result"),modelName);
				}
			}
		} catch (JSONException e) {
			throw new Exception("Json format error");
		} catch (Exception e) {
			e.printStackTrace();
		}
		return message;
	}
	
	public static boolean isHttpUrl(String url){
		 if(url.startsWith("http")){
			 return true;
		 }
		 return false;
	}
	
	
}
