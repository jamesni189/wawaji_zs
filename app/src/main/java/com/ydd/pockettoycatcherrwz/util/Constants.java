package com.ydd.pockettoycatcherrwz.util;

import android.os.Environment;

/**
 * 
 * 常量
 */
public class Constants {
	/**
	 * 请求基础地址
	 */
/*	public static final String
			BASE_URL = "https://m.baifengtech.com/dm-api/";*/
	/*
	* 任我抓测试地址
	* */
/*	public static final String
			BASE_URL ="http://121.41.101.63:8088/dm-api/";*/

	public static final String
			BASE_URL =	"https://api.zwwgame.com/dm-api/";

/*
	public static final String
			BASE_URL ="http://api.toy.xm.ydd100.cn/";*/

	/*
	* h5
	* */
	/*public static final String
			BASE_H5_URL ="http://test.h5.toy.ydd100.cn/";*/
	/*public static final String
			BASE_H5_URL ="https://h5.toy.ydd100.cn/?key=8dd758066c594324962cc2de7ee7a306/#/";*/
	 //测试
	public static final String
			BASE_H5_URL ="https://h5.zwwgame.com/?key=8dd758066c594324962cc2de7ee7a306/#/";
	//正式
	/**
	 * sp配置文件名
	 */
	public static final String SP_FILE_CONFIG = "config";
	/**
	 * 背景音乐key
	 */
	public static final String SP_MUSIC_ON_1 = "music_on_1";
	/**
	 * 背景音效key
	 */
	public static final String SP_MUSIC_ON_2 = "music_on_2";

	/**
	 * 表示id不传
	 */
	public static final int NONE_ID = -0x1001;

	public static final String SDCARD_PATH = Environment
			.getExternalStorageDirectory().getAbsolutePath();
	public static final String APP_PATH = SDCARD_PATH + "/PocketToyCatcher";
	public static final String VIDEO_PATH = APP_PATH + "/videos";

	public static final int HX_TYPE_JOIN = 0;
	public static final int HX_TYPE_QUIT = 1;
	public static final int HX_TYPE_SUCCESS = 2;
	public static final int HX_TYPE_NEARLY_SUCCESS = 3;

	public static final String CACHE_PATH=Environment.getExternalStorageDirectory().getPath() + "/"
			+ "Android" + "/" + "data" + "/" + "com.ydd.pockettoycatcherrwz" + "/"
			+ "cache";

	public static final String LOG_PATH=APP_PATH+"/logs";

	public static byte[] DOLL_BYTE;

	static {
		DOLL_BYTE = new byte[4];
		DOLL_BYTE[0] = 0x64;
		DOLL_BYTE[1] = 0x6F;
		DOLL_BYTE[2] = 0x6C;
		DOLL_BYTE[3] = 0x6C;
	}

	public static final String QQ_APPID = "1106515098";

	//public static final String WX_APPID = "wx254f96d077fd2c84";
	public static final String WX_APPID = "wx0089830373ef2267";

	//public static final String WX_SECRET = "285f1f2187465adc82af0faa6c0abab8";
	public static final String WX_SECRET = "7cc8eea714eff066e2a3e923def55aea";

	public static final String WXBASE_URL = "https://api.weixin.qq.com";

}
