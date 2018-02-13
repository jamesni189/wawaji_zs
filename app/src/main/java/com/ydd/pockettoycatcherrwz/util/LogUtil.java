package com.ydd.pockettoycatcherrwz.util;

import android.util.Log;

/**
 * 日志打印类，需要打印日志的时候请使用此类来处理，以便在不需要打印日志的时候统一关闭日志打印，或者统一设置日志打印级别
 * 
 * @author fantao
 * 
 */
public class LogUtil {
	/**
	 * 日志开关
	 */
	private static boolean logFlagIsOpen = true;

	private static int logLevel = 0;

	/**
	 * 打印log
	 * 
	 * @param tag
	 *            标记
	 * @param msg
	 *            消息
	 * @param logType
	 *            消息类型："i" , "e", "d", "v", "w"
	 */
	public static void log(String tag, String msg, String logType) {
		// 打印日志到文件
		int curLevel = 0;
		if ("v".equals(logType)) {
			curLevel = 0;
		} else if ("d".equals(logType)) {
			curLevel = 1;
		} else if ("i".equals(logType)) {
			curLevel = 2;
		} else if ("w".equals(logType)) {
			curLevel = 3;
		} else if ("e".equals(logType)) {
			curLevel = 4;
		}
		if (logFlagIsOpen && curLevel >= logLevel) {
			if ("i".equals(logType)) {
				Log.i(tag == null ? "" : tag, msg == null ? "" : msg);
			} else if ("e".equals(logType)) {
				Log.e(tag == null ? "" : tag, msg == null ? "" : msg);
			} else if ("d".equals(logType)) {
				Log.d(tag == null ? "" : tag, msg == null ? "" : msg);
			} else if ("v".equals(logType)) {
				Log.v(tag == null ? "" : tag, msg == null ? "" : msg);
			} else if ("w".equals(logType)) {
				Log.w(tag == null ? "" : tag, msg == null ? "" : msg);
			}
		}
	}

	public static void setLogFlag(boolean isOpen) {
		logFlagIsOpen = isOpen;
	}

	/**
	 * 设置日志打印的级别
	 * 
	 * @param level
	 *            0:v, 1:d, 2:i, 3:w, 4:e,
	 */
	public static void setLogLevel(int level) {
		if (level >= 0 && level <= 4) {
			logLevel = level;
		}
	}

	/**
	 * 调试log<br>
	 * add by czhang
	 * 
	 * @param msg
	 */
	public static final void printCZ(String msg) {
		log("czhang", msg + "", "i");
	}

	/**
	 * 打印mina信息
	 * 
	 * @param msg
	 *            待打印信息
	 */
	public static final void printMina(String msg) {
		log("mina", msg + "", "i");
	}


	public static final void printJ(String msg){log("jia",msg+"","e");}
}
