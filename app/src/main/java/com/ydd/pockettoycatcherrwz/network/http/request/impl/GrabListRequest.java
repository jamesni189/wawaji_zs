package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.lidroid.xutils.http.client.HttpRequest;
import com.ydd.pockettoycatcherrwz.entity.GrabRecordsInfo;
import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 抓取记录列表
 */
public class GrabListRequest extends AbstractRequest {

	private String accessToken;
	/**
	 * 页码
	 */
	private int page;
	/**
	 * 每页数据量
	 */
	private int pageSize;

	public GrabListRequest(String accessToken, int page, int pageSize) {
		this.accessToken = accessToken;
		this.page = page;
		this.pageSize = pageSize;
	}

	@Override
	public String getUrl() {
		return Constants.BASE_URL + "/doll/log";
	}

	@Override
	public void initRequestParams() {
		addParam("accessToken", accessToken);
		addParam("page", String.valueOf(page));
		addParam("pageSize", String.valueOf(pageSize));
	}

	@Override
	public List<Header> getHeaders() {
		return null;
	}

	@Override
	public Type getResultType() {
		return GrabRecordsInfo.class;
	}

	@Override
	public HttpRequest.HttpMethod getRequestMethod() {
		return HttpRequest.HttpMethod.GET;
	}
}
