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
 * 2016��8��13��8:18:31
 * 
 * ��ȡ��ǰλ�þ�γ��
 * 
 * @author XFHY
 * 
 *         �뵽�����ĵط�,���ܶ�λ
 * 
 *      ��ȡ��ǰ��γ�ȵĲ���:
 *         1.��onCreate()������ʵ����LocationListener����,����д����ķ���,onLocationChanged
 *           ( )��ʾλ�ñ仯ʱ���� (��������ʾ���µ�λ��) 
 *         2.��ȡLocationManagerʵ��,ͨ��getSystemService()����
 *         3.��ȡ���п��õ�λ���ṩ�� �����ʱ�����붨λ�ľ��Ⱦ�����һЩ,���ֲ�ȷ��GPS��λ�Ĺ����Ƿ��Ѿ�����,���ʱ��Ϳ������ж�һ��
 *            ����Щλ���ṩ������ 
 *         4.��ѡ��õ�λ���ṩ�����뵽getLastKnownLocation()������.�Ϳ��Եõ�һ��Location����
 *           ���Location�����а����˾���,γ��,���ε�һϵ�е�λ����Ϣ,Ȼ�����ȡ�����������ĵ��ǲ������ݼ���
 *         5.���豸λ�÷����ı�ʱ,��ô����ͨ��requestLocationUpdates()����������µ�λ����Ϣ.
 *           ����:λ���ṩ������,����λ�ñ仯ʱ����,����λ�ñ仯�ľ�����,LocationListener
 *         6.�ڻ��ͣ(onPause)���߻����
 *            (onDestroy)ʱ,�ǵõ���removeUpdates()��������λ�ü������Ƴ�,�Ա�֤������������ֻ��ĵ���
 *         7.��ҪȨ��<uses-permission
 *            android:name="android.permission.ACCESS_FINE_LOCATION"/>
 *      
 *       ���뷴�������빦��(����γ��ת��Ϊ���ö���λ����Ϣ):
 *         1.����һ��HTTP������ȸ�ķ�����
 *         2.Ȼ���ٽ����ص�JSON���ݽ��н���,����HTTP����ķ�ʽ����׼��ʹ��HttpClient
 *         3.����JSON���ݵķ�ʽʹ��JSONObject.
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

		// λ�ü����� ʵ����
		locationListener = new LocationListener() {

			// ��λ�øı�ʱ
			@Override
			public void onLocationChanged(Location location) {
				Toast.makeText(MainActivity.this, "λ���Ѿ��仯��", Toast.LENGTH_SHORT)
						.show();
				// �Լ�д�ķ���,��ʾ��ǰλ��
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

		// ��ȡLocationManagerʵ��
		locationManager = (LocationManager) getSystemService(Context.LOCATION_SERVICE);
		// ��ȡ���п��õ�λ���ṩ�� �����ʱ�����붨λ�ľ��Ⱦ�����һЩ,���ֲ�ȷ��GPS��λ�Ĺ����Ƿ��Ѿ�����,���ʱ��Ϳ������ж�һ��
		// ����Щλ���ṩ������
		List<String> providerList = locationManager.getProviders(true); // �������п��õ�λ���ṩ��
		if (providerList.contains(LocationManager.GPS_PROVIDER)) {
			provider = LocationManager.GPS_PROVIDER;
			Toast.makeText(MainActivity.this, "GPS_PROVIDER",
					Toast.LENGTH_SHORT).show();
		} else if (providerList.contains(LocationManager.NETWORK_PROVIDER)) {
			provider = LocationManager.NETWORK_PROVIDER;
			Toast.makeText(MainActivity.this, "NETWORK_PROVIDER",
					Toast.LENGTH_SHORT).show();
		} else {
			// ��û��λ���ṩ��ʱ,����Toast��ʾ�û�
			Toast.makeText(this, "No location provider to use",
					Toast.LENGTH_SHORT).show();
			return;
		}
		// ��ѡ��õ�λ���ṩ�����뵽getLastKnownLocation()������.�Ϳ��Եõ�һ��Location����
		// ���Location�����а����˾���,γ��,���ε�һϵ�е�λ����Ϣ,Ȼ�����ȡ�����������ĵ��ǲ������ݼ���
		Location location = locationManager.getLastKnownLocation(provider);

		if (location != null) {
			// ��ʾ��ǰ�豸��λ����Ϣ
			showLocation(location);
		} else {
			positionTextView.setText("λ����ϢΪ��");
		}

		/*
		 * ���豸λ�÷����ı�ʱ,��ô����ͨ��requestLocationUpdates()����������µ�λ����Ϣ.
		 * ����:λ���ṩ������,����λ�ñ仯ʱ����,����λ�ñ仯�ľ�����,LocationListener
		 */
		locationManager.requestLocationUpdates(provider, 5000, 1,
				locationListener);

	}

	// ��ʾ��ǰ�豸λ����Ϣ
	private void showLocation(final Location location) {
		Toast.makeText(this, "��ʼ��ʾλ����", Toast.LENGTH_SHORT).show();
		// ��ʾ���Ⱥ�γ��
		String currentPosition = "latitude is " + location.getLatitude() + "\n"
				+ "longitude is " + location.getLongitude();
		positionTextView.setText(currentPosition);
		
		//��Ҫ������������,������Ҫ�������߳�
		new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					//��װ����������Ľӿڵ�ַ
					StringBuilder url = new StringBuilder();
					url.append("http://maps.googleapis.com/maps/api/geocode/json?latlng=");
					url.append(location.getLatitude()).append(",");
					url.append(location.getLongitude());
					url.append("&sensor=false");
					
					/*url.append("http://api.map.baidu.com/geocoder/v2/?ak=61f8bd72d68aef3a7b66537761d29d82&callback=renderReverse&location=");
					url.append(location.getLatitude()).append(",");
					url.append(location.getLongitude());
					url.append("*output=json&pois=0");*/
					
					   /*-----------��������---------------*/
					//HttpClient��һ���ӿ�,����޷���������ʵ��,һ���Ǵ���DefaultHttpClientʵ��
					HttpClient httpClient = new DefaultHttpClient();
					//�����ҪGET����,����Ҫ����HttpGet����,������Ŀ�������ַ,execute()ִ��
					HttpGet httpGet = new HttpGet(url.toString());
					//�����һ����Ϣͷ,����������ָ��Ϊ����,��Ȼ�᷵��Ӣ��
					httpGet.addHeader("Accept-Language","zh-CN");
					HttpResponse httpResponse = httpClient.execute(httpGet);  //���صĶ����浽httpResponse
					
					//�������200��˵���������Ӧ���ɹ���
					if(httpResponse.getStatusLine().getStatusCode() == 200){
						  /*-----------ȡ���������ľ�������-------------*/
						//�õ�HttpEntityʵ��
						HttpEntity entity = httpResponse.getEntity();
						//��HttpEntityת�����ַ���,����Ϊutf-8
						String response = EntityUtils.toString(entity,"utf-8");
						
						 /*-------------��JSONObject����JSON��ʽ����------------*/
						
						JSONObject jsonObject = new JSONObject(response);
						//��ȡresults�ڵ��µ�λ����Ϣ
						JSONArray resultArray = jsonObject.getJSONArray("results");
						if(resultArray.length()>0){
							JSONObject subObject = resultArray.getJSONObject(0);
							//ȡ����ʽ�����λ����Ϣ
							String address = subObject.getString("formatted_address");
							
							//��Ϣ�첽�������
							Message message = new Message();
							message.what = SHOW_LOCATION;
							message.obj = address;
							handler.sendMessage(message);
						}
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					System.out.println("�е�����");
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
	
	// ������ر�ʱ,��Ҫ��removeUpdates()��������λ�ü������Ƴ�,�Ա�֤������������ֻ��ĵ���
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if (locationManager != null) {
			// �رճ���ʱ���������Ƴ�
			locationManager.removeUpdates(locationListener);
		}
	}

}
