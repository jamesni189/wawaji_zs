package com.ydd.pockettoycatcherrwz.ui.personal;

import android.app.Dialog;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.entity.AddressInfo;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.DeleteAddressRequest;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.SetDefaultAddressRequest;
import com.ydd.pockettoycatcherrwz.ui.BaseActivity;
import com.ydd.pockettoycatcherrwz.util.CommonUtil;
import com.ydd.pockettoycatcherrwz.util.ListUtil;
import com.ydd.pockettoycatcherrwz.widget.CommonAlertDialog;

import java.util.ArrayList;
import java.util.List;

/**
 * 地址管理
 */
public class AddressManageLvAdapter extends BaseAdapter {

	private BaseActivity mContext;

	private List<AddressInfo> mDatas;

	private OnDataChangedListener mListener;

	private CommonAlertDialog mDeleteDialog;

	public AddressManageLvAdapter(BaseActivity context) {
		mContext = context;
		mDatas = new ArrayList<>();
	}

	@Override
	public int getCount() {
		return mDatas.size();
	}

	@Override
	public AddressInfo getItem(int position) {
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
					.inflate(R.layout.listitem_address, parent, false);
			viewHolder.nameTv = (TextView) convertView
					.findViewById(R.id.tv_item_name);
			viewHolder.mobileTv = (TextView) convertView
					.findViewById(R.id.tv_item_mobile);
			viewHolder.addressTv = (TextView) convertView
					.findViewById(R.id.tv_item_address);
			viewHolder.defaultIv = (ImageView) convertView
					.findViewById(R.id.iv_item_default);
			viewHolder.editTv = (TextView) convertView
					.findViewById(R.id.tv_item_edit);
			viewHolder.deleteTv = (TextView) convertView
					.findViewById(R.id.tv_item_delete);
			viewHolder.defaultIv.setOnClickListener(mOnClickListener);
			viewHolder.addressTv.setOnClickListener(mOnClickListener);
			viewHolder.editTv.setOnClickListener(mOnClickListener);
			viewHolder.deleteTv.setOnClickListener(mOnClickListener);
			convertView.setTag(viewHolder);
		} else {
			viewHolder = (ViewHolder) convertView.getTag();
		}
		AddressInfo addressInfo = mDatas.get(position);
		viewHolder.nameTv.setText(addressInfo.consignee);
		viewHolder.mobileTv.setText(addressInfo.mobile);
		viewHolder.addressTv.setText(addressInfo.address);
		viewHolder.defaultIv.setImageResource(
				addressInfo.isDefault == 1 ? R.mipmap.common_check_back
						: R.mipmap.ic_login_checkbox_normal);
		viewHolder.addressTv.setTag(addressInfo);
		viewHolder.editTv.setTag(addressInfo);
		viewHolder.deleteTv.setTag(addressInfo);
		viewHolder.defaultIv.setTag(addressInfo);
		return convertView;
	}

	private View.OnClickListener mOnClickListener = new View.OnClickListener() {
		@Override
		public void onClick(View v) {
			AddressInfo addressInfo = (AddressInfo) v.getTag();
			if (addressInfo == null) {
				return;
			}
			switch (v.getId()) {
			case R.id.iv_item_default:
				// 默认地址
				setDefault(addressInfo);
				break;
			case R.id.tv_item_edit:
				// 编辑
				Intent intent = new Intent();
				intent.putExtra("info", addressInfo);
				intent.setClass(mContext, AddAddressActivity.class);
				mContext.startActivity(intent);
				break;
			case R.id.tv_item_delete:
				// 删除
				showDeleteDialog(addressInfo);
				break;
			}
		}
	};

	private void showDeleteDialog(final AddressInfo addressInfo) {
		mDeleteDialog = new CommonAlertDialog(mContext);
		mDeleteDialog.setContent("是否删除该地址?", false);
		mDeleteDialog.configLeftBtn("取消",
				new CommonAlertDialog.OnDialogClickListener() {
					@Override
					public void onDialogClick(Dialog dialog, View v) {
						mDeleteDialog.dismiss();
					}
				});
		mDeleteDialog.configRightBtn("确定",
				new CommonAlertDialog.OnDialogClickListener() {
					@Override
					public void onDialogClick(Dialog dialog, View v) {
						deleteAddress(addressInfo);
						mDeleteDialog.dismiss();
					}
				});
		mDeleteDialog.show();
	}

	/**
	 * 设置默认地址
	 * 
	 * @param addressInfo
	 */
	private void setDefault(AddressInfo addressInfo) {
		if (addressInfo.isDefault == 1) {
			return;
		}
		mContext.showDialog("");
		BusinessManager.getInstance().setDefaultAddress(
				new SetDefaultAddressRequest(RunningContext.accessToken,
						addressInfo.id),
				new MyCallback<Void>() {
					@Override
					public void onSuccess(Void result, String message) {
						if (mListener != null) {
							mListener.onDataChanged();
						}
					}

					@Override
					public void onError(String errorCode, String message) {
						mContext.showToast(message);
					}

					@Override
					public void onFinished() {
						mContext.dismissDialog();
					}
				});
	}

	/**
	 * 删除地址
	 * 
	 * @param addressInfo
	 */
	private void deleteAddress(final AddressInfo addressInfo) {
		mContext.showDialog("");
		BusinessManager.getInstance().deleteAddress(
				new DeleteAddressRequest(RunningContext.accessToken,
						addressInfo.id),
				new MyCallback<Void>() {
					@Override
					public void onSuccess(Void result, String message) {
						if (mListener != null) {
							mListener.onAddressDeleted(addressInfo);
							mListener.onDataChanged();
						}
					}

					@Override
					public void onError(String errorCode, String message) {
						mContext.showToast(message);
					}

					@Override
					public void onFinished() {
						mContext.dismissDialog();
					}
				});
	}

	private class ViewHolder {
		/**
		 * 姓名
		 */
		TextView nameTv;
		/**
		 * 电话
		 */
		TextView mobileTv;
		/**
		 * 地址
		 */
		TextView addressTv;
		/**
		 * 默认
		 */
		ImageView defaultIv;
		/**
		 * 编辑
		 */
		TextView editTv;
		/**
		 * 删除
		 */
		TextView deleteTv;
	}

	/**
	 * 刷新列表数据
	 * 
	 * @param datas
	 */
	public void refreshUi(List<AddressInfo> datas) {
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
	public void refreshUiByAdd(List<AddressInfo> datas) {
		if (!ListUtil.isEmpty(datas)) {
			mDatas.addAll(datas);
		}
		notifyDataSetChanged();
	}

	public void setListener(OnDataChangedListener listener) {
		mListener = listener;
	}

	public interface OnDataChangedListener {
		void onDataChanged();

		void onAddressDeleted(AddressInfo addressInfo);
	}

	public void release(){
		CommonUtil.dismissDialog(mDeleteDialog);
	}
}
