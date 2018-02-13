package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.client.HttpRequest;
import com.ydd.pockettoycatcherrwz.entity.BannerInfo;
import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 首页热点活动
 */
public class BannerRequest extends AbstractRequest {

	@Override
	public String getUrl() {
		return Constants.BASE_URL + "/banner";
	}

	@Override
	public void initRequestParams() {
	}

	@Override
	public List<Header> getHeaders() {
		return null;
	}

	@Override
	public Type getResultType() {
		return new TypeToken<List<BannerInfo>>() {
		}.getType();
	}

	@Override
	public HttpRequest.HttpMethod getRequestMethod() {
		return HttpRequest.HttpMethod.GET;
	}
}
