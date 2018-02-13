package com.ydd.pockettoycatcherrwz.ui.home;

import android.graphics.Bitmap;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.webkit.WebChromeClient;
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


/*
*商品详情 兑换记录
* */
public class ShopDetailsActivity extends BaseActivity {

    private BridgeWebView webView;
    private String url;
    private ImageView title_back;
    private TextView wb_title;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shop_details);

        webView = (BridgeWebView) findViewById(R.id.bwView);
        title_back = (ImageView) findViewById(R.id.title_back);
        wb_title = (TextView) findViewById(R.id.wb_title);
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

        webView.setWebViewClient(new MyWebViewClient(webView){
            @Override
            public void onPageStarted(WebView view, String url, Bitmap favicon) {
               super.onPageStarted(view, url, favicon);
            }

            @Override
            public void onPageFinished(WebView view, String url) {
             //   super.onPageFinished(view, url);
                if (url.contains("/goodsDetail")) {
                    super.onPageFinished(view, url);
                }else if (url.contains("/convertList")){
                    wb_title.setText("兑换记录");
                }
            }

        });
        webView.loadUrl(url);

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


       /* webView.registerHandler("enterRoom", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i("===",data);
                // function.onCallBack(data);
                RoomItem roomItem = new Gson().fromJson(data, RoomItem.class);
                Intent intent = new Intent(ShopDetailsActivity.this, LiveActivityNew.class);
                intent.putExtra("roomItem", roomItem);
                startActivity(intent);
            }
        });*/


    }

    public String getUserAgent()
    {
        String userAgentString = new WebView(ShopDetailsActivity.this).getSettings().getUserAgentString();
        return userAgentString;
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        //这是一个监听用的按键的方法，keyCode 监听用户的动作，如果是按了返回键，同时Webview要返回的话，WebView执行回退操作，因为mWebView.canGoBack()返回的是一个Boolean类型，所以我们把它返回为true
        if(keyCode==KeyEvent.KEYCODE_BACK && webView.canGoBack()){
           // webView.clearHistory();
            webView.goBack();

            return true;
        }

        return super.onKeyDown(keyCode, event);
    }

    class MyWebViewClient extends BridgeWebViewClient {

        public MyWebViewClient(BridgeWebView webView) {
            super(webView);
        }



    }

}
