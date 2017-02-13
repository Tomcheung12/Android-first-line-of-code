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
 * 2016年7月23日8:13:23
 * 
 * 编写界面的最佳实现 制作Nine-Patch图片
 * 
 * @author XFHY
 * 
 * 这个程序用到了临时数据保存,Activity中还提供了一个onSaveInstanceState()回调方法,
 * 这个方法会保证一定在活动被
      回收之前调用,因此我们可以通过这个方法来解决活动被回收时临时数据得不到保存的问题.
   这里面我先把Msg写一个toString方法,再将ArrayList<Msg>()数组一个一个地存到String数组里面,
   再 将这个String数组保存到onSaveInstanceState(Bundle outState)中的Bundle类型里面,
   用户再次加载界面时,我再把数据读取出来,通过savedInstanceState.getStringArray("data");
   可返回之前临时保存的String数组
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
			initMsgs(); // 初始化消息数据
		}

		// 初始化适配器,参数:上下文,每个选项的布局,数据
		adapter = new MsgAdapter(MainActivity.this, R.layout.msg_item, msgList);
		msgListView.setAdapter(adapter); // 设置适配器
		// 发送消息
		send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				String content = inputText.getText().toString();
				if (!content.equals("")) // 如果发送的消息不为空
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

	// 取出临时数据到msgList
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

	// 保存临时数据
	@Override
	protected void onSaveInstanceState(Bundle outState) {
		super.onSaveInstanceState(outState);

		// 实例化一个String数组,用来保存List<Msg>中的每个对象的toString
		String[] str = new String[msgList.size()];
		for (int i = 0; i < msgList.size(); i++) {
			str[i] = msgList.get(i).toString();
		}
		outState.putStringArray("data", str);
	}

	// 初始化数据
	private void initMsgs() {
		Msg msg1 = new Msg("Hello guy", Msg.TYPE_RECEIVED);
		msgList.add(msg1);
		Msg msg2 = new Msg("Hello,Who is that?", Msg.TYPE_SENT);
		msgList.add(msg2);
		Msg msg3 = new Msg("This is Tom.Nice talking to you", Msg.TYPE_RECEIVED);
		msgList.add(msg3);
	}
}
