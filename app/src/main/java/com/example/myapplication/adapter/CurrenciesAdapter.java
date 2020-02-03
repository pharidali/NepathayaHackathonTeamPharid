package com.example.myapplication.adapter;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import java.util.ArrayList;

import com.example.myapplication.R;
import com.example.myapplication.model.NameModel;
import com.example.myapplication.model.DataValuesModel;

public class CurrenciesAdapter extends BaseAdapter{


	public LayoutInflater l_Inflater;

	ArrayList<NameModel> list_curency_names ;
	public Activity activity;
	int resId;
	public CurrenciesAdapter(Activity a , ArrayList<NameModel> list ) {
	 	this.activity=a;
		this.list_curency_names =list;
	 	this.l_Inflater = (LayoutInflater) activity.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
	}

	public int getCount() {
		return list_curency_names.size();
	}
	public Object getItem(int position) {
		return list_curency_names.get(position);
	}
	public long getItemId(int position) {
		return position;
	}

	public View getView(final int position, View convertView, ViewGroup parent) {
		
		final View_Holder holder;
		
		if (convertView == null) {

			convertView = l_Inflater.inflate(R.layout.row_curency_converter, null);
			holder      = new View_Holder();

			holder.short_title= (TextView)convertView.findViewById(R.id.title);

			holder.imageView= (ImageView) convertView.findViewById(R.id.imageView);

			convertView.setTag(holder);
		} else {
			holder = (View_Holder) convertView.getTag();
		}
		holder.short_title.setText(list_curency_names.get(position).short_name);


		resId = activity.getResources().getIdentifier(list_curency_names.get(position).abrivation.toLowerCase(), "drawable",activity.getPackageName());



		if(resId==0)
		{
			resId = activity.getResources().getIdentifier("tryk", "drawable",activity.getPackageName());

			holder.imageView.setImageResource(resId);
		}
			holder.imageView.setImageResource(resId);

		convertView.setOnClickListener(new View.OnClickListener()
		{
			@Override
			public void onClick(View view)
			{

					DataValuesModel.global_image_id = activity.getResources().getIdentifier(list_curency_names.get(position).abrivation.toLowerCase(), "drawable", activity.getPackageName());
					DataValuesModel.global_country_name = list_curency_names.get(position).short_name;
					DataValuesModel.country_id = list_curency_names.get(position).abrivation;

					if(DataValuesModel.country_id.contains("TRY"))
					{
						DataValuesModel.global_image_id = activity.getResources().getIdentifier("tryk", "drawable", activity.getPackageName());

					}
					if(DataValuesModel.country_id.contains("TMM"))
					{
						DataValuesModel.global_image_id = activity.getResources().getIdentifier("tryk", "drawable", activity.getPackageName());

					}



				((Activity)activity).finish();
			}
		});
		return convertView;
	}
	static class View_Holder
	{
		public TextView short_title;
		public ImageView imageView;
	}
}
