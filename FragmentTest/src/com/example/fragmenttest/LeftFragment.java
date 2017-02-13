package com.example.fragmenttest;

import android.app.Fragment;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

public class LeftFragment extends Fragment {
	//重写Fragment的onCreateView()方法,然后在这个方法中通过LayoutInflater的inflate()方法将
	//刚才定义的left_fragment布局动态加载进来
	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		View view = inflater.inflate(R.layout.left_fragment, container,false);
		return view;
	}
}
