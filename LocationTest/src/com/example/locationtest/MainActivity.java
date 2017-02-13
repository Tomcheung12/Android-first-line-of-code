package com.example.locationtest;

import java.util.List;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

import android.app.Activity;
import android.content.Context;
import android.location.Location;
import android.location.LocationListener;
import android.location.LocationManager;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 2016年8月13日8:18:31
 * 
 * 获取当前位置经纬度
 * 
 * @author XFHY
 * 
 *         请到开阔的地方,才能定位
 * 
 *      获取当前经纬度的步骤:
 *         1.在onCreate()方法里实例化LocationListener对象,并重写里面的方法,onLocationChanged
 *           ( )表示位置变化时调用 (在里面显示最新的位置) 
 *         2.获取LocationManager实例,通过getSystemService()方法
 *         3.获取所有可用的位置提供器 如果有时候你想定位的经度尽量高一些,但又不确定GPS定位的功能是否已经启用,这个时候就可以先判断一下
 *            有哪些位置提供器可用 
 *         4.将选择好的位置提供器传入到getLastKnownLocation()方法中.就可以得到一个Location对象
 *           这个Location对象中包含了经度,纬度,海拔等一系列的位置信息,然后从中取出我们所关心的那部分数据即可
 *         5.当设备位置发生改变时,那么我们通过requestLocationUpdates()方法获得最新的位置信息.
 *           参数:位置提供器类型,监听位置变化时间间隔,监听位置变化的距离间隔,LocationListener
 *         6.在活动暂停(onPause)或者活动销毁
 *            (onDestroy)时,记得调用removeUpdates()方法来将位置监听器移除,以保证不会继续消耗手机的电量
 *         7.需要权限<uses-permission
 *            android:name="android.permission.ACCESS_FINE_LOCATION"/>
 *      
 *       加入反向地理编码功能(将经纬度转化为看得懂的位置信息):
 *         1.发送一个HTTP请求给谷歌的服务器
 *         2.然后再将返回的JSON数据进行解析,发送HTTP请求的方式我们准备使用HttpClient
 *         3.解析JSON数据的方式使用JSONObject.
 *       
 *       
 */
public class MainActivity extends Activity {

	private TextView positionTextView;
	private LocationManager locationManager;
	private String provider;
	LocationListener locationListener;
	
	public static final int SHOW_LOCATION = 0;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		positionTextView = (TextView) findViewById(R.id.position_text_view);

		// 位置监听器 实例化
		locationListener = new LocationListener() {

			// 当位置改变时
			@Override
			public void onLocationChanged(Location location) {
				Toast.makeText(MainActivity.this, "位置已经变化啦", Toast.LENGTH_SHORT)
						.show();
				// 自己写的方法,显示当前位置
				showLocation(location);
			}

			@Override
			public void onStatusChanged(String provider, int status,
					Bundle extras) {

			}

			@Override
			public void onProviderEnabled(String provider) {

			}

			@Override
			public void onProviderDisabled(String provider) {

			}
		};

		// 获取LocationManager实例
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// 获取所有可用的位置提供器 如果有时候你想定位的经度尽量高一些,但又不确定GPS定位的功能是否已经启用,这个时候就可以先判断一下
		// 有哪些位置提供器可用
		List<String> providerList = locationManager.getProviders(true); // 返回所有可用的位置提供器
		if (providerList.contains(LocationManager.GPS_PROVIDER)) {
			provider = LocationManager.GPS_PROVIDER;
			Toast.makeText(MainActivity.this, "GPS_PROVIDER",
					Toast.LENGTH_SHORT).show();
		} else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
			provider = LocationManager.NETWORK_PROVIDER;
			Toast.makeText(MainActivity.this, "NETWORK_PROVIDER",
					Toast.LENGTH_SHORT).show();
		} else {
			// 当没有位置提供器时,弹出Toast提示用户
			Toast.makeText(this, "No location provider to use",
					Toast.LENGTH_SHORT).show();
			return;
		}
		// 将选择好的位置提供器传入到getLastKnownLocation()方法中.就可以得到一个Location对象
		// 这个Location对象中包含了经度,纬度,海拔等一系列的位置信息,然后从中取出我们所关心的那部分数据即可
		Location location = locationManager.getLastKnownLocation(provider);

		if (location != null) {
			// 显示当前设备的位置信息
			showLocation(location);
		} else {
			positionTextView.setText("位置信息为空");
		}

		/*
		 * 当设备位置发生改变时,那么我们通过requestLocationUpdates()方法获得最新的位置信息.
		 * 参数:位置提供器类型,监听位置变化时间间隔,监听位置变化的距离间隔,LocationListener
		 */
		locationManager.requestLocationUpdates(provider, 5000, 1,
				locationListener);

	}

	// 显示当前设备位置信息
	private void showLocation(final Location location) {
		Toast.makeText(this, "开始显示位置啦", Toast.LENGTH_SHORT).show();
		// 显示经度和纬度
		String currentPosition = "latitude is " + location.getLatitude() + "\n"
				+ "longitude is " + location.getLongitude();
		positionTextView.setText(currentPosition);
		
		//需要发起网络请求,所以需要开启子线程
		new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					//组装反向地理编码的接口地址
					StringBuilder url = new StringBuilder();
					url.append("http://maps.googleapis.com/maps/api/geocode/json?latlng=");
					url.append(location.getLatitude()).append(",");
					url.append(location.getLongitude());
					url.append("&sensor=false");
					
					/*url.append("http://api.map.baidu.com/geocoder/v2/?ak=61f8bd72d68aef3a7b66537761d29d82&callback=renderReverse&location=");
					url.append(location.getLatitude()).append(",");
					url.append(location.getLongitude());
					url.append("*output=json&pois=0");*/
					
					   /*-----------发送请求---------------*/
					//HttpClient是一个接口,因此无法创建它的实例,一般是创建DefaultHttpClient实例
					HttpClient httpClient = new DefaultHttpClient();
					//如果需要GET请求,就需要创建HttpGet对象,并传入目标网络地址,execute()执行
					HttpGet httpGet = new HttpGet(url.toString());
					//添加了一个消息头,将语言类型指定为中文,不然会返回英文
					httpGet.addHeader("Accept-Language","zh-CN");
					HttpResponse httpResponse = httpClient.execute(httpGet);  //返回的东西存到httpResponse
					
					//如果返回200则说明请求个响应都成功了
					if(httpResponse.getStatusLine().getStatusCode() == 200){
						  /*-----------取出服务器的具体内容-------------*/
						//得到HttpEntity实例
						HttpEntity entity = httpResponse.getEntity();
						//将HttpEntity转换成字符串,编码为utf-8
						String response = EntityUtils.toString(entity,"utf-8");
						
						 /*-------------用JSONObject解析JSON格式内容------------*/
						
						JSONObject jsonObject = new JSONObject(response);
						//获取results节点下的位置信息
						JSONArray resultArray = jsonObject.getJSONArray("results");
						if(resultArray.length()>0){
							JSONObject subObject = resultArray.getJSONObject(0);
							//取出格式化后的位置信息
							String address = subObject.getString("formatted_address");
							
							//消息异步处理机制
							Message message = new Message();
							message.what = SHOW_LOCATION;
							message.obj = address;
							handler.sendMessage(message);
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					System.out.println("有点问题");
				}
			}
		}).start();
	}

	private Handler handler = new Handler(){
		public void handleMessage(Message msg) {
			switch (msg.what) {
			case SHOW_LOCATION:
				String currentPosition = (String)msg.obj;
				positionTextView.setText(currentPosition);
				break;
			default:
				break;
			}
		};
	};
	
	// 当程序关闭时,需要用removeUpdates()方法来将位置监听器移除,以保证不会继续消耗手机的电量
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (locationManager != null) {
			// 关闭程序时将监听器移除
			locationManager.removeUpdates(locationListener);
		}
	}

}
