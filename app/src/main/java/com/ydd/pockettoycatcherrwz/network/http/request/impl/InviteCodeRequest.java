package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 邀请码兑积分
 */
public class InviteCodeRequest extends AbstractRequest {

	private String code;

	private String accessToken;

	public InviteCodeRequest(String code, String accessToken) {
		this.code = code;
		this.accessToken = accessToken;
	}

	@Override
	public String getUrl() {
		return Constants.BASE_URL + "/invite/code";
	}

	@Override
	public void initRequestParams() {
		addParam("code", code);
		addParam("accessToken", accessToken);
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
