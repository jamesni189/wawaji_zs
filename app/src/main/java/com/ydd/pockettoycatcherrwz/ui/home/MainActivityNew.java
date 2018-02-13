package com.ydd.pockettoycatcherrwz.ui.home;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.ydd.pockettoycatcherrwz.R;
import com.ydd.pockettoycatcherrwz.RunningContext;
import com.ydd.pockettoycatcherrwz.ui.BaseActivity;
import com.ydd.pockettoycatcherrwz.ui.fragment.HomePageFragment;
import com.ydd.pockettoycatcherrwz.ui.fragment.MineInfoFragment;
import com.ydd.pockettoycatcherrwz.ui.fragment.ShopFragment;
import com.ydd.pockettoycatcherrwz.ui.fragment.WawaBoxWebFragment;
import com.ydd.pockettoycatcherrwz.ui.login.LoginActivity;

public class MainActivityNew extends BaseActivity implements View.OnClickListener{


    private HomePageFragment homePageFragment = null;
   // private WawaBoxFragment wawaBoxFragment = null;
    private WawaBoxWebFragment wawaBoxFragment = null;
    private ShopFragment shopFragment = null;
    private MineInfoFragment mineInfoFragment = null;
    private TextView tvIndex;//首页
    private TextView tvBox;// 娃娃盒
    private TextView tvShop; //商城
    private TextView tvMine; //我的

    Fragment fragment = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_new);
        tvIndex = (TextView) findViewById(R.id.tv_index);
        tvBox = (TextView) findViewById(R.id.tv_box);
        tvShop = (TextView) findViewById(R.id.tv_shop);
        tvMine = (TextView) findViewById(R.id.tv_mine);


        tvIndex.setOnClickListener(this);
        tvBox.setOnClickListener(this);
        tvShop.setOnClickListener(this);
        tvMine.setOnClickListener(this);
        setSelector(0);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.tv_index:
                setSelector(0);
                break;
            case R.id.tv_box:
                if(RunningContext.loginTelInfo==null){
                    startActivity(new Intent(this,LoginActivity.class));
                    finish();
                    return;
                }
                    setSelector(1);

                break;
            case R.id.tv_shop:
                if(RunningContext.loginTelInfo==null){
                    startActivity(new Intent(this,LoginActivity.class));
                    finish();
                    return;
                }
                setSelector(2);
                break;
            case R.id.tv_mine:
                if(RunningContext.loginTelInfo==null){
                    startActivity(new Intent(this,LoginActivity.class));
                    finish();
                    return;
                }
                setSelector(3);
                break;
        }
    }



    public void setSelector(int selector) {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction transaction = manager.beginTransaction();
        hideFragment(transaction);
        switch (selector) {
            case 0:
                if (homePageFragment == null) {
                    homePageFragment = new HomePageFragment();
                    transaction.add(R.id.frame_index, homePageFragment);
                } else {
                    transaction.show(homePageFragment);
                }
                tvIndex.setSelected(false);
                tvBox.setSelected(true);
                tvShop.setSelected(true);
                tvMine.setSelected(true);
             //   ivImg0.setImageResource(R.mipmap.tab_weixin_pressed);
                break;
            case 1:
                if (wawaBoxFragment == null) {
                    wawaBoxFragment = new WawaBoxWebFragment();
                    transaction.add(R.id.frame_index, wawaBoxFragment);
                } else {
                    transaction.show(wawaBoxFragment);
                }

                tvIndex.setSelected(true);
                tvBox.setSelected(false);
                tvShop.setSelected(true);
                tvMine.setSelected(true);
                //ivImg1.setImageResource(R.mipmap.tab_find_frd_pressed);
                break;
            case 2:
                if (shopFragment == null) {
                    shopFragment = new ShopFragment();
                    transaction.add(R.id.frame_index, shopFragment);
                } else {
                    transaction.show(shopFragment);
                }

                tvIndex.setSelected(true);
                tvBox.setSelected(true);
                tvShop.setSelected(false);
                tvMine.setSelected(true);
             //   ivImg2.setImageResource(R.mipmap.tab_address_pressed);
                break;
            case 3:
                if (mineInfoFragment == null) {
                    mineInfoFragment = new MineInfoFragment();
                    transaction.add(R.id.frame_index, mineInfoFragment);
                } else {
                    transaction.show(mineInfoFragment);
                }
                tvIndex.setSelected(true);
                tvBox.setSelected(true);
                tvShop.setSelected(true);
                tvMine.setSelected(false);
          //      ivImg3.setImageResource(R.mipmap.tab_settings_pressed);
                break;
        }
        transaction.commit();
    }

    private void hideFragment(FragmentTransaction transaction) {
        if (homePageFragment != null) {
            transaction.hide(homePageFragment);
        }
        if (wawaBoxFragment != null) {
            transaction.hide(wawaBoxFragment);
        }
        if (shopFragment != null) {
            transaction.hide(shopFragment);
        }
        if (mineInfoFragment != null) {
            transaction.hide(mineInfoFragment);
        }
    }

}
