package com.ydd.pockettoycatcherrwz.ui.home;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.entity.RoomItem;
import com.ydd.pockettoycatcherrwz.util.BusinessUtil;
import com.ydd.pockettoycatcherrwz.util.CommonUtil;
import com.ydd.pockettoycatcherrwz.util.ImgLoaderUtil;
import com.ydd.pockettoycatcherrwz.util.ListUtil;
import com.ydd.pockettoycatcherrwz.widget.StrokeTextView_2;
import com.ydd.pockettoycatcherrwz.widget.XCRoundRectImageView;

/**
 * 首页房间视图
 */
public class RoomItemView extends FrameLayout {
	/**
	 * 图像
	 */
	private XCRoundRectImageView mToyImgIv;
	/**
	 * 状态
	 */
	private TextView mStatusTv;
	/**
	 * 名字
	 */
	private StrokeTextView_2 mNameTv;

	/**
	 * 价格
	 */
	private StrokeTextView_2 mPriceTv;

	public RoomItemView(Context context) {
		super(context);
		initView();
	}

	public RoomItemView(Context context, AttributeSet attrs) {
		super(context, attrs);
		initView();
	}

	private void initView() {
		LayoutInflater layoutInflater = LayoutInflater.from(getContext());
		View rootView = layoutInflater.inflate(R.layout.view_home_room, this,
				true);
		mToyImgIv = (XCRoundRectImageView) rootView
				.findViewById(R.id.iv_room_item);
		mStatusTv = (TextView) rootView.findViewById(R.id.tv_room_item_status);
		mNameTv = (StrokeTextView_2) rootView.findViewById(R.id.tv_room_item_name);

		mPriceTv = (StrokeTextView_2) rootView.findViewById(R.id.tv_room_item_price);
		CommonUtil.setTextBold(mNameTv);
		CommonUtil.setTextBold(mStatusTv);
	}

	/**
	 * 刷新视图
	 * 
	 * @param roomItem
	 */
	public void refreshView(RoomItem roomItem) {
		if (!ListUtil.isEmpty(roomItem.imgs)) {
			ImgLoaderUtil.displayImage(roomItem.imgs.get(0), mToyImgIv,
					getResources().getDrawable(R.mipmap.pic_zww_default));
		}
		mStatusTv.setText(BusinessUtil.getRoomStatus(roomItem.status));
		mNameTv.setText(roomItem.name);

		mPriceTv.setText(roomItem.price+"");

		switch (roomItem.status) {
		case 0:
			mStatusTv.setBackgroundResource(
					R.drawable.shape_room_status_bg_blue);
			break;
		case 1:
			mStatusTv
					.setBackgroundResource(R.drawable.shape_room_status_bg_red);
			break;
		case 2:
			mStatusTv.setBackgroundResource(
					R.drawable.shape_room_status_bg_gray);
			break;
		default:
			mStatusTv.setBackgroundResource(
					R.drawable.shape_room_status_bg_gray);
			break;
		}
	}
}
