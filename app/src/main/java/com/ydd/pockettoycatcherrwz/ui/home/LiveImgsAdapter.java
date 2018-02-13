package com.ydd.pockettoycatcherrwz.ui.home;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AbsListView;
import android.widget.BaseAdapter;
import android.widget.ImageView;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.util.ImgLoaderUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by jia on 17/11/7.
 */

public class LiveImgsAdapter extends BaseAdapter {

	private Context context;

	private LayoutInflater inflater;

	private List<String> list;

	public LiveImgsAdapter(Context context) {
		this.context = context;
		inflater = LayoutInflater.from(context);
		list = new ArrayList<>();
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
		ViewHolder viewHolder;
		if (view == null) {
			viewHolder = new ViewHolder();
			viewHolder.img = new ImageView(context);
			viewHolder.img.setTag(viewHolder);
			viewHolder.img.setLayoutParams(new AbsListView.LayoutParams(
					ViewGroup.LayoutParams.MATCH_PARENT,
					ViewGroup.LayoutParams.WRAP_CONTENT));
			int radius = (int) context.getResources()
					.getDimension(R.dimen.dlg_common_radius);
			viewHolder.img.setPadding(radius, 0, radius, 0);
			viewHolder.img.setAdjustViewBounds(true);
			viewHolder.img.setScaleType(ImageView.ScaleType.FIT_XY);
		} else {
			viewHolder = (ViewHolder) view.getTag();
		}
		ImgLoaderUtil.displayImage(list.get(i), viewHolder.img,
				context.getResources().getDrawable(R.mipmap.pic_zww_default));
		return viewHolder.img;
	}

	class ViewHolder {
		public ImageView img;
	}

	public void refreshUi(List<String> list) {
		this.list.clear();
		this.list.addAll(list);
		notifyDataSetChanged();
	}
}
