package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.ydd.pockettoycatcherrwz.entity.AccessToken;
import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 刷新凭证请求
 */
public class RefreshTokenRequest extends AbstractRequest {
	/**
	 * 刷新凭证
	 */
	private String refreshToken;

	public RefreshTokenRequest(String refreshToken) {
		this.refreshToken = refreshToken;
	}

	@Override
	public String getUrl() {
		return Constants.BASE_URL + "/refreshToken";
	}

	@Override
	public void initRequestParams() {
		addParam("refreshToken", refreshToken);
	}

	@Override
	public List<Header> getHeaders() {
		return null;
	}

	@Override
	public Type getResultType() {
		return AccessToken.class;
	}
}
