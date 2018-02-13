package com.ydd.pockettoycatcherrwz.ui.web;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentTransaction;
import android.text.TextUtils;

import com.ydd.pockettoycatcherrwz.R;


/**
 * webview界面
 *
 * Created by czhang on 16/7/8.
 */
public class WebActivity extends FragmentActivity {

	public static final String INTENT_EXTRA_BEGIN_URL = "beginUrl";

	private WebFragment mWebFragment;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		Intent intent = getIntent();
		if (intent == null) {
			finish();
			return;
		}
		String beginUrl = intent.getStringExtra(INTENT_EXTRA_BEGIN_URL);
		if (TextUtils.isEmpty(beginUrl)) {
			finish();
			return;
		}
		setContentView(R.layout.rp_activity_web);
		mWebFragment = new WebFragment();
		Bundle b = new Bundle();
		b.putString(WebFragment.ARGUMENT_BEGIN_URL, beginUrl);
		mWebFragment.setArguments(b);
		FragmentTransaction transaction = getSupportFragmentManager()
				.beginTransaction();
		transaction.replace(R.id.rp_fl_web_container, mWebFragment).commit();
	}

	@Override
	public void onBackPressed() {
		if (mWebFragment != null && !mWebFragment.onBackPressed()) {
			super.onBackPressed();
		}
	}
}
