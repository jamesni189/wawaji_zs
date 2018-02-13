package com.ydd.pockettoycatcherrwz;

import org.json.JSONObject;

import com.ydd.pockettoycatcherrwz.ui.home.HomeActivity;
import com.ydd.pockettoycatcherrwz.ui.login.SplashActivity;
import com.ydd.pockettoycatcherrwz.util.LogUtil;

import android.app.NotificationManager;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by jia on 17/11/7.
 */
public class MyJpushReceiver extends BroadcastReceiver {
    private static final String TAG = "MyReceiver";

    private NotificationManager nm;

    @Override
    public void onReceive(Context context, Intent intent) {
        if (null == nm) {
            nm = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
        }

        Bundle bundle = intent.getExtras();
        LogUtil.printJ("onReceive - " + intent.getAction() + ", extras: ");

        if (JPushInterface.ACTION_REGISTRATION_ID.equals(intent.getAction())) {
            LogUtil.printJ("JPush用户注册成功");

        } else if (JPushInterface.ACTION_MESSAGE_RECEIVED.equals(intent.getAction())) {
            LogUtil.printJ("接受到推送下来的自定义消息");

        } else if (JPushInterface.ACTION_NOTIFICATION_RECEIVED.equals(intent.getAction())) {
            LogUtil.printJ("接受到推送下来的通知");

            receivingNotification(context, bundle);

        } else if (JPushInterface.ACTION_NOTIFICATION_OPENED.equals(intent.getAction())) {
            LogUtil.printJ("用户点击打开了通知");

            openNotification(context, bundle);

        } else {
            LogUtil.printJ("Unhandled intent - " + intent.getAction());
        }
    }

    private void receivingNotification(Context context, Bundle bundle) {
//        String title = bundle.getString(JPushInterface.EXTRA_NOTIFICATION_TITLE);
//        LogUtil.printJ(" title : " + title);
//        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
//        LogUtil.printJ("message : " + message);
//        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
//        LogUtil.printJ("extras : " + extras);
        int buildid=bundle.getInt(JPushInterface.EXTRA_NOTIFICATION_ID);
        String message = bundle.getString(JPushInterface.EXTRA_MESSAGE);
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        // 使用notification
        // 使用广播或者通知进行内容的显示


    }

    private void openNotification(Context context, Bundle bundle) {
        String extras = bundle.getString(JPushInterface.EXTRA_EXTRA);
        String myValue = "";
        try {
            JSONObject extrasJson = new JSONObject(extras);
            myValue = extrasJson.optString("myKey");
        } catch (Exception e) {
            LogUtil.printJ("Unexpected: extras is not a valid json" + e.getMessage());
            return;
        }


        if (!PTCApplication.isHomeLoaded) {
            Intent intent = new Intent(Intent.ACTION_MAIN);
            intent.addCategory(Intent.CATEGORY_LAUNCHER);
            intent.setClass(context, SplashActivity.class);
            intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED);
            context.startActivity(intent);
        } else {
            Intent intent1 = new Intent();
            intent1.setClass(context, HomeActivity.class);
            intent1.setFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            context.startActivity(intent1);
        }


//        if (TYPE_THIS.equals(myValue)) {
//            Intent mIntent = new Intent(context, ThisActivity.class);
//            mIntent.putExtras(bundle);
//            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(mIntent);
//        } else if (TYPE_ANOTHER.equals(myValue)){
//            Intent mIntent = new Intent(context, AnotherActivity.class);
//            mIntent.putExtras(bundle);
//            mIntent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
//            context.startActivity(mIntent);
//        }
    }

}

