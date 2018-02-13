package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 申诉
 */
public class AppealRequest extends AbstractRequest {

    private String accessToken;
    /**
     * 理由
     */
    private String reason;
    /**
     * 抓取记录ID
     */
    private long id;

    private int type;

    public AppealRequest(String accessToken, String reason, long id, int type) {
        this.accessToken = accessToken;
        this.reason = reason;
        this.id = id;
        this.type=type;
    }
//  /doll/appeal
    @Override
    public String getUrl() {
        return Constants.BASE_URL + "doll/appeal/v15";
    }

    @Override
    public void initRequestParams() {
        addParam("accessToken", accessToken);
        addParam("reason", reason);
        addParam("id", String.valueOf(id));
        addParam("type", type+"");
    }

    @Override
    public List<Header> getHeaders() {
        return null;
    }

    @Override
    public Type getResultType() {
        return null;
    }
}
