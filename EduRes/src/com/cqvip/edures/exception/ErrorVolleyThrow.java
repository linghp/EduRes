package com.cqvip.edures.exception;

import android.app.Dialog;
import android.content.Context;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Response.ErrorListener;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;

/**
 * 异常处理
 * 
 * @author luojiang
 * 
 */
public class ErrorVolleyThrow implements ErrorListener {

	private Context context;
	private Dialog dialog;

	public ErrorVolleyThrow(Context context, Dialog dialog) {
		this.context = context;
		this.dialog = dialog;
	}

	@Override
	public void onErrorResponse(VolleyError arg0) {

		VolleyLog.e("Error: ", arg0.getMessage());

		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
		Log.i("onErrorResponse", "111");
		System.out.println(arg0.toString());
		String info = VolleyErrorHelper.getMessage(arg0, context);
		Log.i("info", info);
		Toast.makeText(context, info, Toast.LENGTH_LONG).show();
	}

}
