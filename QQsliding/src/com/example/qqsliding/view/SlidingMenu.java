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
 * 2016��8��29��14:57:03 �̳���HorizontalScrollView
 * 
 * @author XFHY
 * 
 *         �Զ���ViewGroup 
 *         1.onMeasure �����ڲ�View(��View)�Ŀ�͸�,�Լ��Լ��Ŀ�͸� 
 *         2.onLayout  ������View�ķ��õ�λ��
 *         3.onTouchEvent �����û���ָ�Ļ���״̬,����,̧��,�����ƶ�
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

	//�Ƿ��ǵ�һ�ε���onMeasure()
	private boolean once = false; 
	
	public SlidingMenu(Context context) {
		this(context, null);
	}

	public SlidingMenu(Context context, AttributeSet attrs) {
		this(context, attrs, 0);
	}

	/**
	 * ��ʹ�����Զ�������ʱ,����ô˹��췽��
	 * @param context
	 * @param attrs
	 * @param defStyleAttr
	 */
	public SlidingMenu(Context context, AttributeSet attrs, int defStyleAttr) {
		super(context, attrs, defStyleAttr);

		//��ȡ���Ƕ��������
		TypedArray a = context.getTheme().obtainStyledAttributes(attrs, R.styleable.SlidingMenu,
				defStyleAttr, 0);
		
		int n = a.getIndexCount();   //�õ���Ӧ���Եĸ���
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
		
		a.recycle();  //TypedArray��ʵ��������Ҫrecycle()�ͷ�һ��
		
		/*----------��ȡ��Ļ�Ŀ��(��λ������px)--------------*/
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
			
			mMenuWidth = mMenu.getLayoutParams().width = mScreenWidth - mMenuRightPadding;  //���ÿ��(��Ļ���-�����ұߵı߾�)
			mContent.getLayoutParams().width = mScreenWidth; 
			once = true;
		}
		
		super.onMeasure(widthMeasureSpec, heightMeasureSpec);
	}

	/**
	 * ͨ������ƫ����,��menu����
	 */
	@Override
	protected void onLayout(boolean changed, int l, int t, int r, int b) {
		
		super.onLayout(changed, l, t, r, b);
		if(changed){
			this.scrollTo(mMenuWidth, 0);  //��menu��������
		}
	}
	
	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		int action = ev.getAction();
		switch (action) {
		case MotionEvent.ACTION_UP:   //�û���ָ̧��
			//��������ߵĿ��
			int scrollX = getScrollX();  //Return the scrolled left position of this view.
			
			//���û�̧����ָʱ,������صĿ��������ڲ˵���ȵ�һ������ز˵�(�൱��������صĿ��һ��δ��������Ļ��)
			if(scrollX >= (mMenuWidth/2)){  //�����ߵĿ�ȴ��ڲ˵��Ŀ�ȵ�һ��
				//�������ƶ�  Like scrollTo, but scroll smoothly instead of immediately.
				this.smoothScrollTo(mMenuWidth, 0);  //����
				Log.d("xfhy","���ز˵�");
			} else {   //�����ľ���С�ڲ˵���һ�����ȫ��ʾ
				this.smoothScrollTo(0, 0);  //��ȫ��ʾ
				Log.d("xfhy","��ȫ��ʾ�˵�");
			}
			Log.d("xfhy",String.valueOf(scrollX));
			return true;

		default:
			break;
		}
		
		return super.onTouchEvent(ev);
	}
	
}
