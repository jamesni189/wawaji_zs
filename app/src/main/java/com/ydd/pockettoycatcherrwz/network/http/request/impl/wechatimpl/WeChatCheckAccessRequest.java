package com.ydd.pockettoycatcherrwz.network.http.request.impl.wechatimpl;

import com.lidroid.xutils.http.client.HttpRequest;
import com.ydd.pockettoycatcherrwz.entity.WXentity.WXuserInfo;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;
import org.json.JSONObject;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by jia on 17/11/7.
 *
 * 微信检验授权凭证（access_token）是否有效接口
 */

public class WeChatCheckAccessRequest extends AbstractRequest {

    private String accessToken;

    private String openid;


    public WeChatCheckAccessRequest(String accessToken, String openid) {
        this.accessToken = accessToken;
        this.openid = openid;
    }

    @Override
    public String getUrl() {
        return Constants.WXBASE_URL+"/sns/auth";
    }

    @Override
    public void initRequestParams() {
        addParam("access_token",accessToken);
        addParam("openid",openid);
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


    @Override
    public boolean parseResultMyself() {
        return true;
    }


    @Override
    public <DataType> Object parseResult(JSONObject response,
                                         MyCallback<DataType> callback) {
        return WXuserInfo.class;
    }
}
