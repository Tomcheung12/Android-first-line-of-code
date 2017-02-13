package com.example.fragmentbestpractice;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.Window;

/**
 * 作为新闻内容的活动Activity
 * 
 * @author XFHY
 * 
 */
public class NewsContentActivity extends Activity {
	
	//启动活动的最佳写法,在本活动中写该方法,自己启动自己,然后还可以得到数据
	public static void actionStart(Context context,String newsTitle,String newsContent){
		Intent intent = new Intent(context,NewsContentActivity.class);
		intent.putExtra("news_title", newsTitle);
		intent.putExtra("news_content", newsContent);
		context.startActivity(intent);
	}
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		requestWindowFeature(Window.FEATURE_NO_TITLE);
		setContentView(R.layout.news_content);
		// 获取传入的新闻标题
		String newsTitle = getIntent().getStringExtra("news_title");
		// 获取传入的新闻标题
		String newsContent = getIntent().getStringExtra("news_content");
		NewsContentFragment newsContentFragment = (NewsContentFragment) getFragmentManager()
				.findFragmentById(R.id.news_content_fragment);
		
		//刷新NewsContentFragment界面
		newsContentFragment.refresh(newsTitle, newsContent);
	}
}
