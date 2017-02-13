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
 * 2016年8月2日11:25:32
 * 
 * 接收短信,发送短信
 * 
 * @author XFHY
 *
 * 当手机接收到一条短信的时候,系统会发出一条值为android.provider.Telephony.SMS_RECEIVED的广播
 * 这条广播里携带者与短信相关的所有数据,每个应用程序都可以在广播接收器里对它进行监听,收到广播时再从充解析出短信的
 * 内容即可
 * 
 * 
 * 我们需要创建一个光播接收器来接收系统发出的短信广播
 * 需要去配置文件声明权限<uses-permission android:name="android.permission.RECEIVE_SMS"/>
 */
public class MainActivity extends Activity {

	private TextView sender = null;   //发送者
	private TextView content = null;  //内容
	private EditText to = null;
	private EditText msg_input = null;
	private Button send = null;
	
	private IntentFilter sendFilter;  //发送的IntentFilter 
	private SendStatusReceiver sendStatusReceiver;
	
	private IntentFilter receiveFilter;  //接收的IntentFilter
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
        
        
        //动态注册广播技术   用来监听系统接收到的短信
        receiveFilter = new IntentFilter();
        //当收到短信时,系统会发出一条值为下面android.XXXX......的广播   想要监听什么广播,就在这里写就行 
        receiveFilter.addAction("android.provider.Telephony.SMS_RECEIVED");
        messageReceiver = new MessageReceiver();  //实例化广播接收器
        registerReceiver(messageReceiver,receiveFilter);  //注册广播   参数:广播接收器,IntentFilter实例
        
        //动态注册广播技术  用来监听发送的短信的状态
        sendFilter = new IntentFilter();
        sendFilter.addAction("SENT_SMS_ACTION");
        sendStatusReceiver = new SendStatusReceiver();
        registerReceiver(sendStatusReceiver,sendFilter);
        
        //发送短信
        send.setOnClickListener(new OnClickListener() {
			@Override
			public void onClick(View v) {
				//获取SmsManager实例
				SmsManager smsMagager = SmsManager.getDefault();
				
				//
				Intent sentIntent = new Intent("SENT_SMS_ACTION");
				
				//获取到一个PendingIntent对象
				PendingIntent pi = PendingIntent.getBroadcast(
						MainActivity.this, 0, sentIntent, 0);
				
				//调用SmsManager实例的sendTextMessage()方法就可以发送短信
				//第一个参数是接收人的手机号码,第三个是短信内容    其他的暂时用不到,传入null
				//第四个参数是PendingIntent对象
				smsMagager.sendTextMessage(to.getText().toString(),
						null, msg_input.getText().toString(), pi, null);
			}
		});
    }
    
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	unregisterReceiver(messageReceiver);  //取消广播
    	unregisterReceiver(sendStatusReceiver);
    }
    
    //内部类 这个类是专门监听短信发送状态的
    class SendStatusReceiver extends BroadcastReceiver{
		@Override
		public void onReceive(Context context, Intent intent) {
			if(getResultCode()==RESULT_OK){
				//短信发送成功!
				Toast.makeText(context, "短信发送成功!", Toast.LENGTH_SHORT).show();
			} else {
				//短信发送失败
				Toast.makeText(context, "短信发送失败!", Toast.LENGTH_SHORT).show();
			}
		}
    }
    
    //内部类  创建一个光播接收器来接收系统发出的短信广播
    class MessageReceiver extends BroadcastReceiver{

		@Override
		public void onReceive(Context context, Intent intent) {
			//先从Intent参数中取出一个Bundle对象
			Bundle bundle = intent.getExtras();
			
			//然后用pdu密匙来提取一个SMS pdus数组,其中每一个pdu都表示一条短信消息
			Object[] pdus = (Object[])bundle.get("pdus");  //提取短信消息
			
			//使用SmsMessage的createFromPdu()方法将每一个pdu字节数组转换为SmsMessage对象
			SmsMessage[] messages = new SmsMessage[pdus.length];
			for (int i = 0; i < messages.length; i++) {
				messages[i] = SmsMessage.createFromPdu((byte[])pdus[i]);
			}
			
			//调用getOriginatingAddress()可以获得发送方的号码
			String address = messages[0].getOriginatingAddress(); //获取发送方号码
			
			String fullMessage = "";
			for(SmsMessage message : messages){
				fullMessage += message.getMessageBody();    //获取短信内容
			}
			
			//将发送方的号码和短信内容显示在TextView上
			sender.setText(address);
			content.setText(fullMessage);
		}
    	
    }
}
