package com.ydd.pockettoycatcherrwz.ui.personal;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.google.gson.Gson;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.UserManager;
import com.ydd.pockettoycatcherrwz.entity.AddressInfo;
import com.ydd.pockettoycatcherrwz.entity.BusQuit;
import com.ydd.pockettoycatcherrwz.entity.Doll;
import com.ydd.pockettoycatcherrwz.entity.DollCallback;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.GetDefaultAddress;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.SendOrderRequest;
import com.ydd.pockettoycatcherrwz.ui.BaseActivity;
import com.ydd.pockettoycatcherrwz.ui.home.OrderDollAdapter;
import com.ydd.pockettoycatcherrwz.util.LogUtil;
import com.ydd.pockettoycatcherrwz.widget.CommonTitleBar;
import com.ydd.pockettoycatcherrwz.widget.ListView4ScrollView;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jia on 17/11/4.
 */

public class OrderActivity extends BaseActivity
		implements View.OnClickListener {

	private TextView noaddresstxt, addresstxt, nametxt, mobieltxt, mydiamontxt,
			costtxt;

	private CommonTitleBar commonTitleBar;

	private DollCallback callback;

	private ListView4ScrollView listView4ScrollView;

	private OrderDollAdapter adapter;

	private int addressid = -1;

	List<Doll> data = new ArrayList<>();
	private  int expressMoney = 0;

	ArrayList<Integer> productIds = new ArrayList<>();
	ArrayList<Integer> nums = new ArrayList<>();

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		callback = new Gson().fromJson(getIntent().getStringExtra("callback"),
				DollCallback.class);
		expressMoney= getIntent().getIntExtra("expressMoney",0);
		ArrayList<Doll> dolllist = getIntent().getParcelableArrayListExtra("dolllist");
		if (callback == null  && dolllist ==null && dolllist.size() ==0) {
			finish();
			return;
		}else if (callback != null){
			data = callback.data;
		}else if (dolllist !=null && dolllist.size() >0){
			data.clear();
		for (int i=0;i<dolllist.size();i++){
			if (dolllist.get(i).isChoosed){
				data.add(dolllist.get(i));
			}
		}

		}

		for (int i = 0; i < data.size(); i++) {
			int selectNum = data.get(i).selectNum;
			if (data.get(i).isChoosed && selectNum > 0) {
				for (int x = 0; x < selectNum; x++) {
					productIds.add(data.get(i).productId);
					nums.add(data.get(i).selectNum);
				}
			}
		}
		Log.i("===",data.size()+"");

		initView();
		initData();
	}

	private void initView() {
		commonTitleBar = (CommonTitleBar) findViewById(R.id.common_title_bar);
		commonTitleBar.setTitle("填写订单");
		noaddresstxt = (TextView) findViewById(R.id.noaddresstxt);
		addresstxt = (TextView) findViewById(R.id.addresstxt);
		nametxt = (TextView) findViewById(R.id.nametxt);
		mobieltxt = (TextView) findViewById(R.id.mobiletxt);
		mydiamontxt = (TextView) findViewById(R.id.mydimontxt);
		costtxt = (TextView) findViewById(R.id.costdimen);
		listView4ScrollView = (ListView4ScrollView) findViewById(R.id.listview);
		findViewById(R.id.rl_address).setOnClickListener(this);
		findViewById(R.id.okbutton).setOnClickListener(this);
		if (RunningContext.loginTelInfo != null) {
			mydiamontxt.setText(RunningContext.loginTelInfo.money + "");
		}
		adapter = new OrderDollAdapter(this);
		listView4ScrollView.setAdapter(adapter);
		adapter.refreshUi(data);
		costtxt.setText(expressMoney + "");
	}

	@Override
	public void onClick(View view) {
		switch (view.getId()) {
		case R.id.rl_address:
			Intent intent = new Intent(this, AddressManageActivity.class);
			intent.putExtra("need_choose", true);
			startActivity(intent);
			break;
		case R.id.okbutton:

			sendorder();
			break;
		}

	}

	private void initData() {
		BusinessManager.getInstance().getDefaultAddress(
				new GetDefaultAddress(RunningContext.accessToken),
				new MyCallback<AddressInfo>() {
					@Override
					public void onSuccess(AddressInfo result, String message) {
						if (result != null) {
							noaddresstxt.setVisibility(View.GONE);
							addresstxt.setText(result.address);
							nametxt.setText(result.consignee);
							mobieltxt.setText(result.mobile);
							addressid = result.id;
						}
					}

					@Override
					public void onError(String errorCode, String message) {
						LogUtil.printJ("error code=" + errorCode);
						if (errorCode.equals("2")) {
							noaddresstxt.setVisibility(View.VISIBLE);
						}
					}

					@Override
					public void onFinished() {

					}
				});
	}

	private void sendorder() {
		if (addressid == -1) {
			showToast("请选择地址");
			return;
		}
		BusinessManager.getInstance().sendOrder(
				new SendOrderRequest(RunningContext.accessToken, addressid,nums,productIds),
				new MyCallback<Void>() {
					@Override
					public void onSuccess(Void result, String message) {
						EventBus.getDefault().post(new BusQuit());
						UserManager.getInstance().refresh();
						showToast("下单成功");
						finish();
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

	@Override
	protected void onResume() {
		super.onResume();
	}

	@Subscribe(threadMode = ThreadMode.MAIN)
	public void onMessage(final AddressInfo addressInfo) {
		if (addressInfo == null) {
			return;
		}
		if (addressInfo.isChoosed) {
			noaddresstxt.setVisibility(View.GONE);
			addresstxt.setText(addressInfo.address);
			nametxt.setText(addressInfo.consignee);
			mobieltxt.setText(addressInfo.mobile);
			addressid = addressInfo.id;
		} else {
			noaddresstxt.setVisibility(View.VISIBLE);
			addresstxt.setText("");
			nametxt.setText("");
			mobieltxt.setText("");
			addressid = -1;
			initData();
		}
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
	}
}
