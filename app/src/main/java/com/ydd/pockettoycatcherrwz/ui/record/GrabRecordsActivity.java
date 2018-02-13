package com.ydd.pockettoycatcherrwz.ui.record;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.entity.GrabDetail;
import com.ydd.pockettoycatcherrwz.entity.GrabRecordsInfo;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.GrabListRequest;
import com.ydd.pockettoycatcherrwz.ui.BaseTitleActivity;
import com.ydd.pockettoycatcherrwz.util.ListUtil;
import com.ydd.pockettoycatcherrwz.widget.NoImageView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * 抓取记录页
 */
public class GrabRecordsActivity extends BaseTitleActivity {

	/**
	 * 订单列表
	 */
	private PullToRefreshListView mGrabLv;
	/**
	 * 列表适配器
	 */
	private GrabRecordLvAdapter mAdapter;
	/**
	 * 当前页码
	 */
	private int mCurrentPage;

	private boolean needRefresh;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContent(R.layout.activity_grab_records);
		setTitle("抓取记录");
		initView();
		autoScroll();
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (needRefresh) {
			autoScroll();
		}
	}

	private void initView() {
		mGrabLv = (PullToRefreshListView) findViewById(R.id.lv_grab_records);
		mAdapter = new GrabRecordLvAdapter(this);
		NoImageView imageView=new NoImageView(this,NoImageView.NoOrders);
		mGrabLv.setEmptyView(imageView);
		mGrabLv.setAdapter(mAdapter);
		mGrabLv.setMode(PullToRefreshBase.Mode.BOTH);
		mGrabLv.setOnRefreshListener(
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
		GrabListRequest request = new GrabListRequest(
				RunningContext.accessToken, page, 20);
		BusinessManager.getInstance().getGrabList(request,
				new MyCallback<GrabRecordsInfo>() {
					@Override
					public void onSuccess(GrabRecordsInfo result,
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
						showToast(message);
					}

					@Override
					public void onFinished() {
						mGrabLv.onRefreshComplete();
					}
				});
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessage(final GrabDetail grabDetail) {
		// needRefresh = true;
		autoScroll();
	}

	/**
	 * 自动加载
	 */
	private void autoScroll() {
		needRefresh = false;
		mGrabLv.setRefreshing(true);
		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				loadData(1);
			}
		}, 100);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
