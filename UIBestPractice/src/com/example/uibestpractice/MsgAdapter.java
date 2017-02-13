package com.example.uibestpractice;

import java.util.List;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

public class MsgAdapter extends ArrayAdapter<Msg> {

	private int resourceId;

	public MsgAdapter(Context context, int resource, List<Msg> objects) {
		super(context, resource, objects);
		resourceId = resource;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		Msg msg = getItem(position);
		View view;
		ViewHolder viewHolder;
		if (convertView == null) { // 判断之前是否有缓存,是否是第一次加载
			// 形成布局
			view = LayoutInflater.from(getContext()).inflate(resourceId, null);
			viewHolder = new ViewHolder();
			viewHolder.leftlayout = (LinearLayout) view
					.findViewById(R.id.left_layout);
			viewHolder.rightlayout = (LinearLayout) view
					.findViewById(R.id.right_layout);
			viewHolder.leftMsg = (TextView) view.findViewById(R.id.left_msg);
			viewHolder.rightMsg = (TextView) view.findViewById(R.id.right_msg);
			view.setTag(viewHolder);
		} else {
			view = convertView;
			viewHolder = (ViewHolder) view.getTag();
		}

		if (msg.getType() == 0) { // 如果是左边  获取消息类型,是接收还是发送
			viewHolder.leftlayout.setVisibility(View.VISIBLE); // 左边显示
			viewHolder.rightlayout.setVisibility(View.GONE); // 隐藏右边,且不占位置
			viewHolder.leftMsg.setText(msg.getContent()); // 显示获得的内容
		} else {
			//如果是发出的消息,则显示右边的消息布局,将左边的消息布局隐藏
			viewHolder.leftlayout.setVisibility(View.GONE); 
			viewHolder.rightlayout.setVisibility(View.VISIBLE); 
			viewHolder.rightMsg.setText(msg.getContent()); // 显示获得的内容
		}

		return view;
	}

	// 内部类,用于缓存
	class ViewHolder {
		LinearLayout leftlayout; // 左边布局
		LinearLayout rightlayout; // 右边布局
		TextView leftMsg; // 左边消息
		TextView rightMsg; // 右边消息
	}

}
