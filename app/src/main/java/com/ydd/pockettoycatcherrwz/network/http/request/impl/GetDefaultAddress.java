package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.lidroid.xutils.http.client.HttpRequest;
import com.ydd.pockettoycatcherrwz.entity.AddressInfo;
import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by jia on 17/11/4.
 */

public class GetDefaultAddress extends AbstractRequest {



    private String accessToken;

    public GetDefaultAddress(String accessToken){
        this.accessToken=accessToken;
    }

    @Override
    public String getUrl() {
        return Constants.BASE_URL+"/address/default";
    }

    @Override
    public void initRequestParams() {
            addParam("accessToken",accessToken);
    }

    @Override
    public List<Header> getHeaders() {
        return null;
    }

    @Override
    public Type getResultType() {
        return AddressInfo.class;
    }

    @Override
    public HttpRequest.HttpMethod getRequestMethod() {
        return HttpRequest.HttpMethod.GET;
    }
}
