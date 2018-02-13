package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 发送短信，获取验证码
 */
public class GetCodeRequest extends AbstractRequest {

	private String mobile;

	public GetCodeRequest(String mobile) {
		this.mobile = mobile;
	}

	@Override
	public String getUrl() {
		return Constants.BASE_URL + "/mobile/captcha";
	}

	@Override
	public void initRequestParams() {
		addParam("mobile", mobile);
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
