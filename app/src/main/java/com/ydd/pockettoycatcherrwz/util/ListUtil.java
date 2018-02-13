package com.ydd.pockettoycatcherrwz.util;

import java.util.List;

/**
 * 列表工具类
 * 
 */
public class ListUtil {

	/**
	 * 判断列表是否为空
	 * 
	 * @param list
	 *            列表数据
	 * @return
	 */
	public static boolean isEmpty(List list) {
		if (list == null || list.size() == 0) {
			return true;
		}
		return false;
	}
}
