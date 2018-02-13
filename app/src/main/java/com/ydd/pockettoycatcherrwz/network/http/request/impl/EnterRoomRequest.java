package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.ydd.pockettoycatcherrwz.entity.EnterRoomInfo;
import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.lang.reflect.Type;
import java.util.List;

/**
 * 进入房间
 */
public class EnterRoomRequest extends AbstractRequest {

	public String accessToken;
	/**
	 * 机器ID
	 */
	public String machineSn;

	public EnterRoomRequest(String accessToken, String machineSn) {
		this.accessToken = accessToken;
		this.machineSn = machineSn;
	}

	@Override
	public String getUrl() {
		return Constants.BASE_URL + "/enter/room";
	}

	@Override
	public void initRequestParams() {
		addParam("accessToken", accessToken);
		addParam("machineSn", machineSn);
	}

	@Override
	public List<Header> getHeaders() {
		return null;
	}

	@Override
	public Type getResultType() {
		return EnterRoomInfo.class;
	}

}
