package com.example.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * �����Զ���Ĺ㲥������
 * @author XFHY
 * abortBroadcast();   //�������㲥�ض�,����Ĺ㲥���������޷����յ������㲥
 */
public class MyBroadcastReceiver extends BroadcastReceiver {
	
	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "received in MyBroadcastReceiver", 0).show();
		abortBroadcast();   //�������㲥�ض�,����Ĺ㲥���������޷����յ������㲥
	}
}
