package com.ydd.pockettoycatcherrwz.ui.personal;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.os.Environment;
import android.text.TextUtils;
import android.view.View;
import android.widget.Toast;

import com.tencent.connect.share.QQShare;
import com.tencent.connect.share.QzoneShare;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXImageObject;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.tencent.tauth.IUiListener;
import com.tencent.tauth.Tencent;
import com.tencent.tauth.UiError;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.util.Constants;
import com.ydd.pockettoycatcherrwz.util.ImageUtil;
import com.ydd.pockettoycatcherrwz.util.LogUtil;
import com.ydd.pockettoycatcherrwz.widget.BaseBottomDialog;

import java.io.File;
import java.util.ArrayList;

/**
 * Created by jia on 17/11/6.
 */

public class BottomShareDialog extends BaseBottomDialog implements View.OnClickListener {


    private Tencent tencent;

    private Activity context;

    private IWXAPI api;

    private boolean issuccess = false;

    private static final int THUMB_SIZE = 150;

    public BottomShareDialog(Boolean issuccess, Activity context) {
        super(context);
        this.context = context;
        setContentView(R.layout.dialog_share);
//        tencent = Tencent.createInstance(Constants.QQ_APPID, context);
        api = WXAPIFactory.createWXAPI(context, Constants.WX_APPID, true);
        api.registerApp(Constants.WX_APPID);
        this.issuccess = issuccess;
        LogUtil.printJ("issuccess=" + issuccess);
        initView();
    }

    private void initView() {
        findViewById(R.id.tv_invite_cancle).setOnClickListener(this);
        findViewById(R.id.tv_invite_circle).setOnClickListener(this);
        findViewById(R.id.tv_invite_qq).setOnClickListener(this);
        findViewById(R.id.tv_invite_qzone).setOnClickListener(this);
        findViewById(R.id.tv_invite_wechat).setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.tv_invite_cancle:
                dismiss();
                break;
            case R.id.tv_invite_qq:
                shareToQQ();
                dismiss();
                break;
            case R.id.tv_invite_qzone:
                shareToQzone();
                dismiss();
                break;
            case R.id.tv_invite_circle:
                shareToTimeline();
                dismiss();
                break;
            case R.id.tv_invite_wechat:
                shareToWX();
                dismiss();
                break;
        }
    }


    private void shareToQQ() {
        final Bundle params = new Bundle();
        params.putInt(QQShare.SHARE_TO_QQ_KEY_TYPE, QQShare.SHARE_TO_QQ_TYPE_APP);
        params.putString(QQShare.SHARE_TO_QQ_TITLE, "抓娃娃");
        params.putString(QQShare.SHARE_TO_QQ_SUMMARY, "我在玩抓娃娃 你也一起来吧");
        params.putString(QQShare.SHARE_TO_QQ_EXT_STR, "非常好玩 你也来玩吗");
        params.putString(QQShare.SHARE_TO_QQ_TARGET_URL, "http://www.baidu.com");
        params.putString(QQShare.SHARE_TO_QQ_IMAGE_URL, "https://ss2.baidu.com/6ONYsjip0QIZ8tyhnq/it/u=3316471391,3569458864&fm=58");
//        params.putInt(QQShare.SHARE_TO_QQ_EXT_INT,  "其他附加功能");
        tencent.shareToQQ(context, params, new IUiListener() {
            @Override
            public void onComplete(Object o) {
                LogUtil.printJ("dialog complete");
            }

            @Override
            public void onError(UiError uiError) {
                LogUtil.printJ("dialog errorcode=" + uiError.errorCode + "  detail=" + uiError.errorDetail + "  message=" + uiError.errorMessage);
            }

            @Override
            public void onCancel() {
                LogUtil.printJ("dialog cancel");
            }
        });
    }


    private void shareToQzone() {
        final Bundle params = new Bundle();
        params.putInt(QzoneShare.SHARE_TO_QZONE_KEY_TYPE, QzoneShare.SHARE_TO_QZONE_TYPE_IMAGE_TEXT);
        params.putString(QzoneShare.SHARE_TO_QQ_TITLE, "标题");//必填
        params.putString(QzoneShare.SHARE_TO_QQ_SUMMARY, "摘要");//选填
        params.putString(QzoneShare.SHARE_TO_QQ_TARGET_URL, "http://www.baidu.com");//必填
        ArrayList<String> list = new ArrayList<>();
        list.add(Environment.getExternalStorageDirectory().getPath()//s.getExternalStorageDirectory()
                + "/" + "utooo" + "/" + "share.jpg");
        params.putStringArrayList(QzoneShare.SHARE_TO_QQ_IMAGE_URL, list);
        tencent.shareToQzone(context, params, new IUiListener() {
            @Override
            public void onComplete(Object o) {

            }

            @Override
            public void onError(UiError uiError) {

            }

            @Override
            public void onCancel() {

            }
        });
    }


    private void shareToWX() {
        if (!api.isWXAppInstalled()) {
            Toast.makeText(context, "您没有安装微信客户端", Toast.LENGTH_SHORT).show();
            return;
        }
        String path = "";
        if (issuccess) {
            path = Constants.CACHE_PATH + "/" + "success" + RunningContext.loginTelInfo.inviteCode + ".png";
        } else {
            path = Constants.CACHE_PATH + "/" + "failed" + RunningContext.loginTelInfo.inviteCode + ".png";
        }
        if (TextUtils.isEmpty(path)) {
            Toast.makeText(context, "图片解析失败", Toast.LENGTH_SHORT).show();
            return;
        }
        File file = new File(path);
        if (!file.exists()) {
            LogUtil.printJ("no exists");
            Toast.makeText(context, "图片解析失败", Toast.LENGTH_SHORT).show();
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


    private void shareToTimeline() {
        if (!api.isWXAppInstalled()) {
            Toast.makeText(context, "您没有安装微信客户端", Toast.LENGTH_SHORT).show();
            return;
        }
        String path = "";
        if (issuccess) {
            path = Constants.CACHE_PATH + "/" + "success" + RunningContext.loginTelInfo.inviteCode + ".png";
        } else {
            path = Constants.CACHE_PATH + "/" + "failed" + RunningContext.loginTelInfo.inviteCode + ".png";
        }
        if (TextUtils.isEmpty(path)) {
            Toast.makeText(context, "图片解析失败", Toast.LENGTH_SHORT).show();
            LogUtil.printJ("path=null");
            return;
        }
        File file = new File(path);
        if (!file.exists()) {
            Toast.makeText(context, "图片解析失败", Toast.LENGTH_SHORT).show();
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
        req.scene = SendMessageToWX.Req.WXSceneTimeline;
        api.sendReq(req);

    }


    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
    }
}


