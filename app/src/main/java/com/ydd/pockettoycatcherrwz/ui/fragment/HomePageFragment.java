package com.ydd.pockettoycatcherrwz.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;

import com.lhh.apst.library.AdvancedPagerSlidingTabStrip;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.entity.AdsPlayInfo;
import com.ydd.pockettoycatcherrwz.entity.BannerInfo;
import com.ydd.pockettoycatcherrwz.entity.ViewPagerIndicateMessage;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.BannerRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.GetViewPagerIndicateMessage;
import com.ydd.pockettoycatcherrwz.transformer.AlphaPageTransformer;
import com.ydd.pockettoycatcherrwz.ui.home.Messages_Activity;
import com.ydd.pockettoycatcherrwz.ui.home.NewRankActivity;
import com.ydd.pockettoycatcherrwz.ui.home.RankingActivity;
import com.ydd.pockettoycatcherrwz.ui.vp_fragment.NewBlankFragment;
import com.ydd.pockettoycatcherrwz.util.CallBackUtils;
import com.ydd.pockettoycatcherrwz.view.CustomScrollView;
import com.ydd.pockettoycatcherrwz.view.MyViewPager;
import com.ydd.pockettoycatcherrwz.view.xscrollview.XScrollView;
import com.ydd.pockettoycatcherrwz.widget.AdsPagerAdapter;
import com.ydd.pockettoycatcherrwz.widget.AutoScrollViewPager;
import com.ydd.pockettoycatcherrwz.widget.CirclePageIndicator;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link HomePageFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link HomePageFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class HomePageFragment extends Fragment implements View.OnClickListener {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private MyViewPager viewPager;
    AdvancedPagerSlidingTabStrip tabs;

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
    private CustomScrollView home_scroll;

    private RelativeLayout line_home_rank;
    private RelativeLayout line_home_fuli;

    private ImageView iv_home_message;
    private int vpId = 0;
    private int mCurrentPage = 1;
    AdsPlayInfo mAdsPlayInfo;
    List<BannerInfo> bannerInfos = new ArrayList<>();
    private Handler mHandle = new Handler() {
        @Override
        public void handleMessage(Message msg) {

            if (msg.what == 1) {

                refreshAds(mAdsPlayInfo, bannerInfos);
            }
        }
    };

    private Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            if (msg.what == 1) {

                // itemAdapter.notifyDataSetChanged();
                home_scroll.stopRefresh();  // 停止刷新
                home_scroll.smoothScrollTo(0, 0); // 滚动的时候平滑的效果
            } else if (msg.what == 2) {

                //itemAdapter.notifyDataSetChanged();
                home_scroll.stopLoadMore();  // 停止加载
            }
        }
    };


    public HomePageFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment HomePageFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static HomePageFragment newInstance(String param1, String param2) {
        HomePageFragment fragment = new HomePageFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_home_page, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        viewPager = (MyViewPager) view.findViewById(R.id.main_ViewPager);
        iv_home_message =(ImageView) view.findViewById(R.id.iv_home_message);
        mAdPager = (AutoScrollViewPager) view.findViewById(R.id.vp_home_ads);
        line_home_fuli = (RelativeLayout) view.findViewById(R.id.line_home_fuli);
        line_home_rank = (RelativeLayout) view.findViewById(R.id.line_home_rank);
        home_scroll = (CustomScrollView) view.findViewById(R.id.home_scroll);
        mPageIndicator = (CirclePageIndicator) view.findViewById(R.id.view_home_ads_indicator);

        viewPager.setOffscreenPageLimit(3);
        mAdPager.setOffscreenPageLimit(3);

        mPageIndicator.setRadius(
                getResources().getDimension(R.dimen.home_ad_raduis_indication));
        mPageIndicator.setDivder(
                getResources().getDimension(R.dimen.home_ad_offset_indicator));
        mPageIndicator.setFillColor(0xE6FFFFFF);
        mPageIndicator.setPageColor(0x66FFFFFF);
        mPageIndicator.setStrokeWidth(0);
        AlphaPageTransformer mAlphaPageTransformer = new AlphaPageTransformer();
        mAdPager.setPageTransformer(false, mAlphaPageTransformer);
        mAdsPagerAdapter = new AdsPagerAdapter(getActivity());
        mAdPager.setAdapter(mAdsPagerAdapter);

        new Thread(new Runnable() {
            @Override
            public void run() {
                loadHotAct();
            }
        }).start();

        initdatas(view);
        line_home_fuli.setOnClickListener(this);
        line_home_rank.setOnClickListener(this);
        iv_home_message.setOnClickListener(this);

        home_scroll.setXScrollViewListener(new XScrollView.IXScrollViewListener() {
            @Override
            public void onRefresh() {
                CallBackUtils.doCallonRefresh();
                handler.sendEmptyMessageDelayed(1,1500);
            }

            @Override
            public void onLoadMore() {
                CallBackUtils.doCallonLoadMore();
                handler.sendEmptyMessageDelayed(2,1500);
            }
        });

    }


    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }


    /**
     * 获取轮播条名字
     */
    private void initdatas(final View view) {
        GetViewPagerIndicateMessage request = new GetViewPagerIndicateMessage();
        BusinessManager.getInstance().getViewPagerIndicateMessage(request,
                new MyCallback<List<ViewPagerIndicateMessage>>() {
                    @Override
                    public void onSuccess(List<ViewPagerIndicateMessage> result, String message) {
                        Log.i("adddqqq", "onSuccess: " + result.toString());
                        //初始化轮播
                        initViewPagerIndicate(result, view);
                    }

                    @Override
                    public void onError(String errorCode, String message) {
                        Log.i("adddqqq", "onSuccess: " + message);
//                        showToast(message);
                    }

                    @Override
                    public void onFinished() {

                    }
                });

    }

    private void initViewPagerIndicate(final List<ViewPagerIndicateMessage> result, View view) {
        final List<Fragment> mlist = new ArrayList<>();
        // BlankMainfragment
        for (int i = 0; i < result.size(); i++) {
            //  BlankFragment b = new BlankFragment();
            NewBlankFragment b = new NewBlankFragment();
            mlist.add(b);
            Log.i("caaaa", "initViewPagerIndicate: " + mlist);
        }
        viewPager.setAdapter(new FragmentPagerAdapter(getFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                Bundle bundle = new Bundle();
                bundle.putSerializable("vpmessage", result.get(position));
                //   vpId = result.get(position).getId();
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
        tabs = (AdvancedPagerSlidingTabStrip) view.findViewById(R.id.tabs);
        tabs.setViewPager(viewPager);
        //控件下划线颜色
        tabs.setUnderlineColor(Color.parseColor("#00000000"));
        tabs.setDividerColor(Color.parseColor("#00000000"));
        //tabs.setTabPaddingLeftRight(20);
        tabs.setShouldExpand(false);
        //下划线出现的样式
        tabs.setIndicatorColor(Color.parseColor("#00c24f"));
        //   tabs.setBackgroundColor(Color.parseColor("#F64071"));
        tabs.setTextColorResource(R.color.common_text_gray);
        tabs.setSelectItem(0);
        tabs.setShouldExpand(false);


    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } /*else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }*/
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.line_home_rank:

                Intent intent = new Intent(getActivity(), RankingActivity.class);
                startActivity(intent);
                break;
            case R.id.line_home_fuli:
                Intent intent1 = new Intent(getActivity(), NewRankActivity.class);
                startActivity(intent1);
                break;

            case R.id.iv_home_message:
                Intent intent2 = new Intent(getActivity(), Messages_Activity.class);
                startActivity(intent2);
                break;
        }
    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }

    /**
     * 加载热点活动
     */
    private void loadHotAct() {
        BannerRequest request = new BannerRequest();
        BusinessManager.getInstance().getBanner(request,
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
                        mAdsPlayInfo = adsPlayInfo;
                        bannerInfos = result;
                        //refreshAds(adsPlayInfo, result);
                        mHandle.sendEmptyMessage(1);
                    }

                    @Override
                    public void onError(String errorCode, String message) {
                        Log.i("===", message);
                    }

                    @Override
                    public void onFinished() {
                        Log.i("===", "message");
                    }
                });
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    /**
     * 刷新广告
     *
     * @param adsPlayInfo
     * @param adPool
     */
    private void refreshAds(AdsPlayInfo adsPlayInfo, List<BannerInfo> adPool) {
        mAdPager.stopAutoScroll();
        if (null == adsPlayInfo || null == adPool) {
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
          /*  FrameLayout.LayoutParams linearParams = (FrameLayout.LayoutParams) mAdPager.getLayoutParams();
            linearParams.topMargin = DensityUtil.dip2px(getActivity(), 5);
            linearParams.leftMargin = DensityUtil.dip2px(getActivity(), 40);
            linearParams.rightMargin = DensityUtil.dip2px(getActivity(), 40);
            mAdPager.setLayoutParams(linearParams);*/
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
            mPageIndicator.setAutoScrollCount(adPool.size());
            mPageIndicator.setVisibility(View.VISIBLE);
            mPageIndicator.setViewPager(mAdPager);
            mAdPager.setCurrentItem(currentItem, true);// 保证该页为第一页
        } else if (adPool.size() == 1) {
            mAdsPagerAdapter.refreshUi(adPool);
            mAdPager.setCycle(false);
            mAdPager.setCurrentItem(0, true);
        }

    }


    public  interface  CusScrollCall{
        void onRefresh();
        void onLoadMore();
    }


}
