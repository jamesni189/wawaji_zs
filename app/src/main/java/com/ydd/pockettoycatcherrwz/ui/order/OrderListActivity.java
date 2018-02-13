package com.ydd.pockettoycatcherrwz.ui.order;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.entity.OrderListBack;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.OrderListRequest;
import com.ydd.pockettoycatcherrwz.ui.BaseTitleActivity;
import com.ydd.pockettoycatcherrwz.util.LogUtil;
import com.ydd.pockettoycatcherrwz.widget.NoImageView;

/**
 * 订单中心
 */
public class OrderListActivity extends BaseTitleActivity {

	/**
	 * 订单列表
	 */
	private PullToRefreshListView mOrdersLv;
	/**
	 * 列表适配器
	 */
	private OrderLvAdapter mAdapter;
	/**
	 * 当前页码
	 */
	private int mCurrentPage;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContent(R.layout.activity_order_list);
		setTitle("订单中心");
		initView();
		autoScroll();
	}

	private void initView() {
		mOrdersLv = (PullToRefreshListView) findViewById(R.id.lv_orders);
		mAdapter = new OrderLvAdapter(this);
		NoImageView imageView = new NoImageView(this, NoImageView.NoOrders);
		imageView.setLayoutParams(new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
		((ViewGroup) mOrdersLv.getParent()).addView(imageView);
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				autoScroll();
			}
		});
		mOrdersLv.setEmptyView(imageView);
		mOrdersLv.setAdapter(mAdapter);
		mOrdersLv.setMode(PullToRefreshBase.Mode.BOTH);
		mOrdersLv.setOnRefreshListener(
				new PullToRefreshBase.OnRefreshListener2<ListView>() {
					@Override
					public void onPullDownToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						loadData(1);
					}

					@Override
					public void onPullUpToRefresh(
							PullToRefreshBase<ListView> refreshView) {
						loadData(mCurrentPage + 1);
					}
				});
	}

	/**
	 * 加载数据
	 * 
	 * @param page
	 */
	private void loadData(final int page) {
		OrderListRequest request = new OrderListRequest(
				RunningContext.accessToken, page, 20);
		BusinessManager.getInstance().getOrderList(request,
				new MyCallback<OrderListBack>() {
					@Override
					public void onSuccess(OrderListBack result,
							String message) {
						LogUtil.printJ(result.toString());
						if (result.data.size() == 0) {
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
						mOrdersLv.onRefreshComplete();
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
				mOrdersLv.setRefreshing(true);
				loadData(1);
			}
		}, 100);
	}

}
