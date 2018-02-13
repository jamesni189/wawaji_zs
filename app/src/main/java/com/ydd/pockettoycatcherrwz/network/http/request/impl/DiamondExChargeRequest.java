package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.lidroid.xutils.http.client.HttpRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

/**
 * 兑换钻石
 */
public class DiamondExChargeRequest extends AbstractRequest {

    private String accessToken;
    private List<String> logIds;
    private ArrayList<Integer> nums;
    private ArrayList<Integer> 	productIds;


    public DiamondExChargeRequest(String accessToken,ArrayList<Integer> nums,ArrayList<Integer>	productIds) {
        this.accessToken = accessToken;
        this.nums = nums;
        this.productIds = productIds;
    }

    @Override
    public String getUrl() {
        return Constants.BASE_URL + "/doll/diamond/exchange/part";
    }

    @Override
    public void initRequestParams() {
        addParam("accessToken", accessToken);
        for(int i= 0;i<nums.size();i++ ){
            addParam("nums", nums.get(i)+"");
            addParam("productIds", productIds.get(i)+"");
        }



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
