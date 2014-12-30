package com.test;

import java.util.ArrayList;
import java.util.List;

import com.test.MainActivity.MyNotify;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

public class MyAdapter extends BaseAdapter{
	
	private List<MyNotify> list=new ArrayList<MyNotify>();
	private Context context;
	
	public MyAdapter(List<MyNotify> list,Context context){
		this.list=list;
		this.context=context;
	}

	@Override
	public int getCount() {
		return list.size();
	}

	@Override
	public Object getItem(int position) {
		return null;
	}

	@Override
	public long getItemId(int position) {
		return 0;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder holder;
		if (convertView==null) {
			LayoutInflater inflater=((Activity) context).getLayoutInflater();
			convertView=inflater.inflate(R.layout.listitem, parent, false);
			holder=new ViewHolder();
			holder.pkname=(TextView)convertView.findViewById(R.id.pkname);
			holder.time=(TextView)convertView.findViewById(R.id.time);
			holder.title=(TextView)convertView.findViewById(R.id.title);
			holder.message=(TextView)convertView.findViewById(R.id.message);
			convertView.setTag(holder);
		}else {
			holder=(ViewHolder) convertView.getTag();
		}
		holder.pkname.setText("PackageName:"+list.get(position).pkname);
		holder.time.setText("Time:"+list.get(position).time);
		holder.title.setText("Title:"+list.get(position).title);
		holder.message.setText("Content:"+list.get(position).message);
		return convertView;
	}
	
	class ViewHolder{
		TextView pkname,title,message,time;
	}
	

}


