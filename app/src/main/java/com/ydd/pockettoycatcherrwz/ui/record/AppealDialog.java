package com.ydd.pockettoycatcherrwz.ui.record;

import android.view.View;
import android.widget.TextView;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.entity.GrabDetail;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.AppealRequest;
import com.ydd.pockettoycatcherrwz.ui.BaseActivity;
import com.ydd.pockettoycatcherrwz.widget.BaseBottomDialog;

import org.greenrobot.eventbus.EventBus;

/**
 * 申诉对话框
 */
public class AppealDialog extends BaseBottomDialog {

	private long mRecordId;

	private BaseActivity mActivity;

	public AppealDialog(BaseActivity context, long recordId) {
		super(context);
		mActivity = context;
		mRecordId = recordId;
		setContentView(R.layout.dlg_appeal);
		initView();
	}

	private void initView() {
		findViewById(R.id.tv_dlg_appeal_cancel)
				.setOnClickListener(mOnClickListener);
		findViewById(R.id.tv_dlg_appeal_reason_1)
				.setOnClickListener(mOnClickListener);
		findViewById(R.id.tv_dlg_appeal_reason_2)
				.setOnClickListener(mOnClickListener);
		findViewById(R.id.tv_dlg_appeal_reason_3)
				.setOnClickListener(mOnClickListener);
		findViewById(R.id.tv_dlg_appeal_reason_4)
				.setOnClickListener(mOnClickListener);
		findViewById(R.id.tv_dlg_appeal_reason_5)
				.setOnClickListener(mOnClickListener);

	}

	private View.OnClickListener mOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_dlg_appeal_cancel:
				dismiss();
				break;
			default:
				dismiss();
				String text = ((TextView) v).getText().toString();
				appeal(text);
				break;
			}
		}
	};

	/**
	 * 申诉
	 */
	private void appeal(String reason) {
		mActivity.showDialog("");
		AppealRequest request = new AppealRequest(RunningContext.accessToken,
				reason, mRecordId,1);
		BusinessManager.getInstance().appeal(request, new MyCallback<Void>() {
			@Override
			public void onSuccess(Void result, String message) {
				if (mActivity != null && !mActivity.isFinishing()) {
					mActivity.showToast("申诉已提交");
					EventBus.getDefault().post(new GrabDetail());
				}
			}

			@Override
			public void onError(String errorCode, String message) {
				if (mActivity != null && !mActivity.isFinishing()) {
					mActivity.showToast(message);
				}
			}

			@Override
			public void onFinished() {
				if (mActivity != null) {
					mActivity.dismissDialog();
				}
			}
		});
	}

}
