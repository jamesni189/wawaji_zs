package com.ydd.pockettoycatcherrwz.widget;


import android.app.Dialog;
import android.content.Context;
import android.view.Gravity;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.ImageView;
import android.widget.TextView;

import com.ydd.pockettoycatcherrwz.R;

/**
 * 自定义加载对话框
 *
 */
public class CPProgressDialog extends Dialog {
	private Animation operatingAnim;
	private ImageView mImageView = null;
	private TextView mMsgTxt = null;

	public CPProgressDialog(Context context) {
		super(context, R.style.rp_custom_progress_dialog);
		initDialog(context);
	}

	public CPProgressDialog(Context context, int theme) {
		super(context, theme);
		initDialog(context);
	}

	private void initDialog(Context context) {
		setContentView(R.layout.rp_progressdialog);
		getWindow().getAttributes().gravity = Gravity.CENTER;
		mImageView = (ImageView) findViewById(R.id.loadingImageView);
		mMsgTxt = (TextView) findViewById(R.id.id_tv_loadingmsg);

		operatingAnim = AnimationUtils.loadAnimation(context, R.anim.rotate);
		LinearInterpolator lin = new LinearInterpolator();
		operatingAnim.setInterpolator(lin);
	}

	@Override
	public void show() {
		super.show();
		mImageView.clearAnimation();
		mImageView.startAnimation(operatingAnim);
	}

	@Override
	public void dismiss() {
		super.dismiss();
		mImageView.clearAnimation();
	}

	public CPProgressDialog setMessage(String strMessage) {
		if (mMsgTxt != null) {
			mMsgTxt.setText(strMessage);
		}
		return this;
	}
}
