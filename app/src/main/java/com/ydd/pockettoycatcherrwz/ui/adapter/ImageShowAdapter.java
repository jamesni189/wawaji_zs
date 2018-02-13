package com.ydd.pockettoycatcherrwz.ui.adapter;

import android.content.Context;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.util.ImgLoaderUtil;


/**
 * Created by WZH on 2016/12/25.
 */

public class ImageShowAdapter extends BaseListAdapter<String> {
    public ImageShowAdapter(Context ctx) {
        super(ctx);
    }
    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ViewHolder viewHolder ;
        if (convertView == null) {
            viewHolder = new ViewHolder();
            convertView = inflater.inflate(R.layout.item_image_list, null, false);
            viewHolder.level = (ImageView) convertView.findViewById(R.id.iv_pic);
         //   viewHolder.message = (TextView) convertView.findViewById(R.id.message);
            convertView.setTag(viewHolder);
        }else {
            viewHolder = (ViewHolder) convertView.getTag();
        }

        String url =datas.get(position);
        if (url!=null) {
            ImgLoaderUtil.displayImage(url, viewHolder.level);
        }


        return  convertView ;
    }
    public class ViewHolder{
        ImageView level ;
       // TextView message ;
    }
}
