package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 修改用户信息
 */
public class UserEditRequest extends AbstractRequest {

	private String accessToken;
	/**
	 * 头像
	 */
	private String avatar;
	/**
	 * 昵称
	 */
	private String nickname;

	public UserEditRequest(String accessToken, String avatar, String nickname) {
		this.accessToken = accessToken;
		this.avatar = avatar;
		this.nickname = nickname;
	}

	@Override
	public String getUrl() {
		return Constants.BASE_URL + "/user/edit";
	}

	@Override
	public void initRequestParams() {
		addParam("accessToken", accessToken);
		addParam("avatar", avatar);
		addParam("nickname", nickname);
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
