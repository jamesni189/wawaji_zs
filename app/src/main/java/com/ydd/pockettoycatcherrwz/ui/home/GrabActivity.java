package com.ydd.pockettoycatcherrwz.ui.home;

import android.os.Bundle;
import android.support.annotation.Nullable;

import com.tencent.rtmp.TXLiveConstants;
import com.tencent.rtmp.TXLivePlayConfig;
import com.tencent.rtmp.TXLivePlayer;
import com.tencent.rtmp.ui.TXCloudVideoView;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.ui.BaseActivity;

/**
 * 抓取页面
 */
public class GrabActivity extends BaseActivity {

	/**
	 * mPlayerView即step1中添加的界面view
	 */
	private TXCloudVideoView mPlayerView;
	/**
	 * 创建player对象
	 */
	private TXLivePlayer mLivePlayer;

	private String mPlayUrl = "rtmp://live.hkstv.hk.lxdns.com/live/hks";

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_grab);
		initView();
		initPlayer();
	}

	private void initView() {
		mPlayerView = (TXCloudVideoView) findViewById(R.id.cloud_view);
	}

	private void initPlayer() {
		mLivePlayer = new TXLivePlayer(this);
		// 全屏，多余部分裁掉
		mLivePlayer.setRenderMode(TXLiveConstants.RENDER_MODE_FULL_FILL_SCREEN);
		// 画面旋转模式为正常播放(竖屏，home在下方)
		mLivePlayer.setRenderRotation(TXLiveConstants.RENDER_ROTATION_PORTRAIT);
		// 开启硬编码
		mLivePlayer.enableHardwareDecode(true);
		// 设置极速模式
		TXLivePlayConfig playConfig = new TXLivePlayConfig();
		playConfig.setAutoAdjustCacheTime(true);
		playConfig.setMinAutoAdjustCacheTime(1);
		playConfig.setMaxAutoAdjustCacheTime(1);
		mLivePlayer.setConfig(playConfig);
		mLivePlayer.setPlayerView(mPlayerView);
		//TODO 看具体的流协议
		mLivePlayer.startPlay(mPlayUrl, TXLivePlayer.PLAY_TYPE_LIVE_RTMP);
	}

	@Override
	protected void onDestroy() {
		super.onDestroy();
		mLivePlayer.stopPlay(true);// true代表清除最后一帧画面
		mPlayerView.onDestroy();
	}
}
