package com.example.fragmentbestpractice;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * 这是新闻标题的适配器
 * 
 * @author XFHY
 * 
 */
public class NewsAdapter extends ArrayAdapter<News> {

	private int resourceId;

	public NewsAdapter(Context context, int resource, List<News> objects) {
		super(context, resource, objects);
		resourceId = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		News news = getItem(position);  //获得当前的项的News对象
		View view;
		if (convertView == null) {
			//创建布局
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		} else {
			//恢复之前的缓存的数据
			view = convertView;
		}
		//找到布局里的id
		TextView newsTitleText = (TextView)view.findViewById(R.id.news_title);
		newsTitleText.setText(news.getTitle());   //设置内容
		return view;
	}

}
