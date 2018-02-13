package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.ydd.pockettoycatcherrwz.entity.LoginTelInfo;
import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by jia on 17/11/7.
 */

public class LoginWechatRequest extends AbstractRequest {


    private String avatar;

    private String nickname;

    private String openid;


    public LoginWechatRequest(String avatar,String nickname,String openid){
        this.avatar=avatar;
        this.nickname=nickname;
        this.openid=openid;
    }

    @Override
    public String getUrl() {
        return Constants.BASE_URL+"/oauth/login";
    }

    @Override
    public void initRequestParams() {
        addParam("avatar",avatar);
        addParam("nickname",nickname);
        addParam("oauthId",openid);
        addParam("code",openid);
        addParam("platform","2");

    }

    @Override
    public List<Header> getHeaders() {
        return null;
    }

    @Override
    public Type getResultType() {
        return LoginTelInfo.class;
    }



}
