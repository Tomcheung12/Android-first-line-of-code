package com.example.anotherbroadcastreceiver;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.widget.Toast;

/**
 * ����һ���㲥������,���ڽ�����һС���е��Զ���㲥
 * @author XFHY
 *
 */
public class AnotherBroadcastReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "received in AnotherBroadcastReceiver", 0).show();
	}

}
