package com.ydd.pockettoycatcherrwz.ui.home;

import android.app.Activity;
import android.graphics.Bitmap;
import android.support.v4.app.FragmentActivity;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.ui.BaseActivity;


/*
*隐私协议
 */
public class SecretWebActivity extends Activity {

    private ImageView title_back;
    private BridgeWebView webView;
    private String url;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_secret_web);
        title_back = (ImageView) findViewById(R.id.title_back);
        webView = (BridgeWebView) findViewById(R.id.web_View);

        url = getIntent().getStringExtra("url");
        WebSettings webSettings = webView.getSettings();
        // webSettings.setUserAgentString();
        webSettings.setJavaScriptEnabled(true);
        // webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setAppCacheEnabled(true);
        String userAgent = getUserAgent();
        webSettings.setUserAgentString("hybridApp/" + userAgent);
        webView.setDefaultHandler(new DefaultHandler());
        webSettings.setAppCacheEnabled(false);
        webView.setWebChromeClient(new WebChromeClient());
        webView.loadUrl(url);
        webView.setWebViewClient(new MyWebViewClient(webView) {
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);

            }
        });


        title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public String getUserAgent() {
        String userAgentString = new WebView(SecretWebActivity.this).getSettings().getUserAgentString();
        return userAgentString;
    }

    class MyWebViewClient extends BridgeWebViewClient {

        public MyWebViewClient(BridgeWebView webView) {
            super(webView);
        }

    }


}
