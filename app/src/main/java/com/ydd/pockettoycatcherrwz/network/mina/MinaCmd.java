package com.ydd.pockettoycatcherrwz.network.mina;

/**
 * 命令
 */
public class MinaCmd {
	/**
	 * 心跳包resp
	 */
	public static final String HB_R = "hb_r";
	/**
	 * 连接服务器resp
	 */
	public static final String CONN_R = "conn_r";
	/**
	 * 状态resp
	 */
	public static final String STATUS = "status";
	/**
	 * 开始游戏resp
	 */
	public static final String START_R = "start_r";
	/**
	 * 抓取resp
	 */
	public static final String GRAB_R = "grab_r";

	/**
	 * 进入房间
	 */
	public static final String INTO_ROOM = "into_room";

	/**
	 * 退出房间
	 */
	public static final String LEAVE_ROOM = "leave_room";

	/**
	 * 系统通知
	 */
	public static final String SYSTEM = "system";

	/**
	 * 其他人的抓取通知
	 */
	public static final String OTHER_GRAB = "other_grab";


	/*
	* 设备维护通知
	* */
	public static final String MAINTAIN = "maintain";



	/*
	* 文字聊天通知
	*
	* */
	public static final String TEXT_MESSAGE = "text_message";




}
