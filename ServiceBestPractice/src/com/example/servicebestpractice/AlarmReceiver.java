package com.example.servicebestpractice;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

/**
 * 2016��8��7��11:40:16
 * 
 * ����һ���㲥,ȥ����LongRunningService�������
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
