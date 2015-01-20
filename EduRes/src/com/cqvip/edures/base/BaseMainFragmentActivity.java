package com.cqvip.edures.base;


import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentManager.OnBackStackChangedListener;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.GestureDetector;
import android.view.GestureDetector.SimpleOnGestureListener;
import android.view.KeyEvent;
import android.view.MotionEvent;
import android.view.View;
import android.widget.Toast;

import com.android.volley.RequestQueue;
import com.android.volley.toolbox.Volley;
import com.cqvip.edures.R;
import com.cqvip.edures.exception.ErrorVolleyThrow;
import com.cqvip.edures.widget.CustomProgressDialog;
/**
 * 为了使双击回退键退出程序能够在主页面（四个）有效，所以开辟此类
 * @author ling
 *
 */
public class BaseMainFragmentActivity extends FragmentActivity implements OnBackStackChangedListener{
	private static final String TAG = "BaseMainFragmentActivity";
	long exitTime;
	private GestureDetector mGestureDetector;
	
	public FragmentManager fManager;
	protected RequestQueue mQueue;
	protected ErrorVolleyThrow volleyErrorListener;
	protected CustomProgressDialog customProgressDialog;
	
	/**
	 * 判断是不是热点activity，因为viewpager的滑动受dispatchTouchEvent影响。
	 */
	protected boolean isHotListActivity=false;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		fManager = getSupportFragmentManager();
		mQueue = Volley.newRequestQueue(this);
		if(customProgressDialog==null){
			customProgressDialog=CustomProgressDialog.createDialog(this);
		}
		volleyErrorListener = new ErrorVolleyThrow(this, customProgressDialog);
		
		mGestureDetector = new GestureDetector(this,
				new MyGestrueListener(this));
		fManager.addOnBackStackChangedListener(this);
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
	
	@Override
	public boolean onKeyDown(int keyCode, KeyEvent event) {
		Log.i("BaseMainFragmentActivity", "onKeyDown");
		if (fManager.getBackStackEntryCount() <= 0) {
//			Log.i(TAG, "onKeyDown");
			if (keyCode == KeyEvent.KEYCODE_BACK) {
				exitApp();
				return false;
			}
		}
		return super.onKeyDown(keyCode, event);
	}

		
	/**
	 * 退出程序
	 */
	private void exitApp() {
		// 判断2次点击事件时间
		if ((System.currentTimeMillis() - exitTime) > 2000) {
			Toast.makeText(this, "再按一次退出程序", Toast.LENGTH_SHORT).show();
			exitTime = System.currentTimeMillis();
		} else {
			finish();
		}
	}
	
/**
 * 回退 
 * @param v
 */
	public void back(View v){
		if (fManager.getBackStackEntryCount() <= 0) {
//			Log.i(TAG, "onKeyDown");
			finish();
		}else{
		fManager.popBackStack();
		}
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
			if (Math.abs(velocityX) > minVelocitx
					&& Math.abs(velocityX) > 1.5 * Math.abs(velocityY)
					&& Math.abs(e2.getY() - e1.getY())
							/ Math.abs(e2.getX() - e1.getX()) < 0.36// 角度<20度
					&& velocityX > 0) {
				Log.i("onFling", "20140714");
				back(null);
				return true;
			}
			return false;
		}
	}
	
	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if(isHotListActivity){
			return super.dispatchTouchEvent(ev);
		}else{
		if (mGestureDetector.onTouchEvent(ev)) {
			return true;
		} else {
			boolean temp = super.dispatchTouchEvent(ev);
			return temp;
		}
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		//android.os.Process.killProcess(android.os.Process.myPid());//加上，关闭后进程消失
	}
	
	@Override
	public void onBackStackChanged() {
		if(fManager.getBackStackEntryCount()>0){
			isHotListActivity=false;
		}else{
			isHotListActivity=true;
		}
	}
}
