package com.ydd.pockettoycatcherrwz.widget;

import android.view.View;

/**
 * 防快速多次点击
 *
 * Created by czhang on 17/5/12.
 */
public abstract class OnMultiClickListener implements View.OnClickListener {

	/**
	 * 两次点击按钮之间的点击间隔不能少于1000毫秒
	 */
	private static final int MIN_CLICK_DELAY_TIME = 1000;
	/**
	 * 上次的点击时间
	 */
	private long mLastClickTime = 0;

	public abstract void onMultiClick(View v);

	@Override
	public void onClick(View v) {
		long curClickTime = System.currentTimeMillis();
		if ((curClickTime - mLastClickTime) >= MIN_CLICK_DELAY_TIME) {
			// 超过点击间隔后再将lastClickTime重置为当前点击时间
			mLastClickTime = curClickTime;
			onMultiClick(v);
		}
	}
}