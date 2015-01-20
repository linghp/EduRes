package com.cqvip.edures.ui;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Editable;
import android.text.Html;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.cqvip.edures.R;
import com.cqvip.edures.adapter.ListItemAdapter;
import com.cqvip.edures.base.BaseFragmentActivity;
import com.cqvip.edures.base.C;
import com.cqvip.edures.base.GlobleData;
import com.cqvip.edures.fragment.DetailFragment;
import com.cqvip.edures.model.SearchItem;
import com.cqvip.edures.util.HttpUtils;
import com.cqvip.edures.widget.DropDownListView;

public class SearchResultActivity extends BaseFragmentActivity implements
		OnItemClickListener, OnClickListener {
	public final static String TAG = "SearchResultActivity";
	private EditText edit;
	private DropDownListView listview;
	private TextView totalsearch_tv;
	private View noresult_rl;
	private ImageView icon_clear;// 清除按钮
	private TextView btn_back;// 搜索按钮
	private int page;
	private ListItemAdapter adapter;
	private String key;
	private ArrayList<Integer> query_kus;
	private String query_kus_str = "";// 选择的库
	private String field2_str = "";// 选择的字段
	private Context context;
	private String[] field2 = { "default", "title", "author", "keyword",
			"abstract" };
	private int[] query_kus_array = { 2, 8, 15, 18, 24, 25 };

	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_search_result);
		context = this;
		edit = (EditText) findViewById(R.id.et_searchbar);
		listview = (DropDownListView) findViewById(R.id.list_search);
		totalsearch_tv = (TextView) findViewById(R.id.totalsearch_tv);
		btn_back = (TextView) findViewById(R.id.btn_cancel);
		btn_back.setOnClickListener(this);
		noresult_rl = findViewById(R.id.noresult_rl);

		Bundle bundle = getIntent().getExtras();
		key = bundle.getString("key");
		field2_str = field2[bundle.getInt("field2", 0)];
		query_kus = bundle.getIntegerArrayList("query_kus");
		for (int i = 0; i < query_kus.size(); i++) {
			query_kus_str += "m" + query_kus_array[query_kus.get(i) - 1] + ",";
		}
		query_kus_str = query_kus_str.substring(0, query_kus_str.length() - 1);
		// Log.i(TAG, query_kus_str+"::"+query_kus_str.length());
		edit.setText(key);
		init();
		request();
		listview.setOnItemClickListener(this);

		listview.setOnBottomListener(new View.OnClickListener() {

			@Override
			public void onClick(View v) {
				getData(key, page + 1, GlobleData.GETNEXTPAGE);
				page++;
			}

		});

	}

	private void request() {
		// 检查网络
		if (HttpUtils.checkNetWork(context)) {
			// 网络访问,获取首页
			customProgressDialog.show();
			page = 1;
			getData(key, page, GlobleData.GETFIRSTPAGE);
		}
	}

	private void init() {
		icon_clear = (ImageView) this.findViewById(R.id.icon_btn_clear);
		icon_clear.setOnClickListener(this);
		edit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void afterTextChanged(Editable s) {
				String str = edit.getText().toString();
				if (!TextUtils.isEmpty(str)) {
					icon_clear.setVisibility(View.VISIBLE);
				} else {
					icon_clear.setVisibility(View.GONE);
				}
			}
		});
		edit.setOnEditorActionListener(new TextView.OnEditorActionListener() {

			@Override
			public boolean onEditorAction(TextView v, int actionId,
					KeyEvent event) {
				 key = edit.getText().toString().trim();
				if (!TextUtils.isEmpty(key)) {
					request();
				} else {
					toast("请输入关键字");
				}
				return true;
			}
		});
	}

	private void getData(String key, int page, int getWhichPage) {
		Map<String, String> gparams = new HashMap<String, String>();
		Listener<String> listner;
		if (getWhichPage == GlobleData.GETFIRSTPAGE) {
			listner = backlistener;
		} else {
			listner = backlistenerMore;
		}
		gparams.put("query_txt", key);
		gparams.put("pageNo", page + "");
		gparams.put("pageSize", GlobleData.DEFAULYPAGESIZE + "");
		gparams.put("field2", field2_str);
		gparams.put("query_type", "");
		gparams.put("query_kus", query_kus_str);
		requestVolley(gparams, C.BASE + C.SEARCH, listner, Method.POST);
	}

	private void requestVolley(final Map<String, String> gparams, String url,
			Listener<String> listener, int post) {
		StringRequest mys = new StringRequest(post, url, listener,
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

					JSONObject jsonObject = (JSONObject) json
							.getJSONObject("result");
					int count = Integer.parseInt(jsonObject.getString("total"));
					// Toast.makeText(getActivity(), count + "", 1).show();
					if (count != 0) {
						listview.setVisibility(View.VISIBLE);
						noresult_rl.setVisibility(View.GONE);
						totalsearch_tv.setVisibility(View.VISIBLE);
//						String temp = "搜索到与 \"<font face=\"arial\" color=\"red\">"
//								+ key
//								+ "</font>\"  相关的资源 <font face=\"arial\" color=\"red\">"
//								+ count + "</font> 个";
						String temp=String.format(getString(R.string.search_result), key,count);
						totalsearch_tv.setText(Html.fromHtml(temp));
						// 判断
						List<SearchItem> lists = SearchItem
								.formList(jsonObject);
						// System.out.println("长度："+lists.size());
						if (lists != null && !lists.isEmpty())
							adapter = new ListItemAdapter(context, lists);
						if (lists.size() < GlobleData.DEFAULYPAGESIZE) {
							listview.setHasMore(false);
							listview.setAdapter(adapter);
							listview.onBottomComplete();
						} else {
							listview.setHasMore(true);
							listview.setAdapter(adapter);
						}

					} else {
						totalsearch_tv.setVisibility(View.GONE);
						listview.setVisibility(View.GONE);
						noresult_rl.setVisibility(View.VISIBLE);
					}
				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				toast("无数据");

			}
		}
	};
	private Listener<String> backlistenerMore = new Listener<String>() {
		@Override
		public void onResponse(String response) {
			if (customProgressDialog != null
					&& customProgressDialog.isShowing())
				customProgressDialog.dismiss();
			// 解析结果
			if (response != null) {
				try {
					JSONObject json = new JSONObject(response);

					JSONObject jsonObject = (JSONObject) json
							.getJSONObject("result");
					List<SearchItem> lists = SearchItem.formList(jsonObject);
					if (lists != null && !lists.isEmpty()
							&& lists.size() == GlobleData.DEFAULYPAGESIZE) {
						// System.out.println(lists.toString());
						adapter.addMoreData(lists);
						listview.onBottomComplete();
					} else if (lists != null && lists.size() > 0) {
						adapter.addMoreData(lists);
						listview.setHasMore(false);
						listview.onBottomComplete();
					} else {
						listview.setHasMore(false);
						listview.onBottomComplete();
					}

				} catch (JSONException e) {
					e.printStackTrace();
				}
			} else {
				toast("无数据");
			}
		}
	};

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		// forward(DetailActivity.class);
		SearchItem searchItem = (SearchItem) adapter.getItem(position);
		//Bundle bundle = new Bundle();
		//bundle.putString("handle", searchItem.getHandle());
		String ihandle = searchItem.getIhandle();
		if (ihandle != null && ihandle.startsWith("http")) {
           gotoBrowser(ihandle);
			return;
		}
		// bundle.putString("ihandle", searchItem.getIhandle());
//		Intent intent = new Intent(this, DetailActivity.class);
//		intent.putExtras(bundle);
//		startActivityForResult(intent, 0);
		Fragment newFragment = DetailFragment.newInstance(0,0,searchItem.getHandle());
		addFragmentToStack(newFragment, android.R.id.content,DetailFragment.TAG);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.icon_btn_clear:
			edit.setText("");
			break;
		case R.id.btn_cancel:
			finish();
			break;

		default:
			break;
		}
	}

}
