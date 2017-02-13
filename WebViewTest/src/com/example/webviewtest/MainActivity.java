package com.example.webviewtest;

import android.app.Activity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.webkit.WebView;
import android.webkit.WebViewClient;

/**
 * 2016年8月8日8:50:33
 * 
 * 使用WebView控件
 * 
 * @author XFHY
 * 
 * WebView控件,借助它我们就可以在自己的应用程序中嵌入一个浏览器,从而轻松地展示各种各样的网页.因为使用到了网络功能,所以需要声明权限
 * 
 * WebView控件已经在后台帮我们处理好了发送HTTP请求,接收服务器响应,解析返回数据,以及最终的页面展示着几步工作.
 * 
 */
public class MainActivity extends Activity {

	private WebView webView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        webView = (WebView)findViewById(R.id.web_view);
        //getSettings():设置浏览器属性    setJavaScriptEnabled():让WebView支持JavaScript脚本
        webView.getSettings().setJavaScriptEnabled(true);
        //当需要从一个网页跳转到另一个网页时,我们希望目标网页仍然在当前WebView中显示,而不是打开系统浏览器
        webView.setWebViewClient(new WebViewClient());
        //将网址传入,即可展示相应网页的内容
        webView.loadUrl("http://www.baidu.com");
    }
}
