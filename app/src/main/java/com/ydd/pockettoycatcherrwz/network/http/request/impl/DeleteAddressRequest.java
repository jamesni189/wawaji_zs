package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 删除地址
 */
public class DeleteAddressRequest extends AbstractRequest {

	private String accessToken;
	/**
	 * 地址id
	 */
	private int id;

	public DeleteAddressRequest(String accessToken, int id) {
		this.accessToken = accessToken;
		this.id = id;
	}

	@Override
	public String getUrl() {
		return Constants.BASE_URL + "/address/delete";
	}

	@Override
	public void initRequestParams() {
		addParam("accessToken", accessToken);
		addParam("id", String.valueOf(id));
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
