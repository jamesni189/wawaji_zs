package com.ydd.pockettoycatcherrwz.ui.personal;

import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.entity.UserInfo;
import com.ydd.pockettoycatcherrwz.entity.WXentity.WXpayResult;
import com.ydd.pockettoycatcherrwz.ui.BaseActivity;
import com.ydd.pockettoycatcherrwz.ui.record.GrabRecordsActivity;
import com.ydd.pockettoycatcherrwz.util.CommonUtil;
import com.ydd.pockettoycatcherrwz.util.ImgLoaderUtil;
import com.ydd.pockettoycatcherrwz.widget.CommonDialog;
import com.ydd.pockettoycatcherrwz.widget.StrokeTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 个人信息对话框
 */
public class PersonalDialog extends CommonDialog {

	/**
	 * 名字
	 */
	private TextView mNameTv;
	/**
	 * 头像
	 */
	private ImageView mAvatarIv;
	/**
	 * 钻石充值
	 */
	private StrokeTextView mChargeTv1;
	/**
	 * 积分充值
	 */
	private StrokeTextView mChargeTv2;

	private BaseActivity mContext;

	/**
	 * 充值列表对话框
	 */
	private RechargeListDialog mRechargeListDialog;

	public PersonalDialog(BaseActivity context) {
		super(context);
		mContext = context;
		initView();
		EventBus.getDefault().register(this);
	}

	private void initView() {
		addContent(R.layout.dlg_personal);
		mNameTv = (TextView) findViewById(R.id.tv_dlg_personal_name);
		mAvatarIv = (ImageView) findViewById(R.id.iv_dlg_personal_avatar);
		mChargeTv1 = (StrokeTextView) findViewById(
				R.id.tv_dlg_personal_charge_1);
		mChargeTv2 = (StrokeTextView) findViewById(
				R.id.tv_dlg_personal_charge_2);

		mChargeTv1.setBlackStroke(7);
		mChargeTv2.setBlackStroke(7);

		findViewById(R.id.ln_dlg_personal_charge_1)
				.setOnClickListener(mOnClickListener);
		findViewById(R.id.ln_dlg_personal_charge_2)
				.setOnClickListener(mOnClickListener);
		findViewById(R.id.iv_dlg_personal_settings)
				.setOnClickListener(mOnClickListener);
		findViewById(R.id.iv_dlg_personal_grab_records)
				.setOnClickListener(mOnClickListener);
		findViewById(R.id.iv_dlg_personal_charge_records)
				.setOnClickListener(mOnClickListener);
		findViewById(R.id.iv_dlg_personal_invite)
				.setOnClickListener(mOnClickListener);

		ImgLoaderUtil.displayImage(RunningContext.loginTelInfo.avatar,
				mAvatarIv, getContext().getResources()
						.getDrawable(R.mipmap.pic_zww_default));
		mNameTv.setText(RunningContext.loginTelInfo.nickname);
		// mNameTv.setText("张三");
		mChargeTv1.setText(
				String.valueOf(" " + RunningContext.loginTelInfo.money) + " ");
		mChargeTv2.setText(
				String.valueOf(" " + RunningContext.loginTelInfo.points) + " ");
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessage(UserInfo userInfo) {
		if (userInfo != null) {
			ImgLoaderUtil.displayImage(RunningContext.loginTelInfo.avatar,
					mAvatarIv, getContext().getResources()
							.getDrawable(R.mipmap.pic_zww_default));
			mNameTv.setText(RunningContext.loginTelInfo.nickname);
			mChargeTv1.setText(
					String.valueOf(" " + RunningContext.loginTelInfo.money)
							+ " ");
			mChargeTv2.setText(
					String.valueOf(" " + RunningContext.loginTelInfo.points)
							+ " ");
		}
	}

	private View.OnClickListener mOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.iv_dlg_personal_settings:
				// 设置
				getContext().startActivity(
						new Intent(getContext(), SettingsActivity.class));
				break;
			case R.id.ln_dlg_personal_charge_1:
				// 钻石充值
				showRechargeDialog();
				break;
			case R.id.ln_dlg_personal_charge_2:
				// TODO 积分商城
				break;
			case R.id.iv_dlg_personal_grab_records:
				// 抓取记录
				getContext().startActivity(
						new Intent(getContext(), GrabRecordsActivity.class));
				break;
			case R.id.iv_dlg_personal_charge_records:
				// 充值记录
				getContext().startActivity(new Intent(getContext(),
						RechargeRecordListActivity.class));
				dismiss();
				break;
			case R.id.iv_dlg_personal_invite:
				// 邀请好友
				getContext().startActivity(
						new Intent(getContext(), InviteFriendActivity.class));
				dismiss();
				break;
			}
		}
	};

	private void showRechargeDialog() {
		mRechargeListDialog = new RechargeListDialog(mContext);
		mRechargeListDialog.show();
	}

	public void release() {
		EventBus.getDefault().unregister(this);
		CommonUtil.dismissDialog(mRechargeListDialog);
	}


	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessage(WXpayResult userInfo) {
		if (userInfo != null) {
			mRechargeListDialog.dismiss();
		}
	}



}
