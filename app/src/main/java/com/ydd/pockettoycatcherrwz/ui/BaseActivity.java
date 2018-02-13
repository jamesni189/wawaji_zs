package com.ydd.pockettoycatcherrwz.ui;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.FragmentActivity;
import android.text.TextUtils;
import android.widget.Toast;

import com.umeng.analytics.MobclickAgent;
import com.ydd.pockettoycatcherrwz.PTCApplication;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.UserManager;
import com.ydd.pockettoycatcherrwz.entity.TokenInvalidMsg;
import com.ydd.pockettoycatcherrwz.ui.home.HomeActivity_new;
import com.ydd.pockettoycatcherrwz.ui.home.MainActivityNew;
import com.ydd.pockettoycatcherrwz.ui.login.LoginActivity;
import com.ydd.pockettoycatcherrwz.ui.login.LoginTelActivity;
import com.ydd.pockettoycatcherrwz.ui.login.SplashActivity;
import com.ydd.pockettoycatcherrwz.widget.CPProgressDialog;
import com.ydd.pockettoycatcherrwz.wxapi.WXEntryActivity;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 基础activity
 */
public class BaseActivity extends FragmentActivity {

    /**
     * toast提示
     */
    private Toast mToast;

    /**
     * 通用弹框
     */
    private CPProgressDialog mDialog;

    private static boolean isUploaded = false;
    private PTCApplication mApplication;
    private BaseActivity oContext;
    private MyBaseActiviy_Broad oBaseActiviy_Broad;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EventBus.getDefault().register(this);
        if (this instanceof SplashActivity || this instanceof LoginActivity || this instanceof LoginTelActivity || this instanceof WXEntryActivity || this instanceof HomeActivity_new
                || this instanceof MainActivityNew) {
        } else {
            if (RunningContext.loginTelInfo == null) {
                startActivity(new Intent(this, LoginActivity.class));
                finish();
                return;
            }
        }
        ((PTCApplication) getApplication()).initWorkerThread();
        UserManager.getInstance().checkRefresh();
        if (mApplication == null) {
            // 得到Application对象
            mApplication = (PTCApplication) getApplication();
        }
        oContext = this;// 把当前的上下文对象赋值给BaseActivity
        addActivity();
    }

    // 添加Activity方法
    public void addActivity() {
        mApplication.addActivity_(oContext);// 调用myApplication的添加Activity方法
    }

    //销毁当个Activity方法
    public void removeActivity() {
        mApplication.removeActivity_(oContext);// 调用myApplication的销毁单个Activity方法
    }

    //销毁所有Activity方法
    public void removeALLActivity() {
        mApplication.removeALLActivity_();// 调用myApplication的销毁所有Activity方法
    }

    /**
     * toast提示
     *
     * @param msg 待提示内容
     */
    public void showToast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(this, msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }

    /**
     * toast提示
     */
    public void showResultErrorToast() {
        showToast(getResources().getString(R.string.network_error));
    }

    /**
     * 弹出对话框
     *
     * @param msg            待展示message
     * @param isCancelable   是否可取消
     * @param cancelListener 取消监听器
     */
    public void showDialog(String msg, boolean isCancelable,
                           DialogInterface.OnCancelListener cancelListener) {
        dismissDialog();
        if (mDialog == null) {
            mDialog = new CPProgressDialog(this);
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
     * 弹出对话框
     *
     * @param msg 待展示message
     */
    public void showDialog(String msg) {
        if (TextUtils.isEmpty(msg)) {
            showDialog("正在加载中...", false, null);
            return;
        }
        showDialog(msg, false, null);
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

    @Override
    protected void onDestroy() {
        dismissDialog();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    protected void onResume() {
        super.onResume();
        // 友盟统计
        MobclickAgent.onResume(this);
    }

    @Override
    protected void onPause() {
        super.onPause();
        // 友盟统计
        MobclickAgent.onPause(this);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(TokenInvalidMsg msg) {
        finish();
    }


    //定义一个广播
    public class MyBaseActiviy_Broad extends BroadcastReceiver {

		/*public void onReceive(Context arg0, Intent intent) {
//接收发送过来的广播内容

		}*/

        @Override
        public void onReceive(Context context, Intent intent) {
            int closeAll = intent.getIntExtra("closeAll", 0);
            if (closeAll == 1) {
                finish();//销毁BaseActivity
            }
        }
    }


}
