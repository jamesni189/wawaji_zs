package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.ydd.pockettoycatcherrwz.entity.RankCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 土豪月榜单
 */
public class RichMonthRankRequest extends AbstractRequest {


	private String page;
	private String pageSize;
	private String accessToken;

	public RichMonthRankRequest(String accessToken, String page, String pageSize) {
		this.page = page;
		this.pageSize = pageSize;
		this.accessToken = accessToken;
	}

	@Override
	public String getUrl() {
		return Constants.BASE_URL + "summary/diamond/month";
	}

	@Override
	public void initRequestParams() {
		addParam("page", page);
		addParam("accessToken", accessToken);
		addParam("pageSize", pageSize);
	}

	@Override
	public List<Header> getHeaders() {
		return null;
	}

	@Override
	public Type getResultType() {
		return RankCallback.class;
	}
}
