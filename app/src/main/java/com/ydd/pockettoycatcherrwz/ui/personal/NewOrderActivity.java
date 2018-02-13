package com.ydd.pockettoycatcherrwz.ui.personal;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.Nullable;
import android.text.TextUtils;
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
import com.ydd.pockettoycatcherrwz.entity.Orders;
import com.ydd.pockettoycatcherrwz.entity.OrdersMessage;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.DollListRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.GetDefaultAddress;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.SendOrderRequest;
import com.ydd.pockettoycatcherrwz.ui.BaseActivity;
import com.ydd.pockettoycatcherrwz.ui.home.OrderDollAdapter;
import com.ydd.pockettoycatcherrwz.util.ListUtil;
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

public class NewOrderActivity extends BaseActivity
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

	List<Integer> productIds = new ArrayList<>();
	List<Integer> nums = new ArrayList<>();

	private List<Doll> dolllist;
	private ArrayList<Doll> arrayListdoll = new ArrayList<>();
	int total=0;
	Orders orders;
	int totalNum =0;

	private Handler mHandle = new Handler() {
		@Override
		public void handleMessage(Message msg) {

			if (msg.what ==1){
				List<Integer> productId = orders.productId;
				for (int i =0;i<dolllist.size();i++){
					int dollProductI = dolllist.get(i).productId;
					for (int x=0;x<productId.size();x++){
						int integer = productId.get(x);
						int integer1 = nums.get(x);
						if (integer == dollProductI){
							dolllist.get(i).num =  integer1;
							data.add(dolllist.get(i));
						}
					}
				}
				if (data != null && data.size()>0) {
					adapter.refreshUi(data);
				}
				costtxt.setText(expressMoney + "");
			}



		}
	};

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_order);
		callback = new Gson().fromJson(getIntent().getStringExtra("callback"),
				DollCallback.class);
		expressMoney= getIntent().getIntExtra("expressMoney",0);
		String url = getIntent().getStringExtra("url");
		dolllist = new ArrayList<>();
		if (TextUtils.isEmpty(url)){
			finish();
			return;

		}else {
			OrdersMessage	OrdersMessage = new Gson().fromJson(url, OrdersMessage.class);
			orders = OrdersMessage.data;
			productIds =orders.productId;
			nums =orders.num;
			totalNum =orders.totalNum;

		}

	//ArrayList<Doll> dolllist = getIntent().getParcelableArrayListExtra("dolllist");

		Log.i("===",data.size()+"");
		initWawaData();
	    initView();
	    initData();
	}

	//获取娃娃数据
	public void initWawaData(){
		BusinessManager.getInstance().getDollList(new DollListRequest(RunningContext.accessToken,totalNum), new MyCallback<DollCallback>() {
			@Override
			public void onSuccess(DollCallback result, String message) {
				callback = result;
				dolllist.clear();
				if (!ListUtil.isEmpty(result.data)) {
					dolllist = result.data;
					total = result.total;
					expressMoney =result.expressMoney;
					mHandle.sendEmptyMessage(1);
					// getButton.setVisibility(View.VISIBLE);
				}

			}

			@Override
			public void onError(String errorCode, String message) {
				Log.i("===", errorCode + message);
			}

			@Override
			public void onFinished() {
			}
		});
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
		//adapter.refreshUi(data);
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
