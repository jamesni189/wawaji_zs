package com.ydd.pockettoycatcherrwz.ui.record;

import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.entity.GrabRecord;
import com.ydd.pockettoycatcherrwz.util.BusinessUtil;
import com.ydd.pockettoycatcherrwz.util.ImgLoaderUtil;
import com.ydd.pockettoycatcherrwz.util.ListUtil;
import com.ydd.pockettoycatcherrwz.widget.XCRoundRectImageView;

import java.util.ArrayList;
import java.util.List;

/**
 * 抓取记录adapter
 */
public class GrabRecordLvAdapter extends BaseAdapter {

	private Context mContext;

	private List<GrabRecord> mDatas;

	public GrabRecordLvAdapter(Context context) {
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
					.inflate(R.layout.listitem_grab_record, parent, false);
			viewHolder.toyImg = (XCRoundRectImageView) convertView
					.findViewById(R.id.iv_grab_item_img);
			viewHolder.nameTv = (TextView) convertView
					.findViewById(R.id.tv_grab_item_name);
			viewHolder.statusTv = (TextView) convertView
					.findViewById(R.id.tv_grab_item_status);
			viewHolder.appealTv = (TextView) convertView
					.findViewById(R.id.tv_grab_item_appeal);
			viewHolder.timeTv = (TextView) convertView
					.findViewById(R.id.tv_grab_item_time);
//			viewHolder.spaceHolderView = convertView
			//		.findViewById(R.id.view_grab_item_space_holder);

//			CommonUtil.setTextBold(viewHolder.nameTv);
//			CommonUtil.setTextBold(viewHolder.statusTv);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		final GrabRecord grabRecord = mDatas.get(position);
		ImgLoaderUtil.displayImage(grabRecord.img, viewHolder.toyImg,
				mContext.getResources().getDrawable(R.mipmap.pic_zww_default));
		viewHolder.nameTv.setText(grabRecord.name);
		viewHolder.timeTv.setText(grabRecord.createTime);
		viewHolder.statusTv
				.setText(BusinessUtil.getGrabStatusDesc(grabRecord.status));
		// 状态颜色设置
		int statusColorId = grabRecord.status == 0 ? R.color.app_title_color
				: R.color.common_text_black;
		viewHolder.statusTv
				.setTextColor(mContext.getResources().getColor(statusColorId));
		// 申诉
		if (grabRecord.appealStatus != -1) {
			viewHolder.appealTv.setVisibility(View.VISIBLE);
			viewHolder.appealTv.setText(
					"" + BusinessUtil.getAppealDesc(grabRecord.appealStatus)
							+ "");
		} else {
			viewHolder.appealTv.setVisibility(View.GONE);
		}
//		viewHolder.spaceHolderView.setVisibility(
//				position == mDatas.size() - 1 ? View.GONE : View.VISIBLE);
		viewHolder.toyImg.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Log.i("vaaffgfg", "onClick: "+grabRecord.toString());
				Intent intent = new Intent();
				intent.setClass(mContext, VideoActivity.class);
				intent.putExtra(VideoActivity.INTENT_EXTRA_URL, grabRecord.url);
				mContext.startActivity(intent);
			}
		});
		convertView.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				Intent intent = new Intent(mContext, GrabDetailActivity.class);
				intent.putExtra(GrabDetailActivity.INTENT_EXTRA_GRAB_RECORD,
						grabRecord);
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
	public void refreshUi(List<GrabRecord> datas) {
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
	public void refreshUiByAdd(List<GrabRecord> datas) {
		if (!ListUtil.isEmpty(datas)) {
			mDatas.addAll(datas);
		}
		notifyDataSetChanged();
	}

	private class ViewHolder {
		/**
		 * 玩具图像
		 */
		ImageView toyImg;
		/**
		 * 名字
		 */
		TextView nameTv;
		/**
		 * 状态
		 */
		TextView statusTv;
		/**
		 * 申诉
		 */
		TextView appealTv;
		/**
		 * 时间
		 */
		TextView timeTv;
		/**
		 * 灰色分割区域
		 */
		View spaceHolderView;
	}
}
