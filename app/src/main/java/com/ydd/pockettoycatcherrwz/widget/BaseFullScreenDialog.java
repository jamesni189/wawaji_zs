package com.ydd.pockettoycatcherrwz.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.ydd.pockettoycatcherrwz.R;

/**
 * 全屏对话框
 * 
 * Created by czhang on 17/4/21.
 */

public class BaseFullScreenDialog extends Dialog {

	public BaseFullScreenDialog(Context context) {
		super(context, R.style.popup_dialog);
		initWindow();
	}

	public BaseFullScreenDialog(Context context, int styleId) {
		super(context, styleId);
		initWindow();
	}

	/**
	 * 初始化window属性，全屏显示
	 */
	private void initWindow() {
		Window window = getWindow();
		window.setGravity(Gravity.CENTER);
		window.getDecorView().setPadding(0, 0, 0, 0);
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.MATCH_PARENT;
		window.setAttributes(lp);
	}
}
