package com.ydd.pockettoycatcherrwz.entity;

import java.io.Serializable;

/**
 * 收货地址
 */
public class AddressInfo implements Serializable {
	/**
	 * 具体地址 苏州XXX路XXX号
	 */
	public String address;
	/**
	 * 收货人
	 */
	public String consignee;
	/**
	 * 地址ID
	 */
	public int id;
	/**
	 * 1:默认地址 0:非默认地址
	 */
	public int isDefault;
	/**
	 * 手机号
	 */
	public String mobile;
	/**
	 * 非接口返回字段，是否被选中，若不是，则为删除
	 */
	public boolean isChoosed;
}
