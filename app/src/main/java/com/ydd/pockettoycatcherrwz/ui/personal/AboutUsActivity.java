package com.ydd.pockettoycatcherrwz.ui.personal;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.ui.BaseTitleActivity;
import com.ydd.pockettoycatcherrwz.util.CommonUtil;

/**
 * 关于我们
 */
public class AboutUsActivity extends BaseTitleActivity {

	@Override
	protected void onCreate(@Nullable Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContent(R.layout.activity_about_us);
		setTitle("关于我们");
		initView();
	}

	private void initView() {
		TextView versionTv = (TextView) findViewById(R.id.tv_about_us_version);
		String version = CommonUtil.getAPPVersionName(this);
		if (TextUtils.isEmpty(version)) {
			versionTv.setVisibility(View.GONE);
			return;
		}
		if (!version.startsWith("v") && !version.startsWith("V")) {
			version = "V" + version;
		}
		versionTv.setText(version);
	}
}
