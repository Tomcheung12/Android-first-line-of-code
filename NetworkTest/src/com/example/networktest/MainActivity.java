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
 * 2016��8��8��9:31:08
 * 
 * ����HttpURLConnection���÷�,�ֶ�����HTTP����. sendRequestWithHttpURLConnection()����
 * ����HttpClient���÷�  sendRequestWithHttpClient()
 * XML������ʽ:Pull����    SAX����
 * JSON������ʽ:�ٷ��ṩ��JSONObject,�ȸ�Ŀ�Դ��GSON
 * 
 * @author XFHY
 * 
 * �����Ҫ�ύ���ݸ�������,ֻ��Ҫ��HTTP����ķ����ĳ�POST,���ڻ�ȡ������֮ǰ��Ҫ�ύ������д������.ע��ÿ�����ݶ�Ҫ�Լ�ֵ�Ե���ʽ����
 * ,����������֮����&���Ÿ���.����˵������Ҫ��������ύ�û���������Ϳ�������д:
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
				//���������UI����,�������ʾ��������
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

    //��ť������
	@Override
	public void onClick(View v) {
		if(v.getId()==R.id.send_request){
			//�Լ�д�ķ���
			//sendRequestWithHttpURLConnection();  //ʹ��HttpURLConnection
			sendRequestWithHttpClient();    //ʹ��HttpClient
		}
	}

	//ʹ��HttpClient
	private void sendRequestWithHttpClient() {
		//�����߳���ʹ��HttpClient����һ��HTTP����
		new Thread(new Runnable(){
			@Override
			public void run() {
				try {
					//HttpClient��һ���ӿ�,����޷���������ʵ��,ͨ�������,���ᴴ��һ��DefaultHttpClient��ʵ��
					HttpClient httpClient = new DefaultHttpClient();
					
					//����һ��GET����,��Ҫ����һ��HttpGet����,������Ŀ��������ַ.Ȼ�����HttpClient��execute()����
					//HttpGet httpGet = new HttpGet("http://www.baidu.com");
					
					//ָ�����ʵķ�������ַ�ǵ��Ա���   192.168.43.48�Ǳ�����IP��ַ,�ڵõ��˷��������ص��վݺ�,���ǲ�����
					//ȥ����һ����Ϣ,���ǵ�����parseXMLWithPull()�������������������ص�����
					//192.168.56.1   10.0.2.2
					//HttpGet httpGet = new HttpGet("http://10.0.2.2:8080/get_data.xml");
					HttpGet httpGet = new HttpGet("http://10.0.2.2/get_data.json");
					HttpResponse httpResponse = httpClient.execute(httpGet);
					
					//ȡ�����������ص�״̬��,�������200��˵���������Ӧ���ɹ���
					if(httpResponse.getStatusLine().getStatusCode()==200){
						//ȡ�����񷵻صľ�������
						HttpEntity entity = httpResponse.getEntity();
						//������ת��Ϊ�ַ���,�����ñ���Ϊ UTF-8
						String response = EntityUtils.toString(entity,"utf-8");
						
						//����XML��ʽ
						//parseXMLWithPull(response);   Pull����
						//parseXMLWithSAX(response);      //SAX����
						
						//����JSON��ʽ
						parseJSONWithJSONObject(response);
						
						/*//��Ҫ������Ϣ
						Message message = new Message();
						message.what = SHOW_RESPONSE;
						//�����������صĽ����ŵ�Message(�����obj)��
						message.obj = response.toString();
						handler.sendMessage(message);*/
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
			}

			

			
		}).start();
	}

	//�������������ص�����   JSON��ʽ��    �ùٷ��Ƽ���JSONObject��ʽ
	private void parseJSONWithJSONObject(String jsonData) {
		try {
			//���������ڷ������ж������JSON����,������������ǽ����������ص����ݴ��뵽��һ��JSONArray������
			JSONArray jsonArray = new JSONArray(jsonData);
			//ѭ���������JSONArray,����ȡ����ÿһ��Ԫ�ض���һ��JSONObject����,ÿ��JSONObject�������ְ���id,name,version��Щ����
			//������ֻ��Ҫ����getString()��������Щ����ȡ��.
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
	
	//�������������ص�����   SAX����
	@SuppressWarnings("unused")
	private void parseXMLWithSAX(String xmlData) {
		try {
			//����SAXParserFactory����
			SAXParserFactory factory = SAXParserFactory.newInstance();
			//��ȡ��XMLReader����
			XMLReader xmlReader = factory.newSAXParser().getXMLReader();
			ContentHandler handler = new ContentHandler();
			//��ContentHandler��ʵ�����õ�XMLReader��
			xmlReader.setContentHandler(handler);
			//��ʼ����
			xmlReader.parse(new InputSource(new StringReader(xmlData)));
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	//�������������ص�����    Pull����
	@SuppressWarnings("unused")
	private void parseXMLWithPull(String xmlData) {
		try {
			//������Ҫ��ȡXmlPullParserFactoryʵ��
			XmlPullParserFactory factory = XmlPullParserFactory.newInstance();
			//ͨ��XmlPullParserFactoryʵ���õ�XmlPullParser����
			XmlPullParser xmlPullParser = factory.newPullParser();
			//setInput()�����������ص��������ý�ȥ�Ϳ��Կ�ʼ������
			xmlPullParser.setInput(new StringReader(xmlData));
			//ͨ��getEventType()���Եõ���ǰ�Ľ����¼�,Ȼ����whileѭ���в��ϵؽ��н���,����next()�������Ի�ȡ��һ�������¼�
			int eventType = xmlPullParser.getEventType();
			String id = "";
			String name = "";
			String version = "";
			while(eventType!=XmlPullParser.END_DOCUMENT){
				//getName():�õ���ǰ��������
				String nodeName = xmlPullParser.getName();
				switch (eventType) {
				//��ʼ����ĳ�����
				case XmlPullParser.START_TAG:
					//������ֽ��������id,name,version,�͵���nextText()��������ȡ����о��������,ÿ��������һ��app����
					//�ͽ���ȡ�������ݽ�������
					if("id".equals(nodeName)){
						id = xmlPullParser.nextText();
					} else if ("name".equals(nodeName)){
						name = xmlPullParser.nextText();
					} else if ("version".equals(nodeName)){
						version = xmlPullParser.nextText();
					}
					break;
				//��ɽ���ĳ�����
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
	
	//ʹ��HttpURLConnection
	@SuppressWarnings("unused")
	private void sendRequestWithHttpURLConnection() {
		//�����߳���������������
		new Thread(new Runnable(){
			//ʵ��run()����
			@Override
			public void run() {
				HttpURLConnection connection = null;
				try {
					//��ȡHttpURLConnection����
					URL url = new URL("http://www.baidu.com");
					connection = (HttpURLConnection)url.openConnection(); //����һ��HTTP����
					
					/*
					 * ����HTTP������ʹ�õķ���:���õķ�����Ҫ��2��,GET��POST.GET��ʾϣ���ӷ����������ȡ����,
					 * ��POST���ʾϣ���ύ���ݸ�������
					 * */
					connection.setRequestMethod("GET");  //����HTTP����
					connection.setConnectTimeout(8000);  //�������ӳ�ʱ��8000����
					connection.setReadTimeout(8000);     //��ȡ��ʱ
					
					//getInputStream():��ȡ�����������ص�������
					InputStream in = connection.getInputStream();
					//����Ի�ȡ�������������ж�ȡ   InputStreamReader:���ֽ���ת�����ַ���  BufferedReader:���ַ����ж�ȡ�ı�
					BufferedReader reader = new BufferedReader(new InputStreamReader(in));
					
					//���ж�ȡ����
					StringBuilder response = new StringBuilder();
					String line;
					while((line=reader.readLine())!=null){
						response.append(line);
					}
					
					//ʵ����Message����,����������Ϣ
					Message message = new Message();
					message.what = SHOW_RESPONSE;
					
					//�����������صĽ����ŵ�Message��
					message.obj = response.toString();
					handler.sendMessage(message);  //����Message
					
				} catch (Exception e) {
					e.printStackTrace();
				} finally {
					if(connection!=null){
						connection.disconnect();    //�ǵùر�HTTP����
					}
				}
			}
		}).start();   //�����߳�
	}
}
