package com.ydd.pockettoycatcherrwz.network.http;

/**
 * 接口错误码定义
 *
 */
public interface ResultCode {
	/**
	 * 成功
	 */
	String CODE_SUCCESS = "0";
	/**
	 * 业务错误
	 */
	String CODE_ERROR_BUSINESS = "1";
	/**
	 * json格式错误
	 */
	String CODE_ERROR_FORMAT = "2";
	/**
	 * 网络不可用
	 */
	String CODE_ERROR_NET_INVALID = "-2";

	/**
	 * 默认的错误码
	 */
	String CODE_ERROR_DEFAULT = "-3";
	/**
	 * token失效
	 */
	String CODE_TOKEN_INVALID = "20002";

}
