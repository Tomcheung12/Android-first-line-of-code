package com.example.notificationtest;

import android.app.Activity;
import android.app.NotificationManager;
import android.os.Bundle;

/**
 * 2016年8月2日10:15:27
 * 
 * @author XFHY
 * 
 *  这是跳转的第二个页面
 * 
 */
public class NotificationActivity extends Activity {
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.notification);
		
		NotificationManager manager = (NotificationManager)
			      getSystemService(NOTIFICATION_SERVICE);
		manager.cancel(1);   //取消通知   1是id,之前设置了通知的id是1
	}
}
