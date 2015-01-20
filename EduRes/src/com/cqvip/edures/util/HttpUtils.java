package com.cqvip.edures.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.widget.Toast;

import com.android.volley.DefaultRetryPolicy;
import com.android.volley.RetryPolicy;
import com.cqvip.edures.base.C;

public class HttpUtils {

	/**
	 * 检测网络是否可用
	 * 
	 * @param context
	 * @return
	 */
	public static boolean checkNetWork(Context context) {
		boolean netWorkIsOK = false;
		ConnectivityManager connectManager = (ConnectivityManager) context
				.getSystemService(Context.CONNECTIVITY_SERVICE);
		NetworkInfo networkInfo = connectManager.getActiveNetworkInfo();
		if (networkInfo != null && networkInfo.isConnected()) {
			netWorkIsOK = true;
		} else {
			Toast.makeText(context, "联网失败，请检查网络！", Toast.LENGTH_LONG).show();
		}
		return netWorkIsOK;
	}

	// 判断mobile网络是否可用
	public static boolean isMobileDataEnable(Context context) {
	
			return false;
		
	}

	// 判断wifi网络是否可用
	public static boolean isWifiDataEnable(Context context) {
		
			return false;
		
	}


	
	
	/**
	 * vollery设置超时20s
	 * @return
	 */
	public static RetryPolicy setTimeout(){
		RetryPolicy retryPolicy = new DefaultRetryPolicy(C.SOCKET_TIMEOUT, DefaultRetryPolicy.DEFAULT_MAX_RETRIES, DefaultRetryPolicy.DEFAULT_BACKOFF_MULT);
		return retryPolicy;
	}
}
