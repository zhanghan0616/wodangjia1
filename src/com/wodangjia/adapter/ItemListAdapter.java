package com.wodangjia.adapter;

import java.util.List;

import com.example.wodangjialayout.R;

import com.wodangjia.bean.Item;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class ItemListAdapter extends BaseAdapter {

	// 通过构造器获取数据来源
	List<Item> items;
	// 接收上下文参数，以便了解是哪个activity在调用
	Context context;
	// 用来初始化布局文件，可以把一个xml布局转换为对应的android对象view
	LayoutInflater mInflater;

	public ItemListAdapter(List<Item> items, Context context) {
		super();
		this.items = items;
		this.context = context;
		mInflater = LayoutInflater.from(context);
	}

	@Override
	public int getCount() {
		// TODO Auto-generated method stub
		return items.size();
	}

	@Override
	public Object getItem(int position) {
		// TODO Auto-generated method stub
		return items.get(position);
	}

	@Override
	public long getItemId(int position) {
		// TODO Auto-generated method stub
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		
		 ViewHolder viewHolder = null;
		if (convertView == null) {
			viewHolder = new ViewHolder();
			convertView=mInflater.inflate(R.layout.item_view, null);
//			viewHolder.itemImg=(SmartImageView) convertView.findViewById(R.id.item_img);
			viewHolder.itemTitle= (TextView) convertView.findViewById(R.id.item_title);
			viewHolder.itemSubtitle= (TextView) convertView.findViewById(R.id.item_store);
			viewHolder.itemSales= (TextView) convertView.findViewById(R.id.item_sales);
			viewHolder.itemPrice= (TextView) convertView.findViewById(R.id.item_price);
			convertView.setTag(viewHolder);
		}else{
			viewHolder=(ViewHolder) convertView.getTag();
		}
		Item item=items.get(position);
		if(item!=null){
//			viewHolder.itemImg.setImageUrl(item.getGoods_imgs().get(0), R.drawable.item_loadfail, R.drawable.item_loading);
			viewHolder.itemTitle.setText(item.getGoods_title());
			viewHolder.itemSubtitle.setText(item.getGoods_subtitle());
			viewHolder.itemPrice.setText(item.getGoods_price()+"元");
			viewHolder.itemSales.setText("已售"+item.getGoods_sales());
		}
		return convertView;
	}
	private  class ViewHolder
    {
//		SmartImageView itemImg;
        TextView itemTitle;
        TextView itemSubtitle;
        TextView itemPrice;
        TextView itemSales;
    }

}
