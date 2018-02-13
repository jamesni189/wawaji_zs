package com.ydd.pockettoycatcherrwz.ui.personal;

import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.text.Editable;
import android.text.InputFilter;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;

import com.lljjcoder.bean.CityBean;
import com.lljjcoder.bean.DistrictBean;
import com.lljjcoder.bean.ProvinceBean;
import com.lljjcoder.citywheel.CityConfig;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.entity.AddressInfo;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.SaveAddressRequest;
import com.ydd.pockettoycatcherrwz.ui.BaseTitleActivity;
import com.ydd.pockettoycatcherrwz.util.CommonUtil;
import com.ydd.pockettoycatcherrwz.util.Constants;
import com.ydd.pockettoycatcherrwz.widget.CityPickerView;
import com.ydd.pockettoycatcherrwz.widget.OnMultiClickListener;

/**
 * 新增地址
 */
public class AddAddressActivity extends BaseTitleActivity {
	/**
	 * 姓名hint
	 */
	private TextView mNameHintTv;
	/**
	 * 姓名输入框
	 */
	private EditText mNameEt;
	/**
	 * 手机号hint
	 */
	private TextView mMobileHintTv;
	/**
	 * 手机号
	 */
	private EditText mMobileEt;
	/**
	 * 地址选择
	 */
	private TextView mAddressEt;
	/**
	 * 详细地址
	 */
	private EditText mAddressDetailEt;
	/**
	 * 地址，省市区
	 */
	private String mAddressPre;

	private String mAddressDetail;

	private CityPickerView mCityPickerView;

	private AddressInfo addressInfo;

	private int id;

	private int isdefault = 0;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContent(R.layout.activity_add_address);
		addressInfo = (AddressInfo) getIntent().getSerializableExtra("info");
		if (addressInfo == null) {
			setTitle("新建地址");
			id = Constants.NONE_ID;
		} else {
			setTitle("编辑地址");
			id = addressInfo.id;
			isdefault = addressInfo.isDefault;
		}
		initCityPicker("直辖市", "北京", "东城区");
		initView();

		new Handler().postDelayed(new Runnable() {
			@Override
			public void run() {
				CommonUtil.hideKeyBoard(AddAddressActivity.this);
			}
		}, 100);
	}

	private void initView() {
		mNameHintTv = (TextView) findViewById(R.id.tv_add_address_name_hint);
		mNameEt = (EditText) findViewById(R.id.et_add_address_name);
		mMobileHintTv = (TextView) findViewById(
				R.id.tv_add_address_mobile_hint);
		mMobileEt = (EditText) findViewById(R.id.et_add_address_mobile);
		mAddressEt = (TextView) findViewById(R.id.et_add_address_address);
		mAddressDetailEt = (EditText) findViewById(R.id.et_add_address_detail);
		if (addressInfo != null) {
			mNameEt.setText(addressInfo.consignee);
			mMobileHintTv.setVisibility(View.GONE);
			mNameHintTv.setVisibility(View.GONE);
			mMobileEt.setText(addressInfo.mobile);
			String[] address = mCityPickerView
					.parseAddress(addressInfo.address);
			if (address != null) {
				mAddressPre = address[0] + address[1] + address[2];
				mAddressEt.setText(mAddressPre);
				mAddressDetailEt.setText(address[3]);
				// copy 没时间搞
				initCityPicker(address[0], address[1], address[2]);
			}
		}

		findViewById(R.id.ln_add_address_address)
				.setOnClickListener(new OnMultiClickListener() {
					@Override
					public void onMultiClick(View v) {
						CommonUtil.hideKeyBoard(AddAddressActivity.this);
						showCityPicker();
					}
				});
		findViewById(R.id.btn_add_address_bottom)
				.setOnClickListener(mOnClickListener);

		mMobileEt.setFilters(
				new InputFilter[] { new InputFilter.LengthFilter(11) });
		mMobileEt.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (TextUtils.isEmpty(s)) {
					mMobileHintTv.setVisibility(View.VISIBLE);
				} else {
					mMobileHintTv.setVisibility(View.GONE);
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});

		mNameEt.addTextChangedListener(new TextWatcher() {
			@Override
			public void beforeTextChanged(CharSequence s, int start, int count,
					int after) {
			}

			@Override
			public void onTextChanged(CharSequence s, int start, int before,
					int count) {
				if (TextUtils.isEmpty(s)) {
					mNameHintTv.setVisibility(View.VISIBLE);
				} else {
					mNameHintTv.setVisibility(View.GONE);
				}
			}

			@Override
			public void afterTextChanged(Editable s) {
			}
		});
	}

	private View.OnClickListener mOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			switch (v.getId()) {
			case R.id.btn_add_address_bottom:
				doBottomClick();
				break;
			}
		}
	};

	/**
	 * 点击底部按钮
	 */
	private void doBottomClick() {
		String name = mNameEt.getText().toString();
		if (TextUtils.isEmpty(name)) {
			showToast("请输入姓名");
			return;
		}
		String mobile = mMobileEt.getText().toString();
		if (TextUtils.isEmpty(mobile)) {
			showToast("请输入手机号");
			return;
		}
		String addressPre = mAddressEt.getText().toString();
		if (TextUtils.isEmpty(addressPre)) {
			showToast("请选择地区");
			return;
		}
		String addressDetail = mAddressDetailEt.getText().toString();
		if (TextUtils.isEmpty(addressDetail)) {
			showToast("请填写详细地址");
			return;
		}
		SaveAddressRequest request = new SaveAddressRequest(
				RunningContext.accessToken, addressPre + addressDetail, name,
				id, isdefault, mobile);
		showDialog("");
		BusinessManager.getInstance().saveAddress(request,
				new MyCallback<Void>() {
					@Override
					public void onSuccess(Void result, String message) {
						AddressManageActivity.needrefresh = true;
						finish();
					}

					@Override
					public void onError(String errorCode, String message) {
						showToast(message);
					}

					@Override
					public void onFinished() {
						dismissDialog();
					}
				});
	}

	/**
	 * 地址选择
	 *
	 */
	private void showCityPicker() {
		mCityPickerView.show();
	}

	private void initCityPicker(String province, String city, String district) {
		// 详细属性设置，如果不需要自定义样式的话，可以直接使用默认的，去掉下面的属性设置，直接build()即可。
		CityConfig cityConfig = new CityConfig.Builder(AddAddressActivity.this)
				.title("地址选择").titleBackgroundColor("#FFFFFF").textSize(15)
				.titleTextColor("#000000").textColor("#000000")
				.confirTextColor("#000000").cancelTextColor("#000000")
				.province(province).city(city).district(district)
				.visibleItemsCount(7).provinceCyclic(false).cityCyclic(false)
				.districtCyclic(false).itemPadding(10)
				.setCityInfoType(CityConfig.CityInfoType.BASE)
				.setCityWheelType(CityConfig.WheelType.PRO_CITY_DIS).build();

		// 配置属性
		mCityPickerView = new CityPickerView(cityConfig);
		// 监听方法，获取选择结果
		mCityPickerView.setOnCityItemClickListener(
				new CityPickerView.OnCityItemClickListener() {
					@Override
					public void onSelected(ProvinceBean provinceBean,
							CityBean cityBean, DistrictBean districtBean) {
						// ProvinceBean 省份信息
						// CityBean 城市信息
						// DistrictBean 区县信息
						if (provinceBean == null) {
							return;
						}
						try {
							String province = provinceBean.getName();
							String city = "";
							if (cityBean != null
									&& !TextUtils.isEmpty(cityBean.getName())) {
								city = cityBean.getName();
							}
							String district = "";
							if (districtBean != null && !TextUtils
									.isEmpty(districtBean.getName())) {
								district = districtBean.getName();
							}
							mAddressPre = province + city + district;
							mAddressEt.setText(mAddressPre);
						} catch (Exception e) {
						}

					}

					@Override
					public void onCancel() {

					}
				});
	}

	@Override
	protected void onDestroy() {
		if (mCityPickerView != null && mCityPickerView.isShow()) {
			mCityPickerView.hide();
		}
		super.onDestroy();
	}
}
