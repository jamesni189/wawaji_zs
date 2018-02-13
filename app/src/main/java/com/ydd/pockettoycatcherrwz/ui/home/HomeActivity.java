package com.ydd.pockettoycatcherrwz.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.ydd.pockettoycatcherrwz.PTCApplication;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.UserManager;
import com.ydd.pockettoycatcherrwz.entity.AdsPlayInfo;
import com.ydd.pockettoycatcherrwz.entity.BannerInfo;
import com.ydd.pockettoycatcherrwz.entity.HomeInfo;
import com.ydd.pockettoycatcherrwz.entity.RoomItem;
import com.ydd.pockettoycatcherrwz.entity.RoomStatusInfo;
import com.ydd.pockettoycatcherrwz.entity.UserInfo;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.UploadVideoManager;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.BannerRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.HomeRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.RoomStatusRequest;
import com.ydd.pockettoycatcherrwz.transformer.AlphaPageTransformer;
import com.ydd.pockettoycatcherrwz.ui.BaseActivity;
import com.ydd.pockettoycatcherrwz.ui.personal.PersonalDialog;
import com.ydd.pockettoycatcherrwz.util.CommonUtil;
import com.ydd.pockettoycatcherrwz.util.ImgLoaderUtil;
import com.ydd.pockettoycatcherrwz.util.ListUtil;
import com.ydd.pockettoycatcherrwz.widget.AdsPagerAdapter;
import com.ydd.pockettoycatcherrwz.widget.AutoScrollViewPager;
import com.ydd.pockettoycatcherrwz.widget.CirclePageIndicator;
import com.ydd.pockettoycatcherrwz.widget.CustomHeadPtrListView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.Collections;
import java.util.List;

public class HomeActivity extends BaseActivity {
	/**
	 * 头像
	 */
	private ImageView mAvatarIv;
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
	/**
	 * 房间gridview
	 */
	private CustomHeadPtrListView mRoomsLv;
	/**
	 * 列表适配器
	 */
	private HomeRoomLvAdapter mAdapter;
	/**
	 * 签名对话框
	 */
	private SignDialog mSignDialog;
	/**
	 * 个人信息对话框
	 */
	private PersonalDialog mPersonalDialog;
	/**
	 * 当前页码
	 */
	private int mCurrentPage;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_home);
		initView();
		autoScroll();

		if (!PTCApplication.isUploaded) {
			UploadVideoManager.getInstance().checkUpload();
			PTCApplication.isUploaded = true;
		}

		PTCApplication.isHomeLoaded = true;
	}

	private void initView() {
		mAvatarIv = (ImageView) findViewById(R.id.iv_home_title_avatar);
		mRoomsLv = (CustomHeadPtrListView) findViewById(R.id.lv_home_rooms);
		mAvatarIv.setOnClickListener(mOnClickListener);
		findViewById(R.id.iv_home_title_sign)
				.setOnClickListener(mOnClickListener);
		findViewById(R.id.iv_home_title_shop)
				.setOnClickListener(mOnClickListener);

		if (RunningContext.loginTelInfo != null) {
			ImgLoaderUtil.displayImage(RunningContext.loginTelInfo.avatar,
					mAvatarIv,
					getResources().getDrawable(R.mipmap.pic_default));
		}
		// FIXME 微信登录设置头像
		initHeaderView();
		initListView();
	}

	/**
	 * 头部视图
	 */
	private void initHeaderView() {
		HomeHeaderView homeHeaderView = new HomeHeaderView(this);

		mAdPager = homeHeaderView.mAdPager;
	//	mPageIndicator = homeHeaderView.mPageIndicator;
	//	mAdPager.setPageMargin(40);
		mAdPager.setOffscreenPageLimit(3);
		AlphaPageTransformer mAlphaPageTransformer=new AlphaPageTransformer();
		mAdPager.setPageTransformer(false,mAlphaPageTransformer);
		mRoomsLv.addHeaderView(homeHeaderView, null, false);

//		mPageIndicator.setRadius(
//				getResources().getDimension(R.dimen.home_ad_raduis_indication));
//		mPageIndicator.setDivder(
//				getResources().getDimension(R.dimen.home_ad_offset_indicator));
//		mPageIndicator.setFillColor(0xE6FFFFFF);
//		mPageIndicator.setPageColor(0x66FFFFFF);
//		mPageIndicator.setStrokeWidth(0);

		mAdsPagerAdapter = new AdsPagerAdapter(this);

		mAdPager.setAdapter(mAdsPagerAdapter);


	}

	private void initListView() {
		mAdapter = new HomeRoomLvAdapter(this);
		mRoomsLv.setAdapter(mAdapter);
		mRoomsLv.setMode(PullToRefreshBase.Mode.BOTH);
		mRoomsLv.setOnRefreshListener(
				new PullToRefreshBase.OnRefreshListener2<ListView>() {
					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						loadData(1);
						loadHotAct();
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						loadData(mCurrentPage + 1);
					}
				});
	}

	@Override
	protected void onResume() {
		super.onResume();
		refreshRoomStatus();
	}

	// TODO check 刷新房间状态
	private void refreshRoomStatus() {
		if (mAdapter == null || ListUtil.isEmpty(mAdapter.getDatas())) {
			return;
		}
		String sn = "";
		for (RoomItem roomItem : mAdapter.getDatas()) {
			if (!TextUtils.isEmpty(sn)) {
				sn += ",";
			}
			sn += roomItem.machineSn;
		}
		RoomStatusRequest request = new RoomStatusRequest(
				RunningContext.accessToken, sn);
		BusinessManager.getInstance().getRoomStatus(request,
				new MyCallback<List<RoomStatusInfo>>() {
					@Override
					public void onSuccess(List<RoomStatusInfo> result,
							String message) {
						if (ListUtil.isEmpty(result)) {
							return;
						}
						mAdapter.refreshStatus(result);
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
	 * 加载热点活动
	 */
	private void loadHotAct() {
		BusinessManager.getInstance().getBanner(new BannerRequest(),
				new MyCallback<List<BannerInfo>>() {
					@Override
					public void onSuccess(List<BannerInfo> result,
							String message) {
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
	 * 加载数据
	 *
	 * @param page
	 */
	private void loadData(final int page) {
		HomeRequest request = new HomeRequest(page, 10);
		BusinessManager.getInstance().getHomeInfo(request,
				new MyCallback<HomeInfo>() {
					@Override
					public void onSuccess(HomeInfo result, String message) {
						if (result.pageSize == 0) {
							showToast("没有更多数据");
							return;
						}
						mCurrentPage = page;
						if (page == 1) {
							mAdapter.refreshUi(result.data);
						} else {
							mAdapter.refreshUiByAdd(result.data);
						}
					}

					@Override
					public void onError(String errorCode, String message) {
						showToast(message);
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

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessage(UserInfo userInfo) {
		if (userInfo != null) {
			ImgLoaderUtil.displayImage(RunningContext.loginTelInfo.avatar,
					mAvatarIv,
					getResources().getDrawable(R.mipmap.pic_default));
		}
	}

	private View.OnClickListener mOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.iv_home_title_avatar:
				// 头像
				showPersonalDialog();
				break;
			case R.id.iv_home_title_sign:
				// 签到
				showSignDialog();
				break;
			case R.id.iv_home_title_shop:
				startActivity(
						new Intent(HomeActivity.this, ToysBagActivity.class));
				// 购物车
				break;
			}
		}
	};

	/**
	 * 展示签到对话框
	 */
	private void showSignDialog() {
		mSignDialog = new SignDialog(this);
		mSignDialog.show();
	}

	/**
	 * 展示个人对话框
	 */
	private void showPersonalDialog() {
		UserManager.getInstance().refresh();
		mPersonalDialog = new PersonalDialog(this);
		mPersonalDialog.show();
	}

	@Override
	protected void onDestroy() {
		if (mSignDialog != null) {
			mSignDialog.release();
		}
		if (mPersonalDialog != null) {
			mPersonalDialog.release();
		}
		mAdsPagerAdapter.release();
		CommonUtil.dismissDialog(mSignDialog);
		CommonUtil.dismissDialog(mPersonalDialog);
		super.onDestroy();
		PTCApplication.isHomeLoaded = false;
	}
}
