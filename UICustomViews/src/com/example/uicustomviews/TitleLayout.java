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

	//重写LinearLayout中有2个参数的构造函数
	public TitleLayout(Context context, AttributeSet attrs) {
		super(context, attrs);
		//需要对布局进行动态加载,需要借助LayoutInflater来实现
		//LayoutInflater的from方法可以构建出一个LayoutInflater对象
		//再调用inflate可动态加载一个布局文件,参数:布局文件id,加载好的布局再添加一个父布局
		LayoutInflater.from(context).inflate(R.layout.title, this);
		
		Button titltBack = (Button)findViewById(R.id.title_back);
		Button titltEdit = (Button)findViewById(R.id.title_edit);
		titltBack.setOnClickListener(new OnClickListener() {
			
			@Override
			public void onClick(View v) {
				((Activity)getContext()).finish();  //销毁当前的活动
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
