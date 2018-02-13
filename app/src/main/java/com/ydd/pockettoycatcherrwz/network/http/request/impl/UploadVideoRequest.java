package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 上传video
 */
public class UploadVideoRequest extends AbstractRequest {
	private String accessToken;
	/**
	 * 抓取记录ID
	 */
	private String logId;
	/**
	 * 视频
	 */
	private File video;

	public UploadVideoRequest(String accessToken, String logId, File video) {
		this.accessToken = accessToken;
		this.logId = logId;
		this.video = video;
	}

	@Override
	public String getUrl() {
		return Constants.BASE_URL + "/upload/video";
	}

	@Override
	public void initRequestParams() {
		addParam("accessToken", accessToken);
		addParam("logId", logId);
		addFileParam("video", video);
	}

	@Override
	public List<Header> getHeaders() {
		return null;
	}

	@Override
	public Type getResultType() {
		return String.class;
	}
}
