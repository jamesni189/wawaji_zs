package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.ydd.pockettoycatcherrwz.entity.LoginTelInfo;
import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 手机号登录
 */
public class LoginTelRequest extends AbstractRequest {

	/**
	 * 验证码
	 */
	private String code;
	/**
	 * 手机号
	 */
	private String mobile;

	public LoginTelRequest(String code, String mobile) {
		this.code = code;
		this.mobile = mobile;
	}

	@Override
	public String getUrl() {
		return Constants.BASE_URL + "/login";
	}

	@Override
	public void initRequestParams() {
		addParam("code", code);
		addParam("mobile", mobile);
		addParam("platform", "2");
	}

	@Override
	public List<Header> getHeaders() {
		return null;
	}

	@Override
	public Type getResultType() {
		return LoginTelInfo.class;
	}
}
