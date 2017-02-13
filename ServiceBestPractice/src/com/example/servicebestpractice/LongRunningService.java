package com.example.servicebestpractice;

import java.util.Date;

import android.app.AlarmManager;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.os.SystemClock;
import android.util.Log;

/**
 * 2016��8��7��10:59:41
 * 
 * ���Գ����ں�ִ̨�ж�ʱ����ķ���
 * 
 * @author XFHY
 * 
 * ��������Ҫȥִ��ĳ����ʱ�����ʱ��,ֻ��Ҫ����ӡ��־���ɾ���������߼�������.
 * 
 */
public class LongRunningService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	//�������濪����һ�����߳�,���������ִ�о�����߼�������.��������,ֻ�Ǵ�ӡ��һ��ʱ��.
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Log.d("xfhy","execute at "+new Date().toString());
			}
		}).start();

		//���AlarmManagerʵ��,ͨ��getSystemService()�������,����Ҫ����һ������ALARM_SERVICE
		AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
		int time = 10*1000;   //����һ��10��ĺ�����
		long triggerAtTime = SystemClock.elapsedRealtime()+time;  //ִ��ʱ���ǿ��������ڵ�ʱ��+�Զ��趨��ʱ��
		Intent i = new Intent(this,AlarmReceiver.class);  //�����������Ǹ��ڵ�AlarmReceiver����㲥
		//ÿ��10�������һ�ι㲥,�㲥�����������������    ��֤LongRunningServiceÿ10��ͻ�����һ��
		//����:1.Context,2.һ���ò���,����0����,3.Intent����,ͨ�����������Թ�����PendingIntent��"��ͼ".4.����ȷ��PendingIntent����Ϊ
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
		
		/*
		 * ���AlarmManager��set()�����Ϳ�������һ����ʱ������,����:���Ͳ���,��ʱ���񴥷���ʱ��,PendingIntent����
		 * ��������Ͳ�����ָ:ELAPSED_REALTIME,ELAPSED_REALTIME_WAKEUP,RTC,RTC_WAKEUP
		 * ELAPSED_REALTIME:��ʾ�ö�ʱ�����ϵͳ������ʼ����,�����ỽ��CPU
		 * ELAPSED_REALTIME_WAKEUP:��ʾ�ö�ʱ�����ϵͳ������ʼ����,���ỽ��CPU
		 * RTC:��ʾ������Ĵ���ʱ���1970��1��1��0�㿪ʼ����,�����ỽ��CPU
		 * RTC_WAKEUP:��ʾ������Ĵ���ʱ���1970��1��1��0�㿪ʼ����,���ỽ��CPU
		 * 
		 * ʹ��SystemClock.elapsedRealtime()�������Ի�ȡ��ϵͳ��������������ʱ��ĺ�����
		 * ʹ��System.currentTimeMillis()���Ի�ȡ1970��1��1��0������������ʱ��ĺ�����
		 * 
		 * ����������һ������getBroadcast()��������ȡһ���ܹ�ִ�й㲥��PendingIntent.
		 */
		manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
		
		return super.onStartCommand(intent, flags, startId);
	}
}
