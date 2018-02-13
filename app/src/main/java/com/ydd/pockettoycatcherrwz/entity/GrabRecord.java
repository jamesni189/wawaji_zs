package com.ydd.pockettoycatcherrwz.entity;

import java.io.Serializable;

/**
 * 抓取记录
 */
public class GrabRecord implements Serializable {

	/**
	 * 抓取时间 string
	 */
	public String createTime;
	/**
	 * 抓取记录ID
	 */
	public long id;
	/**
	 * 商品名称 string
	 */
	public String name;
	/**
	 * 0 成功 1 失败 number
	 */
	public int status;
	/**
	 * 视频地址 string
	 */
	public String url;
	/**
	 * 图像
	 */
	public String img;
	/**
	 * 申诉状态 0申诉中 1 申诉成功 2申诉失败
	 */
	public int appealStatus = -1;

	@Override
	public String toString() {
		return "GrabRecord{" +
				"createTime='" + createTime + '\'' +
				", id=" + id +
				", name='" + name + '\'' +
				", status=" + status +
				", url='" + url + '\'' +
				", img='" + img + '\'' +
				", appealStatus=" + appealStatus +
				'}';
	}
}
