package com.ydd.pockettoycatcherrwz.util.hx;

import android.app.ActivityManager;
import android.app.Application;
import android.content.pm.PackageManager;
import android.util.Log;

import com.hyphenate.EMCallBack;
import com.hyphenate.chat.EMClient;
import com.hyphenate.chat.EMOptions;
import com.ydd.pockettoycatcherrwz.util.LogUtil;

import java.util.Iterator;
import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;
import static com.nostra13.universalimageloader.core.ImageLoader.TAG;

/**
 * 环信基础方法抽取
 */
public class HXBaseUtil {

	/**
	 * 初始化环信
	 */
	public static void init(Application application) {
		int pid = android.os.Process.myPid();
		String processAppName = getAppName(application, pid);
		// 如果APP启用了远程的service，此application:onCreate会被调用2次
		// 为了防止环信SDK被初始化2次，加此判断会保证SDK被初始化1次
		// 默认的APP会在以包名为默认的process name下运行，如果查到的process name不是APP的process
		// name就立即返回

		if (processAppName == null || !processAppName
				.equalsIgnoreCase(application.getPackageName())) {
			Log.e(TAG, "enter the service process!");

			// 则此application::onCreate 是被service 调用的，直接返回
			return;
		}
		EMOptions options = new EMOptions();
		// 默认添加好友时，是不需要验证的，改成需要验证
		options.setAcceptInvitationAlways(false);
		// 初始化
		EMClient.getInstance().init(application, options);
		// 在做打包混淆时，关闭debug模式，避免消耗不必要的资源
		EMClient.getInstance().setDebugMode(true);
	}

	/**
	 * 登录
	 * 
	 * @param userName
	 * @param password
	 * @param callBack
	 */
	public static void login(String userName, String password,
			EMCallBack callBack) {
		EMClient.getInstance().login(userName, password, callBack);
	}

	/**
	 * 登录
	 *
	 * @param userName
	 * @param password
	 */
	public static void login(String userName, String password) {
		EMClient.getInstance().login(userName, password, new EMCallBack() {
			@Override
			public void onSuccess() {
				LogUtil.printCZ("hx login success");
			}

			@Override
			public void onError(int i, String s) {
				LogUtil.printCZ("hx login failed:" + s);
			}

			@Override
			public void onProgress(int i, String s) {

			}
		});
	}

	/**
	 * 登出
	 * 
	 * @param callBack
	 */
	public static void logout(final EMCallBack callBack) {

		EMClient.getInstance().logout(true, new EMCallBack() {
			@Override
			public void onSuccess() {
				callBack.onSuccess();
			}

			@Override
			public void onError(int i, String s) {
				// 失败的话，忽略网络状态，直接退出
				EMClient.getInstance().logout(false, callBack);
			}

			@Override
			public void onProgress(int i, String s) {
			}
		});
	}

	private static String getAppName(Application application, int pID) {
		String processName = null;
		ActivityManager am = (ActivityManager) application
				.getSystemService(ACTIVITY_SERVICE);
		List l = am.getRunningAppProcesses();
		Iterator i = l.iterator();
		PackageManager pm = application.getPackageManager();
		while (i.hasNext()) {
			ActivityManager.RunningAppProcessInfo info = (ActivityManager.RunningAppProcessInfo) (i
					.next());
			try {
				if (info.pid == pID) {
					processName = info.processName;
					return processName;
				}
			} catch (Exception e) {
			}
		}
		return processName;
	}
}
