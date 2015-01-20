package com.cqvip.edures.fragment;

import java.util.HashMap;
import java.util.Map;

import org.json.JSONArray;
import org.json.JSONObject;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request.Method;
import com.android.volley.Response.Listener;
import com.android.volley.toolbox.StringRequest;
import com.cqvip.edures.R;
import com.cqvip.edures.base.BaseAuth;
import com.cqvip.edures.base.BaseFragment;
import com.cqvip.edures.base.BaseMessage;
import com.cqvip.edures.base.C;
import com.cqvip.edures.base.GlobleData;
import com.cqvip.edures.download.DownloadManageActivity;
import com.cqvip.edures.model.MResult;
import com.cqvip.edures.model.RBase;
import com.cqvip.edures.model.Rinfo;
import com.cqvip.edures.model.Shortfile;
import com.cqvip.edures.tool.DataTools;
import com.cqvip.edures.util.HttpUtils;
import com.mozillaonline.providers.DownloadManager;
import com.mozillaonline.providers.DownloadManager.Request;
import com.mozillaonline.providers.downloads.DownloadService;

public class DetailFragment extends BaseFragment implements OnClickListener{
	public static final String ARG_INNERTYPE = "itype";
	public static final String ARG_ID = "handleId";
	public static final String ARG_HOMETYPE = "htype";
	public static final String TAG = "DetailFragment";
	private int innerType =0;
	private int homeType = 0;
	private TextView modle_title;
	private TextView title;
	private TextView next_title;
	private TextView content;
	private TextView download;
	private View content_sl;
	private HashMap<String, String> gparams;
	private RBase baseInfo=new RBase();
	private int countFile=0;
	public static DetailFragment newInstance(int innertype,int hometype,String handle) {
		DetailFragment f = new DetailFragment();
		Bundle args = new Bundle();
		args.putInt(ARG_INNERTYPE, innertype);
		args.putInt(ARG_HOMETYPE, hometype);
		args.putString(ARG_ID,handle);
		f.setArguments(args);
		return f;
	}
	public static DetailFragment newInstance(String handle) {
		DetailFragment f = new DetailFragment();
		Bundle args = new Bundle();
		args.putString(ARG_ID,handle);
		f.setArguments(args);
		return f;
	}
	
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
		Bundle savedInstanceState) {
		innerType = getArguments().getInt(ARG_INNERTYPE);
		homeType = getArguments().getInt(ARG_HOMETYPE);
		final String hanleId = getArguments().getString(ARG_ID);
		view =  inflater.inflate(R.layout.detail_content, container, false);
		modle_title = (TextView) view.findViewById(R.id.tv_title);
		modle_title.setText(getString(R.string.detail));
		title = (TextView) view.findViewById(R.id.title_txt);
		next_title = (TextView) view.findViewById(R.id.next_txt);
		content = (TextView) view.findViewById(R.id.content_abst);
		content_sl= view.findViewById(R.id.content_sl);
		content_sl.setVisibility(View.GONE);
		download = (TextView) view.findViewById(R.id.tv_title_right);
		download.setVisibility(View.VISIBLE);
		download.setText("下载");
		download.setOnClickListener(this);
		Log.i("ARG_INNERTYPE","二级分类："+innerType);
		Log.i("ARG_INNERTYPE","一级分类："+homeType);
		//判定用户是否登录，获取userName
			getData(hanleId,DataTools.getUserName(getActivity()));
		startDownloadService();
		
		return view;
	}

    private void startDownloadService() {
	Intent intent = new Intent();
	intent.setClass(getActivity(), DownloadService.class);
	getActivity().startService(intent);
    }
	
    private void startDownload(String url,String username) {
	//String url = "http://www.pptok.com/wp-content/uploads/2012/06/huanbao-1.jpg";
	//url = "http://www.it.com.cn/dghome/img/2009/06/23/17/090623_tv_tf2_13h.jpg";
	//String url = "http://down.mumayi.com/41052/mbaidu";
   // url = "http://192.168.20.134:8086/ningbo-edures/edures-bitstream/ningbo12345/2478/1/HY01094367.PDF?userName=user";
    String holeUrl = C.BASE+C.DOWNLOAD+url+"?"+"userName="+username;	
    Log.i("DownloadManager",holeUrl);
	Uri srcUri = Uri.parse(holeUrl);
	DownloadManager.Request request = new Request(srcUri);
	request.setDestinationInExternalPublicDir(
		Environment.DIRECTORY_DOWNLOADS, "/");
	request.setDescription("test!!!");
	 DownloadManager mDownloadManager = new DownloadManager(getActivity().getContentResolver(),
			getActivity().getPackageName());
	mDownloadManager.enqueue(request);
    }
    
	private void getData(String subjectId,String userName) {
		//请求网络
		getDataFromNet(subjectId,userName);
		customProgressDialog.show();
	}


	private void getDataFromNet(String subjectId,String userName) {
		gparams = new HashMap<String, String>();
		Listener<String> listner;
			listner = backlistener;

		gparams.put("handle", subjectId);
		gparams.put("userName", userName);
		Log.i("gparams","sub"+subjectId+","+userName);
		requestVolley(gparams, C.BASE + C.DETAIL,
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
			if(customProgressDialog!=null&&customProgressDialog.isShowing())
			customProgressDialog.dismiss();
			//解析结果
			Log.i("Listener",response);
			RBase info;
			try {
				JSONObject json = new JSONObject(response);
				MResult result = new MResult(json);
				if(result.getCode()==GlobleData.CODE_SUCESS){
					content_sl.setVisibility(View.VISIBLE);
				info = formNexttitle(result.getResult());
				Log.i("Download",info.getFiles()[0].getUrl());
				baseInfo = info;
				setView(info);
				}else{
					Toast.makeText(getActivity(),result.getMessage(), Toast.LENGTH_SHORT).show();
				}
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			
		}

	}; 
	
	private RBase formNexttitle(String response) throws Exception {
		JSONObject json = new JSONObject(response);
		BaseMessage base = new BaseMessage();
		String result = json.getString("dcvalue");
		Shortfile[] arrayfiles = null;
		if(!json.isNull("files")){

			JSONArray array = json.getJSONArray("files");
			arrayfiles = Shortfile.formList(array);
		}
		 
		Rinfo res = (Rinfo) base.getRinfoResult(result,"Rinfo");
		String title = res.getDc_title_null();
		
		String t1 = "作者：";
		String t2 = "关键词：";
		String t3 = "中文刊名：";
		String t4 = "年/期：";
		String t5 = "卷：";
		String t6 = "分类号：";
		String t7 = "机构：";
		String t8 = "第一作者：";
		String t9 = "第一作者单位：";
		String t10 = "收录数据库：";
		String t11 = "授予单位：";
		String t12 = "学位授予单位";
		String t13 = "导师姓名";
		String t14 = "授予年度";
		String t15 = "研究方向";
		String t16 = "类型";
		String t17 = "项目基金";
		String t18 = "会议名称：";
		String t19 = "会议时间：";
		String t20 = "会议地点：";
		String t21 = "主办方：";
		String t22 = "出版单位：";
		String t23 = "出版日期：";
		String t24 = "参考资料：";
		String t25 = "原文地址：";
		String t26 = "类型：";
		String t27 = "学校地址：";
		String t28 = "邮编：";
		String t29 = "联系电话：";
		String t30 = "历史沿革：";
		String t31 = "办学理念：";
		String t32 = "办学特色：";
		String t33 = "师资建设：";
		String t34 = "校训校歌：";
		String t35 = "学校传真：";
		String t36 = "类别：";
		String t37 = "备注：";
		String t38 = "网站：";
		String tt1 =formfield(res.getDc_contributor_author(), t1);
		String tt2 =formfield(res.getDc_subject_null(), t2);
		String tt3 =formfield(res.getZk_string_namecn(), t3);
		String tt4 =formfield(addfield(res.getZk_string_year(),res.getZk_string_num()), t4);
		String tt5 =formfield(res.getZk_string_Vol(), t5);
		String tt6 =formfield(res.getDc_string_CategoryNo(), t6);
		String tt7 =formfield(res.getZk_string_Organ(), t7);
		String tt8 =formfield(res.getZk_string_FirstWriter(), t8);
		String tt9 =formfield(res.getZk_string_FirstOrgan(), t9);
		String tt10 =formfield(res.getZk_string_sci(), t10);
		String tt11 =formfield(res.getBs_string_Degree(), t11);
		String tt12 =formfield(res.getBs_string_Unit(), t12);
		String tt13 =formfield(res.getBs_string_TutorsName(), t13);
		String tt14 =formfield(res.getBs_string_DegreeYear(), t14);
		String tt15 =formfield(res.getBs_string_StudyDirection(), t15);
		String tt16 =formfield(res.getDc_title_null(), t16);
		String tt17=formfield(res.getBs_string_FundProject(), t17);
		
		String tt18 =formfield(res.getHy_string_meetingname(), t18);
		String tt19 =formfield(res.getHy_string_MeetingDate(), t19);
		String tt20 =formfield(res.getHy_string_MeetingPlace(), t20);
		String tt21 =formfield(res.getHy_string_HostOrganization(), t21);
		String tt22 =formfield(res.getHy_string_PressOrganization(), t22);
		String tt23=formfield(res.getHy_string_PressDate(), t23);
		String tt24 =formfield(res.getBs_string_References(), t24);
		String tt25 =formfield(res.getNb_string_address(), t25);
		String tt26 =formfield(res.getNb_string_sub(), t26);
		
		String tt27 =formfield(res.getNb_mlk_address(), t27);
		String tt28 =formfield(res.getNb_mlk_code(), t28);
		String tt29 =formfield(res.getNb_mlk_telephone(), t29);
		String tt30 =   formfield(res.getNb_mlk_history(), t30);
		
		String tt31 =formfield(res.getNb_mlk_idea(), t31);
		String tt32 =formfield(res.getNb_mlk_characteristic(), t32);
		
		String tt33 =formfield(res.getNb_mlk_teaconstruct(), t33);
		String tt34 =formfield(res.getNb_mlk_mottoandsong(), t34);
		String tt35 =formfield(res.getNb_mlk_fox(), t35);
		String tt36 =formfield(res.getNb_mlk_category(), t36);
		String tt37 =formfield(res.getNb_zlk_other(), t37);
		String tt38 =formfield(res.getNb_mlk_website(), t38);
		
		String nexttitle = null;
		StringBuilder builder = new StringBuilder();
		builder.append(tt1);
		builder.append(tt2);
		builder.append(tt3);
		builder.append(tt4);
		builder.append(tt5);
		builder.append(tt6);
		builder.append(tt7);
		builder.append(tt8);
		builder.append(tt9);
		builder.append(tt10);
		builder.append(tt11);
		builder.append(tt12);
		builder.append(tt13);
		builder.append(tt14);
		builder.append(tt15);
		builder.append(tt16);
		builder.append(tt17);
		builder.append(tt18);
		builder.append(tt19);
		builder.append(tt20);
		builder.append(tt21);
		builder.append(tt22);
		builder.append(tt23);
		builder.append(tt24);
		builder.append(tt25);
		builder.append(tt26);
		builder.append(tt27);
		builder.append(tt28);
		builder.append(tt29);
		builder.append(tt30);
		builder.append(tt31);
		builder.append(tt32);
		builder.append(tt33);
		builder.append(tt34);
		builder.append(tt35);
		builder.append(tt36);
		builder.append(tt37);
		builder.append(tt38);
		nexttitle = builder.toString();
		String abst = res.getDc_description_abstract();
		 return new RBase(title,nexttitle,abst,arrayfiles,null);
	}
	

private String addfield(String zk_string_year, String zk_string_num) {
   String str = "";
	if(!TextUtils.isEmpty(zk_string_year)&&!TextUtils.isEmpty(zk_string_num)){
	   str = zk_string_year+","+zk_string_num;
   }
	return str;
}

private String formfield(String res, String t1) {
	String tt1;
	if(TextUtils.isEmpty(res)){
		tt1 = "";
	}else{
		tt1 = t1+res+"\n";
	}
	return tt1;
}
	private void setView(RBase info) {
		if(!TextUtils.isEmpty(info.getTitle())){
			title.setText(info.getTitle());
		}else{
			title.setVisibility(View.GONE);
		}
		if(!TextUtils.isEmpty(info.getNexttitle())){
			next_title.setText(info.getNexttitle());
		}else{
		next_title.setVisibility(View.GONE);
		}
		if(!TextUtils.isEmpty(info.getAbst())){
			content.setText(info.getAbst());
		}else{
			content.setVisibility(View.GONE);
		}
		Shortfile[] files = info.getFiles();
		if(files!=null&&files.length>1){
			countFile=files.length;
			download.setText("下载"+"("+files.length+")");
		}
		
		if(!files[0].isIsdown()){
			download.setEnabled(false);
			download.invalidate();
		}
		
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
			case R.id.tv_title_right:
				//判断是否有权限
				if(!baseInfo.getFiles()[0].isIsdown()){
					download.setEnabled(false);
					break;
				}
				Shortfile[] sfiles = baseInfo.getFiles();
				if(sfiles==null||sfiles.length==0){
					break;
				}
				for (int i = 0; i < sfiles.length; i++) {
					startDownload(sfiles[i].getUrl(),DataTools.getUserName(getActivity()));
					Log.i("onClick",sfiles[i].getUrl()+","+DataTools.getUserName(getActivity()));
				}
				Intent intent=new Intent(getActivity(), DownloadManageActivity.class);
				startActivity(intent);
				getActivity().overridePendingTransition(R.anim.slide_right_in, R.anim.scale_out_left);
				break;
		default:
			break;
		}
	}
	
	
}
