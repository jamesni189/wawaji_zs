package com.ydd.pockettoycatcherrwz.network.mina.result;

/**
 * 开始
 */
public class StartResult {

	public String cmd;

	public String vmc_no;
	/**
	 * 用户
	 */
	public String member_id;
	/**
	 * 200代表成功
	 */
	public int status;
	/**
	 * 游戏记录id。开始成功返回,作为视频记录编号
	 */
	public String dollLogId;
	/**
	 * 失败描述
	 */
	public String msg;
	/**
	 * 剩余金额
	 */
	public String remainGold;

   public int point;

	@Override
	public String toString() {
		return "StartResult{" +
				"cmd='" + cmd + '\'' +
				", vmc_no='" + vmc_no + '\'' +
				", member_id='" + member_id + '\'' +
				", status=" + status +
				", dollLogId='" + dollLogId + '\'' +
				", msg='" + msg + '\'' +
				", remainGold='" + remainGold + '\'' +
				", point=" + point +
				'}';
	}
}
