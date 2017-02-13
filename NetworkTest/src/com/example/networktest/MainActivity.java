package com.example.networktest;

import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.StringReader;
import java.net.HttpURLConnection;
import java.net.URL;

import javax.xml.parsers.SAXParserFactory;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.DefaultHttpClient;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;
import org.xml.sax.InputSource;
import org.xml.sax.XMLReader;
import org.xmlpull.v1.XmlPullParser;
import org.xmlpull.v1.XmlPullParserFactory;

import android.app.Activity;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.TextView;

/**
 * 2016年8月8日9:31:08
 * 
 * 体验HttpURLConnection的用法,手动发送HTTP请求. sendRequestWithHttpURLConnection()方法
 * 体验HttpClient的用法  sendRequestWithHttpClient()
 * XML解析方式:Pull解析    SAX解析
 * JSON解析方式:官方提供的JSONObject,谷歌的开源库GSON
 * 
 * @author XFHY
 * 
 * 如果需要提交数据给服务器,只需要将HTTP请求的方法改成POST,并在获取输入流之前把要提交的数据写出即可.注意每条数据都要以键值对的形式存在
 * ,数据与数据之间用&符号隔开.比如说我们想要向服务器提交用户名和密码就可以这样写:
 *    connection.setRequestMethod("POST");
 *    DataOutputStream out = new DataOutputStream(connection.getOutputStream());
 *    out.writeBytes("username=admin&password=123456");
 * 
 */
public class MainActivity extends Activity implements OnClickListener {

	private static final int SHOW_RESPONSE = 0;
	private Button sendRequest;
	private TextView responseText;
	private Handler handler = new Handler(){
		public void handleMessage(android.os.Message msg) {
			switch (msg.what) {
			case SHOW_RESPONSE:
				String response = (String)msg.obj;
				//在这里进行UI操作,将结果显示到界面上
				responseText.setText(response);
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
        
        sendRequest = (Button)findViewById(R.id.send_request);
        responseText = (TextView)findViewById(R.id.response);
        sendRequest.setOnClickListener(this);
    }

    //按钮监听器
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.send_request){
			//自己写的方法
			//sendRequestWithHttpURLConnection();  //使用HttpURLConnection
			sendRequestWithHttpClient();    //使用HttpClient
		}
	}

	//使用HttpClient
	private void sendRequestWithHttpClient() {
		//在子线程中使用HttpClient发出一条HTTP请求
		new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					//HttpClient是一个接口,因此无法创建它的实例,通常情况下,都会创建一个DefaultHttpClient的实例
					HttpClient httpClient = new DefaultHttpClient();
					
					//发起一条GET请求,需要创建一个HttpGet对象,并传入目标的网络地址.然后调用HttpClient的execute()方法
					//HttpGet httpGet = new HttpGet("http://www.baidu.com");
					
					//指定访问的服务器地址是电脑本机   192.168.43.48是本机的IP地址,在得到了服务器返回的收据后,我们并不再
					//去发送一条消息,而是调用了parseXMLWithPull()方法来解析服务器返回的数据
					//192.168.56.1   10.0.2.2
					//HttpGet httpGet = new HttpGet("http://10.0.2.2:8080/get_data.xml");
					HttpGet httpGet = new HttpGet("http://10.0.2.2/get_data.json");
					HttpResponse httpResponse = httpClient.execute(httpGet);
					
					//取出服务器返回的状态码,如果等于200就说明请求和响应都成功了
					if(httpResponse.getStatusLine().getStatusCode()==200){
						//取出服务返回的具体内容
						HttpEntity entity = httpResponse.getEntity();
						//将内容转换为字符串,并设置编码为 UTF-8
						String response = EntityUtils.toString(entity,"utf-8");
						
						//解析XML格式
						//parseXMLWithPull(response);   Pull解析
						//parseXMLWithSAX(response);      //SAX解析
						
						//解析JSON格式
						parseJSONWithJSONObject(response);
						
						/*//需要传递消息
						Message message = new Message();
						message.what = SHOW_RESPONSE;
						//将服务器返回的结果存放到Message(里面的obj)中
						message.obj = response.toString();
						handler.sendMessage(message);*/
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			

			
		}).start();
	}

	//解析服务器返回的数据   JSON格式的    用官方推荐的JSONObject方式
	private void parseJSONWithJSONObject(String jsonData) {
		try {
			//由于我们在服务器中定义的是JSON数组,因此这里首先是将服务器返回的数据传入到了一个JSONArray对象中
			JSONArray jsonArray = new JSONArray(jsonData);
			//循环遍历这个JSONArray,从中取出的每一个元素都是一个JSONObject对象,每个JSONObject对象中又包含id,name,version这些数据
			//接下来只需要调用getString()方法将这些数据取出.
			for (int i = 0; i < jsonArray.length(); i++) {
				JSONObject jsonObject = jsonArray.getJSONObject(i);
				String id = jsonObject.getString("id");
				String name = jsonObject.getString("name");
				String version = jsonObject.getString("version");
				Log.d("xfhy","id is "+id);
				Log.d("xfhy","name is "+name);
				Log.d("xfhy","version is "+version);
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//解析服务器返回的数据   SAX解析
	@SuppressWarnings("unused")
	private void parseXMLWithSAX(String xmlData) {
		try {
			//创建SAXParserFactory对象
			SAXParserFactory factory = SAXParserFactory.newInstance();
			//获取到XMLReader对象
			XMLReader xmlReader = factory.newSAXParser().getXMLReader();
			ContentHandler handler = new ContentHandler();
			//将ContentHandler的实例设置到XMLReader中
			xmlReader.setContentHandler(handler);
			//开始解析
			xmlReader.parse(new InputSource(new StringReader(xmlData)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//解析服务器返回的数据    Pull解析
	@SuppressWarnings("unused")
	private void parseXMLWithPull(String xmlData) {
		try {
			//首先需要获取XmlPullParserFactory实例
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			//通过XmlPullParserFactory实例得到XmlPullParser对象
			XmlPullParser xmlPullParser = factory.newPullParser();
			//setInput()将服务器返回的数据设置进去就可以开始解析了
			xmlPullParser.setInput(new StringReader(xmlData));
			//通过getEventType()可以得到当前的解析事件,然后在while循环中不断地进行解析,调用next()方法可以获取下一个解析事件
			int eventType = xmlPullParser.getEventType();
			String id = "";
			String name = "";
			String version = "";
			while(eventType!=XmlPullParser.END_DOCUMENT){
				//getName():得到当前结点的名字
				String nodeName = xmlPullParser.getName();
				switch (eventType) {
				//开始解析某个结点
				case XmlPullParser.START_TAG:
					//如果发现结点名等于id,name,version,就调用nextText()方法来获取结点中具体的内容,每当解析完一个app结点后
					//就将获取到的内容解析出来
					if("id".equals(nodeName)){
						id = xmlPullParser.nextText();
					} else if ("name".equals(nodeName)){
						name = xmlPullParser.nextText();
					} else if ("version".equals(nodeName)){
						version = xmlPullParser.nextText();
					}
					break;
				//完成解析某个结点
				case XmlPullParser.END_TAG:
					if("app".equals(nodeName)){
						Log.d("xfhy","id is "+id);
						Log.d("xfhy","name is "+name);
						Log.d("xfhy","version is "+version);
					}
					break;
				default:
					break;
				}
				eventType = xmlPullParser.next();
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//使用HttpURLConnection
	@SuppressWarnings("unused")
	private void sendRequestWithHttpURLConnection() {
		//开启线程来发送网络请求
		new Thread(new Runnable(){
			//实现run()方法
			@Override
			public void run() {
				HttpURLConnection connection = null;
				try {
					//获取HttpURLConnection对象
					URL url = new URL("http://www.baidu.com");
					connection = (HttpURLConnection)url.openConnection(); //发送一条HTTP请求
					
					/*
					 * 设置HTTP请求所使用的方法:常用的方法主要有2个,GET和POST.GET表示希望从服务器那里获取数据,
					 * 而POST则表示希望提交数据给服务器
					 * */
					connection.setRequestMethod("GET");  //设置HTTP请求
					connection.setConnectTimeout(8000);  //设置连接超时是8000毫秒
					connection.setReadTimeout(8000);     //读取超时
					
					//getInputStream():获取到服务器返回的输入流
					InputStream in = connection.getInputStream();
					//下面对获取到的输入流进行读取   InputStreamReader:将字节流转换成字符流  BufferedReader:从字符流中读取文本
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					
					//按行读取数据
					StringBuilder response = new StringBuilder();
					String line;
					while((line=reader.readLine())!=null){
						response.append(line);
					}
					
					//实例化Message对象,用来传入消息
					Message message = new Message();
					message.what = SHOW_RESPONSE;
					
					//将服务器返回的结果存放到Message中
					message.obj = response.toString();
					handler.sendMessage(message);  //发送Message
					
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if(connection!=null){
						connection.disconnect();    //记得关闭HTTP连接
					}
				}
			}
		}).start();   //启动线程
	}
}
