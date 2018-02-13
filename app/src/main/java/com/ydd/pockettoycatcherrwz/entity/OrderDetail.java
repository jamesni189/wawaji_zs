package com.ydd.pockettoycatcherrwz.entity;

import java.util.List;

/**
 * 订单详情
 */
public class OrderDetail {

	/**
	 * 具体地址
	 */
	public String address;
	/**
	 * 收货人
	 */
	public String consignee;
	/**
	 * 下单时间
	 */
	public String createTime;
	/**
	 * 快递号
	 */
	public String expressNo;
	/**
	 * 发货时间
	 */
	public String expressTime;
	/**
	 * 手机号
	 */
	public String mobile;
	/**
	 * 订单号
	 */
	public String orderSn;
	/**
	 * 娃娃列表 array<object>
	 */
	public List<OrderToy> productList;
	/**
	 * 0已发货 1未发货
	 */
	public int status;
	/**
	 * 快递类型 1顺丰 2申通 3韵达 4中通 5天天 6ems
	 */
	public int type;
}
