package com.example.uibestpractice;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.Window;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;

/**
 * 2016��7��23��8:13:23
 * 
 * ��д��������ʵ�� ����Nine-PatchͼƬ
 * 
 * @author XFHY
 * 
 * ��������õ�����ʱ���ݱ���,Activity�л��ṩ��һ��onSaveInstanceState()�ص�����,
 * ��������ᱣ֤һ���ڻ��
      ����֮ǰ����,������ǿ���ͨ���������������������ʱ��ʱ���ݵò������������.
   ���������Ȱ�Msgдһ��toString����,�ٽ�ArrayList<Msg>()����һ��һ���ش浽String��������,
   �� �����String���鱣�浽onSaveInstanceState(Bundle outState)�е�Bundle��������,
   �û��ٴμ��ؽ���ʱ,���ٰ����ݶ�ȡ����,ͨ��savedInstanceState.getStringArray("data");
   �ɷ���֮ǰ��ʱ�����String����
 * 
 */
public class MainActivity extends Activity {

	private ListView msgListView;
	private EditText inputText;
	private Button send;
	private MsgAdapter adapter;
	private List<Msg> msgList = new ArrayList<Msg>();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.activity_main);
		msgListView = (ListView) findViewById(R.id.msg_list_view);
		inputText = (EditText) findViewById(R.id.input_text);
		send = (Button) findViewById(R.id.send);

		if (savedInstanceState != null) {
			String[] data = savedInstanceState.getStringArray("data");
			getData(data);
		}else{
			initMsgs(); // ��ʼ����Ϣ����
		}

		// ��ʼ��������,����:������,ÿ��ѡ��Ĳ���,����
		adapter = new MsgAdapter(MainActivity.this, R.layout.msg_item, msgList);
		msgListView.setAdapter(adapter); // ����������
		// ������Ϣ
		send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String content = inputText.getText().toString();
				if (!content.equals("")) // ������͵���Ϣ��Ϊ��
				{
					Msg msg = new Msg(content, Msg.TYPE_SENT);
					msgList.add(msg);
					adapter.notifyDataSetChanged();
					msgListView.setSelection(msgList.size());
					inputText.setText("");
				}
			}
		});
	}

	// ȡ����ʱ���ݵ�msgList
	private void getData(String[] data) {
		String[] temp = new String[2];
		Msg msg;
		for (int i = 0; i < data.length; i++) {
			System.out.println(data[i]);
			temp = data[i].split(":");
			msg = new Msg(temp[0], Integer.parseInt(temp[1]));
			msgList.add(msg);
		}
	}

	// ������ʱ����
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// ʵ����һ��String����,��������List<Msg>�е�ÿ�������toString
		String[] str = new String[msgList.size()];
		for (int i = 0; i < msgList.size(); i++) {
			str[i] = msgList.get(i).toString();
		}
		outState.putStringArray("data", str);
	}

	// ��ʼ������
	private void initMsgs() {
		Msg msg1 = new Msg("Hello guy", Msg.TYPE_RECEIVED);
		msgList.add(msg1);
		Msg msg2 = new Msg("Hello,Who is that?", Msg.TYPE_SENT);
		msgList.add(msg2);
		Msg msg3 = new Msg("This is Tom.Nice talking to you", Msg.TYPE_RECEIVED);
		msgList.add(msg3);
	}
}
