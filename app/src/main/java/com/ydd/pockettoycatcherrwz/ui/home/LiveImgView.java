package com.ydd.pockettoycatcherrwz.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.RelativeLayout;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.util.LogUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jia on 17/11/8.
 */

public class LiveImgView extends RelativeLayout {

    /**
     * 订单列表
     */
    private ListView mGrabLv;
    /**
     * 列表适配器
     */
    private LiveImgsAdapter mAdapter;

    private List<String> list;

    private Context context;


    public LiveImgView(Context context) {
        super(context);
        this.context=context;
        initView();
    }

    private void initView(){
        LogUtil.printJ("img initview");
        list=new ArrayList<>();
        mGrabLv = (ListView) LayoutInflater.from(context)
                .inflate(R.layout.fragment_liveimg, null);
        mAdapter = new LiveImgsAdapter(context);
        mGrabLv.setAdapter(mAdapter);
        mAdapter.refreshUi(list);
        this.addView(mGrabLv);
    }


    public void setData(List<String> list){
        LogUtil.printJ("setdata");
        if(mAdapter!=null) {
            mAdapter.refreshUi(list);
        }else {
            this.list.clear();
            this.list.addAll(list);
        }
    }
}
