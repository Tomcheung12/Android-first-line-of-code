package com.example.broadcasttest;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

/**
 * 2016年7月25日9:47:29 广播接收器 监听网络的变化
 * 
 * @author XFHY 动态注册监听网络变化:
 *         1.创建一个广播接收器:新建一个类,让它继承自BroadcastReceiver,并重写父类的onReceive()方法,
 *         当广播到来时,onReceive()方法会得到执行.
 *         2.不过,在这之前,需要用intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE"
 *         ); 添加广播
 * 
 *         判断是否有网络:通过getSystemService()得到ConnectivityManager的实例,这是一个系统服务类,专门用于管理
 *         //网络连接的,然后调用它的getActiveNetworkInfo()可以得到NetworkInfo实例,
 *         接着调用NetworkInfo的 //isAvailable()方法即可判断当前是否有网络
 * 
 *         如果程序需要访问一些系统的关键性信息,必须在配置文件中声明权限才行,否则程序会直接崩溃. 这里的查询系统的网络状态就需要去配置文件里声明:
 *         <uses-permission
 *         android:name="android.permission.ACCESS_NETWORK_STATE"/>
 * 
 * 
 *         标准广播
 *         com.example.broadcasttest.MY_BROADCAST是自己自定义的广播,MyBroadcastReceiver
 *         是自己定义的广播接收器,用来接收自己定义的广播
 *         
 *         有序广播
 *         设置优先级
 *         
 *         本地广播
 *         com.example.broadcasttest.LOCAL_BROADCAST,高效,不需要担心数据泄露
 *  
 */
public class MainActivity extends Activity {

	private IntentFilter intentFilter;
	private NetworkChangeReceiver networkChangeReceiver;

	private LocalReceiver localReceiver;
	private LocalBroadcastManager localBroadcastManager;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		localBroadcastManager = LocalBroadcastManager.getInstance(this); // 获取实例

		Button btn = (Button) findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent("com.example.broadcasttest.LOCAL_BROADCAST");
				localBroadcastManager.sendBroadcast(intent);  //发送本地广播
				
				/*
				 * 标准广播 com.example.broadcasttest.MY_BROADCAST是自己自定义的广播,
				 * MyBroadcastReceiver 是自己定义的广播接收器,用来接收自己定义的广播
				 * 
				 * Intent intent = new
				 * Intent("com.example.broadcasttest.MY_BROADCAST");
				 * //sendBroadcast(intent); //发送标准广播
				 * sendOrderedBroadcast(intent,null); //发送有序广播(广播是有先后顺序的,且可以被截断)
				 */

			}
		});

		intentFilter = new IntentFilter();
		
		//注册本地广播监听器
		intentFilter.addAction("com.example.broadcasttest.LOCAL_BROADCAST");
		localReceiver = new LocalReceiver();
		// //参数:本地广播接收的类,IntentFilter实例对象
		localBroadcastManager.registerReceiver(localReceiver, intentFilter); 

		// 现在需要添加什么广播 这个广播就是网络改变时,监听网络变化的功能
		intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		networkChangeReceiver = new NetworkChangeReceiver();

		// 动态注册广播的接收器
		registerReceiver(networkChangeReceiver, intentFilter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// 动态注册广播的接收器一定要取消才行,这里我们在onDestroy()中取消
		unregisterReceiver(networkChangeReceiver);
		
		localBroadcastManager.unregisterReceiver(localReceiver);
	}

	//本地广播接收器
	class LocalReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			Toast.makeText(context, "本地广播接收器:received loacal broadcast",
					Toast.LENGTH_SHORT).show();
		}

	}

	class NetworkChangeReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {

			// 通过getSystemService()得到ConnectivityManager的实例,这是一个系统服务类,专门用于管理
			// 网络连接的,然后调用它的getActiveNetworkInfo()可以得到NetworkInfo实例,接着调用NetworkInfo的
			// isAvailable()方法即可判断当前是否有网络
			ConnectivityManager connectivityManager = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

			NetworkInfo networkInfo = connectivityManager
					.getActiveNetworkInfo();

			if (networkInfo != null && networkInfo.isAvailable()) {
				Toast.makeText(context, "network is available", 0).show();
				Log.i("xfhy", "network is available");
			} else {
				Toast.makeText(context, "network is unavailable", 0).show();
				Log.i("xfhy", "network is unavailable");
			}
		}
	}

}
