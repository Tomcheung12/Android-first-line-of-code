package com.example.accelerometersensor;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.widget.Toast;

/**
 * 2016��8��17��11:37:47
 * 
 * ���ü��ٶȴ�������ģ��΢�ŵ�ҡһҡ����
 * 
 * @author XFHY
 * 
 * ����ʵ���߼�:ֻ��Ҫ����ֻ���X��,Y��,Z���ϵļ��ٶ�,���ﵽ��Ԥ����ֵ����Ϊ���û�ҡ�����ֻ�,�Ӷ�����ҡһҡ���߼�
 * �����������ٶȵĴ���,��ʹ�ֻ���ֹ����,ĳһ����ļ��ٶ�Ҳ�п��ܴﵽ9.8m/s^2 ,������Ԥ��ֵ��Ҫ����9.8��
 * �����������ó�15m/s^2
 * 
 */
public class MainActivity extends Activity {

	private SensorManager sensorManager;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //��ô�����������
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        //��ü��ٶȵĴ�����
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //ע�ᴫ�����ļ�����  ����:������,Sensorʵ��(������),����Ƶ��
        //����Ƶ�����ε���:SENSOR_DELAY_UI,SENSOR_DELAY_NORMAL,SENSOR_DELAY_GAME,SENSOR_DELAY_FASTEST
        sensorManager.registerListener(listener, sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }
    
    //�������˳��򴫸���ʹ�����ʱ,һ��Ҫ����unregisterListener()������ʹ�õ���Դ�ͷŵ�
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	if(sensorManager != null){
    		sensorManager.unregisterListener(listener);
    	}
    }
    
    ////ע�ᴫ�����ļ�����
    private SensorEventListener listener = new SensorEventListener(){

    	//����������⵽����ֵ�����仯ʱ����  
    	//���SensorEvent�Ĳ���event�а�����һ��values����,���д������������Ϣ���Ǵ���������
		@Override
		public void onSensorChanged(SensorEvent event) {
			//���ٶȿ����Ǹ�ֵ,������Ҫȡ����ֵ
			float xValue = Math.abs(event.values[0]);
			float yValue = Math.abs(event.values[1]);
			float zValue = Math.abs(event.values[2]);
			
			if(xValue > 16 || yValue > 16 || zValue > 16){
				//��Ϊ�û�ҡ�����ֻ�,����ҡһҡ�߼�
				Toast.makeText(MainActivity.this, "ҡ��ҡ,ҡ��������", Toast.LENGTH_SHORT).show();
			}
		}

		//���������ľ��ȷ����仯ʱ����
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			
		}};
}
