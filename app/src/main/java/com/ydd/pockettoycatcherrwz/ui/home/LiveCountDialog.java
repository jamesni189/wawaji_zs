package com.ydd.pockettoycatcherrwz.ui.home;

import android.app.Activity;
import android.os.CountDownTimer;
import android.widget.ImageView;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.widget.BaseFullScreenDialog;

/**
 * 
 */
public class LiveCountDialog extends BaseFullScreenDialog {

	private ImageView mCountIv;
	private Activity mContext;

	public LiveCountDialog(Activity context) {
		super(context);
		mContext = context;
		setCancelable(false);
		setContentView(R.layout.dlg_live_count);
		mCountIv = (ImageView) findViewById(R.id.iv_dlg_live_count);
		startCountDown();
	}

	/**
	 * 开始倒计时
	 */
	private void startCountDown() {
		mCountDownTimer.cancel();
		mCountDownTimer.start();
	}

	/**
	 * 倒计时计数器
	 */
	private CountDownTimer mCountDownTimer = new CountDownTimer(3 * 1000 + 100,
			1000) {

		@Override
		public void onTick(long millisUntilFinished) {
			int count = (int) (millisUntilFinished / 1000);
			int resId;
			switch (count) {
			case 3:
				resId = R.mipmap.ic_count_3;
				break;
			case 2:
				resId = R.mipmap.ic_count_2;
				break;
			case 1:
				resId = R.mipmap.ic_count_1;
				break;
			default:
				resId = R.mipmap.ic_count_1;
				break;
			}
			mCountIv.setImageResource(resId);
		}

		@Override
		public void onFinish() {
			if (mContext != null && !mContext.isFinishing()) {
				dismiss();
			}
		}
	};
}
