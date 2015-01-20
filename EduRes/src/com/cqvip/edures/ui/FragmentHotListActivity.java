package com.cqvip.edures.ui;


import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.cqvip.edures.R;
import com.cqvip.edures.base.BaseMainFragmentActivity;
import com.cqvip.edures.fragment.ItemHotPerListFragment;
import com.cqvip.edures.fragment.ItemHotSpecialFragment;
import com.cqvip.edures.widget.SwipHorizontalScrollView;

public class FragmentHotListActivity extends BaseMainFragmentActivity{

	
	private RelativeLayout rl_nav;
	private SwipHorizontalScrollView mHsv;
	private RadioGroup rg_nav_content;
	private ImageView iv_nav_indicator;
	private ImageView iv_nav_left;
	private ImageView iv_nav_right;
	private ViewPager mViewPager;
	private int indicatorWidth;
	public static String[] tabTitle ;	//

	private LayoutInflater mInflater;
	private TabFragmentPagerAdapter mAdapter;
	private int currentIndicatorLeft = 0;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.page_swipe);
		isHotListActivity=true;
		fvByid();
		initview();
		setListener();		
	}
	
	private void setListener() {
		mViewPager.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {
			
			@Override
			public void onPageSelected(int position) {
				if(rg_nav_content!=null && rg_nav_content.getChildCount()>position){
					((RadioButton)rg_nav_content.getChildAt(position)).performClick();
				}
			}
			
			@Override
			public void onPageScrolled(int arg0, float arg1, int arg2) {
				// TODO Auto-generated method stub
				
			}
			
			@Override
			public void onPageScrollStateChanged(int arg0) {
				// TODO Auto-generated method stub
				
			}
		});
		
		rg_nav_content.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
			
			@Override
			public void onCheckedChanged(RadioGroup group, int checkedId) {
				if(rg_nav_content.getChildAt(checkedId)!=null){
					TranslateAnimation animation = new TranslateAnimation(
							currentIndicatorLeft ,
							((RadioButton) rg_nav_content.getChildAt(checkedId)).getLeft(), 0f, 0f);
					animation.setInterpolator(new LinearInterpolator());
					animation.setDuration(100);
					animation.setFillAfter(true);
					
					iv_nav_indicator.startAnimation(animation);
					
					mViewPager.setCurrentItem(checkedId);	
					
					currentIndicatorLeft = ((RadioButton) rg_nav_content.getChildAt(checkedId)).getLeft();
					//Log.i("PeriodicalClassfyActivity", ""+((checkedId > 1 ? ((RadioButton) rg_nav_content.getChildAt(checkedId)).getLeft() : 0) - ((RadioButton) rg_nav_content.getChildAt(2)).getLeft()));
					if(rg_nav_content.getChildAt(2)!=null){
					mHsv.smoothScrollTo(
							(checkedId > 1 ? ((RadioButton) rg_nav_content.getChildAt(checkedId)).getLeft() : 0) - ((RadioButton) rg_nav_content.getChildAt(2)).getLeft(), 0);
					}
				}
			}
		});
		
	}

	private void initview() {
		tabTitle = getResources().getStringArray(R.array.twotabtitle);
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);
		
		indicatorWidth = dm.widthPixels / tabTitle.length;
		LayoutParams cursor_Params = iv_nav_indicator.getLayoutParams();
		cursor_Params.width = indicatorWidth;// 
		iv_nav_indicator.setLayoutParams(cursor_Params);
		
		mHsv.setSomeParam(rl_nav, iv_nav_left, iv_nav_right, this);
		
		mInflater = (LayoutInflater)this.getSystemService(LAYOUT_INFLATER_SERVICE);

//		LayoutInflater mInflater = LayoutInflater.from(this);  
		
		initNavigationHSV();
		
//		TextView title = (TextView) findViewById(R.id.tv_show_title);
//		title.setText(R.string.hot);
//		ImageView back = (ImageView) findViewById(R.id.img_back_header);
//		back.setOnClickListener(new View.OnClickListener() {
//			@Override
//			public void onClick(View v) {
//				finish();
//				// overridePendingTransition(R.anim.slide_left_in,
//				// R.anim.slide_right_out);
//			}
//		});
		
		mAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager());
		mViewPager.setAdapter(mAdapter);
		//mViewPager.setOffscreenPageLimit(2);
	}

	private void initNavigationHSV() {
			rg_nav_content.removeAllViews();
		for(int i=0;i<tabTitle.length;i++){
			RadioButton rb = (RadioButton) mInflater.inflate(R.layout.nav_radiogroup_item, null);
			if (i == 0) {
				rb.setChecked(true);
			}
			rb.setId(i);
			rb.setText(tabTitle[i]);
			rb.setLayoutParams(new LayoutParams(indicatorWidth,
					LayoutParams.MATCH_PARENT));
			rg_nav_content.addView(rb);
		}
		
	}

	private void fvByid() {
		ImageView back = (ImageView) findViewById(R.id.back);
		back.setVisibility(View.GONE);
		rl_nav = (RelativeLayout) findViewById(R.id.rl_nav);
		
		mHsv = (SwipHorizontalScrollView) findViewById(R.id.mHsv);
		
		rg_nav_content = (RadioGroup) findViewById(R.id.rg_nav_content);
		
		iv_nav_indicator = (ImageView) findViewById(R.id.iv_nav_indicator);
		iv_nav_left = (ImageView) findViewById(R.id.iv_nav_left);
		iv_nav_right = (ImageView) findViewById(R.id.iv_nav_right);
		iv_nav_left.setVisibility(View.GONE);
		iv_nav_right.setVisibility(View.GONE);
		TextView title = (TextView) findViewById(R.id.tv_title);
		title.setText("热点推荐");
		mViewPager = (ViewPager) findViewById(R.id.mViewPager);
		
	}
	
	public static class TabFragmentPagerAdapter extends FragmentPagerAdapter{

		public TabFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		@Override
		public Fragment getItem(int arg0) {
			Fragment ft = null;
			if(arg0==0){
			ft = ItemHotPerListFragment.newInstance(arg0);
			}else if(arg0==1){
			ft = ItemHotSpecialFragment.newInstance(arg0);
			}
			return ft;
		}

		@Override
		public int getCount() {
			
			return tabTitle.length;
		}
		
	}
}
