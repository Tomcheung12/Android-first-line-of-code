package com.example.fragmentbestpractice;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.TextView;

/**
 * �������ű����������
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
		News news = getItem(position);  //��õ�ǰ�����News����
		View view;
		if (convertView == null) {
			//��������
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
		} else {
			//�ָ�֮ǰ�Ļ��������
			view = convertView;
		}
		//�ҵ��������id
		TextView newsTitleText = (TextView)view.findViewById(R.id.news_title);
		newsTitleText.setText(news.getTitle());   //��������
		return view;
	}

}
