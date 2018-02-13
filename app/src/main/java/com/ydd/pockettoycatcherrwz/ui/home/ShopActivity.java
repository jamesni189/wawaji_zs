package com.ydd.pockettoycatcherrwz.ui.home;

import android.content.Intent;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.ui.BaseActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class ShopActivity extends BaseActivity {

    private BridgeWebView webView;

    private String url;

    private TextView wb_right_title;
    private ImageView title_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop);

        webView = (BridgeWebView) findViewById(R.id.bwView);
     //   wb_right_title = (TextView) findViewById(R.id.wb_right_title);
        title_back = (ImageView) findViewById(R.id.title_back);
        url ="http://test.h5.toy.ydd100.cn/#/intergalMall";
        initWbView();

    /*    wb_right_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(ShopActivity.this,ShopDetailsActivity.class);
                intent.putExtra("url", Constants.BASE_H5_URL+"#/convertList");
                startActivity(intent);
            }
        });*/
        title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }

    private void initWbView(){
        WebSettings webSettings = webView.getSettings();
        // webSettings.setUserAgentString();
        webSettings.setJavaScriptEnabled(true);
        // webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setDomStorageEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        String userAgent = getUserAgent();
        webSettings.setUserAgentString("hybridApp/"+userAgent);
        // 添加客户端支持
        webView.setDefaultHandler(new DefaultHandler());
        webSettings.setAppCacheEnabled(false);
        webView.setWebChromeClient(new WebChromeClient());



        webView.loadUrl(url, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                // showToast("==="  + RunningContext.accessToken);
            }
        });

        webView.setWebViewClient(new MyWebViewClient(webView){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {

                if (url.contains("/goodsDetail")){
                    // findViewById(R.id.line_title).setVisibility(View.GONE);
                    Intent intent = new Intent(ShopActivity.this,ShopDetailsActivity.class);
                    intent.putExtra("url",url);
                    startActivity(intent);
                    webView.goBack();
                }else {
                    super.onPageFinished(view, url);
                }


            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);

                //   dismissDialog();
            }
        });

        webView.registerHandler("getUserInfo", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                String s="";
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("key","8dd758066c594324962cc2de7ee7a306");
                    jsonObject.put("access_token", RunningContext.accessToken);
                    jsonObject.put("expireTime",102434444444444l);
                    s= jsonObject.toString();
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                function.onCallBack(s);

            }
        });

        webView.registerHandler("enterRoom", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i("===",data);
            }
        });

        webView.registerHandler("enterAppPage", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i("===",data);
            }
        });
    }

    public String getUserAgent()
    {
        String userAgentString = new WebView(ShopActivity.this).getSettings().getUserAgentString();
        return userAgentString;
    }
    class MyWebViewClient extends BridgeWebViewClient {

        public MyWebViewClient(BridgeWebView webView) {
            super(webView);
        }

    }

}
