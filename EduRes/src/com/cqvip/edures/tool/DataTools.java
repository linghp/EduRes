package com.cqvip.edures.tool;


import android.content.Context;
import android.content.SharedPreferences;

import com.cqvip.edures.base.C;

public class DataTools {
	/**
	 * dip转为 px
	 */
	public static int dip2px(Context context, float dipValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (dipValue * scale + 0.5f);
	}

	/**
	 *  px 转为 dip
	 */
	public static int px2dip(Context context, float pxValue) {
		final float scale = context.getResources().getDisplayMetrics().density;
		return (int) (pxValue / scale + 0.5f);

	}
	public static String getUserName(Context context){
		SharedPreferences sf = context.getSharedPreferences(C.SharedPreferences_name,context.MODE_PRIVATE);
		String reuslt = sf.getString(C.USERNAME, "");
		return reuslt;
		
	}
}
