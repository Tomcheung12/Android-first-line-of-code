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
		resourceId = resource; // ListView的子项布局的id
	}

	// 这个方法在每个子项被滚动到屏幕内的时候会被调用
	/*
	 * getView()方法中有一个convertView参数,这个参数用于将之前加载好的布局进行缓存,以便
               之后可以进行重用.
	 * */
	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Fruit fruit = getItem(position); // 首先获得当前项的Fruit实例
		// 加载传入的布局
		View view; 
		ViewHolder viewHolder;
		if (convertView == null) {  //如果先前没有加载,则就没有缓存View,则需要加载一下
			//加载传入的布局
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			//获得一个选项布局中的控件id
			viewHolder.fruitimage = (ImageView) view.findViewById(R.id.fruit_image);
			viewHolder.fruitname = (TextView) view.findViewById(R.id.fruit_name);
			view.setTag(viewHolder);  //将这个内部类存到view里面
		} else {  //第二次加载,则只需要加载之前的缓存数据
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}
		//设置布局文件中控件的数据
		viewHolder.fruitimage.setImageResource(fruit.getImageId()); // 设置图片和文字
		viewHolder.fruitname.setText(fruit.getName());
		return view;
	}
	
	//这是一个内部类,用于缓存已经加载过了的布局上的id
	class ViewHolder{
		ImageView fruitimage;
		TextView fruitname;
	}
}
