package com.ydd.pockettoycatcherrwz.ui.home;

import android.app.Activity;
import android.os.Handler;
import android.view.LayoutInflater;
import android.widget.ListView;
import android.widget.RelativeLayout;
import android.widget.Toast;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.entity.RoomLogBack;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.RoomLogRequest;
import com.ydd.pockettoycatcherrwz.util.ListUtil;
import com.ydd.pockettoycatcherrwz.util.LogUtil;

/**
 * Created by jia on 17/11/8.
 */

public class LiveGrabRecordsView extends RelativeLayout {

    /**
     * 订单列表
     */
    private PullToRefreshListView mGrabLv;
    /**
     * 列表适配器
     */
    private LiveGrabRecordAdapter mAdapter;
    /**
     * 当前页码
     */
    private int mCurrentPage;

    private Activity context;

    private String machineId;

    public LiveGrabRecordsView(Activity context,String machineId) {
        super(context);
        this.context=context;
        this.machineId=machineId;
        initView();
    }

    private void initView(){
        LogUtil.printJ("record initview");
        mGrabLv = (PullToRefreshListView) LayoutInflater.from(context)
                .inflate(R.layout.fragment_live_grab_records, null);
        mAdapter = new LiveGrabRecordAdapter(context);
        mGrabLv.setAdapter(mAdapter);
        mGrabLv.setMode(PullToRefreshBase.Mode.BOTH);
        mGrabLv.setOnRefreshListener(
                new PullToRefreshBase.OnRefreshListener2<ListView>() {
                    @Override
                    public void onPullDownToRefresh(
                            PullToRefreshBase<ListView> refreshView) {
                        loadData(1);
                    }

                    @Override
                    public void onPullUpToRefresh(
                            PullToRefreshBase<ListView> refreshView) {
                        loadData(mCurrentPage + 1);
                    }
                });
        loadData(1);
        this.addView(mGrabLv,LayoutParams.MATCH_PARENT,LayoutParams.MATCH_PARENT);
    }



    /**
     * 加载数据
     *
     * @param page
     */
    private void loadData(final int page) {
        RoomLogRequest request = new RoomLogRequest(machineId,RunningContext.accessToken,page,20);
        BusinessManager.getInstance().getRoomLog(request,
                new MyCallback<RoomLogBack>() {
                    @Override
                    public void onSuccess(RoomLogBack result,
                                          String message) {
                        Activity activity = context;
                        if (activity == null || activity.isFinishing()) {
                            return;
                        }
                        if (result == null) {
                            return;
                        }
                        if (ListUtil.isEmpty(result.data)) {
                            Toast.makeText(activity, "没有更多数据",
                                    Toast.LENGTH_SHORT).show();
                            return;
                        }
                        mCurrentPage = page;
                        if (page == 1) {
                            mAdapter.refreshUi(result.data);
                        } else {
                            mAdapter.refreshUiByAdd(result.data);
                        }
                    }

                    @Override
                    public void onError(String errorCode, String message) {
                        Activity activity = context;
                        if (activity != null && !activity.isFinishing()) {
                            Toast.makeText(activity, "没有更多数据",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }

                    @Override
                    public void onFinished() {
                        mGrabLv.onRefreshComplete();
                    }
                });
    }

    /**
     * 自动加载
     */
    public  void autoScroll() {
        mGrabLv.setRefreshing(true);
        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                loadData(1);
            }
        }, 500);
    }
}
