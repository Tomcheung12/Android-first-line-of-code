package com.example.servicetest;

import android.app.Activity;
import android.content.ComponentName;
import android.content.Intent;
import android.content.ServiceConnection;
import android.os.Bundle;
import android.os.IBinder;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 2016年8月6日22:32:04
 * 
 * 服务的基本用法
 * 
 * @author XFHY
 * 
 *         启动服务:主要借助Intent来实现
 *         
 *         startService():启动服务
 *         stopService():停止服务
 *         bindService():绑定服务
 *         unbind_service():解除绑定
 *         
 *         注意:任何一个服务在整个应用程序范围内都是通用的,即MyService不仅可以和MainActivity绑定,还可以和任何一个其他的活动进行绑定
 *         而且在绑定完成后它们都可以获取到相同的DownloadBinder实例
 * 
 */
public class MainActivity extends Activity implements OnClickListener {

	private Button startService; // 启动服务
	private Button stopService; // 停止服务
	private Button bind_service; // 绑定服务
	private Button unbind_service;// 取消绑定服务
	private MyService.DownloadBinder downloadBinder;  //服务类里面的自定的内部类
	private ServiceConnection connection = new ServiceConnection() {  //创建了一个ServiceConnection的匿名类

		//活动与服务成功绑定的时候调用
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			downloadBinder = (MyService.DownloadBinder)service;  //通过向下转型,得到DownloadBinder实例
			downloadBinder.startDownload();  //绑定服务后,就可以根据活动的场景来调用DownloadBinder所有的public方法
			downloadBinder.getProgress();
		}

		//活动与服务解除绑定的时候调用
		@Override
		public void onServiceDisconnected(ComponentName name) {
		}
	};
	private Button startIntentservice;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		startService = (Button) findViewById(R.id.start_service);
		stopService = (Button) findViewById(R.id.stop_service);
		bind_service = (Button) findViewById(R.id.bind_service);
		unbind_service = (Button) findViewById(R.id.unbind_service);
		startIntentservice = (Button) findViewById(R.id.start_intent_service);

		startService.setOnClickListener(this);
		stopService.setOnClickListener(this);
		bind_service.setOnClickListener(this);
		unbind_service.setOnClickListener(this);
		startIntentservice.setOnClickListener(this);
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.start_service:
			Intent startIntent = new Intent(this, MyService.class);
			startService(startIntent);// 启动服务
			break;
		case R.id.stop_service:
			Intent stopIntent = new Intent(this, MyService.class);
			stopService(stopIntent);// 停止服务
			break;
		case R.id.bind_service:
			Intent bindIntent = new Intent(this,MyService.class);
			//参数:Intent对象,ServiceConnection的实例,标志位(这里传入的标志位表示在活动和服务进行绑定后自动创建服务,这会
			//使得MyService中的onCreate()方法得到执行,但onStartCommand()方法不会执行)
			bindService(bindIntent,connection,BIND_AUTO_CREATE);  //绑定服务
			break;
		case R.id.unbind_service:
			unbindService(connection); //解绑服务
			break;
		case R.id.start_intent_service:  //启动IntentService对象
			//打印主线程的id
			Log.d("xfhy","MainActivity Thread id is "+Thread.currentThread().getId());
			Intent intentService = new Intent(this,MyIntentService.class);
			startService(intentService);
			break;
		default:
			break;
		}
	}
}
