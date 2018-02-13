package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.client.HttpRequest;
import com.ydd.pockettoycatcherrwz.entity.RoomStatusInfo;
import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 房间状态请求
 *
 * Created by czhang on 17/11/9.
 */

public class RoomStatusRequest extends AbstractRequest {
	private String accessToken;

	private String sn;

	public RoomStatusRequest(String accessToken, String sn) {
		this.accessToken = accessToken;
		this.sn = sn;
	}

	@Override
	public String getUrl() {
		return Constants.BASE_URL + "/room/status";
	}

	@Override
	public void initRequestParams() {
		addParam("accessToken", accessToken);
		addParam("sn", sn);
	}

	@Override
	public List<Header> getHeaders() {
		return null;
	}

	@Override
	public Type getResultType() {
		return new TypeToken<List<RoomStatusInfo>>() {
		}.getType();
	}

	@Override
	public HttpRequest.HttpMethod getRequestMethod() {
		return HttpRequest.HttpMethod.GET;
	}
}
