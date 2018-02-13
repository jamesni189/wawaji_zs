package com.ydd.pockettoycatcherrwz.ui.personal;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.UserManager;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.InviteCodeRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.InvitePointsRequest;
import com.ydd.pockettoycatcherrwz.ui.BaseActivity;
import com.ydd.pockettoycatcherrwz.util.Constants;
import com.ydd.pockettoycatcherrwz.util.ImageUtil;
import com.ydd.pockettoycatcherrwz.util.LogUtil;
import com.ydd.pockettoycatcherrwz.util.WaterImgUtil;
import com.ydd.pockettoycatcherrwz.widget.CommonTitleBar;

import java.io.File;

/**
 * 邀请好友
 */
public class InviteFriendActivity extends BaseActivity
        implements View.OnClickListener {
    /**
     * 邀请码输入框
     */
    private EditText mCodeEt;
    /**
     * 兑换按钮
     */
    private Button mExchangeBtn;
    /**
     * 邀请码展示框
     */
    private TextView mInviteCodeTv;
    /**
     * 邀请按钮
     */
    private LinearLayout mInviteBtn;

    private TextView mCommentTv;

    private BottomShareDialog dialog;

    private boolean mIsSuccess = false;
    private LinearLayout buttom_lin;
    protected CommonTitleBar mTitleBar;
    private ImageView transpants_image_click;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (RunningContext.loginTelInfo == null) {
            finish();
            return;
        }
        setContentView(R.layout.activity_invite_friend);
        mIsSuccess = getIntent().getBooleanExtra("issuccess", false);
        mTitleBar = (CommonTitleBar) findViewById(R.id.common_title_bar);
     //   mTitleBar.setTitle("兑换中心");
        mTitleBar.setTitle("邀请有礼");
        mTitleBar.setBackground(R.mipmap.intvite_fridents_titile_back);
        initView();
        loadDesc();
    }

    private void initView() {
        mCodeEt = (EditText) findViewById(R.id.et_invite_friend);
        mExchangeBtn = (Button) findViewById(
                R.id.btn_invite_friend_exchange);
        mExchangeBtn.setOnClickListener(this);
        mInviteCodeTv = (TextView) findViewById(R.id.tv_invite_friend);
        mCommentTv = (TextView) findViewById(R.id.tv_invite_friend_comment);
        transpants_image_click = (ImageView) findViewById(R.id.transpants_image_click);
        mInviteBtn = (LinearLayout) findViewById(
                R.id.btn_invite_friend_invite);
       // mInviteCodeTv.setText(RunningContext.loginTelInfo.inviteCode);
        buttom_lin = (LinearLayout) findViewById(R.id.share_buttom);
        String str=RunningContext.loginTelInfo.inviteCode;
        for(int i=0;i<str.length();i++){
            RelativeLayout button= (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.share_button_my,null);
            TextView t= (TextView) button.findViewById(R.id.tv_invite_friend);
            t.setText(str.charAt(i)+"");
            buttom_lin.addView(button);
        }


        mInviteBtn.setOnClickListener(this);
        dealWithWater();
        mCommentTv.setText("\n");
//        transpants_image_click.setOnTouchListener(new View.OnTouchListener() {
//            @Override
//            public boolean onTouch(View v, MotionEvent event) {
//                mInviteBtn.dispatchGenericMotionEvent(event);
//                return false;
//            }
//        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_invite_friend_exchange:
                String code = mCodeEt.getText().toString();
                if (TextUtils.isEmpty(code)) {
                    showToast("请输入验证码");
                    return;
                }
                doInvite(code);
                break;
            case R.id.btn_invite_friend_invite:
                showShareDialog();
                break;
        }
    }

    private void loadDesc() {
        showDialog("");
        BusinessManager.getInstance().inviteDesc(
                new InvitePointsRequest(RunningContext.accessToken),
                new MyCallback<String>() {
                    @Override
                    public void onSuccess(String result, String message) {
                        if (TextUtils.isEmpty(result)) {
                            return;
                        }
                        String comment = getResources()
                                .getString(R.string.invite_comment, result);

                        mCommentTv.setText(Html.fromHtml(comment));
                    }

                    @Override
                    public void onError(String errorCode, String message) {
                        String comment = getResources()
                                .getString(R.string.invite_comment, "");
                        mCommentTv.setText(comment);
                    }

                    @Override
                    public void onFinished() {
                        dismissDialog();
                    }
                });
    }

    private void doInvite(String code) {
        BusinessManager.getInstance().inviteCode(
                new InviteCodeRequest(code, RunningContext.accessToken),
                new MyCallback<Void>() {
                    @Override
                    public void onSuccess(Void result, String message) {
                        showToast("兑换成功");
                        UserManager.getInstance().refresh();
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

    private void showShareDialog() {
        if (dialog == null) {
            dialog = new BottomShareDialog(mIsSuccess, this);
        }
        dialog.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode,
                                    Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        Tencent.onActivityResultData(requestCode, resultCode, data,
                new IUiListener() {
                    @Override
                    public void onComplete(Object o) {
                        LogUtil.printJ("activity complete");
                    }

                    @Override
                    public void onError(UiError uiError) {
                        LogUtil.printJ("activity errorcode=" + uiError.errorCode
                                + "  detail=" + uiError.errorDetail
                                + "  message=" + uiError.errorMessage);
                    }

                    @Override
                    public void onCancel() {
                        LogUtil.printJ("activity cancel");
                    }
                });
    }

    private void dealWithWater() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mIsSuccess) {
                    File f = new File(Constants.CACHE_PATH, "success"
                            + RunningContext.loginTelInfo.inviteCode + ".png");
                    if (f.exists()) {
                        return;
                    }
                    Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                            R.mipmap.share_succeed);
                    Bitmap bmp2 = WaterImgUtil.drawInviteSuccessCode(
                            InviteFriendActivity.this, bmp,
                            RunningContext.loginTelInfo.inviteCode);
                    ImageUtil.saveBitmapToDisk(bmp2, Constants.CACHE_PATH,
                            "success" + RunningContext.loginTelInfo.inviteCode);
                } else {
                    File f = new File(Constants.CACHE_PATH, "failed"
                            + RunningContext.loginTelInfo.inviteCode + ".png");
                    if (f.exists())
                        return;
                    LogUtil.printJ("not exists");
                    Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                            R.mipmap.share_fail);
                    Bitmap bmp2 = WaterImgUtil.drawInviteFailedCode(
                            InviteFriendActivity.this, bmp,
                            RunningContext.loginTelInfo.inviteCode);
                    ImageUtil.saveBitmapToDisk(bmp2, Constants.CACHE_PATH,
                            "failed" + RunningContext.loginTelInfo.inviteCode);
                }
            }
        }).start();

    }

}
