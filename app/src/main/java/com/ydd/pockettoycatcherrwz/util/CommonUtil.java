package com.ydd.pockettoycatcherrwz.util;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.graphics.Typeface;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.InputMethodManager;
import android.widget.ListAdapter;
import android.widget.ListView;
import android.widget.TextView;

import java.util.regex.Pattern;

/**
 * 通用方法
 */
public class CommonUtil {

	/**
	 * 检测手机号是否符合标准
	 *
	 * @return
	 */
	public static boolean checkPhone(String phone) {
		if (TextUtils.isEmpty(phone)) {
			return false;
		}
		if (!phone.startsWith("1") || !isMobile(phone)) {
			return false;
		}
		return true;
	}

	/**
	 * 验证手机号码
	 *
	 * @param mobile
	 * @return
	 */
	public static boolean isMobile(String mobile) {

		if (TextUtils.isEmpty(mobile)) {
			return false;
		}
		Pattern phonner = Pattern.compile("[0-9]{11}");

		return phonner.matcher(mobile).matches();
	}

	/**
	 * 字体加粗
	 *
	 * @param textView
	 */
	public static void setTextBold(TextView textView) {
		textView.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
	}

	/**
	 * 设定listview高度
	 * 
	 * @param listView
	 */
	public void setListViewHeightBasedOnChildren(ListView listView) {
		ListAdapter listAdapter = listView.getAdapter();
		if (listAdapter == null) {
			// pre-condition
			return;
		}

		int totalHeight = 0;
		for (int i = 0; i < listAdapter.getCount(); i++) {
			View listItem = listAdapter.getView(i, null, listView);
			listItem.measure(0, 0);
			totalHeight += listItem.getMeasuredHeight();
		}

		ViewGroup.LayoutParams params = listView.getLayoutParams();
		params.height = totalHeight
				+ (listView.getDividerHeight() * (listAdapter.getCount() - 1));
		listView.setLayoutParams(params);
	}

	/**
	 * 获取apk的版本号 currentVersionCode
	 *
	 * @param ctx
	 * @return
	 */
	public static int getAPPVersionCode(Context ctx) {
		int currentVersionCode = 0;
		PackageManager manager = ctx.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
			currentVersionCode = info.versionCode; // 版本号
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return currentVersionCode;
	}

	/**
	 * 获取apk的版本号 currentVersionCode
	 *
	 * @param ctx
	 * @return
	 */
	public static String getAPPVersionName(Context ctx) {
		String appVersionName = null;
		PackageManager manager = ctx.getPackageManager();
		try {
			PackageInfo info = manager.getPackageInfo(ctx.getPackageName(), 0);
			appVersionName = info.versionName; // 版本名
		} catch (PackageManager.NameNotFoundException e) {
			e.printStackTrace();
		}
		return appVersionName;
	}

	/**
	 * 隐藏键盘
	 * 
	 * @param activity
	 */
	public static void hideKeyBoard(Activity activity) {
		try {
			InputMethodManager imm = (InputMethodManager) activity
					.getSystemService(Context.INPUT_METHOD_SERVICE);
			imm.hideSoftInputFromWindow(
					activity.getCurrentFocus().getWindowToken(),
					InputMethodManager.HIDE_NOT_ALWAYS);
		} catch (Exception e) {
		}
	}

	public static void dismissDialog(Dialog dialog) {
		if (dialog != null && dialog.isShowing()) {
			dialog.dismiss();
		}
	}

	/**
	 * 两个数组想加
	 */
	public static byte[] addArray(byte[] a, byte[] b) {
		if (a == null) {
			return b;
		}
		if (b == null) {
			return a;
		}
		byte[] sum = new byte[a.length + b.length];
		for (int i = 0; i < sum.length; i++) {
			if (i < a.length) {
				sum[i] = a[i];
				continue;
			}
			sum[i] = b[i - a.length];
		}
		return sum;
	}

	/**
	 * 分割字符串
	 * 
	 * @param data
	 * @param start
	 * @param end
	 * @return
	 */
	public static byte[] subArray(byte[] data, int start, int end) {
		if (end <= start || data == null) {
			return null;
		}
		byte[] subBytes = new byte[end - start];
		for (int i = 0; i < end; i++) {
			if (i < start) {
				continue;
			}
			subBytes[i - start] = data[i];
		}
		return subBytes;
	}

}
