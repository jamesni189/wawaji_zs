package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by jia on 17/11/10.
 */

public class ChargeRequest extends AbstractRequest {


    private String accesstoken;

    private String id;

    private String type;
    private String kinds;

    public ChargeRequest(String accesstoken, String id, String type, String kinds) {
        this.accesstoken = accesstoken;
        this.id = id;
        this.type = type;
        this.kinds=kinds;
    }


    @Override
    public String getUrl() {
        if(kinds.equals("week") || kinds.equals("mouth")){
            return Constants.BASE_URL + "charge/card/v15";
        }
        return Constants.BASE_URL + "charge";
    }

    @Override
    public void initRequestParams() {
       if(kinds.equals("week") || kinds.equals("mouth")){
           if(kinds.equals("week")){
               addParam("buyType", "1");
           }
           if(kinds.equals("mouth")){
               addParam("buyType", "2");
           }
       }
        addParam("id", id);
        addParam("type", type);
        addParam("accessToken", accesstoken);
    }

    @Override
    public List<Header> getHeaders() {
        return null;
    }

    @Override
    public Type getResultType() {
        return String.class;
    }

//    @Override
//    public HttpRequest.HttpMethod getRequestMethod() {
//        return HttpRequest.HttpMethod.GET;
//    }
}
