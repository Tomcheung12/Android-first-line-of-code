package com.example.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * 让程序在未启动时就能接收到广播,这就需要使用静态注册的方式
 * @author XFHY
 * 
 * 这里让程序接收一条开机广播,当收到这条光比时就可以再onReceive()方法里执行相应的逻辑,从而实现开机启动的功能
 */
public class BootCompleteReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "Boot Complete", 0).show();
		Log.i("xfhy","Boot Complete");
	}

}
