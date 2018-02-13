package com.ydd.pockettoycatcherrwz.ui.record;

import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.widget.MediaController;
import android.widget.Toast;
import android.widget.VideoView;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.ui.BaseTitleActivity;

/**
 * 视频播放
 */
public class VideoActivity extends BaseTitleActivity {

	public static final String INTENT_EXTRA_URL = "extra_url";

	private VideoView mVideoView;

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		 String url = getIntent().getStringExtra(INTENT_EXTRA_URL);
//		String url = "https://yingdd.oss-cn-hangzhou.aliyuncs.com/6c5af17ac1dc5ce43981e293177f7081.mp4";
		if (TextUtils.isEmpty(url)) {
			finish();
			return;
		}
		setContent(R.layout.activity_video);
		setTitle("视频播放");
		initVideoView(url);
	}

	private void initVideoView(String url) {
		mVideoView = (VideoView) findViewById(R.id.video_view);
		Uri uri = Uri.parse(url);
		// 设置视频控制器
		mVideoView.setMediaController(new MediaController(this));

		// 播放完成回调
		mVideoView.setOnCompletionListener(new MyPlayerOnCompletionListener());

		// 设置视频路径
		mVideoView.setVideoURI(uri);

		// 开始播放视频
		mVideoView.start();
	}

	class MyPlayerOnCompletionListener
			implements MediaPlayer.OnCompletionListener {

		@Override
		public void onCompletion(MediaPlayer mp) {
			Toast.makeText(VideoActivity.this, "播放完成了", Toast.LENGTH_SHORT)
					.show();
		}
	}
}
