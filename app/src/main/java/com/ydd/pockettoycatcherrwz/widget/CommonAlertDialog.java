package com.ydd.pockettoycatcherrwz.widget;

import android.app.Dialog;
import android.content.Context;
import android.text.TextPaint;
import android.view.View;
import android.view.WindowManager.LayoutParams;
import android.widget.Button;
import android.widget.TextView;

import com.ydd.pockettoycatcherrwz.R;

/**
 * 通用的对话框，包含一个文字显示部分和两个按钮<br>
 * 后续可以继续优化，用builder进行构建，还有粗体设置
 * 
 * @author czhang
 */
public class CommonAlertDialog extends Dialog implements View.OnClickListener {
	/** dialog文本内容 */
	private TextView mContentTv;

	/** 左按钮 */
	private Button mLeftBtn;

	/** 右按钮 */
	private Button mRightBtn;

	private OnDialogClickListener mLeftClickListener;

	private OnDialogClickListener mRightClickListener;

	public CommonAlertDialog(Context context) {
		super(context, R.style.common_alert_dialog);
		setContentView(R.layout.dialog_common_alert);
		mContentTv = (TextView) findViewById(R.id.tv_dialog_content);
		mLeftBtn = (Button) findViewById(R.id.btn_dialog_left);
		mRightBtn = (Button) findViewById(R.id.btn_dialog_right);

		// 设置对话框的宽度为400px，高度自适应
		LayoutParams params = getWindow().getAttributes();
		params.width = (int) context.getResources()
				.getDimension(R.dimen.dlg_common_width);
		params.height = LayoutParams.WRAP_CONTENT;
		getWindow().setAttributes(params);

		mLeftBtn.setOnClickListener(this);
		mRightBtn.setOnClickListener(this);
	}

	/**
	 * 设置文本内容
	 * 
	 * @param content
	 *            文本內容
	 */
	public void setContent(String content, boolean isBold) {
		mContentTv.setText(content);
		if (isBold) {
			// 设置右边字体为粗体
			TextPaint tp = mContentTv.getPaint();
			tp.setFakeBoldText(true);
		}
	}

	/**
	 * 设置文本内容
	 * 
	 * @param contentId
	 *            文本对应的resid
	 */
	public void setContent(int contentId, boolean isBold) {
		mContentTv.setText(contentId);
		if (isBold) {
			// 设置右边字体为粗体
			TextPaint tp = mContentTv.getPaint();
			tp.setFakeBoldText(true);
		}
	}

	/**
	 * 配置左侧的点击按钮
	 * 
	 * @param text
	 *            按钮文字
	 * @param listener
	 *            回调
	 */
	public void configLeftBtn(String text, OnDialogClickListener listener) {
		mLeftBtn.setText(text);
		mLeftClickListener = listener;
	}

	/**
	 * 配置右侧的点击按钮
	 * 
	 * @param text
	 *            按钮文字
	 * @param listener
	 *            回调
	 */
	public void configRightBtn(String text, OnDialogClickListener listener) {
		mRightBtn.setText(text);
		mRightClickListener = listener;
		configRightBtn(text, false, listener);
	}

	/**
	 * 配置右侧的点击按钮
	 * 
	 * @param text
	 *            按钮文字
	 * @param isBold
	 *            是否为粗体
	 * @param listener
	 *            回调
	 */
	public void configRightBtn(String text, Boolean isBold,
			OnDialogClickListener listener) {
		mRightBtn.setText(text);
		if (isBold) {
			// 设置右边字体为粗体
			TextPaint tp = mRightBtn.getPaint();
			tp.setFakeBoldText(true);
		}
		mRightClickListener = listener;
	}

	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.btn_dialog_left:
			if (mLeftClickListener != null) {
				mLeftClickListener.onDialogClick(this, v);
			}
			break;
		case R.id.btn_dialog_right:
			if (mRightClickListener != null) {
				mRightClickListener.onDialogClick(this, v);
			}
			break;
		}
	}

	public interface OnDialogClickListener {
		public void onDialogClick(Dialog dialog, View v);
	}

}
