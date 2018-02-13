package com.ydd.pockettoycatcherrwz.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.entity.Vip_user_messages;

import java.util.List;

/**
 * Created by C-yl on 2017/12/15.
 */

public class vip_listview_adapter extends BaseAdapter {
    private Context context;
    private List<Vip_user_messages> list;

    public vip_listview_adapter(Context context, List<Vip_user_messages> list) {
        this.context = context;
        this.list = list;
    }

    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int position) {
        return list.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        convertView = LayoutInflater.from(context).inflate(R.layout.vip_messags_items, null);
        TextView takes_station_1 = (TextView) convertView.findViewById(R.id.takes_station_1);
        TextView takes_station_data = (TextView) convertView.findViewById(R.id.takes_station_data);
        TextView takes_station_points = (TextView) convertView.findViewById(R.id.takes_station_points);
        if(list.get(position).data!=null){
            takes_station_1.setText("已领取");
            takes_station_data.setText(list.get(position).data);
            takes_station_points.setText(list.get(position).menoy+"");
        }
        return convertView;
    }
}
