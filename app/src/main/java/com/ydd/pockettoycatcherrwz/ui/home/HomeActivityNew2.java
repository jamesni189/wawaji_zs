package com.ydd.pockettoycatcherrwz.ui.home;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AlertDialog;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.lhh.apst.library.AdvancedPagerSlidingTabStrip;
import com.ydd.pockettoycatcherrwz.PTCApplication;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.entity.AppUpdataMessage;
import com.ydd.pockettoycatcherrwz.entity.ViewPagerIndicateMessage;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.UploadVideoManager;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.GetViewPagerIndicateMessage;
import com.ydd.pockettoycatcherrwz.ui.BaseActivity;
import com.ydd.pockettoycatcherrwz.ui.vp_fragment.NewBlankFragment;
import com.ydd.pockettoycatcherrwz.ui.web.WebActivity;
import com.ydd.pockettoycatcherrwz.util.ImgLoaderUtil;
import com.ydd.pockettoycatcherrwz.widget.AdsPagerAdapter;
import com.ydd.pockettoycatcherrwz.widget.AutoScrollViewPager;
import com.ydd.pockettoycatcherrwz.widget.CirclePageIndicator;
import com.ydd.pockettoycatcherrwz.widget.CustomHeadPtrListView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.List;


/*
* 可以被删的
* */
public class HomeActivityNew2 extends BaseActivity {

    private ViewPager viewPager;
    /**
     * 头像
     */
    private ImageView mAvatarIv;
    private ImageView main_right_icon;
    private AppUpdataMessage mAppUpdataMessage;

    private CustomHeadPtrListView mRoomsLv;
    /**
     * 广告的viewpager
     */
    private AutoScrollViewPager mAdPager;
    /**
     * 广告轮播适配器
     */
    private AdsPagerAdapter mAdsPagerAdapter;
    /**
     * 广告Indicator
     */
    private CirclePageIndicator mPageIndicator;
    private AdvancedPagerSlidingTabStrip tabs;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_new1);
        initview();
        initdatas();
        if (!PTCApplication.isUploaded) {
            UploadVideoManager.getInstance().checkUpload();
            PTCApplication.isUploaded = true;
        }
        PTCApplication.isHomeLoaded = true;
        mAppUpdataMessage = (AppUpdataMessage) getIntent().getSerializableExtra("AppUpdataMessage");
        if (mAppUpdataMessage != null) {
            dalog(mAppUpdataMessage);
        }

    }

    private void initview() {
        mAvatarIv = (ImageView) findViewById(R.id.iv_home_title_avatar);
        mAdPager = (AutoScrollViewPager) findViewById(R.id.vp_home_ads);
        mPageIndicator = (CirclePageIndicator) findViewById(R.id.view_home_ads_indicator);
        viewPager = (ViewPager) findViewById(R.id.view_pager);
        viewPager.setOffscreenPageLimit(3);
        if (RunningContext.loginTelInfo != null) {
            ImgLoaderUtil.displayImage(RunningContext.loginTelInfo.avatar,
                    mAvatarIv,
                    getResources().getDrawable(R.mipmap.pic_default));
        }
        //个人中心
        mAvatarIv.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(HomeActivityNew2.this, UsermainActivity.class));
            }
        });
    }

    /**
     * 获取轮播条名字
     */
    private void initdatas() {
        GetViewPagerIndicateMessage request = new GetViewPagerIndicateMessage();
        BusinessManager.getInstance().getViewPagerIndicateMessage(request,
                new MyCallback<List<ViewPagerIndicateMessage>>() {
                    @Override
                    public void onSuccess(List<ViewPagerIndicateMessage> result, String message) {
                        Log.i("adddqqq", "onSuccess: " + result.toString());
                        //初始化轮播
                        initViewPagerIndicate(result);
                    }

                    @Override
                    public void onError(String errorCode, String message) {
                        Log.i("adddqqq", "onSuccess: " + message);
                        showToast(message);
                    }

                    @Override
                    public void onFinished() {

                    }
                });

    }

    @Override
    protected void onResume() {
        super.onResume();
    }

    @Override
    protected void onDestroy() {
        removeALLActivity();
        super.onDestroy();
    }

    private void initViewPagerIndicate(final List<ViewPagerIndicateMessage> result) {
        final List<Fragment> mlist = new ArrayList<>();
        // BlankMainfragment
        for (int i = 0; i < result.size(); i++) {
            NewBlankFragment b = new NewBlankFragment();
            mlist.add(b);
            Log.i("caaaa", "initViewPagerIndicate: " + mlist);
        }
        viewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("vpmessage", result.get(position));
                mlist.get(position).setArguments(bundle);
                return mlist.get(position);
            }

            @Override
            public int getCount() {
                return mlist.size();
            }

            @Override
            public CharSequence getPageTitle(int position) {
                return result.get(position).getName();
            }
        });
         tabs = (AdvancedPagerSlidingTabStrip) findViewById(R.id.tabs);
        tabs.setViewPager(viewPager);
        //控件下划线颜色
        tabs.setUnderlineColor(Color.parseColor("#00000000"));
        tabs.setDividerColor(Color.parseColor("#00000000"));
        //tabs.setTabPaddingLeftRight(20);
        tabs.setShouldExpand(false);
        //下划线出现的样式
        tabs.setIndicatorColor(Color.parseColor("#FFFFFF"));
        //   tabs.setBackgroundColor(Color.parseColor("#F64071"));
        tabs.setTextColorResource(R.color.common_text_white_1);
        tabs.setSelectItem(0);
        tabs.setShouldExpand(false);
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(final AppUpdataMessage msg) {
        dalog(msg);
    }

    public void dalog(final AppUpdataMessage msg) {

        if (msg.android_update == 1) {
            AlertDialog dialog = new AlertDialog.Builder(this, R.style.dialog)
                    .setTitle(getResources().getString(R.string.app_name) + "更新啦！")
                    .setMessage(msg.mesage)
                    .setPositiveButton("确定", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {

                            Intent intent = new Intent(HomeActivityNew2.this, WebActivity.class);
                            intent.putExtra(WebActivity.INTENT_EXTRA_BEGIN_URL,
                                    msg.android_url);

                            startActivity(intent);
                        }
                    })
                    .setNegativeButton("取消", new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialog, int which) {
                            dialog.dismiss();
                        }
                    })
                    .create();
            dialog.show();
            try {
                Field mAlert = AlertDialog.class.getDeclaredField("mAlert");
                mAlert.setAccessible(true);
                Object mAlertController = mAlert.get(dialog);
                Field mMessage = mAlertController.getClass().getDeclaredField("mMessageView");
                mMessage.setAccessible(true);
                TextView mMessageView = (TextView) mMessage.get(mAlertController);
                mMessageView.setTextColor(Color.BLACK);
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (NoSuchFieldException e) {
                e.printStackTrace();
            }
        }
    }
}