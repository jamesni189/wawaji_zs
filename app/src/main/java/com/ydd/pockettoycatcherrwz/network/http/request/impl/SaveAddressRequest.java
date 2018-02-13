package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 新增或修改地址
 */
public class SaveAddressRequest extends AbstractRequest {

	private String accessToken;
	/**
	 * 具体地址
	 */
	private String address;
	/**
	 * 收货人
	 */
	private String consignee;
	/**
	 * 地址ID 传就是更新 不传新增
	 */
	private int id;
	/**
	 * 1:默认地址 0:非默认地址
	 */
	private int isDefault;
	/**
	 * 手机号
	 */
	private String mobile;

	public SaveAddressRequest(String accessToken, String address,
			String consignee, int id, int isDefault, String mobile) {
		this.accessToken = accessToken;
		this.address = address;
		this.consignee = consignee;
		this.id = id;
		this.isDefault = isDefault;
		this.mobile = mobile;
	}

	@Override
	public String getUrl() {
		return Constants.BASE_URL + "/address";
	}

	@Override
	public void initRequestParams() {
		addParam("accessToken", accessToken);
		addParam("address", address);
		addParam("consignee", consignee);
		if (id != Constants.NONE_ID) {
			addParam("id", String.valueOf(id));
		}
		addParam("isDefault", String.valueOf(isDefault));
		addParam("mobile", String.valueOf(mobile));
	}

	@Override
	public List<Header> getHeaders() {
		return null;
	}

	@Override
	public Type getResultType() {
		return null;
	}
}
