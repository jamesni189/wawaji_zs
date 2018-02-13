package com.ydd.pockettoycatcherrwz.ui.home;

import android.content.Context;
import android.graphics.Color;
import android.util.AttributeSet;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.entity.SignInfo;

/**
 * Created by C-yl on 2017/12/13.
 */

public class SignItem_newView extends LinearLayout {

    private TextView sign_text_day_text1;
    private TextView sign_text_day_text2;

    public SignItem_newView(Context context) {
        super(context);
        initView();
    }
    public SignItem_newView(Context context, AttributeSet attrs) {
        super(context, attrs);
        initView();
    }
    private void initView() {
        LayoutInflater layoutInflater = LayoutInflater.from(getContext());
        View rootView = layoutInflater.inflate(R.layout.sign_item_new_linear, this,
                true);
        sign_text_day_text1 = (TextView) rootView.findViewById(R.id.sign_text_day_text1);
        sign_text_day_text2 = (TextView) rootView.findViewById(R.id.sign_text_day_text2);
    }
    public void refreshView(SignInfo signInfo) {
        Log.i("caaadf", "refreshView: "+signInfo.toString());
        sign_text_day_text1.setText("+"+signInfo.points+"");
        sign_text_day_text2.setText(signInfo.desc);
        if (signInfo.status == 0) {
            // 签到
            sign_text_day_text1.setBackgroundResource(R.mipmap.sign_text_day_on);
            sign_text_day_text1.setTextColor(Color.parseColor("#733CF2"));
            sign_text_day_text2.setTextColor(Color.parseColor("#333333"));
        } else {
            // 未签到
            sign_text_day_text1.setBackgroundResource(R.mipmap.sign_text_off);
            sign_text_day_text1.setTextColor(Color.parseColor("#aaaaaa"));
            sign_text_day_text2.setTextColor(Color.parseColor("#aaaaaa"));
        }
    }
}
