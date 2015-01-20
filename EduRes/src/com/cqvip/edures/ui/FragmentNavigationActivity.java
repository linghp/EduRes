package com.cqvip.edures.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.cqvip.edures.base.BaseMainFragmentActivity;
import com.cqvip.edures.fragment.NavigationFragment;

public class FragmentNavigationActivity extends BaseMainFragmentActivity{

	private static final String TAG = "FragmentNavigationActivity";
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 if (getSupportFragmentManager().findFragmentByTag(TAG) == null) {
	            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
	            ft.add(android.R.id.content, new NavigationFragment(), TAG);
	            ft.commit();
	        }	
	     }
	
	
}
