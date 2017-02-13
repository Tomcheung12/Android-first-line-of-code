package com.example.servicetest;

import android.app.Notification;
import android.app.PendingIntent;
import android.app.Service;
import android.content.Intent;
import android.os.Binder;
import android.os.IBinder;
import android.util.Log;

/**
 * 2016��8��6��22:20:48
 * 
 * �����Ƿ������,����Ļ���ʹ��
 * 
 * ����ĸ��༼��:����ǰ̨����
 * 
 * @author XFHY
 * 
 *         ��д��onCreate(),onStartCommand()��onDestroy()��3������,������ÿ����������õ���3������,
 *         ����onCreate()�������ڷ��񴴽�
 *         ��ʱ�����,onStartCommand()��������ÿ�η���������ʱ�����,onDestroy()�������ڷ������ٵ�ʱ�����.
 * 
 *         ͨ�������,�������ϣ������һ�����������ȥִ��ĳ������,�Ϳ��Խ��߼�д��onStartCommand()������.������������ʱ,
 *         ������Ӧ���� onDestroy()������ȥ������Щ����ʹ�õ���Դ.
 * 
 *         ����,��Ҫע��,ÿһ��������Ҫ��AndroidManifest.xml�ļ��н���ע�������Ч,����Android�Ĵ�������е��ص�.
 * 
 *         ��MyService���κ�һ��λ�õ���stopSelf()�����������������ֹͣ������
 * 
 *         ��������startService()������,��ȥ����stopService()����,���������Ҫͬʱ����stopService()
 *         ��unbindService()����,onDestroy() �����Ż�ִ��
 * 
 *         ǰ̨�������ͨ�������������,����һֱ��һ���������е�ͼ����ϵͳ��״̬����ʾ,����״̬������Կ���������ϸ����Ϣ,
 *         �ǳ�������֪ͨЧ��.
 *         
 *         �����������߳�,���׼��ʽ���ڷ�����д�����´���
 *            new Thread(new Runnable() {
				@Override
				public void run() {
					//��������д  ���������߼�
					
					stopSelf();   //ֹͣ����(�����Ҫһ������ִ���������Զ�ֹͣ�Ĺ���,��д��������)
				}
			}).start();
 * 
 */
public class MyService extends Service {

	private DownloadBinder mBinder = new DownloadBinder();

	// �����û�����Ĺ�ϵ������ �ָ�ӷ����ͨ�����
	@Override
	public IBinder onBind(Intent intent) {
		return mBinder;
	}

	// �Զ�����ڲ���
	class DownloadBinder extends Binder {
		// ��ʼ����
		public void startDownload() {
			Log.d("xfhy", "startDownload executed");
		}

		// �鿴���ؽ���
		public int getProgress() {
			Log.d("xfhy", "getProgress executed");
			return 0;
		}
	}

	@Override
	public void onCreate() {
		super.onCreate();
		Log.d("xfhy", "onCreate executed");

		// ������Ϊ��ǰ̨����,��д�Ĵ��� ����֪ͨ
		// ����Notification����,����:ͼ��,����,��������ʱ��
		Notification notification = new Notification(R.drawable.ic_launcher,
				"Notification comes", System.currentTimeMillis());
		Intent notificationIntent = new Intent(this, MainActivity.class);
		// ���Ҳ�������������,�����������������ĳ��ʱ��ȥִ��ĳ������.����:context,�ڶ���һ�㴫��0,������������Intent����,���ĸ���
		// ����ȷ��PendingIntent����Ϊ,��4��ֵ.
		PendingIntent pendingIntent = PendingIntent.getActivity(this, 0,
				notificationIntent, 0);
		// ��֪ͨ�Ĳ��ֽ����趨,����:context,��������(����״̬���Ϳ��Կ���),֪ͨ����,PendingIntent����
		notification.setLatestEventInfo(this, "This is title",
				"This is content", pendingIntent);
		// ��֪ͨ��ʾ����,����:֪ͨ��id,��������Notification����
		// ����startForeground()������ͻ���MyService���һ��ǰ̨����
		// ����ϵͳ��״̬����ʾ����.
		startForeground(1, notification);
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		Log.d("xfhy", "onStartCommand executed");

		/*new Thread(new Runnable() {
			@Override
			public void run() {
				//��������д  ���������߼�
				
				stopSelf();   //ֹͣ����(�����Ҫһ������ִ���������Զ�ֹͣ�Ĺ���,��д��������)
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
