package com.ydd.pockettoycatcherrwz;

import android.text.TextUtils;

import com.google.gson.Gson;
import com.ydd.pockettoycatcherrwz.entity.UserInfo;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.GetUserInfoRequest;
import com.ydd.pockettoycatcherrwz.util.Constants;
import com.ydd.pockettoycatcherrwz.util.SharedPrefConfig;

import org.greenrobot.eventbus.EventBus;

/**
 * 用户信息管理
 * 
 * Created by czhang on 17/11/6.
 */

public class UserManager {

	private static UserManager instance;

	private UserManager() {
	}

	/**
	 * 单例模式，获取实例
	 *
	 * @return
	 */
	public static UserManager getInstance() {
		if (instance == null) {
			synchronized (UserManager.class) {
				if (instance == null) {
					instance = new UserManager();
				}
			}
		}
		return instance;
	}

	private boolean needRefresh;

	private boolean isRefreshing;

	public void checkRefresh() {
		if (needRefresh) {
			refresh();
		}
	}

	public void refresh() {
		needRefresh = true;
		if (isRefreshing) {
			return;
		}
		isRefreshing = true;
		BusinessManager.getInstance().getUserInfo(
				new GetUserInfoRequest(RunningContext.accessToken),
				new MyCallback<UserInfo>() {
					@Override
					public void onSuccess(UserInfo result, String message) {
						if (result == null) {
							return;
						}
						needRefresh = false;
						if (RunningContext.loginTelInfo != null) {
							RunningContext.loginTelInfo.avatar = result.avatar;
							RunningContext.loginTelInfo.mobile = result.mobile;
							RunningContext.loginTelInfo.money = result.money;
							RunningContext.loginTelInfo.points = result.points;
							RunningContext.loginTelInfo.nickname = result.nickname;

							// 放入sp
							SharedPrefConfig sharedPrefConfig = new SharedPrefConfig();
							sharedPrefConfig.open(PTCApplication.instance,
									Constants.SP_FILE_CONFIG);
							String phone = sharedPrefConfig
									.getString("last_login", "");
							if (!TextUtils.isEmpty(phone)) {
								sharedPrefConfig.open(PTCApplication.instance,
										phone + "_" + Constants.SP_FILE_CONFIG);
							}
							sharedPrefConfig.putString("login_info", new Gson()
									.toJson(RunningContext.loginTelInfo));

							EventBus.getDefault().post(result);
						}
					}

					@Override
					public void onError(String errorCode, String message) {

					}

					@Override
					public void onFinished() {
						isRefreshing = false;
					}
				});
	}

}
