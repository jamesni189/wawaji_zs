package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.lidroid.xutils.http.client.HttpRequest;
import com.ydd.pockettoycatcherrwz.entity.AppUpdataMessage;
import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by C-yl on 2017/12/21.
 */

public class GetupDataMessage extends AbstractRequest {
    private  String version;
    public GetupDataMessage(String  version) {
        this.version=version;
    }

    @Override
    public String getUrl() {
        return Constants.BASE_URL + "home/update";
    }

    @Override
    public void initRequestParams() {
        addParam("version",version);
        addParam("platform","1");
    }

    @Override
    public List<Header> getHeaders() {
        return null;
    }

    @Override
    public Type getResultType() {
        return AppUpdataMessage.class;
    }

    @Override
    public HttpRequest.HttpMethod getRequestMethod() {
        return HttpRequest.HttpMethod.GET;
    }
}
