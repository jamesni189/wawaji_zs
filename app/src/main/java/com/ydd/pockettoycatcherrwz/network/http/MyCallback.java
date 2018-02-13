package com.ydd.pockettoycatcherrwz.network.http;


import com.ydd.pockettoycatcherrwz.network.http.request.AbstractRequest;

/**
 * 常用网络请求回调类
 *
 * @param <T>
 *            需要返回的数据类型，需要与对应的{@link AbstractRequest#getResultType()}子类返回类型一致
 */
public abstract class MyCallback<T> {
	/**
	 * 请求成功
	 * 
	 * @param result
	 *            返回的数据封装对象
	 * @param message
	 *            成功后的消息
	 */
	public abstract void onSuccess(T result, String message);

	/**
	 * 请求失败，可能是业务接口调用失败，也可能是网络层面的问题
	 * 
	 * @param errorCode
	 *            错误码
	 * @param message
	 *            错误消息
	 */
	public abstract void onError(String errorCode, String message);

	/**
	 * 请求完成，无论成功还是失败最终都会走此回调
	 */
	public abstract void onFinished();

	/**
	 * 请求开始
	 */
	public void onStart() {
	}

	/**
	 * 请求取消
	 */
	public void onCancel() {

	}

}
