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
		if (convertView == null) { // �ж�֮ǰ�Ƿ��л���,�Ƿ��ǵ�һ�μ���
			// �γɲ���
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

		if (msg.getType() == 0) { // ��������  ��ȡ��Ϣ����,�ǽ��ջ��Ƿ���
			viewHolder.leftlayout.setVisibility(View.VISIBLE); // �����ʾ
			viewHolder.rightlayout.setVisibility(View.GONE); // �����ұ�,�Ҳ�ռλ��
			viewHolder.leftMsg.setText(msg.getContent()); // ��ʾ��õ�����
		} else {
			//����Ƿ�������Ϣ,����ʾ�ұߵ���Ϣ����,����ߵ���Ϣ��������
			viewHolder.leftlayout.setVisibility(View.GONE); 
			viewHolder.rightlayout.setVisibility(View.VISIBLE); 
			viewHolder.rightMsg.setText(msg.getContent()); // ��ʾ��õ�����
		}

		return view;
	}

	// �ڲ���,���ڻ���
	class ViewHolder {
		LinearLayout leftlayout; // ��߲���
		LinearLayout rightlayout; // �ұ߲���
		TextView leftMsg; // �����Ϣ
		TextView rightMsg; // �ұ���Ϣ
	}

}
