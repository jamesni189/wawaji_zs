package com.ydd.pockettoycatcherrwz.ui.home;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Paint;
import android.os.AsyncTask;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.entity.RoomItem;
import com.ydd.pockettoycatcherrwz.network.mina.result.GrabResult;
import com.ydd.pockettoycatcherrwz.ui.personal.BottomShareDialog;
import com.ydd.pockettoycatcherrwz.util.CommonUtil;
import com.ydd.pockettoycatcherrwz.util.Constants;
import com.ydd.pockettoycatcherrwz.util.ImageUtil;
import com.ydd.pockettoycatcherrwz.util.ImgLoaderUtil;
import com.ydd.pockettoycatcherrwz.util.LogUtil;
import com.ydd.pockettoycatcherrwz.util.WaterImgUtil;
import com.ydd.pockettoycatcherrwz.widget.BaseFullScreenDialog;
import com.ydd.pockettoycatcherrwz.widget.CPProgressDialog;
import com.ydd.pockettoycatcherrwz.widget.StrokeTextView;

import java.io.File;

import io.agora.openlive.ui.BaseActivity;

/**
 * 抓取结果对话框
 */
public class GrabResultDialog extends BaseFullScreenDialog {

	/**
	 * 描述
	 */
	private TextView mContentTv;
	private TextView mNumsTv; //数量
	private TextView tv_dlg_grab_result_view1;
	/**
	 * 查看详情
	 */
	private TextView mViewDetailsTv;
	/**
	 * 玩具
	 */
	private ImageView mToyIv;
	/**
	 * 分享好友
	 */
	private StrokeTextView mLeftBtn;
	/**
	 * 倒计时
	 */
	private TextView mCountTv;
	/**
	 * 再次挑战按钮监听
	 */
	private OnRetryClickListener mListener;

	private boolean mIsSuccess;

	private RoomItem mRoomItem;

	private BaseActivity mActivity;
	private ImageView share_iv;

	/**
	 * 通用弹框
	 */
	private CPProgressDialog mDialog;

	private BottomShareDialog mShareDialog;

	private IWXAPI api;
	private GrabResult grabResult;
	private int timeSecond=0;

	public GrabResultDialog(Context context, RoomItem roomItem,GrabResult grabResult,
                            boolean isSuccess) {
		super(context);
		mActivity = (BaseActivity) context;
		mIsSuccess = isSuccess;
		mRoomItem = roomItem;
		grabResult = grabResult;
		setCancelable(false);
		setContentView(R.layout.dlg_grab_result);
		initView(grabResult);

		api = WXAPIFactory.createWXAPI(mActivity,
				Constants.WX_APPID, true);
		api.registerApp(Constants.WX_APPID);
	}

	private void initView(GrabResult grabResult) {
		mContentTv = (TextView) findViewById(R.id.tv_dlg_grab_result_content);
		mNumsTv = (TextView) findViewById(R.id.tv_dlg_grab_result_content2);

		mViewDetailsTv = (TextView) findViewById(R.id.tv_dlg_grab_result_view);
		mToyIv = (ImageView) findViewById(R.id.iv_dlg_grab_result_toy);
		share_iv = (ImageView) findViewById(R.id.share_iv);
		mLeftBtn = (StrokeTextView) findViewById(R.id.btn_dlg_grab_result_left);
		mCountTv = (TextView) findViewById(R.id.tv_dlg_grab_result_count);

		findViewById(R.id.btn_dlg_grab_result_right)
				.setOnClickListener(mOnClickListener);
		findViewById(R.id.btn_dlg_grab_result_left)
				.setOnClickListener(mOnClickListener);
		TextView textView = (TextView) findViewById(R.id.tv_dlg_grab_result_view);
		textView.getPaint().setFlags(Paint.UNDERLINE_TEXT_FLAG);
		//textView.setOnClickListener(mOnClickListener);
		share_iv.setOnClickListener(mOnClickListener);


		findViewById(R.id.iv_dlg_grab_result_cancel)
				.setOnClickListener(mOnClickListener);

		showState(mIsSuccess,grabResult);
		timeSecond = grabResult.protection_seconds;
		startCountDown();
	}

	private void showState(boolean isSuccess,GrabResult grabResult) {

		if (isSuccess){
			mContentTv.setText(getContext().getResources()
					.getString(R.string.dlg_grab_result_success));
			//mViewDetailsTv.setText("立即查看");
			mLeftBtn.setVisibility(View.VISIBLE);
			mLeftBtn.setText("立即查看");
			if (mRoomItem != null) {
				ImgLoaderUtil.displayImage(mRoomItem.winImg, mToyIv);
			}
		}else {
			if (grabResult.prizeType == 1){
				mContentTv.setText("差一点就抓到娃娃了");
				mViewDetailsTv.setText("获得钻石奖励");
			//	mLeftBtn.setText("分享好友");
				mNumsTv.setText("钻石  "+ "X"+grabResult.num);
				mToyIv.setBackgroundResource(R.mipmap.icon_jewel);
				//		ImgLoaderUtil.displayImage(grabResult.img, mToyIv);

			}else if (grabResult.prizeType == 2){
				mContentTv.setText("差一点就抓到娃娃了");
				mViewDetailsTv.setText("获得积分奖励");
			//	mLeftBtn.setText("分享好友");
				mNumsTv.setText("积分  "+ "X"+grabResult.num);
				mToyIv.setBackgroundResource(R.mipmap.icon_jifen);

			}else if (grabResult.prizeType == 3){
				mContentTv.setText("差一点就抓到娃娃了");
				mViewDetailsTv.setText("获得碎片奖励");
			//	mLeftBtn.setText("分享好友");
				mNumsTv.setText(grabResult.name + "X"+grabResult.num);
				ImgLoaderUtil.displayImage(grabResult.img, mToyIv);
				//mToyIv.setBackgroundResource(R.mipmap.icon_jewel);
			}else if (grabResult.prizeType == 5){
				mContentTv.setText("差一点就抓到娃娃了");
				mViewDetailsTv.setText("获得五福碎片奖励");
				//mLeftBtn.setText("分享好友");
				mNumsTv.setText(grabResult.name + "X"+grabResult.num);
				ImgLoaderUtil.displayImage(grabResult.img, mToyIv);
			}else {
				mContentTv.setText(getContext().getResources()
						.getString(R.string.dlg_grab_result_failed));
				mViewDetailsTv.setVisibility(View.INVISIBLE);
				mToyIv.setBackgroundResource(R.mipmap.loss_live_background);
				//	ImgLoaderUtil.displayImage(mRoomItem.winImg, mToyIv);
			//	mLeftBtn.setText("邀请好友");
			}
		}



	}

	private View.OnClickListener mOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_dlg_grab_result_view:
				// 立即查看
				getContext().startActivity(
						new Intent(getContext(), ToysBagActivity.class));
				break;
			case R.id.btn_dlg_grab_result_left:
				// 分享好友
				// dismiss();
			//	doShare(mIsSuccess);
				getContext().startActivity(
						new Intent(getContext(), ToysBagActivity.class));
//				doShare2(mIsSuccess);
				// Intent intent = new Intent(getContext(),
				// InviteFriendActivity.class);
				// intent.putExtra("issuccess", mIsSuccess);
				// getContext().startActivity(intent);
				break;
			case R.id.btn_dlg_grab_result_right:
				// 再次挑战
				if (mListener != null) {
					mListener.onRetryClick();
				}
				break;
			case R.id.iv_dlg_grab_result_cancel:
				// 关闭对话框
				dismiss();
				break;
				case R.id.share_iv:
					doShare(mIsSuccess);
					break;
			}
		}
	};

	public void setRetryListener(OnRetryClickListener listener) {
		mListener = listener;
	}

	/**
	 * 再次挑战
	 */
	public interface OnRetryClickListener {
		void onRetryClick();
	}

	/**
	 * 开始倒计时
	 */
	private void startCountDown() {
		mCountDownTimer.cancel();
		mCountDownTimer.start();
	}

	private void doShare2(boolean isSuccess) {
		if (mShareDialog == null) {
			mShareDialog = new BottomShareDialog(isSuccess, mActivity);
		}
		mShareDialog.show();
	}

	private void doShare(boolean isSuccess) {
		AsyncTask<Object, Integer, Void> task = new AsyncTask<Object, Integer, Void>() {

			private boolean isSuccess;

			@Override
			protected void onPreExecute() {
				super.onPreExecute();
				showDialog("");
			}

			@Override
			protected Void doInBackground(Object... params) {
				isSuccess = (Boolean) params[0];
				if (isSuccess) {
					File f = new File(Constants.CACHE_PATH, "success"
							+ RunningContext.loginTelInfo.inviteCode + ".png");
					if (f.exists()) {
						return null;
					}
					Bitmap bmp = BitmapFactory.decodeResource(
							getContext().getResources(),
							R.mipmap.share_succeed);
					Bitmap bmp2 = WaterImgUtil.drawInviteSuccessCode(
							getContext(), bmp,
							RunningContext.loginTelInfo.inviteCode);
					ImageUtil.saveBitmapToDisk(bmp2, Constants.CACHE_PATH,
							"success" + RunningContext.loginTelInfo.inviteCode);
				} else {
					File f = new File(Constants.CACHE_PATH, "failed"
							+ RunningContext.loginTelInfo.inviteCode + ".png");
					if (f.exists())
						return null;
					LogUtil.printJ("not exists");
					Bitmap bmp = BitmapFactory.decodeResource(
							getContext().getResources(), R.mipmap.share_fail);
					Bitmap bmp2 = WaterImgUtil.drawInviteFailedCode(
							getContext(), bmp,
							RunningContext.loginTelInfo.inviteCode);
					ImageUtil.saveBitmapToDisk(bmp2, Constants.CACHE_PATH,
							"failed" + RunningContext.loginTelInfo.inviteCode);
				}
				return null;
			}

			@Override
			protected void onPostExecute(Void aVoid) {
				super.onPostExecute(aVoid);
				dismissDialog();
				doShare2(isSuccess);
			}
		};
		task.execute(isSuccess);
	}

	private void shareToWX(boolean issuccess) {
		if (!api.isWXAppInstalled()) {
			Toast.makeText(mActivity, "您没有安装微信客户端", Toast.LENGTH_SHORT).show();
			return;
		}
		String path = "";
		if (issuccess) {
			path = Constants.CACHE_PATH + "/"
					+ "success" + RunningContext.loginTelInfo.inviteCode
					+ ".png";
		} else {
			path = Constants.CACHE_PATH + "/"
					+ "failed" + RunningContext.loginTelInfo.inviteCode
					+ ".png";
		}
		if (TextUtils.isEmpty(path)) {
			Toast.makeText(mActivity, "图片解析失败", Toast.LENGTH_SHORT).show();
			return;
		}
		File file = new File(path);
		if (!file.exists()) {
			LogUtil.printJ("no exists");
			Toast.makeText(mActivity, "图片解析失败", Toast.LENGTH_SHORT).show();
			return;
		}

		WXImageObject imgObj = new WXImageObject();
		imgObj.setImagePath(path);

		WXMediaMessage msg = new WXMediaMessage();
		msg.mediaObject = imgObj;

		BitmapFactory.Options options = new BitmapFactory.Options();
		options.inJustDecodeBounds = true;
		options.inPreferredConfig = Bitmap.Config.RGB_565;
		BitmapFactory.decodeFile(path, options);
		int sampleszie = ImageUtil.computeSampleSize(options, -1, 32 * 1024);
		options.inSampleSize = sampleszie;
		options.inJustDecodeBounds = false;
		Bitmap bmp = BitmapFactory.decodeFile(path, options);
		msg.thumbData = ImageUtil.bitmap2Bytes(bmp, 32 * 1024);
		bmp.recycle();

		SendMessageToWX.Req req = new SendMessageToWX.Req();
		req.transaction = buildTransaction("img");
		req.message = msg;
		req.scene = SendMessageToWX.Req.WXSceneSession;
		api.sendReq(req);
	}

	private String buildTransaction(final String type) {
		return (type == null) ? String.valueOf(System.currentTimeMillis())
				: type + System.currentTimeMillis();
	}

	/**
	 * 倒计时计数器
	 */
	private CountDownTimer mCountDownTimer = new CountDownTimer(5 * 1000 + 50,
			1000) {

		@Override
		public void onTick(long millisUntilFinished) {
			mCountTv.setText("倒计时 " + millisUntilFinished / 1000 + "秒");
			Log.i("garb_",millisUntilFinished+"秒");
		}

		@Override
		public void onFinish() {
			mCountTv.setVisibility(View.INVISIBLE);
		}
	};

	/**
	 * 弹出对话框
	 *
	 * @param msg
	 *            待展示message
	 * @param isCancelable
	 *            是否可取消
	 * @param cancelListener
	 *            取消监听器
	 */
	public void showDialog(String msg, boolean isCancelable,
			OnCancelListener cancelListener) {
		dismissDialog();
		if (mDialog == null) {
			mDialog = new CPProgressDialog(mActivity);
		}
		if (!TextUtils.isEmpty(msg)) {
			mDialog.setMessage(msg);
		} else {
			mDialog.setMessage("");
		}
		mDialog.setCancelable(isCancelable);
		mDialog.setOnCancelListener(cancelListener);
		mDialog.show();
	}

	/**
	 * dismiss对话框
	 */
	public void dismissDialog() {
		if (mDialog != null && mDialog.isShowing()) {
			mDialog.dismiss();
		}
		mDialog = null;
	}

	/**
	 * 弹出对话框
	 *
	 * @param msg
	 *            待展示message
	 */
	public void showDialog(String msg) {
		if (TextUtils.isEmpty(msg)) {
			showDialog("正在加载中...", false, null);
			return;
		}
		showDialog(msg, false, null);
	}

	public void release() {
		CommonUtil.dismissDialog(mShareDialog);
	}

}
