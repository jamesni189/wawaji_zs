package com.ydd.pockettoycatcherrwz.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.entity.Doll;
import com.ydd.pockettoycatcherrwz.util.ImgLoaderUtil;
import com.ydd.pockettoycatcherrwz.util.LogUtil;
import com.ydd.pockettoycatcherrwz.widget.RoundedCPImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jia on 17/11/4.
 */

public class ToysBagAdapter extends BaseAdapter {


    private Context context;

    private List<Doll> list;

    private LayoutInflater inflater;

    public ToysBagAdapter(Context context){
        inflater=LayoutInflater.from(context);
        this.context=context;
        list=new ArrayList<>();
    }



    @Override
    public int getCount() {
        return list.size();
    }

    @Override
    public Object getItem(int i) {
        return null;
    }

    @Override
    public long getItemId(int i) {
        return 0;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        ViewHolder viewHolder=null;
        if(view==null){
            viewHolder=new ViewHolder();
            view=inflater.inflate(R.layout.view_doll,null);
            viewHolder.dollimg=(RoundedCPImageView) view.findViewById(R.id.dollimg);
            viewHolder.newImg=(ImageView)view.findViewById(R.id.newimg);
            viewHolder.detailtxt=(TextView)view.findViewById(R.id.detailtxt);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)view.getTag();
        }
        //FIX
        viewHolder.newImg.setVisibility(View.VISIBLE);
        ImgLoaderUtil.displayImage(list.get(i).img,viewHolder.dollimg,context.getResources().getDrawable(R.mipmap.pic_zww_default));
        viewHolder.detailtxt.setText(list.get(i).name+" x "+list.get(i).num);
        return view;
    }


    public void refreshUi(List<Doll> list){
        LogUtil.printJ("size="+list.size());
        this.list.clear();
        this.list.addAll(list);
        notifyDataSetChanged();
    }


    class ViewHolder{
        public RoundedCPImageView dollimg;

        public ImageView newImg;

        public TextView detailtxt;
    }
}
