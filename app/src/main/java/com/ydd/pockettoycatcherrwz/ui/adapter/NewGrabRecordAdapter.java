package com.ydd.pockettoycatcherrwz.ui.adapter;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.entity.RoomLogInfo;
import com.ydd.pockettoycatcherrwz.ui.record.VideoActivity;
import com.ydd.pockettoycatcherrwz.util.ImgLoaderUtil;
import com.ydd.pockettoycatcherrwz.util.ListUtil;
import com.ydd.pockettoycatcherrwz.util.LogUtil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by jia on 17/11/7.
 */

public class NewGrabRecordAdapter extends BaseAdapter {

	private List<RoomLogInfo> records;

	private Context context;

	private LayoutInflater inflater;

	public NewGrabRecordAdapter(Context context) {
		this.context = context;
		this.inflater = LayoutInflater.from(context);
		records = new ArrayList<>();
	}

	@Override
	public int getCount() {
		return records.size();
	}

	@Override
	public Object getItem(int i) {
		return records.get(i);
	}

	@Override
	public long getItemId(int i) {
		return 0;
	}

	@Override
	public View getView(final int i, View view, ViewGroup viewGroup) {
		ViewHoler viewHoler = null;
		if (view == null) {
			viewHoler = new ViewHoler();
			view = inflater.inflate(R.layout.item_live_record2, null);
			viewHoler.dollimg = (ImageView) view
					.findViewById(R.id.iv_grab_item_img);
			/*viewHoler.tvDollStatus = (TextView) view
					.findViewById(R.id.tv_doll_status);*/
			viewHoler.tvTime = (TextView) view.findViewById(R.id.tv_grab_item_time);
			viewHoler.tvUsername = (TextView) view
					.findViewById(R.id.tv_grab_item_name);
			view.setTag(viewHoler);
		} else {
			viewHoler = (ViewHoler) view.getTag();
		}

		ImgLoaderUtil.displayImage(records.get(i).getImg(), viewHoler.dollimg,
				context.getResources().getDrawable(R.mipmap.pic_zww_default));
	/*	ImgLoaderUtil.displayImage(records.get(i).avatar, viewHoler.dollimg,
				context.getResources().getDrawable(R.mipmap.pic_zww_default));*/
		if (!TextUtils.isEmpty(RunningContext.loginTelInfo.nickname)) {
			viewHoler.tvUsername.setText(RunningContext.loginTelInfo.nickname);
		}
		// CommonUtil.setTextBold(viewHoler.tvUsername);
		// CommonUtil.setTextBold(viewHoler.tvDollStatus);
		viewHoler.tvUsername.setText(records.get(i).getName());

		if (!TextUtils.isEmpty(records.get(i).getCreateTime())) {

			String time = dateUitls(records.get(i).getCreateTime());
			viewHoler.tvTime.setText(time);

		}
		viewHoler.dollimg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View v) {
				if (records.get(i).getUrl() != null) {
					Intent intent = new Intent();
					intent.setClass(context, VideoActivity.class);
					intent.putExtra(VideoActivity.INTENT_EXTRA_URL, records.get(i).getUrl());
					context.startActivity(intent);
				}
			}
		});
		return view;
	}

	/**
	 * 刷新列表
	 *
	 * @param datas
	 */
	public void refreshUi(List<RoomLogInfo> datas) {
		records.clear();
		if (!ListUtil.isEmpty(datas)) {
			LogUtil.printJ("datas size " + datas.size());
			records.addAll(datas);
		}
		notifyDataSetChanged();
	}

	/**
	 * 刷新列表
	 *
	 * @param datas
	 */
	public void refreshUiByAdd(List<RoomLogInfo> datas) {
		if (!ListUtil.isEmpty(datas)) {
			records.addAll(datas);
		}
		notifyDataSetChanged();
	}

	class ViewHoler {
		public ImageView dollimg;

		public TextView tvUsername;

		public TextView tvDollStatus;

		public TextView tvTime;
	}

	private String dateUitls(String time) {
		Date date = new Date();
		long nowtime = date.getTime();
		long lasttime = stringToLong(time);
		if (lasttime != 0) {
			long realtime = nowtime - lasttime;
			if (realtime < 1000 * 60) {
				return (realtime / 1000) + "秒前";
			} else if (realtime >= 1000 * 60 && realtime < 1000 * 60 * 60) {
				return (realtime / (1000 * 60)) + "分钟前";
			} else if (realtime >= 1000 * 60 * 60
					&& realtime < 1000 * 60 * 60 * 24) {
				return (realtime / (1000 * 60 * 60)) + "小时前";
			} else {
				return (realtime / (1000 * 60 * 60 * 24)) + "天前";
			}
		}
		return "数据错误";
	}

	public static long stringToLong(String strTime) {
		SimpleDateFormat formatter = new SimpleDateFormat(
				"yyyy-MM-dd HH:mm:ss");
		Date date = null;
		try {
			date = formatter.parse(strTime);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		if (date == null) {
			return 0;
		} else {
			long currentTime = date.getTime(); // date类型转成long类型
			return currentTime;
		}
	}

}
