package com.example.myapplication.adapter;

import java.util.ArrayList;


import com.example.myapplication.model.NameModel;
import com.example.myapplication.model.ValuesModel;
import com.example.myapplication.R;


import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class ListAdapter extends BaseAdapter{

	public LayoutInflater l_Inflater;
	public ArrayList<ValuesModel> list_curency_rates;
	public ArrayList<NameModel> list_curency_names;
	public Activity activity;
	int resId;
	public ListAdapter(Activity a , ArrayList<NameModel> list , ArrayList<ValuesModel> list1 ) {
	 	this.activity	 =	a;
		this.list_curency_names	 =	list;
		this.list_curency_rates	 =	list1;
	 	this.l_Inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return list_curency_rates.size();
	}

	public Object getItem(int position) {
		return list_curency_rates.get(position);
	}

	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {

		final View_Holder holder;
		if (convertView == null) {
			convertView = l_Inflater.inflate(R.layout.row_custom, null);
			holder      = new View_Holder();
			holder.short_title= (TextView)convertView.findViewById(R.id.title);
			holder.curency_rate= (TextView)convertView.findViewById(R.id.currency_title);
			holder.long_title= (TextView)convertView.findViewById(R.id.description);
			holder.imageView= (ImageView) convertView.findViewById(R.id.imageView);
			convertView.setTag(holder);
		} else {
			holder = (View_Holder) convertView.getTag();
		}
		holder.short_title.setText(list_curency_names.get(position).short_name);
		holder.long_title.setText(list_curency_names.get(position).abrivation);
		holder.curency_rate.setText(list_curency_rates.get(position).price);
		resId = activity.getResources().getIdentifier(list_curency_names.get(position).short_name.toLowerCase(), "drawable",activity.getPackageName());
		if(list_curency_names.get(position).short_name.contains("TRY"))
		{
			resId = activity.getResources().getIdentifier("tnd", "drawable",activity.getPackageName());
			holder.imageView.setImageResource(resId);
		}else if(resId==0)
		{
			resId = activity.getResources().getIdentifier("xdr", "drawable",activity.getPackageName());
			holder.imageView.setImageResource(resId);
		}
		holder.imageView.setImageResource(resId);
		return convertView;
	}

	static class View_Holder
	{
		public TextView short_title;
		public TextView long_title;
		public ImageView imageView;
		public TextView curency_rate;
		
	}
	
}
