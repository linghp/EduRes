package com.cqvip.edures.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewGroup.LayoutParams;
import android.view.Window;
import android.view.animation.LinearInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.cqvip.edures.R;
import com.cqvip.edures.base.BaseFragmentActivity;
import com.cqvip.edures.base.C;
import com.cqvip.edures.base.GlobleData;
import com.cqvip.edures.fragment.ItemTechResFragment;
import com.cqvip.edures.model.MResult;
import com.cqvip.edures.model.ThreeItem;
import com.cqvip.edures.util.HttpUtils;
import com.cqvip.edures.util.VersStringRequest;
import com.cqvip.edures.widget.SwipHorizontalScrollView;

/**
 * 教师教育资源库
 * 
 * @author luojiang
 * 
 */
public class TechResClassfyActivity extends BaseFragmentActivity {

	private RelativeLayout rl_nav;
	private SwipHorizontalScrollView mHsv;
	private RadioGroup rg_nav_content;
	private ImageView iv_nav_indicator;
	private ImageView iv_nav_left;
	private ImageView iv_nav_right;
	private ViewPager mViewPager;
	private ViewGroup content_ll;
	private int indicatorWidth;
	public static String[] tabTitle = { "中文期刊", "学位论文", "会议论文", "课题研究", "会议论文",
			"课题研究报告", "电子图书" }; //

	private static final int[] ALLTYPE = { 2, 8, 15, 18, 24, 25 };
	private static List<ThreeItem> lists;
	private LayoutInflater mInflater;
	private TabFragmentPagerAdapter mAdapter;
	private int currentIndicatorLeft = 0;
	private int req_type = 0; // 请求的库，参数，参加接口
	private int home_type = 0;// 一级分类
	private int currenttype = 0;// 滑动第几个item
	private HashMap<String, String> gparams;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		home_type = getIntent().getIntExtra("id", 0);
		currenttype = getIntent().getIntExtra("cid", 0);
		req_type = ALLTYPE[home_type];
		setContentView(R.layout.page_swipe);
		TextView title = (TextView) findViewById(R.id.tv_title);
		String[] titles = getResources().getStringArray(R.array.libname);
		title.setText(titles[home_type]);
		initTab();
		fvByid();

	}

	public void back(View v) {
		if (fManager.getBackStackEntryCount() <= 0) {
//			Log.i(TAG, "onKeyDown");
			finish();
		}else{
		fManager.popBackStack();
		}
	}
	/**
	 * 获取到Tab
	 */
	private void initTab() {

		getDataFromNet(req_type);
	}

	private void getDataFromNet(int id) {
		customProgressDialog.show();
		gparams = new HashMap<String, String>();
		Listener<String> listner;
		listner = backlistener;
		if (id == 24) {
			gparams.put("type", GlobleData.ICOLLECTION);
		} else {
			gparams.put("type", GlobleData.IGETSECONDCLASSFY);
		}
		gparams.put("id", id + "");
		Log.i("Request", "param" + GlobleData.IGETSECONDCLASSFY + "id:" + id
				+ "url:" + C.BASE + C.CLASSFY);
		requestVolley(gparams, C.BASE + C.CLASSFY, listner, Method.POST);

	}

	private void requestVolley(final Map<String, String> gparams, String url,
			Listener<String> listener, int post) {
		VersStringRequest mys = new VersStringRequest(post, url, listener,
				volleyErrorListener) {
			protected Map<String, String> getParams()
					throws com.android.volley.AuthFailureError {
				return gparams;
			};
		};
		mys.setRetryPolicy(HttpUtils.setTimeout());
		mQueue.add(mys);

	}

	private Listener<String> backlistener = new Listener<String>() {
		@Override
		public void onResponse(String response) {
			if (customProgressDialog != null
					&& customProgressDialog.isShowing())
				customProgressDialog.dismiss();
			// 解析结果
			if (response != null) {
				try {
					JSONObject json = new JSONObject(response);
					MResult result = new MResult(json);
					// 成功
					if (result.getCode() == 1) {
						lists = ThreeItem.formList(result.getResult());
						// 初始化Tab
						if (lists != null) {
							initview(lists);
							setListener();
						}
					} else {
						exceptionTips();
					}
				} catch (JSONException e) {
					e.printStackTrace();
					exceptionTips();
				}
			} else {
				exceptionTips();
			}
		}
	};

	// 异常提示
	private void exceptionTips() {
		content_ll.setVisibility(View.GONE);
		toast(getString(R.string.no_internet));
	}

	private void setListener() {
		mViewPager
				.setOnPageChangeListener(new ViewPager.OnPageChangeListener() {

					@Override
					public void onPageSelected(int position) {
						if (rg_nav_content != null
								&& rg_nav_content.getChildCount() > position) {
							((RadioButton) rg_nav_content.getChildAt(position))
									.performClick();
						}
						if (position == 0) {
							isLeftFragment = true;
						} else {
							isLeftFragment = false;
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

		rg_nav_content
				.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {

					@Override
					public void onCheckedChanged(RadioGroup group, int checkedId) {
						if (rg_nav_content.getChildAt(checkedId) != null) {
							TranslateAnimation animation = new TranslateAnimation(
									currentIndicatorLeft,
									((RadioButton) rg_nav_content
											.getChildAt(checkedId)).getLeft(),
									0f, 0f);
							animation.setInterpolator(new LinearInterpolator());
							animation.setDuration(100);
							animation.setFillAfter(true);

							iv_nav_indicator.startAnimation(animation);

							mViewPager.setCurrentItem(checkedId);

							currentIndicatorLeft = ((RadioButton) rg_nav_content
									.getChildAt(checkedId)).getLeft();
							// Log.i("PeriodicalClassfyActivity", ""+((checkedId
							// > 1 ? ((RadioButton)
							// rg_nav_content.getChildAt(checkedId)).getLeft() :
							// 0) - ((RadioButton)
							// rg_nav_content.getChildAt(2)).getLeft()));
							if (rg_nav_content.getChildAt(2) != null) {
								mHsv.smoothScrollTo(
										(checkedId > 1 ? ((RadioButton) rg_nav_content
												.getChildAt(checkedId))
												.getLeft() : 0)
												- ((RadioButton) rg_nav_content
														.getChildAt(2))
														.getLeft(), 0);
							}
						}
					}
				});

	}

	private void initview(List<ThreeItem> lists) {
		DisplayMetrics dm = new DisplayMetrics();
		getWindowManager().getDefaultDisplay().getMetrics(dm);

		if (lists.size() > 4) {
			indicatorWidth = dm.widthPixels / 4;

		} else {
			indicatorWidth = dm.widthPixels / lists.size();
			iv_nav_left.setVisibility(View.GONE);
			iv_nav_right.setVisibility(View.GONE);
		}
		LayoutParams cursor_Params = iv_nav_indicator.getLayoutParams();
		cursor_Params.width = indicatorWidth;//
		iv_nav_indicator.setLayoutParams(cursor_Params);
		mHsv.setSomeParam(rl_nav, iv_nav_left, iv_nav_right, this);

		mInflater = (LayoutInflater) this
				.getSystemService(LAYOUT_INFLATER_SERVICE);

		// LayoutInflater mInflater = LayoutInflater.from(this);

		initNavigationHSV(lists);

		// ImageView back = (ImageView) findViewById(R.id.img_back_header);
		// back.setOnClickListener(new View.OnClickListener() {
		// @Override
		// public void onClick(View v) {
		// finish();
		// // overridePendingTransition(R.anim.slide_left_in,
		// // R.anim.slide_right_out);
		// }
		// });

		mAdapter = new TabFragmentPagerAdapter(getSupportFragmentManager(),
				home_type);
		mViewPager.setAdapter(mAdapter);
		// mViewPager.setCurrentItem(2);
		mViewPager.setOffscreenPageLimit(5);
	}

	@Override
	public void onWindowFocusChanged(boolean hasFocus) {
		 if (hasFocus) {
		mViewPager.setCurrentItem(currenttype);
		// ispressdownbutton=false;
		 }
	}

	private void initNavigationHSV() {
		rg_nav_content.removeAllViews();
		for (int i = 0; i < tabTitle.length; i++) {
			RadioButton rb = (RadioButton) mInflater.inflate(
					R.layout.nav_radiogroup_item, null);
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

	private void initNavigationHSV(List<ThreeItem> tabs) {
		rg_nav_content.removeAllViews();
		for (int i = 0; i < tabs.size(); i++) {
			RadioButton rb = (RadioButton) mInflater.inflate(
					R.layout.nav_radiogroup_item, null);
			if (i == 0) {
				rb.setChecked(true);
			}
			rb.setId(i);
			rb.setText(tabs.get(i).getTitle());
			rb.setLayoutParams(new LayoutParams(indicatorWidth,
					LayoutParams.MATCH_PARENT));
			rg_nav_content.addView(rb);
		}

	}

	private void fvByid() {
		rl_nav = (RelativeLayout) findViewById(R.id.rl_nav);
		content_ll = (ViewGroup) findViewById(R.id.content_ll);
		mHsv = (SwipHorizontalScrollView) findViewById(R.id.mHsv);

		rg_nav_content = (RadioGroup) findViewById(R.id.rg_nav_content);

		iv_nav_indicator = (ImageView) findViewById(R.id.iv_nav_indicator);
		iv_nav_left = (ImageView) findViewById(R.id.iv_nav_left);
		iv_nav_right = (ImageView) findViewById(R.id.iv_nav_right);

		mViewPager = (ViewPager) findViewById(R.id.mViewPager);

	}

	public static class TabFragmentPagerAdapter extends FragmentPagerAdapter {

		private int type;

		public TabFragmentPagerAdapter(FragmentManager fm) {
			super(fm);
		}

		public TabFragmentPagerAdapter(FragmentManager fm, int type) {
			super(fm);
			this.type = type;
		}

		@Override
		public Fragment getItem(int arg0) {
			Fragment ft = null;
			String hanle = null;
			if (lists != null && lists.size() > 0) {
				hanle = lists.get(arg0).getHandle();
			}
			ft = ItemTechResFragment.newInstance(arg0, type, hanle);

			return ft;
		}

		@Override
		public int getCount() {
			if (lists != null && lists.size() > 0) {
				return lists.size();
			} else {
				return tabTitle.length;
			}
		}

	}
}