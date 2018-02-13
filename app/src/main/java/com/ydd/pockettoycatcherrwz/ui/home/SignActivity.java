package com.ydd.pockettoycatcherrwz.ui.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.os.Handler;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.UserManager;
import com.ydd.pockettoycatcherrwz.entity.GetMessageForWx;
import com.ydd.pockettoycatcherrwz.entity.GetPointsEverDay;
import com.ydd.pockettoycatcherrwz.entity.SignListInfo;
import com.ydd.pockettoycatcherrwz.entity.SignSuccessInfo;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.CetPointsDayMessages;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.RewardsPoints;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.Share_accice;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.SignListRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.SignRequest;
import com.ydd.pockettoycatcherrwz.ui.BaseActivity;
import com.ydd.pockettoycatcherrwz.ui.personal.BottomShareDialog;
import com.ydd.pockettoycatcherrwz.util.Constants;
import com.ydd.pockettoycatcherrwz.util.ImageUtil;
import com.ydd.pockettoycatcherrwz.util.ListUtil;
import com.ydd.pockettoycatcherrwz.util.LogUtil;
import com.ydd.pockettoycatcherrwz.util.WaterImgUtil;
import com.ydd.pockettoycatcherrwz.widget.CommonTitleBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class SignActivity extends BaseActivity {

    private CommonTitleBar titleBar;
    private SignItem_newView mItemView1;
    private SignItem_newView mItemView2;
    private SignItem_newView mItemView3;
    private SignItem_newView mItemView4;
    private SignItem_newView mItemView5;
    private SignItem_newView mItemView6;
    private SignItem_newView mItemView7;
    private TextView sign_text_suresign;
    private LinearLayout sign_addliear_view;
    private Map<String, Integer> map;
    private BottomShareDialog dialog;
    private boolean mIsSuccess;
    private PullToRefreshScrollView pullscroll_fresh;
    private LinearLayout sign_main_visabliity;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign);
        titleBar = (CommonTitleBar) findViewById(R.id.common_title_bar);
        titleBar.setTitle("福利中心");
        titleBar.setBackground(R.mipmap.sign_title_background);
        initMap();
        sign_main_visabliity = (LinearLayout) findViewById(R.id.sign_main_visabliity);
        mIsSuccess = getIntent().getBooleanExtra("issuccess", false);
        pullscroll_fresh = (PullToRefreshScrollView) findViewById(R.id.pullscroll_fresh);
        pullscroll_fresh.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        //上拉监听函数
        pullscroll_fresh.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                //执行刷新函数
                loadDatas();
            }
        });
        autoScroll();
        //sign_main_visabliity
    }

    private void initMap() {
        map = new HashMap<>();
        map.put("week", R.mipmap.sign_addlinear_icon_1);
        map.put("mouth", R.mipmap.sign_addlinear_icon_1);
        map.put("share", R.mipmap.sign_addlinear_icon_2);
        map.put("charge", R.mipmap.sign_addlinear_icon_3);
        map.put("grab", R.mipmap.sign_addlinear_icon_4);
    }

    private void loadDatas() {
        CetPointsDayMessages mCetPointsDayMessages = new CetPointsDayMessages();
        BusinessManager.getInstance().getPointsBydaystation(mCetPointsDayMessages,
                new MyCallback<List<GetPointsEverDay>>() {
                    @Override
                    public void onSuccess(List<GetPointsEverDay> result, String message) {
                        Log.i("dqqrtt", "onSuccess: " + result.toString());
                        addviews(result);
                    }

                    @Override
                    public void onError(String errorCode, String message) {

                    }

                    @Override
                    public void onFinished() {
                        if (pullscroll_fresh.isRefreshing()) {
                            pullscroll_fresh.onRefreshComplete();
                        }
                    }
                });


    }

    private void addviews(final List<GetPointsEverDay> reqsult) {
        dealWithWater();
        sign_main_visabliity.setVisibility(View.VISIBLE);
        sign_addliear_view = (LinearLayout) findViewById(R.id.sign_addliear_view);
        sign_addliear_view.removeAllViews();
        int z=0;
        boolean b1 = false;
        boolean b2= false;
        for(int m=0;m<reqsult.size();m++){
           if(reqsult.get(m).type.equals("week")) {
               b1=true;
            }
            if(reqsult.get(m).type.equals("mouth")) {
                b2=true;
            }
        }
        for (int i = 0; i < reqsult.size(); i++) {
            final int j = i;
            View view = LayoutInflater.from(SignActivity.this).inflate(R.layout.sign_addlinear_view, null);
            //icon图标
            ImageView get_points_day_icon = (ImageView) view.findViewById(R.id.get_points_day_icon);
            //标题
            TextView get_points_day_title = (TextView) view.findViewById(R.id.get_points_day_title);
            //获取的积分或钻石
            TextView get_points_day_title_1 = (TextView) view.findViewById(R.id.get_points_day_title_1);
            //描述
            TextView get_points_day_esc = (TextView) view.findViewById(R.id.get_points_day_esc);
            //状态
            TextView get_points_day_station = (TextView) view.findViewById(R.id.get_points_day_station);
            //下划虚线
            View getpoints_lines_1 = view.findViewById(R.id.getpoints_lines_1);
            //实线
            View getpoints_lines_2 = view.findViewById(R.id.getpoints_lines_2);
            get_points_day_icon.setBackgroundResource(map.get(reqsult.get(i).type));
            get_points_day_title.setText(reqsult.get(i).title);
            get_points_day_title_1.setText(reqsult.get(i).reward);
            if (map.get(reqsult.get(i).type) == R.mipmap.sign_addlinear_icon_1) {
                 if(b1==b2){
                     if(z==0){
                         getpoints_lines_2.setVisibility(View.VISIBLE);
                     } else {
                         getpoints_lines_1.setVisibility(View.VISIBLE);
                     }
                 }else {
                     getpoints_lines_1.setVisibility(View.VISIBLE);
                 }
                get_points_day_title_1.setTextColor(Color.parseColor("#7538F2"));
                if (reqsult.get(i).completeStatus == 0) {
                    get_points_day_station.setText("领取钻石");
                    get_points_day_station.setTextColor(Color.parseColor("#7538F2"));
                    get_points_day_station.setBackgroundResource(R.mipmap.sign_image_linqu);
                }
                if (reqsult.get(i).completeStatus == 1) {
                    get_points_day_station.setBackgroundResource(R.mipmap.get_points_get_1);
                }
                z++;
            } else {
                //积分字体颜色
                get_points_day_title_1.setTextColor(Color.parseColor("#fd8819"));
                getpoints_lines_2.setVisibility(View.VISIBLE);
                //完成
                if (reqsult.get(i).completeStatus == 1) {
                    get_points_day_station.setBackgroundResource(R.mipmap.get_points_get_2);
                }
                //完成未领取
                if (reqsult.get(i).completeStatus == 0) {
                    get_points_day_station.setBackgroundResource(R.mipmap.sign_text_background_1);
                    get_points_day_station.setText("领取");
                }
                //未完成
                if (reqsult.get(i).completeStatus == -1) {
                    get_points_day_station.setBackgroundResource(R.mipmap.sign_text_background_1);
                    //充值
                    if (map.get(reqsult.get(i).type) == R.mipmap.sign_addlinear_icon_3) {
                        get_points_day_station.setText("去充值");
                    }
                    //分享
                    if (map.get(reqsult.get(i).type) == R.mipmap.sign_addlinear_icon_2) {
                        get_points_day_station.setText("去分享");
                    }
                    if (map.get(reqsult.get(i).type) == R.mipmap.sign_addlinear_icon_4) {
                        get_points_day_station.setText("去抓取");
                    }
                }
            }
            get_points_day_esc.setText(reqsult.get(i).desc);
            sign_addliear_view.addView(view);
            //设置监听事件
            get_points_day_station.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //已完成
                    if (reqsult.get(j).completeStatus == 1) {
                        return;
                    }
                    //完成未领取
                    if (reqsult.get(j).completeStatus == 0) {
                        Log.i("ccaa", "onSuccess: " + reqsult.get(j).type);
                        drwapoints(reqsult.get(j).type, reqsult.get(j).reward);
                    }
                    //未完成
                    if (reqsult.get(j).completeStatus == -1) {
                        //去分享
                        if (map.get(reqsult.get(j).type) == R.mipmap.sign_addlinear_icon_2) {
                            showShareDialog();
                        }
                        //去充值
                        if (map.get(reqsult.get(j).type) == R.mipmap.sign_addlinear_icon_3) {
                           // startActivity(new Intent(SignActivity.this, Recharge_newActivity.class));
                            startActivity(new Intent(SignActivity.this, RechargeNewActivity.class));
                        }
                        //去抓取
                        if (map.get(reqsult.get(j).type) == R.mipmap.sign_addlinear_icon_4) {
                            startActivity(new Intent(SignActivity.this, HomeActivity_new.class));
                        }

                    }

                }
            });

        }
        z=0;
    }

    //领取奖励
    private void drwapoints(final String type, final String reword) {
        RewardsPoints mRewardsPoints = new RewardsPoints(type);
        //
        BusinessManager.getInstance().reward(
                mRewardsPoints,
                new MyCallback<Void>() {
                    @Override
                    public void onSuccess(Void result, String message) {
                        String comment = reword;
                        SignSuccessDialog mSignSuccessDialog = new SignSuccessDialog(SignActivity.this,
                                comment,type);
                        mSignSuccessDialog.show();
                        loadDatas();
                    }

                    @Override
                    public void onError(String errorCode, String message) {
                        Log.i("ccaa", "onSuccess: " + message);
                        showToast(message);
                    }

                    @Override
                    public void onFinished() {
                    }
                });

    }


    private void loadSignList() {
        BusinessManager.getInstance().signList(
                new SignListRequest(RunningContext.accessToken),
                new MyCallback<SignListInfo>() {
                    @Override
                    public void onSuccess(SignListInfo result, String message) {
                        if (result != null && !ListUtil.isEmpty(result.list)) {
                            refreshView(result);
                        }
                    }

                    @Override
                    public void onError(String errorCode, String message) {
                    }

                    @Override
                    public void onFinished() {

                    }
                });
    }

    private void refreshView(final SignListInfo signInfoList) {
        if (mItemView1 == null) {
            mItemView1 = (SignItem_newView) findViewById(R.id.sign_SignItem_newView_1);
            mItemView2 = (SignItem_newView) findViewById(R.id.sign_SignItem_newView_2);
            mItemView3 = (SignItem_newView) findViewById(R.id.sign_SignItem_newView_3);
            mItemView4 = (SignItem_newView) findViewById(R.id.sign_SignItem_newView_4);
            mItemView5 = (SignItem_newView) findViewById(R.id.sign_SignItem_newView_5);
            mItemView6 = (SignItem_newView) findViewById(R.id.sign_SignItem_newView_6);
            mItemView7 = (SignItem_newView) findViewById(R.id.sign_SignItem_newView_7);
            sign_text_suresign = (TextView) findViewById(R.id.sign_text_suresign);

            if (signInfoList.status == 0) {
                sign_text_suresign.setText("已签到");
                sign_text_suresign.setBackgroundResource(R.mipmap.sign_text_nosign);
            } else {
                sign_text_suresign.setBackgroundResource(R.mipmap.user_sign_text_sign_off);
                sign_text_suresign
                        .setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                sign();
                                sign_text_suresign.setText("已签到");
                                sign_text_suresign.setBackgroundResource(R.mipmap.sign_text_nosign);
                            }
                        });
            }

        }
        mItemView1.refreshView(signInfoList.list.get(0));
        mItemView2.refreshView(signInfoList.list.get(1));
        mItemView3.refreshView(signInfoList.list.get(2));
        mItemView4.refreshView(signInfoList.list.get(3));
        mItemView5.refreshView(signInfoList.list.get(4));
        mItemView6.refreshView(signInfoList.list.get(5));
        mItemView7.refreshView(signInfoList.list.get(6));
    }

    /**
     * 签到
     */
    private void sign() {
        BusinessManager.getInstance().sign(
                new SignRequest(RunningContext.accessToken),
                new MyCallback<SignSuccessInfo>() {

                    @Override
                    public void onSuccess(SignSuccessInfo result,
                                          String message) {
                        if (result == null) {
                            (SignActivity.this).showResultErrorToast();
                            return;
                        }
                        String comment = "已累计签到 " + result.id + " 天，获得 "
                                + result.points + "积分";
                        SignSuccessDialog mSignSuccessDialog = new SignSuccessDialog(SignActivity.this,
                                comment,"sign");
                        mSignSuccessDialog.show();
                        UserManager.getInstance().refresh();
                        loadSignList();
                    }

                    @Override
                    public void onError(String errorCode, String message) {
                        (SignActivity.this).showToast(message);
                    }

                    @Override
                    public void onFinished() {
                        (SignActivity.this).dismissDialog();
                    }
                });
    }

    private void showShareDialog() {
        if (dialog == null) {
            dialog = new BottomShareDialog(false, this);
        }
        dialog.show();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(GetMessageForWx mGetMessageForWx) {
        //分享
        if (mGetMessageForWx.result == R.string.errcode_success) {
            //drwapoints("share");
            share("share");
        }

    }

    //完成分享任务
    private void share(String type) {
        Share_accice mShare_accice=new Share_accice(type);
        BusinessManager.getInstance().share_submit(
                mShare_accice,
                new MyCallback<Void>() {
                    @Override
                    public void onSuccess(Void result,
                                          String message) {
                        loadDatas();
                    }

                    @Override
                    public void onError(String errorCode, String message) {
                    }

                    @Override
                    public void onFinished() {
                        (SignActivity.this).dismissDialog();
                    }
                });
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        autoScroll();
    }

    /**
     * 自动加载
     */
    private void autoScroll() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                pullscroll_fresh.setRefreshing(true);
                loadSignList();
                loadDatas();
            }
        }, 100);
    }
    private void dealWithWater() {
        if(RunningContext.loginTelInfo==null){
            return;
        }
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
                            SignActivity.this, bmp,
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
                            SignActivity.this, bmp,
                            RunningContext.loginTelInfo.inviteCode);
                    ImageUtil.saveBitmapToDisk(bmp2, Constants.CACHE_PATH,
                            "failed" + RunningContext.loginTelInfo.inviteCode);
                }
            }
        }).start();

    }
}
