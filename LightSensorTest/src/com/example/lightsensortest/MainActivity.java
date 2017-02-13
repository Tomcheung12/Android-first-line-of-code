package com.example.lightsensortest;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;

/**
 * 2016��8��17��10:49:32
 * �������׹���̽����,ʹ���ֻ����Լ�⵽��Χ�����Ĺ���ǿ��.
 * @author XFHY 
 * 
 * Android��ÿ�����������÷���ʵ���Ƚ�����
 * 
 * ���ȵ�һ��Ҫ��ȡ��SensorManager��ʵ��,SensorManager��ϵͳ���д������Ĺ�����.��������ʵ��֮��Ϳ��Ե���getDefaultSensor()
 * �������õ�����Ĵ�����������.���ڵõ���Sensorʵ��,������Ҫ�Դ�����������źŽ��м���,���Ҫ����SensorEventListener��ʵ����.
 * 
 */
public class MainActivity extends Activity {

	private TextView lightLevel;
	private SensorManager sensorManager;  //SensorManager��ϵͳ���д������Ĺ�����
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        lightLevel = (TextView)findViewById(R.id.light_level);
        //���SensorManagerʵ��
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        //����SensorManager��getDefaultSensor() �������õ����յĴ�����
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        
        //ע�ᴫ�����ļ�����  ����:������,Sensorʵ��(������),����Ƶ��
        //����Ƶ�����ε���:SENSOR_DELAY_UI,SENSOR_DELAY_NORMAL,SENSOR_DELAY_GAME,SENSOR_DELAY_FASTEST
        sensorManager.registerListener(listener, sensor,SensorManager.SENSOR_DELAY_UI);
    }
    
    //�������˳��򴫸���ʹ�����ʱ,һ��Ҫ����unregisterListener()������ʹ�õ���Դ�ͷŵ�
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	if(sensorManager != null){
    		sensorManager.unregisterListener(listener);
    	}
    }
    
    //ע�ᴫ�����ļ�����
    private SensorEventListener listener = new SensorEventListener(){

    	//����������⵽����ֵ�����仯ʱ����  
    	//���SensorEvent�Ĳ���event�а�����һ��values����,���д������������Ϣ���Ǵ���������
		@Override
		public void onSensorChanged(SensorEvent event) {
			//������ǿ�ȷ����仯ʱ���ô˷���
			
			//values�����е�һ���±��ֵ���ǵ�ǰ�Ĺ���ǿ��
			float value = event.values[0];    //��ȡ��һ��ֵ
			lightLevel.setText("Current light level is "+value+" lx");
		}

		//���������ľ��ȷ����仯ʱ����
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			
		}};
}
