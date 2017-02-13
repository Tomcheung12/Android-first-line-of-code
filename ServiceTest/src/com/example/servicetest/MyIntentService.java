package com.example.servicetest;

import android.app.IntentService;
import android.content.Intent;
import android.util.Log;

/**
 * 2016��8��7��9:33:11
 * 
 * �򵥵Ĵ���һ���첽��,���Զ�ֹͣ�ķ���
 * 
 * @author XFHY
 *
 * Ϊ�˿���д�򵥵Ĵ���һ���첽��,���Զ�ֹͣ�ķ���,Androidר���ṩ��һ��IntentService��,�����ͺܺõĽ����
 * ���ǿ����߳�,��������stopSelf()����(�������������ֹͣ�����).
 * 
 * �������Ҫ�ṩһ���޲εĹ��캯��,���ұ��������ڲ����ø�����вι��캯��.
 * Ȼ��Ҫ��������ȥʵ��onHandleIntent()������󷽷�,����������д���һЩ������߼�.��������������߳�������,���õ���ANR����.
 * 
 * ����IntentService������,������������н�������Զ�ֹͣ��.
 * 
 */
public class MyIntentService extends IntentService {

	//�������Ҫ�ṩһ���޲εĹ��캯��,���ұ��������ڲ����ø�����вι��캯��
	public MyIntentService() {
		super("MyIntentService");  //���ø�����вι��캯��
	}

	//Ȼ��Ҫ��������ȥʵ��onHandleIntent()������󷽷�,����������д���һЩ������߼�.��������������߳�������,���õ���ANR����.
	@Override
	protected void onHandleIntent(Intent intent) {
		//��ӡ��ǰ�̵߳�id
		Log.d("xfhy","IntentService Thread id is "+Thread.currentThread().getId());
	}
	
	@Override
	public void onDestroy() {
		super.onDestroy();
		Log.d("xfhy","onDestroy executed");
	}

}
