package com.ydd.pockettoycatcherrwz.ui.home;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.entity.SignInfo;

/**
 * 签到item
 */
public class SignItemView extends FrameLayout {
	/**
	 * 已签
	 */
	private TextView mSignedTv;
	/**
	 * 未签layout
	 */
	private LinearLayout mUnSignedLn;
	/**
	 * 未签奖励
	 */
	private TextView mUnSignedTv;

	public SignItemView(Context context) {
		super(context);
		initView();
	}

	public SignItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	private void initView() {
		LayoutInflater layoutInflater = LayoutInflater.from(getContext());
		View rootView = layoutInflater.inflate(R.layout.view_sign_item, this,
				true);
		mSignedTv = (TextView) rootView.findViewById(R.id.tv_dlg_sign_signed);
		mUnSignedLn = (LinearLayout) rootView
				.findViewById(R.id.ln_dlg_sign_unsigned);
		mUnSignedTv = (TextView) rootView
				.findViewById(R.id.tv_dlg_sign_unsigned);
	}

	/**
	 * 刷新页面
	 * 
	 * @param signInfo
	 */
	public void refreshView(SignInfo signInfo) {
		if (signInfo.status == 0) {
			// 签到
			mSignedTv.setVisibility(View.VISIBLE);
			mUnSignedLn.setVisibility(View.GONE);
		} else {
			// 未签到
			mSignedTv.setVisibility(View.GONE);
			mUnSignedLn.setVisibility(View.VISIBLE);
			mUnSignedTv.setText(String.valueOf(signInfo.points));
		}
	}

}
