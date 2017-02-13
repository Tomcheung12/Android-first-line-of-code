package com.example.webviewtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 2016��8��8��8:50:33
 * 
 * ʹ��WebView�ؼ�
 * 
 * @author XFHY
 * 
 * WebView�ؼ�,���������ǾͿ������Լ���Ӧ�ó�����Ƕ��һ�������,�Ӷ����ɵ�չʾ���ָ�������ҳ.��Ϊʹ�õ������繦��,������Ҫ����Ȩ��
 * 
 * WebView�ؼ��Ѿ��ں�̨�����Ǵ�����˷���HTTP����,���շ�������Ӧ,������������,�Լ����յ�ҳ��չʾ�ż�������.
 * 
 */
public class MainActivity extends Activity {

	private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView)findViewById(R.id.web_view);
        //getSettings():�������������    setJavaScriptEnabled():��WebView֧��JavaScript�ű�
        webView.getSettings().setJavaScriptEnabled(true);
        //����Ҫ��һ����ҳ��ת����һ����ҳʱ,����ϣ��Ŀ����ҳ��Ȼ�ڵ�ǰWebView����ʾ,�����Ǵ�ϵͳ�����
        webView.setWebViewClient(new WebViewClient());
        //����ַ����,����չʾ��Ӧ��ҳ������
        webView.loadUrl("http://www.baidu.com");
    }
}
