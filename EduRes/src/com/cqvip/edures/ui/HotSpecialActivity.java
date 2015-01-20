package com.cqvip.edures.ui;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Context;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.TextView;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.cqvip.edures.R;
import com.cqvip.edures.adapter.ThreeItemAdapter;
import com.cqvip.edures.base.BaseFragmentActivity;
import com.cqvip.edures.base.C;
import com.cqvip.edures.base.GlobleData;
import com.cqvip.edures.fragment.DetailFragment;
import com.cqvip.edures.model.MResult;
import com.cqvip.edures.model.ThreeItem;
import com.cqvip.edures.util.HttpUtils;
import com.cqvip.edures.widget.DropDownListView;


public class HotSpecialActivity extends BaseFragmentActivity implements OnItemClickListener {
	public static final String ARG_HOMETYPE = "htype";
	private DropDownListView listview;
    private Map<String, String> gparams;
    private ThreeItemAdapter adapter; 
    private int page;
   	private View noresult_rl,loading_probar;
   	private int id;// 所属类型
   	private Context context;
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.fragment_hotspecail_withtitle);
		context = this;
		TextView title = (TextView) findViewById(R.id.tv_title);
		title.setText("热门专题");
		listview = (DropDownListView) findViewById(R.id.list_search);
		listview.setOnItemClickListener(this);
		noresult_rl=findViewById(R.id.noresult_rl);
		loading_probar=findViewById(R.id.loading_probar);
		loading_probar.setVisibility(View.VISIBLE);
		id = getIntent().getIntExtra("id",0);//
		page = 1;
		getData(id,page,GlobleData.GETFIRSTPAGE);
		listview.setOnBottomListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getData(id,page+1,GlobleData.GETNEXTPAGE);
				page++;
			}
			
		});
	}

private void getData(int type,int page,int getWhichPage) {
		
		
		//请求网络
		getDataFromNet(type,page,getWhichPage);
		
	}


	private void getDataFromNet( int hometyp,int page,int getWhichPage) {
		gparams = new HashMap<String, String>();
		Listener<String> listner;
		String reqUrl = null;
		if(getWhichPage==GlobleData.GETFIRSTPAGE){
			listner = backlistener;
	     }else{
	    	 listner = backlistenerMore;
	     }
			
			reqUrl = C.BASE+C.HOTSUBJECT;

			
		gparams.put("id",id+"");
		gparams.put("pageNo",page+"");
		gparams.put("pageSize",20+"");
		requestVolley(gparams, reqUrl,
				listner, Method.POST);
		
		
	}
	private void requestVolley(final Map<String, String> gparams, String url,
			Listener<String> listener, int post) {
		StringRequest mys = new StringRequest(post, url, listener, volleyErrorListener) {
			protected Map<String, String> getParams()
					throws com.android.volley.AuthFailureError {
				return gparams;
			};
		};
		mys.setRetryPolicy(HttpUtils.setTimeout());
		mQueue.add(mys);

	}
	private  Listener<String> backlistener = new Listener<String>() {
		@Override
		public void onResponse(String response) {
			loading_probar.setVisibility(View.GONE);
			if (response != null) {
				try {
					JSONObject json = new JSONObject(response);
					//判断
					MResult result = new MResult(json);
					if(result.getCode()==GlobleData.CODE_SUCESS){
						List<ThreeItem> lists = ThreeItem.formDetailList(result.getResult());
						
						if(lists!=null&&!lists.isEmpty()){
							listview.setVisibility(View.VISIBLE);
							noresult_rl.setVisibility(View.GONE);
							loading_probar.setVisibility(View.GONE);
							adapter = new ThreeItemAdapter(context, lists);
							if(lists.size()<GlobleData.DEFAULYPAGESIZE){
							//	listview.setHasMore(false);
								listview.setAdapter(adapter);
							//	listview.onBottomComplete();
							}else{
							//	listview.setHasMore(true);
								listview.setAdapter(adapter);
							}
						}else{
							listview.setVisibility(View.GONE);
							noresult_rl.setVisibility(View.VISIBLE);
							loading_probar.setVisibility(View.GONE);
						}	
						
					}
						
					
						
					
				} catch (JSONException e) {
					e.printStackTrace();
				}
			 } else {
//					Toast.makeText(getActivity(), "无数据",
//							Toast.LENGTH_LONG).show();
				}
		}
	};  
	
	private  Listener<String> backlistenerMore = new Listener<String>() {
		@Override
		public void onResponse(String response) {
			if(customProgressDialog!=null&&customProgressDialog.isShowing())
			customProgressDialog.dismiss();
			if (response != null) {
				try {
					JSONObject json = new JSONObject(response);
					//判断
					MResult result = new MResult(json);
					if(result.getCode()==GlobleData.CODE_SUCESS){
						List<ThreeItem> lists = ThreeItem.formDetailList(result.getResult());
					if (lists != null && !lists.isEmpty()&&lists.size()==GlobleData.DEFAULYPAGESIZE) {
						adapter.addMoreData(lists);
						listview.onBottomComplete();
					} else if(lists != null &&lists.size()>0){
						adapter.addMoreData(lists);
						listview.setHasMore(false);
						listview.onBottomComplete();	
					}else
					{
						listview.setHasMore(false);
						listview.onBottomComplete();
					}

				}else {
					//错误
					//TODO
				}
			} catch (JSONException e) {
				e.printStackTrace();
			}
		 } else {
//				Toast.makeText(getActivity(), "无数据",
//						Toast.LENGTH_LONG).show();
			}
		}
	};
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		ThreeItem info = adapter.getList().get(position);
		if(info!=null){
			Fragment newFragment = DetailFragment.newInstance(info.getHandle());
			addFragmentToStack(newFragment, R.id.rl_container,DetailFragment.TAG);

		
		}
	} 

}
