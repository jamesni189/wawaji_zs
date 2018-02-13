package com.ydd.pockettoycatcherrwz.ui.personal;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.entity.RechargeLogInfo;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.ChargeLogRequest;
import com.ydd.pockettoycatcherrwz.ui.BaseTitleActivity;
import com.ydd.pockettoycatcherrwz.util.ListUtil;

/**
 * 订单中心
 */
public class RechargeRecordListActivity extends BaseTitleActivity {

	/**
	 * 订单列表
	 */
	private PullToRefreshListView mListView;
	/**
	 * 列表适配器
	 */
	private RechargeRecordLvAdapter mAdapter;
	/**
	 * 当前页码
	 */
	private int mCurrentPage;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContent(R.layout.activity_recharge_record_list);
		setTitle("充值记录");
		initView();
		autoScroll();
	}

	private void initView() {
		mListView = (PullToRefreshListView) findViewById(
				R.id.lv_recharge_record);
		mAdapter = new RechargeRecordLvAdapter(this);
		mListView.setAdapter(mAdapter);
		mListView.setMode(PullToRefreshBase.Mode.BOTH);
		mListView.setOnRefreshListener(
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
		ChargeLogRequest request = new ChargeLogRequest(
				RunningContext.accessToken, page, 20);
		BusinessManager.getInstance().chargeLog(request,
				new MyCallback<RechargeLogInfo>() {
					@Override
					public void onSuccess(RechargeLogInfo result,
							String message) {
						if (result == null) {
							showResultErrorToast();
							return;
						}
						if (ListUtil.isEmpty(result.data)) {
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
						// List<RechargeRecord> datas = new
						// ArrayList<RechargeRecord>();
						// for (int i=0;i<10;i++){
						// RechargeRecord rechargeRecord = new RechargeRecord();
						// rechargeRecord.status = i%2;
						// rechargeRecord.type = i%2;
						// rechargeRecord.points = 8000;
						// rechargeRecord.price = 99;
						// rechargeRecord.createTime = "2017.10.13 00:35";
						// datas.add(rechargeRecord);
						// }
						// mAdapter.refreshUiByAdd(datas);
						showToast(message);
					}

					@Override
					public void onFinished() {
						mListView.onRefreshComplete();
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
				mListView.setRefreshing(true);
				loadData(1);
			}
		}, 100);
	}

}
