package com.example.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * 这是自定义的广播接收器
 * @author XFHY
 * abortBroadcast();   //将这条广播截断,后面的广播接收器将无法接收到这条广播
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "received in MyBroadcastReceiver", 0).show();
		abortBroadcast();   //将这条广播截断,后面的广播接收器将无法接收到这条广播
	}
}
