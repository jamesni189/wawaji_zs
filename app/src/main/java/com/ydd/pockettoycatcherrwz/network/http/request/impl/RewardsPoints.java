package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.lidroid.xutils.http.client.HttpRequest;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by C-yl on 2017/12/14.
 */

public class RewardsPoints extends AbstractRequest {

    private String parmers;
    private int type;

    public RewardsPoints(String parmers) {
        this.parmers = parmers;
    }
    public RewardsPoints(int parmers) {
        this.type = parmers;
    }

    @Override
    public String getUrl() {
    //    return Constants.BASE_URL + "/user/getReward/v15";
        return Constants.BASE_URL + "task/receiveCard";
    }

    @Override
    public void initRequestParams() {
        addParam("accessToken", RunningContext.accessToken);
        addParam("type", type+"");
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
        return HttpRequest.HttpMethod.GET;
    }
}
