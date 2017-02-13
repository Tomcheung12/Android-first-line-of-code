package com.example.playaudiotest;

import java.io.File;

import android.app.Activity;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;

/**
 * 2016年8月3日11:06:01
 * 
 * 播放音频(本程序事先在SD卡根目录放了个文件 "music.mp3")
 * 
 * @author XFHY
 *
 * 在Android播放音频文件一般都是使用MediaPlayer类来实现,它对多种格式的音频文件提供了非常全面的控制方法
 * ,从而使得播放音乐的工作变得十分简单.下面是MediaPlayer类中一些较为常用的控制方法.
 * 
 * 
 *
 */
public class MainActivity extends Activity implements OnClickListener {

	private Button play = null;
	private Button pause = null;
	private Button stop = null;
	private MediaPlayer mediaPlayer = new MediaPlayer();

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		play = (Button) findViewById(R.id.play);
		pause = (Button) findViewById(R.id.pause);
		stop = (Button) findViewById(R.id.stop);

		play.setOnClickListener(this);
		pause.setOnClickListener(this);
		stop.setOnClickListener(this);

		initMediaPlayer(); // 初始化MediaPlayer
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(mediaPlayer!=null){
			mediaPlayer.stop();     //停止播放音频,调用这个方法后的MediaPlayer对象无法再播放音频
			mediaPlayer.release();  //释放掉与MediaPlayer对象相关的资源
		}
	}
	
	//初始化MediaPlayer对象
	private void initMediaPlayer() {
		try {
			// 打开文件
			File file = new File(Environment.getExternalStorageDirectory(),
					"music.mp3");
			mediaPlayer.setDataSource(file.getPath()); // 设置要播放的音频文件的位置
			mediaPlayer.prepare(); // 在开始播放之前调用这个方法完成准备工作
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.play:
			if(!mediaPlayer.isPlaying()){  //判断当前MediaPlayer是否正在播放音频
				mediaPlayer.start();
			}
			break;
		case R.id.pause:
			if(mediaPlayer.isPlaying()){   //如果正在播放,则暂停
				mediaPlayer.pause();
			}
			break;
		case R.id.stop:
			if(mediaPlayer.isPlaying()){
				mediaPlayer.reset();  //停止播放  将MediaPlayer对象重置到刚刚创建的状态
				initMediaPlayer();    //初始化MediaPlayer
			}
			break;

		default:
			break;
		}
	}
}
