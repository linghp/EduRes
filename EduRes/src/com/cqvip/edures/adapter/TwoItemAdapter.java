package com.cqvip.edures.adapter;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.cqvip.edures.R;
import com.cqvip.edures.base.AdapterBase;
import com.cqvip.edures.model.TwoItem;



public class TwoItemAdapter extends AdapterBase<TwoItem>{

private Context	context;;
	
	public TwoItemAdapter (Context context,List<TwoItem> lists) {
		this.context = context;
		this.mList = lists;
	}
	
	/**
	 * 增加更多数据
	 * 
	 * @param moreStatus
	 */
	public void addMoreData(List<TwoItem> moreStatus) {
		this.mList.addAll(moreStatus);// 把新数据增加到原有集合
		this.notifyDataSetChanged();
	}
	
	
	private static class ViewHolder{
		private TextView title;
//		private TextView year;
//		private TextView addtime;
	}
	
	@Override
	protected View getExView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder = null;
		if(convertView==null){
			holder = new ViewHolder();
				convertView = LayoutInflater.from(context).inflate(R.layout.item_shortline, null);
				holder.title = (TextView) convertView.findViewById(R.id.tx_item_title);
				//holder.abstrac = (TextView) convertView.findViewById(R.id.tx_item_abstract);
				convertView.setTag(holder);
		}else{
			holder = (ViewHolder) convertView.getTag();
		}
		holder.title.setText(""+mList.get(position).getName());
		
		return convertView;
	}
	@Override
	protected void onReachBottom() {
		
		
	}
	
}
