package com.ydd.pockettoycatcherrwz.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.InputFilter;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.entity.AppUpdataMessage;
import com.ydd.pockettoycatcherrwz.entity.LoginTelInfo;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.GetCodeRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.GetupDataMessage;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.LoginTelRequest;
import com.ydd.pockettoycatcherrwz.ui.BaseTitleActivity;
import com.ydd.pockettoycatcherrwz.ui.home.HomeActivity_new;
import com.ydd.pockettoycatcherrwz.ui.home.MainActivityNew;
import com.ydd.pockettoycatcherrwz.ui.home.SecretWebActivity;
import com.ydd.pockettoycatcherrwz.util.CommonUtil;
import com.ydd.pockettoycatcherrwz.util.Constants;
import com.ydd.pockettoycatcherrwz.util.LogUtil;
import com.ydd.pockettoycatcherrwz.util.SharedPrefConfig;

import org.greenrobot.eventbus.EventBus;

/**
 * 手机号登录
 */
public class LoginTelActivity extends BaseTitleActivity {

	/**
	 * 手机号输入
	 */
	private EditText mTelEt;
	/**
	 * 验证码输入
	 */
	private EditText mCodeEt;
	/**
	 * 获取短信验证码按钮
	 */
	private TextView mGetCodeBtn;
	private String url=Constants.BASE_H5_URL+"agree";

	private boolean isSetted = false;
	private TextView tv_login_tip;
	private String content="未注册任我抓娃娃的手机号，登录时将自动注册且代表您已经同意遵守<font color='#00c24f'>用户协议</font>与<font color='#00c24f'>隐私条款</font>";

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContent(R.layout.activity_login_tel);
		setTitle("手机登录");
		initView();
		tv_login_tip.setText(Html.fromHtml(content));
		tv_login_tip.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				Intent intent = new Intent(LoginTelActivity.this, SecretWebActivity.class);
				intent.putExtra("url",url);
				startActivity(intent);
			}
		});
	}

	private void initView() {
		mTelEt = (EditText) findViewById(R.id.et_login_tel);
		mCodeEt = (EditText) findViewById(R.id.et_login_code);
		mGetCodeBtn = (TextView) findViewById(R.id.tv_login_get_code);
		tv_login_tip = (TextView) findViewById(R.id.tv_login_tip);

		mGetCodeBtn.setOnClickListener(mOnClickListener);
		findViewById(R.id.iv_login).setOnClickListener(mOnClickListener);

		mTelEt.setFilters(
				new InputFilter[] { new InputFilter.LengthFilter(11) });
		mCodeEt.setFilters(
				new InputFilter[] { new InputFilter.LengthFilter(8) });
	}

	private View.OnClickListener mOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.tv_login_get_code:
				// 获取验证码
				doGetCode();
				break;
			case R.id.iv_login:
				// 右下角登录
				doLogin();
				break;
			}
		}
	};

	/**
	 * 获取短信验证码
	 */
	private void doGetCode() {
		String phone = mTelEt.getText().toString();
		if (!CommonUtil.checkPhone(phone)) {
			showToast("请输入正确的手机号");
			return;
		}
		int height = mGetCodeBtn.getHeight();
		int width = mGetCodeBtn.getWidth();
		if (!isSetted && width > 0 && height > 0) {
			mGetCodeBtn.setLayoutParams(
					new LinearLayout.LayoutParams(width, height));
			isSetted = true;
		}
		showDialog("");
		GetCodeRequest request = new GetCodeRequest(phone);
		BusinessManager.getInstance().getCode(request, new MyCallback<Void>() {
			@Override
			public void onSuccess(Void result, String message) {
				showToast("验证码已发送");
				startCountDown();
				mGetCodeBtn.setEnabled(false);
			}

			@Override
			public void onError(String errorCode, String message) {
				showToast(message);
				stopCountDown();
			}

			@Override
			public void onFinished() {
				dismissDialog();
			}
		});
	}

	/**
	 * 登录
	 */
	private void doLogin() {
		final String phone = mTelEt.getText().toString();
		if (!CommonUtil.checkPhone(phone)) {
			showToast("请输入正确的手机号");
			return;
		}
		String code = mCodeEt.getText().toString();
		if (TextUtils.isEmpty(code)) {
			showToast("请输入验证码");
			return;
		}
		LoginTelRequest request = new LoginTelRequest(code, phone);
		showDialog("正在登录，请稍候...");
		BusinessManager.getInstance().loginTel(request,
				new MyCallback<LoginTelInfo>() {
					@Override
					public void onSuccess(LoginTelInfo result, String message) {
						Log.i("vvaaarr11", "onSuccess: "+result.toString());
						if (result == null || result.accessToken == null
								|| TextUtils.isEmpty(
										result.accessToken.accessToken)) {
							showToast("登录异常");
							return;
						}
						EventBus.getDefault().post(result);
						// 放入sp
						SharedPrefConfig sharedPrefConfig = new SharedPrefConfig();
						sharedPrefConfig.open(LoginTelActivity.this,
								Constants.SP_FILE_CONFIG);
						sharedPrefConfig.putString("last_login", phone);
						sharedPrefConfig.open(LoginTelActivity.this,
								phone + "_" + Constants.SP_FILE_CONFIG);
						sharedPrefConfig.putString("login_info",
								new Gson().toJson(result));

						// 打开首页
						RunningContext.accessToken = result.accessToken.accessToken;
						RunningContext.loginTelInfo = result;
						// 登录环信 user67
//						HXBaseUtil.login(RunningContext.loginTelInfo.hxId,
//								RunningContext.loginTelInfo.hxPwd);
//						HXBaseUtil.login("user18797",
//								"97817698");
//						Intent intent=new Intent(LoginTelActivity.this,
//								HomeActivity_new.class);
						Intent intent=new Intent(LoginTelActivity.this,
								MainActivityNew.class);

						//intent.putExtra("AppUpdataMessage",result);
						startActivity(intent);
						finish();
						//updataApp();

					}

					@Override
					public void onError(String errorCode, String message) {
						showToast(message);
					}

					@Override
					public void onFinished() {
						dismissDialog();
					}
				});
	}




	/**
	 * 开始倒计时
	 */
	private void startCountDown() {
		mCountDownTimer.cancel();
		mCountDownTimer.start();
	}

	/**
	 * 停止计数
	 */
	private void stopCountDown() {
		mCountDownTimer.cancel();
		mCountDownTimer.onFinish();
	}

	/**
	 * 倒计时计数器
	 */
	private CountDownTimer mCountDownTimer = new CountDownTimer(60 * 1000 + 50,
			1000) {

		@Override
		public void onTick(long millisUntilFinished) {
			mGetCodeBtn.setText(millisUntilFinished / 1000 + "");
			LogUtil.printCZ("" + millisUntilFinished);
		}

		@Override
		public void onFinish() {
			LogUtil.printCZ("onFinish");
			mGetCodeBtn.setEnabled(true);
			mGetCodeBtn.setText("获取验证码");
		}
	};

	@Override
	protected void onDestroy() {
		mCountDownTimer.cancel();
		super.onDestroy();
	}
	public void updataApp() {
		GetupDataMessage mGetupDataMessage=new GetupDataMessage(CommonUtil.getAPPVersionCode(this)+"");
		BusinessManager.getInstance().GetAppUpadataMessgae(
				mGetupDataMessage,
				new MyCallback<AppUpdataMessage>() {
					@Override
					public void onSuccess(AppUpdataMessage result, String message) {
						//EventBus.getDefault().post(result);
						Intent intent=new Intent(LoginTelActivity.this,
								HomeActivity_new.class);
						intent.putExtra("AppUpdataMessage",result);
						startActivity(intent);
						finish();
					}

					@Override
					public void onError(String errorCode, String message) {
						showToast(message);
					}

					@Override
					public void onFinished() {
					}
				});
	}
}
