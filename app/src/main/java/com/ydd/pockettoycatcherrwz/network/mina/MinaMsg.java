package com.ydd.pockettoycatcherrwz.network.mina;

/**
 * mina消息
 * 
 * Created by czhang on 17/2/6.
 */

public class MinaMsg {
	/**
	 * 消息id
	 */
	public String msgid;
	/**
	 * 消息类型
	 */
	public String funcid;
	/**
	 * 状态码
	 */
	public int code;
	/**
	 * 发送者
	 */
	public String from;
	/**
	 * 接收者
	 */
	public String to;
	/**
	 * 消息内容
	 */
	public String msgContent;

	public MinaPostMsg genPostMsg() {
		MinaPostMsg msg = new MinaPostMsg();
		msg.msgid = msgid;
		msg.funcid = funcid;
		msg.code = code;
		msg.from = from;
		msg.to = to;
		msg.msgContent = msgContent;
		return msg;
	}
}
