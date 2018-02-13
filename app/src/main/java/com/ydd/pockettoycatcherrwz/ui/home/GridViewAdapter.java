package com.ydd.pockettoycatcherrwz.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.entity.RechargeGridBean;
import com.ydd.pockettoycatcherrwz.widget.StrokeTextView;

import java.text.DecimalFormat;
import java.util.List;

/**
 * Created by C-yl on 2017/12/11.
 */

public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private List<RechargeGridBean> mlist;

    public GridViewAdapter(Context context, List<RechargeGridBean> mlist) {
        this.context = context;
        this.mlist = mlist;
    }

    @Override
    public int getCount() {
        return mlist.size();
    }

    @Override
    public Object getItem(int position) {
        return mlist.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.recharge_gridview, null);
            // android:layout_marginLeft="50dp"
            //   android:layout_marginRight="50dp"
//            ViewGroup.LayoutParams params = convertView.getLayoutParams();
//            float p = DensityUtil.dip2px(context, 50);
//            //params.setMargins(p,p,p, p);
//            params.height = DensityUtil.dip2px(context, 210);
//            params.width = DensityUtil.dip2px(context, 210);
//            convertView.setLayoutParams(params);
            viewHolder.recharge_message = (TextView) convertView.findViewById(R.id.recharge_message);
            viewHolder.strokeTextView = (StrokeTextView) convertView.findViewById(R.id.tv_recharge_item_num);
            viewHolder.recharge_money = (TextView) convertView.findViewById(R.id.recharge_money);
            viewHolder.rechager_gold_back = (LinearLayout) convertView.findViewById(R.id.rechager_gold_back);
            viewHolder.rechange_station = (TextView) convertView.findViewById(R.id.rechange_station);
            viewHolder.recharge_kinds = (LinearLayout) convertView.findViewById(R.id.recharge_kinds);
            viewHolder.recharge_kinds_text = (TextView) convertView.findViewById(R.id.recharge_kinds_text);
            viewHolder.recharge_normal = (LinearLayout) convertView.findViewById(R.id.recharge_normal);
            convertView.setTag(viewHolder);
        } else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.recharge_message.setText(mlist.get(position).desc);
        DecimalFormat df = new DecimalFormat("######0.00");
        viewHolder.recharge_money.setText(String.valueOf("￥" + df.format(Double.valueOf(mlist.get(position).price)) + " "));
        viewHolder.strokeTextView.setText(mlist.get(position).money + "");
//        viewHolder.recharge_money.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//
//            }
//        });
        if (mlist.get(position).kinds.equals("week") || mlist.get(position).kinds.equals("mouth")) {
            /*viewHolder.rechager_gold_back.setBackgroundResource(R.mipmap.recharge_money_back);
            viewHolder.recharge_normal.setVisibility(View.GONE);
            viewHolder.recharge_kinds.setVisibility(View.VISIBLE);
            viewHolder.recharge_kinds_text.setText(mlist.get(position).title);
            viewHolder.recharge_money.setBackgroundResource(R.drawable.user_recharge_weekormonth);
            viewHolder.recharge_money.setTextColor(context.getResources().getColor(R.color.app_title_color));*/
        }
        if (!mlist.get(position).mark.equals("")) {
            viewHolder.rechange_station.setVisibility(View.VISIBLE);
            viewHolder.rechange_station.setText(mlist.get(position).mark);
        }
        return convertView;
    }

    class ViewHolder {
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
    }
}
