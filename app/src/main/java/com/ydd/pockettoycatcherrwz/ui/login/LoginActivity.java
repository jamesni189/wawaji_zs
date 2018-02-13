package com.ydd.pockettoycatcherrwz.ui.login;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.widget.CheckBox;
import android.widget.Toast;

import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.entity.LoginTelInfo;
import com.ydd.pockettoycatcherrwz.ui.BaseActivity;
import com.ydd.pockettoycatcherrwz.ui.home.MainActivityNew;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 登录页面
 */
public class
LoginActivity extends BaseActivity {

    private CheckBox mCheckBox;

    private IWXAPI api;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        initView();
        api = WXAPIFactory.createWXAPI(this, Constants.WX_APPID, true);
        api.registerApp(Constants.WX_APPID);

    }

    private void initView() {
        //	mCheckBox = (CheckBox) findViewById(R.id.cb_login_protocol);
        //	mCheckBox.setChecked(true);
        findViewById(R.id.btn_wechat_login)
                .setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_tel_login).setOnClickListener(mOnClickListener);
        findViewById(R.id.login_pass).setOnClickListener(mOnClickListener);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.btn_wechat_login:
                    // 微信登录
//				if (!mCheckBox.isChecked()) {
//					showToast("请确认协议");
//					return;
//				}
                    doWXAuthor();
                    break;
                case R.id.btn_tel_login:
                    // 手机号登录
//				if (!mCheckBox.isChecked()) {
//					showToast("请确认协议");
//					return;
//				}
                    startActivity(
                            new Intent(LoginActivity.this, LoginTelActivity.class));

                    break;

                case R.id.login_pass:
//                    startActivity(
//                            new Intent(LoginActivity.this, HomeActivity_new.class));

                    startActivity(
                            new Intent(LoginActivity.this, MainActivityNew.class));
                    break;
                default:
                    break;

            }

        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(final LoginTelInfo loginTelInfo) {
        // 消息接收
        if (loginTelInfo != null) {
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    private void doWXAuthor() {
        if (!api.isWXAppInstalled()) {
            Toast.makeText(this, "您没有安装微信客户端", Toast.LENGTH_SHORT).show();
            return;
        }
        SendAuth.Req req = new SendAuth.Req();
        req.scope = "snsapi_userinfo";
        req.state = "app_wechat";
        api.sendReq(req);

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
    }
}
