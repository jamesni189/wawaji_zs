package com.ydd.pockettoycatcherrwz.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.widget.AutoScrollViewPager;


/**
 * 布局临时调整，简单写写
 * 
 * Created by czhang on 17/2/16.
 */

public class HomeHeaderView extends LinearLayout {

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
//	public CirclePageIndicator mPageIndicator;

	public HomeHeaderView(Context context) {
		super(context);
		initView(context);
	}

	private void initView(Context context) {
		mContainer = (RelativeLayout) LayoutInflater.from(context)
				.inflate(R.layout.view_home_header, null);
		mAdPager = (AutoScrollViewPager) mContainer
				.findViewById(R.id.vp_home_ads);
//		mPageIndicator = (CirclePageIndicator) mContainer
//				.findViewById(R.id.view_home_ads_indicator);
		addView(mContainer);
	}
}
