package com.ydd.pockettoycatcherrwz.entity;

/**
 * 抓取记录详情信息
 */
public class GrabDetail {

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
	public String productName;
	/**
	 * 0 成功 1 失败 number
	 */
	public int status;
	/**
	 * 视频地址 string
	 */
	public String url;
	/**
	 * 申诉 不等于空就说明有申诉
	 */
	public Appeal appeal;
}
