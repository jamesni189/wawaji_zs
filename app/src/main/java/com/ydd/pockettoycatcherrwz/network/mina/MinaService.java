package com.ydd.pockettoycatcherrwz.network.mina;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;

/**
 * 长连接入口
 * 
 * Created by czhang on 17/1/18.
 */

public class MinaService extends Service {

	@Nullable
	@Override
	public IBinder onBind(Intent intent) {
		return null;
	}

	@Override
	public int onStartCommand(Intent intent, int flags, int startId) {
		super.onStartCommand(intent, flags, startId);
		MinaManager.getInstance().connect();
		return START_REDELIVER_INTENT;
	}

}
