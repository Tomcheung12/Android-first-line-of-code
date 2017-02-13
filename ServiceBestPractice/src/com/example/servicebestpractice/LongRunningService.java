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
 * 2016年8月7日10:59:41
 * 
 * 可以长期在后台执行定时任务的服务
 * 
 * @author XFHY
 * 
 * 当真正需要去执行某个定时任务的时候,只需要将打印日志换成具体的任务逻辑就行了.
 * 
 */
public class LongRunningService extends Service {

	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	//在这里面开启了一个子线程,在里面可以执行具体的逻辑操作了.这里简单起见,只是打印了一下时间.
	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		new Thread(new Runnable() {
			@Override
			public void run() {
				Log.d("xfhy","execute at "+new Date().toString());
			}
		}).start();

		//获得AlarmManager实例,通过getSystemService()方法获得,这里要传入一个参数ALARM_SERVICE
		AlarmManager manager = (AlarmManager)getSystemService(ALARM_SERVICE);
		int time = 10*1000;   //这是一个10秒的毫秒数
		long triggerAtTime = SystemClock.elapsedRealtime()+time;  //执行时间是开机到现在的时间+自定设定的时间
		Intent i = new Intent(this,AlarmReceiver.class);  //这里启动的是隔壁的AlarmReceiver这个广播
		//每隔10秒就启动一次广播,广播立刻又启动这个服务    保证LongRunningService每10秒就会启动一次
		//参数:1.Context,2.一般用不到,传入0即可,3.Intent对象,通过这个对象可以构建出PendingIntent的"意图".4.用于确定PendingIntent的行为
		PendingIntent pi = PendingIntent.getBroadcast(this, 0, i, 0);
		
		/*
		 * 这个AlarmManager的set()方法就可以设置一个定时任务了,参数:整型参数,定时任务触发的时间,PendingIntent对象
		 * 这里的整型参数是指:ELAPSED_REALTIME,ELAPSED_REALTIME_WAKEUP,RTC,RTC_WAKEUP
		 * ELAPSED_REALTIME:表示让定时任务从系统开机开始算起,但不会唤醒CPU
		 * ELAPSED_REALTIME_WAKEUP:表示让定时任务从系统开机开始算起,但会唤醒CPU
		 * RTC:表示让任务的触发时间从1970年1月1日0点开始算起,但不会唤醒CPU
		 * RTC_WAKEUP:表示让任务的触发时间从1970年1月1日0点开始算起,但会唤醒CPU
		 * 
		 * 使用SystemClock.elapsedRealtime()方法可以获取到系统开机至今所经历时间的毫秒数
		 * 使用System.currentTimeMillis()可以获取1970年1月1日0点至今所经历时间的毫秒数
		 * 
		 * 第三个参数一般会调用getBroadcast()方法来获取一个能够执行广播的PendingIntent.
		 */
		manager.set(AlarmManager.ELAPSED_REALTIME_WAKEUP, triggerAtTime, pi);
		
		return super.onStartCommand(intent, flags, startId);
	}
}
