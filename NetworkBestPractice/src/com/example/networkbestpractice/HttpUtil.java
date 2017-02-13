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
 * 2016��8��12��21:52:20
 * ר�����������������
 * @author XFHY
 * 
 * һ��Ӧ�ó���ܿ��ܻ��ںܶ�ط���ʹ�õ����繦��,������HTTP����Ĵ������������ͬ��
 * ����,ͨ�������,���Ƕ�Ӧ�ý���Щͨ�õ����������ȡ��һ��������������,���ṩһ����̬����,����
 * Ҫ�������������ʱ��ֻ��򵥵ص���һ�������������.
 * 
 * �ڻ�ȡ��������Ӧ�����ݺ����ǾͿ��Զ������н����ʹ�����.������Ҫע��,��������ͨ�������ں�ʱ����,��sendHttpRequest()�������ڲ�
 * ��û�п�ʼ�̵߳Ļ�,�������п��ܵ����ڵ���sendHttpRequest()������ʱ��ʹ�����̱߳�����ס.
 * 
 * ������Ҫ�õ����߳�,���߳�ȥִ�о�����������,ע�����߳����޷���return������������ݵ�.����������ǽ���������Ӧ�����ݴ�����
 * �Լ�д��HttpCallbackListener��onFinish()��������,����������쳣�ʹ���onError()������.
 * ��ʹ��java�Ļص����ƾͿ�����.
 * 
 * �����߳���ʹ��Toast��ʱ��,��Can't create handler inside thread that has not called Looper.prepare()����ʱ,
 * ��Ҫ��Toastǰ�����Looper.prepare();��Looper.loop();
 * 
 */
public class HttpUtil {
	public static void sendHttpRequest(final String address,final HttpCallbackListener listener){
		
		//�����ж�һ�µ�ǰ�����Ƿ��������  �����ԵĻ�,ֱ�Ӳ���ִ������Ĵ�����
		//if(!isNetworkAvailable()){
		if(!ping()){
			Looper.prepare(); //��Ҫ����Looper.prepare()�����̴߳���һ����Ϣѭ��
			Toast.makeText(MyApplication.getContext(), "��ǰ���粻����", Toast.LENGTH_SHORT).show();
			Looper.loop();   //��Looper��ʼ����������Ϣ������ȡ��Ϣ��������Ϣ�� 
			return ;
		}
		
		new Thread(new Runnable(){
			@Override
			public void run() {
				HttpURLConnection connection = null;
				try {
					URL url = new URL(address);
					connection = (HttpURLConnection)url.openConnection();
					connection.setRequestMethod("GET");  //���� URL ����ķ���
					connection.setConnectTimeout(8000);  //����һ������ָ���ĳ�ʱֵ���Ժ���Ϊ��λ��
					connection.setReadTimeout(8000);     //������ʱ����Ϊָ���ĳ�ʱֵ���Ժ���Ϊ��λ
					connection.setDoInput(true);  //URL ���ӿ����������/��������������ʹ�� URL ���ӽ������룬�� DoInput ��־����Ϊ true��
					connection.setDoOutput(true);
					
					//��ȡ���ӷ��ص�������
					InputStream in = connection.getInputStream();
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					StringBuilder response = new StringBuilder();
					String line;
					while((line=reader.readLine())!=null){
						response.append(line);
					}
					//return response.toString();
					
					//����������Ӧ�����ݴ�����HttpCallbackListener��onFinish()��������,�������������������Ϳ��Ի�ȡ��������
					if(listener!=null){
						//�ص�onFinish()����
						listener.onFinish(response.toString());
					}
				} catch (Exception e) {
					Log.d("xfhy",e.getMessage());
					
					//�������쳣�ʹ���onError()������.
					if(listener!=null){
						//�ص�onError()����
						listener.onError(e);
					}
				} finally {    //���ǵùر�����
					if(connection!=null){
						connection.disconnect();
					}
				}
			}
		}).start();
	}

	/**
	 * �ж������Ƿ�����   
	 * �򵥵��ж������Ƿ�����,����һЩ������������ж�,���� �������ƶ����絫�޷�����,������wifi���޷�������.
	 * @return �������򷵻�true,���򷵻�false(�޿�������)
	 */
	public static boolean isNetworkAvailable() {
		
		//������Ҫ����Ȩ��
		//<uses-permission android:name="android.permission.ACCESS_NETWORK_STATE"/>
		//�õ���������
		ConnectivityManager manager = (ConnectivityManager)
				MyApplication.getContext().getSystemService(Context.CONNECTIVITY_SERVICE);
		
		//ȥ�ж������Ƿ�����
		if(manager.getActiveNetworkInfo() != null){
			return manager.getActiveNetworkInfo().isAvailable();  //�������������Ƿ����
		}
		return false;
	}
	
	/**
	 * �ж��Ƿ��������������ͨ���������ж������������Ƿ����ӣ����������Ͼ�������
	 * ��ʱ������������һ��û���������ӵ�WiFi������Ҫ�����˺ź���������������������磬
	 * �ͻ������Ȼ������ã���������ȴ�����Է��ʡ�������������һ��Ľ���취����pingһ�������������pingͨ��˵����������������
	 * @return  ���ӵ����򷵻�true,���򷵻�false(��������ʧ��)
	 */
	public static final boolean ping() {
		String result = null;
		
		try {
			String ip = "www.baidu.com";   //ping�ĵ�ַ
			Process p = Runtime.getRuntime().exec("ping -c 3 -w 100 "+ip);  //ping��ַ3��
			
			/*//��ȡping������,���Բ���
			InputStream input = p.getInputStream();
			//�ֽ���ת�����ַ���
			BufferedReader in = new BufferedReader(new InputStreamReader(input));
			StringBuffer stringBuffer = new StringBuffer();
			String content = "";
			while( (content = in.readLine()) != null ){  //���ж�ȡ
				stringBuffer.append(content);
			}
			
			Log.d("xfhy", "result content : " + stringBuffer.toString()); */
			 
			//ping��״̬
			int status = p.waitFor();   //the exit value of the native process being waited on.  
			//�Ҿ������ж��߳��Ƿ�����
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
