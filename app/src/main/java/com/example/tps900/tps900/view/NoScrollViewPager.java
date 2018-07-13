package com.example.tps900.tps900.view;

import android.content.Context;
import android.support.v4.view.ViewPager;
import android.util.AttributeSet;
import android.view.MotionEvent;

public class NoScrollViewPager extends ViewPager {

	public NoScrollViewPager(Context context, AttributeSet attrs) {
		super(context, attrs);
	}

	public NoScrollViewPager(Context context) {
		super(context);
	}

	@Override
	public boolean onInterceptTouchEvent(MotionEvent ev) {
		return false;//不拦截事件, 交给子控件处理
	}

	@Override
	public boolean onTouchEvent(MotionEvent ev) {
		//重写触摸事件, 此处什么都不做, 从而禁用掉了ViewPager原生的触摸效果
		return true;
	}

}
