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
 * 2016年8月18日7:59:59
 * 
 * 简易指南针
 * 
 * @author XFHY
 * 
 * 原理:只需要检测到手机围绕Z轴的旋转角度,然后对这个值进行处理就可以了.
 * 
 * 这里需要用到方向传感器,方向传感器TYPE_ORIENTATION已经被弃用.
 * Android获取手机旋转的方向和角度是通过加速度传感器和地磁传感器共同计算出来的.
 * 
 */
public class MainActivity extends Activity {

	private SensorManager sensorManager;
	private ImageView compassImg;   //指南针背景图
	
	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		
		compassImg = (ImageView)findViewById(R.id.compass_img);
		
		//获取传感器管理器
		sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
		
		//地磁传感器
		Sensor magneticSensor = sensorManager.getDefaultSensor(Sensor.TYPE_MAGNETIC_FIELD);
		//加速度传感器
		Sensor accelerometerSensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
		
		//注册传感器的监听器  参数:监听器,Sensor实例(传感器),更新频率
        //更新频率依次递增:SENSOR_DELAY_UI,SENSOR_DELAY_NORMAL,SENSOR_DELAY_GAME,SENSOR_DELAY_FASTEST
		//由于方向传感器的精准度通常都比较高,这里把更新频率提高一些.
		sensorManager.registerListener(listener, magneticSensor,SensorManager.SENSOR_DELAY_GAME);
		sensorManager.registerListener(listener, accelerometerSensor,SensorManager.SENSOR_DELAY_GAME);
	}

	//销毁活动时,一定要记得取消注册监听器
	@Override
	protected void onDestroy() {
		super.onDestroy();
		if(sensorManager != null){
			sensorManager.unregisterListener(listener);
		}
	}
	
	//监听器  公用的监听器
	private SensorEventListener listener = new SensorEventListener(){

		float[] accelerometerValues = new float[3];
		float[] magneticValues = new float[3];
		private float lastRotatedegree;  //上一次的角度       初始值是0.0
		
		//当传感器检测到的数值发生变化时调用  
    	//这个SensorEvent的参数event中包含了一个values数组,分别记录着加速度传感器和地磁传感器输出的值
		@Override
		public void onSensorChanged(SensorEvent event) {
			//判断当前是加速度还是地磁传感器
			if(event.sensor.getType() == Sensor.TYPE_ACCELEROMETER){
				//注意赋值时要调用clone()方法
				accelerometerValues = event.values.clone();   //clone(): 创建并返回此对象的一个副本
			} else if(event.sensor.getType() == Sensor.TYPE_MAGNETIC_FIELD){
				magneticValues = event.values.clone();
			}
			
			float[] R = new float[9];
			float[] values = new float[3];
			
			//将记录着加速度传感器和地磁传感器输出的值的2个float数组传入SensorManager的getRotationMatrix()方法
			//就可以得到一个包含旋转矩阵的R数组,R数组的长度为9,getRotationMatrix()计算出的旋转数据就会放到这数组里面
			SensorManager.getRotationMatrix(R, null, accelerometerValues, magneticValues);
			
			//SensorManager的getOrientation()方法用于计算手机的旋转数据
			//values是一个长度为3的float数组,手机在各个方向上的旋转数据都会存放到这个数组中.其中values[0]记录着手机围绕着Z轴旋转
			//弧度,values[1]记录着手机围绕X轴的旋转弧度,values[2]记录着手机围绕Y轴的旋转弧度
			SensorManager.getOrientation(R, values);
			
			//注意:上面计算出的数据都是以弧度为单位的,因此想转为角度的话还需要调用Math.toDegrees()方法
			Log.d("xfhy","value[0] is "+Math.toDegrees(values[0]));  //这里输出的是围绕Z轴的旋转角度
			//values[0]的取值范围是-180度到180度,其中±180度表示正南方向,0度表示正北方向,-90度表示正西方向,90度表示正东方向
			
			   /*--------------------将计算出的旋转角度取反,用于旋转 指南针背景图----------------------------*/
			float rotateDegree = -(float)Math.toDegrees(values[0]);   
			if( Math.abs(rotateDegree - lastRotatedegree) > 1 ){ //现在的角度与上一次的角度之差
				//这里使用到了旋转动画技术
				/**
				 * 创建了一个RotateAnimation实例,并传入6个参数
				 * 参数:旋转的起始角度,旋转的终止角度,后面四个参数用于指定旋转的中心点
				 * 这里是相对于自己自转
				 * 
				 * RotateAnimation (float fromDegrees, float toDegrees, int pivotXType, 
				 * float pivotXValue, int pivotYType, float pivotYValue) 
					参数说明： 
					float fromDegrees：旋转的开始角度。 
					float toDegrees：旋转的结束角度。 
					int pivotXType：X轴的伸缩模式，可以取值为ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT。
					float pivotXValue：X坐标的伸缩值。 
					int pivotYType：Y轴的伸缩模式，可以取值为ABSOLUTE、RELATIVE_TO_SELF、RELATIVE_TO_PARENT。
					float pivotYValue：Y坐标的伸缩值
				 */
				RotateAnimation animation = new RotateAnimation(lastRotatedegree, rotateDegree, 
						Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
				animation.setFillAfter(true);   //动画执行时动作是持续存在的
				compassImg.startAnimation(animation);   //开始动画
				lastRotatedegree = rotateDegree;    //记录这次的角度
			}
		}

		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			
		}};
	
}
