package com.ydd.pockettoycatcherrwz.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.lhh.apst.library.AdvancedPagerSlidingTabStrip;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.widget.AutoScrollViewPager;
import com.ydd.pockettoycatcherrwz.widget.CirclePageIndicator;
import com.ydd.pockettoycatcherrwz.widget.FixedRatioRelativeLayout;


/**
 * 布局临时调整，简单写写
 * 
 * Created by czhang on 17/2/16.
 */

public class HomeHeaderViewNew extends LinearLayout {

	/**
	 * 头部视图，将其直接加到HeaderView中
	 */
	public RelativeLayout mContainer;
	/**
	 * 广告的viewpager
	 */
	public AutoScrollViewPager mAdPager;
	/**
	 * 广告Indicator
	 */
	public CirclePageIndicator mPageIndicator;

	public LinearLayout heard_lin;
	public LinearLayout sinlin;
	public LinearLayout invitelin;
	public LinearLayout wawaboxlin;
	public LinearLayout integrallin;
	public FixedRatioRelativeLayout fix;

	private AdvancedPagerSlidingTabStrip tabs;

	public HomeHeaderViewNew(Context context) {
		super(context);
		initView(context);
	}

	private void initView(Context context) {
		mContainer = (RelativeLayout) LayoutInflater.from(context)
				.inflate(R.layout.view_home_header1, null);
		fix = (FixedRatioRelativeLayout) mContainer.findViewById(R.id.fix);
		mAdPager = (AutoScrollViewPager) mContainer
				.findViewById(R.id.vp_home_ads);
		mPageIndicator = (CirclePageIndicator) mContainer
				.findViewById(R.id.view_home_ads_indicator);
		heard_lin = (LinearLayout) mContainer.findViewById(R.id.iv_dlg_personal_linear);
		//签到
		sinlin = (LinearLayout) mContainer.findViewById(R.id.iv_dlg_personal_grab_sin);
		//邀请好友
		invitelin = (LinearLayout) mContainer.findViewById(R.id.iv_dlg_personal_charge_invite);
		//娃娃盒
		wawaboxlin = (LinearLayout) mContainer.findViewById(R.id.iv_dlg_personal_wawabox);
		//积分商城
		integrallin = (LinearLayout) mContainer.findViewById(R.id.iv_dlg_personal_integral);


		addView(mContainer);
	}
}
