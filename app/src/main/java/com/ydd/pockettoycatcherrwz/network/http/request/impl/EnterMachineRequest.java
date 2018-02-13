package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.lidroid.xutils.http.client.HttpRequest;
import com.ydd.pockettoycatcherrwz.entity.RoomItem;
import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by jia on 17/11/4.
 */


//根据机器id进入房间
public class EnterMachineRequest extends AbstractRequest {


    private String accessToken;
    private String machineSn;


    public EnterMachineRequest(String accessToken,String machineSn) {
        this.accessToken = accessToken;
        this.machineSn = machineSn;
    }

    @Override
    public String getUrl() {
        return Constants.BASE_URL + "/enter/machine";
    }

    @Override
    public void initRequestParams() {
        addParam("accessToken", accessToken);
        addParam("machineSn", machineSn);
    }

    @Override
    public List<Header> getHeaders() {
        return null;
    }

    @Override
    public Type getResultType() {
        return RoomItem.class;
    }

    @Override
    public HttpRequest.HttpMethod getRequestMethod() {
        return HttpRequest.HttpMethod.GET;
    }
}
