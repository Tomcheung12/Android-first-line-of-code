package com.example.smstest;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Bundle;
import android.telephony.SmsManager;
import android.telephony.gsm.SmsMessage;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

/**
 * 2016��8��2��11:25:32
 * 
 * ���ն���,���Ͷ���
 * 
 * @author XFHY
 *
 * ���ֻ����յ�һ�����ŵ�ʱ��,ϵͳ�ᷢ��һ��ֵΪandroid.provider.Telephony.SMS_RECEIVED�Ĺ㲥
 * �����㲥��Я�����������ص���������,ÿ��Ӧ�ó��򶼿����ڹ㲥��������������м���,�յ��㲥ʱ�ٴӳ���������ŵ�
 * ���ݼ���
 * 
 * 
 * ������Ҫ����һ���ⲥ������������ϵͳ�����Ķ��Ź㲥
 * ��Ҫȥ�����ļ�����Ȩ��<uses-permission android:name="android.permission.RECEIVE_SMS"/>
 */
public class MainActivity extends Activity {

	private TextView sender = null;   //������
	private TextView content = null;  //����
	private EditText to = null;
	private EditText msg_input = null;
	private Button send = null;
	
	private IntentFilter sendFilter;  //���͵�IntentFilter 
	private SendStatusReceiver sendStatusReceiver;
	
	private IntentFilter receiveFilter;  //���յ�IntentFilter
	private MessageReceiver messageReceiver;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        sender = (TextView)findViewById(R.id.sender);
        content = (TextView)findViewById(R.id.content);
        to = (EditText)findViewById(R.id.to);
        msg_input = (EditText)findViewById(R.id.msg_input);
        send = (Button)findViewById(R.id.send);
        
        
        //��̬ע��㲥����   ��������ϵͳ���յ��Ķ���
        receiveFilter = new IntentFilter();
        //���յ�����ʱ,ϵͳ�ᷢ��һ��ֵΪ����android.XXXX......�Ĺ㲥   ��Ҫ����ʲô�㲥,��������д���� 
        receiveFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        messageReceiver = new MessageReceiver();  //ʵ�����㲥������
        registerReceiver(messageReceiver,receiveFilter);  //ע��㲥   ����:�㲥������,IntentFilterʵ��
        
        //��̬ע��㲥����  �����������͵Ķ��ŵ�״̬
        sendFilter = new IntentFilter();
        sendFilter.addAction("SENT_SMS_ACTION");
        sendStatusReceiver = new SendStatusReceiver();
        registerReceiver(sendStatusReceiver,sendFilter);
        
        //���Ͷ���
        send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//��ȡSmsManagerʵ��
				SmsManager smsMagager = SmsManager.getDefault();
				
				//
				Intent sentIntent = new Intent("SENT_SMS_ACTION");
				
				//��ȡ��һ��PendingIntent����
				PendingIntent pi = PendingIntent.getBroadcast(
						MainActivity.this, 0, sentIntent, 0);
				
				//����SmsManagerʵ����sendTextMessage()�����Ϳ��Է��Ͷ���
				//��һ�������ǽ����˵��ֻ�����,�������Ƕ�������    ��������ʱ�ò���,����null
				//���ĸ�������PendingIntent����
				smsMagager.sendTextMessage(to.getText().toString(),
						null, msg_input.getText().toString(), pi, null);
			}
		});
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	unregisterReceiver(messageReceiver);  //ȡ���㲥
    	unregisterReceiver(sendStatusReceiver);
    }
    
    //�ڲ��� �������ר�ż������ŷ���״̬��
    class SendStatusReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			if(getResultCode()==RESULT_OK){
				//���ŷ��ͳɹ�!
				Toast.makeText(context, "���ŷ��ͳɹ�!", Toast.LENGTH_SHORT).show();
			} else {
				//���ŷ���ʧ��
				Toast.makeText(context, "���ŷ���ʧ��!", Toast.LENGTH_SHORT).show();
			}
		}
    }
    
    //�ڲ���  ����һ���ⲥ������������ϵͳ�����Ķ��Ź㲥
    class MessageReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			//�ȴ�Intent������ȡ��һ��Bundle����
			Bundle bundle = intent.getExtras();
			
			//Ȼ����pdu�ܳ�����ȡһ��SMS pdus����,����ÿһ��pdu����ʾһ��������Ϣ
			Object[] pdus = (Object[])bundle.get("pdus");  //��ȡ������Ϣ
			
			//ʹ��SmsMessage��createFromPdu()������ÿһ��pdu�ֽ�����ת��ΪSmsMessage����
			SmsMessage[] messages = new SmsMessage[pdus.length];
			for (int i = 0; i < messages.length; i++) {
				messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
			}
			
			//����getOriginatingAddress()���Ի�÷��ͷ��ĺ���
			String address = messages[0].getOriginatingAddress(); //��ȡ���ͷ�����
			
			String fullMessage = "";
			for(SmsMessage message : messages){
				fullMessage += message.getMessageBody();    //��ȡ��������
			}
			
			//�����ͷ��ĺ���Ͷ���������ʾ��TextView��
			sender.setText(address);
			content.setText(fullMessage);
		}
    	
    }
}
