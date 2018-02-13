package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.lidroid.xutils.http.client.HttpRequest;
import com.ydd.pockettoycatcherrwz.entity.RechargeLogInfo;
import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 充值记录列表
 */
public class ChargeLogRequest extends AbstractRequest {

    private String accessToken;
    private int page;
    private int pageSize;

    public ChargeLogRequest(String accessToken, int page, int pageSize) {
        this.accessToken = accessToken;
        this.page = page;
        this.pageSize = pageSize;
    }

    @Override
    public String getUrl() {
        return Constants.BASE_URL + "/charge/log/v15";
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
        return RechargeLogInfo.class;
    }

    @Override
    public HttpRequest.HttpMethod getRequestMethod() {
        return HttpRequest.HttpMethod.GET;
    }
}
