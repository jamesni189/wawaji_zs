package com.ydd.pockettoycatcherrwz.ui.home;

import android.content.Context;
import android.view.View;
import android.widget.TextView;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.UserManager;
import com.ydd.pockettoycatcherrwz.entity.SignListInfo;
import com.ydd.pockettoycatcherrwz.entity.SignSuccessInfo;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.SignListRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.SignRequest;
import com.ydd.pockettoycatcherrwz.ui.BaseActivity;
import com.ydd.pockettoycatcherrwz.util.CommonUtil;
import com.ydd.pockettoycatcherrwz.util.ListUtil;
import com.ydd.pockettoycatcherrwz.widget.CommonDialog;
import com.ydd.pockettoycatcherrwz.widget.StrokeTextView;

/**
 * 签名对话框
 */
public class SignDialog extends CommonDialog {

	private SignItemView mItemView1;
	private SignItemView mItemView2;
	private SignItemView mItemView3;
	private SignItemView mItemView4;
	private SignItemView mItemView5;
	private SignItemView mItemView6;
	private SignItemView mItemView7;

	private SignSuccessDialog mSignSuccessDialog;

	public SignDialog(Context context) {
		super(context);
		loadSignList();
	}

	/**
	 * 加载签名信息
	 */
	private void loadSignList() {
		BusinessManager.getInstance().signList(
				new SignListRequest(RunningContext.accessToken),
				new MyCallback<SignListInfo>() {
					@Override
					public void onSuccess(SignListInfo result, String message) {
						if (result != null && !ListUtil.isEmpty(result.list)) {
							refreshView(result);
						}
					}

					@Override
					public void onError(String errorCode, String message) {
					}

					@Override
					public void onFinished() {
						((BaseActivity) mContext).dismissDialog();
					}
				});
	}

	private void refreshView(final SignListInfo signInfoList) {
		if (mItemView1 == null) {
			addContent(R.layout.dlg_sign);
			TextView signTv = (TextView) findViewById(R.id.tv_dlg_sign_title);
			CommonUtil.setTextBold(signTv);
			mItemView1 = (SignItemView) findViewById(R.id.view_sign_dlg_item_1);
			mItemView2 = (SignItemView) findViewById(R.id.view_sign_dlg_item_2);
			mItemView3 = (SignItemView) findViewById(R.id.view_sign_dlg_item_3);
			mItemView4 = (SignItemView) findViewById(R.id.view_sign_dlg_item_4);
			mItemView5 = (SignItemView) findViewById(R.id.view_sign_dlg_item_5);
			mItemView6 = (SignItemView) findViewById(R.id.view_sign_dlg_item_6);
			mItemView7 = (SignItemView) findViewById(R.id.view_sign_dlg_item_7);
			StrokeTextView btnTv = (StrokeTextView) findViewById(
					R.id.btn_dlg_sign);
			if (signInfoList.status == 0) {
				btnTv.setText("已签到");
				btnTv.setBackgroundResource(R.mipmap.btn_signed);
				btnTv.setClickable(false);
			} else {
				findViewById(R.id.btn_dlg_sign)
						.setOnClickListener(new View.OnClickListener() {
							@Override
							public void onClick(View v) {
								sign();
							}
						});
			}
		}
		mItemView1.refreshView(signInfoList.list.get(0));
		mItemView2.refreshView(signInfoList.list.get(1));
		mItemView3.refreshView(signInfoList.list.get(2));
		mItemView4.refreshView(signInfoList.list.get(3));
		mItemView5.refreshView(signInfoList.list.get(4));
		mItemView6.refreshView(signInfoList.list.get(5));
		mItemView7.refreshView(signInfoList.list.get(6));
	}

	/**
	 * 签到
	 */
	private void sign() {
		((BaseActivity) mContext).showDialog("");
		BusinessManager.getInstance().sign(
				new SignRequest(RunningContext.accessToken),
				new MyCallback<SignSuccessInfo>() {
					@Override
					public void onSuccess(SignSuccessInfo result,
							String message) {
						if (result == null) {
							((BaseActivity) mContext).showResultErrorToast();
							return;
						}
						String comment = "已累计签到 " + result.id + " 天，获得 "
								+ result.points + "钻石";
						mSignSuccessDialog = new SignSuccessDialog(getContext(),
								comment,"sign");
						mSignSuccessDialog.show();
						UserManager.getInstance().refresh();
						dismiss();
					}

					@Override
					public void onError(String errorCode, String message) {
						((BaseActivity) mContext).showToast(message);
					}

					@Override
					public void onFinished() {
						((BaseActivity) mContext).dismissDialog();
					}
				});
	}

	public void release() {
		CommonUtil.dismissDialog(mSignSuccessDialog);
	}

}
