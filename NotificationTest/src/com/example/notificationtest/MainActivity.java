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
 * 2016��8��2��9:14:46
 * 
 * ʹ��֪ͨ,֪ͨ�Ļ����÷�
 * 
 * @author XFHY
 *
	 * �����м���ط���Ҫע�⣺
	1.�����ֻ֪ͨ���� ��֪ͨ�������ã���ϣ���û����������Ӧ����ת����ô��
	intent,pendingIntent�⼸�д�����Բ�д�����Դ�����ʱ������
	��֪ͨ���ɹ� notify ��һ��ʱ������manager.cancel(notificationID),��֪ͨ�����
	��ʱbuilder.setAutoCancel()������дҲ���ԡ�  
	2.���֪ͨ��������ϣ���û����������Ӧ����ת�¼�����ô��Ҫע����ת��֪ͨ�Ƿ��б�Ҫ�������ڡ�
	��������֪ͨ��ʧ�����ַ���
	A ����setAutoCancel ��������Ϊ true��Ĭ����false��
	B ��intent�¼��е��籾���е�MainActivity��onCreate()�������� manager.cancel(notificationID)ȡ����֪ͨ��
	��IDҪ�ʹ���֪ͨ��IDһ�£�����֪ͨ����ʧ������������ʧ����setAutoCancel ��������Ϊ false���ɡ�
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
			   /*----------���ַ�ʽ�Ѿ���ʱ-------------*/
			/*//NotificationManager����,��֪ͨ���й���
			NotificationManager manager = (NotificationManager)
			      getSystemService(NOTIFICATION_SERVICE);
			
			//֪ͨͼ��,֪ͨ����,֪ͨʱ��   ����һ��Notification����
			Notification notification = new Notification(R.drawable.icon,
					"This is ticker text",System.currentTimeMillis());
			
			//����֪ͨ����,֪ͨ����,֪ͨ����
			notification.setLatestEventInfo(this, "This is content title",
					"This is content text", null);
			
			//id,ÿ��֪ͨ��id��ͬ    ��������ʾ֪ͨ
			manager.notify(1,notification);*/
			
			/*--------------------------�������µķ�ʽ(SDK 23)----------------------------*/
			NotificationManager manager = (NotificationManager)
		      getSystemService(NOTIFICATION_SERVICE);
			
			//ͨ��Builder������Notification����
			Notification.Builder builder = new Notification.Builder(this);
			builder.setContentInfo("10086");   //��������
			builder.setContentText("���Ļ�����Ƿ��,�뼰ʱ��������,лл......");  //��������
			builder.setContentTitle("�����µĶ���Ϣ");    //֪ͨ����
			builder.setSmallIcon(R.drawable.icon);   //ͼ��
			builder.setTicker("����Ϣ");
			builder.setAutoCancel(false);     //�Զ�ȡ��
			builder.setWhen(System.currentTimeMillis());   //���õ�ǰʱ��
			
			//���֪ͨ�Ϳ�����ת���ڶ���ҳ����
			Intent intent = new Intent(this, NotificationActivity.class);   //������ת�Ļ
			PendingIntent pendingIntent = PendingIntent.
					getActivity(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT);
			builder.setContentIntent(pendingIntent);   //������ת
			Notification notification = builder.build();  //����Notification����
			
			/*---------------����֪ͨ������-------------------*/
			Uri soundUri = Uri.fromFile(new File("/system/media/audio/ringtones/Basic_tone"));
			notification.sound = soundUri;
			
			/*---------------����֪ͨʱ��  ����Ҫ����Ȩ��-------------------*/
			long[] vibrates = {0,1000,1000,1000};  
			//��,��,��,��   �����ʾ�Ⱦ�ֹ0��,������1��,�پ�ֹ1��,����1��,��λ�Ǻ���
			notification.vibrate = vibrates;
			
			/*---------------����LED��֪ͨ-------------------*/
			notification.ledARGB = Color.RED;   //������ɫ
			notification.ledOnMS = 1000;          //���õ������ʱ��
			notification.ledOffMS = 1000;         //���õư�ȥ��ʱ��
			notification.flags = Notification.FLAG_SHOW_LIGHTS; //����ָ��֪ͨ��һЩ��Ϊ
			
			//��Ҳ���Բ������õ���ô����,����ֱ��ʹ��Ĭ�ϵ�֪ͨЧ��  ��������ֻ��Ļ�������������ʲô����,�Լ������
			//notification.defaults = Notification.DEFAULT_ALL;
			
			manager.notify(1, notification);   //��ʾ֪ͨ
			break;

		default:
			break;
		}
	}
}
