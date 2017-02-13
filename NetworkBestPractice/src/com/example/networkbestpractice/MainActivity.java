package com.example.networkbestpractice;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 2016��8��12��21:37:17
 * �����̵����ʵ��   �Լ����,������HttpURLConnection����ٶ�,��������,����ʾ����
 * �õ���java�Ļص�����.�첽��Ϣ������Ƹ���UI
 * @author XFHY
 * 
 * һ��Ӧ�ó���ܿ��ܻ��ںܶ�ط���ʹ�õ����繦��,������HTTP����Ĵ������������ͬ��
 * ����,ͨ�������,���Ƕ�Ӧ�ý���Щͨ�õ����������ȡ��һ��������������,���ṩһ����̬����,����
 * Ҫ�������������ʱ��ֻ��򵥵ص���һ�������������.
 * 
 * ���������ɹ���Ӧ��ʱ�����ǾͿ�����onFinish()���������Ӧ���ݽ��д���,���Ƶ�,�������쳣,�Ϳ�����onError()��������쳣���д���.
 * ���һ��,���Ǿ���������ûص����ƽ���Ӧ���ݳɹ����ظ����÷���.
 * 
 * ����,��Ҫע�����,onFinish()��onError()�������ջ��������߳������е�,��˲�����������ֻ���κε�UI����,�����Ҫ���ݷ��صĽ��������
 * UI,����Ҫʹ���첽��Ϣ�������.
 * 
 */
public class MainActivity extends Activity implements OnClickListener{

	private Button send;
	private TextView textview;
	private final static int SHOW = 1;
	private String address = "http://www.bilibili.com/";
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SHOW:
				String response = (String)msg.obj;
				textview.setText(response);
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
        
        send = (Button)findViewById(R.id.http_request);
        textview = (TextView)findViewById(R.id.textview);
        
        send.setOnClickListener(this);
    }

    //��ť������
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.http_request){
			new Thread(new Runnable(){
				@Override
				public void run() {
					//�Լ������sendHttpRequest()����������������
					HttpUtil.sendHttpRequest(address,new HttpCallbackListener() {
						
						//onFinish()�����еĲ��������ŷ��������ص�����
						//��������ݷ��ص�����ִ�о�����߼�,������ֻ�ǽ����������ص����ݷ��͵�Handler��ȥ��
						@Override
						public void onFinish(String response) {
							//�첽��Ϣ�������
							Message message = new Message();
							message.what  = SHOW;
							message.obj = response.toString();//�����������صĽ���浽Message������
							handler.sendMessage(message);  //��Message��������ȥ
						}
						
						//onError()�еĲ�����¼�Ŵ������ϸ��Ϣ
						//��������쳣������д���     ������ֻ�ǽ�������Ϣ���͵�Handler��,����ʾ����
						@Override
						public void onError(Exception e) {
							//�첽��Ϣ�������
							Message message = new Message();
							message.what  = SHOW;
							message.obj = e.getMessage();  //��������Ϣ���ݽ�ȥ
							handler.sendMessage(message);  //��Message��������ȥ
						}
					});
					
					
				}
			}).start();
			
		}
	}
	

}
