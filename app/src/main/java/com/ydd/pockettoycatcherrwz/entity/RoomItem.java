package com.ydd.pockettoycatcherrwz.entity;

import java.io.Serializable;
import java.util.List;

/**
 * 首页房间信息
 */
public class RoomItem implements Serializable {

	/**
	 * 娃娃图片
	 */
	public List<String> imgs;
	/**
	 * 机器ID
	 */
	public String machineId;
	/**
	 * 机器编号
	 */
	public String machineSn;
	/**
	 * 娃娃名称
	 */
	public String name;
	/**
	 * 价格
	 */
	public int price;
	/**
	 * 0空闲 1游戏中 2关闭
	 */
	public int status;
	/**
	 * 人数
	 */
	public int num;
	/**
	 * 赢的游戏后的图片
	 */
	public String winImg;

	public String liveRoom1;
	public String channelKey1;
	public String liveRoom2;
	public String channelKey2;

	/**
	 * 直播房间号
	 */
	public String liveRoomCode;
	public String chatRoom;

	@Override
	public String toString() {
		return "RoomItem{" +
				"imgs=" + imgs +
				", machineId='" + machineId + '\'' +
				", machineSn='" + machineSn + '\'' +
				", name='" + name + '\'' +
				", price=" + price +
				", status=" + status +
				", num=" + num +
				", winImg='" + winImg + '\'' +
				", liveRoom1='" + liveRoom1 + '\'' +
				", channelKey1='" + channelKey1 + '\'' +
				", liveRoom2='" + liveRoom2 + '\'' +
				", channelKey2='" + channelKey2 + '\'' +
				'}';
	}
}
