package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.ydd.pockettoycatcherrwz.entity.SignSuccessInfo;
import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 签到
 */
public class SignRequest extends AbstractRequest {

	private String accessToken;

	public SignRequest(String accessToken) {
		this.accessToken = accessToken;
	}

	@Override
	public String getUrl() {
		return Constants.BASE_URL + "/sign/v15";
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
		return SignSuccessInfo.class;
	}
}
