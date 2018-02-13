package com.ydd.pockettoycatcherrwz.ui.personal;

import android.app.Dialog;
import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.CompoundButton;
import android.widget.TextView;

import com.hyphenate.EMCallBack;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.ui.BaseTitleActivity;
import com.ydd.pockettoycatcherrwz.ui.login.LoginActivity;
import com.ydd.pockettoycatcherrwz.util.CommonUtil;
import com.ydd.pockettoycatcherrwz.util.Constants;
import com.ydd.pockettoycatcherrwz.util.FileUtils;
import com.ydd.pockettoycatcherrwz.util.LogUtil;
import com.ydd.pockettoycatcherrwz.util.SharedPrefConfig;
import com.ydd.pockettoycatcherrwz.util.hx.HXBaseUtil;
import com.ydd.pockettoycatcherrwz.view.ToggleButton;
import com.ydd.pockettoycatcherrwz.widget.CommonAlertDialog;

import java.io.File;

/**
 * 设置页
 */
public class SettingsActivity extends BaseTitleActivity {

    private TextView mNameTv;
    /**
     * 缓存大小
     */
    private TextView mCacheSizeTv;
    /**
     * 背景音乐
     */
    private ToggleButton mMusic1Cb;
    /**
     * 背景音效
     */
    private ToggleButton mMusic2Cb;


    private SharedPrefConfig mSpConfig;

    private String mLoginAccount;
    /**
     * 清除缓存对话框
     */
    private CommonAlertDialog mClearCacheDlg;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContent(R.layout.activity_settings);
        setTitle("设置");
        initView();
        mSpConfig = new SharedPrefConfig();
        mSpConfig.open(this, Constants.SP_FILE_CONFIG);
        mLoginAccount = mSpConfig.getString("last_login", "");
        if (!TextUtils.isEmpty(mLoginAccount)) {
            mSpConfig.open(this,
                    mLoginAccount + "_" + Constants.SP_FILE_CONFIG);
        }
        if (mSpConfig.getBoolean(Constants.SP_MUSIC_ON_1, true)) {
            mMusic1Cb.setToggleOn();
        } else {
            mMusic1Cb.setToggleOff();
        }
        if (mSpConfig.getBoolean(Constants.SP_MUSIC_ON_2, true)) {
            mMusic2Cb.setToggleOn();
        } else {
            mMusic2Cb.setToggleOff();
        }
    }

    public void initView() {
        mCacheSizeTv = (TextView) findViewById(R.id.tv_settings_cache_size);
        mMusic1Cb = (ToggleButton) findViewById(R.id.cb_settings_music_1);
        mMusic2Cb = (ToggleButton) findViewById(R.id.cb_settings_music_2);

        findViewById(R.id.ln_settings_clear_cache)
                .setOnClickListener(mOnClickListener);
        findViewById(R.id.ln_settings_about_us)
                .setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_settings_logout)
                .setOnClickListener(mOnClickListener);
        mMusic1Cb.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                Log.i("ccxxxxx", "onToggle: " + on);
                mSpConfig.putBoolean(Constants.SP_MUSIC_ON_1, on);
            }
        });
        mMusic2Cb.setOnToggleChanged(new ToggleButton.OnToggleChanged() {
            @Override
            public void onToggle(boolean on) {
                mSpConfig.putBoolean(Constants.SP_MUSIC_ON_2, on);
            }
        });

        try {
            setSize();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }


    /**
     * 刷新用户数据
     */

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ln_settings_clear_cache:
                    showClearCacheDialog();
                    break;
                case R.id.ln_settings_about_us:
                    // 关于我们
                    startActivity(new Intent(SettingsActivity.this,
                            AboutUsActivity.class));
                    break;
                case R.id.btn_settings_logout:
                    // FIXME 退出登录 应该还有其他的
                    RunningContext.loginTelInfo = null;
                    HXBaseUtil.logout(new EMCallBack() {
                        @Override
                        public void onSuccess() {
                            Log.i("czhang", "onSuccess: ");
                        }

                        @Override
                        public void onError(int i, String s) {
                            Log.i("czhang", "onError: "+s);
                        }

                        @Override
                        public void onProgress(int i, String s) {

                        }
                    });
                    Intent intent = new Intent(SettingsActivity.this,
                            LoginActivity.class);
                    intent.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TASK
                            | Intent.FLAG_ACTIVITY_NEW_TASK);
                    mSpConfig.open(SettingsActivity.this, Constants.SP_FILE_CONFIG);
                    mSpConfig.remove("last_login");
                    startActivity(intent);
                    break;
            }
        }
    };

    private CompoundButton.OnCheckedChangeListener mOnCheckedChangeListener = new CompoundButton.OnCheckedChangeListener() {
        @Override
        public void onCheckedChanged(CompoundButton buttonView,
                                     boolean isChecked) {
            if (TextUtils.isEmpty(mLoginAccount)) {
                return;
            }
            switch (buttonView.getId()) {
                case R.id.cb_settings_music_1:
                    mSpConfig.putBoolean(Constants.SP_MUSIC_ON_1, isChecked);
                    break;
                case R.id.cb_settings_music_2:
                    mSpConfig.putBoolean(Constants.SP_MUSIC_ON_2, isChecked);
                    break;
            }
        }
    };

    private void showClearCacheDialog() {
        mClearCacheDlg = new CommonAlertDialog(this);
        mClearCacheDlg.setContent("是否清除缓存?", false);
        mClearCacheDlg.configLeftBtn("取消",
                new CommonAlertDialog.OnDialogClickListener() {
                    @Override
                    public void onDialogClick(Dialog dialog, View v) {
                        mClearCacheDlg.dismiss();
                    }
                });
        mClearCacheDlg.configRightBtn("确定",
                new CommonAlertDialog.OnDialogClickListener() {
                    @Override
                    public void onDialogClick(Dialog dialog, View v) {
                        mClearCacheDlg.dismiss();
                        FileUtils.deleteAllFiles(new File(Constants.CACHE_PATH));
                        mCacheSizeTv.setText("");
                    }
                });
        mClearCacheDlg.show();
    }

    @Override
    protected void onDestroy() {
        mSpConfig.close();
        CommonUtil.dismissDialog(mClearCacheDlg);
        super.onDestroy();
    }


    private void setSize() throws Exception {
        File f = new File(Constants.CACHE_PATH);
        if (f.exists()) {
            if (f.isDirectory()) {
                mCacheSizeTv.setText(
                        FileUtils.FormetFileSize(FileUtils.getFileSizes(f)));
            } else {
                mCacheSizeTv.setText(
                        FileUtils.FormetFileSize(FileUtils.getFileSize(f)));
            }
            LogUtil.printJ("size=" + mCacheSizeTv.getText());
        }
    }
}
