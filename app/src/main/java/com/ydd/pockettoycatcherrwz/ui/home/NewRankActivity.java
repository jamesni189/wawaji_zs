package com.ydd.pockettoycatcherrwz.ui.home;

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

public class NewRankActivity extends BaseActivity {

    private BridgeWebView webView;


    private String key;
    private String access_token;

    private String url;
    private ImageView back_left;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_rank);
        webView = (BridgeWebView) findViewById(R.id.wb_rank);
        back_left = (ImageView) findViewById(R.id.back_left);
        url ="https://h5.toy.ydd100.cn/?key=8dd758066c594324962cc2de7ee7a306/#/welfareCenter";


        WebSettings webSettings = webView.getSettings();
       // webSettings.setUserAgentString();
        webSettings.setJavaScriptEnabled(true);
       // webSettings.setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        webSettings.setDomStorageEnabled(true);
        webSettings.setAppCacheEnabled(true);
        webSettings.setCacheMode(WebSettings.LOAD_NO_CACHE);
        String userAgent = getUserAgent();
        webSettings.setUserAgentString("hybridApp/"+userAgent);

        // 添加客户端支持

        webView.setDefaultHandler(new DefaultHandler());
        webSettings.setAppCacheEnabled(false);
        webView.setWebChromeClient(new WebChromeClient());

        webView.setWebViewClient(new MyWebViewClient(webView){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);
//                  showDialog();
            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
//                          dismissDialog();
              /*  progressbar.postDelayed(new Runnable() {
                    @Override
                    public void run() {
                        launcherLayout.setVisibility(View.GONE);
                    }
                }, 1000);*/
            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);
                dismissDialog();
            }
        });

        webView.loadUrl(url, new CallBackFunction() {
            @Override
            public void onCallBack(String data) {
                showToast("==="  + RunningContext.accessToken);
            }
        });
     // webView.loadUrl("javascript:getUserInfo('" +"key\\"+"8dd758066c594324962cc2de7ee7a306" +"\\"+"access_token\\"+RunningContext.accessToken+"')");


        webView.registerHandler("getUserInfo", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                String s="";
                JSONObject jsonObject = new JSONObject();
                try {
                    jsonObject.put("key","8dd758066c594324962cc2de7ee7a306");
                    jsonObject.put("access_token", RunningContext.accessToken);
                    jsonObject.put("expireTime",102434444444444l);
                  //  jsonObject1 = JSONUtil.createJsonObject(jsonObject);
                   // jsonObject1= "key:"+"8dd758066c594324962cc2de7ee7a306"+","+"access_token:"+RunningContext.accessToken;
                    //showToast("===data"  + RunningContext.accessToken);
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


        back_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

    }

    public String getUserAgent()
    {
        String userAgentString = new WebView(NewRankActivity.this).getSettings().getUserAgentString();
        return userAgentString;
    }
    class MyWebViewClient extends BridgeWebViewClient {

        public MyWebViewClient(BridgeWebView webView) {
            super(webView);
        }

    }

}
