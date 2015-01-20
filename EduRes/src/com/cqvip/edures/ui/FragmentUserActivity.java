package com.cqvip.edures.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqvip.edures.R;
import com.cqvip.edures.base.BaseMainFragmentActivity;
import com.cqvip.edures.base.C;
import com.cqvip.edures.download.DownloadManageActivity;
import com.cqvip.edures.fragment.DownloadManageFragment;
import com.cqvip.edures.fragment.LoginFragment;
import com.cqvip.edures.fragment.LoginInfoFragment;

public class FragmentUserActivity extends BaseMainFragmentActivity {
	private ViewGroup login_ll;
	private ViewGroup loginsuccess_rl;
	private ViewGroup logout_rl;
	private TextView username_tv;
	private SharedPreferences sharedPreferences;
	private TextView tv_title;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		// TODO Auto-generated method stub
		super.onCreate(savedInstanceState);
		setContentView(R.layout.main_usercenter);
		ImageView back = (ImageView) findViewById(R.id.back);
		back.setVisibility(View.GONE);
		login_ll = (ViewGroup) findViewById(R.id.login_ll);
		loginsuccess_rl = (ViewGroup) findViewById(R.id.loginsuccess_rl);
		logout_rl = (ViewGroup) findViewById(R.id.logout_rl);
		username_tv = (TextView) findViewById(R.id.username_tv);
		tv_title = (TextView)findViewById(R.id.tv_title);
		tv_title.setText(getString(R.string.usercenter));
		sharedPreferences = getSharedPreferences(C.SharedPreferences_name,
				MODE_PRIVATE);
		if (!sharedPreferences.getString(C.USERNAME, "").equals("")) {
			updateLoginUI();
		}
	}

	public void updateLoginUI() {
		login_ll.setVisibility(View.GONE);
		loginsuccess_rl.setVisibility(View.VISIBLE);
		logout_rl.setVisibility(View.VISIBLE);
		username_tv.append(sharedPreferences.getString(C.USERNAME, ""));
		Log.i("updateLoginUI", sharedPreferences.getString(C.LOGININFO, ""));
	}

	/**
	 * logout
	 * 
	 * @param view
	 */
	public void logout(View v) {
		login_ll.setVisibility(View.VISIBLE);
		loginsuccess_rl.setVisibility(View.GONE);
		logout_rl.setVisibility(View.GONE);
		Editor editor = sharedPreferences.edit();
		editor.putString(C.LOGININFO, "");
		editor.putString(C.USERNAME, "");
		editor.commit();
	}

	/**
	 * setting
	 * 
	 * @param view
	 */
	public void detail(View view) {
		Fragment newFragment = new LoginInfoFragment();
		addFragmentToStack(newFragment, android.R.id.content, "");
	}
	
	/**
	 * setting
	 * 
	 * @param view
	 */
	public void setting(View view) {
		toast("setting");
	}
	
	/**
	 * downloadmanage
	 * 
	 * @param view
	 */
	public void downloadmanage(View view) {
//		toast("downloadmanage");
//		Fragment newFragment = new DownloadManageFragment();
//		addFragmentToStack(newFragment, android.R.id.content, "");
		Intent intent=new Intent(this, DownloadManageActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_right_in, R.anim.scale_out_left);
	}

	/**
	 * about
	 * 
	 * @param view
	 */
	public void about(View view) {
		toast("about");
	}

	/**
	 * login
	 * 
	 * @param view
	 */
	public void login(View view) {
		Fragment newFragment = new LoginFragment();
		addFragmentToStack(newFragment, android.R.id.content, "");
	}
}
