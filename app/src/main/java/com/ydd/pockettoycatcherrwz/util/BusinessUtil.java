package com.ydd.pockettoycatcherrwz.util;

/**
 * 
 * 业务工具类
 */
public class BusinessUtil {

	/**
	 * 获取订单状态
	 * 
	 * @param status
	 * @return
	 */
	public static String getOrderStatus(int status) {
		switch (status) {
		case 0:
			return "已发货";
		case 1:
			return "待发货";
		default:
			return "";
		}

	}

	/**
	 * 获取订单状态
	 *
	 * @param expressId
	 * @return
	 */
	public static String getExpressDesc(int expressId) {
		switch (expressId) {
		case 1:
			return "顺丰快递";
		case 2:
			return "申通快递";
		case 3:
			return "韵达快递";
		case 4:
			return "中通快递";
		case 5:
			return "天天快递";
		case 6:
			return "ems";
		default:
			return "";
		}
	}

	/**
	 * 获取申诉状态描述
	 * 
	 * @param status
	 * @return
	 */
	public static String getAppealDesc(int status) {
		switch (status) {
		case 0:
			return "申诉中";
		case 1:
			return "申诉成功";
		case 2:
			return "申诉失败";
		default:
			return "";
		}
	}

	/**
	 * 获取抓取状态
	 * 
	 * @param status
	 * @return
	 */
	public static String getGrabStatusDesc(int status) {
		switch (status) {
		case 0:
			return "抓取成功";
		case 1:
			return "抓取失败";
		default:
			return "";
		}
	}

	/**
	 * 获取抓取状态
	 *
	 * @param status
	 * @return
	 */
	public static String getRoomStatus(int status) {
		switch (status) {
			case 0:
				return "空闲中";
			case 1:
				return "游戏中";
			case 2:
				//关闭
				return "维护中";
			default:
				return "维护中";
		}
	}

	/**
	 * 获取抓取状态
	 *
	 * @return
	 */
	public static String getChatTypeDesc(String name, String type) {
		if (name == null){
			name = "";
		}
		int typeInt = Integer.valueOf(type);
		switch (typeInt) {
			case Constants.HX_TYPE_JOIN:
				return name + " 加入了房间";
			case Constants.HX_TYPE_QUIT:
				return name + " 离开了房间";
			case Constants.HX_TYPE_SUCCESS:
				//关闭
				return name + " 逆天了，抓中！";
			case Constants.HX_TYPE_NEARLY_SUCCESS:
				//关闭
				return name + " 差点就抓中了！";
			default:
				return "";
		}
	}

}
