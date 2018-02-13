package com.ydd.pockettoycatcherrwz.ui.home;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.ui.BaseActivity;
import com.ydd.pockettoycatcherrwz.ui.personal.BottomShareDialog;
import com.ydd.pockettoycatcherrwz.ui.personal.InviteFriendActivity;
import com.ydd.pockettoycatcherrwz.util.Constants;
import com.ydd.pockettoycatcherrwz.util.ImageUtil;
import com.ydd.pockettoycatcherrwz.util.LogUtil;
import com.ydd.pockettoycatcherrwz.util.WaterImgUtil;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.File;

public class NewInviteActivity extends BaseActivity {

    BridgeWebView webView;
    private String url="";
    private ImageView back_left;
    private TextView wb_title;
    private RelativeLayout line_title;

    private BottomShareDialog dialog;
    private boolean mIsSuccess = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_new_invite);
        boolean isTask = getIntent().getBooleanExtra("isTakes", false);
        back_left = (ImageView) findViewById(R.id.back_left);
        wb_title = (TextView) findViewById(R.id.wb_title);
        line_title = (RelativeLayout) findViewById(R.id.line_title);
        if (isTask){
            wb_title.setText("我的碎片");
            line_title.setBackgroundColor(getResources().getColor(R.color.app_login_title_color));
            url ="https://h5.toy.ydd100.cn/?key=8dd758066c594324962cc2de7ee7a306/#/fragmentList";
        }else {
          //  wb_title.setText("");
            url ="https://h5.toy.ydd100.cn/?key=8dd758066c594324962cc2de7ee7a306/#/invite";
        }

        webView =  (BridgeWebView)findViewById(R.id.live_web);
        back_left.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });

        initWbView();
        dealWithWater();

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


            }

            @Override
            public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
                super.onReceivedError(view, request, error);

                //   dismissDialog();
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

        webView.registerHandler("shareApp", new BridgeHandler() {
            @Override
            public void handler(String data, CallBackFunction function) {
                Log.i("===",data);
                showShareDialog();
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
        String userAgentString = new WebView(NewInviteActivity.this).getSettings().getUserAgentString();
        return userAgentString;
    }
    class MyWebViewClient extends BridgeWebViewClient {

        public MyWebViewClient(BridgeWebView webView) {
            super(webView);
        }

    }

    private void showShareDialog() {
        if (dialog == null) {
            dialog = new BottomShareDialog(mIsSuccess, this);
        }
        dialog.show();
    }

    private void dealWithWater() {

        new Thread(new Runnable() {
            @Override
            public void run() {
                if (mIsSuccess) {
                    File f = new File(Constants.CACHE_PATH, "success"
                            + RunningContext.loginTelInfo.inviteCode + ".png");
                    if (f.exists()) {
                        return;
                    }
                    Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                            R.mipmap.share_succeed);
                    Bitmap bmp2 = WaterImgUtil.drawInviteSuccessCode(
                            NewInviteActivity.this, bmp,
                            RunningContext.loginTelInfo.inviteCode);
                    ImageUtil.saveBitmapToDisk(bmp2, Constants.CACHE_PATH,
                            "success" + RunningContext.loginTelInfo.inviteCode);
                } else {
                    File f = new File(Constants.CACHE_PATH, "failed"
                            + RunningContext.loginTelInfo.inviteCode + ".png");
                    if (f.exists())
                        return;
                    LogUtil.printJ("not exists");
                    Bitmap bmp = BitmapFactory.decodeResource(getResources(),
                            R.mipmap.share_fail);
                    Bitmap bmp2 = WaterImgUtil.drawInviteFailedCode(
                            NewInviteActivity.this, bmp,
                            RunningContext.loginTelInfo.inviteCode);
                    ImageUtil.saveBitmapToDisk(bmp2, Constants.CACHE_PATH,
                            "failed" + RunningContext.loginTelInfo.inviteCode);
                }
            }
        }).start();

    }

}
