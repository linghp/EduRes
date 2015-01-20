package com.cqvip.edures.fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.cqvip.edures.R;
import com.cqvip.edures.adapter.ThreeItemAdapter;
import com.cqvip.edures.base.BaseFragment;
import com.cqvip.edures.base.C;
import com.cqvip.edures.base.GlobleData;
import com.cqvip.edures.fragment.ItemTechResFragment.NextCallbacks;
import com.cqvip.edures.model.MResult;
import com.cqvip.edures.model.SearchItem;
import com.cqvip.edures.model.ThreeItem;
import com.cqvip.edures.util.HttpUtils;
import com.cqvip.edures.util.VersStringRequest;
import com.cqvip.edures.widget.DropDownListView;

public class HotSpecialFragment extends BaseFragment implements OnItemClickListener {

	
	public static final String TAG = "HotSpecialFragment";
	public static final String ARG_HOMETYPE = "htype";
	private DropDownListView listview;
    private Map<String, String> gparams;
    private ThreeItemAdapter adapter; 
    private int page;
   	private View noresult_rl,loading_probar;
   	private int id;// 所属类型
 
    public static HotSpecialFragment newInstance(int hometype) {
    	HotSpecialFragment f = new HotSpecialFragment();
        Bundle args = new Bundle();
        args.putInt(ARG_HOMETYPE, hometype);
        f.setArguments(args);
        return f;
    }

	

    
    
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		
		View rootView = inflater.inflate(
				R.layout.fragment_hotlist, container, false);
		listview = (DropDownListView) rootView
				.findViewById(R.id.list_search);
		listview.setOnItemClickListener(this);
		noresult_rl=rootView.findViewById(R.id.noresult_rl);
		loading_probar=rootView.findViewById(R.id.loading_probar);
		loading_probar.setVisibility(View.VISIBLE);
		Bundle bundle = getArguments();
		id = bundle.getInt(ARG_HOMETYPE);//
		page = 1;
		getData(id,page,GlobleData.GETFIRSTPAGE);
		listview.setOnBottomListener(new View.OnClickListener() {
			
			@Override
			public void onClick(View v) {
				getData(id,page+1,GlobleData.GETNEXTPAGE);
				page++;
			}
			
		});
		
		return rootView;
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
			
			reqUrl = C.BASE+"ningbo-edures/edures-rank-collection";

			
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
							adapter = new ThreeItemAdapter(getActivity(), lists);
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
	
	private void addFragmentToStack(Fragment newFragment, int layoutid,String tag) {
		FragmentTransaction ft = getActivity().getSupportFragmentManager()
				.beginTransaction();
		ft.setCustomAnimations(R.anim.slide_right_in, R.anim.slide_left_out,
				R.anim.slide_left_in, R.anim.slide_right_out);
		ft.replace(layoutid, newFragment,tag);
		ft.setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN);
		ft.addToBackStack(null);
		ft.commit();
	}
	
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

