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
 * 2016��7��25��9:47:29 �㲥������ ��������ı仯
 * 
 * @author XFHY ��̬ע���������仯:
 *         1.����һ���㲥������:�½�һ����,�����̳���BroadcastReceiver,����д�����onReceive()����,
 *         ���㲥����ʱ,onReceive()������õ�ִ��.
 *         2.����,����֮ǰ,��Ҫ��intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE"
 *         ); ��ӹ㲥
 * 
 *         �ж��Ƿ�������:ͨ��getSystemService()�õ�ConnectivityManager��ʵ��,����һ��ϵͳ������,ר�����ڹ���
 *         //�������ӵ�,Ȼ���������getActiveNetworkInfo()���Եõ�NetworkInfoʵ��,
 *         ���ŵ���NetworkInfo�� //isAvailable()���������жϵ�ǰ�Ƿ�������
 * 
 *         ���������Ҫ����һЩϵͳ�Ĺؼ�����Ϣ,�����������ļ�������Ȩ�޲���,��������ֱ�ӱ���. ����Ĳ�ѯϵͳ������״̬����Ҫȥ�����ļ�������:
 *         <uses-permission
 *         android:name="android.permission.ACCESS_NETWORK_STATE"/>
 * 
 * 
 *         ��׼�㲥
 *         com.example.broadcasttest.MY_BROADCAST���Լ��Զ���Ĺ㲥,MyBroadcastReceiver
 *         ���Լ�����Ĺ㲥������,���������Լ�����Ĺ㲥
 *         
 *         ����㲥
 *         �������ȼ�
 *         
 *         ���ع㲥
 *         com.example.broadcasttest.LOCAL_BROADCAST,��Ч,����Ҫ��������й¶
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

		localBroadcastManager = LocalBroadcastManager.getInstance(this); // ��ȡʵ��

		Button btn = (Button) findViewById(R.id.btn);
		btn.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {

				Intent intent = new Intent("com.example.broadcasttest.LOCAL_BROADCAST");
				localBroadcastManager.sendBroadcast(intent);  //���ͱ��ع㲥
				
				/*
				 * ��׼�㲥 com.example.broadcasttest.MY_BROADCAST���Լ��Զ���Ĺ㲥,
				 * MyBroadcastReceiver ���Լ�����Ĺ㲥������,���������Լ�����Ĺ㲥
				 * 
				 * Intent intent = new
				 * Intent("com.example.broadcasttest.MY_BROADCAST");
				 * //sendBroadcast(intent); //���ͱ�׼�㲥
				 * sendOrderedBroadcast(intent,null); //��������㲥(�㲥�����Ⱥ�˳���,�ҿ��Ա��ض�)
				 */

			}
		});

		intentFilter = new IntentFilter();
		
		//ע�᱾�ع㲥������
		intentFilter.addAction("com.example.broadcasttest.LOCAL_BROADCAST");
		localReceiver = new LocalReceiver();
		// //����:���ع㲥���յ���,IntentFilterʵ������
		localBroadcastManager.registerReceiver(localReceiver, intentFilter); 

		// ������Ҫ���ʲô�㲥 ����㲥��������ı�ʱ,��������仯�Ĺ���
		intentFilter.addAction("android.net.conn.CONNECTIVITY_CHANGE");
		networkChangeReceiver = new NetworkChangeReceiver();

		// ��̬ע��㲥�Ľ�����
		registerReceiver(networkChangeReceiver, intentFilter);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();

		// ��̬ע��㲥�Ľ�����һ��Ҫȡ������,����������onDestroy()��ȡ��
		unregisterReceiver(networkChangeReceiver);
		
		localBroadcastManager.unregisterReceiver(localReceiver);
	}

	//���ع㲥������
	class LocalReceiver extends BroadcastReceiver {

		@Override
		public void onReceive(Context context, Intent intent) {
			Toast.makeText(context, "���ع㲥������:received loacal broadcast",
					Toast.LENGTH_SHORT).show();
		}

	}

	class NetworkChangeReceiver extends BroadcastReceiver {
		@Override
		public void onReceive(Context context, Intent intent) {

			// ͨ��getSystemService()�õ�ConnectivityManager��ʵ��,����һ��ϵͳ������,ר�����ڹ���
			// �������ӵ�,Ȼ���������getActiveNetworkInfo()���Եõ�NetworkInfoʵ��,���ŵ���NetworkInfo��
			// isAvailable()���������жϵ�ǰ�Ƿ�������
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
