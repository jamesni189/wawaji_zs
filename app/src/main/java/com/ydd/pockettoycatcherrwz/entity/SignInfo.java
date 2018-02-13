package com.ydd.pockettoycatcherrwz.entity;

/**
 * 签名信息
 */
public class SignInfo {

	/**
	 * 积分
	 */
	public int points;
	/**
	 * 是否签到 0签到 1未签到
	 */
	public int status;
	/**
	 * 描述
	 */
	public String desc;

	@Override
	public String toString() {
		return "SignInfo{" +
				"points=" + points +
				", status=" + status +
				", desc='" + desc + '\'' +
				'}';
	}
}
