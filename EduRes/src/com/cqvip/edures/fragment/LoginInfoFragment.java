package com.cqvip.edures.fragment;

import java.util.ArrayList;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

import com.cqvip.edures.R;
import com.cqvip.edures.base.BaseFragment;
import com.cqvip.edures.base.BaseMessage;
import com.cqvip.edures.base.C;
import com.cqvip.edures.model.User;
import com.cqvip.edures.util.AppUtil;

public class LoginInfoFragment extends BaseFragment {
	private ListView logininfo_lv;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (reuseView()) {
			return view;
		}
		view = inflater.inflate(R.layout.fragment_logininfo, null);
		initview(view);
		return view;
	}

	private void initview(View v) {
		logininfo_lv = (ListView) v.findViewById(R.id.logininfo_lv);
		((TextView)(v.findViewById(R.id.tv_title))).setText(getString(R.string.logininfo));
		SharedPreferences sharedPreferences = getActivity()
				.getSharedPreferences(C.SharedPreferences_name,
						getActivity().MODE_PRIVATE);
		String logininfo = sharedPreferences.getString(C.LOGININFO, "");
		BaseMessage message = null;
		if (!TextUtils.isEmpty(logininfo)) {
			try {
				message = AppUtil.getMessage(logininfo, "User");
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		if (message != null) {
			try {
				User user = (User) message.getResult("User");
				ArrayList<String> userList = user.getList();
				logininfo_lv.setAdapter(new Myadapter(getActivity(), userList));
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

	public static class Myadapter extends BaseAdapter {
		private Context context;
		private ArrayList<String> arrayList;
		private String[] attrs = { "用户名：", "电话：", "邮箱：", "真实姓名：", "学校：", "QQ：",
				"手机：" };

		public Myadapter(Context context, ArrayList<String> arrayList) {
			super();
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
			return position;
		}

		@Override
		public long getItemId(int position) {
			// TODO Auto-generated method stub
			return position;
		}

		@Override
		public View getView(int position, View convertView, ViewGroup parent) {
			if (convertView == null) {
				convertView = LayoutInflater.from(context).inflate(
						R.layout.item_logininfo, null);
			}
			TextView tv1 = (TextView) convertView.findViewById(R.id.tv1);
			TextView tv2 = (TextView) convertView.findViewById(R.id.tv2);
			String temp = arrayList.get(position);
			if (temp.equals("null") || temp.equals("")) {
				temp = "无";
			}
			tv1.setText(attrs[position]);
			tv2.setText(temp);
			return convertView;
		}

	}
}
