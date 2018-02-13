package com.ydd.pockettoycatcherrwz.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.Toast;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.entity.RechargBean;
import com.ydd.pockettoycatcherrwz.entity.RechargeGridBean;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.ChargeListRequest;
import com.ydd.pockettoycatcherrwz.ui.BaseActivity;
import com.ydd.pockettoycatcherrwz.ui.personal.RechargeConfirmActivity;
import com.ydd.pockettoycatcherrwz.util.ListUtil;
import com.ydd.pockettoycatcherrwz.widget.CommonTitleBar;

import java.util.ArrayList;
import java.util.List;

public class Recharge_newActivity extends BaseActivity {
    private CommonTitleBar mTitleBar;
    private GridView recharge_user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_new);
        mTitleBar = (CommonTitleBar) findViewById(R.id.common_title_bar);
        mTitleBar.setTitle("充值");
        initviews();
        initdata();
    }

    private void initdata() {
        BusinessManager.getInstance().chargeList_2(new ChargeListRequest(),
                new MyCallback<RechargBean>() {
                    @Override
                    public void onSuccess(RechargBean result,
                                          String message) {
                        Log.i("vasasas", "onSuccess: "+result.toString());
                        if (ListUtil.isEmpty(result.normal)) {
                            return;
                        }
                        final List<RechargeGridBean>  mlist=new ArrayList<>();

                        if(result.week!=null){
                            mlist.add(new RechargeGridBean(result.week.price,result.week.money,
                                    result.week.desc,1,0,result.week.mark,"week",result.week.detail,result.week.title));
                        }
                        if(result.mouth!=null){
                            mlist.add(new RechargeGridBean(result.mouth.price,result.mouth.money,
                                    result.mouth.desc,1,0,result.mouth.mark,"mouth",result.mouth.detail,result.mouth.title));
                        }
                        for(int i=0;i<result.normal.size();i++){
                            mlist.add(new RechargeGridBean(result.normal.get(i).price,result.normal.get(i).money,
                                    result.normal.get(i).desc,1,result.normal.get(i).id,result.normal.get(i).mark,"normal",null,""));
                        }
                        GridViewAdapter mGridViewAdapter=new GridViewAdapter(Recharge_newActivity.this,mlist);
                        recharge_user.setAdapter(mGridViewAdapter);
                        recharge_user.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(Recharge_newActivity.this, RechargeConfirmActivity.class);
                                intent.putExtra(
                                        RechargeConfirmActivity.INTENT_EXTRA_KEY_RECHARGE_INFO,
                                        mlist.get(position));
                                startActivity(intent);
                            }
                        });
                }

                    @Override
                    public void onError(String errorCode, String message) {
                        Toast.makeText(Recharge_newActivity.this, message,
                                Toast.LENGTH_SHORT).show();
                    }

                    @Override
                    public void onFinished() {

                    }
                });
    }

    private void initviews() {
        recharge_user = (GridView) findViewById(R.id.recharge_user);
    }
}
