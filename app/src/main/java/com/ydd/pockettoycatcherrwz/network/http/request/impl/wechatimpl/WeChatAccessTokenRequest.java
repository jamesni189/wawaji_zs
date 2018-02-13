package com.ydd.pockettoycatcherrwz.network.http.request.impl.wechatimpl;

import com.google.gson.Gson;
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
 * 微信获取accesstoken的请求
 */

public class WeChatAccessTokenRequest extends AbstractRequest {

    private String appid;

    private String secret;

    private String code;

    public WeChatAccessTokenRequest(String appid, String secret, String code) {
        this.appid = appid;
        this.secret = secret;
        this.code = code;
    }


    @Override
    public String getUrl() {
        return Constants.WXBASE_URL + "/sns/oauth2/access_token";
    }

    @Override
    public void initRequestParams() {
        addParam("appid", appid);
        addParam("secret", secret);
        addParam("code", String.valueOf(code));
        addParam("grant_type", "authorization_code");
    }

    @Override
    public List<Header> getHeaders() {
        return null;
    }

    @Override
    public Type getResultType() {
        return AuthorResult.class;
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
        try {
            Gson resultGson = new Gson();
            DataType resultObj = (DataType) resultGson
                    .fromJson(response.toString(), getResultType());
            callback.onSuccess(resultObj, "解析成功");
            callback.onFinished();
        }catch (Exception e){
            callback.onError("-1", "解析失败");
            callback.onFinished();
        }

        return null;
    }
}
