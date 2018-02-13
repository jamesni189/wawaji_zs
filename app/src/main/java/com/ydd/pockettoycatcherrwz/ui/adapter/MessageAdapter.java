package com.ydd.pockettoycatcherrwz.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.network.mina.result.ChatResult;


/**
 * Created by WZH on 2016/12/25.
 */

public class MessageAdapter extends BaseListAdapter<ChatResult> {
    private Context ctx;
    public MessageAdapter(Context ctx) {
        super(ctx);
        this.ctx =ctx;
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_message, null, false);
            viewHolder.level = (TextView) convertView.findViewById(R.id.level);
            viewHolder.message = (TextView) convertView.findViewById(R.id.message);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        ChatResult message = datas.get(position);
        if (message.system == 1){
            viewHolder.message.setText(message.content);
            viewHolder.message.setTextColor(ctx.getResources().getColor(R.color.sign_addview_text_yellow));
        }else {
            viewHolder.level.setText(message.sender + ":");
            viewHolder.message.setText(message.content);
            viewHolder.message.setTextColor(ctx.getResources().getColor(R.color.white));
        }

        return  convertView ;
    }
    public class ViewHolder{
        TextView level ;
        TextView message ;
    }
}
