package com.ydd.pockettoycatcherrwz.widget;

import android.content.Context;
import android.content.Intent;
import android.support.v4.view.PagerAdapter;
import android.text.TextUtils;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.entity.BannerInfo;
import com.ydd.pockettoycatcherrwz.entity.RoomItem;
import com.ydd.pockettoycatcherrwz.ui.home.LiveActivityNew;
import com.ydd.pockettoycatcherrwz.ui.home.RechargeNewActivity;
import com.ydd.pockettoycatcherrwz.ui.home.Recharge_newActivity;
import com.ydd.pockettoycatcherrwz.ui.personal.RechargeListDialog;
import com.ydd.pockettoycatcherrwz.ui.web.WebActivity;
import com.ydd.pockettoycatcherrwz.util.ImgLoaderUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 广告轮播的适配器
 * 
 * @author czhang
 */
public class AdsPagerAdapter extends PagerAdapter {

	/** xUtils中的图片处理工具类 */
//	private BitmapUtils mBmpUtils;

	/** xUtils中从网络中下载图片时的图片配置信息 */
//	private BitmapDisplayConfig mBmpConfig;

	private LayoutInflater mInflater;

	/** 需要显示的广告列表 */
	private List<BannerInfo> mAdsList;

	private RechargeListDialog mRechargeListDialog;

	private Context mContext;

	public AdsPagerAdapter(Context context) {
		mContext = context;
		mAdsList = new ArrayList<>();
		mInflater = LayoutInflater.from(context);
//		mBmpUtils = new BitmapUtils(context);

		// 初始化xUtils图片相关配置信息
//		mBmpConfig = new BitmapDisplayConfig();
//		mBmpConfig.setLoadingDrawable(
//				context.getResources().getDrawable(R.mipmap.pic_default));
//		mBmpConfig.setLoadFailedDrawable(
//				context.getResources().getDrawable(R.mipmap.pic_default));
//		mBmpConfig.setBitmapConfig(Config.RGB_565);
//		int size = (int) (RunningContext.screenWidth / 4.0 * 3);
//		mBmpConfig.setBitmapMaxSize(new BitmapSize(size, size));
	}

	@Override
	public int getCount() {
		if (mAdsList.size() == 0) {
			return 0;
		} else if (mAdsList.size() <= 1) {
			return mAdsList.size();
		}
		return 2000;
	}


	@Override
	public boolean isViewFromObject(View arg0, Object arg1) {
		return arg0 == arg1;
	}

	@Override
	public void destroyItem(ViewGroup container, int position, Object object) {
		if (object instanceof View) {
			container.removeView((View) object);
		}
	}

	@Override
	public Object instantiateItem(ViewGroup container, int position) {
		XCRoundRectImageView imageView = (XCRoundRectImageView) mInflater
				.inflate(R.layout.pageritem_home_ad, null);
		if (mAdsList.size() != 0) {
			final BannerInfo ads = mAdsList.get(position % mAdsList.size());
			Log.i("ccaa", "onClick: "+ads);
//			mBmpUtils.display(imageView, ads.imgUrl, mBmpConfig);
			ImgLoaderUtil.displayImage(ads.imgUrl, imageView,
					mContext.getResources().getDrawable(R.mipmap.pic_default));
			imageView.setOnClickListener(new OnClickListener() {

				@Override
				public void onClick(View v) {
					// 外部跳转
					if (ads.type == 1 && !TextUtils.isEmpty(ads.machineSn)) {
						// 抓娃娃
						RoomItem roomItem = new RoomItem();
						roomItem.machineSn = ads.machineSn;
						roomItem.imgs = ads.imgs;
						roomItem.winImg = ads.winImg;
						Intent intent = new Intent(mContext,
								LiveActivityNew.class);
						intent.putExtra("roomItem", roomItem);
						mContext.startActivity(intent);
					} else if (ads.type == 3 && !TextUtils.isEmpty(ads.url)) {
						// 网页
						Intent intent = new Intent(mContext, WebActivity.class);
						intent.putExtra(WebActivity.INTENT_EXTRA_BEGIN_URL,
								ads.url);

						mContext.startActivity(intent);
					} else if (ads.type == 2) {
						// 充值
					//	mContext.startActivity(new Intent(mContext, Recharge_newActivity.class));
						mContext.startActivity(new Intent(mContext, RechargeNewActivity.class));
	//				mRechargeListDialog = new RechargeListDialog(
//								(Activity) mContext);
//						mRechargeListDialog.show();
					}
				}
			});
		}

		container.addView(imageView);
		return imageView;
	}

	/**
	 * 刷新广告轮播数据<br>
	 * 需要重置adapter。这样的写法效果不明显
	 * 
	 * @param adsList
	 *            广告列表
	 */
	public void refreshUi(List<BannerInfo> adsList) {
		mAdsList.clear();
		if (adsList != null && adsList.size() != 0) {
			mAdsList.addAll(adsList);
			notifyDataSetChanged();
		}
	}

	public void release() {
		if (mRechargeListDialog != null) {
			mRechargeListDialog.dismiss();
		}
	}

}
