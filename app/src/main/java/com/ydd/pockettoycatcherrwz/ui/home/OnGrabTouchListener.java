package com.ydd.pockettoycatcherrwz.ui.home;

import android.util.Log;
import android.view.MotionEvent;
import android.view.View;

/**
 * 娃娃机操作的触摸事件
 */
public abstract class OnGrabTouchListener implements View.OnTouchListener {

	private boolean mIsPressed;

	/**
	 * 任务是否在运行中
	 */
	private boolean mIsRunning;
	private int mTouchedViewId;

	@Override
	public boolean onTouch(View v, MotionEvent event) {
		switch (event.getAction()) {
		case MotionEvent.ACTION_DOWN:
			// 按下
			mIsPressed = true;
			mTouchedViewId = v.getId();
			/*if (mIsRunning) {
				Log.i("===move","i");
				return false;
			}*/
			Log.i("====","1");
			onViewPressed(mTouchedViewId);
		//	runTask();
			break;
		case MotionEvent.ACTION_UP:
			Log.i("====","2");
			break;
		case MotionEvent.ACTION_CANCEL:
		case MotionEvent.ACTION_OUTSIDE:
			// 松开
			Log.i("doLoosen","3");
			mIsPressed = false;
			break;
		}
		return false;
	}

	private void runTask() {
		mIsRunning = true;
		new Thread(new Runnable() {
			@Override
			public void run() {
				int sumTime = 0;
				while (mIsPressed && sumTime < 20000) {  //sumTime < 30000
					onViewPressed(mTouchedViewId);
					try {
						Thread.sleep(200); //300
					} catch (InterruptedException e) {
						e.printStackTrace();
						mIsPressed = false;
					}
					sumTime += 200;
				}
				mIsRunning = false;
			}
		}).start();
	}

	/**
	 * 释放
	 */
	public void release() {
		mIsPressed = false;
	}

	protected abstract void onViewPressed(int viewId);
}
