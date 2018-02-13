package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.lidroid.xutils.http.client.HttpRequest;
import com.ydd.pockettoycatcherrwz.entity.OrderDetail;
import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 订单详情
 */
public class OrderDetailRequest extends AbstractRequest {

	public String accessToken;
	/**
	 * 订单号
	 */
	public String orderSn;

	public OrderDetailRequest(String accessToken, String orderSn) {
		this.accessToken = accessToken;
		this.orderSn = orderSn;
	}

	@Override
	public String getUrl() {
		return Constants.BASE_URL + "/order/info";
	}

	@Override
	public void initRequestParams() {
		addParam("accessToken", accessToken);
		addParam("orderSn", orderSn);
	}

	@Override
	public List<Header> getHeaders() {
		return null;
	}

	@Override
	public Type getResultType() {
		return OrderDetail.class;
	}

	@Override
	public HttpRequest.HttpMethod getRequestMethod() {
		return HttpRequest.HttpMethod.GET;
	}
}
