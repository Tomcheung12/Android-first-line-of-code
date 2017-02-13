package com.example.servicebestpractice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 2016年8月7日11:40:16
 * 
 * 这是一个广播,去启动LongRunningService这个服务
 * 
 * @author XFHY
 * 
 * 
 */
public class AlarmReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Intent i = new Intent(context,LongRunningService.class);
		context.startService(i);
	}

}
