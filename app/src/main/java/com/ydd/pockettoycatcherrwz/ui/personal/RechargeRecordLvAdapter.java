package com.ydd.pockettoycatcherrwz.ui.personal;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.entity.RechargeRecord;
import com.ydd.pockettoycatcherrwz.util.ListUtil;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * 充值记录页
 */
public class RechargeRecordLvAdapter extends BaseAdapter {
	private Context mContext;

	private List<RechargeRecord> mDatas;

	public RechargeRecordLvAdapter(Context context) {
		mContext = context;
		mDatas = new ArrayList<>();
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public Object getItem(int position) {
		return mDatas.get(position);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {
		ViewHolder viewHolder;
		if (convertView == null) {
			convertView = LayoutInflater.from(mContext)
					.inflate(R.layout.listitem_recharge_record, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.title = (TextView) convertView
					.findViewById(R.id.tv_recharge_record_item_type);
			viewHolder.payTypeIv = (ImageView) convertView
					.findViewById(R.id.iv_recharge_item_type);
			viewHolder.price = (TextView) convertView
					.findViewById(R.id.tv_recharge_record_item_price);
			viewHolder.timeTv = (TextView) convertView
					.findViewById(R.id.tv_recharge_record_item_time);
			viewHolder.dividerView = convertView
					.findViewById(R.id.divider_recharge_record_item);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		RechargeRecord rechargeRecord = mDatas.get(position);
		viewHolder.title.setText(rechargeRecord.title);
		DecimalFormat df = new DecimalFormat("######0.00");
		viewHolder.price.setText("¥" + df.format(Double.valueOf(rechargeRecord.price)) + " ");
		if (rechargeRecord.type == 3){
			viewHolder.payTypeIv.setImageResource(R.mipmap.ic_price);
		}else if (rechargeRecord.type == 2 || rechargeRecord.type==1){
			viewHolder.payTypeIv.setImageResource(R.mipmap.user_main_vip);
		}
		viewHolder.timeTv.setText(rechargeRecord.createTime);
		if (position == mDatas.size() - 1) {
			viewHolder.dividerView.setVisibility(View.GONE);
		} else {
			viewHolder.dividerView.setVisibility(View.VISIBLE);
		}
		return convertView;
	}

	/**
	 * 刷新列表
	 *
	 * @param datas
	 */
	public void refreshUi(List<RechargeRecord> datas) {
		mDatas.clear();
		if (!ListUtil.isEmpty(datas)) {
			mDatas.addAll(datas);
		}
		notifyDataSetChanged();
	}

	/**
	 * 刷新列表
	 *
	 * @param datas
	 */
	public void refreshUiByAdd(List<RechargeRecord> datas) {
		if (!ListUtil.isEmpty(datas)) {
			mDatas.addAll(datas);
		}
		notifyDataSetChanged();
	}


	private class ViewHolder {
		TextView title;

		ImageView payTypeIv;

		TextView timeTv;

		TextView price;

		View dividerView;
	}
}
