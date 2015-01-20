package com.cqvip.edures.base;

import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.cqvip.edures.R;
import com.cqvip.edures.exception.ErrorVolleyThrow;
import com.cqvip.edures.widget.CustomProgressDialog;

public class BaseFragmentActivity extends FragmentActivity {

	private static final String TAG = "BaseFragmentActivity";
	public FragmentManager fManager;
	
	protected RequestQueue mQueue;
	protected ErrorVolleyThrow volleyErrorListener;
	protected CustomProgressDialog customProgressDialog;
	
	private GestureDetector mGestureDetector;
	/**
	 * 防止viewpager滑动非第一页时，右滑直接退出。
	 */
	protected boolean isLeftFragment=true;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		// requestWindowFeature(Window.FEATURE_NO_TITLE);
		fManager = getSupportFragmentManager();
		mQueue = Volley.newRequestQueue(this);
		if(customProgressDialog==null){
			customProgressDialog=CustomProgressDialog.createDialog(this);
		}
		volleyErrorListener = new ErrorVolleyThrow(this, customProgressDialog);
		
		mGestureDetector = new GestureDetector(this,
				new MyGestrueListener(this));
	}

	/**
	 * 回退 
	 * @param v
	 */
		public void back(View v){
			if (fManager.getBackStackEntryCount() <= 0) {
//				Log.i(TAG, "onKeyDown");
				finish();
			}else{
			fManager.popBackStack();
			}
		}


	public void addFragmentToStack(Fragment newFragment, int layoutid,String tag) {
		FragmentTransaction ft = fManager.beginTransaction();
		ft.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
				R.anim.scale_in_left, R.anim.slide_right_out);
		ft.replace(layoutid, newFragment,tag);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.addToBackStack(null);
		ft.commit();
	}

	public void toast (String msg) {
		Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
	}
	
	public void gotoBrowser(String ihandle){
		Intent intent = new Intent();
		intent.setAction("android.intent.action.VIEW");
		Uri content_url = Uri.parse(ihandle);
		intent.setData(content_url);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_right_in, R.anim.scale_out_left);
	}
	
	class MyGestrueListener extends SimpleOnGestureListener {
		private Context mContext;

		MyGestrueListener(Context context) {
			mContext = context;
		}

		private int minVelocitx = 500;

		// private int minVelocity = 5000;

		public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX,
				float velocityY) {
//			Log.e("onFling",
//					"velocityY" + velocityY + "--velocityX" + velocityX
//							+ "  y/x" + (e2.getY() - e1.getY())
//							/ (e2.getX() - e1.getX()));
//			Log.i("onFling", isLeftFragment+"");
			if (isLeftFragment
					&& Math.abs(velocityX) > minVelocitx
					&& Math.abs(velocityX) > 1.5 * Math.abs(velocityY)
					&& Math.abs(e2.getY() - e1.getY())
							/ Math.abs(e2.getX() - e1.getX()) < 0.36// 角度<20度
					&& velocityX > 0) {
				if(fManager.getBackStackEntryCount()>0){
					fManager.popBackStack();
				}else{
				finish();
				}
				return true;
			}
			return false;
		}
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (mGestureDetector.onTouchEvent(ev)) {
			return true;
		} else {
			boolean temp = super.dispatchTouchEvent(ev);
			return temp;
		}
	}
	
	
	@Override
	public void finish() {
		// TODO Auto-generated method stub
		super.finish();
		overridePendingTransition(R.anim.scale_in_left, R.anim.slide_right_out);
	}
}
