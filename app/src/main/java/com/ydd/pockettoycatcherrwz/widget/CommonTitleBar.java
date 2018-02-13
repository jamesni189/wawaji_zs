package com.ydd.pockettoycatcherrwz.widget;

import android.app.Activity;
import android.content.Context;
import android.text.TextUtils;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ydd.pockettoycatcherrwz.R;

/**
 * 通用标题栏
 * 
 * Created by czhang on 17/1/7.
 */

public class CommonTitleBar extends FrameLayout
		implements View.OnClickListener {
	private Context mContext;

	/**
	 * 返回按键
	 */
	public ImageView mBackIv;
	/**
	 * 标题栏
	 */
	public TextView mTitleTv;


	public TextView mRightTv;
	private View rootView;
	private RelativeLayout layout_base;

	public CommonTitleBar(Context context) {
		super(context);
		mContext = context;
		initView();
	}

	public CommonTitleBar(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initView();
	}

	public CommonTitleBar(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		initView();
	}

	private void initView() {
		LayoutInflater layoutInflater = LayoutInflater.from(getContext());
		rootView = layoutInflater.inflate(R.layout.view_common_titlebar,
				this, true);
		layout_base = (RelativeLayout) findViewById(R.id.layout_base);
		mBackIv = (ImageView) rootView.findViewById(R.id.iv_common_title_back);
		mTitleTv = (TextView) rootView
				.findViewById(R.id.tv_common_title_middle);
		mRightTv =(TextView) rootView.findViewById(R.id.tv_common_title_right);

		mBackIv.setOnClickListener(this);
	}

	/**
	 * 设置标题
	 * 
	 * @param title
	 *            标题
	 */
	public void setTitle(String title) {
		if (TextUtils.isEmpty(title)) {
			mTitleTv.setText("");
		} else {
			mTitleTv.setText(title);
		}
	}

	public void setBackground(int id){
		layout_base.setBackgroundResource(id);
	}
	@Override
	public void onClick(View v) {
		switch (v.getId()) {
		case R.id.iv_common_title_back:
			if (mContext != null && mContext instanceof Activity) {
				((Activity) mContext).finish();
			}
			break;
		}
	}

	public void setmRightTv(String tv,OnClickListener onClickListener){
		mRightTv.setVisibility(VISIBLE);
		mRightTv.setText(tv);
		mRightTv.setOnClickListener(onClickListener);
	}
}
