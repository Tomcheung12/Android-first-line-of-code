package com.example.networkbestpractice;

import android.app.Application;
import android.content.Context;

/**
 * 2016��8��18��10:18:21
 * 
 * ȫ�ֻ�ȡContext�ļ���
 * 
 * @author XFHY
 * 
 * ��ʱ����Ҫ�õ�Context,����Toast,�����,���͹㲥,�������ݿ�,ʹ��֪ͨ��ʱ����ҪContext
 * ����ʱ���ȡContext�е��Ѷ�.���������и��������Խ����������.
 * 
 * Android�ṩ��һ��Application��,ÿ��Ӧ�ó���������ʱ��,ϵͳ�ͻ��Զ���������ʼ��.
 * �����ǿ��Զ���һ���Լ���Application��,�Ա��ڹ��������һЩȫ�ֵ�״̬��Ϣ,����˵Context
 * 
 * ����һ���Լ���Application,��Ҫ�̳���Application,��onCreate()�����г�ʼ���õ�һ��Ӧ�ü����Context,�½�һ����̬����,
 * ���Է���Context;  ��������Ҫ��֪ϵͳ,����������ʱӦ�ó�ʼ��MyApplication��,������Ĭ�ϵ�Application��.��AndroidManifest.xml
 * �ļ���<application>��ǩ�½���ָ���Ϳ�����.
 * android:name="com.example.networkbestpractice.MyApplication"
 * 
 */
public class MyApplication extends Application {
	private static Context context;
	
	@Override
	public void onCreate() {
		//ͨ������getApplicationContext()�����õ�һ��Ӧ�ü����Context
		context = getApplicationContext();
	}
	
	//��̬����ȫ�ֶ��ɵ���
	public static Context getContext(){
		return context;
	}
}
