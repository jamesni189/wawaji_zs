package com.ydd.pockettoycatcherrwz.network.http.request.impl.wechatimpl;

import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.client.HttpRequest;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.entity.MainPage_Messages;
import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;


import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by C-yl on 2017/12/4.
 */

public class GetMessages extends AbstractRequest {

    @Override
    public String getUrl() {
        return Constants.BASE_URL + "messages";
    }

    @Override
    public void initRequestParams() {
        addParam("accessToken", RunningContext.accessToken);
    }

    @Override
    public List<Header> getHeaders() {
        return null;
    }

    @Override
    public Type getResultType() {
        return new TypeToken<List<MainPage_Messages>>() {
        }.getType();
    }

    @Override
    public HttpRequest.HttpMethod getRequestMethod() {
        return HttpRequest.HttpMethod.GET;
    }
}
