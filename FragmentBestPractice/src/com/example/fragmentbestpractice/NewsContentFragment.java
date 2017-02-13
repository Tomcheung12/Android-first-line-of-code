package com.example.fragmentbestpractice;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

/**
 * ������Ƭ����
 * ��������
 * @author XFHY
 *
 */
public class NewsContentFragment extends Fragment {
	private View view;
	
	//���ز���
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		//���ظոմ�����news_content_frag����
		view = inflater.inflate(R.layout.news_content_frag,container,false);
		return view;
	}
	
	//���ڽ����ŵı����������ʾ�ڽ�����
	public void refresh(String newsTitle, String newsContent) {
		View visibilityLayout = view.findViewById(R.id.visibility_layout);
		visibilityLayout.setVisibility(View.VISIBLE);
		TextView newsTitleText = (TextView)view.findViewById(R.id.news_title);
		TextView newsContentText = (TextView)view.findViewById(R.id.news_content);
		newsTitleText.setText(newsTitle);    //ˢ�����ŵı���
		newsContentText.setText(newsContent); //ˢ�����ŵ�����
	}
}
