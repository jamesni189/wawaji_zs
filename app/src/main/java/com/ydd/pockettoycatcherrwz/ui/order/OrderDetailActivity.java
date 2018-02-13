package com.ydd.pockettoycatcherrwz.ui.order;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.entity.OrderDetail;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.OrderDetailRequest;
import com.ydd.pockettoycatcherrwz.ui.BaseTitleActivity;
import com.ydd.pockettoycatcherrwz.util.BusinessUtil;
import com.ydd.pockettoycatcherrwz.util.ListUtil;
import com.ydd.pockettoycatcherrwz.widget.CommonToyView;

/**
 * 订单详情
 */
public class OrderDetailActivity extends BaseTitleActivity {

	public static final String INTENT_EXTRA_ORDER_SN = "mOrderSn";
	/**
	 * 状态
	 */
	private TextView mStatusTv;
	/**
	 * 状态描述
	 */
	private TextView mStatusDescTv;
	/**
	 * 名字
	 */
	private TextView mNameTv;
	/**
	 * 地址
	 */
	private TextView mAddressTv;
	/**
	 * 玩具列表
	 */
	private LinearLayout mToysContainer;
	/**
	 * 订单号
	 */
	private TextView mOrderSnTv;
	/**
	 * 领取时间
	 */
	private TextView mTimeTv;

	private String mOrderSn;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		mOrderSn = getIntent().getStringExtra(INTENT_EXTRA_ORDER_SN);
		if (TextUtils.isEmpty(mOrderSn)) {
			finish();
			return;
		}
		setContent(R.layout.activity_order_detail);
		setTitle("订单详情");
		initView();
		loadData();
	}

	private void initView() {
		mStatusTv = (TextView) findViewById(R.id.tv_order_detail_status);
		mStatusDescTv = (TextView) findViewById(
				R.id.tv_order_detail_status_desc);
		mNameTv = (TextView) findViewById(R.id.tv_order_detail_name);
		mAddressTv = (TextView) findViewById(R.id.tv_order_detail_address);
		mToysContainer = (LinearLayout) findViewById(R.id.ln_order_detail_toys);
		mOrderSnTv = (TextView) findViewById(R.id.tv_order_detail_ordersn);
		mTimeTv = (TextView) findViewById(R.id.tv_order_detail_time);
	}

	private void loadData() {
		BusinessManager.getInstance().getOrderDetail(
				new OrderDetailRequest(RunningContext.accessToken, mOrderSn),
				new MyCallback<OrderDetail>() {
					@Override
					public void onSuccess(OrderDetail result, String message) {
						if (result == null) {
							showResultErrorToast();
							return;
						}
						String status = BusinessUtil
								.getOrderStatus(result.status);
						mStatusTv.setText(status);
						mStatusTv.setTextColor(getResources()
								.getColor(R.color.common_text_green));
						// 快递信息
						String express = BusinessUtil
								.getExpressDesc(result.type);
						if (!TextUtils.isEmpty(express)) {
							express = express + "：";
						} else {
							express = "";
						}
						if (TextUtils.isEmpty(result.expressNo)) {
							mStatusDescTv.setText("暂无快递号");
						} else {
							mStatusDescTv.setText(express + result.expressNo);
						}

						// 联系人信息
						String info = "";
						if (!TextUtils.isEmpty(result.consignee)) {
							info += result.consignee;
							info += getResources()
									.getString(R.string.text_space);
						}
						if (!TextUtils.isEmpty(result.mobile)) {
							info += result.mobile;
						}
						mNameTv.setText(info);
						mAddressTv.setText(result.address);

						// 玩具列表
						if (ListUtil.isEmpty(result.productList)) {
							mToysContainer.setVisibility(View.GONE);
						} else {
							mToysContainer.setVisibility(View.VISIBLE);
							for (int i = 0; i < result.productList
									.size(); i++) {
								CommonToyView commonToyView = new CommonToyView(
										OrderDetailActivity.this,
										result.productList.get(i));
								commonToyView.showDivider(
										i == result.productList.size() - 1
												? false : true);
								mToysContainer.addView(commonToyView);
							}
						}

						// 订单信息
						mOrderSnTv.setText("订单号：" + result.orderSn);
						mTimeTv.setText("领取时间：" + result.createTime);
					}

					@Override
					public void onError(String errorCode, String message) {
						showToast(message);
					}

					@Override
					public void onFinished() {
					}
				});
	}
}
