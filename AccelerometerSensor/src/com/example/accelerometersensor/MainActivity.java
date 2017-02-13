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
 * 2016年8月17日11:37:47
 * 
 * 利用加速度传感器来模仿微信的摇一摇功能
 * 
 * @author XFHY
 * 
 * 主体实现逻辑:只需要检测手机在X轴,Y轴,Z轴上的加速度,当达到了预定的值就认为是用户摇动了手机,从而触发摇一摇的逻辑
 * 由于重力加速度的存在,即使手机静止不动,某一个轴的加速度也有可能达到9.8m/s^2 ,因此这个预定值是要大于9.8的
 * 这里我们设置成15m/s^2
 * 
 */
public class MainActivity extends Activity {

	private SensorManager sensorManager;
	
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        //获得传感器管理器
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        //获得加速度的传感器
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_ACCELEROMETER);
        //注册传感器的监听器  参数:监听器,Sensor实例(传感器),更新频率
        //更新频率依次递增:SENSOR_DELAY_UI,SENSOR_DELAY_NORMAL,SENSOR_DELAY_GAME,SENSOR_DELAY_FASTEST
        sensorManager.registerListener(listener, sensor,SensorManager.SENSOR_DELAY_NORMAL);
    }
    
    //当程序退出或传感器使用完毕时,一定要调用unregisterListener()方法将使用的资源释放掉
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	if(sensorManager != null){
    		sensorManager.unregisterListener(listener);
    	}
    }
    
    ////注册传感器的监听器
    private SensorEventListener listener = new SensorEventListener(){

    	//当传感器检测到的数值发生变化时调用  
    	//这个SensorEvent的参数event中包含了一个values数组,所有传感器输出的信息都是存放在这里的
		@Override
		public void onSensorChanged(SensorEvent event) {
			//加速度可能是负值,所以需要取绝对值
			float xValue = Math.abs(event.values[0]);
			float yValue = Math.abs(event.values[1]);
			float zValue = Math.abs(event.values[2]);
			
			if(xValue > 16 || yValue > 16 || zValue > 16){
				//认为用户摇动了手机,触发摇一摇逻辑
				Toast.makeText(MainActivity.this, "摇啊摇,摇到外婆桥", Toast.LENGTH_SHORT).show();
			}
		}

		//当传感器的精度发生变化时调用
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			
		}};
}
