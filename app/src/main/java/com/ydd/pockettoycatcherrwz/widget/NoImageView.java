package com.ydd.pockettoycatcherrwz.widget;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ydd.pockettoycatcherrwz.R;

/**
 * Created by jia on 17/11/4.
 */

public class NoImageView extends RelativeLayout {

    /**
     *
     * @param context
     * @param type
     * 1 没娃娃 2 没订单 3 没地址
     */

    public static final int NoToys=1;

    public static final int NoOrders=2;

    public static final int NoAddress=3;

    public static final int NoRechareg=4;

    public static final int NoMessage=5;

    public static final int NoToysTakes=6;

    private Context context;

    private int type;

    private TextView detailtext;

    private ImageView noimg;

    public NoImageView(Context context,int type) {
        super(context);
        this.context=context;
        this.type=type;
        initView();
    }

    private void initView(){
        View view= LayoutInflater.from(context).inflate(R.layout.layout_noimg,null);
        this.addView(view,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
        noimg=(ImageView)view.findViewById(R.id.imgno);
        detailtext=(TextView)view.findViewById(R.id.textno);
        switch (type){
            case NoToys:
                noimg.setImageResource(R.drawable.nowawa);
                detailtext.setText("暂无娃娃");
                break;
            case NoOrders:
                noimg.setImageResource(R.mipmap.no_orders_icon);
                detailtext.setText("暂无订单记录");
                break;
            case NoAddress:
                noimg.setImageResource(R.drawable.noaddress);
                detailtext.setText("暂无地址");
                break;

            case NoRechareg:
               // noimg.setImageResource(R.drawable.zz_norechare);
                detailtext.setText("暂无充值记录");
                break;
            case NoMessage:
             //   noimg.setImageResource(R.drawable.zz_nomessage);
                detailtext.setText("暂无消息");
                break;
            case NoToysTakes:
           //     noimg.setImageResource(R.drawable.zz_toys_takes);
                detailtext.setText("暂无抓取记录");
                break;
        }
    }


}
