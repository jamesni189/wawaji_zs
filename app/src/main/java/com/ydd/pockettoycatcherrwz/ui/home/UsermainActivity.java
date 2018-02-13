package com.ydd.pockettoycatcherrwz.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.UserManager;
import com.ydd.pockettoycatcherrwz.entity.UserInfo;
import com.ydd.pockettoycatcherrwz.entity.User_mian_Messages;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.getMessagesforUser;
import com.ydd.pockettoycatcherrwz.ui.BaseActivity;
import com.ydd.pockettoycatcherrwz.ui.login.LoginActivity;
import com.ydd.pockettoycatcherrwz.ui.login.SplashActivity;
import com.ydd.pockettoycatcherrwz.ui.order.OrderListActivity;
import com.ydd.pockettoycatcherrwz.ui.personal.AddressManageActivity;
import com.ydd.pockettoycatcherrwz.ui.personal.InviteFriendActivity;
import com.ydd.pockettoycatcherrwz.ui.personal.PersonalInfoActivity;
import com.ydd.pockettoycatcherrwz.ui.personal.RechargeRecordListActivity;
import com.ydd.pockettoycatcherrwz.ui.personal.SettingsActivity;
import com.ydd.pockettoycatcherrwz.ui.record.GrabRecordsActivity;
import com.ydd.pockettoycatcherrwz.util.ImgLoaderUtil;
import com.ydd.pockettoycatcherrwz.widget.CommonTitleBar;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

public class UsermainActivity extends BaseActivity implements View.OnClickListener {

    private CommonTitleBar mTitleBar;
    private ImageView iv_dlg_personal_avatar;
    private TextView tv_dlg_personal_name;
    private LinearLayout linearLayout;
    private TextView user_main_diamonds;
    private LinearLayout user_main_diamonds_linear;
    private TextView user_main_points_shop;
    private LinearLayout user_main_points_shop_linear;
    private TextView user_main_takes;
    private LinearLayout user_main_takes_linear;
    private TextView user_main_wawadai;
    private LinearLayout user_main_wawadai_linear;
    private LinearLayout user_main_adreess;
    private LinearLayout user_main_orders;
    private LinearLayout user_main_repay;
    private LinearLayout user_main_settings;
    private LinearLayout user_main_vip;
    private ImageView user_main_pic_vip;
    private LinearLayout user_mian_login_now;
    private TextView user_mian_vip_station;
    private PullToRefreshScrollView myscrollview_usermian;
    private LinearLayout user_mian_arrow;
    private LinearLayout scroll_view_addview;
    private LinearLayout user_mian_rechargr_message;
    private View view;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_usermain);
        mTitleBar = (CommonTitleBar) findViewById(R.id.common_title_bar);
        mTitleBar.setTitle("我的");
        if (RunningContext.loginTelInfo != null) {
                initDatas();
        }
//        else {
//            initviews(null);
//        }
    }


    private void initDatas() {
        getMessagesforUser mgetMessagesforUser = new getMessagesforUser();
        BusinessManager.getInstance().getUserMessageV15(mgetMessagesforUser,
                new MyCallback<User_mian_Messages>() {
                    @Override
                    public void onSuccess(User_mian_Messages result, String message) {
                        Log.i("dqrrrrrr", "onSuccess: " + result);
                        initviews(result);
                        UserManager.getInstance().refresh();
                    }

                    @Override
                    public void onError(String errorCode, String message) {
                    }

                    @Override
                    public void onFinished() {
                    }
                });

    }

    private void addListener() {
        linearLayout.setOnClickListener(this);
        user_main_diamonds_linear.setOnClickListener(this);
        user_main_points_shop_linear.setOnClickListener(this);
        user_main_takes_linear.setOnClickListener(this);
        user_main_wawadai_linear.setOnClickListener(this);
        user_main_adreess.setOnClickListener(this);
        user_main_orders.setOnClickListener(this);
        user_main_repay.setOnClickListener(this);
        user_main_settings.setOnClickListener(this);
        user_main_vip.setOnClickListener(this);
        user_mian_login_now.setOnClickListener(this);
        user_mian_arrow.setOnClickListener(this);
        user_mian_rechargr_message.setOnClickListener(this);
    }

    private void initviews(User_mian_Messages result) {
        //动态添加的linearlayout
        scroll_view_addview = (LinearLayout) findViewById(R.id.scroll_view_addview);
        view = LayoutInflater.from(UsermainActivity.this).inflate(R.layout.user_mian_scrollview_layout, null);
        myscrollview_usermian = (PullToRefreshScrollView) findViewById(R.id.myscrollview_usermian);
        myscrollview_usermian.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        //上拉监听函数

        scroll_view_addview.removeAllViews();
        scroll_view_addview.addView(view);
        //头像
        iv_dlg_personal_avatar = (ImageView) findViewById(R.id.iv_dlg_personal_avatar);
        //昵称
        tv_dlg_personal_name = (TextView) findViewById(R.id.tv_dlg_personal_name);
        //个人信息
        linearLayout = (LinearLayout) findViewById(R.id.user_main_persson);
        //箭头
        //钻石
        user_main_diamonds = (TextView) view.findViewById(R.id.user_main_Diamonds);
        user_main_diamonds_linear = (LinearLayout) view.findViewById(R.id.user_main_Diamonds_linear);
        //积分商城
        user_main_points_shop = (TextView) view.findViewById(R.id.user_main_points_shop);
        user_main_points_shop_linear = (LinearLayout) view.findViewById(R.id.user_main_points_shop_linear);


        user_mian_arrow = (LinearLayout) findViewById(R.id.user_mian_arrow);
        //用户是否是会员
        user_main_pic_vip = (ImageView) findViewById(R.id.user_main_pic_vip);

        //立即登陆
        user_mian_login_now = (LinearLayout) findViewById(R.id.user_mian_login_now);

        //抓取记录
        user_main_takes = (TextView) view.findViewById(R.id.user_main_takes);
        user_main_takes_linear = (LinearLayout) view.findViewById(R.id.user_main_takes_linear);
        //娃娃带
        user_main_wawadai = (TextView) view.findViewById(R.id.user_main_wawadai);
        user_main_wawadai_linear = (LinearLayout) view.findViewById(R.id.user_main_wawadai_linear);
        //地址管理
        user_main_adreess = (LinearLayout) view.findViewById(R.id.user_main_adreess);
        //订单中心
        user_main_orders = (LinearLayout) view.findViewById(R.id.user_main_orders);
        //兑换中心
        user_main_repay = (LinearLayout) view.findViewById(R.id.user_main_repay);
        //设置
        user_main_settings = (LinearLayout) view.findViewById(R.id.user_main_settings);
        //会员中心
        user_main_vip = (LinearLayout) view.findViewById(R.id.user_main_vip);
        //会员状态
        user_mian_vip_station = (TextView) view.findViewById(R.id.user_mian_vip_station);
        //充值记录
        user_mian_rechargr_message = (LinearLayout) findViewById(R.id.user_mian_rechargr_message);


        if(RunningContext.loginTelInfo != null){
            myscrollview_usermian.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
                @Override
                public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                    //执行刷新函数
                    if (RunningContext.loginTelInfo != null) {
                        initDatas();
                    }
                }
            });
            user_main_takes.setText(result.grabCounts + "");
            user_main_wawadai.setText(result.dollCounts + "");
            //用户不是会员
            if (result.hasMember == 0) {
                user_main_vip.setVisibility(View.GONE);
            } else {
                user_main_pic_vip.setVisibility(View.VISIBLE);
                String sText = result.erpireDate + "<font color='#fd485c'>到期</font>";
                user_mian_vip_station.setText(Html.fromHtml(sText));
            }
            addListener();
        }else {
            user_main_vip.setVisibility(View.GONE);
            user_mian_login_now.setVisibility(View.VISIBLE);
            user_mian_login_now.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(UsermainActivity.this,SplashActivity.class));
                    finish();
                }
            });
        }
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(UserInfo userInfo) {
        if (userInfo != null) {
            Log.i("dqqttt", "onMessage: " + userInfo);
            ImgLoaderUtil.displayImage(RunningContext.loginTelInfo.avatar,
                    iv_dlg_personal_avatar, UsermainActivity.this.getResources()
                            .getDrawable(R.mipmap.user_mian_icon_new_1));
            tv_dlg_personal_name.setText(RunningContext.loginTelInfo.nickname);
            user_main_diamonds.setText(
                    String.valueOf(" " + RunningContext.loginTelInfo.money)
                            + " ");
            user_main_points_shop.setText(
                    String.valueOf(" " + RunningContext.loginTelInfo.points)
                            + " ");
            if (myscrollview_usermian.isRefreshing()) {
                myscrollview_usermian.onRefreshComplete();
            }
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.user_mian_arrow:
            case R.id.user_main_persson:
                //个人中心
                startActivity(new Intent(UsermainActivity.this, PersonalInfoActivity.class));
                break;
            case R.id.user_main_Diamonds_linear:
                //我的钻石
            //    startActivity(new Intent(UsermainActivity.this, Recharge_newActivity.class));
                startActivity(new Intent(UsermainActivity.this, RechargeNewActivity.class));
                break;
            case R.id.user_main_points_shop_linear:
                //积分商城
                Toast.makeText(UsermainActivity.this, "暂未开放", Toast.LENGTH_SHORT).show();
                break;
            case R.id.user_main_takes_linear:
                //抓取记录
                startActivity(new Intent(UsermainActivity.this, GrabRecordsActivity.class));
                break;
            case R.id.user_main_wawadai_linear:
                //娃娃带
                startActivity(new Intent(UsermainActivity.this, ToysBagActivity.class));
                break;
            case R.id.user_main_adreess:
                //地址管理
                startActivity(new Intent(UsermainActivity.this, AddressManageActivity.class));
                break;
            case R.id.user_main_orders:
                //订单中心
                startActivity(new Intent(UsermainActivity.this, OrderListActivity.class));
                break;
            case R.id.user_main_repay:
                //兑换中心
                startActivity(new Intent(UsermainActivity.this, InviteFriendActivity.class));
                break;
            case R.id.user_main_settings:
                //设置 SettingsActivity
                startActivity(new Intent(UsermainActivity.this, SettingsActivity.class));
                break;
            case R.id.user_main_vip:
                //会员中心
                startActivity(new Intent(UsermainActivity.this, Vip_mainActivity.class));
                break;
            case R.id.user_mian_login_now:
                //立即登陆
                startActivity(new Intent(UsermainActivity.this, LoginActivity.class));
                break;

            case R.id.user_mian_rechargr_message:
                //充值记录
                startActivity(new Intent(UsermainActivity.this, RechargeRecordListActivity.class));
                break;
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        initDatas();
    }
}
