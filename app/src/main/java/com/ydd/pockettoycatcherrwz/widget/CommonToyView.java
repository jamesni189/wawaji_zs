package com.ydd.pockettoycatcherrwz.widget;

import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.FrameLayout;
import android.widget.TextView;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.entity.OrderToy;
import com.ydd.pockettoycatcherrwz.util.ImgLoaderUtil;

/**
 * 玩具item
 */
public class CommonToyView extends FrameLayout {

	private Context mContext;

	/**
	 * 图像
	 */
	private XCRoundRectImageView mImgIv;
	/**
	 * 名字
	 */
	private TextView mNameTv;
	/**
	 * 数量
	 */
	private TextView mNumTv;
	/**
	 * 分割线
	 */
	private View mDividerView;

	public CommonToyView(Context context) {
		super(context);
		mContext = context;
		initView();
	}

	public CommonToyView(Context context, OrderToy orderToy) {
		super(context);
		mContext = context;
		initView();
		setView(orderToy);
	}

	public CommonToyView(Context context, AttributeSet attrs) {
		super(context, attrs);
		mContext = context;
		initView();
	}

	public CommonToyView(Context context, AttributeSet attrs, int defStyle) {
		super(context, attrs, defStyle);
		mContext = context;
		initView();
	}

	private void initView() {
		LayoutInflater layoutInflater = LayoutInflater.from(getContext());
		View rootView = layoutInflater.inflate(R.layout.view_common_toy, this,
				true);
		mImgIv = (XCRoundRectImageView) rootView.findViewById(R.id.iv_toy_img);
		mNameTv = (TextView) rootView.findViewById(R.id.tv_toy_name);
		mNumTv = (TextView) rootView.findViewById(R.id.tv_toy_num);
		//mDividerView = rootView.findViewById(R.id.view_toy_divider);

//		CommonUtil.setTextBold(mNameTv);
//		CommonUtil.setTextBold(mNumTv);
	}

	public void setView(OrderToy orderToy) {
		ImgLoaderUtil.displayImage(orderToy.img, mImgIv);
		mNameTv.setText(orderToy.name);
		mNumTv.setText("x" + orderToy.num);
	}

	public void showDivider(boolean isShown) {
	//	mDividerView.setVisibility(isShown ? View.VISIBLE : View.GONE);
	}

}
