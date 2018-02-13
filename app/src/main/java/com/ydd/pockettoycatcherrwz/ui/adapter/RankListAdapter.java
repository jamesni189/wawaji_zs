package com.ydd.pockettoycatcherrwz.ui.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.entity.RankUserInfo;
import com.ydd.pockettoycatcherrwz.util.ImgLoaderUtil;

import java.util.List;

/**
 * Created by Administrator on 2018/2/1.
 */

public class RankListAdapter extends BaseAdapter {

    private List<RankUserInfo> mData;

    private Context context;

    public RankListAdapter(Context context,List<RankUserInfo> mData){
        this.context = context;
        this.mData = mData;
    }


    @Override
    public int getCount() {
        return mData.size();
    }

    @Override
    public Object getItem(int position) {
        return mData.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {

     ViewHolder holder =null;
        if (convertView == null) {
            holder = new ViewHolder();
            convertView = LayoutInflater.from(context).inflate(R.layout.item_rank_list, null, false);
            holder.tvName = (TextView) convertView.findViewById(R.id.tv_rank_name);
            holder.tvCatchNum = (TextView) convertView.findViewById(R.id.tv_catch_num);
            holder.tvRankNum = (TextView) convertView.findViewById(R.id.tv_rank_number);
            holder.ivAvatar = (ImageView) convertView.findViewById(R.id.iv_avatar);
            holder.ivRankNum = (ImageView) convertView.findViewById(R.id.iv_rank_number);

            convertView.setTag(holder);
        } else {
            holder = (ViewHolder) convertView.getTag();
        }
        RankUserInfo rankUserInfo = mData.get(position);
        if (rankUserInfo != null) {
            if (position == 0) {
                holder.ivRankNum.setImageResource(R.mipmap.icon_num1);
            } else if (position == 1) {
                holder.ivRankNum.setImageResource(R.mipmap.icon_num2);
            } else if (position == 2) {
                holder.ivRankNum.setImageResource(R.mipmap.icon_num3);
            } else {
                holder.tvRankNum.setText(position+1);
            }
            int total = rankUserInfo.total;
            String content= "抓中了<font color='#00c24f'>total</font>个";
            holder.tvCatchNum.setText(content);
            ImgLoaderUtil.displayImage(rankUserInfo.avatar,holder.ivAvatar,context.getResources().getDrawable(R.mipmap.pic_zww_default));
            holder.tvName.setText(rankUserInfo.nickName);
        }



        return convertView;
    }


    class ViewHolder {
        ImageView ivAvatar, ivRankNum;
        TextView tvRankNum,tvName,tvCatchNum;
    }
}
