package com.ydd.pockettoycatcherrwz.network.http.request.impl.wechatimpl;

import com.google.gson.Gson;
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
 * 微信获取用户个人信息（UnionID机制）接口
 */

public class WechatGetUserInfoRequest extends AbstractRequest{


    private String access_token;

    private String openid;


    public WechatGetUserInfoRequest(String access_token,String openid){
        this.access_token=access_token;
        this.openid=openid;
    }

    @Override
    public String getUrl() {
        return Constants.WXBASE_URL+"/sns/userinfo";
    }

    @Override
    public void initRequestParams() {
        addParam("openid",openid);
        addParam("access_token",access_token);
    }

    @Override
    public List<Header> getHeaders() {
        return null;
    }

    @Override
    public Type getResultType() {
        return WXuserInfo.class;
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
