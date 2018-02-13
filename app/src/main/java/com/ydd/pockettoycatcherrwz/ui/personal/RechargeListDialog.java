package com.ydd.pockettoycatcherrwz.ui.personal;

import java.util.List;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.entity.RechargeItemInfo;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.ChargeListRequest;
import com.ydd.pockettoycatcherrwz.util.ListUtil;
import com.ydd.pockettoycatcherrwz.widget.CommonDialog;

import android.app.Activity;
import android.widget.ListView;
import android.widget.Toast;

/**
 * 充值列表
 */
public class RechargeListDialog extends CommonDialog {

	private Activity mActivity;

	/**
	 * 充值列表
	 */
	private ListView mRechargeLv;
	private RechargeLvAdapter mAdapter;

	public RechargeListDialog(Activity context) {
		super(context);
		mActivity = context;
		loadData();
	}

	private void initView() {
		addContent(R.layout.dlg_recharge_list);
		mRechargeLv = (ListView) findViewById(R.id.lv_dlg_recharge);
		mAdapter = new RechargeLvAdapter(mActivity);
		mRechargeLv.setAdapter(mAdapter);
	}

	private void loadData() {
		BusinessManager.getInstance().chargeList(new ChargeListRequest(),
				new MyCallback<List<RechargeItemInfo>>() {
					@Override
					public void onSuccess(List<RechargeItemInfo> result,
							String message) {
						if (ListUtil.isEmpty(result)) {
							return;
						}
						initView();
						mAdapter.refreshUi(result);
					}

					@Override
					public void onError(String errorCode, String message) {
						if (mActivity != null) {
							Toast.makeText(mActivity, message,
									Toast.LENGTH_SHORT).show();
						}
					}

					@Override
					public void onFinished() {

					}
				});
	}

}
