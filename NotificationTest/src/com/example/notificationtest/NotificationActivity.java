package com.example.notificationtest;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;

/**
 * 2016��8��2��10:15:27
 * 
 * @author XFHY
 * 
 *  ������ת�ĵڶ���ҳ��
 * 
 */
public class NotificationActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification);
		
		NotificationManager manager = (NotificationManager)
			      getSystemService(NOTIFICATION_SERVICE);
		manager.cancel(1);   //ȡ��֪ͨ   1��id,֮ǰ������֪ͨ��id��1
	}
}
