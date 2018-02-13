package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.lidroid.xutils.http.client.HttpRequest;
import com.ydd.pockettoycatcherrwz.entity.HomeInfo;
import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 首页请求
 * 
 * Created by czhang on 17/10/28.
 */

public class HomeRequest extends AbstractRequest {

	/**
	 * 页码
	 */
	private int page;
	/**
	 * 每页数据量
	 */
	private int pageSize;
	private int id;
	public HomeRequest(int page, int pageSize) {
		this.page = page;
		this.pageSize = pageSize;
	}
	public HomeRequest(int page, int pageSize, int id) {
		this.page = page;
		this.pageSize = pageSize;
		this.id = id;
	}

	@Override
	public String getUrl() {
		return Constants.BASE_URL + "home/tag?type="+id;
	}

	@Override
	public void initRequestParams() {
		addParam("page", String.valueOf(page));
		addParam("pageSize", String.valueOf(pageSize));
	}

	@Override
	public List<Header> getHeaders() {
		return null;
	}

	@Override
	public Type getResultType() {
		return HomeInfo.class;
	}

	@Override
	public HttpRequest.HttpMethod getRequestMethod() {
		return HttpRequest.HttpMethod.GET;
	}

}
