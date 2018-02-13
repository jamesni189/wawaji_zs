package com.ydd.pockettoycatcherrwz.ui.personal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.LinearLayout;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.entity.AddressInfo;
import com.ydd.pockettoycatcherrwz.entity.AddressListBack;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.GetAddressListRequest;
import com.ydd.pockettoycatcherrwz.ui.BaseTitleActivity;
import com.ydd.pockettoycatcherrwz.util.LogUtil;
import com.ydd.pockettoycatcherrwz.widget.NoImageView;

import org.greenrobot.eventbus.EventBus;

/**
 * 地址管理
 */
public class AddressManageActivity extends BaseTitleActivity {

	/**
	 * 地址列表
	 */
	private PullToRefreshListView mAddressLv;

	private AddressManageLvAdapter lvAdapter;

	public static boolean needrefresh = true;

	/**
	 * 当前页码
	 */
	private int mCurrentPage;

	private boolean needChoose;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContent(R.layout.activity_address_manage);
		needChoose = getIntent().getBooleanExtra("need_choose", false);
		setTitle("地址管理");
		initView();

	}

	private void initView() {
		mAddressLv = (PullToRefreshListView) findViewById(
				R.id.lv_address_manage);
		mAddressLv.setMode(PullToRefreshBase.Mode.PULL_FROM_START);

		NoImageView imageView = new NoImageView(this, NoImageView.NoAddress);
		imageView.setLayoutParams(new LinearLayout.LayoutParams(
				ViewGroup.LayoutParams.MATCH_PARENT,
				ViewGroup.LayoutParams.MATCH_PARENT, 1.0f));
		((ViewGroup) mAddressLv.getParent()).addView(imageView);
		mAddressLv.setEmptyView(imageView);
		imageView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				autoScroll();
			}
		});

		lvAdapter = new AddressManageLvAdapter(this);

		mAddressLv.setAdapter(lvAdapter);
		lvAdapter.setListener(
				new AddressManageLvAdapter.OnDataChangedListener() {
					@Override
					public void onDataChanged() {
						LogUtil.printJ("data change");
						autoScroll();
					}

					@Override
					public void onAddressDeleted(AddressInfo addressInfo) {
						if (needChoose) {
							addressInfo.isChoosed = false;
							EventBus.getDefault().post(addressInfo);
						}
					}
				});

		mAddressLv.setOnRefreshListener(
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

		mAddressLv
				.setOnItemClickListener(new AdapterView.OnItemClickListener() {
					@Override
					public void onItemClick(AdapterView<?> parent, View view,
							int position, long id) {
						if (!needChoose) {
							return;
						}
						if (position - 1 < 0
								|| position - 1 > lvAdapter.getCount()) {
							return;
						}
						AddressInfo addressInfo = lvAdapter
								.getItem(position - 1);
						if (addressInfo != null) {
							addressInfo.isChoosed = true;
							EventBus.getDefault().post(addressInfo);
							finish();
						}
					}
				});

		findViewById(R.id.btn_address_manage_add)
				.setOnClickListener(new View.OnClickListener() {
					@Override
					public void onClick(View v) {
						startActivity(new Intent(AddressManageActivity.this,
								AddAddressActivity.class));
					}
				});
	}

	/**
	 * 加载数据
	 *
	 * @param page
	 */
	private void loadData(final int page) {
		GetAddressListRequest request = new GetAddressListRequest(
				RunningContext.accessToken, page, 20);
		BusinessManager.getInstance().getAddressList(request,
				new MyCallback<AddressListBack>() {
					@Override
					public void onSuccess(AddressListBack result,
							String message) {
						LogUtil.printJ(result.toString());
						if (result.data.size() == 0) {
							showToast("没有更多数据");
							lvAdapter.refreshUi(result.data);
							return;
						}
						mCurrentPage = page;
						if (page == 1) {
							lvAdapter.refreshUi(result.data);
						} else {
							lvAdapter.refreshUiByAdd(result.data);
						}
						needrefresh = false;
					}

					@Override
					public void onError(String errorCode, String message) {
						showToast(message);
					}

					@Override
					public void onFinished() {
						mAddressLv.onRefreshComplete();
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
				mAddressLv.setRefreshing(true);
				loadData(1);
			}
		}, 100);
	}

	@Override
	protected void onResume() {
		super.onResume();
		if (needrefresh) {
			autoScroll();
		}
	}

	@Override
	protected void onDestroy() {
		needrefresh = true;
		lvAdapter.release();
		super.onDestroy();
	}
}
