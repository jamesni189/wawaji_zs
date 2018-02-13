package com.ydd.pockettoycatcherrwz.ui.vp_fragment;


import android.os.Bundle;

import android.os.Handler;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.hyphenate.util.DensityUtil;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.entity.AdsPlayInfo;
import com.ydd.pockettoycatcherrwz.entity.BannerInfo;
import com.ydd.pockettoycatcherrwz.entity.HomeInfo;
import com.ydd.pockettoycatcherrwz.entity.ViewPagerIndicateMessage;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.BannerRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.HomeRequest;
import com.ydd.pockettoycatcherrwz.transformer.AlphaPageTransformer;
import com.ydd.pockettoycatcherrwz.ui.home.HomeHeaderView;
import com.ydd.pockettoycatcherrwz.ui.home.HomeRoomLvAdapter;
import com.ydd.pockettoycatcherrwz.widget.AdsPagerAdapter;
import com.ydd.pockettoycatcherrwz.widget.AutoScrollViewPager;
import com.ydd.pockettoycatcherrwz.widget.CirclePageIndicator;
import com.ydd.pockettoycatcherrwz.widget.CustomHeadPtrListView;

import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 */
public class BlankFragment extends Fragment {
    private View view;
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
    private HomeRoomLvAdapter mAdapter;
    /**
     * 当前页码
     */
    private int mCurrentPage;
    private LinearLayout heard_lin;
    private ViewPagerIndicateMessage vp;
    private ImageView apach_image;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        view = inflater.inflate(R.layout.fragment_blank, container, false);
        vp = (ViewPagerIndicateMessage) getArguments().getSerializable("vpmessage");
        Log.i("dqqqeeer", "onCreateView: " + vp);
        initview();
        autoScroll();
        return view;
    }

    private void initview() {
        mRoomsLv = (CustomHeadPtrListView) view.findViewById(R.id.lv_home_rooms);
      /*  if (vp.getId() == 0) {
            initHeaderView();
        }*/
        initListView();
    }

    private void initHeaderView() {

        HomeHeaderView homeHeaderView = new HomeHeaderView(getActivity());
        mAdPager = homeHeaderView.mAdPager;

        //	mPageIndicator = homeHeaderView.mPageIndicator;
        //	mAdPager.setPageMargin(40);
        mAdPager.setOffscreenPageLimit(3);
        AlphaPageTransformer mAlphaPageTransformer = new AlphaPageTransformer();
        mAdPager.setPageTransformer(false, mAlphaPageTransformer);
        mRoomsLv.addHeaderView(homeHeaderView, null, false);
//		mPageIndicator.setRadius(
//				getResources().getDimension(R.dimen.home_ad_raduis_indication));
//		mPageIndicator.setDivder(
//				getResources().getDimension(R.dimen.home_ad_offset_indicator));
//		mPageIndicator.setFillColor(0xE6FFFFFF);
//		mPageIndicator.setPageColor(0x66FFFFFF);
//		mPageIndicator.setStrokeWidth(0);
        mAdsPagerAdapter = new AdsPagerAdapter(getActivity());
        mAdPager.setAdapter(mAdsPagerAdapter);
        apach_image = (ImageView) view.findViewById(R.id.apach_image);
        apach_image.setVisibility(View.VISIBLE);
        mRoomsLv.setOnScrollListener(new AbsListView.OnScrollListener() {
            @Override
            public void onScrollStateChanged(AbsListView view, int scrollState) {
            }

            @Override
            public void onScroll(AbsListView view, int firstVisibleItem, int visibleItemCount, int totalItemCount) {
                if(firstVisibleItem == 0){
                    apach_image.setAlpha((float)1);
                }
                if (firstVisibleItem == 1) {
                    View c = view.getChildAt(0); //this is the first visible row
                    if (c != null) {
                        int scrollY = Math.abs(-c.getTop());
                        float alpha = (float)scrollY /(float) c.getHeight();
                        Log.i("cvaaff1112222", "onScroll:-- " + alpha+"scrollY--"+scrollY+"height--"+c.getHeight());
                        apach_image.setAlpha((float)1-alpha);
                    }
                }
                if(firstVisibleItem>1){
                    apach_image.setAlpha((float)0);
                }

            }
        });
    }


    private void initListView() {
        mAdapter = new HomeRoomLvAdapter(getContext());
        mRoomsLv.setAdapter(mAdapter);
        mRoomsLv.setMode(PullToRefreshBase.Mode.BOTH);
        mRoomsLv.setOnRefreshListener(
                new PullToRefreshBase.OnRefreshListener2<ListView>() {
                    @Override
                    public void onPullDownToRefresh(
                            PullToRefreshBase<ListView> refreshView) {
                        loadData(1);
                      /*  if (vp.getId() == 0) {
                            loadHotAct();
                        }*/
                    }

                    @Override
                    public void onPullUpToRefresh(
                            PullToRefreshBase<ListView> refreshView) {
                        loadData(mCurrentPage + 1);
                    }
                });
    }

    private void loadData(final int page) {
        HomeRequest request = new HomeRequest(page, 10, vp.getId());
        BusinessManager.getInstance().getHomeInfo(request,
                new MyCallback<HomeInfo>() {
                    @Override
                    public void onSuccess(HomeInfo result, String message) {
                        Log.i("dqqqrrr", "onSuccess: " + result);
                        if (result.pageSize == 0) {
                            Toast.makeText(getActivity(), "没有更多数据", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mCurrentPage = page;

                        if (page == 1) {
                            //取控件textView当前的布局参数 linearParams.height = 20;// 控件的高强制设成20
                            mAdapter.refreshUi(result.data);
                        } else {
                            mAdapter.refreshUiByAdd(result.data);
                        }
                    }

                    @Override
                    public void onError(String errorCode, String message) {
                        mRoomsLv.onRefreshComplete();
                    }

                    @Override
                    public void onFinished() {
                        mRoomsLv.onRefreshComplete();
                    }
                });
    }

    /**
     * 自动加载
     */
    private void autoScroll() {
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                mRoomsLv.setRefreshing(true);
                // loadData(1);
                // loadHotAct();
            }
        }, 500);
    }

    /**
     * 加载热点活动
     */
    private void loadHotAct() {
        BusinessManager.getInstance().getBanner(new BannerRequest(),
                new MyCallback<List<BannerInfo>>() {
                    @Override
                    public void onSuccess(List<BannerInfo> result,
                                          String message) {
                        Log.i("caaaffg", "onSuccess: " + result.toString());
                        if (result == null) {
                            return;
                        }
                        AdsPlayInfo adsPlayInfo = new AdsPlayInfo();
                        adsPlayInfo.playingOrder = 1;
                        adsPlayInfo.timing = 3;
                        adsPlayInfo.pool = result;

                        refreshAds(adsPlayInfo, result);
                    }

                    @Override
                    public void onError(String errorCode, String message) {

                    }

                    @Override
                    public void onFinished() {

                    }
                });
    }

    /**
     * 刷新广告
     *
     * @param adsPlayInfo
     * @param adPool
     */
    private void refreshAds(AdsPlayInfo adsPlayInfo, List<BannerInfo> adPool) {
        mAdPager.stopAutoScroll();
        if (null == adsPlayInfo || null == adPool || adPool.isEmpty()) {
            return;
        }
        // 设置自动滚动的间隔时间，单位为毫秒。默认为5秒
        mAdPager.setInterval(
                adsPlayInfo.timing > 0 ? adsPlayInfo.timing * 1000 : 5000);
        // 设置自动滚动的方向
        mAdPager.setDirection(adsPlayInfo.playingOrder == 0
                ? AutoScrollViewPager.LEFT : AutoScrollViewPager.RIGHT);
        // 当手指碰到ViewPager时是否停止自动滚动，默认为true
        mAdPager.setStopScrollWhenTouch(true);
        // 滑动到第一个或最后一个Item的处理方式，支持没有任何操作、轮播以及传递到父View三种模式
        mAdPager.setSlideBorderMode(AutoScrollViewPager.SLIDE_BORDER_MODE_NONE);
        // 设置循环滚动时滑动到从边缘滚动到下一个是否需要动画，默认为true
        mAdPager.setBorderAnimation(true);

        if (null != adPool && adPool.size() > 1) {
            FrameLayout.LayoutParams linearParams = (FrameLayout.LayoutParams) mAdPager.getLayoutParams();
            linearParams.topMargin = DensityUtil.dip2px(getActivity(), 5);
            linearParams.leftMargin = DensityUtil.dip2px(getActivity(), 40);
            linearParams.rightMargin = DensityUtil.dip2px(getActivity(), 40);
            mAdPager.setLayoutParams(linearParams);
            int currentItem;
            if (adsPlayInfo.playingOrder == 0) {
                Collections.reverse(adPool);
                currentItem = 2000 - 2000 % adPool.size() - 1;
            } else {
                currentItem = 1000 - 1000 % adPool.size();
            }
            mAdsPagerAdapter.refreshUi(adPool);
            mAdPager.setCycle(true);// 开启循环
            mAdPager.startAutoScroll((int) (adsPlayInfo.timing * 1000)); // 开始轮播
//			mPageIndicator.setAutoScrollCount(adPool.size());
//			mPageIndicator.setVisibility(View.VISIBLE);
//			mPageIndicator.setViewPager(mAdPager);
            mAdPager.setCurrentItem(currentItem, true);// 保证该页为第一页
        } else if (adPool.size() == 1) {
            mAdsPagerAdapter.refreshUi(adPool);
            mAdPager.setCycle(false);
            mAdPager.setCurrentItem(0, true);
        }
    }

    private int listViewScorllY() {
        View view = mRoomsLv.getChildAt(0);
        if (view == null) {
            return 0;
        }

        int top = view.getTop();
        return top;
    }
}
