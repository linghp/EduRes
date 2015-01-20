package com.cqvip.edures.fragment;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.json.JSONException;
import org.json.JSONObject;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentTransaction;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ListView;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.cqvip.edures.R;
import com.cqvip.edures.adapter.ThreeItemAdapter;
import com.cqvip.edures.base.BaseFragment;
import com.cqvip.edures.base.BaseFragmentActivity;
import com.cqvip.edures.base.C;
import com.cqvip.edures.base.GlobleData;
import com.cqvip.edures.model.MResult;
import com.cqvip.edures.model.SearchItem;
import com.cqvip.edures.model.ThreeItem;
import com.cqvip.edures.ui.DetailActivity;
import com.cqvip.edures.util.HttpUtils;

public class ItemHotPerListFragment extends BaseFragment implements OnItemClickListener {

	
	public static final String ARG_INNERTYPE = "itype";
	public static final String ARG_ID = "subjectId";
	public static final String ARG_HOMETYPE = "htype";
	private ListView listview;
    private Map<String, String> gparams;
    private ThreeItemAdapter adapter; 
    private int page;
   	private View noresult_rl,loading_probar;
   	private int hometype;// 所属类型
 
	public interface NextCallbacks {
		/**
		 * Callback for when an item has been selected.
		 */
		public void onItemNextSelected(SearchItem info);
	}

	
    /**
     * Create a new instance of ExamPaperListFragment, providing "num"
     * as an argument.
     */
    public static ItemHotPerListFragment newInstance(int hometype) {
    	ItemHotPerListFragment f = new ItemHotPerListFragment();

        // Supply num input as an argument.
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
		listview = (ListView) rootView
				.findViewById(R.id.list_search);
		listview.setOnItemClickListener(this);
		noresult_rl=rootView.findViewById(R.id.noresult_rl);
		loading_probar=rootView.findViewById(R.id.loading_probar);
		loading_probar.setVisibility(View.VISIBLE);
		Bundle bundle = getArguments();
		hometype = bundle.getInt(ARG_HOMETYPE);//
		Log.i("HotList","hometype"+hometype);
		page = 1;
		getData(hometype,page,GlobleData.GETFIRSTPAGE);
		
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
			listner = backlistener;
			
		switch (hometyp) {
		case 0://热门点击
			reqUrl = C.BASE+C.HOTCLICK;
			break;
		case 1://热点专题
			reqUrl = C.BASE+C.HOTSUBJECT;
			break;

		default:
			break;
		}
		gparams.put("pageNo",page+"");
		gparams.put("pageSize",20+"");
		Log.i("HotList","hometpe"+hometyp+","+reqUrl);
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
			Log.i("HotList",response);
			if(hometype==0){
			
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
			}else{
				
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
			String ihandle = info.getIhandle();
			if (ihandle != null && ihandle.startsWith("http")) {
	          ((BaseFragmentActivity)getActivity()).gotoBrowser(ihandle);
				return;
			}
			Intent intent = new Intent(getActivity(),DetailActivity.class);
			intent.putExtra("handle", info.getHandle());
			startActivity(intent);

		}
	}  
}

