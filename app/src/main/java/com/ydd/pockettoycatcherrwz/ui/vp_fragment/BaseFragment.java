package com.ydd.pockettoycatcherrwz.ui.vp_fragment;

import android.content.DialogInterface;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;
import com.umeng.analytics.MobclickAgent;
import com.ydd.pockettoycatcherrwz.PTCApplication;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.UserManager;
import com.ydd.pockettoycatcherrwz.entity.TokenInvalidMsg;
import com.ydd.pockettoycatcherrwz.widget.CPProgressDialog;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

/**
 * Created by C-yl on 2017/11/20.
 */

public class BaseFragment extends Fragment {
    /**
     * toast提示
     */
    private Toast mToast;

    /**
     * 通用弹框
     */
    private CPProgressDialog mDialog;

    private static boolean isUploaded = false;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        EventBus.getDefault().register(this);

        ((PTCApplication) getActivity().getApplication()).initWorkerThread();
        UserManager.getInstance().checkRefresh();
        return super.onCreateView(inflater, container, savedInstanceState);
    }

    /**
     * toast提示
     *
     * @param msg
     *            待提示内容
     */
    public void showToast(String msg) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        if (mToast == null) {
            mToast = Toast.makeText(getActivity(), msg, Toast.LENGTH_SHORT);
        } else {
            mToast.setText(msg);
        }
        mToast.show();
    }

    /**
     * toast提示
     */
    public void showResultErrorToast() {
        showToast(getResources().getString(R.string.network_error));
    }

    /**
     * 弹出对话框
     *
     * @param msg
     *            待展示message
     * @param isCancelable
     *            是否可取消
     * @param cancelListener
     *            取消监听器
     */
    public void showDialog(String msg, boolean isCancelable,
                           DialogInterface.OnCancelListener cancelListener) {
        dismissDialog();
        if (mDialog == null) {
            mDialog = new CPProgressDialog(getActivity());
        }
        if (!TextUtils.isEmpty(msg)) {
            mDialog.setMessage(msg);
        } else {
            mDialog.setMessage("");
        }
        mDialog.setCancelable(isCancelable);
        mDialog.setOnCancelListener(cancelListener);
        mDialog.show();
    }

    /**
     * 弹出对话框
     *
     * @param msg
     *            待展示message
     */
    public void showDialog(String msg) {
        if (TextUtils.isEmpty(msg)) {
            showDialog("正在加载中...", false, null);
            return;
        }
        showDialog(msg, false, null);
    }

    /**
     * dismiss对话框
     */
    public void dismissDialog() {
        if (mDialog != null && mDialog.isShowing()) {
            mDialog.dismiss();
        }
        mDialog = null;
    }

    @Override
    public void onDestroy() {
        dismissDialog();
        EventBus.getDefault().unregister(this);
        super.onDestroy();
    }

    @Override
    public void onResume() {
        super.onResume();
        // 友盟统计
        MobclickAgent.onResume(getActivity());
    }

    @Override
    public void onPause() {
        super.onPause();
        // 友盟统计
        MobclickAgent.onPause(getActivity());
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void onMessage(TokenInvalidMsg msg) {
        getActivity().finish();
    }

}
