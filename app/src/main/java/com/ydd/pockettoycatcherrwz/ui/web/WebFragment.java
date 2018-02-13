package com.ydd.pockettoycatcherrwz.ui.web;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.DownloadListener;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.ProgressBar;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.widget.CommonTitleBar;

/**
 * web加载页
 * <p/>
 * Created by czhang on 16/7/6.
 */
public class WebFragment extends Fragment {

	/**
	 * 起始的url
	 */
	public static final String ARGUMENT_BEGIN_URL = "beginUrl";
	/**
	 * 用于展示h5
	 */
	private WebView mWebView;

	/**
	 * webview加载进度条
	 */
	private ProgressBar mLoadingProgress;

	/**
	 * 起始的url
	 */
	private String mBeginUrl;

	/**
	 * 标题
	 */
	private CommonTitleBar mTitleBar;

	@Override
	public View onCreateView(LayoutInflater inflater, ViewGroup container,
			Bundle savedInstanceState) {
		Bundle b = getArguments();
		if (b != null) {
			mBeginUrl = b.getString(ARGUMENT_BEGIN_URL);
		}
		View rootView = inflater.inflate(R.layout.rp_fragment_web, container,
				false);
		initView(rootView);
		return rootView;
	}

	private void initView(View rootView) {
		mLoadingProgress = (ProgressBar) rootView
				.findViewById(R.id.rp_pb_web_progress);
		mWebView = (WebView) rootView.findViewById(R.id.rp_wv_web);
		mWebView.setDownloadListener(new MyDownloadStart());
		mTitleBar = (CommonTitleBar) rootView
				.findViewById(R.id.common_title_bar);

		initTitle();
		initWebView();
		// 加载对应的url
		if (!TextUtils.isEmpty(mBeginUrl)) {
			mWebView.loadUrl(mBeginUrl);
		}
	}

	/**
	 * 初始化标题
	 */
	private void initTitle() {
		mTitleBar.setTitle("");
		mTitleBar.mBackIv.setOnClickListener(mClickListener);
	}

	/**
	 * 初始化webview
	 */
	private void initWebView() {
		initWebViewSettings();
		mWebView.setWebChromeClient(new WebChromeClient() {
			@Override
			public void onProgressChanged(WebView view, int newProgress) {
				if (newProgress == 100) {
					mLoadingProgress.setVisibility(View.GONE);
				} else {
					if (mLoadingProgress.getVisibility() == View.GONE) {
						mLoadingProgress.setVisibility(View.VISIBLE);
					}
					mLoadingProgress.setProgress(newProgress);
				}
				super.onProgressChanged(view, newProgress);
			}
		});
		mWebView.setWebViewClient(new WebViewClient() {

			@Override
			public boolean shouldOverrideUrlLoading(WebView view, String url) {
				return false;
			}

			@Override
			public void onReceivedError(WebView view, int errorCode,
					String description, String failingUrl) {
				super.onReceivedError(view, errorCode, description, failingUrl);
			}

			@Override
			public void onPageFinished(WebView view, String url) {
				super.onPageFinished(view, url);
			}

		});
	}

	/**
	 * 初始化webview的一些设置
	 */
	private void initWebViewSettings() {
		WebSettings settings = mWebView.getSettings();
		// 支持js
		settings.setJavaScriptEnabled(true);
		settings.setDomStorageEnabled(true);
		// 将图片调整到适合webview的大小
		settings.setUseWideViewPort(true);
		// 缩放至屏幕的大小
		settings.setLoadWithOverviewMode(true);
		// 支持通过JS打开新窗口
		settings.setJavaScriptCanOpenWindowsAutomatically(true);

		// 不支持缩放
		settings.setBuiltInZoomControls(false);
		// setting.setCacheMode(WebSettings.LOAD_DEFAULT);
		// 允许在https页面中进行http请求
		if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
			settings.setMixedContentMode(
					WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
		}
	}

	/**
	 * 加载js方法
	 *
	 * @param functionName
	 *            回调名称
	 * @param params
	 *            参数
	 */
	private void loadJSFunction(final String functionName,
			final String params) {
		mWebView.post(new Runnable() {
			@Override
			public void run() {
				mWebView.loadUrl(
						"javascript:" + functionName + "('" + params + "')");
			}
		});
	}

	/**
	 * 回退按钮监听
	 * 
	 * @return
	 */
	protected boolean onBackPressed() {
		if (isCanGoBack()) {
			// 返回前一个页面
			mWebView.goBack();
			return true;
		}
		return false;
	}

	protected boolean isCanGoBack() {
		return mWebView.canGoBack();
	}

	/**
	 * 本页所有按钮点击的监听
	 */
	private View.OnClickListener mClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			if (v.getId() == R.id.iv_common_title_back) {
				doBackClick();
			}
		}
	};

	/**
	 * 点击回退按钮
	 */
	private void doBackClick() {
		if (mWebView.canGoBack()) {
			mWebView.goBack();
			return;
		}
		Activity activity = getActivity();
		if (activity != null) {
			activity.finish();
		}
	}
	class MyDownloadStart implements DownloadListener {

		@Override
		public void onDownloadStart(String url, String userAgent,
									String contentDisposition, String mimetype, long contentLength) {
			// TODO Auto-generated method stub
			//调用自己的下载方式
//          new HttpThread(url).start();
			//调用系统浏览器下载
			Uri uri = Uri.parse(url);
			Intent intent = new Intent(Intent.ACTION_VIEW, uri);
			startActivity(intent);
		}

	}
}
