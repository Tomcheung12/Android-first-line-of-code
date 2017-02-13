package com.example.playvideoview;

import java.io.File;

import android.app.Activity;
import android.os.Bundle;
import android.os.Environment;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.VideoView;

/**
 * 2016年8月3日20:57:25
 * 
 * 播放视频
 * 
 * @author XFHY
 * 
 *         播放视频文件主要是使用VideoView类来实现,这个类将视频的显示和控制集于一身.
 *         
 *       注意:VideoView并不是一个万能的视频播放工具类,它在视频格式的支持以及播放效率方面都存在着较大的不足.
 *       所以,如果想仅仅使用VideoView就编写出一个功能强大的视频播放器是不太现实的,但是,如果只是用于播放一些游戏的片头动画
 *       ,或者某个应用的视频宣传,使用VideoView还是绰绰有余的.
 * 
 * 在最后的时候,onDestroy(),一定要写下如下代码,关闭资源
 * if(videoView!=null){
			videoView.suspend();  //将VideoView所占用的资源释放掉
		}
 */
public class MainActivity extends Activity implements OnClickListener {

	private VideoView videoView = null;
	private Button play; // 播放
	private Button pause; // 暂停
	private Button replay; // 重新播放

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);

		videoView = (VideoView) findViewById(R.id.video_view);
		play = (Button) findViewById(R.id.play);
		pause = (Button) findViewById(R.id.pause);
		replay = (Button) findViewById(R.id.replay);

		play.setOnClickListener(this);
		pause.setOnClickListener(this);
		replay.setOnClickListener(this);
		
		initVideoPath();  //初始化视频
	}

	//初始化视频
	private void initVideoPath(){
		File file = new File(Environment.getExternalStorageDirectory(),"movie.mp4");
		videoView.setVideoPath(file.getPath());   //指定视频文件的路径
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.play:
			if(!videoView.isPlaying()){  //如果没有在播放
				videoView.start();  //开始播放
			}
			break;
		case R.id.pause:
			if(videoView.isPlaying()){  //如果正在播放
				videoView.pause();    //暂停播放
			}
			break;
		case R.id.replay:
			if(videoView.isPlaying()){  //如果正在播放
				videoView.resume();   //重新播放
			}
			break;

		default:
			break;
		}
	}
	
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(videoView!=null){
			videoView.suspend();  //将VideoView所占用的资源释放掉
		}
	}
}
