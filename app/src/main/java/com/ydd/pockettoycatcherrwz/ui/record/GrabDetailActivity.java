package com.ydd.pockettoycatcherrwz.ui.record;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.Html;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.entity.GrabDetail;
import com.ydd.pockettoycatcherrwz.entity.GrabRecord;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.GetGrabDetailRequest;
import com.ydd.pockettoycatcherrwz.ui.BaseTitleActivity;
import com.ydd.pockettoycatcherrwz.util.BusinessUtil;
import com.ydd.pockettoycatcherrwz.util.CommonUtil;
import com.ydd.pockettoycatcherrwz.util.ImgLoaderUtil;
import com.ydd.pockettoycatcherrwz.widget.XCRoundRectImageView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 抓取详情
 */
public class GrabDetailActivity extends BaseTitleActivity {

    public static final String INTENT_EXTRA_GRAB_RECORD = "grab_record";

    /**
     * 抓取编号
     */
    private TextView mIdTv;
    /**
     * 图像
     */
    private XCRoundRectImageView mToyImgIv;
    /**
     * 名字
     */
    private TextView mNameTv;
    /**
     * 抓取状态
     */
    private TextView mGrabStatusTv;
    /**
     * 时间
     */
    private TextView mTimeTv;
    /**
     * 申诉原因layout
     */
    private LinearLayout mReasonLn;
    /**
     * 申诉原因
     */
    private TextView mReasonTv;
    /**
     * 申诉结果layout
     */
    private LinearLayout mResultLn;
    /**
     * 申诉结果
     */
    private TextView mResultTv;
    /**
     * 申诉layout1
     */
    private LinearLayout mAppealLn;
    /**
     * 申诉layout2
     */
    private LinearLayout mAppealLn_1;

    /**
     * 申诉按钮
     */
    private Button mAppealBtn;
    /**
     * 抓取记录
     */
    private GrabDetail mGrabDetail;

    private GrabRecord mGrabRecord;
    private LinearLayout mContainer;
    /**
     * 申诉对话框
     */
    private AppealDialog mAppealDialog;
    private Button mAppealBtn_1;
    private AppealDialog_2 mAppealDialog2;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        if (intent == null) {
            finish();
            return;
        }
        mGrabRecord = (GrabRecord) intent
                .getSerializableExtra(INTENT_EXTRA_GRAB_RECORD);
        if (mGrabRecord == null) {
            finish();
            return;
        }
        // mockData();
        setContent(R.layout.activity_grab_detail);
        setTitle("抓取详情");
        initView();
        loadData();
    }

    private void initView() {
        mContainer = (LinearLayout) findViewById(R.id.ln_grab_detail_container);
        mIdTv = (TextView) findViewById(R.id.tv_grab_detail_id);
        mToyImgIv = (XCRoundRectImageView) findViewById(R.id.iv_grab_detail_img);
        mNameTv = (TextView) findViewById(R.id.tv_grab_detail_name);
        mGrabStatusTv = (TextView) findViewById(R.id.tv_grab_detail_status);
        mTimeTv = (TextView) findViewById(R.id.tv_grab_detail_time);
        mReasonLn = (LinearLayout) findViewById(R.id.ln_grab_detail_reason);
        mReasonTv = (TextView) findViewById(R.id.tv_grab_detail_reason);
        mResultLn = (LinearLayout) findViewById(R.id.ln_grab_detail_result);
        mResultTv = (TextView) findViewById(R.id.tv_grab_detail_result);
        mAppealLn = (LinearLayout) findViewById(R.id.ln_grab_detail_appeal);
        mAppealLn_1 = (LinearLayout) findViewById(R.id.ln_grab_detail_appeal_1);

        mAppealBtn = (Button) findViewById(R.id.btn_grab_detail_appeal);


        mAppealBtn_1 = (Button) findViewById(R.id.btn_grab_detail_appeal_1);

        mToyImgIv.setOnClickListener(mOnClickListener);
        mAppealBtn.setOnClickListener(mOnClickListener);
        mAppealBtn_1.setOnClickListener(mOnClickListener);
//        CommonUtil.setTextBold(mNameTv);
//        CommonUtil.setTextBold(mGrabStatusTv);
    }

    private void refreshView() {
        // 刷新页面
        mContainer.setVisibility(View.VISIBLE);
        String str = "<font color='#999999'>抓取编号：</font>" + mGrabDetail.id;
        mIdTv.setText(Html.fromHtml(str));
        Drawable defaultDraw = getResources()
                .getDrawable(R.mipmap.pic_zww_default);
        ImgLoaderUtil.displayImage(mGrabRecord.img, mToyImgIv, defaultDraw);
        mToyImgIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent();
                intent.setClass(GrabDetailActivity.this, VideoActivity.class);
                intent.putExtra(VideoActivity.INTENT_EXTRA_URL,
                        mGrabDetail.url);
                startActivity(intent);
            }
        });
        mNameTv.setText(mGrabDetail.productName);
        mTimeTv.setText(mGrabDetail.createTime);
        mGrabStatusTv
                .setText(BusinessUtil.getGrabStatusDesc(mGrabDetail.status));
        // 状态颜色设置
        int statusColorId = mGrabDetail.status == 0 ? R.color.common_text_red
                : R.color.common_text_black;
        mGrabStatusTv.setTextColor(getResources().getColor(statusColorId));

        if (mGrabDetail.status == 0) {
            // 成功
            mAppealLn.setVisibility(View.INVISIBLE);
            mAppealLn_1.setVisibility(View.INVISIBLE);
            return;
        }
        // 失败
        if (mGrabDetail.appeal == null) {
            // 未申诉
            mAppealLn.setVisibility(View.VISIBLE);
            mAppealLn_1.setVisibility(View.VISIBLE);
            return;
        } else {
            mAppealLn.setVisibility(View.INVISIBLE);
            mAppealLn_1.setVisibility(View.INVISIBLE);
        }
        if (!TextUtils.isEmpty(mGrabDetail.appeal.reason)) {
            mReasonLn.setVisibility(View.VISIBLE);
            mReasonTv.setText(mGrabDetail.appeal.reason);
            CommonUtil.setTextBold(
                    (TextView) findViewById(R.id.tv_grab_detail_reason_title));
        }
        if (!TextUtils.isEmpty(mGrabDetail.appeal.result)) {
            mResultLn.setVisibility(View.VISIBLE);
            mResultTv.setText(mGrabDetail.appeal.result);
            CommonUtil.setTextBold(
                    (TextView) findViewById(R.id.tv_grab_detail_result_title));
        }
    }

    private void loadData() {
        showDialog("");
        mContainer.setVisibility(View.INVISIBLE);
        BusinessManager.getInstance().getGrabDetail(
                new GetGrabDetailRequest(mGrabRecord.id,
                        RunningContext.accessToken),
                new MyCallback<GrabDetail>() {
                    @Override
                    public void onSuccess(GrabDetail result, String message) {
                        if (result == null) {
                            showResultErrorToast();
                            return;
                        }
                        mGrabDetail = result;
                        refreshView();
                    }

                    @Override
                    public void onError(String errorCode, String message) {
                        showToast(message);
                    }

                    @Override
                    public void onFinished() {
                        dismissDialog();
                    }
                });
    }

    private View.OnClickListener mOnClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View v) {
            switch (v.getId()) {
                case R.id.iv_grab_detail_img:
                    // 视频播放
                    break;
                case R.id.btn_grab_detail_appeal:
                    // 申诉退钻
                    if (mGrabDetail != null) {
                        showAppealDialog();
                    }
                    break;
                case R.id.btn_grab_detail_appeal_1:
                    // 申诉娃娃
                    if (mGrabDetail != null) {
                        showAppealDialog_2();
                    }
                    break;
            }
        }
    };

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(final GrabDetail grabDetail) {
        if (grabDetail != null) {
            loadData();
        }
    }

    /**
     * 展示申诉对话框
     */
    private void showAppealDialog() {
        if (mAppealDialog == null) {
            mAppealDialog = new AppealDialog(this, mGrabRecord.id);
        }
        mAppealDialog.show();
    }
//AppealDialog_2
private void showAppealDialog_2() {
    if (mAppealDialog2 == null) {
        mAppealDialog2 = new AppealDialog_2(this, mGrabRecord.id);
    }
    mAppealDialog2.show();
}

    @Override
    protected void onDestroy() {
        CommonUtil.dismissDialog(mAppealDialog);
        super.onDestroy();
    }
}
