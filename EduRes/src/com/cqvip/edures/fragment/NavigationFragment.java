package com.cqvip.edures.fragment;


import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.cqvip.edures.R;
import com.cqvip.edures.base.BaseFragment;
import com.cqvip.edures.ui.TechResClassfyActivity;

public class NavigationFragment extends BaseFragment implements OnItemClickListener {

	private String[] array_navi;
	private ListView listview;
	private int[][] array;
 	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {

		View v = inflater.inflate(R.layout.fragment_navigation, null);
		ImageView back = (ImageView) v.findViewById(R.id.back);
		back.setVisibility(View.GONE);
		TextView title = (TextView)v.findViewById(R.id.tv_title);
		title.setText(getString(R.string.Navigation));
		listview = (ListView) v.findViewById(R.id.list_navi);
		listview.setOnItemClickListener(this);
		array_navi = getResources().getStringArray(R.array.navi_title);
		 ArrayAdapter<String> adapter = new ArrayAdapter<String>(getActivity(),
	                R.layout.tv_item, array_navi);
		array = new int[6][];
		
		array[0] = new int[]{0,1,2,3,4};
		array[1] = new int[]{0,2,5};
		array[3] = new int[]{0};
		array[4] = new int[]{0};
		array[5] = new int[]{0};
		listview.setAdapter(adapter);
		return v;
	}

 	
	@Override
	public void onItemClick(AdapterView<?> parent, View view, int position,
			long id) {
		Intent intent = new Intent(getActivity(),TechResClassfyActivity.class);
		switch (position) {
		case 0:
		case 1:
		case 2:
		case 3:
		case 4:
			intent.putExtra("cid", position);
			intent.putExtra("id", 0);
			
			break;
		case 5:
		case 6:
		case 7:
			intent.putExtra("cid", position-5);
			intent.putExtra("id", 1);
			break;
		case 8:
			intent.putExtra("cid", 0);
			intent.putExtra("id", 2);
			break;
		case 9:
		case 10:
			intent.putExtra("cid", 0);
			intent.putExtra("id", 3);
			break;
		case 11:
			intent.putExtra("cid", 0);
			intent.putExtra("id", 4);
			break;
		case 12:
			intent.putExtra("cid", 0);
			intent.putExtra("id", 5);
			break;

		default:
			break;
		}
		startActivity(intent);
		getActivity().overridePendingTransition(R.anim.slide_right_in, R.anim.scale_out_left);
	}
	
	  
}
