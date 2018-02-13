package com.ydd.pockettoycatcherrwz.widget;

import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;

import com.ydd.pockettoycatcherrwz.R;

/**
 * 底部半屏dialog
 * 
 * @author czhang
 */
public class BaseBottomDialog extends Dialog {

	public BaseBottomDialog(Context context) {
		super(context, R.style.bottom_dialog);
		initWindow();
	}

	/**
	 * 初始化window属性，使对话框居于底部，水平向填满屏幕
	 */
	private void initWindow() {
		Window window = getWindow();
		window.setGravity(Gravity.BOTTOM);
		window.getDecorView().setPadding(0, 0, 0, 0);
		WindowManager.LayoutParams lp = window.getAttributes();
		lp.width = WindowManager.LayoutParams.MATCH_PARENT;
		lp.height = WindowManager.LayoutParams.WRAP_CONTENT;
		window.setAttributes(lp);
	}
}
