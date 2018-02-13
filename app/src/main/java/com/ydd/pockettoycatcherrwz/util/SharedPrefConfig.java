package com.ydd.pockettoycatcherrwz.util;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.text.TextUtils;

/**
 * 使用系统内建的SharedPreferences的config配置器
 */
public class SharedPrefConfig implements IConfig {
	private Editor editor;
	private SharedPreferences mSharedPreferences;

	@Override
	public boolean open(Context context, String configname) {
		if (TextUtils.isEmpty(configname)) {
			return false;
		}
		mSharedPreferences = context.getSharedPreferences(configname,
				Context.MODE_PRIVATE);
		editor = mSharedPreferences.edit();
		return true;
	}

	@Override
	public boolean close() {
		editor.commit();
		mSharedPreferences = null;
		editor = null;
		return true;
	}

	@Override
	public boolean remove(String key) {
		editor.remove(key);
		return editor.commit();
	}

	@Override
	public boolean clear() {
		editor.clear();
		return editor.commit();
	}

	@Override
	public boolean putString(String key, String value) {
		editor.putString(key, value);
		return editor.commit();
	}

	@Override
	public boolean putInt(String key, int value) {
		editor.putInt(key, value);
		return editor.commit();
	}

	@Override
	public boolean putBoolean(String key, Boolean value) {
		editor.putBoolean(key, value);
		return editor.commit();
	}

	@Override
	public String getString(String key, String defaultValue) {
		return mSharedPreferences.getString(key, defaultValue);
	}

	@Override
	public int getInt(String key, int defaultValue) {
		return mSharedPreferences.getInt(key, defaultValue);
	}

	@Override
	public boolean getBoolean(String key, Boolean defaultValue) {
		return mSharedPreferences.getBoolean(key, defaultValue);
	}
}
