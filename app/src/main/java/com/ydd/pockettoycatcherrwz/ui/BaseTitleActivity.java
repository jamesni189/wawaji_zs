package com.ydd.pockettoycatcherrwz.ui;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.widget.CommonTitleBar;

/**
 * Created by mac on 17/10/23.
 */

public class BaseTitleActivity extends BaseActivity {

	protected CommonTitleBar mTitleBar;

	protected void setContent(int id) {
		setContentView(id);
		mTitleBar = (CommonTitleBar) findViewById(R.id.common_title_bar);
	}

	/**
	 * 设置标题
	 * 
	 * @param title
	 */
	protected void setTitle(String title) {
		if (mTitleBar == null) {
			return;
		}
		mTitleBar.setTitle(title);
	}
}
