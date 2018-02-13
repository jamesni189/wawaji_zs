package com.ydd.pockettoycatcherrwz.ui.personal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.alipay.sdk.app.PayTask;
import com.google.gson.Gson;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.UserManager;
import com.ydd.pockettoycatcherrwz.entity.RechargeGridBean;
import com.ydd.pockettoycatcherrwz.entity.WXentity.WXpayResult;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.ChargeRequest;
import com.ydd.pockettoycatcherrwz.ui.BaseTitleActivity;
import com.ydd.pockettoycatcherrwz.util.Constants;
import com.ydd.pockettoycatcherrwz.util.PayResult;
import com.ydd.pockettoycatcherrwz.widget.StrokeTextView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.text.DecimalFormat;
import java.util.Map;

/**
 * 确认充值页
 * <p>
 * Created by czhang on 17/11/1.
 */

public class RechargeConfirmActivity extends BaseTitleActivity {

    public static final String INTENT_EXTRA_KEY_RECHARGE_INFO = "extra_recharge_info";

    private static final int SDK_PAY_FLAG = 1;
    /**
     * 充值信息
     */
    private RechargeGridBean mRechargeItemInfo;
    /**
     * 宝石数
     */
    private TextView mMoneyTv;
    /**
     * 价格
     */
    private TextView mPriceTv;
    /**
     * 支付宝checkbox
     */
    private CheckBox mZfbCb;
    /**
     * 微信checkbox
     */
    private CheckBox mWxCb;

    private IWXAPI iwxapi;
    private LinearLayout recharge_linear_addview;
    private TextView rechange_text_dec_1;
    private StrokeTextView rechange_text_price;
    private LinearLayout common_item_left_messgae;
    //  private CheckBox cb_login_protocol_1;
    private TextView tv_login_protocol_2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mRechargeItemInfo = (RechargeGridBean) getIntent()
                .getSerializableExtra(INTENT_EXTRA_KEY_RECHARGE_INFO);
        if (mRechargeItemInfo == null) {
            return;
        }
        setContent(R.layout.activity_recharge_confirm);
        //setTitle("确认充值");
        setTitle("订单确认");
        iwxapi = WXAPIFactory.createWXAPI(this, Constants.WX_APPID);
        // 将该app注册到微信
        iwxapi.registerApp(Constants.WX_APPID);
        initView();
    }

    private void initView() {
        //普通充值
        View v = LayoutInflater.from(RechargeConfirmActivity.this).inflate(R.layout.linear_addview_view_1, null);
        mMoneyTv = (TextView) v.findViewById(R.id.tv_recharge_confirm_money);
        mPriceTv = (TextView) v.findViewById(R.id.tv_recharge_confirm_price);
        rechange_text_price = (StrokeTextView) v.findViewById(R.id.rechange_text_price);
        rechange_text_dec_1 = (TextView) v.findViewById(R.id.rechange_text_dec_1);
        //会员充值
        View vip = LayoutInflater.from(RechargeConfirmActivity.this).inflate(R.layout.rechange_vip_layout, null);
        StrokeTextView rechange_text_price_2 = (StrokeTextView) vip.findViewById(R.id.rechange_text_price_2);
        TextView tv_recharge_confirm_des_1 = (TextView) vip.findViewById(R.id.tv_recharge_confirm_des_1);
        TextView tv_recharge_confirm_price_1 = (TextView) vip.findViewById(R.id.tv_recharge_confirm_price_1);
        TextView rechange_text_dec_2 = (TextView) vip.findViewById(R.id.rechange_text_dec_2);


        mZfbCb = (CheckBox) findViewById(R.id.cb_recharge_confirm_zfb);
        mWxCb = (CheckBox) findViewById(R.id.cb_recharge_confirm_wx);
        common_item_left_messgae = (LinearLayout) findViewById(R.id.common_item_left_messgae);
        //check
        //   cb_login_protocol_1 = (CheckBox) findViewById(R.id.cb_login_protocol_1);
        //购买规则
        tv_login_protocol_2 = (TextView) findViewById(R.id.tv_login_protocol_2);
        tv_login_protocol_2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //    Log.i("vsassrt", "onClick: ");
//                startActivity(new Intent(RechargeConfirmActivity.this, WebActivity_1.class));
            }
        });
        findViewById(R.id.ln_recharge_confirm_zfb)
                .setOnClickListener(mOnClickListener);
        findViewById(R.id.ln_recharge_confirm_wx)
                .setOnClickListener(mOnClickListener);
        findViewById(R.id.btn_recharge_confirm)
                .setOnClickListener(mOnClickListener);
        recharge_linear_addview = (LinearLayout) findViewById(R.id.recharge_linear_addview);
        if (mRechargeItemInfo.kinds.equals("week") || mRechargeItemInfo.kinds.equals("mouth")) {
            common_item_left_messgae.setVisibility(View.VISIBLE);
            recharge_linear_addview.addView(vip);

            rechange_text_price_2.setText(mRechargeItemInfo.title);


            DecimalFormat df = new DecimalFormat("######0.00");
            tv_recharge_confirm_des_1.setText(mRechargeItemInfo.desc);
            tv_recharge_confirm_price_1.setText("￥" + df.format(Double.valueOf(mRechargeItemInfo.price)) + " ");
//            String comment = getResources()
//                    .getString(R.string.sure_orders, mRechargeItemInfo.detail);
            rechange_text_dec_2.setText(mRechargeItemInfo.detail);
        } else {
            recharge_linear_addview.addView(v);
            rechange_text_price.setText(mRechargeItemInfo.money + "");
            mMoneyTv.setText(String.valueOf(mRechargeItemInfo.desc));
            DecimalFormat df = new DecimalFormat("######0.00");
            mPriceTv.setText("￥" + df.format(Double.valueOf(mRechargeItemInfo.price)) + " ");
            rechange_text_dec_1.setText(getResources().getString(R.string.app_name) + "充值");
        }
        mZfbCb.setChecked(true);
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.ln_recharge_confirm_zfb:
                    // 支付宝
                    doZfbClick();
                    break;
                case R.id.ln_recharge_confirm_wx:
                    // 微信
                    doWxClick();
                    break;
                case R.id.btn_recharge_confirm:
                    // 确认支付
                    if (mZfbCb.isChecked()) {

                        doZFBzf();

                    } else {
                        doWXzf();

                    }

                    break;
            }
        }
    };

    private void doZfbClick() {
        mZfbCb.setChecked(true);
        mWxCb.setChecked(false);
    }

    private void doWxClick() {
        mZfbCb.setChecked(false);
        mWxCb.setChecked(true);
    }

    private void doZFBzf() {
        BusinessManager
                .getInstance().charge(
                new ChargeRequest(RunningContext.accessToken,
                        mRechargeItemInfo.id + "", "1", mRechargeItemInfo.kinds),
                new MyCallback<String>() {
                    @Override
                    public void onSuccess(String result,
                                          final String message) {
                        final String s = result;
                        Runnable payRunnable = new Runnable() {
                            @Override
                            public void run() {
                                PayTask alipay = new PayTask(
                                        RechargeConfirmActivity.this);
                                Map<String, String> result = alipay
                                        .payV2(s, true);
                                Log.i("msp", result.toString());
                                Message msg = new Message();
                                msg.what = SDK_PAY_FLAG;
                                msg.obj = result;
                                mHandler.sendMessage(msg);
                            }
                        };
                        Thread payThread = new Thread(payRunnable);
                        payThread.start();
                    }

                    @Override
                    public void onError(String errorCode,
                                        String message) {
                        showToast(message);
                    }

                    @Override
                    public void onFinished() {

                    }
                });
    }

    private void doWXzf() {
        BusinessManager
                .getInstance().charge(
                new ChargeRequest(RunningContext.accessToken,
                        mRechargeItemInfo.id + "", "2", mRechargeItemInfo.kinds),
                new MyCallback<String>() {
                    @Override
                    public void onSuccess(String result,
                                          final String message) {
                        Gson gson = new Gson();
                        WXpayResult wXpayResult = gson.fromJson(result,
                                WXpayResult.class);
                        if (wXpayResult == null) {
                            Toast.makeText(RechargeConfirmActivity.this,
                                    "支付参数错误", Toast.LENGTH_SHORT)
                                    .show();
                            return;
                        }
                        PayReq request = new PayReq();
                        request.appId = wXpayResult.app_id;
                        request.partnerId = wXpayResult.partner_id;
                        request.prepayId = wXpayResult.prepay_id;
                        request.packageValue = "Sign=WXPay";
                        request.nonceStr = wXpayResult.nonce_str;
                        request.timeStamp = wXpayResult.time_stamp;
                        request.sign = wXpayResult.pay_sign;
                        iwxapi.sendReq(request);
                    }

                    @Override
                    public void onError(String errorCode,
                                        String message) {
                        showToast(message);
                    }

                    @Override
                    public void onFinished() {

                    }
                });
    }

    private Handler mHandler = new Handler() {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    @SuppressWarnings("unchecked")
                    PayResult payResult = new PayResult(
                            (Map<String, String>) msg.obj);
                    /**
                     * 对于支付结果，请商户依赖服务端的异步通知结果。同步通知结果，仅作为支付结束的通知。
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息
                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为9000则代表支付成功
                    if (TextUtils.equals(resultStatus, "9000")) {
                        // 该笔订单是否真实支付成功，需要依赖服务端的异步通知。
                        Toast.makeText(RechargeConfirmActivity.this, "支付成功",
                                Toast.LENGTH_SHORT).show();
                        EventBus.getDefault().post(new WXpayResult());
                        UserManager.getInstance().refresh();
                        startActivity(new Intent(RechargeConfirmActivity.this, RechargeRecordListActivity.class));
                        finish();
                    } else {
                        // 该笔订单真实的支付结果，需要依赖服务端的异步通知。
                        Toast.makeText(RechargeConfirmActivity.this, "支付失败",
                                Toast.LENGTH_SHORT).show();
                    }
                    break;
                }
            }
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(WXpayResult userInfo) {
        if (userInfo != null) {
            startActivity(new Intent(RechargeConfirmActivity.this, RechargeRecordListActivity.class));
            finish();
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}
