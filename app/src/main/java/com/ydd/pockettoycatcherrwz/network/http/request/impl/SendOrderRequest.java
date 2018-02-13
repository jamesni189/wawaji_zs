package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by jia on 17/11/4.
 */

public class SendOrderRequest extends AbstractRequest {

	private String accessToken;

	private int addressId;

	private List<Integer> nums;
	private List<Integer> 	productIds;

	public SendOrderRequest(String accessToken, int addressId) {
		this.accessToken = accessToken;
		this.addressId = addressId;
	}
	public SendOrderRequest(String accessToken, int addressId,ArrayList<Integer> nums,ArrayList<Integer>	productIds) {
		this.accessToken = accessToken;
		this.addressId = addressId;
		//this.nums = nums;
		//this.productIds = productIds;
	}

	public SendOrderRequest(String accessToken, int addressId,List<Integer> nums,List<Integer>	productIds) {
		this.accessToken = accessToken;
		this.addressId = addressId;
		this.nums = nums;
		this.productIds = productIds;
	}

	@Override
	public String getUrl() {
		return Constants.BASE_URL + "/order/part";
	}

	@Override
	public void initRequestParams() {
		addParam("accessToken", accessToken);
		addParam("addressId", String.valueOf(addressId));
		for(int i= 0;i<nums.size();i++ ){
			addParam("nums", nums.get(i)+"");
			addParam("productIds", productIds.get(i)+"");
		}
	}

	@Override
	public List<Header> getHeaders() {
		return null;
	}

	@Override
	public Type getResultType() {
		return Void.class;
	}
}
