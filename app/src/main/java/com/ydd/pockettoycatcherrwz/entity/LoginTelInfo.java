package com.ydd.pockettoycatcherrwz.entity;

/**
 * 手机号登录信息
 */
public class LoginTelInfo {
	/**
	 * 
	 */
	public AccessToken accessToken;
	/**
	 * 头像
	 */
	public String avatar;
	/**
	 * 环信ID
	 */
	public String hxId;
	/**
	 * 环信密码
	 */
	public String hxPwd;
	/**
	 * 邀请码
	 */
	public String inviteCode;
	/**
	 * 手机号
	 */
	public String mobile;
	/**
	 * 游戏币 number
	 */
	public long money;
	/**
	 * 昵称
	 */
	public String nickname;
	/**
	 * 积分
	 */
	public String points;

	@Override
	public String toString() {
		return "LoginTelInfo{" +
				"accessToken=" + accessToken +
				", avatar='" + avatar + '\'' +
				", hxId='" + hxId + '\'' +
				", hxPwd='" + hxPwd + '\'' +
				", inviteCode='" + inviteCode + '\'' +
				", mobile='" + mobile + '\'' +
				", money=" + money +
				", nickname='" + nickname + '\'' +
				", points='" + points + '\'' +
				'}';
	}
}
