package com.ydd.pockettoycatcherrwz.network.http.request.impl.wechatimpl;

import com.lidroid.xutils.http.client.HttpRequest;
import com.ydd.pockettoycatcherrwz.entity.WXentity.AuthorResult;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by jia on 17/11/7.
 */

public class WechatRefreshTokenRequest extends AbstractRequest{


    private String appid;

    private String refreshToken;


    public WechatRefreshTokenRequest(String appid,String refreshToken){
        this.appid=appid;
        this.refreshToken=refreshToken;
    }

    @Override
    public String getUrl() {
        return Constants.WXBASE_URL+"oauth2/refresh_token";
    }

    @Override
    public void initRequestParams() {
        addParam("appid",appid);
        addParam("refresh_token",refreshToken);
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
    public boolean parseResultMyself() {
        return true;
    }


    @Override
    public <DataType> Object parseResult(JSONObject response,
                                         MyCallback<DataType> callback) {
        return AuthorResult.class;
    }


    @Override
    public HttpRequest.HttpMethod getRequestMethod() {
        return HttpRequest.HttpMethod.GET;
    }
}

