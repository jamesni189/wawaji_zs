package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.google.gson.reflect.TypeToken;
import com.lidroid.xutils.http.client.HttpRequest;
import com.ydd.pockettoycatcherrwz.entity.ViewPagerIndicateMessage;
import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by C-yl on 2017/11/24.
 */

public class GetViewPagerIndicateMessage extends AbstractRequest {


    @Override
    public String getUrl() {
       // return Constants.BASE_URL +
        return Constants.BASE_URL +"tags";
    }

    @Override
    public void initRequestParams() {

    }

    @Override
    public List<Header> getHeaders() {
        return null;
    }

    @Override
    public Type getResultType() {
      //  return ViewPagerIndicateMessage.class;
        return new TypeToken<List<ViewPagerIndicateMessage>>() {
        }.getType();
    }

    @Override
    public HttpRequest.HttpMethod getRequestMethod() {
        return HttpRequest.HttpMethod.GET;
    }
}
