package com.example.activitytest1;

import java.util.ArrayList;
import java.util.List;

import android.app.Activity;

/**
 * 这是活动的总管理器
 * @author XFHY
 *
 */
public class ActivityCollector {
	// 将所有的活动添加到这里来
	public static List<Activity> activities = new ArrayList<Activity>();

	public static void addActivity(Activity activity) {
		activities.add(activity);
	}

	public static void removeActivity(Activity activity) {
		activities.remove(activities);
	}

	public static void finishAll() // 销毁所有活动
	{
		for (Activity activity : activities) {
			if (!activity.isFinishing()) {
				activity.finish();
			}
		}
	}
}
