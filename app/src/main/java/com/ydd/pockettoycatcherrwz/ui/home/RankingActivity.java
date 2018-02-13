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
import com.ydd.pockettoycatcherrwz.ui.fragment.MaintoRankFragment;
import com.ydd.pockettoycatcherrwz.ui.fragment.RicherRankFragment;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

public class RankingActivity extends BaseActivity implements View.OnClickListener{

    private ImageView ivBack;
    private TextView btn_manito;
    private TextView btn_richer;

    private MaintoRankFragment rankFragment;//大神榜单
    private RicherRankFragment richerRankFragment; //土豪榜单

   // private String url="http://test.h5.toy.ydd100.cn/#/rankList";
    private String url= Constants.BASE_H5_URL+"rankListApp/Catch";
    private BridgeWebView webView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_ranking);

        ivBack = (ImageView) findViewById(R.id.title_back);
        webView = (BridgeWebView) findViewById(R.id.bwView);

        btn_manito = (TextView) findViewById(R.id.btn_manito);
        btn_richer = (TextView) findViewById(R.id.btn_richer);

        ivBack.setOnClickListener(this);
        btn_manito.setOnClickListener(this);
        btn_richer.setOnClickListener(this);
       // setSelector(0);


        initWbView();
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
        webSettings.setAppCacheEnabled(true);
        webView.setWebChromeClient(new WebChromeClient());
        webView.setWebViewClient(new MyWebViewClient(webView){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
                super.onPageStarted(view, url, favicon);

            }

            @Override
            public void onPageFinished(WebView view, String url) {
                super.onPageFinished(view, url);
                  if (url.contains("/rankDetail")){
                     // findViewById(R.id.line_title).setVisibility(View.GONE);
                      Intent intent = new Intent(RankingActivity.this,OtherInfoActivity.class);
                      intent.putExtra("url",url);
                      startActivity(intent);
                      webView.goBack();
                  }

            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);

             //   dismissDialog();
            }
        });



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
            }
        });

        webView.registerHandler("enterAppPage", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i("===",data);
            }
        });
    }


    @Override
    public void onClick(View v) {

        switch (v.getId()){
            case R.id.btn_manito:
            //    url = "http://test.h5.toy.ydd100.cn/?key=/#/rankListApp/Catch";
                url = "http://test.h5.toy.ydd100.cn/#/rankListApp/Catch";
                webView.loadUrl(url);
                btn_manito.setTextColor(getResources().getColor(R.color.white));
                btn_richer.setTextColor(getResources().getColor(R.color.common_text_gray));

            //   setSelector(0);
                break;
            case R.id.btn_richer:
                url = "http://test.h5.toy.ydd100.cn/#/rankListApp/Diamond";
                webView.loadUrl(url);
                btn_richer.setTextColor(getResources().getColor(R.color.white));
                btn_manito.setTextColor(getResources().getColor(R.color.common_text_gray));
              //  setSelector(1);
                break;
            case R.id.title_back:
                finish();
                break;
        }
    }




    public String getUserAgent()
    {
        String userAgentString = new WebView(RankingActivity.this).getSettings().getUserAgentString();
        return userAgentString;
    }
    class MyWebViewClient extends BridgeWebViewClient {

        public MyWebViewClient(BridgeWebView webView) {
            super(webView);
        }

    }

}
