package com.ydd.pockettoycatcherrwz.network.http.request.impl.wechatimpl;

import com.lidroid.xutils.http.client.HttpRequest;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by C-yl on 2017/12/5.
 */

public class DelteMessages extends AbstractRequest {
    private String id;
    public DelteMessages(String id) {
        this.id=id;
    }

    @Override
    public String getUrl() {
        return Constants.BASE_URL + "messages/delete";
    }

    @Override
    public void initRequestParams() {
        addParam("accessToken", RunningContext.accessToken);
        addParam("messageId",id);
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
