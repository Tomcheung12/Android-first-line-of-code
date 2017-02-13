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
 * 2016��8��3��20:57:25
 * 
 * ������Ƶ
 * 
 * @author XFHY
 * 
 *         ������Ƶ�ļ���Ҫ��ʹ��VideoView����ʵ��,����ཫ��Ƶ����ʾ�Ϳ��Ƽ���һ��.
 *         
 *       ע��:VideoView������һ�����ܵ���Ƶ���Ź�����,������Ƶ��ʽ��֧���Լ�����Ч�ʷ��涼�����Žϴ�Ĳ���.
 *       ����,��������ʹ��VideoView�ͱ�д��һ������ǿ�����Ƶ�������ǲ�̫��ʵ��,����,���ֻ�����ڲ���һЩ��Ϸ��Ƭͷ����
 *       ,����ĳ��Ӧ�õ���Ƶ����,ʹ��VideoView���Ǵ´������.
 * 
 * ������ʱ��,onDestroy(),һ��Ҫд�����´���,�ر���Դ
 * if(videoView!=null){
			videoView.suspend();  //��VideoView��ռ�õ���Դ�ͷŵ�
		}
 */
public class MainActivity extends Activity implements OnClickListener {

	private VideoView videoView = null;
	private Button play; // ����
	private Button pause; // ��ͣ
	private Button replay; // ���²���

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
		
		initVideoPath();  //��ʼ����Ƶ
	}

	//��ʼ����Ƶ
	private void initVideoPath(){
		File file = new File(Environment.getExternalStorageDirectory(),"movie.mp4");
		videoView.setVideoPath(file.getPath());   //ָ����Ƶ�ļ���·��
	}
	
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.play:
			if(!videoView.isPlaying()){  //���û���ڲ���
				videoView.start();  //��ʼ����
			}
			break;
		case R.id.pause:
			if(videoView.isPlaying()){  //������ڲ���
				videoView.pause();    //��ͣ����
			}
			break;
		case R.id.replay:
			if(videoView.isPlaying()){  //������ڲ���
				videoView.resume();   //���²���
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
			videoView.suspend();  //��VideoView��ռ�õ���Դ�ͷŵ�
		}
	}
}
