package com.ydd.pockettoycatcherrwz.ui.home;

import android.content.Context;
import android.content.Intent;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Toast;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.entity.RoomItem;
import com.ydd.pockettoycatcherrwz.entity.RoomStatusInfo;
import com.ydd.pockettoycatcherrwz.util.ListUtil;

import java.util.ArrayList;
import java.util.List;

/**
 * 首页，每条item2条数据
 */
public class HomeRoomLvAdapter extends BaseAdapter {

	private Context mContext;

	private List<RoomItem> mDatas;

	public HomeRoomLvAdapter(Context context) {
		mContext = context;
		mDatas = new ArrayList<>();
	}

	@Override
	public int getCount() {
		return mDatas.size() / 2 + mDatas.size() % 2;
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
					.inflate(R.layout.listitem_home_room, parent, false);
			viewHolder = new ViewHolder();
			viewHolder.leftRoomView = (RoomItemView) convertView
					.findViewById(R.id.room_item_left);
			viewHolder.rightRoomView = (RoomItemView) convertView
					.findViewById(R.id.room_item_right);
			viewHolder.leftRoomView.setOnClickListener(mItemClickListener);
			viewHolder.rightRoomView.setOnClickListener(mItemClickListener);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		// 左侧item
		RoomItem leftRoomItem = mDatas.get(2 * position);
		viewHolder.leftRoomView.refreshView(leftRoomItem);
		viewHolder.leftRoomView.setTag(leftRoomItem);
		// 右侧item
		if (2 * position + 1 <= mDatas.size() - 1) {
			RoomItem rightRoomItem = mDatas.get(2 * position + 1);
			viewHolder.rightRoomView.setVisibility(View.VISIBLE);
			viewHolder.rightRoomView.refreshView(rightRoomItem);
			viewHolder.rightRoomView.setTag(rightRoomItem);
		} else {
			viewHolder.rightRoomView.setVisibility(View.INVISIBLE);
		}
		return convertView;
	}

	private View.OnClickListener mItemClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			RoomItem roomItem = (RoomItem) v.getTag();
			// 跳转
		//	Intent intent = new Intent(mContext, LiveActivity.class);
			if (roomItem.status == 2){
				Toast.makeText(mContext,"设备维护中",Toast.LENGTH_SHORT).show();
			}else {
				Intent intent = new Intent(mContext, LiveActivityNew.class);
				intent.putExtra("roomItem", roomItem);
				mContext.startActivity(intent);
			}
		}
	};

	/**
	 * 刷新列表
	 *
	 * @param datas
	 */
	public void refreshUi(List<RoomItem> datas) {
		mDatas.clear();
		if (!ListUtil.isEmpty(datas)) {
			mDatas.addAll(datas);
		}
		notifyDataSetChanged();
	}

	public void refreshStatus(List<RoomStatusInfo> statusInfos) {
		if (ListUtil.isEmpty(statusInfos)) {
			return;
		}
		if (statusInfos.size() != mDatas.size()) {
			return;
		}
		for (RoomItem roomItem : mDatas) {
			if (TextUtils.isEmpty(roomItem.machineSn)) {
				return;
			}
			for (RoomStatusInfo roomStatusInfo : statusInfos) {
				if (roomItem.machineSn.equals(roomStatusInfo.machineSn)) {
					roomItem.status = roomStatusInfo.status;
					break;
				}
			}
		}
		notifyDataSetChanged();
	}

	/**
	 * 刷新列表
	 *
	 * @param datas
	 */
	public void refreshUiByAdd(List<RoomItem> datas) {
		if (!ListUtil.isEmpty(datas)) {
			mDatas.addAll(datas);
		}
		notifyDataSetChanged();
	}

	public List<RoomItem> getDatas() {
		return mDatas;
	}

	private class ViewHolder {
		RoomItemView leftRoomView;
		RoomItemView rightRoomView;
	}

}
