package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.lidroid.xutils.http.client.HttpRequest;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.entity.WeekOrMouthBean;
import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by C-yl on 2017/12/19.
 */

public class GetweekorMonthMessage extends AbstractRequest {
    private int type;

    public GetweekorMonthMessage(int type) {
        this.type = type;
    }

    @Override
    public String getUrl() {
        return Constants.BASE_URL + "charge/card/v15";
    }

    @Override
    public void initRequestParams() {
        addParam("type",type+"");
        addParam("accessToken", RunningContext.accessToken);
    }

    @Override
    public List<Header> getHeaders() {
        return null;
    }

    @Override
    public Type getResultType() {
        return WeekOrMouthBean.class;
    }

    @Override
    public HttpRequest.HttpMethod getRequestMethod() {
        return HttpRequest.HttpMethod.GET;
    }
}
