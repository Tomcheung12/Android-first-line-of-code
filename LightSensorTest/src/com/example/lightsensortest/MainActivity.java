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
 * 2016年8月17日10:49:32
 * 制作简易光照探测器,使得手机可以检测到周围环境的光照强度.
 * @author XFHY 
 * 
 * Android中每个传感器的用法其实都比较类似
 * 
 * 首先第一步要获取到SensorManager的实例,SensorManager是系统所有传感器的管理器.有了它的实例之后就可以调用getDefaultSensor()
 * 方法来得到任意的传感器类型了.现在得到了Sensor实例,我们需要对传感器输出的信号进行监听,这就要借助SensorEventListener来实现了.
 * 
 */
public class MainActivity extends Activity {

	private TextView lightLevel;
	private SensorManager sensorManager;  //SensorManager是系统所有传感器的管理器
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        
        lightLevel = (TextView)findViewById(R.id.light_level);
        //获得SensorManager实例
        sensorManager = (SensorManager)getSystemService(Context.SENSOR_SERVICE);
        //调用SensorManager的getDefaultSensor() 方法来得到光照的传感器
        Sensor sensor = sensorManager.getDefaultSensor(Sensor.TYPE_LIGHT);
        
        //注册传感器的监听器  参数:监听器,Sensor实例(传感器),更新频率
        //更新频率依次递增:SENSOR_DELAY_UI,SENSOR_DELAY_NORMAL,SENSOR_DELAY_GAME,SENSOR_DELAY_FASTEST
        sensorManager.registerListener(listener, sensor,SensorManager.SENSOR_DELAY_UI);
    }
    
    //当程序退出或传感器使用完毕时,一定要调用unregisterListener()方法将使用的资源释放掉
    @Override
    protected void onDestroy() {
    	super.onDestroy();
    	if(sensorManager != null){
    		sensorManager.unregisterListener(listener);
    	}
    }
    
    //注册传感器的监听器
    private SensorEventListener listener = new SensorEventListener(){

    	//当传感器检测到的数值发生变化时调用  
    	//这个SensorEvent的参数event中包含了一个values数组,所有传感器输出的信息都是存放在这里的
		@Override
		public void onSensorChanged(SensorEvent event) {
			//当光照强度发生变化时调用此方法
			
			//values数组中第一个下标的值就是当前的光照强度
			float value = event.values[0];    //获取第一个值
			lightLevel.setText("Current light level is "+value+" lx");
		}

		//当传感器的精度发生变化时调用
		@Override
		public void onAccuracyChanged(Sensor sensor, int accuracy) {
			
		}};
}
