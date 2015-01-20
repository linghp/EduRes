package com.cqvip.edures.ui;

import java.util.ArrayList;
import java.util.List;

import android.content.Context;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.util.Log;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.BaseAdapter;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.PopupWindow;
import android.widget.TextView;

import com.cqvip.edures.MyApplication;
import com.cqvip.edures.R;
import com.cqvip.edures.base.BaseActivity;
import com.cqvip.edures.base.BaseFragmentActivity;
import com.cqvip.edures.db.SearchHistory;
import com.cqvip.edures.db.SearchHistoryDao;
import com.cqvip.edures.db.SearchHistoryDao.Properties;
import com.cqvip.edures.view.PopupMenu;
import com.cqvip.edures.view.PopupMenu.onMyItemOnClickListener;

public class SearchActivity extends BaseFragmentActivity implements OnClickListener,
		onMyItemOnClickListener, OnItemClickListener {
	public final static String TAG="SearchActivity";
	private EditText edit;
	private TextView tx_search_condition;// 显示搜索条件
	private ImageView icon_search;// 搜索按钮
	private ImageView icon_clear;// 清除按钮
	private PopupMenu popup; // 弹出框
	private String[] searchType_arr;// 条件
	private TextView btn_search;// 搜索按钮
	private String search_condition;
	private View search_layout_container;
	private LinearLayout hot_history_ll;// 热门搜索和搜索记录
	private ViewGroup hotwords_ll;// 热门搜索
	private ListView history_lv;// 历史记录
	private ViewGroup resource_lib_vg;// 资源库
	
	private int field2;//搜索字段
	
	private ArrayList<String> historys = new ArrayList<String>();
	private ArrayList<Integer> reslibs = new ArrayList<Integer>();

	private SearchHistoryDao searchHistoryDao;
	private SQLiteDatabase db;
   
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.searchall);
		getDB();
		init();
	}

	private void init() {
		searchType_arr = getResources().getStringArray(R.array.searchtype);
		edit = (EditText) findViewById(R.id.et_search_keyword);
		icon_search = (ImageView) this.findViewById(R.id.img_search_icon);
		icon_clear = (ImageView) this.findViewById(R.id.icon_btn_clear);
		search_layout_container = this.findViewById(R.id.ly_left_contain);
		tx_search_condition = (TextView) this
				.findViewById(R.id.tv_search_condition);
		btn_search = (TextView) findViewById(R.id.btn_search);
		hot_history_ll = (LinearLayout) findViewById(R.id.hot_history_ll);
		hotwords_ll = (ViewGroup) findViewById(R.id.hotwords_ll);
		resource_lib_vg = (ViewGroup) findViewById(R.id.resource_lib_vg);
		history_lv = (ListView) findViewById(R.id.history_lv);
		history_lv.setOnItemClickListener(this);
		btn_search.setOnClickListener(this);
		
		getHistoryFromDB();

		View del_ll = LayoutInflater.from(this).inflate(
				R.layout.foot_searchistory, null);
		del_ll.setOnClickListener(this);
		history_lv.addFooterView(del_ll);
		history_lv.setAdapter(new HistoryAdapter(this, historys));
		popup = new PopupMenu(this, searchType_arr);
		popup.setonMyItemOnClickListener(this);
		search_layout_container.setOnClickListener(this);
		//tx_search_condition.setOnClickListener(this);
		icon_clear.setOnClickListener(this);
		// search_condition = GlobleData.QUERY_ALL;
		edit.addTextChangedListener(new TextWatcher() {

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				// TODO Auto-generated method stub

			}

			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
				// TODO Auto-generated method stub

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
				String key = edit.getText().toString().trim();
				if (!TextUtils.isEmpty(key)) {
					addToDB(key);
				}
				Log.i(TAG, "onEditorAction");
				startSearch(key);

				// key = edit.getText().toString().trim();
				// if (TextUtils.isEmpty(key)) {
				// return true;
				// }
				// // 检查网络
				// if (!HttpUtils.checkNetWork(context)) {
				// return false;
				// }
				// // 网络访问,获取首页
				// customProgressDialog.show();
				// page = 1;
				// getData(key, page, GlobleData.GETFIRSTPAGE);
				// //Log.i("SearchExamFragment_onEditorAction",
				// "onEditorAction");
				return true;
			}
		});

		//热门资源
		String tempstr = "教育_文献_改革_人才_高中_数学_语文_英语_教学_课堂_沟通_教师_学生_宁波_中小学_课程_家长_心理_计算机";
		String[] hotwords = tempstr.split("_");
		TextView title = (TextView) LayoutInflater.from(this).inflate(
				R.layout.hotwords_item, null);
		title.setBackgroundColor(getResources().getColor(
				android.R.color.transparent));
		title.setText("热门搜索");
		title.setTextColor(getResources().getColor(R.color.hot_textcolor));
		hotwords_ll.addView(title);
		for (int i = 0; i < hotwords.length; i++) {
			TextView textView = (TextView) LayoutInflater.from(this).inflate(
					R.layout.hotwords_item, null);
			textView.setText(hotwords[i]);
			textView.setOnClickListener(this);
			hotwords_ll.addView(textView);
		}
		
		//资源库
		String[] libname_array = getResources().getStringArray(R.array.libname);
		TextView title_reslib = (TextView) LayoutInflater.from(this).inflate(
				R.layout.hotwords_item, null);
		title_reslib.setText("资源库(全选)");
		title_reslib.setTag("");
		title_reslib.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				for (int i = 1; i < resource_lib_vg.getChildCount(); i++) {
					if(v.getTag().equals("")){
						Log.i(TAG," title_reslib_onclick1");
					resource_lib_vg.getChildAt(i).setTag("tag");
					resource_lib_vg.getChildAt(i).setBackgroundResource(R.drawable.reslib_itemselected_bg);
					}else{
						Log.i(TAG," title_reslib_onclick2");
						resource_lib_vg.getChildAt(i).setTag("");
						resource_lib_vg.getChildAt(i).setBackgroundResource(R.drawable.reslib_item_bg);	
					}
				}
				if(v.getTag().equals("")){
					v.setTag("selected");
				}else{
					v.setTag("");
				}
			}
		});
		title_reslib.setBackgroundColor(getResources().getColor(
				android.R.color.transparent));
		title_reslib.setTextColor(getResources().getColor(R.color.hot_textcolor));
		resource_lib_vg.addView(title_reslib);
		for (int i = 0; i < libname_array.length; i++) {
			TextView textView = (TextView) LayoutInflater.from(this).inflate(
					R.layout.hotwords_item, null);
			textView.setText(libname_array[i]);
			textView.setBackgroundResource(R.drawable.reslib_item_bg);
			textView.setTag("");
			textView.setTextSize(14);
			//textView.setId(i);
			textView.setOnClickListener(new OnClickListener() {
				
				@Override
				public void onClick(View v) {
					if(v.getTag()!=null&&v.getTag().equals("")){
						v.setTag("tag");
						v.setBackgroundResource(R.drawable.reslib_itemselected_bg);
					}else{
						v.setTag("");
						v.setBackgroundResource(R.drawable.reslib_item_bg);
					}
				}
			});
			resource_lib_vg.addView(textView);
		}
			
	}

	private void getHistoryFromDB() {
		List<SearchHistory> searchHistories = searchHistoryDao.queryBuilder()
				.orderDesc(Properties.Id).listLazy();
		historys.clear();
		if (searchHistories.size() > 0) {
			history_lv.setVisibility(View.VISIBLE);
		} else {
			history_lv.setVisibility(View.GONE);
		}
		// 搜索历史
		for (SearchHistory searchHistory : searchHistories) {
			historys.add(searchHistory.getHistoryword());
		}
	}

	private void getDB() {
		searchHistoryDao = ((MyApplication) getApplication()).daoSession
				.getSearchHistoryDao();
		db = ((MyApplication) getApplication()).db;
	}

	private void startSearch(String key) {
		Log.i(TAG, "startSearch");
		if (TextUtils.isEmpty(key)) {
			toast("请输入关键字");
			return;
		}
		
		reslibs.clear();
		for (int i = 1; i < resource_lib_vg.getChildCount(); i++) {
			if(!resource_lib_vg.getChildAt(i).getTag().equals("")){
				reslibs.add(i);
			}
		}
		if(reslibs.size()==0){
			toast("请选择至少一个库");
			return;	
		}
		
		Bundle bundle = new Bundle();
		bundle.putString("key", key);
		bundle.putInt("field2", field2);
		bundle.putIntegerArrayList("query_kus", reslibs);
        Intent intent = new Intent(this,SearchResultActivity.class);
        intent.putExtras(bundle);
        startActivityForResult(intent, 0);
        overridePendingTransition(R.anim.slide_right_in, R.anim.scale_out_left);
	}

	@Override
	protected void onActivityResult(int requestCode, int resultCode, Intent data) {
		// TODO Auto-generated method stub
		super.onActivityResult(requestCode, resultCode, data);
		if(requestCode==0){
			getHistoryFromDB();
		}
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.ly_left_contain:
			if (popup != null) {
				if (popup.isShowing()) {
					popup.closeWindow();
					Log.i("SearchActivity", "showing");
				} else {
					popup.showWindow(v);
					Log.i("SearchActivity", "unshowing");
				}
			}
			break;
		case R.id.icon_btn_clear:
			edit.setText("");
			break;
		case R.id.delhistory_ll:
			searchHistoryDao.deleteAll();
			history_lv.setVisibility(View.GONE);
			break;
		case R.id.btn_search:
			String editcontent = edit.getText().toString().trim();
			if (!TextUtils.isEmpty(editcontent)) {
				addToDB(edit.getText().toString().trim());
			}
			startSearch(editcontent);
			Log.i(TAG, "onclick_btn_search");
			break;
		case R.id.hotword_tv:
			startSearch(((TextView) v).getText().toString());
			break;
		default:
			break;
		}

	}

	private void addToDB(String trim) {
		SearchHistory searchHistory = new SearchHistory(trim);
		// 判断重复的
		List temp_list = searchHistoryDao.queryBuilder()
				.where(Properties.Historyword.eq(trim)).list();
		if (temp_list.size() > 0) {
			searchHistoryDao.deleteInTx(temp_list);
		}
		searchHistoryDao.insert(searchHistory);
	}

	@Override
	public void onMyItemClick(ListView view, final PopupWindow popupWindow) {
		view.setOnItemClickListener(new OnItemClickListener() {

			@Override
			public void onItemClick(AdapterView<?> adapterView, View view,
					int position, long id) {
				switch (position) {
				case 0:
					// search_condition = GlobleData.QUERY_ALL;
					tx_search_condition.setVisibility(View.GONE);
					icon_search.setVisibility(View.VISIBLE);
					// search_condition = getSearchType(position);
					field2=position;
					break;
				case 1:
				case 2:
				case 3:
				case 4:
					tx_search_condition.setVisibility(View.VISIBLE);
					icon_search.setVisibility(View.GONE);
					tx_search_condition.setText(searchType_arr[position]);
					// search_condition = getSearchType(position);
					field2=position;
					break;
				}

				if (popupWindow != null && popupWindow.isShowing()) {
					popupWindow.dismiss();
				}
			}

			// private String getSearchType(int position) {
			// switch (position) {
			// case 0:
			// return GlobleData.QUERY_ALL;
			// case 1:
			// return GlobleData.QUERY_TITLE;
			// case 2:
			// return GlobleData.QUERY_AUTHOR;
			// case 3:
			// return GlobleData.QUERY_ISBN;
			// default:
			// return GlobleData.QUERY_ALL;
			// }
			// }
		});
	}

	@Override
	public boolean dispatchTouchEvent(MotionEvent ev) {
		if (popup.isShowing()) {
			popup.closeWindow();
		}
		return super.dispatchTouchEvent(ev);
	}

	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Log.i(TAG, "onItemClick");
		startSearch(historys.get(position));
	}

	public static class HistoryAdapter extends BaseAdapter {

		private Context context;
		private ArrayList<String> arrayList;

		public HistoryAdapter(Context context, ArrayList<String> arrayList) {
			this.context = context;
			this.arrayList = arrayList;
		}

		@Override
		public int getCount() {
			// TODO Auto-generated method stub
			return arrayList.size();
		}

		@Override
		public Object getItem(int position) {
			// TODO Auto-generated method stub
			return arrayList.get(position);
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		static class ViewHolder {
			TextView historyname;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			final ViewHolder holder;
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_searchistory, null);
				holder = new ViewHolder();
				holder.historyname = (TextView) convertView
						.findViewById(R.id.history_tv);
				convertView.setTag(holder);
			} else {
				holder = (ViewHolder) convertView.getTag();
			}
			holder.historyname.setText(arrayList.get(position));

			return convertView;
		}

	}
	
}
