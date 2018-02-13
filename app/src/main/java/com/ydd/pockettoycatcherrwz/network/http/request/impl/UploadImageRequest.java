package com.ydd.pockettoycatcherrwz.network.http.request.impl;

import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.apache.http.Header;

import java.io.File;
import java.lang.reflect.Type;
import java.util.List;

/**
 * 上传图片
 */
public class UploadImageRequest extends AbstractRequest {
	/**
	 * 图片
	 */
	private File image;

	public UploadImageRequest(File image) {
		this.image = image;
	}

	@Override
	public String getUrl() {
		return Constants.BASE_URL + "/upload/image";
	}

	@Override
	public void initRequestParams() {
		addParam("accessToken", RunningContext.accessToken);
		addFileParam("image", image);
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
