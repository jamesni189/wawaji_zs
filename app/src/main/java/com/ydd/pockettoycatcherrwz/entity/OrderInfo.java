package com.ydd.pockettoycatcherrwz.entity;

import java.util.List;

/**
 * 订单记录
 */
public class OrderInfo {

	/**
	 * 下单时间
	 */
	public String createTime;
	/**
	 * 订单号
	 */
	public String orderSn;
	/**
	 * 娃娃列表
	 */
	public List<OrderToy> productList;
	/**
	 * 0已发货 1未发货
	 */
	public int status;
}
