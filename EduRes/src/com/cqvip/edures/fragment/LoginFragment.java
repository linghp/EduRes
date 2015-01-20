package com.cqvip.edures.fragment;

import java.util.HashMap;
import java.util.Map;

import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
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
import com.cqvip.edures.model.User;
import com.cqvip.edures.ui.FragmentUserActivity;
import com.cqvip.edures.util.AppUtil;
import com.cqvip.edures.util.HttpUtils;
import com.cqvip.edures.view.CleanableEditText;

public class LoginFragment extends BaseFragment implements OnClickListener {
	private CleanableEditText name_et;
	private CleanableEditText password_et;
	private TextView tv_title;
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		if (reuseView()) {
			return view;
		}
		view = inflater.inflate(R.layout.activity_login, null);
		initview(view);
		return view;
	}

	private void initview(View v) {
		name_et = (CleanableEditText) v.findViewById(R.id.app_login_edit_name);
		password_et = (CleanableEditText) v
				.findViewById(R.id.app_login_edit_pass);
		tv_title = (TextView)v.findViewById(R.id.tv_title);
		tv_title.setText(getString(R.string.login_submit));
		// password_et.setOnEditorActionListener(this);
		v.findViewById(R.id.app_login_btn_submit).setOnClickListener(this);
	}

	private void doTaskLogin() {
		String name = name_et.getText().toString().trim();
		String pwd = password_et.getText().toString().trim();
		if (!validate(name, getResources().getString(R.string.need_username))) {
			if (!name_et.isFocused()) {
				name_et.requestFocus();
			}
			name_et.setShakeAnimation();
			return;
		}
		if (!validate(pwd, getResources().getString(R.string.need_pwd))) {
			if (!password_et.isFocused()) {
				password_et.requestFocus();
			}
			password_et.setShakeAnimation();
			return;
		}
		Map<String, String> params = new HashMap<String, String>();
		params.put("userName", name);
		params.put("passWord", pwd);
		requestVolley(params, C.BASE + C.LOGIN, backlistener, Method.POST);
		customProgressDialog.show();
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

	private boolean validate(String trim, String msg) {
		if (TextUtils.isEmpty(trim)) {
			Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT).show();
			return false;
		} else {
			return true;
		}
	}

	private Listener<String> backlistener = new Listener<String>() {
		@Override
		public void onResponse(String response) {
			// TODO Auto-generated method stub
			if (customProgressDialog != null
					&& customProgressDialog.isShowing())
				customProgressDialog.dismiss();

			User user = null;
			try {
				if (!TextUtils.isEmpty(response)) {
					BaseMessage message = AppUtil.getMessage(response, "User");
					int code = Integer.parseInt(message.getCode());
					String tips = message.getMessage();
					// Log.i("LoginActivity",code+","+tips);

					if (code == GlobleData.SUCESS) {
						// 登陆成功
						user = (User) message.getResult("User");
						Log.i("LoginActivity", user.toString());

						BaseAuth.setUser(user);
						BaseAuth.setLogin(true);
						
		   			    SharedPreferences localUsers = getActivity().getSharedPreferences(C.SharedPreferences_name, getActivity().MODE_PRIVATE);
		   				Editor editor = localUsers.edit();
		   				editor.putString(C.LOGININFO, response);
		   				editor.putString(C.USERNAME, user.getUser_name());
		   				editor.commit();
						toast(getActivity(), tips);
						getFragmentManager().popBackStack();
					 ((FragmentUserActivity)getActivity()).updateLoginUI();
					} else {
						BaseAuth.setUser(user);
						BaseAuth.setLogin(false);
						toast(getActivity(), tips);
					}
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	};

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.app_login_btn_submit:
			doTaskLogin();
			break;

		default:
			break;
		}
	}
}
