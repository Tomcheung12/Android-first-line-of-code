package com.example.androidthreadtest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 2016年8月3日22:12:48
 * 
 * 尝试在子线程中更新UI
 * 
 * @author XFHY
 *
 * Android的UI也是线程不安全的,如果想要更新应用程序的UI元素,则必须在主线程中进行,否则就会出现异常.
 * 但是有时,我们必须在子线程里面去执行一些耗时任务,然后根据任务的执行结果来更新相应的UI控件.
 * 对于这种情况,Android提供了一套  异步消息处理机制  ,完美的解决了在子线程中进行UI操作的问题.
 * 
 * 异步消息处理机制:主要流程:
 *   首先需要在主线程中创建一个Handler对象,并重写handleMessage()方法,然后当子线程中需要进行UI操作时,就创建一个Message对象,并通过
 *   Handler将这条消息发送出去.之后这条消息会被添加到MessageQueue的队列中等待被处理,而Looper则会一直尝试从MessageQueue中取出待
 *   处理的消息,,最后分发回Handler的handleMessage()方法中.由于Handler是在主线程中创建的,所以此时handlerMessage()方法中的代码
 *   也会在主线程中运行,于是我们在这里就可以安心地进行UI操作了.
 *   
 *   不过,为了更加方便我们在子线程中对UI进行操作,Android还提供了另外一些好用的工具,AsyncTask就是其中.
 * 
 *
 */
public class MainActivity extends Activity implements OnClickListener{

	public static final int UPDATE_TEXT = 1;  //这里表示更新TextView这个动作
	private Button changeText = null;
	private TextView text = null;
	ProgressDialog progressDialog = new ProgressDialog(this);
	
	/*
	 * 新增一个Handler对象,并重写父类的handleMessage()方法,在这里对具体的Message进行处理,如果发现msg.what等于UPDATE_TEXT
	 * ,就将TextView显示的内容改成Nice to meet you!.
	 * */
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {  //what字段携带了数据
			case UPDATE_TEXT:
				text.setText("Nice to meet you!");
				break;
			default:
				break;
			}
		};
	};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        changeText = (Button)findViewById(R.id.change_text);
        text = (TextView)findViewById(R.id.text);
        
        changeText.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.change_text:
			//利用匿名类的方式启动子线程
			new Thread(new Runnable(){
				@Override
				public void run() {
					/*
					 * 我们并没有在子线程中直接进行更新Ui,而是创建了一个Message对象.并将它的what指定为UPDATE_TEXT,
					 * 然后调用Handler的sendMessage()方法将这个Message发送出去.很快,Handler将收到这条Message,
					 * 并在handleMessage()方法中进行处理.注意此时的handleMessage()方法是在主线程中允许,所以就可以更新
					 * UI了.接下来对Message携带的what字段的值进行判断,如果等UPDATE_TEXT就更新UI.
					 * */
					
					//text.setText("Nice to meet you!");
					Message message = new Message();
					message.what = UPDATE_TEXT;
					handler.sendMessage(message);
				}
			}).start();
			break;

		default:
			break;
		}
	}
}
