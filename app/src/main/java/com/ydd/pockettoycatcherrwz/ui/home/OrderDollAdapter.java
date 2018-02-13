package com.ydd.pockettoycatcherrwz.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.entity.Doll;
import com.ydd.pockettoycatcherrwz.util.CommonUtil;
import com.ydd.pockettoycatcherrwz.util.ImgLoaderUtil;
import com.ydd.pockettoycatcherrwz.widget.XCRoundRectImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jia on 17/11/4.
 */

public class OrderDollAdapter extends BaseAdapter {

    private LayoutInflater inflater;

    private List<Doll> dolls;

    private Context mContext;


    public  OrderDollAdapter (Context context){
        inflater=LayoutInflater.from(context);
        mContext=context;
        dolls=new ArrayList<>();
    }

    @Override
    public int getCount() {
        return dolls.size();
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
            view=inflater.inflate(R.layout.item_order_doll,null);
            viewHolder.dollimg=(XCRoundRectImageView)view.findViewById(R.id.dollimg);
            viewHolder.dollnametxt=(TextView)view.findViewById(R.id.dollnametxt);
            viewHolder.dollnumtxt=(TextView)view.findViewById(R.id.dollnumtxt);
            view.setTag(viewHolder);
        }else {
            viewHolder=(ViewHolder)view.getTag();
        }
        CommonUtil.setTextBold(viewHolder.dollnametxt);
        ImgLoaderUtil.displayImage(dolls.get(i).img,viewHolder.dollimg,mContext.getResources().getDrawable(R.mipmap.pic_zww_default));
        viewHolder.dollnametxt.setText(dolls.get(i).name);
        viewHolder.dollnumtxt.setText("x"+dolls.get(i).num);
        return view;
    }


    class ViewHolder{
        public XCRoundRectImageView dollimg;
        public TextView dollnametxt;
        public TextView dollnumtxt;
    }


    public void refreshUi(List<Doll> dolls){
        this.dolls.clear();
        this.dolls.addAll(dolls);
        notifyDataSetChanged();
    }
}
