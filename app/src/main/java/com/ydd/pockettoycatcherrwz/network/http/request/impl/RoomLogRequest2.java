package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.lidroid.xutils.http.client.HttpRequest;
import com.ydd.pockettoycatcherrwz.entity.RoomLogInfo;
import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by jia on 17/11/9.
 */

public class RoomLogRequest2 extends AbstractRequest {

    private String machineId;

    private String accesstoken;


    public RoomLogRequest2(String machineId, String accesstoken) {
        this.machineId = machineId;
        this.accesstoken = accesstoken;

    }

    @Override
    public String getUrl() {
        return Constants.BASE_URL + "/doll/log/last";
    }

    @Override
    public void initRequestParams() {
        addParam("machineId", machineId);
        addParam("accessToken", accesstoken);

    }

    @Override
    public List<Header> getHeaders() {
        return null;
    }

    @Override
    public Type getResultType() {
        return RoomLogInfo.class;
    }

    @Override
    public HttpRequest.HttpMethod getRequestMethod() {
        return HttpRequest.HttpMethod.POST;
    }
}
