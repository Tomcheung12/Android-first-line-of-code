package com.example.androidthreadtest;

import android.app.Activity;
import android.app.ProgressDialog;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 2016��8��3��22:12:48
 * 
 * ���������߳��и���UI
 * 
 * @author XFHY
 *
 * Android��UIҲ���̲߳���ȫ��,�����Ҫ����Ӧ�ó����UIԪ��,����������߳��н���,����ͻ�����쳣.
 * ������ʱ,���Ǳ��������߳�����ȥִ��һЩ��ʱ����,Ȼ����������ִ�н����������Ӧ��UI�ؼ�.
 * �����������,Android�ṩ��һ��  �첽��Ϣ�������  ,�����Ľ���������߳��н���UI����������.
 * 
 * �첽��Ϣ�������:��Ҫ����:
 *   ������Ҫ�����߳��д���һ��Handler����,����дhandleMessage()����,Ȼ�����߳�����Ҫ����UI����ʱ,�ʹ���һ��Message����,��ͨ��
 *   Handler��������Ϣ���ͳ�ȥ.֮��������Ϣ�ᱻ��ӵ�MessageQueue�Ķ����еȴ�������,��Looper���һֱ���Դ�MessageQueue��ȡ����
 *   �������Ϣ,,���ַ���Handler��handleMessage()������.����Handler�������߳��д�����,���Դ�ʱhandlerMessage()�����еĴ���
 *   Ҳ�������߳�������,��������������Ϳ��԰��ĵؽ���UI������.
 *   
 *   ����,Ϊ�˸��ӷ������������߳��ж�UI���в���,Android���ṩ������һЩ���õĹ���,AsyncTask��������.
 * 
 *
 */
public class MainActivity extends Activity implements OnClickListener{

	public static final int UPDATE_TEXT = 1;  //�����ʾ����TextView�������
	private Button changeText = null;
	private TextView text = null;
	ProgressDialog progressDialog = new ProgressDialog(this);
	
	/*
	 * ����һ��Handler����,����д�����handleMessage()����,������Ծ����Message���д���,�������msg.what����UPDATE_TEXT
	 * ,�ͽ�TextView��ʾ�����ݸĳ�Nice to meet you!.
	 * */
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {  //what�ֶ�Я��������
			case UPDATE_TEXT:
				text.setText("Nice to meet you!");
				break;
			default:
				break;
			}
		};
	};
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        changeText = (Button)findViewById(R.id.change_text);
        text = (TextView)findViewById(R.id.text);
        
        changeText.setOnClickListener(this);
    }

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.change_text:
			//����������ķ�ʽ�������߳�
			new Thread(new Runnable(){
				@Override
				public void run() {
					/*
					 * ���ǲ�û�������߳���ֱ�ӽ��и���Ui,���Ǵ�����һ��Message����.��������whatָ��ΪUPDATE_TEXT,
					 * Ȼ�����Handler��sendMessage()���������Message���ͳ�ȥ.�ܿ�,Handler���յ�����Message,
					 * ����handleMessage()�����н��д���.ע���ʱ��handleMessage()�����������߳�������,���ԾͿ��Ը���
					 * UI��.��������MessageЯ����what�ֶε�ֵ�����ж�,�����UPDATE_TEXT�͸���UI.
					 * */
					
					//text.setText("Nice to meet you!");
					Message message = new Message();
					message.what = UPDATE_TEXT;
					handler.sendMessage(message);
				}
			}).start();
			break;

		default:
			break;
		}
	}
}
