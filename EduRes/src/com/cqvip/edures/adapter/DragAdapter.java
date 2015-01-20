package com.cqvip.edures.adapter;


import java.util.LinkedList;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.cqvip.edures.R;

public class DragAdapter extends BaseAdapter {
	/** TAG*/
	private final static String TAG = "DragAdapter";
	/** 是否显示底部的ITEM */
	private boolean isItemShow = false;
	private Context context;
	/** 控制的postion */
	private int holdPosition;
	/** 是否改变 */
	private boolean isChanged = false;
	/** 是否可见 */
	boolean isVisible = true;
	/** 可以拖动的列表（即用户选择的频道列表） */
	public LinkedList<Integer> channelList;
	/** TextView 频道内容 */
	private ImageView item_text;
	/** 要删除的position */
	public int remove_position = -1;
	
	String[] libname_array;
	private int[] bg ={R.drawable.icon_mian_techdata,R.drawable.icon_main_navi,R.drawable.icon_mian_vedio,
			R.drawable.icon_main_primarytech,R.drawable.icon_mian_project,R.drawable.icon_mian_school};

	public DragAdapter(Context context, LinkedList<Integer> channelList,String[] libname_array) {
		this.context = context;
		this.channelList = channelList;
		this.libname_array=libname_array;
		
	}
	
	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return channelList == null ? 0 : channelList.size();
	}

	@Override
	public Integer getItem(int position) {
		// TODO Auto-generated method stub
		if (channelList != null && channelList.size() != 0) {
			return channelList.get(position);
		}
		return null;
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}
	
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		View view = LayoutInflater.from(context).inflate(R.layout.channel_item, null);
		item_text = (ImageView) view.findViewById(R.id.text_item);
		Integer channel = getItem(position);
		item_text.setImageResource(bg[channel]);
		//item_text.setText(libname_array[channel]);
//		if ((position == 0) || (position == 1)){
////			item_text.setTextColor(context.getResources().getColor(R.color.black));
//			item_text.setEnabled(false);
//		}
		if (isChanged && (position == holdPosition) && !isItemShow) {
			//item_text.setText("");
			item_text.setSelected(true);
			item_text.setEnabled(true);
			isChanged = false;
		}
		if (!isVisible && (position == -1 + channelList.size())) {
			//item_text.setText("");
			item_text.setSelected(true);
			item_text.setEnabled(true);
		}
		if(remove_position == position){
		//	item_text.setText("");
		}
		return view;
	}

	/** 拖动变更频道排序 */
	public void exchange(int dragPostion, int dropPostion) {
		holdPosition = dropPostion;
		Integer dragItem = getItem(dragPostion);
		Log.d(TAG, "startPostion=" + dragPostion + ";endPosition=" + dropPostion);
		if (dragPostion < dropPostion) {
			channelList.add(dropPostion + 1, dragItem);
			channelList.remove(dragPostion);
		} else {
			channelList.add(dropPostion, dragItem);
			channelList.remove(dragPostion + 1);
		}
		isChanged = true;
		notifyDataSetChanged();
	}

	/** 显示放下的ITEM */
	public void setShowDropItem(boolean show) {
		isItemShow = show;
	}
}