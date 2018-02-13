package com.ydd.pockettoycatcherrwz.ui.home;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.entity.Vip_Week;
import com.ydd.pockettoycatcherrwz.entity.Vip_user_messages;
import com.ydd.pockettoycatcherrwz.ui.BaseActivity;
import com.ydd.pockettoycatcherrwz.widget.CommonTitleBar;
import com.ydd.pockettoycatcherrwz.widget.CustomHeadPtrListView;

import java.util.ArrayList;
import java.util.List;

public class LookForDeter_Activity extends BaseActivity {
    private CommonTitleBar mTitleBar;
    private CustomHeadPtrListView vip_user_message;
    private Vip_Week week;
    //private Vip_Month mouth;
    private Vip_Week mouth;
    private int from;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_look_for_deter_);
        mTitleBar = (CommonTitleBar) findViewById(R.id.common_title_bar);
        mTitleBar.setTitle("奖励明细");
        from = getIntent().getIntExtra("kinds", 1);
        if (from == 1) {
            week = (Vip_Week) getIntent().getSerializableExtra("VIP_week");
        }
        if (from == 2) {
            //mouth = (Vip_Month) getIntent().getSerializableExtra("VIP_mouth");
            mouth = (Vip_Week) getIntent().getSerializableExtra("VIP_mouth");
        }
        initviews();
    }

    private void initviews() {
        List<Vip_user_messages> list = new ArrayList<>();
        View view = LayoutInflater.from(LookForDeter_Activity.this).inflate(R.layout.lookfordeler_heard_view, null);
        //累计
        TextView vip_total_accessive = (TextView) view.findViewById(R.id.vip_total_accessive);
        //领取日期
        TextView vip_aceeses_des = (TextView) view.findViewById(R.id.vip_aceeses_des);
        //当日未领取将不可领取，领取到期2017-12-30
        if(from == 1){
            vip_total_accessive.setText(week.totalMoney+"");
            vip_aceeses_des.setText("当日未领取将不可领取，领取到期"+week.erpireDate);
            String str=week.receiveRecord;
            if(!str.equals("") && str!=null){
                String[] strs=str.split("\\|");
                for(int i=0,len=strs.length;i<len;i++){
                    list.add(new Vip_user_messages(week.receiveStatus,strs[i],week.dayMoney));
                }
            }
        }
        if(from == 2) {
            vip_total_accessive.setText(mouth.totalMoney + "");
            vip_aceeses_des.setText("当日未领取将不可领取，领取到期" + mouth.erpireDate);
            String str = mouth.receiveRecord;
            if (!str.equals("") && str != null) {
                String[] strs = str.split("\\|");
                for (int i = 0, len = strs.length; i < len; i++) {
                    list.add(new Vip_user_messages(mouth.receiveStatus, strs[i], mouth.dayMoney));
                }
            }
        }
        vip_user_message = (CustomHeadPtrListView) findViewById(R.id.vip_user_message);
        vip_listview_adapter mvip_listview_adapter = new vip_listview_adapter(LookForDeter_Activity.this, list);
        vip_user_message.addHeaderView(view, null, false);
        vip_user_message.setAdapter(mvip_listview_adapter);
    }
}
