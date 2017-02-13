package com.example.notificationtest;

import java.io.File;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 2016年8月2日9:14:46
 * 
 * 使用通知,通知的基本用法
 * 
 * @author XFHY
 *
	 * 这里有几点地方需要注意：
	1.如果该通知只是起到 “通知”的作用，不希望用户点击后有相应的跳转，那么，
	intent,pendingIntent这几行代码可以不写，可以创建延时操作，
	当通知被成功 notify 后，一段时间后调用manager.cancel(notificationID),将通知清除，
	此时builder.setAutoCancel()方法不写也可以。  
	2.如果通知栏下拉后，希望用户点击并有相应的跳转事件。那么，要注意跳转后，通知是否有必要继续存在。
	如果点击后通知消失，两种方法
	A 设置setAutoCancel 参数设置为 true，默认是false，
	B 在intent事件中的如本例中的MainActivity的onCreate()方法调用 manager.cancel(notificationID)取消该通知，
	此ID要和创建通知的ID一致，否则通知不消失。如果点击不消失，将setAutoCancel 参数设置为 false即可。
 *
 */
public class MainActivity extends Activity implements OnClickListener{

	private Button vertical = null;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        vertical = (Button)findViewById(R.id.send_notice);
        vertical.setOnClickListener(this);
    }
	@SuppressLint("NewApi") @SuppressWarnings("deprecation")
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.send_notice:
			   /*----------这种方式已经过时-------------*/
			/*//NotificationManager对象,对通知进行管理
			NotificationManager manager = (NotificationManager)
			      getSystemService(NOTIFICATION_SERVICE);
			
			//通知图标,通知内容,通知时间   构造一个Notification对象
			Notification notification = new Notification(R.drawable.icon,
					"This is ticker text",System.currentTimeMillis());
			
			//设置通知布局,通知标题,通知内容
			notification.setLatestEventInfo(this, "This is content title",
					"This is content text", null);
			
			//id,每个通知的id不同    这里是显示通知
			manager.notify(1,notification);*/
			
			/*--------------------------这是最新的方式(SDK 23)----------------------------*/
			NotificationManager manager = (NotificationManager)
		      getSystemService(NOTIFICATION_SERVICE);
			
			//通过Builder来构建Notification对象
			Notification.Builder builder = new Notification.Builder(this);
			builder.setContentInfo("10086");   //补充内容
			builder.setContentText("您的话费已欠费,请及时续交话费,谢谢......");  //主内容区
			builder.setContentTitle("你有新的短消息");    //通知标题
			builder.setSmallIcon(R.drawable.icon);   //图标
			builder.setTicker("新消息");
			builder.setAutoCancel(false);     //自动取消
			builder.setWhen(System.currentTimeMillis());   //设置当前时间
			
			//点击通知就可以跳转到第二个页面了
			Intent intent = new Intent(this, NotificationActivity.class);   //设置跳转的活动
			PendingIntent pendingIntent = PendingIntent.
					getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
			builder.setContentIntent(pendingIntent);   //设置跳转
			Notification notification = builder.build();  //创建Notification对象
			
			/*---------------设置通知栏声音-------------------*/
			Uri soundUri = Uri.fromFile(new File("/system/media/audio/ringtones/Basic_tone"));
			notification.sound = soundUri;
			
			/*---------------设置通知时震动  振动需要设置权限-------------------*/
			long[] vibrates = {0,1000,1000,1000};  
			//静,振,静,振   这里表示先静止0秒,立即振动1秒,再静止1秒,再振动1秒,单位是毫秒
			notification.vibrate = vibrates;
			
			/*---------------设置LED灯通知-------------------*/
			notification.ledARGB = Color.RED;   //设置颜色
			notification.ledOnMS = 1000;          //设置灯亮起的时长
			notification.ledOffMS = 1000;         //设置灯暗去的时长
			notification.flags = Notification.FLAG_SHOW_LIGHTS; //用于指定通知的一些行为
			
			//你也可以不必设置得这么繁琐,可以直接使用默认的通知效果  它会根据手机的环境来决定播放什么铃声,以及如何振动
			//notification.defaults = Notification.DEFAULT_ALL;
			
			manager.notify(1, notification);   //显示通知
			break;

		default:
			break;
		}
	}
}
