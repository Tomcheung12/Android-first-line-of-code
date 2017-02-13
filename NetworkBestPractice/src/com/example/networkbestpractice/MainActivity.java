package com.example.networkbestpractice;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 2016年8月12日21:37:17
 * 网络编程的最佳实践   自己想的,我想用HttpURLConnection请求百度,返回数据,并显示出来
 * 用到了java的回调机制.异步消息处理机制更新UI
 * @author XFHY
 * 
 * 一个应用程序很可能会在很多地方都使用到网络功能,而发生HTTP请求的代码基本都是相同的
 * 所以,通常情况下,我们都应该将这些通用的网络操作提取到一个公共的类里面,并提供一个静态方法,当想
 * 要发起网络请求的时候只需简单地调用一下这个方法即可.
 * 
 * 当服务器成功响应的时候我们就可以再onFinish()方法里对响应数据进行处理,类似的,出现了异常,就可以在onError()方法里对异常进行处理.
 * 如此一来,我们就巧妙地利用回调机制将响应数据成功返回给调用方了.
 * 
 * 另外,需要注意的是,onFinish()和onError()方法最终还是在子线程中运行的,因此不可以在这里只想任何的UI操作,如果需要根据返回的结果来更新
 * UI,仍需要使用异步消息处理机制.
 * 
 */
public class MainActivity extends Activity implements OnClickListener{

	private Button send;
	private TextView textview;
	private final static int SHOW = 1;
	private String address = "http://www.bilibili.com/";
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SHOW:
				String response = (String)msg.obj;
				textview.setText(response);
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
        
        send = (Button)findViewById(R.id.http_request);
        textview = (TextView)findViewById(R.id.textview);
        
        send.setOnClickListener(this);
    }

    //按钮监听器
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.http_request){
			new Thread(new Runnable(){
				@Override
				public void run() {
					//自己定义的sendHttpRequest()方法接收两个参数
					HttpUtil.sendHttpRequest(address,new HttpCallbackListener() {
						
						//onFinish()方法中的参数代表着服务器返回的数据
						//在这里根据返回的内容执行具体的逻辑,我这里只是将服务器返回的数据发送到Handler中去了
						@Override
						public void onFinish(String response) {
							//异步消息处理机制
							Message message = new Message();
							message.what  = SHOW;
							message.obj = response.toString();//将服务器返回的结果存到Message对象中
							handler.sendMessage(message);  //将Message对象发生出去
						}
						
						//onError()中的参数记录着错误的详细信息
						//在这里对异常情况进行处理     我这里只是将错误信息发送到Handler中,并显示出来
						@Override
						public void onError(Exception e) {
							//异步消息处理机制
							Message message = new Message();
							message.what  = SHOW;
							message.obj = e.getMessage();  //将错误信息传递进去
							handler.sendMessage(message);  //将Message对象发生出去
						}
					});
					
					
				}
			}).start();
			
		}
	}
	

}
