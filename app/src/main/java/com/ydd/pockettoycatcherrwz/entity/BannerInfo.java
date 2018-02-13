package com.ydd.pockettoycatcherrwz.entity;

import java.util.List;

/**
 * 热点活动
 */
public class BannerInfo {

	public int id;

	public String createTime;
	/**
	 * 图片
	 */
	public String imgUrl;
	/**
	 * url地址
	 */
	public String url;
	public String machineSn;
	public List<String> imgs;
	public String winImg;
	/**
	 * 类型 1房间，2支付 3网页
	 */
	public int type;


	public int num;

	public int price;

	@Override
	public String toString() {
		return "BannerInfo{" +
				"id=" + id +
				", createTime='" + createTime + '\'' +
				", imgUrl='" + imgUrl + '\'' +
				", url='" + url + '\'' +
				", machineSn='" + machineSn + '\'' +
				", imgs=" + imgs +
				", winImg='" + winImg + '\'' +
				", type=" + type +
				", num=" + num +
				", price=" + price +
				'}';
	}
}
