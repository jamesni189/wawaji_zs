package com.ydd.pockettoycatcherrwz.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;


import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.entity.MainPage_Messages;

import java.util.List;

/**
 * Created by C-yl on 2017/12/4.
 */

public class Message_adapter extends BaseAdapter {
    private Context context;
    private List<MainPage_Messages> list;

    public Message_adapter(Context context, List<MainPage_Messages> list) {
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
        ViewHolder viewHolder;
        if(convertView==null){
            viewHolder=new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.messager_item, null);

            viewHolder.messagers_text_main=(TextView) convertView.findViewById(R.id.messagers_text_main);
            viewHolder.messagers_text_time=(TextView) convertView.findViewById(R.id.messagers_text_time);
            viewHolder.messagers_text_messagers=(TextView) convertView.findViewById(R.id.messagers_text_messagers);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        viewHolder.messagers_text_main.setText(list.get(position).title);
        viewHolder.messagers_text_time.setText(list.get(position).createTime);
        viewHolder.messagers_text_messagers.setText(list.get(position).message);
        return convertView;
    }

    class ViewHolder {
        private TextView messagers_text_main;
        private TextView messagers_text_time;
        private TextView messagers_text_messagers;
    }
}
