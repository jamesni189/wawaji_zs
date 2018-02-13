package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.lidroid.xutils.http.client.HttpRequest;
import com.ydd.pockettoycatcherrwz.entity.GrabDetail;
import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 获取抓取记录详情
 */
public class GetGrabDetailRequest extends AbstractRequest {

	private long id;

	private String accessToken;

	public GetGrabDetailRequest(long id, String accessToken) {
		this.id = id;
		this.accessToken = accessToken;
	}

	@Override
	public String getUrl() {
		return Constants.BASE_URL + "/doll/log/info";
	}

	@Override
	public void initRequestParams() {
		addParam("id", String.valueOf(id));
		addParam("accessToken", accessToken);
	}

	@Override
	public List<Header> getHeaders() {
		return null;
	}

	@Override
	public Type getResultType() {
		return GrabDetail.class;
	}

	@Override
	public HttpRequest.HttpMethod getRequestMethod() {
		return HttpRequest.HttpMethod.GET;
	}
}
