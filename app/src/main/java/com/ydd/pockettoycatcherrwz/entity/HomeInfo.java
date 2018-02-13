package com.ydd.pockettoycatcherrwz.entity;

import java.util.List;

/**
 * 首页接口返回数据
 */

public class HomeInfo {

	/**
	 * 总条数
	 */
	public int total;
	/**
	 * 数据
	 */
	public List<RoomItem> data;
    /**
     * 页数
     */
	public int page;
    /**
     * 该页数量
     */
	public int pageSize;
    /**
     * 总页数
     */
	public int totalPages;

	@Override
	public String toString() {
		return "HomeInfo{" +
				"total=" + total +
				", data=" + data +
				", page=" + page +
				", pageSize=" + pageSize +
				", totalPages=" + totalPages +
				'}';
	}
}
