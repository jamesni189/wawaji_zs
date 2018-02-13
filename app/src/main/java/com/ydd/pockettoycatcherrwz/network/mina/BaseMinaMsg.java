package com.ydd.pockettoycatcherrwz.network.mina;

/**
 * 基础的消息
 */
public class BaseMinaMsg {
	/**
	 * 命令
	 */
	public String cmd;

	public String msg;

	@Override
	public String toString() {
		return "BaseMinaMsg{" +
				"cmd='" + cmd + '\'' +
				", msg='" + msg + '\'' +
				'}';
	}
}
