package com.ydd.pockettoycatcherrwz.ui.home;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.TextView;
import android.widget.Toast;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.entity.RechargBean;
import com.ydd.pockettoycatcherrwz.entity.RechargeGridBean;
import com.ydd.pockettoycatcherrwz.entity.Recharge_mouth;
import com.ydd.pockettoycatcherrwz.entity.Recharge_week;
import com.ydd.pockettoycatcherrwz.network.http.BusinessManager;
import com.ydd.pockettoycatcherrwz.network.http.MyCallback;
import com.ydd.pockettoycatcherrwz.network.http.request.impl.ChargeListRequest;
import com.ydd.pockettoycatcherrwz.ui.BaseActivity;
import com.ydd.pockettoycatcherrwz.ui.personal.RechargeConfirmActivity;
import com.ydd.pockettoycatcherrwz.ui.personal.RechargeRecordListActivity;
import com.ydd.pockettoycatcherrwz.util.ListUtil;
import com.ydd.pockettoycatcherrwz.view.VipInfoView;
import com.ydd.pockettoycatcherrwz.widget.CommonTitleBar;

import java.util.ArrayList;
import java.util.List;

public class RechargeNewActivity extends BaseActivity {
    private CommonTitleBar mTitleBar;
    private TextView tv_exchange;
    private GridView recharge_user;

    private VipInfoView viewWeek;
    private VipInfoView viewMonth;

    private RechargeGridBean  week;
    private RechargeGridBean  mouth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recharge_new2);
        //mTitleBar = (CommonTitleBar) findViewById(R.id.common_title_bar);
        tv_exchange = (TextView) findViewById(R.id.tv_exchange);
        viewWeek = (VipInfoView) findViewById(R.id.view_week);
        viewMonth = (VipInfoView) findViewById(R.id.view_month);
       // mTitleBar.setTitle("充值");
        initviews();
        initdata();

        viewWeek.setOnLeftButtonClickListener(new VipInfoView.OnLeftButtonClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                Intent intent = new Intent(RechargeNewActivity.this, RechargeConfirmActivity.class);
                intent.putExtra(
                        RechargeConfirmActivity.INTENT_EXTRA_KEY_RECHARGE_INFO,
                        week);
                startActivity(intent);
            }
        });

        viewMonth.setOnLeftButtonClickListener(new VipInfoView.OnLeftButtonClickListener() {
            @Override
            public void onLeftButtonClick(View v) {
                Intent intent = new Intent(RechargeNewActivity.this, RechargeConfirmActivity.class);
                intent.putExtra(
                        RechargeConfirmActivity.INTENT_EXTRA_KEY_RECHARGE_INFO,
                        mouth);
                startActivity(intent);
            }
        });

        tv_exchange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(RechargeNewActivity.this, RechargeRecordListActivity.class));
            }
        });

        findViewById(R.id.title_back).setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

                            viewWeek.setMoney(result.week.price+"");
                            viewWeek.setkinds_text(result.week.title);
                            viewWeek.settitle(result.week.money+"");
                            viewWeek.setdesc(result.week.desc+"");
                            if (!result.week.mark.equals("")) {
                                viewWeek.setstation(result.week.mark + "");
                            }
                             week = new RechargeGridBean(result.week.price, result.week.money,
                                    result.week.desc, 1, 0, result.week.mark, "week", result.week.detail, result.week.title);
                        /*    mlist.add(new RechargeGridBean(result.week.price,result.week.money,
                                    result.week.desc,1,0,result.week.mark,"week",result.week.detail,result.week.title));*/
                        }
                        if(result.mouth!=null){

                            viewMonth.setMoney(result.mouth.price+"");
                            viewMonth.setkinds_text(result.mouth.title);
                            viewMonth.settitle(result.mouth.money+"");
                            viewMonth.setdesc(result.mouth.desc);
                            if (!result.mouth.mark.equals("")) {
                                viewMonth.setstation(result.mouth.mark + "");
                            }
                            mouth = new RechargeGridBean(result.mouth.price, result.mouth.money,
                                    result.mouth.desc, 1, 0, result.mouth.mark, "mouth", result.mouth.detail, result.mouth.title);
                            /*mlist.add(new RechargeGridBean(result.mouth.price,result.mouth.money,
                                    result.mouth.desc,1,0,result.mouth.mark,"mouth",result.mouth.detail,result.mouth.title));*/
                        }
                        for(int i=0;i<result.normal.size();i++){
                            mlist.add(new RechargeGridBean(result.normal.get(i).price,result.normal.get(i).money,
                                    result.normal.get(i).desc,1,result.normal.get(i).id,result.normal.get(i).mark,"normal",null,""));
                        }
                        GridViewAdapter mGridViewAdapter=new GridViewAdapter(RechargeNewActivity.this,mlist);
                        recharge_user.setAdapter(mGridViewAdapter);
                        recharge_user.setOnItemClickListener(new AdapterView.OnItemClickListener() {
                            @Override
                            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                                Intent intent = new Intent(RechargeNewActivity.this, RechargeConfirmActivity.class);
                                intent.putExtra(
                                        RechargeConfirmActivity.INTENT_EXTRA_KEY_RECHARGE_INFO,
                                        mlist.get(position));
                                startActivity(intent);
                            }
                        });
                }

                    @Override
                    public void onError(String errorCode, String message) {
                        Toast.makeText(RechargeNewActivity.this, message,
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
