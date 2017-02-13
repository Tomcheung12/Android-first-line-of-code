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
 * 2016��8��3��11:06:01
 * 
 * ������Ƶ(������������SD����Ŀ¼���˸��ļ� "music.mp3")
 * 
 * @author XFHY
 *
 * ��Android������Ƶ�ļ�һ�㶼��ʹ��MediaPlayer����ʵ��,���Զ��ָ�ʽ����Ƶ�ļ��ṩ�˷ǳ�ȫ��Ŀ��Ʒ���
 * ,�Ӷ�ʹ�ò������ֵĹ������ʮ�ּ�.������MediaPlayer����һЩ��Ϊ���õĿ��Ʒ���.
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

		initMediaPlayer(); // ��ʼ��MediaPlayer
	}

	@Override
	protected void onDestroy() {
		// TODO Auto-generated method stub
		super.onDestroy();
		if(mediaPlayer!=null){
			mediaPlayer.stop();     //ֹͣ������Ƶ,��������������MediaPlayer�����޷��ٲ�����Ƶ
			mediaPlayer.release();  //�ͷŵ���MediaPlayer������ص���Դ
		}
	}
	
	//��ʼ��MediaPlayer����
	private void initMediaPlayer() {
		try {
			// ���ļ�
			File file = new File(Environment.getExternalStorageDirectory(),
					"music.mp3");
			mediaPlayer.setDataSource(file.getPath()); // ����Ҫ���ŵ���Ƶ�ļ���λ��
			mediaPlayer.prepare(); // �ڿ�ʼ����֮ǰ��������������׼������
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.play:
			if(!mediaPlayer.isPlaying()){  //�жϵ�ǰMediaPlayer�Ƿ����ڲ�����Ƶ
				mediaPlayer.start();
			}
			break;
		case R.id.pause:
			if(mediaPlayer.isPlaying()){   //������ڲ���,����ͣ
				mediaPlayer.pause();
			}
			break;
		case R.id.stop:
			if(mediaPlayer.isPlaying()){
				mediaPlayer.reset();  //ֹͣ����  ��MediaPlayer�������õ��ոմ�����״̬
				initMediaPlayer();    //��ʼ��MediaPlayer
			}
			break;

		default:
			break;
		}
	}
}
