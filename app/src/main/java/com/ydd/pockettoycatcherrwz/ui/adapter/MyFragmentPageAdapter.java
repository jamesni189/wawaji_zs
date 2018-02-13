package com.ydd.pockettoycatcherrwz.ui.adapter;

import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentPagerAdapter;

import java.util.List;

/**
 * Created by Administrator on 2018/2/1.
 */

public class MyFragmentPageAdapter extends FragmentPagerAdapter {

    private List<Fragment> fragments; //Fragment集合

    public MyFragmentPageAdapter(FragmentManager fm) {
        super(fm);
    }

    public MyFragmentPageAdapter(FragmentManager fm, List<Fragment> fragments) {
        super(fm);
        this.fragments = fragments;
    }
    //当前显示的是第几个
    @Override
    public Fragment getItem(int arg0) {
        return fragments.get(arg0);
    }
    //计算需要几个item
    @Override
    public int getCount() {
        return fragments.size();
    }

}
