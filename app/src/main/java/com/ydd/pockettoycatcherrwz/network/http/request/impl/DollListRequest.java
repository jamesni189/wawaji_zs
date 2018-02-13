package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.lidroid.xutils.http.client.HttpRequest;
import com.ydd.pockettoycatcherrwz.entity.DollCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by jia on 17/11/4.
 */

public class DollListRequest extends AbstractRequest {


    private String accessToken;
    private  int  totalNum;


    public DollListRequest(String accessToken) {
        this.accessToken = accessToken;
    }
    public DollListRequest(String accessToken ,int totalNum) {
        this.accessToken = accessToken;
        this.totalNum = totalNum;
    }

    @Override
    public String getUrl() {
        return Constants.BASE_URL + "/doll/giftBox";
    }

    @Override
    public void initRequestParams() {
        addParam("accessToken", accessToken);
    }

    @Override
    public List<Header> getHeaders() {
        return null;
    }

    @Override
    public Type getResultType() {
        return DollCallback.class;
    }

    @Override
    public HttpRequest.HttpMethod getRequestMethod() {
        return HttpRequest.HttpMethod.GET;
    }
}
