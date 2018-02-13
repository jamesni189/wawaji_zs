package com.ydd.pockettoycatcherrwz.view;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.annotation.Nullable;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.widget.StrokeTextView;

import java.text.DecimalFormat;

/**
 * Created by Administrator on 2018/2/8.
 */

public class VipInfoView extends LinearLayout {


    private OnLeftButtonClickListener mLeftButtonClickListener;

    private MyViewHolder mViewHolder;
    private View viewAppTitle;

    public VipInfoView(Context context) {
        super(context);
        init();
    }

    public VipInfoView(Context context, @Nullable AttributeSet attrs) {
        super(context, attrs);
        init();
    }

    public VipInfoView(Context context, @Nullable AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        init();
    }

    private void init()
    {
        LayoutInflater inflater = (LayoutInflater) this.getContext().getSystemService(Context.LAYOUT_INFLATER_SERVICE);

        LayoutParams layoutParams = new LayoutParams(LayoutParams.MATCH_PARENT, LayoutParams.WRAP_CONTENT);
        viewAppTitle = inflater.inflate(R.layout.recharge_gridview, null);
        this.addView(viewAppTitle, layoutParams);

        mViewHolder = new MyViewHolder(this);
        mViewHolder.recharge_money.setOnClickListener(new OnClickListener()
        {
            @Override
            public void onClick(View v)
            {
                if (mLeftButtonClickListener != null)
                {
                    mLeftButtonClickListener.onLeftButtonClick(v);
                }
            }
        });



       // viewHolder.strokeTextView.setText(mlist.get(position).money + "");

        mViewHolder.recharge_normal.setVisibility(View.GONE);
        mViewHolder.recharge_kinds.setVisibility(View.VISIBLE);
        mViewHolder.recharge_money.setTextColor(getResources().getColor(R.color.app_login_title_yellow));
        mViewHolder.rechager_gold_back.setBackground(getResources().getDrawable(R.drawable.commer_background_yellow));
    }
    public void setAppBackground(int color)
    {
        mViewHolder.rechager_gold_back.setBackground(getResources().getDrawable(color));
    }

    public void settitle(String color)
    {
        mViewHolder.strokeTextView.setText(color);

    }
    public void setdesc(String color)
    {
        mViewHolder.recharge_message.setText(color);

    }


    public void setkinds_text(String color)
    {
        mViewHolder.recharge_kinds_text.setText(color);
        mViewHolder.recharge_kinds.setVisibility(VISIBLE);
    }



    public void setMoney(String color)
    {
        DecimalFormat df = new DecimalFormat("######0.00");
        mViewHolder.recharge_money.setText(String.valueOf("￥" + df.format(Double.valueOf(color)) + " "));
    }

    public void setstation(String color)
    {
        mViewHolder.rechange_station.setVisibility(VISIBLE);
        mViewHolder.rechange_station.setText(color);
    }



    public void setMoneyBackground(Drawable color)
    {
        mViewHolder.recharge_money.setBackground(color);
    }



    static class MyViewHolder
    {
        private TextView recharge_message;
        private StrokeTextView strokeTextView;
        private TextView recharge_money;
        private LinearLayout rechager_gold_back;
        private TextView rechange_station;
        //月卡 周卡
        private LinearLayout recharge_kinds;
        private TextView recharge_kinds_text;
        //普通
        private LinearLayout recharge_normal;

        public MyViewHolder(View v)
        {
            recharge_message = (TextView) v.findViewById(R.id.recharge_message);
            strokeTextView = (StrokeTextView) v.findViewById(R.id.tv_recharge_item_num);
            recharge_money = (TextView) v.findViewById(R.id.recharge_money);
            rechager_gold_back = (LinearLayout) v.findViewById(R.id.rechager_gold_back);
            rechange_station = (TextView) v.findViewById(R.id.rechange_station);
            recharge_kinds = (LinearLayout) v.findViewById(R.id.recharge_kinds);
            recharge_kinds_text = (TextView) v.findViewById(R.id.recharge_kinds_text);
            recharge_normal = (LinearLayout) v.findViewById(R.id.recharge_normal);
        }
    }

    public static abstract interface OnLeftButtonClickListener
    {
        public abstract void onLeftButtonClick(View v);
    }
    public void setOnLeftButtonClickListener(OnLeftButtonClickListener listen)
    {
        mLeftButtonClickListener = listen;
    }
}
