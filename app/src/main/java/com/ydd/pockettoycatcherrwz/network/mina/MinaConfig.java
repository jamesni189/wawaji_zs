package com.ydd.pockettoycatcherrwz.network.mina;

/**
 * mina配置信息
 * 
 * Created by czhang on 17/1/18.
 */

public interface MinaConfig {
	/**
	 * 服务器地址 47.104.25.49
	 */
//	String HOST = "47.104.25.49";
	//String HOST = "121.41.101.63";
	String HOST = "api.zwwgame.com";

	/**
	 * 端口号
	 */
	int PORT = 56792;
	/**
	 * 连结超时时间
	 */
	int CONNECT_TIME_OUT = 10000;
}
