package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.lidroid.xutils.http.client.HttpRequest;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by C-yl on 2017/12/18.
 */

public class Share_accice extends AbstractRequest{
    private String type;

    public Share_accice(String type) {
        this.type=type;
    }

    @Override
    public String getUrl() {
        return Constants.BASE_URL + "user/taskComplete/v15";
    }

    @Override
    public void initRequestParams() {
        addParam("accessToken", RunningContext.accessToken);
        addParam("type", type);
    }

    @Override
    public List<Header> getHeaders() {
        return null;
    }

    @Override
    public Type getResultType() {
        return null;
    }

    @Override
    public HttpRequest.HttpMethod getRequestMethod() {
        return HttpRequest.HttpMethod.POST;
    }
}
