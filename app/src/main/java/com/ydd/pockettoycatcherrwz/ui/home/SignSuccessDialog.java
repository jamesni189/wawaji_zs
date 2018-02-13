package com.ydd.pockettoycatcherrwz.ui.home;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.widget.BaseFullScreenDialog;

/**
 * 签到成功对话框
 */
public class SignSuccessDialog extends BaseFullScreenDialog {

	private String mComment;

	private String type;
	public SignSuccessDialog(Context context, String comment,String type) {
		super(context);
		mComment = comment;
		setCancelable(false);
		this.type=type;
		initView();
	}

	private void initView() {
		setContentView(R.layout.dlg_sign_success);
		ImageView who_where_show_1= (ImageView) findViewById(R.id.who_where_show_1);
		ImageView who_where_show_2= (ImageView)findViewById(R.id.who_where_show_2);
		if(type.equals("share") || type.equals("sign")){
			who_where_show_2.setVisibility(View.VISIBLE);

		}else {
			who_where_show_1.setVisibility(View.VISIBLE);
		}

		TextView commentTv = (TextView) findViewById(
				R.id.tv_dlg_sign_success_comment);
		commentTv.setText(mComment);
		findViewById(R.id.btn_dlg_sign_success_confirm)
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						dismiss();
					}
				});

	}

}
