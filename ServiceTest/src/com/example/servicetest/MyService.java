package com.example.servicetest;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * 2016年8月6日22:20:48
 * 
 * 这里是服务的类,服务的基本使用
 * 
 * 服务的更多技巧:创建前台服务
 * 
 * @author XFHY
 * 
 *         重写了onCreate(),onStartCommand()和onDestroy()这3个方法,它们是每个服务中最常用到的3个方法,
 *         其中onCreate()方法会在服务创建
 *         的时候调用,onStartCommand()方法会在每次服务启动的时候调用,onDestroy()方法会在服务销毁的时候调用.
 * 
 *         通常情况下,如果我们希望服务一旦启动就理科去执行某个动作,就可以将逻辑写在onStartCommand()方法里.而当服务销毁时,
 *         我们又应该在 onDestroy()方法中去回收那些不再使用的资源.
 * 
 *         另外,需要注意,每一个服务都需要在AndroidManifest.xml文件中进行注册才能生效,这是Android四大组件共有的特点.
 * 
 *         在MyService的任何一个位置调用stopSelf()方法就能让这个服务停止下来了
 * 
 *         当调用了startService()方法后,又去调用stopService()方法,这种情况下要同时调用stopService()
 *         和unbindService()方法,onDestroy() 方法才会执行
 * 
 *         前台服务和普通服务的区别在于,它会一直有一个正在运行的图标在系统的状态栏显示,下拉状态栏后可以看到更加详细的信息,
 *         非常类似于通知效果.
 *         
 *         服务中运行线程,则标准格式是在方法中写入如下代码
 *            new Thread(new Runnable() {
				@Override
				public void run() {
					//在这里面写  处理具体的逻辑
					
					stopSelf();   //停止服务(如果想要一个服务执行完任务自动停止的功能,就写入这句代码)
				}
			}).start();
 * 
 */
public class MyService extends Service {

	private DownloadBinder mBinder = new DownloadBinder();

	// 可以让活动与服务的关系更紧密 活动指挥服务就通过这个
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	// 自定义的内部类
	class DownloadBinder extends Binder {
		// 开始下载
		public void startDownload() {
			Log.d("xfhy", "startDownload executed");
		}

		// 查看下载进度
		public int getProgress() {
			Log.d("xfhy", "getProgress executed");
			return 0;
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("xfhy", "onCreate executed");

		// 下面是为了前台服务,而写的代码 创建通知
		// 创建Notification对象,参数:图标,内容,被创建的时间
		Notification notification = new Notification(R.drawable.ic_launcher,
				"Notification comes", System.currentTimeMillis());
		Intent notificationIntent = new Intent(this, MainActivity.class);
		// 这个也是拿来启动活动的,但是这个更倾向于在某个时机去执行某个动作.参数:context,第二个一般传入0,第三个参数是Intent对象,第四个是
		// 用于确定PendingIntent的行为,有4种值.
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, 0);
		// 给通知的布局进行设定,参数:context,标题内容(下拉状态栏就可以看见),通知正文,PendingIntent对象
		notification.setLatestEventInfo(this, "This is title",
				"This is content", pendingIntent);
		// 将通知显示出来,参数:通知的id,构建出的Notification对象
		// 调用startForeground()方法后就会让MyService变成一个前台服务
		// 并在系统的状态栏显示出来.
		startForeground(1, notification);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("xfhy", "onStartCommand executed");

		/*new Thread(new Runnable() {
			@Override
			public void run() {
				//在这里面写  处理具体的逻辑
				
				stopSelf();   //停止服务(如果想要一个服务执行完任务自动停止的功能,就写入这句代码)
			}
		}).start();*/

		return super.onStartCommand(intent, flags, startId);
	}

	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d("xfhy", "onDestroy executed");
	}
}
