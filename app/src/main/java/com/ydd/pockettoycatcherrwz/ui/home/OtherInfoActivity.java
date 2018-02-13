package com.ydd.pockettoycatcherrwz.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.google.gson.Gson;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.entity.RoomItem;
import com.ydd.pockettoycatcherrwz.ui.BaseActivity;
import com.ydd.pockettoycatcherrwz.ui.record.VideoActivity;

import org.json.JSONException;
import org.json.JSONObject;

public class OtherInfoActivity extends BaseActivity {

    private BridgeWebView webView;
    private String url;
    private ImageView title_back;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_other_info);

        webView = (BridgeWebView) findViewById(R.id.bwView);
        title_back = (ImageView) findViewById(R.id.title_back);
        url =getIntent().getStringExtra("url");

        initWbView();
        title_back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                webView.goBack();
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
                showToast("==="  + RunningContext.accessToken);
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
               // function.onCallBack(data);
                RoomItem roomItem = new Gson().fromJson(data, RoomItem.class);
                Intent intent = new Intent(OtherInfoActivity.this, LiveActivityNew.class);
                intent.putExtra("roomItem", roomItem);
                startActivity(intent);
            }
        });

        webView.registerHandler("playVideo", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i("===",data);
                if (!TextUtils.isEmpty(data)){
                    Intent intent = new Intent(OtherInfoActivity.this, VideoActivity.class);
                    intent.putExtra("extra_url", data);
                    startActivity(intent);
                }
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
        String userAgentString = new WebView(OtherInfoActivity.this).getSettings().getUserAgentString();
        return userAgentString;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //这是一个监听用的按键的方法，keyCode 监听用户的动作，如果是按了返回键，同时Webview要返回的话，WebView执行回退操作，因为mWebView.canGoBack()返回的是一个Boolean类型，所以我们把它返回为true
        if(keyCode==KeyEvent.KEYCODE_BACK && webView.canGoBack()){
          //  webView.clearHistory();
            webView.goBack();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }
}
