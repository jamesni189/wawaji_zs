package com.ydd.pockettoycatcherrwz.ui.personal;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.entity.RechargeItemInfo;
import com.ydd.pockettoycatcherrwz.util.ListUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 充值列表
 */
public class RechargeLvAdapter extends BaseAdapter {

	private Context mContext;

	private List<RechargeItemInfo> mDatas;

	public RechargeLvAdapter(Context context) {
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
					.inflate(R.layout.listitem_recharge, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.numTv = (TextView) convertView
					.findViewById(R.id.tv_recharge_item_num);
			viewHolder.commentTv = (TextView) convertView
					.findViewById(R.id.tv_recharge_item_comment);
			viewHolder.priceTv = (TextView) convertView
					.findViewById(R.id.tv_recharge_item_price);
			viewHolder.containerRl = (RelativeLayout) convertView
					.findViewById(R.id.rl_recharge_item_container);
			viewHolder.containerRl.setOnClickListener(mOnClickListener);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		RechargeItemInfo rechargeItemInfo = mDatas.get(position);
		viewHolder.numTv.setText(String.valueOf(rechargeItemInfo.money + " "));
		viewHolder.commentTv.setText(rechargeItemInfo.desc);
		viewHolder.priceTv
				.setText(String.valueOf("￥" + rechargeItemInfo.price + " "));
		int externalPadding = (int) mContext.getResources()
				.getDimension(R.dimen.dlg_recharge_item_padding_external);
		int verticalPadding = (int) mContext.getResources()
				.getDimension(R.dimen.dlg_recharge_item_padding_vertical);
		int horizontalPadding = (int) mContext.getResources()
				.getDimension(R.dimen.dlg_recharge_item_padding_horizontal);
		if (position == 0) {
			convertView.setPadding(horizontalPadding, externalPadding,
					horizontalPadding, verticalPadding);
		} else if (position == mDatas.size() - 1) {
			convertView.setPadding(horizontalPadding, verticalPadding,
					horizontalPadding, externalPadding);
		} else {
			convertView.setPadding(horizontalPadding, verticalPadding,
					horizontalPadding, verticalPadding);
		}
		viewHolder.containerRl.setTag(rechargeItemInfo);
		return convertView;
	}

	private View.OnClickListener mOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			RechargeItemInfo rechargeItemInfo = (RechargeItemInfo) v.getTag();
			if (rechargeItemInfo == null) {
				return;
			}
			Intent intent = new Intent(mContext, RechargeConfirmActivity.class);
			intent.putExtra(
					RechargeConfirmActivity.INTENT_EXTRA_KEY_RECHARGE_INFO,
					rechargeItemInfo);
			mContext.startActivity(intent);
		}
	};

	/**
	 * 刷新列表
	 *
	 * @param datas
	 */
	public void refreshUi(List<RechargeItemInfo> datas) {
		mDatas.clear();
		if (!ListUtil.isEmpty(datas)) {
			mDatas.addAll(datas);
		}
		notifyDataSetChanged();
	}

	private class ViewHolder {

		RelativeLayout containerRl;
		/**
		 * 宝石数
		 */
		TextView numTv;
		/**
		 * 额外说明
		 */
		TextView commentTv;
		/**
		 * 价格
		 */
		TextView priceTv;
	}
}
