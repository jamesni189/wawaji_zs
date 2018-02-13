package com.ydd.pockettoycatcherrwz.util;

import com.ydd.pockettoycatcherrwz.ui.fragment.HomePageFragment;

/**
 * Created by Administrator on 2018/2/2.
 */

public class CallBackUtils {

    private static HomePageFragment.CusScrollCall mCallBack;

    public static void setCallBack(HomePageFragment.CusScrollCall callBack) {
        mCallBack = callBack;
    }

    public static void doCallonRefresh(){
        //String info = "这里CallBackUtils即将发送的数据。";
        mCallBack.onRefresh();
    }
    public static void doCallonLoadMore(){
        //String info = "这里CallBackUtils即将发送的数据。";
        mCallBack.onLoadMore();
    }

}
