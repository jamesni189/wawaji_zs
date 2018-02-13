package com.ydd.pockettoycatcherrwz.wxapi;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.widget.Toast;

import com.google.gson.Gson;
import com.tencent.mm.opensdk.constants.ConstantsAPI;
import com.tencent.mm.opensdk.modelbase.BaseReq;
import com.tencent.mm.opensdk.modelbase.BaseResp;
import com.tencent.mm.opensdk.modelmsg.SendAuth;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.IWXAPIEventHandler;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.entity.AppUpdataMessage;
import com.ydd.pockettoycatcherrwz.entity.GetMessageForWx;
import com.ydd.pockettoycatcherrwz.entity.LoginTelInfo;
import com.ydd.pockettoycatcherrwz.entity.WXentity.AuthorResult;
import com.ydd.pockettoycatcherrwz.entity.WXentity.WXuserInfo;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.GetupDataMessage;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.LoginWechatRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.wechatimpl.WeChatAccessTokenRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.wechatimpl.WechatGetUserInfoRequest;
import com.ydd.pockettoycatcherrwz.ui.BaseActivity;
import com.ydd.pockettoycatcherrwz.ui.home.HomeActivity_new;
import com.ydd.pockettoycatcherrwz.ui.home.MainActivityNew;
import com.ydd.pockettoycatcherrwz.util.CommonUtil;
import com.ydd.pockettoycatcherrwz.util.Constants;
import com.ydd.pockettoycatcherrwz.util.LogUtil;
import com.ydd.pockettoycatcherrwz.util.SharedPrefConfig;
import com.ydd.pockettoycatcherrwz.util.hx.HXBaseUtil;

import org.greenrobot.eventbus.EventBus;

/**
 * Created by jia on 17/11/6.
 */

public class WXEntryActivity extends BaseActivity implements IWXAPIEventHandler {

    private IWXAPI api;

    private String mOpenId;

    Handler mHandler = new Handler(){
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            int what = msg.what;
            if(what == 0){
                //在主线程中需要执行的操作，一般是UI操作
            }
        }
    };


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        api = WXAPIFactory.createWXAPI(this, Constants.WX_APPID);
        api.handleIntent(getIntent(), this);
    }

    @Override
    protected void onNewIntent(Intent intent) {
        super.onNewIntent(intent);
        setIntent(intent);
        api.handleIntent(intent, this);
    }

    // 微信发送请求到第三方应用时，会回调到该方法
    @Override
    public void onReq(BaseReq req) {
    }

    // 第三方应用发送到微信的请求处理后的响应结果，会回调到该方法
    @Override
    public void onResp(BaseResp resp) {
        LogUtil.printJ("over");
        int result = 0;
        if (resp.getType() == ConstantsAPI.COMMAND_SENDMESSAGE_TO_WX) {
            //分享
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    result = R.string.errcode_success;
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    result = R.string.errcode_cancel;
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    result = R.string.errcode_deny;
                    break;
                case BaseResp.ErrCode.ERR_UNSUPPORT:
                    result = R.string.errcode_unsupported;
                    break;
                default:
                    result = R.string.errcode_unknown;
                    break;
            }
            Toast.makeText(this, result, Toast.LENGTH_LONG).show();
            EventBus.getDefault().post(new GetMessageForWx(result));
            finish();
        } else if (resp.getType() == ConstantsAPI.COMMAND_PAY_BY_WX) {
            //支付
        } else if (resp.getType() == ConstantsAPI.COMMAND_SENDAUTH) {
            //授权
            LogUtil.printJ("errcode="+resp.errCode);
            switch (resp.errCode) {
                case BaseResp.ErrCode.ERR_OK:
                    showDialog("");
                   String code = ((SendAuth.Resp) resp).code;
                   loginWX("",code,"");
                /*    BusinessManager.getInstance().getWXcode(new WeChatAccessTokenRequest(Constants.WX_APPID, Constants.WX_SECRET, ((SendAuth.Resp) resp).code), new MyCallback<AuthorResult>() {
                        @Override
                        public void onSuccess(AuthorResult result, String message) {
                            LogUtil.printJ("onsuccess");
                            if(result==null){
                                dismissDialog();
                                Toast.makeText(WXEntryActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                                finish();
                                return;
                            }
                            SharedPrefConfig config=new SharedPrefConfig();
                            config.open(WXEntryActivity.this,Constants.SP_FILE_CONFIG);
                            config.putString("wxaccess",result.access_token);
                            LogUtil.printJ("getuser info");
                            //getUserInfo(result.openid,result.access_token);
                            getUserInfo(result.openid,result.access_token);
                        }

                        @Override
                        public void onError(String errorCode, String message) {
                            dismissDialog();
                            Toast.makeText(WXEntryActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                            finish();
                        }

                        @Override
                        public void onFinished() {

                        }
                    });*/
                    break;
                case BaseResp.ErrCode.ERR_AUTH_DENIED:
                    result = R.string.errcode_deny;
                    finish();
                    break;
                case BaseResp.ErrCode.ERR_USER_CANCEL:
                    result = R.string.errcode_usercancle;
                    finish();
                    break;
                default:
                    result = R.string.errcode_unsupported;
                    LogUtil.printJ("result="+resp.errCode);
                    finish();
                    break;
            }
        }
    }


    /**
     * 获取用户信息
     * @param openid
     * @param accsessToken
     */
    private void getUserInfo(final String openid, String accsessToken){
        BusinessManager.getInstance().getWXUserInfo(new WechatGetUserInfoRequest(accsessToken, openid), new MyCallback<WXuserInfo>() {
            @Override
            public void onSuccess(WXuserInfo result, String message) {
                if(result==null) {
                    dismissDialog();
                    Toast.makeText(WXEntryActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                    finish();
                    return;
                }
                LogUtil.printJ("login wx");
                mOpenId=result.openid;
                loginWX(result.headimgurl,result.openid,result.nickname);
            }

            @Override
            public void onError(String errorCode, String message) {
                dismissDialog();
                Toast.makeText(WXEntryActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                finish();
            }

            @Override
            public void onFinished() {

            }
        });
    }




    /**
     * 登录服务器
     */

    private void loginWX(String avatar,String openid,String nickname){
        BusinessManager.getInstance().loginWX(new LoginWechatRequest(avatar, nickname, openid), new MyCallback<LoginTelInfo>() {
            @Override
            public void onSuccess(LoginTelInfo result, String message) {
                Log.i("vvaaarr", "onSuccess: "+result.toString());
                if (result == null || result.accessToken == null
                        || TextUtils.isEmpty(
                        result.accessToken.accessToken)) {
                    showToast("登录异常");
                    return;
                }
                // 放入sp
                SharedPrefConfig sharedPrefConfig = new SharedPrefConfig();
                sharedPrefConfig.open(WXEntryActivity.this,
                        Constants.SP_FILE_CONFIG);
                sharedPrefConfig.putString("last_login", mOpenId);
                sharedPrefConfig.open(WXEntryActivity.this,
                        mOpenId + "_" + Constants.SP_FILE_CONFIG);
                sharedPrefConfig.putString("login_info",
                        new Gson().toJson(result));
                // 打开首页
                RunningContext.accessToken = result.accessToken.accessToken;
                RunningContext.loginTelInfo = result;
                // 登录环信
//                HXBaseUtil.login("user34",
//								"98281085");
                HXBaseUtil.login(RunningContext.loginTelInfo.hxId,
                        RunningContext.loginTelInfo.hxPwd);
                dismissDialog();

              //  EventBus.getDefault().post(result);
                updataApp();
                startActivity(new Intent(WXEntryActivity.this,
                        MainActivityNew.class));
                finish();
            }

            @Override
            public void onError(String errorCode, String message) {
                dismissDialog();
                Toast.makeText(WXEntryActivity.this, "登录失败", Toast.LENGTH_SHORT).show();
                finish();
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
