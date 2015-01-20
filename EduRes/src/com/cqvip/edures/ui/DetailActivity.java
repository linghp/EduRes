package com.cqvip.edures.ui;

import android.os.Bundle;
import android.support.v4.app.FragmentTransaction;

import com.cqvip.edures.base.BaseFragmentActivity;
import com.cqvip.edures.fragment.DetailFragment;

public class DetailActivity  extends BaseFragmentActivity{

	private static final String TAG = "DetailActivity";
	private String handle;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		handle = getIntent().getStringExtra("handle");
		 if (getSupportFragmentManager().findFragmentByTag(TAG) == null) {
	            final FragmentTransaction ft = getSupportFragmentManager().beginTransaction();
	            ft.add(android.R.id.content,DetailFragment.newInstance(handle), TAG);
	            ft.commit();
	        }	
	     }
	
}
