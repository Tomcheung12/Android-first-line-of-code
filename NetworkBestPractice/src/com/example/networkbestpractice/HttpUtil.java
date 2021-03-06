package com.example.networkbestpractice;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;


import android.content.Context;
import android.net.ConnectivityManager;
import android.os.Looper;
import android.util.Log;
import android.widget.Toast;

/**
 * 2016年8月12日21:52:20
 * 专门用于网络操作的类
 * @author XFHY
 * 
 * 一个应用程序很可能会在很多地方都使用到网络功能,而发生HTTP请求的代码基本都是相同的
 * 所以,通常情况下,我们都应该将这些通用的网络操作提取到一个公共的类里面,并提供一个静态方法,当想
 * 要发起网络请求的时候只需简单地调用一下这个方法即可.
 * 
 * 在获取服务器响应的数据后我们就可以对它进行解析和处理了.但是需要注意,网络请求通常是属于耗时操作,而sendHttpRequest()方法的内部
 * 并没有开始线程的话,这样就有可能导致在调用sendHttpRequest()方法的时候使得主线程被阻塞住.
 * 
 * 于是需要用到子线程,子线程去执行具体的网络操作,注意子线程是无法用return语句来返回数据的.因此这里我们将服务器响应的数据传入了
 * 自己写的HttpCallbackListener的onFinish()方法当中,如果出现了异常就传入onError()方法中.
 * 并使用java的回调机制就可以了.
 * 
 * 当在线程中使用Toast的时候,报Can't create handler inside thread that has not called Looper.prepare()错误时,
 * 需要在Toast前后加上Looper.prepare();和Looper.loop();
 * 
 */
public class HttpUtil {
	public static void sendHttpRequest(final String address,final HttpCallbackListener listener){
		
		//首先判断一下当前网络是否可以上网  不可以的话,直接不用执行下面的代码了
		//if(!isNetworkAvailable()){
		if(!ping()){
			Looper.prepare(); //需要调用Looper.prepare()来给线程创建一个消息循环
			Toast.makeText(MyApplication.getContext(), "当前网络不可用", Toast.LENGTH_SHORT).show();
			Looper.loop();   //让Looper开始工作，从消息队列里取消息，处理消息。 
			return ;
		}
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				HttpURLConnection connection = null;
				try {
					URL url = new URL(address);
					connection = (HttpURLConnection)url.openConnection();
					connection.setRequestMethod("GET");  //设置 URL 请求的方法
					connection.setConnectTimeout(8000);  //设置一个连接指定的超时值（以毫秒为单位）
					connection.setReadTimeout(8000);     //将读超时设置为指定的超时值，以毫秒为单位
					connection.setDoInput(true);  //URL 连接可用于输入和/或输出。如果打算使用 URL 连接进行输入，则将 DoInput 标志设置为 true；
					connection.setDoOutput(true);
					
					//获取连接返回的输入流
					InputStream in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line;
					while((line=reader.readLine())!=null){
						response.append(line);
					}
					//return response.toString();
					
					//将服务器响应的数据传入了HttpCallbackListener的onFinish()方法当中,这样调用这个方法的类就可以获取到数据了
					if(listener!=null){
						//回调onFinish()方法
						listener.onFinish(response.toString());
					}
				} catch (Exception e) {
					Log.d("xfhy",e.getMessage());
					
					//出现了异常就传入onError()方法中.
					if(listener!=null){
						//回调onError()方法
						listener.onError(e);
					}
				} finally {    //最后记得关闭连接
					if(connection!=null){
						connection.disconnect();
					}
				}
			}
		}).start();
	}

	/**
	 * 判断网络是否连接   
	 * 简单地判断网络是否连接,对于一些特殊情况不能判断,不如 连接上移动网络但无法上网,连接上wifi但无法上网等.
	 * @return 有连接则返回true,否则返回false(无可用网络)
	 */
	public static boolean isNetworkAvailable() {
		
		//这里需要加入权限
		//<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
		//得到网络连接
		ConnectivityManager manager = (ConnectivityManager)
				MyApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		
		//去判断网络是否连接
		if(manager.getActiveNetworkInfo() != null){
			return manager.getActiveNetworkInfo().isAvailable();  //表明网络连接是否可能
		}
		return false;
	}
	
	/**
	 * 判断是否可连接外网（普通方法不能判断外网的网络是否连接，比如连接上局域网）
	 * 有时候我们连接上一个没有外网连接的WiFi或者需要输入账号和密码才能链接外网的网络，
	 * 就会出现虽然网络可用，但是外网却不可以访问。针对这种情况，一般的解决办法就是ping一个外网，如果能ping通就说明可以真正上网，
	 * @return  连接得上则返回true,否则返回false(网络连接失败)
	 */
	public static final boolean ping() {
		String result = null;
		
		try {
			String ip = "www.baidu.com";   //ping的地址
			Process p = Runtime.getRuntime().exec("ping -c 3 -w 100 "+ip);  //ping网址3次
			
			/*//读取ping的内容,可以不加
			InputStream input = p.getInputStream();
			//字节流转换成字符流
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			StringBuffer stringBuffer = new StringBuffer();
			String content = "";
			while( (content = in.readLine()) != null ){  //按行读取
				stringBuffer.append(content);
			}
			
			Log.d("xfhy", "result content : " + stringBuffer.toString()); */
			 
			//ping的状态
			int status = p.waitFor();   //the exit value of the native process being waited on.  
			//我觉得是判断线程是否阻塞
			if( status == 0 ){
				result = "success";
				return true;
			} else {
				result = "failed";
			}
		} catch (IOException e) { 
            result = "IOException"; 
	    } catch (InterruptedException e) { 
	            result = "InterruptedException"; 
	    } finally { 
	            Log.d("xfhy", "result = " + result); 
	    } 
    return false;
	}
}
