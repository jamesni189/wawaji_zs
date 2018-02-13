package com.ydd.pockettoycatcherrwz.ui.home;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.view.ViewPager;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.lidroid.xutils.HttpUtils;
import com.lidroid.xutils.exception.HttpException;
import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.ResponseInfo;
import com.lidroid.xutils.http.callback.RequestCallBack;
import com.lidroid.xutils.http.client.HttpRequest;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.entity.RechargeGridBean;
import com.ydd.pockettoycatcherrwz.entity.Vip_Week;
import com.ydd.pockettoycatcherrwz.entity.Vip_user_Info;
import com.ydd.pockettoycatcherrwz.entity.Vip_user_WeekorMonth;
import com.ydd.pockettoycatcherrwz.entity.WeekOrMouthBean;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.GetUserVipKinds;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.GetweekorMonthMessage;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.RewardsPoints;
import com.ydd.pockettoycatcherrwz.ui.BaseActivity;
import com.ydd.pockettoycatcherrwz.ui.adapter.MyFragmentPageAdapter2;
import com.ydd.pockettoycatcherrwz.ui.fragment.MonthVipFragment;
import com.ydd.pockettoycatcherrwz.ui.fragment.WeekVipFragment;
import com.ydd.pockettoycatcherrwz.ui.personal.RechargeConfirmActivity;
import com.ydd.pockettoycatcherrwz.util.Constants;

import java.util.List;

public class VipUserActivity extends BaseActivity {

    private ViewPager view_pager;
    private WeekVipFragment weekVipFragment;
    private MonthVipFragment monthVipFragment;
    private MyFragmentPageAdapter2 mfpa;
    private List<Fragment> listfragment;

    private ImageView back_left;

    private TextView tv_vip_title;
    private TextView vip_end_time;
    private TextView tv_vip_add_money; //续费
    private TextView tv_get_tip2;//已领取
    private TextView tv_today_money; //今日领取
    private TextView tv_get_jiangli; //查看明细
    private TextView tv_geted_money; //
    private TextView tv_get_; //立即领取

    private  Vip_user_WeekorMonth resultData;
    private  String result2;
    private  String url;
    private  Vip_Week vip_week ;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vip_user);

        //view_pager = (ViewPager) findViewById(R.id.view_pager);
        back_left = (ImageView) findViewById(R.id.back_left);
       /* listfragment = new ArrayList<>();
        weekVipFragment = new WeekVipFragment();
        monthVipFragment  = new MonthVipFragment();
        listfragment.add(weekVipFragment);
        listfragment.add(monthVipFragment);*/


       /* FragmentManager fm=getSupportFragmentManager();
         mfpa=new MyFragmentPageAdapter2(fm, listfragment); //new myFragmentPagerAdater记得带上两个参数

        view_pager.setAdapter(mfpa);
        view_pager.setCurrentItem(0);*/

        back_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        findViewById(R.id.user_sign_vip).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VipUserActivity.this, NewRankActivity.class));
            }
        });
        initviews();

        //initdatas();

    }

    private  void initviews(){
        tv_vip_title = (TextView) findViewById(R.id.tv_vip_title);
        vip_end_time = (TextView) findViewById(R.id.vip_end_time);
        tv_geted_money = (TextView) findViewById(R.id.tv_geted_money);
        tv_vip_add_money = (TextView) findViewById(R.id.tv_vip_add_money);
        tv_get_tip2 = (TextView) findViewById(R.id.tv_get_tip2);
        tv_today_money = (TextView) findViewById(R.id.tv_today_money);
        tv_get_jiangli = (TextView)findViewById(R.id.tv_get_jiangli);
        tv_get_ = (TextView) findViewById(R.id.tv_get_);
    }
    private void initdatas() {
        GetUserVipKinds mGetUserVipKinds = new GetUserVipKinds();
        BusinessManager.getInstance().getUserVipKings(mGetUserVipKinds,
                new MyCallback<Vip_user_WeekorMonth>() {
                    @Override
                    public void onSuccess(Vip_user_WeekorMonth result, String message) {
                        Log.i("dqrrrrrr", "onSuccess: " + result.toString());
                        //setView(result);
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

   public  void setView(final Vip_user_WeekorMonth result){
       /*  if (result.week != null){
            resultData = result;
            String str = "<u>立即续费</u>";
            vip_end_time.setText(result.week.erpireDate + "到期, ");
            tv_vip_add_money.setText(Html.fromHtml(str));
            if (result.week.receiveStatus == 0) {
                //vip_stataion.setText("尚未领取专属奖励");
                tv_get_.setText("立即领取");
                tv_get_.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        drwapoints("week", result.week.dayMoney + "");
                    }
                });

            }
            if (result.week.receiveStatus == 1) {
                //  vip_stataion.setText("已领取专属奖励");
                tv_get_.setText("已领取");
            }
            tv_today_money.setText(result.week.totalMoney + "");
            tv_geted_money.setText(result.week.dayMoney );
            tv_get_jiangli.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(VipUserActivity.this, LookForDeter_Activity.class);
                    intent.putExtra("kinds", 1);
                    intent.putExtra("VIP_week", result.week);
                    startActivity(intent);
                }
            });
            tv_vip_add_money.setOnClickListener(new View.OnClickListener() {
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
                                    Intent intent = new Intent(VipUserActivity.this, RechargeConfirmActivity.class);
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

        }else if (result.mouth != null){
            tv_vip_title.setText("月卡会员");
            {
                String str = "<u>立即续费</u>";
                vip_end_time.setText(result.mouth.erpireDate + "到期, ");
                tv_vip_add_money.setText(Html.fromHtml(str));
                if (result.mouth.receiveStatus == 0) {
                    //vip_stataion.setText("尚未领取专属奖励");
                    tv_get_.setText("立即领取");
                    tv_get_.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            drwapoints("month", result.mouth.dayMoney + "");
                        }
                    });

                }
                if (result.mouth.receiveStatus == 1) {
                    //  vip_stataion.setText("已领取专属奖励");
                    tv_get_.setText("已领取");
                }

                tv_today_money.setText(result.mouth.totalMoney + "");
                tv_geted_money.setText(result.mouth.dayMoney +"");
                tv_get_jiangli.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(VipUserActivity.this, LookForDeter_Activity.class);
                        intent.putExtra("VIP_mouth", result.mouth);
                        intent.putExtra("kinds", 2);
                        startActivity(intent);
                    }
                });

                tv_vip_add_money.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        GetweekorMonthMessage monthMessage = new GetweekorMonthMessage(2);
                        BusinessManager.getInstance().getCardWeekorMouth(monthMessage,
                                new MyCallback<WeekOrMouthBean>() {
                                    @Override
                                    public void onSuccess(WeekOrMouthBean result, String message) {
                                        Log.i("caaaqq", "onSuccess: " + result);
                                        RechargeGridBean mRechargeGridBean = new RechargeGridBean(result.price + "", result.money,
                                                result.desc, 1, 0, result.mark, "mouth", result.detail,null);
                                        Intent intent = new Intent(VipUserActivity.this, RechargeConfirmActivity.class);
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
            }
        }*/
    }

    //领取奖励
    private void drwapoints(final int type, final String reword) {
        RewardsPoints mRewardsPoints = new RewardsPoints(type);
        //有问题
        BusinessManager.getInstance().reward(
                mRewardsPoints,
                new MyCallback<Void>() {
                    @Override
                    public void onSuccess(Void result, String message) {
                       // initdatas();
                        String comment = "恭喜领取"+reword+"钻石";
                        SignSuccessDialog mSignSuccessDialog = new SignSuccessDialog(VipUserActivity.this,
                                comment, type+"");
                        mSignSuccessDialog.show();
                        tv_get_.setText("已领取");
                    }

                    @Override
                    public void onError(String errorCode, String message) {
                        Log.i("ccaa", "onSuccess: " + message);

                    }

                    @Override
                    public void onFinished() {
                    }
                });
    }

    public String xutilsPost(){

        url = Constants.BASE_URL + "/member/center/v15";
        RequestParams params = new RequestParams();
        params.addQueryStringParameter("key", "8dd758066c594324962cc2de7ee7a306" );
        params.addQueryStringParameter("accessToken", RunningContext.accessToken);

        HttpUtils http = new HttpUtils();
        http.configCurrentHttpCacheExpiry(1000 * 10); //设置超时时间   10s
        http.send(HttpRequest.HttpMethod.GET ,
                url ,
                params,
                new RequestCallBack<String>() {

                    @Override
                    public void onStart() {
                    }

                    @Override
                    public void onLoading(long total, long current, boolean isUploading) {
                    }

                    @Override
                    public void onSuccess(ResponseInfo<String> responseInfo) {
                        result2 = responseInfo.result.toString() ;
                        if (result2 != null){
                            Vip_user_Info mvip=    new Gson().fromJson(result2,Vip_user_Info.class);
                              Log.i("===",mvip+"");
                              setNewView(mvip);
                        }
                    }

                    @Override
                    public void onFailure(HttpException error, String msg) {
                        Log.i("===","");
                    }
                });

        return result2 ;
    }



    public  void setNewView(final Vip_user_Info mvip){
        List<Vip_Week> data = mvip.data;
        vip_week = data.get(0);
        if (vip_week.type == 1){//周卡
            String str = "<u>立即续费</u>";
            vip_end_time.setText(vip_week.erpireDate + "到期, ");
            tv_vip_add_money.setText(Html.fromHtml(str));
            if (vip_week.receiveStatus == 0) {
                //vip_stataion.setText("尚未领取专属奖励");
                tv_get_.setText("立即领取");
                tv_get_.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        drwapoints(1, vip_week.dayMoney + "");
                    }
                });

            }
            if (vip_week.receiveStatus == 1) {
                //  vip_stataion.setText("已领取专属奖励");
                tv_get_.setText("已领取");
            }

            tv_today_money.setText(vip_week.dayMoney + "");
            tv_geted_money.setText(vip_week.totalMoney +"");
            tv_get_jiangli.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(VipUserActivity.this, LookForDeter_Activity.class);
                    intent.putExtra("kinds", 1);
                    intent.putExtra("VIP_week", vip_week);
                    startActivity(intent);
                }
            });
            tv_vip_add_money.setOnClickListener(new View.OnClickListener() {
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
                                    Intent intent = new Intent(VipUserActivity.this, RechargeConfirmActivity.class);
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
        }else if (vip_week.type == 2){ //月卡
            tv_vip_title.setText("月卡会员");
                String str = "<u>立即续费</u>";
                vip_end_time.setText(vip_week.erpireDate + "到期, ");
                tv_vip_add_money.setText(Html.fromHtml(str));
                if (vip_week.receiveStatus == 0) {
                    //vip_stataion.setText("尚未领取专属奖励");
                    tv_get_.setText("立即领取");
                    tv_get_.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            drwapoints(2, vip_week.dayMoney + "");
                        }
                    });

                }
                if (vip_week.receiveStatus == 1) {
                    //  vip_stataion.setText("已领取专属奖励");
                    tv_get_.setText("已领取");
                }

                tv_today_money.setText(vip_week.dayMoney+ "");
                tv_geted_money.setText(vip_week.totalMoney  +"");
                tv_get_jiangli.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(VipUserActivity.this, LookForDeter_Activity.class);
                        intent.putExtra("VIP_mouth", vip_week);
                        intent.putExtra("kinds", 2);
                        startActivity(intent);
                    }
                });

            tv_vip_add_money.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    GetweekorMonthMessage monthMessage = new GetweekorMonthMessage(2);
                    BusinessManager.getInstance().getCardWeekorMouth(monthMessage,
                            new MyCallback<WeekOrMouthBean>() {
                                @Override
                                public void onSuccess(WeekOrMouthBean result, String message) {
                                    Log.i("caaaqq", "onSuccess: " + result);
                                    RechargeGridBean mRechargeGridBean = new RechargeGridBean(result.price + "", result.money,
                                            result.desc, 1, 0, result.mark, "mouth", result.detail,null);
                                    Intent intent = new Intent(VipUserActivity.this, RechargeConfirmActivity.class);
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
        }

    }

    @Override
    protected void onResume() {
        super.onResume();
        xutilsPost();

    }
}
