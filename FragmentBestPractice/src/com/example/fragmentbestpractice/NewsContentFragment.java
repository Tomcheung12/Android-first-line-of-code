package com.example.fragmentbestpractice;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * 这是碎片的类
 * 新闻内容
 * @author XFHY
 *
 */
public class NewsContentFragment extends Fragment {
	private View view;
	
	//加载布局
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//加载刚刚创建的news_content_frag布局
		view = inflater.inflate(R.layout.news_content_frag,container,false);
		return view;
	}
	
	//用于将新闻的标题和内容显示在界面上
	public void refresh(String newsTitle, String newsContent) {
		View visibilityLayout = view.findViewById(R.id.visibility_layout);
		visibilityLayout.setVisibility(View.VISIBLE);
		TextView newsTitleText = (TextView)view.findViewById(R.id.news_title);
		TextView newsContentText = (TextView)view.findViewById(R.id.news_content);
		newsTitleText.setText(newsTitle);    //刷新新闻的标题
		newsContentText.setText(newsContent); //刷新新闻的内容
	}
}
