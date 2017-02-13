package com.example.listviewtest1;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;

public class FruitAdapter extends ArrayAdapter<Fruit> {

	private int resourceId;

	public FruitAdapter(Context context, int resource, List<Fruit> objects) {
		super(context, resource, objects);
		resourceId = resource; // ListView������ֵ�id
	}

	// ���������ÿ�������������Ļ�ڵ�ʱ��ᱻ����
	/*
	 * getView()��������һ��convertView����,����������ڽ�֮ǰ���غõĲ��ֽ��л���,�Ա�
               ֮����Խ�������.
	 * */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Fruit fruit = getItem(position); // ���Ȼ�õ�ǰ���Fruitʵ��
		// ���ش���Ĳ���
		View view; 
		ViewHolder viewHolder;
		if (convertView == null) {  //�����ǰû�м���,���û�л���View,����Ҫ����һ��
			//���ش���Ĳ���
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			//���һ��ѡ����еĿؼ�id
			viewHolder.fruitimage = (ImageView) view.findViewById(R.id.fruit_image);
			viewHolder.fruitname = (TextView) view.findViewById(R.id.fruit_name);
			view.setTag(viewHolder);  //������ڲ���浽view����
		} else {  //�ڶ��μ���,��ֻ��Ҫ����֮ǰ�Ļ�������
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		//���ò����ļ��пؼ�������
		viewHolder.fruitimage.setImageResource(fruit.getImageId()); // ����ͼƬ������
		viewHolder.fruitname.setText(fruit.getName());
		return view;
	}
	
	//����һ���ڲ���,���ڻ����Ѿ����ع��˵Ĳ����ϵ�id
	class ViewHolder{
		ImageView fruitimage;
		TextView fruitname;
	}
}
