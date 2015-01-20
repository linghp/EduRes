package com.cqvip.edures.ui;

import java.util.LinkedList;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.cqvip.edures.R;
import com.cqvip.edures.adapter.DragAdapter;
import com.cqvip.edures.base.BaseAuth;
import com.cqvip.edures.base.BaseMainFragmentActivity;
import com.cqvip.edures.base.C;
import com.cqvip.edures.view.DragGrid;

public class FragmentHomeActivity extends BaseMainFragmentActivity implements OnItemClickListener, OnClickListener {
	public static final String TAG="FragmentHomeActivity";
	
	private DragGrid userGridView;
	DragAdapter userAdapter;
	LinkedList<Integer> chanelList = new LinkedList<Integer>();
	//private EditText search_et;
	private String[] libname_array;
	private SharedPreferences userChoice_SharedP;// 保存用户拖拽后的顺序
	//private TextView btn_login;
	private Context context;
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		context = this;
		userGridView = (DragGrid) findViewById(R.id.userGridView);
		//search_et = (EditText) findViewById(R.id.et_search_keyword);
		
		libname_array = getResources().getStringArray(R.array.libname);
		userChoice_SharedP = getSharedPreferences(C.SharedPreferences_name, MODE_PRIVATE);
		String userchoice_str = userChoice_SharedP.getString("userchoice_str",
				"");
		if (!TextUtils.isEmpty(userchoice_str)) {
			String[] userchoice_arr = userchoice_str.split(",");
			for (int i = 0; i < userchoice_arr.length; i++) {
				chanelList.add(Integer.valueOf(userchoice_arr[i]));
			}
		} else {
			for (int i = 0; i < 6; i++) {
				chanelList.add(Integer.valueOf(i));
			}
		}
		userAdapter = new DragAdapter(this, chanelList, libname_array);
		userGridView.setAdapter(userAdapter);
		userGridView.setOnItemClickListener(this);
	}

	
	public void onEditTextClick(View view){
		Intent intent=new  Intent(this, SearchActivity.class);
		startActivity(intent);
		overridePendingTransition(R.anim.slide_right_in, R.anim.scale_out_left);
	}

	@Override
	protected void onStop() {
		super.onStop();
	}

	@Override
	protected void onDestroy() {
		StringBuffer user_choice = new StringBuffer();
		for (int i = 0; i < chanelList.size(); i++) {
			if (i == chanelList.size() - 1) {
				user_choice.append(chanelList.get(i));
			} else {
				user_choice.append(chanelList.get(i) + ",");
			}
		}
		Editor editor=userChoice_SharedP.edit();
		editor.putString("userchoice_str", user_choice.toString());
		editor.commit();
		super.onDestroy();
	}
	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
//		Toast.makeText(this, libname_array[chanelList.get(position)] + "", 1)
//				.show();
		Intent intent = new Intent(context,TechResClassfyActivity.class);
		//id表示六个数据库，0为教育学术，1为导航库，。。。。
		intent.putExtra("id", chanelList.get(position));
		startActivity(intent);
		Log.i(TAG, "onItemClick");
		overridePendingTransition(R.anim.scale_in_left, R.anim.slide_right_out);
		
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_search:
			
			
			break;

		default:
			break;
		}
		
	}

}
