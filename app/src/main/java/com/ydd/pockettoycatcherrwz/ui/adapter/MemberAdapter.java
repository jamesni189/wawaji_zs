package com.ydd.pockettoycatcherrwz.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.view.ViewGroup;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.entity.Players;
import com.ydd.pockettoycatcherrwz.ui.home.OtherInfoActivity;
import com.ydd.pockettoycatcherrwz.util.Constants;
import com.ydd.pockettoycatcherrwz.util.ImgLoaderUtil;
import com.ydd.pockettoycatcherrwz.widget.RoundImageView;


/**
 * Created by WZH on 2016/12/25.
 */

public class MemberAdapter extends BaseListAdapter<Players> {
    private Context context;
    public MemberAdapter(Context ctx) {
        super(ctx);
        context = ctx;
    }
    @Override
    public View getView(final int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_menber, null, false);
            viewHolder.avatar = (RoundImageView) convertView.findViewById(R.id.avatar);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }
        Players member = (Players) datas.get(position);
        if (!TextUtils.isEmpty(member.avatar)) {
            // Glide.with(ctx).load(member.img).placeholImageViewder(R.drawable.default_head).into(viewHolder.avatar);
            ImgLoaderUtil.displayImage(member.avatar, viewHolder.avatar);
        }else {
            viewHolder.avatar.setImageResource(R.mipmap.pic_zww_default);
        }


        viewHolder.avatar.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Players players = datas.get(position);
                if (players != null && players.member_id !=0) {
                    String urls = Constants.BASE_H5_URL + "rankDetail/" + players.member_id;
                    Intent intent = new Intent(context, OtherInfoActivity.class);
                    intent.putExtra("url", urls);
                    context.startActivity(intent);
                }
            }
        });

        return  convertView ;
    }
    public class ViewHolder{
        RoundImageView avatar ;
    }
}
