package com.ydd.pockettoycatcherrwz.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.google.gson.Gson;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.UserManager;
import com.ydd.pockettoycatcherrwz.entity.AccessToken;
import com.ydd.pockettoycatcherrwz.entity.AppUpdataMessage;
import com.ydd.pockettoycatcherrwz.entity.LoginTelInfo;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.GetupDataMessage;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.RefreshTokenRequest;
import com.ydd.pockettoycatcherrwz.ui.BaseActivity;
import com.ydd.pockettoycatcherrwz.ui.home.MainActivityNew;
import com.ydd.pockettoycatcherrwz.util.CommonUtil;
import com.ydd.pockettoycatcherrwz.util.Constants;
import com.ydd.pockettoycatcherrwz.util.SharedPrefConfig;
import com.ydd.pockettoycatcherrwz.util.hx.HXBaseUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * 欢迎页
 */
public class SplashActivity extends BaseActivity {

    private SharedPrefConfig mSpConfig = new SharedPrefConfig();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
     //   EventBus.getDefault().register(this);
//		Bitmap bmp = BitmapFactory.decodeResource(getResources(),
//				R.mipmap.share_succeed);
//		Bitmap bmp2 = WaterImgUtil.drawInviteFailedCode(this, bmp, "12125324");
//		ImageView imageView = (ImageView) findViewById(R.id.iv_splash);
//		imageView.setImageBitmap(bmp2);
//		if (true) {
//			return;
//		}

        checkLogin();
    }

    /**
     * 校验上次登录信息
     *
     * @return
     */
    private void checkLogin() {
        mSpConfig.open(this, Constants.SP_FILE_CONFIG);
        String lastLogin = mSpConfig.getString("last_login", "");
        if (TextUtils.isEmpty(lastLogin)) {
            showHome();
        //    showLogin();
            return;
        }
        mSpConfig.open(this, lastLogin + "_" + Constants.SP_FILE_CONFIG);
        String loginInfo = mSpConfig.getString("login_info", "");
        if (TextUtils.isEmpty(loginInfo)) {
            showHome();
       //     showLogin();
            return;
        }
        try {
            RunningContext.loginTelInfo = new Gson().fromJson(loginInfo,
                    LoginTelInfo.class);
        } catch (Exception e) {
        }
        if (RunningContext.loginTelInfo != null) {
            refreshToken(lastLogin,
                    RunningContext.loginTelInfo.accessToken.refreshToken);
            return;
        }
        showLogin();
    }

    private void showLogin() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 跳转登陆页
                startActivity(
                        new Intent(SplashActivity.this, LoginActivity.class));
                finish();
            }
        }, 3000);
    }

    private void showHome() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                // 跳转登陆页
                updataApp();
                startActivity(
                    //    new Intent(SplashActivity.this, HomeActivity_new.class));
                        new Intent(SplashActivity.this, MainActivityNew.class));
                finish();
            }
        }, 3000);
    }

    public void refreshToken(final String lastLogin, String refreshToken) {
        BusinessManager.getInstance().refreshToken(
                new RefreshTokenRequest(refreshToken),
                new MyCallback<AccessToken>() {
                    @Override
                    public void onSuccess(AccessToken result, String message) {
                        if (result == null
                                || TextUtils.isEmpty(result.accessToken)) {
                            showResultErrorToast();
                            return;
                        }
                        RunningContext.accessToken = result.accessToken;
                        RunningContext.loginTelInfo.accessToken = result;
                        // 放入sp
                        SharedPrefConfig sharedPrefConfig = new SharedPrefConfig();
                        sharedPrefConfig.open(SplashActivity.this,
                                lastLogin + "_" + Constants.SP_FILE_CONFIG);
                        sharedPrefConfig.putString("login_info",
                                new Gson().toJson(RunningContext.loginTelInfo));
                        //刷新用户信息
                        UserManager.getInstance().refresh();
                        // 登录环信
                        HXBaseUtil.login(RunningContext.loginTelInfo.hxId,
                                RunningContext.loginTelInfo.hxPwd);
                        // 跳转首页
                        showHome();
                    }

                    @Override
                    public void onError(String errorCode, String message) {
                        // 跳转登录页
                        showLogin();
                    }

                    @Override
                    public void onFinished() {
                    }
                });
    }

    public void updataApp() {
        GetupDataMessage mGetupDataMessage=new GetupDataMessage(CommonUtil.getAPPVersionCode(this)+"");
        BusinessManager.getInstance().GetAppUpadataMessgae(
                mGetupDataMessage,
                new MyCallback<AppUpdataMessage>() {
                    @Override
                    public void onSuccess(AppUpdataMessage result, String message) {
                        Log.i("cvaarrl", "onSuccess: "+result.toString());
                        EventBus.getDefault().post(result);
                    }

                    @Override
                    public void onError(String errorCode, String message) {
                    }

                    @Override
                    public void onFinished() {
                    }
                });
    }


}
