package com.example.broadcasttest;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.widget.Toast;

/**
 * �ó�����δ����ʱ���ܽ��յ��㲥,�����Ҫʹ�þ�̬ע��ķ�ʽ
 * @author XFHY
 * 
 * �����ó������һ�������㲥,���յ��������ʱ�Ϳ�����onReceive()������ִ����Ӧ���߼�,�Ӷ�ʵ�ֿ��������Ĺ���
 */
public class BootCompleteReceiver extends BroadcastReceiver {

	@Override
	public void onReceive(Context context, Intent intent) {
		Toast.makeText(context, "Boot Complete", 0).show();
		Log.i("xfhy","Boot Complete");
	}

}
