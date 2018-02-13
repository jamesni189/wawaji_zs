package com.ydd.pockettoycatcherrwz.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;

import com.ydd.pockettoycatcherrwz.R;

/*
 * 通用带取消按钮的对话框
 */
public class CommonDialog extends BaseFullScreenDialog {

	protected Context mContext;

	private LinearLayout mContainer;

	public CommonDialog(Context context) {
		super(context);
		mContext = context;
		setCancelable(false);
		initView();
	}

	private void initView() {
		setContentView(R.layout.dlg_common);
		mContainer = (LinearLayout) findViewById(R.id.ln_dlg_common_container);
		findViewById(R.id.iv_dlg_common_cancel)
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dismiss();
					}
				});
	}

	protected void addContent(int layoutId) {
		LayoutInflater.from(mContext).inflate(layoutId, mContainer, true);
	}
}
