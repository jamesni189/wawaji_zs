package com.ydd.pockettoycatcherrwz.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.GridView;
import android.widget.LinearLayout;

import com.google.gson.Gson;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.entity.BusQuit;
import com.ydd.pockettoycatcherrwz.entity.Doll;
import com.ydd.pockettoycatcherrwz.entity.DollCallback;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.DollListRequest;
import com.ydd.pockettoycatcherrwz.ui.BaseActivity;
import com.ydd.pockettoycatcherrwz.ui.personal.OrderActivity;
import com.ydd.pockettoycatcherrwz.util.ListUtil;
import com.ydd.pockettoycatcherrwz.widget.CommonTitleBar;
import com.ydd.pockettoycatcherrwz.widget.NoImageView;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.List;


/**
 *
 */
public class ToysBagActivity extends BaseActivity implements View.OnClickListener{


    private GridView gridView;

    private CommonTitleBar titleBar;

    private Button getButton;

    private DollCallback dollCallback;

    private ToysBagAdapter adapter;

    private List<Doll> dolllist;

    private DollCallback callback;





    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_toysbag);
        initView();
    }

    private void initView(){
        titleBar=(CommonTitleBar)findViewById(R.id.common_title_bar);
        gridView=(GridView)findViewById(R.id.gridview);
        NoImageView imageView=new NoImageView(this,NoImageView.NoToys);
        imageView.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT,1.0f));
        ((ViewGroup) gridView.getParent()).addView(imageView);
        gridView.setEmptyView(imageView);

        getButton=(Button) findViewById(R.id.getbutton);
        getButton.setOnClickListener(this);
        initData();
    }

    private void initData(){
        titleBar.setTitle("娃娃袋");
        adapter=new ToysBagAdapter(this);
        gridView.setAdapter(adapter);
        BusinessManager.getInstance().getDollList(new DollListRequest(RunningContext.accessToken), new MyCallback<DollCallback>() {
            @Override
            public void onSuccess(DollCallback result, String message) {
                callback=result;
                if(!ListUtil.isEmpty(result.data)){
                    dolllist=result.data;
                    adapter.refreshUi(result.data);
                    getButton.setVisibility(View.VISIBLE);
                }
                dollCallback=result;
            }

            @Override
            public void onError(String errorCode, String message) {

            }

            @Override
            public void onFinished() {
            }
        });
    }

    @Override
    public void onClick(View view) {
        if(ListUtil.isEmpty(dolllist)){
            showToast("没有可选娃娃");
            return;
        }
        Intent intent=new Intent();
        intent.putExtra("callback",new Gson().toJson(callback));
        intent.setClass(this, OrderActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(final BusQuit msg) {
        finish();
    }
}
