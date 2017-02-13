package com.example.qqsliding.view;

import com.example.qqsliding.R;

import android.content.Context;
import android.content.res.TypedArray;
import android.util.AttributeSet;
import android.util.DisplayMetrics;
import android.util.Log;
import android.util.TypedValue;
import android.view.MotionEvent;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;

/**
 * 2016年8月29日14:57:03 继承自HorizontalScrollView
 * 
 * @author XFHY
 * 
 *         自定义ViewGroup 
 *         1.onMeasure 决定内部View(子View)的宽和高,以及自己的宽和高 
 *         2.onLayout  决定子View的放置的位置
 *         3.onTouchEvent 监听用户手指的滑动状态,按下,抬起,或者移动
 * 
 */
public class SlidingMenu extends HorizontalScrollView {

	private LinearLayout mWapper;
	private ViewGroup mMenu;
	private ViewGroup mContent;
	private int mScreenWidth;

	private int mMenuWidth;
	// dp
	private int mMenuRightPadding = 50;

	//是否是第一次调用onMeasure()
	private boolean once = false; 
	
	public SlidingMenu(Context context) {
		this(context, null);
	}

	public SlidingMenu(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * 当使用了自定义属性时,会调用此构造方法
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		//获取我们定义的属性
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SlidingMenu,
				defStyleAttr, 0);
		
		int n = a.getIndexCount();   //得到对应属性的个数
		for (int i = 0; i < n; i++) {
			int attr = a.getIndex(i);
			switch (attr) {
			case R.styleable.SlidingMenu_rightPadding:
				mMenuRightPadding = a.getDimensionPixelSize(attr, 
						(int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 50, context
						.getResources().getDisplayMetrics()));
				break;

			default:
				break;
			}
		}
		
		a.recycle();  //TypedArray的实例用完需要recycle()释放一下
		
		/*----------获取屏幕的宽度(单位是像素px)--------------*/
		WindowManager wm = (WindowManager) context
				.getSystemService(Context.WINDOW_SERVICE);
		DisplayMetrics outMetrics = new DisplayMetrics();
		wm.getDefaultDisplay().getMetrics(outMetrics);
		mScreenWidth = outMetrics.widthPixels;

	}
	
	@Override
	protected void onMeasure(int widthMeasureSpec, int heightMeasureSpec) {
		if(!once){
			mWapper = (LinearLayout) getChildAt(0);
			mMenu = (ViewGroup) mWapper.getChildAt(0);
			mContent = (ViewGroup) mWapper.getChildAt(1);
			
			mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;  //设置宽度(屏幕宽度-距离右边的边距)
			mContent.getLayoutParams().width = mScreenWidth; 
			once = true;
		}
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	/**
	 * 通过设置偏移量,将menu隐藏
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		
		super.onLayout(changed, l, t, r, b);
		if(changed){
			this.scrollTo(mMenuWidth, 0);  //将menu进行隐藏
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_UP:   //用户手指抬起
			//隐藏在左边的宽度
			int scrollX = getScrollX();  //Return the scrolled left position of this view.
			
			//当用户抬起手指时,左边隐藏的宽度如果大于菜单宽度的一半就隐藏菜单(相当于左边隐藏的宽度一半未出现在屏幕中)
			if(scrollX >= (mMenuWidth/2)){  //如果左边的宽度大于菜单的宽度的一半
				//慢慢地移动  Like scrollTo, but scroll smoothly instead of immediately.
				this.smoothScrollTo(mMenuWidth, 0);  //隐藏
				Log.d("xfhy","隐藏菜单");
			} else {   //滑动的距离小于菜单的一半就完全显示
				this.smoothScrollTo(0, 0);  //完全显示
				Log.d("xfhy","完全显示菜单");
			}
			Log.d("xfhy",String.valueOf(scrollX));
			return true;

		default:
			break;
		}
		
		return super.onTouchEvent(ev);
	}
	
}
