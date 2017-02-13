package com.example.compasstest;

import android.app.Activity;
import android.content.Context;
import android.hardware.Sensor;
import android.hardware.SensorEvent;
import android.hardware.SensorEventListener;
import android.hardware.SensorManager;
import android.os.Bundle;
import android.util.Log;
import android.view.animation.Animation;
import android.view.animation.RotateAnimation;
import android.widget.ImageView;

/**
 * 2016��8��18��7:59:59
 * 
 * ����ָ����
 * 
 * @author XFHY
 * 
 * ԭ��:ֻ��Ҫ��⵽�ֻ�Χ��Z�����ת�Ƕ�,Ȼ������ֵ���д���Ϳ�����.
 * 
 * ������Ҫ�õ����򴫸���,���򴫸���TYPE_ORIENTATION�Ѿ�������.
 * Android��ȡ�ֻ���ת�ķ���ͽǶ���ͨ�����ٶȴ������͵شŴ�������ͬ���������.
 * 
 */
public class MainActivity extends Activity {

	private SensorManager sensorManager;
	private ImageView compassImg;   //ָ���뱳��ͼ
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		compassImg = (ImageView)findViewById(R.id.compass_img);
		
		//��ȡ������������
		sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		
		//�شŴ�����
		Sensor magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		//���ٶȴ�����
		Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		//ע�ᴫ�����ļ�����  ����:������,Sensorʵ��(������),����Ƶ��
        //����Ƶ�����ε���:SENSOR_DELAY_UI,SENSOR_DELAY_NORMAL,SENSOR_DELAY_GAME,SENSOR_DELAY_FASTEST
		//���ڷ��򴫸����ľ�׼��ͨ�����Ƚϸ�,����Ѹ���Ƶ�����һЩ.
		sensorManager.registerListener(listener, magneticSensor,SensorManager.SENSOR_DELAY_GAME);
		sensorManager.registerListener(listener, accelerometerSensor,SensorManager.SENSOR_DELAY_GAME);
	}

	//���ٻʱ,һ��Ҫ�ǵ�ȡ��ע�������
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(sensorManager != null){
			sensorManager.unregisterListener(listener);
		}
	}
	
	//������  ���õļ�����
	private SensorEventListener listener = new SensorEventListener(){

		float[] accelerometerValues = new float[3];
		float[] magneticValues = new float[3];
		private float lastRotatedegree;  //��һ�εĽǶ�       ��ʼֵ��0.0
		
		//����������⵽����ֵ�����仯ʱ����  
    	//���SensorEvent�Ĳ���event�а�����һ��values����,�ֱ��¼�ż��ٶȴ������͵شŴ����������ֵ
		@Override
		public void onSensorChanged(SensorEvent event) {
			//�жϵ�ǰ�Ǽ��ٶȻ��ǵشŴ�����
			if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
				//ע�⸳ֵʱҪ����clone()����
				accelerometerValues = event.values.clone();   //clone(): ���������ش˶����һ������
			} else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
				magneticValues = event.values.clone();
			}
			
			float[] R = new float[9];
			float[] values = new float[3];
			
			//����¼�ż��ٶȴ������͵شŴ����������ֵ��2��float���鴫��SensorManager��getRotationMatrix()����
			//�Ϳ��Եõ�һ��������ת�����R����,R����ĳ���Ϊ9,getRotationMatrix()���������ת���ݾͻ�ŵ�����������
			SensorManager.getRotationMatrix(R, null, accelerometerValues, magneticValues);
			
			//SensorManager��getOrientation()�������ڼ����ֻ�����ת����
			//values��һ������Ϊ3��float����,�ֻ��ڸ��������ϵ���ת���ݶ����ŵ����������.����values[0]��¼���ֻ�Χ����Z����ת
			//����,values[1]��¼���ֻ�Χ��X�����ת����,values[2]��¼���ֻ�Χ��Y�����ת����
			SensorManager.getOrientation(R, values);
			
			//ע��:�������������ݶ����Ի���Ϊ��λ��,�����תΪ�ǶȵĻ�����Ҫ����Math.toDegrees()����
			Log.d("xfhy","value[0] is "+Math.toDegrees(values[0]));  //�����������Χ��Z�����ת�Ƕ�
			//values[0]��ȡֵ��Χ��-180�ȵ�180��,���С�180�ȱ�ʾ���Ϸ���,0�ȱ�ʾ��������,-90�ȱ�ʾ��������,90�ȱ�ʾ��������
			
			   /*--------------------�����������ת�Ƕ�ȡ��,������ת ָ���뱳��ͼ----------------------------*/
			float rotateDegree = -(float)Math.toDegrees(values[0]);   
			if( Math.abs(rotateDegree - lastRotatedegree) > 1 ){ //���ڵĽǶ�����һ�εĽǶ�֮��
				//����ʹ�õ�����ת��������
				/**
				 * ������һ��RotateAnimationʵ��,������6������
				 * ����:��ת����ʼ�Ƕ�,��ת����ֹ�Ƕ�,�����ĸ���������ָ����ת�����ĵ�
				 * ������������Լ���ת
				 * 
				 * RotateAnimation (float fromDegrees, float toDegrees, int pivotXType, 
				 * float pivotXValue, int pivotYType, float pivotYValue) 
					����˵���� 
					float fromDegrees����ת�Ŀ�ʼ�Ƕȡ� 
					float toDegrees����ת�Ľ����Ƕȡ� 
					int pivotXType��X�������ģʽ������ȡֵΪABSOLUTE��RELATIVE_TO_SELF��RELATIVE_TO_PARENT��
					float pivotXValue��X���������ֵ�� 
					int pivotYType��Y�������ģʽ������ȡֵΪABSOLUTE��RELATIVE_TO_SELF��RELATIVE_TO_PARENT��
					float pivotYValue��Y���������ֵ
				 */
				RotateAnimation animation = new RotateAnimation(lastRotatedegree, rotateDegree, 
						Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
				animation.setFillAfter(true);   //����ִ��ʱ�����ǳ������ڵ�
				compassImg.startAnimation(animation);   //��ʼ����
				lastRotatedegree = rotateDegree;    //��¼��εĽǶ�
			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			
		}};
	
}
