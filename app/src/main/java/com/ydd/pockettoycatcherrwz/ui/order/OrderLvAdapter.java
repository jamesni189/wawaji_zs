package com.ydd.pockettoycatcherrwz.ui.order;

import android.content.Context;
import android.content.Intent;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.entity.OrderInfo;
import com.ydd.pockettoycatcherrwz.util.BusinessUtil;
import com.ydd.pockettoycatcherrwz.util.ListUtil;
import com.ydd.pockettoycatcherrwz.widget.CommonToyView;

import java.util.ArrayList;
import java.util.List;

/**
 * 订单中心页
 */
public class OrderLvAdapter extends BaseAdapter {

	private Context mContext;

	private List<OrderInfo> mDatas;

	public OrderLvAdapter(Context context) {
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
			viewHolder = new ViewHolder();
			convertView = LayoutInflater.from(mContext)
					.inflate(R.layout.listitem_order, parent, false);
			viewHolder.orderSnTv = (TextView) convertView
					.findViewById(R.id.tv_item_ordersn);
			viewHolder.statusTv = (TextView) convertView
					.findViewById(R.id.tv_item_status);
			viewHolder.toyHolderLn = (LinearLayout) convertView
					.findViewById(R.id.ln_item_toy_holder);
//			viewHolder.spaceHolderView = convertView
//					.findViewById(R.id.view_item_space_holder);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();

		}
//		if (position == mDatas.size() - 1) {
//			viewHolder.spaceHolderView.setVisibility(View.GONE);
//		} else {
//			viewHolder.spaceHolderView.setVisibility(View.VISIBLE);
//		}

		final OrderInfo orderInfo = mDatas.get(position);
		final String sText = "<font color='#666666'>订单号：</font>"+orderInfo.orderSn;
		viewHolder.orderSnTv.setText(Html.fromHtml(sText));
		viewHolder.statusTv
				.setText(BusinessUtil.getOrderStatus(orderInfo.status));
		if (orderInfo.status == 0) {
			viewHolder.statusTv.setTextColor(
					mContext.getResources().getColor(R.color.common_text_red));
		} else {
			viewHolder.statusTv.setTextColor(mContext.getResources()
					.getColor(R.color.common_text_6));
		}

		viewHolder.toyHolderLn.removeAllViews();
		if (ListUtil.isEmpty(orderInfo.productList)) {
			viewHolder.toyHolderLn.setVisibility(View.GONE);
		} else {
			viewHolder.toyHolderLn.setVisibility(View.VISIBLE);
			for (int i = 0; i < orderInfo.productList
					.size(); i++) {
				CommonToyView commonToyView = new CommonToyView(
						mContext,
						orderInfo.productList.get(i));
				commonToyView.showDivider(
						i == orderInfo.productList.size() - 1
								? false : true);
				viewHolder.toyHolderLn.addView(commonToyView);
			}
		}

		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent=new Intent(mContext,OrderDetailActivity.class);
				intent.putExtra(OrderDetailActivity.INTENT_EXTRA_ORDER_SN,orderInfo.orderSn);
				mContext.startActivity(intent);
			}
		});
		return convertView;
	}

	/**
	 * 刷新列表
	 * 
	 * @param datas
	 */
	public void refreshUi(List<OrderInfo> datas) {
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
	public void refreshUiByAdd(List<OrderInfo> datas) {
		if (!ListUtil.isEmpty(datas)) {
			mDatas.addAll(datas);
		}
		notifyDataSetChanged();
	}

	private class ViewHolder {
		/**
		 * 订单号
		 */
		TextView orderSnTv;
		/**
		 * 状态
		 */
		TextView statusTv;
		/**
		 * 玩具显示的layout
		 */
		LinearLayout toyHolderLn;
		/**
		 * 空格分割区域
		 */
		View spaceHolderView;
	}
}
