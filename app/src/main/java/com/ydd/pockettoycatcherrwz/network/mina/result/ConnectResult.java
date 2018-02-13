package com.ydd.pockettoycatcherrwz.network.mina.result;

import com.ydd.pockettoycatcherrwz.entity.Players;

import java.util.List;

/**
 * 连接结果
 */
public class ConnectResult {
	public String cmd;
	/**
	 * 房间状态 1游戏中 0空闲
	 */
	public int room_status;
	/**
	 * 机器编号
	 */
	public String vmc_no;
	/**
	 * 200表示连接成功，其它代表失败
	 */
	public int status;
	/**
	 * 失败原因
	 */
	public String msg;
	/**
	 * 当前游戏人头像
	 */
	public String headUrl;
	/**
	 * 是否在游戏中 1为在游戏中 0为不在
	 */
	public int isGame;
	/**
	 * 剩余秒数
	 */
	public int remainSecond;

	public String dollLogId;

	public int point;

	public int gold;
	public int member_count;

	public String remainGold;

	private List<Players> players;

	public List<Players> getPlayers() {
		return players;
	}

	public void setPlayers(List<Players> players) {
		this.players = players;
	}

	@Override
	public String toString() {
		return "ConnectResult{" +
				"cmd='" + cmd + '\'' +
				", room_status=" + room_status +
				", vmc_no='" + vmc_no + '\'' +
				", status=" + status +
				", msg='" + msg + '\'' +
				", headUrl='" + headUrl + '\'' +
				", isGame=" + isGame +
				", remainSecond=" + remainSecond +
				", dollLogId='" + dollLogId + '\'' +
				", point=" + point +
				", remainGold='" + remainGold + '\'' +
				'}';
	}
}
