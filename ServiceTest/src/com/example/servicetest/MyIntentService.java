package com.example.servicetest;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * 2016年8月7日9:33:11
 * 
 * 简单的创建一个异步的,会自动停止的服务
 * 
 * @author XFHY
 *
 * 为了可以写简单的创建一个异步的,会自动停止的服务,Android专门提供了一个IntentService类,这个类就很好的解决了
 * 忘记开启线程,或者忘记stopSelf()方法(这个方法是用来停止服务的).
 * 
 * 这里必须要提供一个无参的构造函数,并且必须在其内部调用父类的有参构造函数.
 * 然后要在子类中去实现onHandleIntent()这个抽象方法,在这个方法中处理一些具体的逻辑.这个方法是在子线程中运行,不用担心ANR问题.
 * 
 * 根据IntentService的特性,这个服务在运行结束后会自动停止的.
 * 
 */
public class MyIntentService extends IntentService {

	//这里必须要提供一个无参的构造函数,并且必须在其内部调用父类的有参构造函数
	public MyIntentService() {
		super("MyIntentService");  //调用父类的有参构造函数
	}

	//然后要在子类中去实现onHandleIntent()这个抽象方法,在这个方法中处理一些具体的逻辑.这个方法是在子线程中运行,不用担心ANR问题.
	@Override
	protected void onHandleIntent(Intent intent) {
		//打印当前线程的id
		Log.d("xfhy","IntentService Thread id is "+Thread.currentThread().getId());
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d("xfhy","onDestroy executed");
	}

}
