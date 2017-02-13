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
 * 2016��8��6��22:32:04
 * 
 * ����Ļ����÷�
 * 
 * @author XFHY
 * 
 *         ��������:��Ҫ����Intent��ʵ��
 *         
 *         startService():��������
 *         stopService():ֹͣ����
 *         bindService():�󶨷���
 *         unbind_service():�����
 *         
 *         ע��:�κ�һ������������Ӧ�ó���Χ�ڶ���ͨ�õ�,��MyService�������Ժ�MainActivity��,�����Ժ��κ�һ�������Ļ���а�
 *         �����ڰ���ɺ����Ƕ����Ի�ȡ����ͬ��DownloadBinderʵ��
 * 
 */
public class MainActivity extends Activity implements OnClickListener {

	private Button startService; // ��������
	private Button stopService; // ֹͣ����
	private Button bind_service; // �󶨷���
	private Button unbind_service;// ȡ���󶨷���
	private MyService.DownloadBinder downloadBinder;  //������������Զ����ڲ���
	private ServiceConnection connection = new ServiceConnection() {  //������һ��ServiceConnection��������

		//������ɹ��󶨵�ʱ�����
		@Override
		public void onServiceConnected(ComponentName name, IBinder service) {
			downloadBinder = (MyService.DownloadBinder)service;  //ͨ������ת��,�õ�DownloadBinderʵ��
			downloadBinder.startDownload();  //�󶨷����,�Ϳ��Ը��ݻ�ĳ���������DownloadBinder���е�public����
			downloadBinder.getProgress();
		}

		//���������󶨵�ʱ�����
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
			startService(startIntent);// ��������
			break;
		case R.id.stop_service:
			Intent stopIntent = new Intent(this, MyService.class);
			stopService(stopIntent);// ֹͣ����
			break;
		case R.id.bind_service:
			Intent bindIntent = new Intent(this,MyService.class);
			//����:Intent����,ServiceConnection��ʵ��,��־λ(���ﴫ��ı�־λ��ʾ�ڻ�ͷ�����а󶨺��Զ���������,���
			//ʹ��MyService�е�onCreate()�����õ�ִ��,��onStartCommand()��������ִ��)
			bindService(bindIntent,connection,BIND_AUTO_CREATE);  //�󶨷���
			break;
		case R.id.unbind_service:
			unbindService(connection); //������
			break;
		case R.id.start_intent_service:  //����IntentService����
			//��ӡ���̵߳�id
			Log.d("xfhy","MainActivity Thread id is "+Thread.currentThread().getId());
			Intent intentService = new Intent(this,MyIntentService.class);
			startService(intentService);
			break;
		default:
			break;
		}
	}
}
