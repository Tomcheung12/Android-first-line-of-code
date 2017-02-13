package com.example.anotherbroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * 定义一个广播接收器,用于接收上一小节中的自定义广播
 * @author XFHY
 *
 */
public class AnotherBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "received in AnotherBroadcastReceiver", 0).show();
	}

}
