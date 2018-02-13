package com.ydd.pockettoycatcherrwz.ui.fragment;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebSettings;
import android.webkit.WebView;
import android.widget.TextView;

import com.github.lzyzsd.jsbridge.BridgeHandler;
import com.github.lzyzsd.jsbridge.BridgeWebView;
import com.github.lzyzsd.jsbridge.BridgeWebViewClient;
import com.github.lzyzsd.jsbridge.CallBackFunction;
import com.github.lzyzsd.jsbridge.DefaultHandler;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.ui.home.ConvertListActivity;
import com.ydd.pockettoycatcherrwz.ui.home.ShopDetailsActivity;
import com.ydd.pockettoycatcherrwz.util.Constants;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * A simple {@link Fragment} subclass.
 * Activities that contain this fragment must implement the
 * {@link ShopFragment.OnFragmentInteractionListener} interface
 * to handle interaction events.
 * Use the {@link ShopFragment#newInstance} factory method to
 * create an instance of this fragment.
 */
public class ShopFragment extends Fragment {
    // TODO: Rename parameter arguments, choose names that match
    // the fragment initialization parameters, e.g. ARG_ITEM_NUMBER
    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";

    // TODO: Rename and change types of parameters
    private String mParam1;
    private String mParam2;

    private OnFragmentInteractionListener mListener;

    private BridgeWebView webView;

    private String url;

    private TextView wb_right_title;
    private TextView btn_manito;
    private TextView btn_richer;

    public ShopFragment() {
        // Required empty public constructor
    }

    /**
     * Use this factory method to create a new instance of
     * this fragment using the provided parameters.
     *
     * @param param1 Parameter 1.
     * @param param2 Parameter 2.
     * @return A new instance of fragment ShopFragment.
     */
    // TODO: Rename and change types and number of parameters
    public static ShopFragment newInstance(String param1, String param2) {
        ShopFragment fragment = new ShopFragment();
        Bundle args = new Bundle();
        args.putString(ARG_PARAM1, param1);
        args.putString(ARG_PARAM2, param2);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            mParam1 = getArguments().getString(ARG_PARAM1);
            mParam2 = getArguments().getString(ARG_PARAM2);
        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        return inflater.inflate(R.layout.fragment_shop, container, false);
    }

    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        webView = (BridgeWebView) view.findViewById(R.id.bwView);
        btn_manito = (TextView) view.findViewById(R.id.btn_manito);
        btn_richer = (TextView) view.findViewById(R.id.btn_richer);
       // wb_right_title = (TextView) view.findViewById(R.id.wb_right_title);
        url =Constants.BASE_H5_URL+"intergalMall/2";
        initWbView();

       /* wb_right_title.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent =new Intent(getActivity(),ConvertListActivity.class);
                intent.putExtra("url", Constants.BASE_H5_URL+"#/convertList");
                startActivity(intent);
            }
        });*/
        btn_manito.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url =Constants.BASE_H5_URL+"intergalMall/2";
                webView.loadUrl(url);
                btn_manito.setTextColor(getResources().getColor(R.color.white));
                btn_richer.setTextColor(getResources().getColor(R.color.common_text_gray));
            }
        });
        btn_richer.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                url =Constants.BASE_H5_URL+"intergalMall/1";
                webView.loadUrl(url);
                btn_richer.setTextColor(getResources().getColor(R.color.white));
                btn_manito.setTextColor(getResources().getColor(R.color.common_text_gray));
            }
        });

    }

    @Override
    public void onResume() {
        super.onResume();
        webView.loadUrl(url);
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

                if (url.contains("/goodsDetail")){
                    // findViewById(R.id.line_title).setVisibility(View.GONE);
                    Intent intent = new Intent(getActivity(),ShopDetailsActivity.class);
                    intent.putExtra("url",url);
                    startActivity(intent);
                    webView.goBack();

                }else if (url.contains("/convertList")){
                    Intent intent = new Intent(getActivity(),ConvertListActivity.class);
                    intent.putExtra("url",url);
                    startActivity(intent);
                    webView.goBack();
                } else if (url.contains("/intergalMall")){
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
        String userAgentString = new WebView(getActivity()).getSettings().getUserAgentString();
        return userAgentString;
    }

    // TODO: Rename method, update argument and hook method into UI event
    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);
        if (context instanceof OnFragmentInteractionListener) {
            mListener = (OnFragmentInteractionListener) context;
        } else {
          /*  throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");*/
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    class MyWebViewClient extends BridgeWebViewClient {

        public MyWebViewClient(BridgeWebView webView) {
            super(webView);
        }


    }

    /**
     * This interface must be implemented by activities that contain this
     * fragment to allow an interaction in this fragment to be communicated
     * to the activity and potentially other fragments contained in that
     * activity.
     * <p>
     * See the Android Training lesson <a href=
     * "http://developer.android.com/training/basics/fragments/communicating.html"
     * >Communicating with Other Fragments</a> for more information.
     */
    public interface OnFragmentInteractionListener {
        // TODO: Update argument type and name
        void onFragmentInteraction(Uri uri);
    }




}
