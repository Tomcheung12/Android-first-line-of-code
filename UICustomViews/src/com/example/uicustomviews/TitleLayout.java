package com.example.uicustomviews;

import android.app.Activity;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.Toast;

public class TitleLayout extends LinearLayout {

	//��дLinearLayout����2�������Ĺ��캯��
	public TitleLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		//��Ҫ�Բ��ֽ��ж�̬����,��Ҫ����LayoutInflater��ʵ��
		//LayoutInflater��from�������Թ�����һ��LayoutInflater����
		//�ٵ���inflate�ɶ�̬����һ�������ļ�,����:�����ļ�id,���غõĲ��������һ��������
		LayoutInflater.from(context).inflate(R.layout.title, this);
		
		Button titltBack = (Button)findViewById(R.id.title_back);
		Button titltEdit = (Button)findViewById(R.id.title_edit);
		titltBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((Activity)getContext()).finish();  //���ٵ�ǰ�Ļ
			}
		});
		titltEdit.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				Toast.makeText(getContext(), "Edit", 0).show();
			}
		});
	}

}
