package com.ydd.pockettoycatcherrwz.network.http.request;

import android.text.TextUtils;

import com.lidroid.xutils.http.RequestParams;
import com.lidroid.xutils.http.client.HttpRequest.HttpMethod;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;

import org.apache.http.Header;
import org.apache.http.NameValuePair;
import org.apache.http.message.BasicNameValuePair;
import org.json.JSONObject;

import java.io.File;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

/**
 * 业务请求接口的基类
 *
 */
public abstract class AbstractRequest {
	/**
	 * 构造的请求参数
	 */
	private RequestParams params;

	/**
	 * 默认参数，例如全局的token、用户ID等信息
	 */
	private JSONObject defaultParams;

	/**
	 * 参数列表
	 */
	private Map<String, String> paramsMap;

	/**
	 * 参数列表
	 */
	private List<NameValuePair> nameValuePairList;

	/**
	 * 子类需要实现此方法，并返回接口URL地址
	 * 
	 * @return
	 */
	public abstract String getUrl();

	/**
	 * 子类需要实现此方法，并调用{@link #addParam(String, String)}或者
	 * 
	 * @return
	 */
	public abstract void initRequestParams();

	/**
	 * 子类需要实现此方法，来设置请求头
	 * 
	 * @return
	 */
	public abstract List<Header> getHeaders();

	/**
	 * 子类需要实现此方法，来指定响应数据应当被解析成哪种类型 ,如果是list类型，则按如下方式来写:<br>
	 * <p>
	 * return new TypeToken&lt;ArrayList&ltWares&gt;&gt;(){}.getType();
	 * </p>
	 * 
	 * @return
	 */
	public abstract Type getResultType();

	public void addParam(String key, String value) {
		// value无值则不传
		if (TextUtils.isEmpty(key) || TextUtils.isEmpty(value)) {
			return;
		}
		if (nameValuePairList == null) {
			nameValuePairList = new ArrayList<>();
		}
		NameValuePair nameValuePair = new BasicNameValuePair(key, value);
		nameValuePairList.add(nameValuePair);
	}

	/**
	 * 添加文件参数
	 */
	public void addFileParam(String key, File file) {
		// value无值则不传
		if (TextUtils.isEmpty(key) || file == null) {
			return;
		}
		if (params == null) {
			params = new RequestParams();
		}
		params.addBodyParameter(key, file);
	}

	public RequestParams getAddedParams() {
		if (params == null) {
			params = new RequestParams();
		}
		if (nameValuePairList != null) {
			if (getRequestMethod() == HttpMethod.POST) {
				params.addBodyParameter(nameValuePairList);
			} else if (getRequestMethod() == HttpMethod.GET) {
				params.addQueryStringParameter(nameValuePairList);
			}
		}
		return params;
	}

	/**
	 * 指定请求类型，默认是GET请求，其他请求类型需要子类覆盖此方法来指定
	 * 
	 * @return
	 */
	public HttpMethod getRequestMethod() {
		return HttpMethod.POST;
	}

	/**
	 * 子类是否需要自己解析响应数据，如果返回true，则需要子类覆盖
	 * {@link #parseResult(JSONObject, MyCallback)}自己处理响应数据
	 * 
	 * @return
	 */
	public boolean parseResultMyself() {
		return false;
	}

	/**
	 * 子类可覆盖此方法以自己解析响应数据，注意：当{@link #parseResultMyself()}返回false的时候，覆盖此方法将不起作用
	 * 
	 * @param response
	 * @param callback
	 * @return
	 */
	public <DataType> Object parseResult(JSONObject response,
			MyCallback<DataType> callback) {
		return null;
	}

}
