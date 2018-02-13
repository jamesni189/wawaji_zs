package com.ydd.pockettoycatcherrwz.entity;

/**
 * 抓取记录详情
 */
public class CatchRecordDetail {

	/**
	 * 申诉 不等于空就说明有申诉 object
	 */
	public Appeal appeal;
	/**
	 * 抓取时间
	 */
	public String createTime;
	/**
	 * 抓取记录ID
	 */
	public long id;
	/**
	 * 商品名称
	 */
	public String productName;
	/**
	 * 0 成功 1 失败
	 */
	public int status;
	/**
	 * 视频地址
	 */
	public String url;
}
