package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.lidroid.xutils.http.client.HttpRequest;
import com.ydd.pockettoycatcherrwz.entity.AddressListBack;
import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by jia on 17/11/4.
 */

public class GetAddressListRequest extends AbstractRequest {

    private String accesstoken;

    private int page;

    private int pagesize;


    public GetAddressListRequest(String accesstoken,int page,int pagesize){
        this.accesstoken=accesstoken;
        this.page=page;
        this.pagesize=pagesize;
    }


    @Override
    public String getUrl() {
        return Constants.BASE_URL+"/address/list";
    }

    @Override
    public void initRequestParams() {
        addParam("accessToken",accesstoken);
        addParam("page",String.valueOf(page));
        addParam("pageSize",String.valueOf(pagesize));

    }

    @Override
    public List<Header> getHeaders() {
        return null;
    }

    @Override
    public Type getResultType() {
        return AddressListBack.class;
    }

    @Override
    public HttpRequest.HttpMethod getRequestMethod() {
        return HttpRequest.HttpMethod.GET;
    }
}
