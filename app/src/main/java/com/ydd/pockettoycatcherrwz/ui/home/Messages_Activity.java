package com.ydd.pockettoycatcherrwz.ui.home;


import android.app.AlertDialog;
import android.content.DialogInterface;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import com.handmark.pulltorefresh.library.PullToRefreshBase;
import com.handmark.pulltorefresh.library.PullToRefreshListView;
import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.entity.MainPage_Messages;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.wechatimpl.DelteMessages;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.wechatimpl.GetMessages;
import com.ydd.pockettoycatcherrwz.ui.BaseActivity;
import com.ydd.pockettoycatcherrwz.ui.adapter.Message_adapter;
import com.ydd.pockettoycatcherrwz.widget.CommonTitleBar;
import com.ydd.pockettoycatcherrwz.widget.NoImageView;


import java.util.List;

public class Messages_Activity extends BaseActivity {

    private CommonTitleBar commonTitleBar;
    private PullToRefreshListView mPullToRefreshListView;
    private List<MainPage_Messages> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_messages_);
        commonTitleBar = (CommonTitleBar) findViewById(R.id.common_title_bar);
        commonTitleBar.setTitle("消息");
        if (RunningContext.loginTelInfo != null) {
            getMessages();
        }

        initviews();
    }

    private void initviews() {
        mPullToRefreshListView = (PullToRefreshListView) findViewById(R.id.mylistmessage);
        NoImageView imageView = new NoImageView(this, NoImageView.NoMessage);
        mPullToRefreshListView.setEmptyView(imageView);
        mPullToRefreshListView.setMode(PullToRefreshBase.Mode.PULL_FROM_START);
        mPullToRefreshListView.setOnRefreshListener(
                new PullToRefreshBase.OnRefreshListener2<ListView>() {
                    @Override
                    public void onPullDownToRefresh(
                            PullToRefreshBase<ListView> refreshView) {
                        getMessages();
                    }

                    @Override
                    public void onPullUpToRefresh(
                            PullToRefreshBase<ListView> refreshView) {

                    }
                });
        mPullToRefreshListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                showNormalDialog(position - 1);
            }
        });
    }

    /**
     * 获取系统消息
     */
    private void getMessages() {
        GetMessages getMessages = new GetMessages();
        BusinessManager.getInstance().getMessages(getMessages,
                new MyCallback<List<MainPage_Messages>>() {
                    @Override
                    public void onSuccess(List<MainPage_Messages> result, String message) {
                        Log.i("adddqqq", "onSuccess: " + result);
                        list = result;
                        Message_adapter mMessage_adapter = new Message_adapter(Messages_Activity.this, result);
                        mPullToRefreshListView.setAdapter(mMessage_adapter);
                    }

                    @Override
                    public void onError(String errorCode, String message) {
                        showToast(message);
                    }

                    @Override
                    public void onFinished() {
                        mPullToRefreshListView.onRefreshComplete();
                    }
                });
    }

    private void showNormalDialog(final int id) {
        Log.i("qqerrds", "showNormalDialog: " + id);
        final AlertDialog.Builder normalDialog =
                new AlertDialog.Builder(Messages_Activity.this, AlertDialog.THEME_TRADITIONAL);
        normalDialog.setMessage("是否删除?");
        normalDialog.setPositiveButton("确定",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do

                        DelteMessages delteMessages = new DelteMessages(String.valueOf(list.get(id).id));
                        BusinessManager.getInstance().delteMessages(delteMessages,
                                new MyCallback() {
                                    @Override
                                    public void onSuccess(Object result, String message) {
                                        showToast("删除成功");
                                        getMessages();
                                    }

                                    @Override
                                    public void onError(String errorCode, String message) {
                                        showToast(message);
                                    }

                                    @Override
                                    public void onFinished() {
                                        mPullToRefreshListView.onRefreshComplete();
                                    }
                                });
                    }
                });
        normalDialog.setNegativeButton("关闭",
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        //...To-do
                        dismissDialog();
                    }
                });
        // 显示
        normalDialog.show();
    }
}
