package com.cqvip.edures.adapter;

import java.util.List;

import android.content.Context;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cqvip.edures.R;
import com.cqvip.edures.base.AdapterBase;
import com.cqvip.edures.model.SearchItem;

public class ListItemAdapter extends AdapterBase<SearchItem> {

	private Context context;
	
	public ListItemAdapter(Context context,List<SearchItem> lists){
		this.context = context;
		this.mList = lists;
	}
	/**
	 * 增加更多数据
	 * 
	 * @param moreStatus
	 */
	public void addMoreData(List<SearchItem> moreStatus) {
		this.mList.addAll(moreStatus);// 把新数据增加到原有集合
		this.notifyDataSetChanged();
	}
	
	private static class ViewHolder{
		private TextView title;
		private TextView abstrac;
	}
	
	
	@Override
	protected View getExView(int position, View convertView, ViewGroup parent) {
		
		ViewHolder holder = null;
		if(convertView==null){
			holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(R.layout.item_shortline, null);
				holder.title = (TextView) convertView.findViewById(R.id.tx_item_title);
				holder.abstrac = (TextView) convertView.findViewById(R.id.tx_item_abstract);
				convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText("题名："+mList.get(position).getTitle());
		String abst = mList.get(position).getAbstrac();
		if(!TextUtils.isEmpty(abst)){
			holder.abstrac.setVisibility(View.VISIBLE);
			holder.abstrac.setText("简介："+mList.get(position).getAbstrac());
		}else{
			holder.abstrac.setVisibility(View.GONE);
		}
		
		return convertView;
	}

	@Override
	protected void onReachBottom() {
		// TODO Auto-generated method stub
		
	}

}
