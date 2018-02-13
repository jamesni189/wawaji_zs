package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.lidroid.xutils.http.client.HttpRequest;
import com.ydd.pockettoycatcherrwz.entity.UserInfo;
import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 用户信息
 */
public class GetUserInfoRequest extends AbstractRequest {

	private String accessToken;

	public GetUserInfoRequest(String accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public String getUrl() {
		return Constants.BASE_URL + "user/info";
	}

	@Override
	public void initRequestParams() {
		addParam("accessToken", accessToken);
	}

	@Override
	public List<Header> getHeaders() {
		return null;
	}

	@Override
	public Type getResultType() {
		return UserInfo.class;
	}

	@Override
	public HttpRequest.HttpMethod getRequestMethod() {
		return HttpRequest.HttpMethod.GET;
	}
}
