package com.ydd.pockettoycatcherrwz.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.handmark.pulltorefresh.library.PullToRefreshScrollView;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.entity.RechargeGridBean;
import com.ydd.pockettoycatcherrwz.entity.Vip_user_WeekorMonth;
import com.ydd.pockettoycatcherrwz.entity.WeekOrMouthBean;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.GetUserVipKinds;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.GetweekorMonthMessage;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.RewardsPoints;
import com.ydd.pockettoycatcherrwz.ui.BaseActivity;
import com.ydd.pockettoycatcherrwz.ui.personal.RechargeConfirmActivity;
import com.ydd.pockettoycatcherrwz.widget.CommonTitleBar;

public class Vip_mainActivity extends BaseActivity {

    private CommonTitleBar mTitleBar;
    private PullToRefreshListView mPullToRefreshListView;
    private LinearLayout vip_linearlayout_mian;
    private PullToRefreshScrollView myscrollview_usermian;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_main);
        mTitleBar = (CommonTitleBar) findViewById(R.id.common_title_bar);
        mTitleBar.setTitle("会员中心");
        initdatas();
    }

    private void initdatas() {
        GetUserVipKinds mGetUserVipKinds = new GetUserVipKinds();
        BusinessManager.getInstance().getUserVipKings(mGetUserVipKinds,
                new MyCallback<Vip_user_WeekorMonth>() {
                    @Override
                    public void onSuccess(Vip_user_WeekorMonth result, String message) {
                        Log.i("dqrrrrrr", "onSuccess: " + result.toString());
                        initviews(result);
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

    private void initviews(final Vip_user_WeekorMonth result) {
        vip_linearlayout_mian = (LinearLayout) findViewById(R.id.vip_linearlayout_mian);
        myscrollview_usermian = (PullToRefreshScrollView) findViewById(R.id.myscrollview_usermian);
        myscrollview_usermian.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        //上拉监听函数
        myscrollview_usermian.setOnRefreshListener(new PullToRefreshBase.OnRefreshListener<ScrollView>() {
            @Override
            public void onRefresh(PullToRefreshBase<ScrollView> refreshView) {
                //执行刷新函数
                initdatas();
            }
        });
        vip_linearlayout_mian.removeAllViews();
        if (result.week != null) {
            View v = LayoutInflater.from(Vip_mainActivity.this).inflate(R.layout.vip_user_addviews, null);
            //到期时间
            TextView vip_takes_now = (TextView) v.findViewById(R.id.vip_Expiredata);
            //立即续费
            TextView vip_Expiredata_1 = (TextView) v.findViewById(R.id.vip_Expiredata_1);
            //会员卡种类
            TextView vip_kinds = (TextView) v.findViewById(R.id.vip_kinds);
            //会员领取状态
            TextView vip_stataion = (TextView) v.findViewById(R.id.vip_stataion);
            //总的领取
            TextView vip_takes_mumber = (TextView) v.findViewById(R.id.vip_takes_mumber);
            //每日可以领取数
            TextView vip_everday_calltakes = (TextView) v.findViewById(R.id.vip_everday_calltakes);
            //立即领取
            TextView vip_takes_now_1 = (TextView) v.findViewById(R.id.vip_takes_now_1);

            String str = "<u>立即续费</u>";
            vip_takes_now.setText(result.week.erpireDate + "到期, ");

            vip_Expiredata_1.setText(Html.fromHtml(str));
            vip_Expiredata_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //周卡
                    GetweekorMonthMessage monthMessage = new GetweekorMonthMessage(1);
                    BusinessManager.getInstance().getCardWeekorMouth(monthMessage,
                            new MyCallback<WeekOrMouthBean>() {
                                @Override
                                public void onSuccess(WeekOrMouthBean result1, String message) {
                                    Log.i("caaaqq", "onSuccess: " + result1);
                                    RechargeGridBean mRechargeGridBean = new RechargeGridBean(result1.price + "", result1.money,
                                            result1.desc, 1, 0, result1.mark, "week", result1.detail,null);
                                    Intent intent = new Intent(Vip_mainActivity.this, RechargeConfirmActivity.class);
                                    intent.putExtra(
                                            RechargeConfirmActivity.INTENT_EXTRA_KEY_RECHARGE_INFO,
                                            mRechargeGridBean);
                                    startActivity(intent);

                                }

                                @Override
                                public void onError(String errorCode, String message) {
                                }

                                @Override
                                public void onFinished() {
                                }
                            });

                    //     startActivity(new Intent(Vip_mainActivity.this, Recharge_newActivity.class));

                }
            });
            vip_kinds.setText("周卡会员");
            if (result.week.receiveStatus == 0) {
                vip_stataion.setText("尚未领取专属奖励");
                vip_takes_now_1.setText("立即领取");
                vip_takes_now_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        drwapoints("week", result.week.dayMoney + "");
                    }
                });

            }
            if (result.week.receiveStatus == 1) {
                vip_stataion.setText("已领取专属奖励");
                vip_takes_now_1.setText("已领取");
            }
            vip_takes_mumber.setText(result.week.totalMoney + "");
            vip_everday_calltakes.setText(result.week.dayMoney + "钻石");
            //跳到详情页面
            v.findViewById(R.id.looks_vip_messages).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Vip_mainActivity.this, LookForDeter_Activity.class);
                    intent.putExtra("kinds", 1);
                    intent.putExtra("VIP_week", result.week);
                    startActivity(intent);
                }
            });
            findViewById(R.id.user_sign_vip).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Vip_mainActivity.this, SignActivity.class));
                }
            });
            vip_linearlayout_mian.addView(v);
        }
        if (result.mouth != null) {
            View v = LayoutInflater.from(Vip_mainActivity.this).inflate(R.layout.vip_user_addviews, null);
            //到期时间
            TextView vip_takes_now = (TextView) v.findViewById(R.id.vip_Expiredata);
            //立即续费
            TextView vip_Expiredata_1 = (TextView) v.findViewById(R.id.vip_Expiredata_1);
            //会员卡种类
            TextView vip_kinds = (TextView) v.findViewById(R.id.vip_kinds);
            //会员领取状态
            TextView vip_stataion = (TextView) v.findViewById(R.id.vip_stataion);
            //总的领取
            TextView vip_takes_mumber = (TextView) v.findViewById(R.id.vip_takes_mumber);
            //每日可以领取数
            TextView vip_everday_calltakes = (TextView) v.findViewById(R.id.vip_everday_calltakes);
            //立即领取
            TextView vip_takes_now_1 = (TextView) v.findViewById(R.id.vip_takes_now_1);

            String str = "<u>立即续费</u>";
            vip_takes_now.setText(result.mouth.erpireDate + "到期, ");

            vip_Expiredata_1.setText(Html.fromHtml(str));
            vip_Expiredata_1.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    //月卡会员
                    GetweekorMonthMessage monthMessage = new GetweekorMonthMessage(2);
                    BusinessManager.getInstance().getCardWeekorMouth(monthMessage,
                            new MyCallback<WeekOrMouthBean>() {
                                @Override
                                public void onSuccess(WeekOrMouthBean result, String message) {
                                    Log.i("caaaqq", "onSuccess: " + result);
                                    RechargeGridBean mRechargeGridBean = new RechargeGridBean(result.price + "", result.money,
                                            result.desc, 1, 0, result.mark, "mouth", result.detail,null);
                                    Intent intent = new Intent(Vip_mainActivity.this, RechargeConfirmActivity.class);
                                    intent.putExtra(
                                            RechargeConfirmActivity.INTENT_EXTRA_KEY_RECHARGE_INFO,
                                            mRechargeGridBean);
                                    startActivity(intent);

                                }

                                @Override
                                public void onError(String errorCode, String message) {

                                }

                                @Override
                                public void onFinished() {

                                }
                            });

                    //     startActivity(new Intent(Vip_mainActivity.this, Recharge_newActivity.class));

                }
            });
            vip_kinds.setText("月卡会员");
            if (result.mouth.receiveStatus == 0) {
                vip_stataion.setText("尚未领取专属奖励");
                vip_takes_now_1.setText("立即领取");
                vip_takes_now_1.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        drwapoints("mouth", result.mouth.dayMoney + "");
                    }
                });

            }
            if (result.mouth.receiveStatus == 1) {
                vip_stataion.setText("已领取专属奖励");
                vip_takes_now_1.setText("已领取");
            }
            vip_takes_mumber.setText(result.mouth.totalMoney + "");
            vip_everday_calltakes.setText(result.mouth.dayMoney + "钻石");
            v.findViewById(R.id.looks_vip_messages).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(Vip_mainActivity.this, LookForDeter_Activity.class);
                    intent.putExtra("VIP_mouth", result.mouth);
                    intent.putExtra("kinds", 2);
                    startActivity(intent);
                }
            });
            findViewById(R.id.user_sign_vip).setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    startActivity(new Intent(Vip_mainActivity.this, SignActivity.class));
                }
            });
            vip_linearlayout_mian.addView(v);
        }
        if (myscrollview_usermian.isRefreshing()) {
            myscrollview_usermian.onRefreshComplete();
        }
    }

    //领取奖励
    private void drwapoints(final String type, final String reword) {
        RewardsPoints mRewardsPoints = new RewardsPoints(type);
        //有问题
        BusinessManager.getInstance().reward(
                mRewardsPoints,
                new MyCallback<Void>() {
                    @Override
                    public void onSuccess(Void result, String message) {
                        initdatas();
                        String comment = "恭喜领取"+reword+"钻石";
                        SignSuccessDialog mSignSuccessDialog = new SignSuccessDialog(Vip_mainActivity.this,
                                comment, type);
                        mSignSuccessDialog.show();

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

    @Override
    protected void onRestart() {
        initdatas();
        super.onRestart();
    }
}
