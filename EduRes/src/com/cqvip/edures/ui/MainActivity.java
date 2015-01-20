package com.cqvip.edures.ui;

import android.app.TabActivity;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.widget.RadioGroup;
import android.widget.TabHost;
import android.widget.TextView;

import com.cqvip.edures.R;

public class MainActivity extends TabActivity {

	private TabHost tabHost;
	private TextView main_tab_new_message;
	private TextView menuTitle;

	public void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.mainbottommenu);
		tabHost = this.getTabHost();
		TabHost.TabSpec spec;
		Intent intent;

		intent = new Intent().setClass(this, FragmentHomeActivity.class);
		spec = tabHost.newTabSpec("首页").setIndicator("首页").setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, FragmentHotListActivity.class);
		spec = tabHost.newTabSpec("热点").setIndicator("热点").setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, FragmentNavigationActivity.class);
		spec = tabHost.newTabSpec("导航").setIndicator("导航").setContent(intent);
		tabHost.addTab(spec);

		intent = new Intent().setClass(this, FragmentUserActivity.class);
		spec = tabHost.newTabSpec("个人中心").setIndicator("个人中心")
				.setContent(intent);
		tabHost.addTab(spec);

		tabHost.setCurrentTab(0);

		RadioGroup radioGroup = (RadioGroup) this
				.findViewById(R.id.main_tab_group);
		radioGroup
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						// TODO Auto-generated method stub
						switch (checkedId) {
						case R.id.main_tab_vedio://
							tabHost.setCurrentTabByTag("首页");
							break;
						case R.id.main_tab_newInfo://
							tabHost.setCurrentTabByTag("热点");
							break;
						case R.id.main_tab_promotion://
							tabHost.setCurrentTabByTag("导航");
							break;
						case R.id.main_tab_garage://
							tabHost.setCurrentTabByTag("个人中心");
							break;
						default:
							break;
						}
					}
				});

	}

	//屏蔽回退键，当在子activity中按回退键时会响应此activity的回退事件finish掉程序，故在此屏蔽。
	public boolean onKeyDown(int keyCode, KeyEvent event) { 
		Log.i("MainActivity", "onKeyDown");
	    if(keyCode == KeyEvent.KEYCODE_BACK) { //监控/拦截/屏蔽返回键 
	        return true; 
	    } 
	return super.onKeyDown(keyCode, event); 
	}
	
	@Override
	protected void onPause() {
		// TODO Auto-generated method stub
		super.onPause();
		//overridePendingTransition(R.anim.slide_right_in, R.anim.scale_out_left);
	}
}
